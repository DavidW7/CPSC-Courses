int a;
int b[4];
int c;
int d[8];

void doit() {
  c = b[0] + ((d[a & 7] + b[c & 3])&42) + 1;
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 14) {
    fprintf(stderr, "usage: a b[0] ... b[3] d[0] ... d[7]\n");
    exit(EXIT_FAILURE);
  }
  a = strtol(argv[1], NULL, 0);
  for(int k=0; k<4; k++) {
    b[k] = strtol(argv[k + 2], NULL, 0);
  }
  for(int k=0; k<8; k++) {
    d[k] = strtol(argv[k + 6], NULL, 0);
  }

  doit();

  printf("c=%d\n", c);
}
