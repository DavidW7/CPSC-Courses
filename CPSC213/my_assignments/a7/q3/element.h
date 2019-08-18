/* This file defines the abstract interface for an element. */
#pragma once

/* Forward struct declaration */
struct element;

struct element_class {
  /* Print this element (without any trailing newline) */
  void (*print)(struct element *);
  /* Compare two elements. int_element should always compare LESS than str_element. */
  int (*compare)(struct element *, struct element *);
};

struct element {
  struct element_class *class;
};
