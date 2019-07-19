#include <stdlib.h>
#include <stdio.h>

// Base address of the allocated heap.
static char *heap;
// Size of the complete heap.
static int heapsize;
// Next unallocated location in the heap.
static int top;

// offset for metadata
static int offset;

int getIntFromHeap(char *ptr) {
  int a = ptr[0] & 0x0ff;
  int b = ptr[1] & 0x0ff;
  int c = ptr[2] & 0x0ff;
  int d = ptr[3] & 0x0ff;
  return a + b*0x0100 + c*0x010000 + d*0x01000000;
}

void test() {
	top = top+2;
}

int main(void) {
	top = 8;
	heap = malloc(sizeof(16));
	offset = 8;
	int yee = 0xf411f213;
	heap[0] = (yee & 0xff);
	heap[1] = (yee>>8) & 0xff;
	heap[2] = (yee>>16) & 0xff;
	heap[3] = (yee>>24) & 0xff;
	char *ptr;
	ptr = heap+4;
	char *ptr2;
	ptr2 = ptr-4;
	int v3 = getIntFromHeap(heap);
	int v2 = ptr - ptr2 + 1;
	int v0 = getIntFromHeap(ptr2);
	int v1 = getIntFromHeap(ptr2+1);
	printf("%x %x %d %x \n", v0, v1, v2, v3);
	printf("%d \n", top);
	test();
	printf("%d \n", top);
}