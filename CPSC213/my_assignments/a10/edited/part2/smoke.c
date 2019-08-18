#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <fcntl.h>
#include <unistd.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

#define NUM_ITERATIONS 1000

#ifdef VERBOSE
#define VERBOSE_PRINT(S, ...) printf (S, ##__VA_ARGS__);
#else
#define VERBOSE_PRINT(S, ...) ;
#endif

struct Agent {
  uthread_mutex_t mutex;
  uthread_cond_t  match;
  uthread_cond_t  paper;
  uthread_cond_t  tobacco;
  uthread_cond_t  smoke;
};

struct Agent *createAgent() {
  struct Agent *agent = malloc (sizeof (struct Agent));
  agent->mutex   = uthread_mutex_create();
  agent->paper   = uthread_cond_create (agent->mutex);
  agent->match   = uthread_cond_create (agent->mutex);
  agent->tobacco = uthread_cond_create (agent->mutex);
  agent->smoke   = uthread_cond_create (agent->mutex);
  return agent;
}

void freeAgent(struct Agent *agent) {
  uthread_cond_destroy(agent->paper);
  uthread_cond_destroy(agent->match);
  uthread_cond_destroy(agent->tobacco);
  uthread_cond_destroy(agent->smoke);
  uthread_mutex_destroy(agent->mutex);
  free(agent);
}

// Other variables
int iterationsSoFar = 0; // will trade some performance for sanity checker
/*
// variables from prev version of code that didnt work
int count = 0;
int sel = 0;
*/
int tracker[3] = { 0, 0, 0 }; // 0 match, 1 paper, 2 tobacco

/**
 * You might find these declarations helpful.
 *   Note that Resource enum had values 1, 2 and 4 so you can combine resources;
 *   e.g., having a MATCH and PAPER is the value MATCH | PAPER == 1 | 2 == 3
 */
enum Resource            {    MATCH = 1, PAPER = 2,   TOBACCO = 4};
char* resource_name [] = {"", "match",   "paper", "", "tobacco"};

int signal_count [5];  // # of times resource signalled
int smoke_count  [5];  // # of times smoker with resource smoked

/**
 * This is the agent procedure.  It is complete and you shouldn't change it in
 * any material way.  You can re-write it if you like, but be sure that all it does
 * is choose 2 random reasources, signal their condition variables, and then wait
 * wait for a smoker to smoke.
 */
void *agent (void *av) {
  //printf("start agent\n");
  struct Agent *a = av;
  static const int choices[]         = {MATCH|PAPER, MATCH|TOBACCO, PAPER|TOBACCO};
  static const int matching_smoker[] = {TOBACCO,     PAPER,         MATCH};
  //printf("initialized agent\n");
  uthread_mutex_lock (a->mutex);
  //printf("locked mutex from agent\n");
    for (int i = 0; i < NUM_ITERATIONS; i++) {
	  //printf("signal initialize iteration %d\n", i);
      int r = random() % 3;
      signal_count [matching_smoker [r]] ++;
      int c = choices [r];
	  //printf("signal sending iteration %d\n", i);
	  if (c & MATCH) {
		  VERBOSE_PRINT("match available\n");
		  uthread_cond_signal(a->match);
	  }
	  if (c & PAPER) {
		  VERBOSE_PRINT("paper available\n");
		  uthread_cond_signal(a->paper);
	  }
	  if (c & TOBACCO) {
		  VERBOSE_PRINT("tobacco available\n");
		  uthread_cond_signal(a->tobacco);
	  }
	  //printf("agent wait for smoker");
	  VERBOSE_PRINT("agent is waiting for smoker to smoke\n");
	  uthread_cond_wait(a->smoke);
	  //printf("smoker smoked\n");
    }
  uthread_mutex_unlock (a->mutex);
  return NULL;
}

// always send match, paper, tobacco in this order, because of how the loop is set up

// wait match
void *smoker1(void *av) {
	//printf("start match waiter\n");
	struct Agent *a = av;
	//printf("initialized match waiter\n");
	uthread_mutex_lock(a->mutex);
	//printf("locked mutex from match waiter\n");
	while (1) {
		//printf("start wait from match waiter\n");
		uthread_cond_wait(a->match);
		//printf("done wait from match waiter\n");
		tracker[0] = 1;
		if (tracker[0] + tracker[1] + tracker[2] == 2) {
			if (tracker[1] == 1) {
				smoke_count[TOBACCO] ++;
				iterationsSoFar++;
				/*
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
				}
				*/
				tracker[0] = 0;
				tracker[1] = 0;
				tracker[2] = 0;
				uthread_cond_signal(a->smoke);
			}
			else if (tracker[2] == 1) {
				smoke_count[PAPER] ++;
				iterationsSoFar++;
				/*
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
				}
				*/
				tracker[0] = 0;
				tracker[1] = 0;
				tracker[2] = 0;
				uthread_cond_signal(a->smoke);
			}
			else {
				printf("error: paper or tobacco tracker set wrong");
			}
		}
		/*
		//this version depended on signal receive order
		//obviously didnt work
		if (count == 0) {
			sel = 1;
			count++;
		} 
		else if (count == 1) {
			printf("error, get match as second signal, exit\n");
			count = 2;
			uthread_cond_signal(a->paper);
			uthread_cond_signal(a->tobacco);
		}
		else if (count == 2) {
			break;
		}
		*/
		//printf("checked conditions by match waiter\n");
	}
	uthread_mutex_unlock(a->mutex);
	return NULL;
}

// wait paper
void *smoker2(void *av) {
	//printf("start paper waiter\n");
	struct Agent *a = av;
	//printf("init paper waiter\n");
	uthread_mutex_lock(a->mutex);
	//printf("locked mutex from paper water\n");
	while (1) {
		//printf("start wait from paper waiter\n");
		uthread_cond_wait(a->paper);
		//printf("done wait from paper waiter\n");
		tracker[1] = 1;
		if (tracker[0] + tracker[1] + tracker[2] == 2) {
			if (tracker[0] == 1) {
				smoke_count[TOBACCO] ++;
				iterationsSoFar++;
				/*
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
				}
				*/
				tracker[0] = 0;
				tracker[1] = 0;
				tracker[2] = 0;
				uthread_cond_signal(a->smoke);
			}
			else if (tracker[2] == 1) {
				smoke_count[MATCH] ++;
				iterationsSoFar++;
				/*
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
				}
				*/
				tracker[0] = 0;
				tracker[1] = 0;
				tracker[2] = 0;
				uthread_cond_signal(a->smoke);
			}
			else {
				printf("error: match or tobacco tracker set wrong");
			}
		}

		/*
		//this version depended on signal receive order
		//obviously didnt work
		if (count == 0) {
			sel = 2;
			count++;
		}
		else if (count == 1) {
			smoke_count[TOBACCO] ++;
			iterationsSoFar++;
			count = 0;
			if (iterationsSoFar == NUM_ITERATIONS) {  
				printf("total iterations reached, exit\n");
				count = 2;
				uthread_cond_signal(a->match);
				uthread_cond_signal(a->tobacco);
			}
			uthread_cond_signal(a->smoke);
		}
		else if (count == 2) {
			break;
		}
		*/
		//printf("checked conditions by paper waiter\n");
	}
	uthread_mutex_unlock(a->mutex);
	return NULL;
}

// wait tobacco
void *smoker3(void *av) {
	//printf("start tobacco waiter\n");
	struct Agent *a = av;
	//printf("init tobacco waiter\n");
	uthread_mutex_lock(a->mutex);
	//printf("lock mutex from tobacco waiter\n");
	while (1) {
		//printf("start wait from tobacco waiter\n");
		uthread_cond_wait(a->tobacco);
		//printf("done wait from tobacco waiter\n");
		tracker[2] = 1;
		if (tracker[0] + tracker[1] + tracker[2] == 2) {
			if (tracker[0] == 1) {
				smoke_count[PAPER] ++;
				iterationsSoFar++;
				/*
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
				}
				*/
				tracker[0] = 0;
				tracker[1] = 0;
				tracker[2] = 0;
				uthread_cond_signal(a->smoke);
			}
			else if (tracker[1] == 1) {
				smoke_count[MATCH] ++;
				iterationsSoFar++;
				/*
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
				}
				*/
				tracker[0] = 0;
				tracker[1] = 0;
				tracker[2] = 0;
				uthread_cond_signal(a->smoke);
			}
			else {
				printf("error: match or paper tracker set wrong");
			}
		}
		/*
		//this version depended on signal receive order
		//obviously didnt work
		if (count == 0) {
			printf("error, get tobacco as first signal, exit\n");
			count = 2;
			uthread_cond_signal(a->paper);
			uthread_cond_signal(a->match);
		}
		else if (count == 1) {
			if (sel == 1) {
				smoke_count[PAPER] ++;
				iterationsSoFar++;
				count = 0; 
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
					count = 2;
					uthread_cond_signal(a->match);
					uthread_cond_signal(a->paper);
				}
				uthread_cond_signal(a->smoke);
			}
			else if (sel == 2) {
				smoke_count[MATCH] ++;
				iterationsSoFar++;
				count = 0;
				if (iterationsSoFar == NUM_ITERATIONS) {
					printf("total iterations reached, exit\n");
					count = 2;
					uthread_cond_signal(a->match);
					uthread_cond_signal(a->paper);
				}
				uthread_cond_signal(a->smoke);
			}
			else if (sel == 0) {
				printf("error, get match as second signal, exit\n");
				count = 2;
				uthread_cond_signal(a->match);
				uthread_cond_signal(a->paper);
			}
		}
		else if (count == 2) {
			break;
		}
		*/
		//printf("checked conditions by tobacco waiter\n");
	}
	uthread_mutex_unlock(a->mutex);
	return NULL;
}

int main (int argc, char **argv) {
  //printf("0\n");
  uthread_init (7);
  //printf("1\n");
  struct Agent *a = createAgent();
  //printf("2\n");
  uthread_t s1 = uthread_create(smoker1, a);
  uthread_t s2 = uthread_create(smoker2, a);
  uthread_t s3 = uthread_create(smoker3, a);
  //printf("3\n");
  uthread_join (uthread_create (agent, a), 0);
  //printf("4\n");
  assert (signal_count [MATCH]   == smoke_count [MATCH]);
  assert (signal_count [PAPER]   == smoke_count [PAPER]);
  assert (signal_count [TOBACCO] == smoke_count [TOBACCO]);
  assert (smoke_count [MATCH] + smoke_count [PAPER] + smoke_count [TOBACCO] == NUM_ITERATIONS);
  printf ("Smoke counts: %d matches, %d paper, %d tobacco\n",
          smoke_count [MATCH], smoke_count [PAPER], smoke_count [TOBACCO]);
  freeAgent(a);
}
