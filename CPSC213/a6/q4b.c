#include <stdlib.h>
#include <stdio.h>

int x[8] = {1, 2, 3, -1, -2, 0, 184, 340057058};
int y[8] = {0, 0, 0 , 0, 0, 0, 0 , 0};

int f(int a) {
	int aa = a;
	int count = 0;
	int b = 0x80000000;
	while (aa != 0) {
		if ((aa & b) != 0) {
			count++;
		} 
		aa = aa << 1;
	}
	return count;
}

void main() {
	int count = 8;
	while (count != 0) {
		count--;
		y[count] = f(x[count]);
	}
	
	//printing
	for (int i = 0; i < 8; i++) {
		printf("%d\n", x[i]);
	}
	for (int i = 0; i < 8; i++) {
		printf("%d\n", y[i]);
	}
}

