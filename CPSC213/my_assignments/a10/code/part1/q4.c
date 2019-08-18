#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "uthread.h"
#include "uthread_mutex_cond.h"

uthread_t t0, t1, t2;
uthread_mutex_t mx;
uthread_cond_t oneCanGo, twoCanGo;
// You may add your own global variables here.

void randomStall() {
  usleep(random() % 300000);
}

void *p0(void *v) {
  randomStall();
  printf("zero\n");
  return NULL;
}

void *p1(void *v) {
  randomStall();
  printf("one\n");
  return NULL;
}

void *p2(void *v) {
  randomStall();
  printf("two\n");
  return NULL;
}

int main(int argc, char **argv) {
  uthread_init(4);
  mx = uthread_mutex_create();
  oneCanGo = uthread_cond_create(mx);
  twoCanGo = uthread_cond_create(mx);
  t0 = uthread_create(p0, NULL);
  t1 = uthread_create(p1, NULL);
  t2 = uthread_create(p2, NULL);
  randomStall();
  uthread_join(t0, NULL);
  uthread_join(t1, NULL);
  uthread_join(t2, NULL);
  printf("three\n");
  printf("------\n");
  uthread_cond_destroy(oneCanGo);
  uthread_cond_destroy(twoCanGo);
  uthread_mutex_destroy(mx);
}
