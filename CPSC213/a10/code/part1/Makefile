UTHREAD = ../uthreads
TARGETS = q1 q2 q3 q4 pc

CFLAGS += -g -std=gnu11 -I$(UTHREAD)
OBJS = $(UTHREAD)/uthread.o $(UTHREAD)/uthread_mutex_cond.o $(UTHREAD)/uthread_sem.o

UNAME = $(shell uname)
ifeq ($(UNAME), Linux)
LDFLAGS += -pthread
endif

all: $(TARGETS)
clean:
	rm -f $(TARGETS); rm -rf $(OBJS) *~ *.dSYM

$(TARGETS): $(OBJS)

.PHONY: all clean
