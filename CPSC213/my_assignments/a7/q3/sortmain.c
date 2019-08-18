#include <stdio.h>
#include <stdlib.h>

#include "int_element.h"
#include "str_element.h"
#include "element.h"
#include "refcount.h"

/* If the string is numeric, return an int_element. Otherwise return a str_element. */
struct element *parse_string(char *str) {
  char *endp;
  /* strtol returns a pointer to the first non-numeric character in endp.
     If it gets to the end of the string, that character will be the null terminator. */
  int value = strtol(str, &endp, 10);
  if(str[0] != '\0' && endp[0] == '\0') {
    /* String was non-empty and strtol conversion succeeded - integer */
    return (struct element *)int_element_new(value);
  } else {
    return (struct element *)str_element_new(str);
  }
}

int comparing(const void *e1, const void *e2) {
	struct element *ed1 = *(struct element**)e1;
	struct element *ed2 = *(struct element**)e2;
	return ed1->class->compare(ed1, ed2);
}

int main(int argc, char **argv) {
  /* TODO: Read elements into a new array using parse_string */
  
  struct element **ptrs = malloc((argc-1)*sizeof(struct element*)); //didn't use rc_malloc since main is self contained
  
  /* Parse input arguments */
  for (int i=0; i<argc-1; i++) {
    struct element *e = parse_string(argv[i+1]);
    *(ptrs+i) = e;
  }

  /* TODO: Sort elements with qsort */
  
  typedef int (*comparingptr)(const void*, const void*);
  comparingptr comptr = comparing;
  qsort(ptrs, argc-1, sizeof(struct element*), comptr); 
  printf("Sorted: ");
  
  /* TODO: Print elements, separated by a space */
  for (int i=0; i<argc-1; i++) {
    struct element *e = *(ptrs+i);
	e->class->print(e);
	printf(" ");
  }
  printf("\n");
  
  for (int i=0; i<argc-1; i++) {
    struct element *g = *(ptrs+i);
	rc_free_ref(g);
  }
  free(ptrs);
}