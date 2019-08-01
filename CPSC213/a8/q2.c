#include <stdio.h>

int w;
int x;
int y;
int z;

int q2(int a, int b, int c) {
	int aa = a - 10;
	switch (aa) {
		case 0: return c + b; break;		
		case 2: return b - c; break;
		case 4: return (b > c); break;
		case 6: return (c > b); break;
		case 8: return (b == c); break;
		default: return 0; break;					
	}
}

void foo() {
	z = q2(w, x, y);
}

void main() {
	w = 10; // 10, 12, 14, 16, 18 to trigger non-default
	x = 3; // random
	y = 2; // random
	foo();
	printf("%d", z); // testing
}

