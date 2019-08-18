#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

#define NUM_ITERATIONS 200
#define NUM_PROCESSORS 4

int max_items;
int num_producers;
int num_consumers;

// histogram [i] == # of times list stored i items
int *histogram; 

// number of items currently produced but not yet consumed
// invariant that you must maintain: 0 >= items >= max_items
int items = 0;

// You may add your own global variables here.

// if necessary wait until items < max_items and then increment items
// assertion checks the invariant that 0 >= items >= max_items
void *producer(void *v) {
  for (int i=0; i<NUM_ITERATIONS; i++) {
    items += 1;
    assert (items >= 0 && items <= max_items);
    histogram[items]++;
  }
  return NULL;
}

// if necessary wait until items > 0 and then decrement items
// assertion checks the invariant that 0 >= items >= max_items
void *consumer(void *v) {
  for (int i=0; i<NUM_ITERATIONS; i++) {
    items -= 1;
    assert (items >= 0 && items <= max_items);
    histogram[items]++;
  }
  return NULL;
}

int main(int argc, char **argv) {

  // init the thread system
  uthread_init(NUM_PROCESSORS);

  if(argc == 1) {
    max_items = 10;
    num_producers = 2;
    num_consumers = 2;
  } else if(argc == 4) {
    max_items = atoi(argv[1]);
    num_producers = atoi(argv[2]);
    num_consumers = atoi(argv[3]);
  } else {
    printf("Usage: %s max_items num_producers num_consumers\n", argv[0]);
    exit(EXIT_FAILURE);
  }

  histogram = malloc(sizeof(int) * (max_items + 1));
  uthread_t *threads = malloc(sizeof(uthread_t) * (num_producers + num_consumers));
  for(int i=0; i < max_items+1; i++)
    histogram[i] = 0;

  // start the threads
  for(int i = 0; i < num_producers; i++)
    threads[i] = uthread_create(producer, NULL);
  for(int i = num_producers; i < num_producers + num_consumers; i++)  
    threads[i] = uthread_create(consumer, NULL);

  // wait for threads to complete
  for(int i=0; i < num_producers + num_consumers; i++)
    uthread_join(threads[i], NULL);

  // sum up
  printf("items value histogram:\n");
  int sum = 0;
  for(int i = 0; i <= max_items; i++) {
    printf("  items=%d, %d times\n", i, histogram[i]);
    sum += histogram[i];
  }
  // checks invariant that every change to items was recorded in histogram exactly one
  assert(sum == (num_producers + num_consumers) * NUM_ITERATIONS);
  printf("histogram sum = %d\n", sum);

  free(threads);
  free(histogram);
}
