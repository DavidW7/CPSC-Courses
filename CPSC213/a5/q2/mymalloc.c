#include <stdlib.h>
#include <stdio.h>

#include "mymalloc.h"

/* Feel free to change/remove these variables and/or add your own. */



// Base address of the allocated heap.
static char *heap;
// reference for where the head of free blocks is right now
static char *head;
// Size of the complete heap.
static int heapsize;
// Next unallocated location in the heap.
static int top;

//offset for metadata (size and pointer address)
static int offset;

//store int from 0 to ffffff to ptr[0], ptr[1], ptr[2]
void storeIntToHeap(int yee, char *ptr) {
  ptr[0] = (yee & 0xff);
  ptr[1] = (yee>>8) & 0xff;
  ptr[2] = (yee>>16) & 0xff;
  ptr[3] = (yee>>24) & 0xff;
}

//get int of 0 to ffffff from ptr
int getIntFromHeap(char *ptr) {
  int a = ptr[0] & 0x0ff;
  int b = ptr[1] & 0x0ff;
  int c = ptr[2] & 0x0ff;
  int d = ptr[3] & 0x0ff;
  return a + b*0x0100 + c*0x010000 + d*0x01000000;
}

//get size
int getSize(char *_heap) {
  char *yeet = _heap-4;
  return getIntFromHeap(yeet);
}

//get nextValAddress
int getNextValAddress(char *_heap) {
  return getIntFromHeap(_heap);
}

//store size and next free value at metadata
//use 0 size to say it's head 
//use 0 address to say that there's no next val (get new free from top, or attached free block here)
void setSizeAndNextVal(char *_heap, int size, int nextValAddress) {
  char *yeet = _heap-4;
  storeIntToHeap(size, yeet);
  storeIntToHeap(nextValAddress, _heap);

}

/* Initialize your memory manager to manage the given heap. */
void mymalloc_init(char *_heap, int _heapsize) {
  heap = _heap;
  head = _heap;
  heapsize = _heapsize;
  //printf("heapsize: %d \n", heapsize);
  top = 0;

  offset = 8;
  top = top + offset;
  heap = heap + offset;
  head = head + offset;
  setSizeAndNextVal(heap, 0, 0);
  setSizeAndNextVal(head, -1, 0);
  //printf("created block of size: %d, at address: %d", getSize(heap), getNextValAddress(heap));
}


void *findNextFreeToAlloc(char* current, int sizeToAlloc) {
  void *next = &heap[getNextValAddress(current)];
  if (getSize(next) >= sizeToAlloc) {
	//printf("found size: %d \n", getSize(next));
    setSizeAndNextVal(current, getSize(current), getNextValAddress(next));
    setSizeAndNextVal(next, getSize(next), 0);
    return next;
  } else if (getSize(next) == -1) {
	//printf("make new block: %d \n", sizeToAlloc);
	/* Allocate the memory from `top` to `top+size` and return it */
    void *res = &heap[top];
	//setSizeAndNextVal(current, getSize(current), getNextValAddress(current));
	//setSizeAndNextVal(next, getSize(next), top);
	setSizeAndNextVal(res, sizeToAlloc, 0);
    top += sizeToAlloc + offset;
    return res;
  } else {
    findNextFreeToAlloc(next, sizeToAlloc);
  }
}


/* Allocate a block of memory of the given size, or NULL if unable. 
Returns: a pointer aligned to 8 bytes, or NULL if allocation failed. */
void *mymalloc(int size) {
  /* This is a dumb implementation of malloc, adapted slightly from the slides. 

  You will augment this solution to make a real malloc. */

  /* Round `size` up to a multiple of 8 bytes */
  /* TODO: Make room for any extra metadata you want to store */
  size = (size + 7) / 8 * 8;

  /* TODO: Implement and walk a free list before allocating off `top` as a last resort */
  
  if(size < 0 || size + offset > heapsize || heapsize - size < top) {
    /* There is not enough room in the heap - fail */
    return NULL;
  }
  
  //printf("to allocate: %d \n", size);
  if (getSize(head) >= size) {
	//printf("first free block available: %d \n", size);
    void *next = &heap[getNextValAddress(head)];
	void *ret = head;
	head = next;
	return ret;
  } else if (getSize(head) == -1) {
    void *res = &heap[top];
	setSizeAndNextVal(res, size, 0);
    top += size + offset;
	//printf("make new block, new top: %d \n", top);
	return res;
  } else {
	//printf("head size: %x \n", getSize(head));
	//printf("iterate \n");
    return findNextFreeToAlloc(head, size);
  }
  //printf("6");
}

/* Free the given block of memory. */
void myfree(void *ptr) {
  //printf("freed size: %d \n", getSize(ptr));
  int next_address = head - heap;
  setSizeAndNextVal(ptr, getSize(ptr), next_address);
  head = ptr; 
}
