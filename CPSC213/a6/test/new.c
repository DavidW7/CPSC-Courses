#include <stdio.h>

int main() {
	int a = 0x80001;
	printf("%x %x", a < 4, a << 4);
	return 0;
}