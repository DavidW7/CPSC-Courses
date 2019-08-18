#include <stdlib.h>
#include <stdio.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

const int N = 1000000;
int counter = 0;

uthread_t t0, t1, t2;
// You may add your own global variables here.

void countUp() {
  for(int i=0; i<N; i++)
    counter += 1;
}

void countDown() {
  for(int i=0; i<N; i++)
    counter -= 1;
}

void *count(void *v) {
  countUp();
  countDown();
  return NULL;
}

int main(int argc, char **argv) {
  uthread_init(4);
  t0 = uthread_create(count, NULL);
  t1 = uthread_create(count, NULL);
  t2 = uthread_create(count, NULL);
  uthread_join(t0, NULL);
  uthread_join(t1, NULL);
  uthread_join(t2, NULL);

  if(counter == 0) {
    printf("counter = 0 OK!\n");
    exit(EXIT_SUCCESS);
  } else {
    printf("counter = %d FAIL\n", counter);
    exit(EXIT_FAILURE);
  }
}
