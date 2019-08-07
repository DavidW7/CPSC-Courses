#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdint.h>
#include <sys/errno.h>
#include <assert.h>
#include "queue.h"
#include "disk.h"
#include "uthread.h"

queue_t      pending_read_queue;
volatile unsigned int sum = 0;
volatile unsigned int counter = 0;
uthread_t main_thread;
int num;

void interrupt_service_routine () {
  //call unblocker
  void* val;
  void(*callback)(void*, void*);
  queue_dequeue(pending_read_queue, &val, NULL, &callback);
  callback(val, NULL);
}

void unblocker(void* thread, void* not_used) {
  //unblock read_block thread
  uthread_t* toUnblock = (uthread_t*)thread;
  uthread_unblock(*toUnblock);
}

void *read_block (void *arg) {
  // schedule read, block itself, wait for ISR to unblock, add to sum
  int *blockno = (int*)arg;
  int *result = malloc(sizeof(int));
  disk_schedule_read(result, *blockno);
  uthread_t stub = uthread_self();
  queue_enqueue(pending_read_queue, &stub, NULL, unblocker);
  uthread_block();
  // yield
  sum += *result;
  counter++;
  free(result);
  free(blockno);
  if (!(counter < num))
	uthread_unblock(main_thread);
  return NULL;
}

int main (int argc, char** argv) {

  // Command Line Arguments
  static char* usage = "usage: tRead num_blocks";
  int num_blocks;
  char *endptr;
  if (argc == 2)
    num_blocks = strtol (argv [1], &endptr, 10);
  if (argc != 2 || *endptr != 0) {
    printf ("argument error - %s \n", usage);
    return EXIT_FAILURE;
  }

  // Initialize
  uthread_init (1);
  disk_start (interrupt_service_routine);
  pending_read_queue = queue_create();

  main_thread = uthread_self();
  num = num_blocks;

  // Sum Blocks
  for (int blockno = 0; blockno < num_blocks; blockno++) {
	  int *block = malloc(sizeof(int));
	  *block = blockno;
	  uthread_t yeet = uthread_create(read_block, block);
  }
  uthread_block();
  /* TODO: Clean up as appropriate */
  printf("%d\n", sum);
  return 0;
}

