#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

int num_threads;
uthread_t *threads;
// You may add your own global variables here.

void randomStall() {
  usleep(random() % 300000);
}

void waitForAllOtherThreads() {
  // TODO
}

void *p(void *v) {
  randomStall();
  printf("a\n");
  waitForAllOtherThreads();
  printf("b\n");
  return NULL;
}

int main(int argc, char **argv) {
  uthread_init(4);
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

  free(threads);
}
