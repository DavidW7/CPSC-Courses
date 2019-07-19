#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "mymalloc.h"

/* This is the testing harness for mymalloc */

#define MB (1024 * 1024)

struct testfunc;
typedef int (*testfunc_t)(struct testfunc *tf);

struct testfunc {
  const char *name;
  int heapsize;
  testfunc_t func;
  void *heap; // filled in by test routine
};

/* Check a new allocation to see if it's legal, and to see if it overlaps with any existing allocations */
static int check_alloc(struct testfunc *tf, void *ptr, int size, void **ptrs, int *sizes, int nptrs) {
  if(ptr == NULL) {
    fprintf(stderr, "%s: mymalloc returned NULL\n", tf->name);
    return -1;
  }
  if(ptr < tf->heap || ptr + size > tf->heap + tf->heapsize) {
    fprintf(stderr, "%s: mymalloc returned invalid pointer: %p is out of the bounds of the heap [%p, %p]\n",
      tf->name, ptr, tf->heap, tf->heap + tf->heapsize);
    return -1;
  }
  for(int i=0; i<nptrs; i++) {
    if(ptr >= ptrs[i] && ptr < ptrs[i] + sizes[i]) {
      fprintf(stderr, "%s: mymalloc returned an overlapping pointer: %p lies inside existing allocation [%p, %p]\n",
        tf->name, ptr, ptrs[i], ptrs[i] + sizes[i]);
      return -1;
    }
  }
  return 0;
}

#define SIMPLE_NBUCKETS 4
#define SIMPLE_ITERS 16
int test_simple(struct testfunc *tf) {
  int sizes[SIMPLE_NBUCKETS];
  for(int i=0; i<SIMPLE_NBUCKETS; i++) {
    sizes[i] = i * 8;
  }

  void *ptrs[SIMPLE_NBUCKETS] = {0};
  for(int i=0; i<SIMPLE_ITERS; i++) {
    int idx = i % SIMPLE_NBUCKETS;
    if(ptrs[idx]) {
      myfree(ptrs[idx]);
      ptrs[idx] = NULL;
    } else {
      void *ptr = mymalloc(sizes[idx]);
      if(0 != check_alloc(tf, ptr, sizes[idx], ptrs, sizes, SIMPLE_NBUCKETS)) {
        return -1;
      }
      ptrs[idx] = ptr;
    }
  }
  return 0;
}

#define FIXED_NBUCKETS 128
#define FIXED_ITERS 16384
#define FIXED_SIZE 256
int test_fixedsize(struct testfunc *tf) {
  int sizes[FIXED_NBUCKETS];
  for(int i=0; i<FIXED_NBUCKETS; i++) {
    sizes[i] = FIXED_SIZE;
  }

  void *ptrs[FIXED_NBUCKETS] = {0};
  for(int i=0; i<FIXED_ITERS; i++) {
    int idx = i % FIXED_NBUCKETS;
    if(ptrs[idx]) {
      myfree(ptrs[idx]);
      ptrs[idx] = NULL;
    } else {
      void *ptr = mymalloc(sizes[idx]);
      if(0 != check_alloc(tf, ptr, sizes[idx], ptrs, sizes, FIXED_NBUCKETS)) {
        return -1;
      }
      ptrs[idx] = ptr;
    }
  }
  return 0;
}

#define RAND_NBUCKETS 512
#define RAND_ITERS 102400
#define RAND_MINSIZE 64
#define RAND_MAXSIZE 4096
int test_random(struct testfunc *tf) {
  /* Allocate/free a whole bunch of memory at random.
     At any given time, the total usage is bounded above by NBUCKETS * MAXSIZE,
     so a good free-list implementation should never run out of space.
  */
  srand(0x31337);
  int sizes[RAND_NBUCKETS];
  for(int i=0; i<RAND_NBUCKETS; i++) {
    sizes[i] = rand() % (RAND_MAXSIZE - RAND_MINSIZE) + RAND_MINSIZE;
  }

  void *ptrs[RAND_NBUCKETS] = {0};
  for(int i=0; i<RAND_ITERS; i++) {
    int idx = rand() % RAND_NBUCKETS;
    if(ptrs[idx]) {
      myfree(ptrs[idx]);
      ptrs[idx] = NULL;
    } else {
      void *ptr = mymalloc(sizes[idx]);
      if(0 != check_alloc(tf, ptr, sizes[idx], ptrs, sizes, RAND_NBUCKETS)) {
        return -1;
      }
      ptrs[idx] = ptr;
    }
  }
  return 0;
}

static struct testfunc testfuncs[] = {
  { "simple", 1 * MB, test_simple },
  { "fixedsize", 1 * MB, test_fixedsize },
  { "random", 8 * MB, test_random },
  { NULL, 0, NULL },
};

int test(struct testfunc *tf) {
  /* Create heap for mymalloc to use */
  tf->heap = malloc(tf->heapsize);
  mymalloc_init(tf->heap, tf->heapsize);
  int result = tf->func(tf);
  free(tf->heap);
  tf->heap = NULL;

  if(result != 0) {
    fprintf(stderr, "test %s failed\n", tf->name);
  } else {
    printf("test %s passed!\n", tf->name);
  }
  return result;
}

void usage(char *progname) {
  fprintf(stderr, "Usage: %s <test|all>\n", progname);
  fprintf(stderr, "Available tests (all = run all tests sequentially):\n");
  for(struct testfunc *tf = &testfuncs[0]; tf->name; tf++) {
    fprintf(stderr, "  %s\n", tf->name);
  }
  exit(EXIT_FAILURE);
}

int main(int argc, char **argv) {
  if(argc != 2) {
    usage(argv[0]);
  }

  char *testname = argv[1];
  if(0 == strcmp(testname, "all")) {
    int result = 0;
    for(struct testfunc *tf = &testfuncs[0]; tf->name; tf++) {
      if(0 != test(tf)) {
        result = -1;
      }
    }
    if(result == 0) {
      printf("All tests passed!\n");
      exit(EXIT_SUCCESS);
    } else {
      fprintf(stderr, "Some tests failed.\n");
      exit(EXIT_FAILURE);
    }
  }
  for(struct testfunc *tf = &testfuncs[0]; tf->name; tf++) {
    if(strcmp(testname, tf->name) == 0) {
      if(0 == test(tf)) {
        exit(EXIT_SUCCESS);
      } else {
        exit(EXIT_FAILURE);
      }
    }
  }
  usage(argv[0]);
}
