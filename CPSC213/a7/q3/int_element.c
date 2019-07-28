#include <stdlib.h>
#include <stdio.h>
#include "int_element.h"
#include "refcount.h"

/* TODO: Implement all public int_element functions, including element interface functions.

You may add your own private functions here too. */

/* Forward reference to a int_element. You get to define the structure. */
struct int_element {
	struct element_class *class;
	int type;
	int i;
};

// print
void printint(struct element *e) {
	struct int_element *ed = (struct int_element*)e;
	printf("%d", ed->i);
}

// compare
int compareint(struct element *e1, struct element *e2) {
	struct int_element *ed1 = (struct int_element*)e1;
	struct int_element *ed2 = (struct int_element*)e2;
	if (ed1->type == 0 && ed2->type == 0) {
		return ed1->i - ed2->i;
	} else if (ed1->type == 1 && ed2->type == 1) {
		//bruh
		return e1->class->compare(e1, e2); //use compare of correct type
	} else if (ed1->type == 0 && ed2->type == 1) {
		return -1;
	} else {
		return 1;
	}
}

void finalizerint(void *p) {
	struct int_element *e = (struct int_element*)p;
	e->class = 0;
}

struct element_class int_class_table = {printint, compareint};

/* Static constructor that creates new integer elements. */
struct int_element *int_element_new(int value) {
	struct int_element *ptr = rc_malloc(sizeof(struct int_element), finalizerint);
	ptr->class = &int_class_table;
	ptr->i = value;
	ptr->type = 0; // int = 0, str = 1
	return ptr;
}
/* Static function that obtains the value held in an int_element. */
int int_element_get_value(struct int_element *e) {
	return e->i;
}
/* Static function that determines whether this is an int_element. */
int is_int_element(struct element *e) {
	struct int_element *ed = (struct int_element*)e;
	if (ed->type == 0) {
		return 1;
	} else {
		return 0;
	}
}
