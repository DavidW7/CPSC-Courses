#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h>
#include "uthread.h"
#include "uthread_sem.h"

#ifdef VERBOSE
#define VERBOSE_PRINT(S, ...) printf (S, ##__VA_ARGS__);
#else
#define VERBOSE_PRINT(S, ...) ;
#endif

#define MAX_OCCUPANCY      3
#define NUM_ITERATIONS     100
#define NUM_PEOPLE         20
#define FAIR_WAITING_COUNT 4

/**
 * You might find these declarations useful.
 */
enum Endianness { LITTLE = 0, BIG = 1 };
const static enum Endianness oppositeEnd[] = { BIG, LITTLE };

struct Well {
	uthread_sem_t enter;
	int curOccu;
	int curEndianness; //
	int curTime;
};

struct Well* createWell() {
	struct Well* Well = malloc(sizeof(struct Well));
	Well->enter = uthread_sem_create(1);
	Well->curOccu = 0;
	Well->curEndianness = 0;
	Well->curTime = 0;
	return Well;
}

struct Well* Well;

#define WAITING_HISTOGRAM_SIZE (NUM_ITERATIONS * NUM_PEOPLE)
int             entryTicker;                                          // incremented with each entry
int             waitingHistogram[WAITING_HISTOGRAM_SIZE];
int             waitingHistogramOverflow;
uthread_sem_t waitingHistogramSem;
int             occupancyHistogram[2][MAX_OCCUPANCY + 1];

uthread_t tChecker;
uthread_sem_t terminator;
int personName = 0;
int totalDone = 0;

void enterWell(int end, int *time) {
	// update waiting histogram
	int waitTime = Well->curTime - *time;

	if (waitTime < WAITING_HISTOGRAM_SIZE)
		waitingHistogram[waitTime] ++;
	else
		waitingHistogramOverflow++;

	*time = Well->curTime;

	// change well properties
	Well->curOccu++;
	Well->curEndianness = end;
	Well->curTime++; //update prevTime before increment curTime, because entering at 0 should get 0 wait not 1 wait

	// update occupancy histogram
	occupancyHistogram[end][Well->curOccu]++;

	//well status 
	//printf("well status: curOccu %d curEnd %d curTime %d\n", Well->curOccu, Well->curEndianness, Well->curTime);
}

void leaveWell(int end) {
	entryTicker++;
	uthread_sem_signal(terminator);
	Well->curOccu--;
	Well->curEndianness = end;

	//well status 
	//printf("well status: curOccu %d curEnd %d curTime %d\n", Well->curOccu, Well->curEndianness, Well->curTime);
}

void *tryToEnterWell(void *endianness) {
	int num = personName;
	personName++;
	//printf("person %d: initialize\n", num);
	int *endptr = (int*)endianness;
	int end = *endptr;
	int prevTime = 0;
	//printf("person %d: initialize done\n", num);
	//printf("person %d: lock mx\n", num);
	for (int j = 0; j < NUM_ITERATIONS; j++) {
		//printf("person %d: attempt %d\n", num, j);
		// yielding N times before attempting to enter
		for (int i = 0; i < NUM_PEOPLE; i++) {
			uthread_yield();
		}
		//printf("person: finish yielding\n");
		// wait until can go in 
		// check conditions here
		// conditions to enter are
		// 1. endianness match, currOccu <= maxOccu, wait >= fair wait count
		// 2. currOccu = 0, (wait >= fair wait count or peopleLeft {N - totalDone} > wait) 
		// 3. endianness match, currOccu <= maxOccu, entryticker < fair wait count (initial cond)
		// 4. currOccu = 0, totalDone = 0
		// none of them has to match to continue waiting

		//printf("person %d: start check condition loop\n", num);
		while (1) 
		{
			//printf("well status: curOccu %d curEnd %d curTime %d\n", Well->curOccu, Well->curEndianness, Well->curTime);
			uthread_sem_wait(Well->enter);
			if ((Well->curEndianness == end && Well->curOccu <= MAX_OCCUPANCY && ((Well->curTime - prevTime >= FAIR_WAITING_COUNT) || (entryTicker < FAIR_WAITING_COUNT))) 
				|| (Well->curOccu == 0 && (Well->curTime - prevTime >= FAIR_WAITING_COUNT || Well->curTime - prevTime >= NUM_PEOPLE - totalDone || totalDone == 0))) {
			//	printf("person %d: condition can enter\n", num);
				break;
			}
			else {
			//	printf("person %d: condition cant enter, start wait\n", num);
				for (int i = 0; i < NUM_PEOPLE; i++) {
					uthread_yield();
				}
				uthread_sem_signal(Well->enter);
			}
		}
		// enter well
		//printf("person %d: enter well attempt %d\n", num, j);
		enterWell(end, &prevTime);

		assert(Well->curOccu <= MAX_OCCUPANCY && Well->curOccu >= 0);

		// yield N times before exit
		for (int i = 0; i < NUM_PEOPLE; i++) {
			uthread_yield();
		}

		// exit
		//printf("person %d: leave well\n", num);
		leaveWell(end);

		// another person can enter now
		uthread_sem_signal(Well->enter); 
	}
	totalDone++;
}


void *terminateChecker() {
	//printf("tchecker: initialized\n");
	//printf("tchecker: lock\n");
	while (1) {
		uthread_sem_wait(terminator);
		//printf("tchecker: inside loop\n");
		//printf("tchecker: %d\n", entryTicker);
		if (NUM_ITERATIONS * NUM_PEOPLE == entryTicker) {
			break;
		}
	}
	//printf("tchecker: done");
	return NULL;
}

int main(int argc, char** argv) {
	//printf("%d\n", WAITING_HISTOGRAM_SIZE);
	//printf("0\n");
	uthread_init(4);
	Well = createWell();
	uthread_t pt[NUM_PEOPLE];
	waitingHistogramSem = uthread_sem_create(0);
	//printf("1\n");

	terminator = uthread_sem_create(0);
	tChecker = uthread_create(terminateChecker, NULL);
	//printf("2\n");

	srand(time(NULL));
	for (int i = 0; i < NUM_PEOPLE; i++) {
		int endianness = rand() % 2;
		//printf("%d %d\n", i, endianness);
		//printf("well status: curOccu %d curEnd %d curTime %d\n", Well->curOccu, Well->curEndianness, Well->curTime);
		pt[i] = uthread_create(tryToEnterWell, &endianness);

	}

	//printf("3\n");
	uthread_join(tChecker, NULL);

	//printf("4\n");
	printf("Times with 1 little endian %d\n", occupancyHistogram[LITTLE][1]);
	printf("Times with 2 little endian %d\n", occupancyHistogram[LITTLE][2]);
	printf("Times with 3 little endian %d\n", occupancyHistogram[LITTLE][3]);
	printf("Times with 1 big endian    %d\n", occupancyHistogram[BIG][1]);
	printf("Times with 2 big endian    %d\n", occupancyHistogram[BIG][2]);
	printf("Times with 3 big endian    %d\n", occupancyHistogram[BIG][3]);
	printf("Waiting Histogram\n");
	for (int i = 0; i < WAITING_HISTOGRAM_SIZE; i++)
		if (waitingHistogram[i])
			printf("  Number of times people waited for %d %s to enter: %d\n", i, i == 1 ? "person" : "people", waitingHistogram[i]);
	if (waitingHistogramOverflow)
		printf("  Number of times people waited more than %d entries: %d\n", WAITING_HISTOGRAM_SIZE, waitingHistogramOverflow);

	uthread_sem_destroy(waitingHistogramSem);
	uthread_sem_destroy(Well->enter);
	uthread_sem_destroy(terminator);
	free(Well);
}
