#include <stdlib.h>
#include <stdio.h>

int f[10] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
int *e;

void bar(int c, int d) {
	e[d] = c + e[d];
}

void foo() {
	int a = 1;
	int b = 2;
	bar(3, 4);
	bar(a, b);
}

void main() {
	e = f;
	foo();
	
	//printing
	for (int i = 0; i < 10; i++) {
		printf("%d\n", f[i]);
	}
}
