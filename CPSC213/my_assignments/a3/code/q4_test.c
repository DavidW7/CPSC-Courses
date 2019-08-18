#include <stdlib.h>
#include <stdio.h>

int main() {
	
  int a[10] = {1,3,6,10,15,21,28,36,45,54};
  int b = &a[7] - &a[2];
  int c = &a[7];
  int d = &a;
  int e = &a[2];
  
  
  
  printf("b=%d c=%d d=%d e=%d\n", b, c, d, e);
}