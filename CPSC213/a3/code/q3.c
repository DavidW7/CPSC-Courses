// YOU: Allocate these global variables, using these names
int  a;
int* p;
int  b[5];

// YOU: Implement this function in assembly
void doit() {
  a = 3;
  p = &a;
  *p = *p - 1;

  p = &b[0];
  p++;
  p[a] = b[a];
  *(p+3) = b[0];
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 6) {
    fprintf(stderr, "usage: b[0] ... b[4]\n");
    exit(EXIT_FAILURE);
  }
  for (int k=0; k<5; k++)
    b[k] = strtol(argv[k + 1], NULL, 0);

  doit();

  printf("a=%d *p=%d\n", a, *p);
  printf("b={");
  for (int k=0; k<5; k++)
    printf("%d%s", b[k], (k<4) ? ", " : "");
  printf("}\n");
}
