#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include "uthread.h"

uthread_t t0, t1, t2;

void randomStall() {
  usleep(random() % 300000);
}

void *p0(void *v) {
  //randomStall();
  printf("zero\n");
  return NULL;
}

void *p1(void *v) {
  //randomStall();
  printf("one\n");
  return NULL;
}

void *p2(void *v) {
  //randomStall();
  printf("two\n");
  uthread_yield();
  return NULL;
}

int main(int argc, char **argv) {
  uthread_init(4);
  t0 = uthread_create(p0, NULL);
  t1 = uthread_create(p1, NULL);
  t2 = uthread_create(p2, NULL);
  //randomStall();
  uthread_join(t0, NULL);
  uthread_join(t1, NULL);
  uthread_join(t2, NULL);
  printf("three\n");
  printf("------\n");
}
