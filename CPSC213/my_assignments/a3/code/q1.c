// YOU: Allocate these global variables, using these names
int  i,j;
int* p;
int  a[10];

// YOU: Implement this function in assembly
void doit() {
  i  = a[3];
  i  = a[i];
  p  = &j;
  *p = 4;
  p  = &a[a[2]];
  *p = *p + a[4];
}

// Ignore this function
#include <stdlib.h>
#include <stdio.h>
int main(int argc, char** argv) {
  if(argc != 11) {
    fprintf(stderr, "usage: a[0] ... a[9]\n");
    exit(EXIT_FAILURE);
  }
  for(int k=0; k<10; k++)
    a[k] = strtol(argv[k + 1], NULL, 0);

  doit();

  printf("i=%d j=%d *p=%d\n", i, j, *p);
  printf("a={");
  for (int k=0; k<10; k++)
    printf("%d%s", a[k], (k<9) ? ", " : "");
  printf("}\n");
}
