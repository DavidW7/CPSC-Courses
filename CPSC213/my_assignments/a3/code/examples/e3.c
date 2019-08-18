int a[4];
int b[4];
int c[4];
int d;

void doit() {
  d = d + (a[d & 3] & b[(d+1) & 3]) + ~c[(d+2) & 3];
  a[0] = b[1] >> 2;
  b[1] = c[2] << 2;
  c[d & 3] = a[(d+1) & 3];
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 14) {
    fprintf(stderr, "usage: a[0] ... a[3] b[0] ... b[3] c[0] ... c[3] d\n");
    exit(EXIT_FAILURE);
  }
  for(int k=0; k<4; k++) {
    a[k] = strtol(argv[k + 1], NULL, 0);
    b[k] = strtol(argv[k + 5], NULL, 0);
    c[k] = strtol(argv[k + 9], NULL, 0);
  }
  d = strtol(argv[13], NULL, 0);

  doit();

  printf("a={%d, %d, %d, %d}\n", a[0], a[1], a[2], a[3]);
  printf("b={%d, %d, %d, %d}\n", b[0], b[1], b[2], b[3]);
  printf("c={%d, %d, %d, %d}\n", c[0], c[1], c[2], c[3]);
  printf("d=%d\n", d);
}
