#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

int num_threads;
uthread_t *threads;
// You may add your own global variables here.
uthread_mutex_t mx;
uthread_cond_t allAs;
int count = 0;


void randomStall() {
  usleep(random() % 300000);
}

void waitForAllOtherThreads() {
  count++;
  if (count < num_threads) {
	uthread_cond_wait(allAs);
  } else if (count == num_threads) {
	uthread_cond_broadcast(allAs);
  }
  
}

void *p(void *v) {
  uthread_mutex_lock(mx);
  randomStall();
  printf("a\n");
  waitForAllOtherThreads();
  printf("b\n");
  uthread_mutex_unlock(mx);
  return NULL;
}

int main(int argc, char **argv) {
  uthread_init(4);
  
  mx = uthread_mutex_create();
  allAs = uthread_cond_create(mx);
  
  if(argc > 2) {
    printf("Usage: %s [number of threads]\n", argv[0]);
    exit(EXIT_FAILURE);
  }
  if(argc == 2) {
    num_threads = atoi(argv[1]);
  } else {
    num_threads = 3;
  }
  threads = malloc(num_threads * sizeof(uthread_t));

  for (int i=0; i<num_threads; i++)
    threads[i] = uthread_create(p, NULL);
  for (int i=0; i<num_threads; i++)
    uthread_join(threads[i], NULL);

  printf("------\n");

  uthread_cond_destroy(allAs);
  uthread_mutex_destroy(mx);
  free(threads);
}
