#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "list.h"

element_t parse_string(char *str) {
  char *endp;
  /* strtol returns a pointer to the first non-numeric character in endp.
     If it gets to the end of the string, that character will be the null terminator. */
  int value = strtol(str, &endp, 10);
  if(str[0] != '\0' && endp[0] == '\0') {
    /* String was non-empty and strtol conversion succeeded - integer */
    int *ret = malloc(sizeof(int));
	*ret = value;
	return (element_t)ret;
  } else {
    return (element_t)str;
  }
}
//return either int* or char*

int parse_string_get_bool(char *str) {
  char *endp;
  /* strtol returns a pointer to the first non-numeric character in endp.
     If it gets to the end of the string, that character will be the null terminator. */
  int value = strtol(str, &endp, 10);
  if(str[0] != '\0' && endp[0] == '\0') {
    /* String was non-empty and strtol conversion succeeded - integer */
    return 1;
  } else {
    return 0;
  }
}

void strcutter(element_t *out_list, element_t number, element_t string) {
  char **out = (char**)out_list;
  int *num = (int*)number;
  char *str = (char*)string;
  
  //free(*out);
  //*out = malloc(*num + 1);
  char *outt = realloc(*out, *num+1);
  strncpy(outt, str, *num);
  outt[*num] = '\0';
  *out = outt;
}

void printnewline(element_t e) {
  char *straccu = (char*)e;
  if (straccu[0] != '\0') {
	printf("%s\n", e);
  } else {
	printf("\n", e);
  }
}

void intadder(element_t *accu, element_t accu_val, element_t toAdd) {
  int *accu_i = (int*)accu_val;
  int *toAdd_i = (int*)toAdd;
  int a = *accu_i;
  int b = *toAdd_i;
  int add = a + b;
  *accu_i = add;
}

void stradder(element_t *accu, element_t accu_val, element_t toAdd) {
  char *straccu = (char*)accu_val;
  char *strtoadd = (char*)toAdd;
  
  if (strtoadd[0] != '\0') {
	strcat(straccu, " ");
    strcat(straccu, strtoadd);
  }
}

void getlargestint(element_t *accu, element_t accu_val, element_t toCompare) {
  int *accu_i = (int*)accu_val;
  int *toCompare_i = (int*)toCompare;
  if (*accu_i < *toCompare_i) {
    *accu_i = *toCompare_i;
  }
}

void freeintptr(element_t e) {
	free(e);
}

int main(int argc, char **argv) {
  
  struct list* l_number = list_create();
  struct list* l_string = list_create();

  /* Parse input arguments and add to correct list */
  for (int i=0; i<argc-1; i++) {
    element_t e = parse_string(argv[i+1]);
	if (parse_string_get_bool(argv[i+1])) {
	  list_append_array(l_number, &e, 1);
	} else {
	  list_append_array(l_string, &e, 1);
	}
  }
  
  if (list_len(l_string) != 0) {
    /* print on separate line */
    struct list* l_out = list_create();
    list_map2(strcutter, l_out, l_number, l_string);
    list_foreach(printnewline, l_out);
  
    /* print all in one line */
    /* can't just print all the words with space since last word has no space in the back */
    int *sum = malloc(sizeof(int));
    *sum = 0;
    list_foldl(intadder, (element_t*)&sum, l_number);
    int i = list_len(l_number);
    int sum_stub = *sum;
    //printf("%d\n", sum_stub);
    char* concat = malloc(sum_stub + i);
    //sum of all individual letter + spaces (len of l_num - 1) + null
    *concat = '\0';
    strcpy(concat, (char*)list_get(l_out, 0));
    free(list_get(l_out, 0));
    list_remove(l_out, 0);
    list_foldl(stradder, (element_t*)&concat, l_out);
    printf(concat);
    printf("\n");
	
	/* free */
	free(sum);
    free(concat);
    list_foreach(freeintptr, l_out);
    list_destroy(l_out);
  }
  
  
  
  /* print largest */
  int *large = malloc(sizeof(int));
  *large = 0;
  list_foldl(getlargestint, (element_t*)&large, l_number);
  printf("%d\n", *large);
  
  free(large);
  list_foreach(freeintptr, l_number);
  list_destroy(l_number);
  //list_foreach(freeintptr, l_string);
  list_destroy(l_string);
}