#include <stdlib.h>
#include <stdio.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

const int N = 1000000;
int counter = 0;

uthread_t t0, t1, t2;
// You may add your own global variables here.
uthread_mutex_t mx;
//uthread_cond_t subtracting;

void countUp() {
  uthread_mutex_lock(mx);
  for(int i=0; i<N; i++)
    counter += 1;
  uthread_mutex_unlock(mx);
}

void countDown() {
  uthread_mutex_lock(mx);
  for(int i=0; i<N; i++)
    counter -= 1;
  uthread_mutex_unlock(mx);
}

void *count(void *v) {
  //uthread_mutex_lock(mx);
  countUp();
  countDown();
  //uthread_mutex_unlock(mx);
  return NULL;
}

int main(int argc, char **argv) {
  mx = uthread_mutex_create();
  //subtracting = uthread_cond_create(mx);
	
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
    printf("counter = %d FAIL\n", count);
    exit(EXIT_FAILURE);
  }
  
  uthread_mutex_destroy(mx);
}
