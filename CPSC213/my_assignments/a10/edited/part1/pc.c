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
uthread_mutex_t mx;
uthread_cond_t cantProduce;
uthread_cond_t cantConsume;

int producersDone;
int consumersDone;

// if necessary wait until items < max_items and then increment items
// assertion checks the invariant that 0 >= items >= max_items
void *producer(void *v) {
  int state = 0;
  //printf("producer: start\n");
  for (int i=0; i<NUM_ITERATIONS; i++) {
	uthread_mutex_lock(mx);
	while (items + 1 > max_items) {
		
      if (state == 0) {
		//printf("producer: signal consumable, because can't produce anymore\n");
	    uthread_cond_signal(cantConsume);
	    state = 1;
	  }
	  
	  uthread_cond_wait(cantProduce);
	}
	//printf("continue produce\n");
	state = 0;
    items += 1;
	//printf("producer: %d\n", items);
	
	/*
	if (items - 1 == 0) {
	  uthread_cond_signal(cantConsume);
	}
	*/
	
    assert (items >= 0 && items <= max_items);
    histogram[items]++;
	if (i + 1 == NUM_ITERATIONS) {
       producersDone++;
	   if (num_consumers == consumersDone) {
		 //printf("producer: signal produceable, because end of loop and no more consumer\n");
	     uthread_cond_signal(cantProduce);
	   } else {
	     //printf("producer: signal consumable, because end of loop\n");
	     uthread_cond_signal(cantConsume);
	   }
	}
	uthread_mutex_unlock(mx);
  }
  //printf("producer: done\n");
  return NULL;
}

// if necessary wait until items > 0 and then decrement items
// assertion checks the invariant that 0 >= items >= max_items
void *consumer(void *v) {
  //printf("consumer: start\n");
  int state = 0;
  for (int i=0; i<NUM_ITERATIONS; i++) {
	uthread_mutex_lock(mx);
	while (items - 1 < 0) {
		
	  if (state == 0) {
		//printf("consumer: signal produceable, because can't consume anymore\n");
	    uthread_cond_signal(cantProduce);
	    state = 1;
	  }
	  
	  uthread_cond_wait(cantConsume);
	}
	//printf("continue consume\n");
	state = 0;
    items -= 1;
	//printf("consumer: %d\n", items);
	
	/*
	if (items + 1 == max_items) {
	  uthread_cond_signal(cantProduce);
	}
	*/
	
    assert (items >= 0 && items <= max_items);
    histogram[items]++;
	
	if (i + 1 == NUM_ITERATIONS) {
	  consumersDone++;
	  if (producersDone == num_producers) {
		//printf("consumer: signal consumable, because end of loop and no more producer\n");
	    uthread_cond_signal(cantConsume);
	  } else {
		//printf("consumer: signal produceable, because end of loop\n");
	    uthread_cond_signal(cantProduce);
	  }
	}
	uthread_mutex_unlock(mx);
  }
  
  //printf("consumer: done\n");
  return NULL;
}

int main(int argc, char **argv) {

  //printf("0\n");
  // init the thread system
  uthread_init(NUM_PROCESSORS);
  
  mx = uthread_mutex_create();
  cantProduce = uthread_cond_create(mx);
  cantConsume = uthread_cond_create(mx);
  
  //printf("1\n");
  
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
  
  //printf("2\n");

  histogram = malloc(sizeof(int) * (max_items + 1));
  uthread_t *threads = malloc(sizeof(uthread_t) * (num_producers + num_consumers));
  for(int i=0; i < max_items+1; i++) {
    histogram[i] = 0;
  }

  //printf("3\n");
  // start the threads
  for(int i = 0; i < num_producers; i++) {
    threads[i] = uthread_create(producer, NULL);
  }
  //printf("4\n");
  for(int i = num_producers; i < num_producers + num_consumers; i++) {
    threads[i] = uthread_create(consumer, NULL);
  }
  //printf("5\n");
  // wait for threads to complete
  for(int i=0; i < num_producers + num_consumers; i++) {
	//printf("-%d\n", i);
    uthread_join(threads[i], NULL);
  }
  //printf("6\n");
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

  uthread_cond_destroy(cantProduce);
  uthread_cond_destroy(cantConsume);
  uthread_mutex_destroy(mx);

  free(threads);
  free(histogram);
}
