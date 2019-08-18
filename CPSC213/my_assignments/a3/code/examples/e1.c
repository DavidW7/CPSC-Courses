int a;
int b;
int c[4];

void doit() {
  b = a + 5;
  c[0] = ~(a + c[b]);
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 6) {
    fprintf(stderr, "usage: a c[0] ... c[3]\n");
    exit(EXIT_FAILURE);
  }
  a = strtol(argv[1], NULL, 0);
  for(int k=0; k<4; k++) {
    c[k] = strtol(argv[k + 2], NULL, 0);
  }

  doit();

  printf("b=%d\n", b);
  printf("c[0]=%d\n", c[0]);
}
