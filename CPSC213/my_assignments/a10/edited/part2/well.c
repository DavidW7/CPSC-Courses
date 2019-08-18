#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <fcntl.h>
#include <unistd.h>
#include <time.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

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
enum Endianness {LITTLE = 0, BIG = 1};
const static enum Endianness oppositeEnd [] = {BIG, LITTLE};

struct Well {
	uthread_mutex_t mx;
	uthread_cond_t enter;
	uthread_cond_t count;
	int curOccu;
	int curEndianness; //
	int curTime;
};

struct Well* createWell() {
  struct Well* Well = malloc (sizeof (struct Well));
  Well->mx = uthread_mutex_create();
  Well->enter = uthread_cond_create(Well->mx);
  Well->curOccu = 0;
  Well->curEndianness = 0;
  Well->curTime = 0;
  return Well;
}

struct Well* Well;

#define WAITING_HISTOGRAM_SIZE (NUM_ITERATIONS * NUM_PEOPLE)
int             entryTicker;                                          // incremented with each entry
int             waitingHistogram         [WAITING_HISTOGRAM_SIZE];
int             waitingHistogramOverflow;
uthread_mutex_t waitingHistogramMutex;
int             occupancyHistogram       [2] [MAX_OCCUPANCY + 1];

uthread_t tChecker;
int personName = 0;
int totalDone = 0;


void recordWaitingTime(int waitingTime) {
	uthread_mutex_lock(waitingHistogramMutex);
	if (waitingTime < WAITING_HISTOGRAM_SIZE)
		waitingHistogram[waitingTime] ++;
	else
		waitingHistogramOverflow++;
	uthread_mutex_unlock(waitingHistogramMutex);
}

void enterWell (int end, int *time) {
	// update waiting histogram
	int waitTime = Well->curTime - *time;
	recordWaitingTime(waitTime);
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
	uthread_unblock(tChecker);
	Well->curOccu--;
	Well->curEndianness = end;
}

void *tryToEnterWell(void *endianness) {
	int num = personName;
	personName++;
	//printf("person %d: initialize\n", num);
	int *endptr = (int*)endianness;
	int end = *endptr;
	int prevTime = 0;
	//printf("person %d: initialize done\n", num);
	uthread_mutex_lock(Well->mx);
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
		// none of them has to match to continue waiting

		//printf("person %d: start check condition loop\n", num);
		while (!((Well->curEndianness == end && Well->curOccu <= MAX_OCCUPANCY && ((Well->curTime - prevTime >= FAIR_WAITING_COUNT)||(entryTicker < FAIR_WAITING_COUNT))) //cond 1 & 3
			|| (Well->curOccu == 0 && (Well->curTime - prevTime >= FAIR_WAITING_COUNT || Well->curTime - prevTime >= NUM_PEOPLE - totalDone)))) //cond 2 & 4
		{
			//printf("well status: curOccu %d curEnd %d curTime %d\n", Well->curOccu, Well->curEndianness, Well->curTime);
			//printf("person %d: condition cant enter, start wait\n", num);
			uthread_cond_wait(Well->enter);
			//printf("person %d: condition can enter\n", num);
		}
		// enter well
		//printf("person %d: enter well attempt %d\n", num, j);
		enterWell(end, &prevTime);
		
		// yield N times before exit
		for (int i = 0; i < NUM_PEOPLE; i++) {
			uthread_yield();
		}

		// exit
		//printf("person %d: leave well\n", num);
		leaveWell(end);

		// another person can enter now
		uthread_cond_signal(Well->enter); 
	}
	totalDone++;
	uthread_mutex_unlock(Well->mx);
}


void *terminateChecker() {
	//printf("tchecker: initialized\n");
	//printf("tchecker: lock\n");
	while (1) {
		uthread_block();
		//printf("tchecker: inside loop\n");
		//printf("tchecker: %d\n", entryTicker);
		if (NUM_ITERATIONS * NUM_PEOPLE == entryTicker) {
			break;
		}
	}
	//printf("tchecker: done");
	return NULL;
}

int main (int argc, char** argv) {
  //printf("%d\n", WAITING_HISTOGRAM_SIZE);
  //printf("0\n");
  uthread_init (4);
  Well = createWell();
  uthread_t pt [NUM_PEOPLE];
  waitingHistogramMutex = uthread_mutex_create ();
  //printf("1\n");

  tChecker = uthread_create(terminateChecker, NULL);
  //printf("2\n");

  srand(time(NULL));
  for (int i = 0; i < NUM_PEOPLE; i++) {
	  int endianness = rand() % 2;
	  //printf("%d %d\n", i, *endianness);
	  //printf("well status: curOccu %d curEnd %d curTime %d\n", Well->curOccu, Well->curEndianness, Well->curTime);
	  pt[i] = uthread_create(tryToEnterWell, &endianness);

  }

  //printf("3\n");
  uthread_join(tChecker, NULL);

  //printf("4\n");
  printf ("Times with 1 little endian %d\n", occupancyHistogram [LITTLE]   [1]);
  printf ("Times with 2 little endian %d\n", occupancyHistogram [LITTLE]   [2]);
  printf ("Times with 3 little endian %d\n", occupancyHistogram [LITTLE]   [3]);
  printf ("Times with 1 big endian    %d\n", occupancyHistogram [BIG] [1]);
  printf ("Times with 2 big endian    %d\n", occupancyHistogram [BIG] [2]);
  printf ("Times with 3 big endian    %d\n", occupancyHistogram [BIG] [3]);
  printf ("Waiting Histogram\n");
  for (int i=0; i<WAITING_HISTOGRAM_SIZE; i++)
    if (waitingHistogram [i])
      printf ("  Number of times people waited for %d %s to enter: %d\n", i, i==1?"person":"people", waitingHistogram [i]);
  if (waitingHistogramOverflow)
    printf ("  Number of times people waited more than %d entries: %d\n", WAITING_HISTOGRAM_SIZE, waitingHistogramOverflow);

  uthread_mutex_destroy(waitingHistogramMutex);
  uthread_cond_destroy(Well->enter);
  uthread_mutex_destroy(Well->mx);
  free(Well);
}
