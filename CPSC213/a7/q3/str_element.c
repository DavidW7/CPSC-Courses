#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "str_element.h"
#include "refcount.h"

/* TODO: Implement all public str_element functions, including element interface functions.

You may add your own private functions here too. */

/* Forward reference to a str_element. You get to define the structure. */
struct str_element {
	struct element_class *class;
	int type;
	char *i;
};

void printstr(struct element *e) {
	struct str_element *ed = (struct str_element*)e;
	char *buff = ed->i;
	printf(buff); // dereferencing pointer to incomplete type ‘struct str_element’
	//printf("yeeT");
}

// compare
int comparestr(struct element *e1, struct element *e2) {
	struct str_element *ed1 = (struct str_element*)e1;
	struct str_element *ed2 = (struct str_element*)e2;
	if (ed1->type == 1 && ed2->type == 1) {
		return strcmp(ed1->i, ed2->i);
	} else if (ed1->type == 0 && ed2->type == 0) {
		//bruh
		return e1->class->compare(e1, e2); //use compare of correct type
	} else if (ed1->type == 0 && ed2->type == 1) {
		return -1;
	} else {
		return 1;
	}
}

void finalizerstr(void *p) {
	struct str_element *e = (struct str_element*)p;
	e->class = 0;
	rc_free_ref(e->i);
	e->i = 0;
}

void finalizerstub(void* e) {
	// do nothing
}

struct element_class str_class_table = {printstr, comparestr};

/* Static constructor that creates new string elements. */
struct str_element *str_element_new(char *value){
	struct str_element *ptr = rc_malloc(sizeof(struct str_element), finalizerstr);
	ptr->class = &str_class_table;
	char *buff = rc_malloc(strlen(value) + 1, finalizerstub); 
	memcpy(buff, value, strlen(value) + 1);
	ptr->i = buff; 
	ptr->type = 1; // int = 0, str = 1
	return ptr;
}

/* Static function that obtains the string from a str_element. The caller should keep_ref it. */
char *str_element_get_value(struct str_element *e) {
	return e->i;
};
/* Static function that determines whether this is a str_element. */
int is_str_element(struct element *e) {
	
	struct str_element *ed = (struct str_element*)e;
	if (ed->type == 1) {
		return 1;
	} else {
		return 0;
	}
	
	//return e->class == &str_class_table;
	/*
	struct str_element *ed = (struct str_element*) e;
	printf("%d", ed->type);
	return ed->type;
	*/
};
