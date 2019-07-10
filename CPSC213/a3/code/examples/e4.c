int a;
int b;
int c[8];
int *d = c;

void doit() {
  c[b] = ((c[a] + 1) >> 15) - 1;
  b = b + 4;
  d[b] = d[a] - 4;
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 11) {
    fprintf(stderr, "usage: a b c[0] ... c[7]\n");
    exit(EXIT_FAILURE);
  }
  a = strtol(argv[1], NULL, 0);
  b = strtol(argv[2], NULL, 0);
  for (int k=0; k<8; k++)
    c[k] = strtol(argv[3 + k], NULL, 0);

  doit();

  printf("a=%d b=%d\n", a, b);
  printf("c={");
  for (int k=0; k<8; k++)
    printf("%d%s", c[k], (k<7) ? ", " : "");
  printf("}\n");
}
