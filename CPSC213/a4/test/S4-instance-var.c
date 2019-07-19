#include <stdio.h>
#include <stdlib.h>

struct S {
 int x[2];
 int *y;
 struct S *z;
};
int i;
int v0, v1, v2, v3;
struct S s;
void foo() {
 v0 = s.x[i];
 v1 = s.y[i];
 v2 = s.z->x[i];
 v3 = s.z->z->y[i];
}

int main() {
	s.y = malloc(2 * sizeof(int));
	s.z = malloc(sizeof(struct S));
	s.z->z = malloc(sizeof(struct S));
	s.z->z->y = malloc(2 * sizeof(int));
	
	i = 0;
	s.x[0] = 1;
	s.x[1] = 2;
	s.y[0] = 7;
	s.y[1] = 8;
	s.z->x[0] = 3;
	s.z->x[1] = 4;
	s.z->z->y[0] = 5;
	s.z->z->y[1] = 6;
	
	
	foo();
	//printf("testing");
	printf("%d \n %d %d %d %d \n", sizeof(struct S), v0, v1, v2, v3);
	return 0;
}