// YOU: Allocate these global variables, using these names
int  a[3];
int  s[5];
int  tos;
int  tmp;

// YOU: Implement this function in assembly
void doit() {
  tmp = 0;
  tos = 0;
  s[tos] = a[0];
  tos++;
  s[tos] = a[1];
  tos++;
  s[tos] = a[2];
  tos++;
  tos--;
  tmp = s[tos];
  tos--;
  tmp = tmp + s[tos];
  tos--;
  tmp = tmp + s[tos];
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 4) {
    fprintf(stderr, "usage: a[0] a[1] a[2]\n");
    exit(EXIT_FAILURE);
  }
  for(int k=0; k<3; k++)
    a[k] = strtol(argv[k + 1], NULL, 0);
  for(int k=0; k<5; k++)
    s[k] = 0;

  doit();

  printf("a={%d, %d, %d}\n", a[0], a[1], a[2]);
  printf("s={%d, %d, %d, %d, %d}\n", s[0], s[1], s[2], s[3], s[4]);
  printf("tos=%d tmp=%d\n", tos, tmp);
}
