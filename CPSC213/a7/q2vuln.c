#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>

struct str {
	int len;
	char *msg; 
};

struct str a;
struct str b;
struct str c;

void print(struct str *st){
	write(1, st->msg, st->len); 
}

void main() {
	a.len = 30;
	a.msg = "Welcome! Please enter a name:\n";
	b.len = 11;
	b.msg = "Good luck,\n";
	c.len = 43;
	c.msg = "The secret phrase is \"squeamish ossifrage\"\n";
	
	char d[32];
	struct str *e = &a;
	print(e);
	int size = read(0, d, 256);
	e = &b;
	print(e);
	write(1, d, size);
}