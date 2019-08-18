#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <sys/errno.h>
#include <assert.h>
#include "uthread.h"
#include "queue.h"
#include "disk.h"

queue_t pending_read_queue;
uthread_t main_thread;

void interrupt_service_routine() {
	void* val;
	void* count;
	void(*callback)(void*, void*);
	queue_dequeue(pending_read_queue, &val, &count, &callback);
	callback(val, count);
}

void handleOtherReads(void *resultv, void *countv) {
	int *resulti = (int*)resultv;
	int *counti = (int*)countv;
	*counti = *counti - 1;
	if (*counti > 0) {
		disk_schedule_read(resulti, *resulti);
		//printf("other read value: %d count: %d \n", *resulti, *counti);
		queue_enqueue(pending_read_queue, resultv, countv, handleOtherReads);
	} else {
		//printf("final value: %d count: %d \n", *resulti, *counti);
		uthread_unblock(main_thread);
	}
}

void handleFirstRead(void *resultv, void *countv) {
	int *resulti = (int*)resultv;
	int *counti = (int*)countv;
	disk_schedule_read(resulti, *resulti);
	*counti = *resulti;
	//printf("initial read value: %d count: %d \n", *resulti, *counti);
	if (*resulti > 0) {
		queue_enqueue(pending_read_queue, resultv, countv, handleOtherReads);
	}
	else {
		uthread_unblock(main_thread);
	}
}

int main(int argc, char **argv) {
  // Command Line Arguments
  static char* usage = "usage: treasureHunt starting_block_number";
  int starting_block_number;
  char *endptr;
  if (argc == 2)
    starting_block_number = strtol (argv [1], &endptr, 10);
  if (argc != 2 || *endptr != 0) {
    printf ("argument error - %s \n", usage);
    return EXIT_FAILURE;
  }

  // Initialize
  uthread_init (1);
  disk_start (interrupt_service_routine);
  pending_read_queue = queue_create();

  main_thread = uthread_self();

  // Start the Hunt
  int *result = malloc(sizeof(int));
  int *count = malloc(sizeof(int));
  disk_schedule_read(result, starting_block_number);
  queue_enqueue(pending_read_queue, result, count, handleFirstRead);
  uthread_block();
  printf("%d\n", *result);
  free(result);
  free(count);
}
