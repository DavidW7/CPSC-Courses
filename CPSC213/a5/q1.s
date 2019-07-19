## Code - TODO: Comment and translate to C in q1()
.pos 0x1000

## C statement 1
S1:
ld    $i, r0            # r0 = address of i
ld    (r0), r0          # r0 = value of i = 1
ld    $a, r1            # r1 = address of pointer a
ld    (r1), r1          # r1 = address of struct d0
ld    (r1), r1          # r1 = leftmost value of d0 = address of d1
ld    (r1, r0, 4), r2   # r2 = d1[i] = d1[1] = 11
ld    $v0, r3           # r3 = address of v0
st    r2, (r3)          # v0 = d1[i] = 11

## C statement 2
S2:
ld    $i, r0            # r0 = address of i
ld    (r0), r0          # r0 = value of i = 1
ld    $a, r1            # r1 = address of pointer a
ld    (r1), r1          # r1 = address of struct d0
inca  r1                # r1 = r1 + 4 = address of d0[1]
ld    (r1, r0, 4), r2   # r2 = d0[i+1] = d0[2] = 21 
ld    $v1, r3           # r3 = address of v1
st    r2, (r3)          # v1 = d0[2] = 21

## C statement 3
S3:
ld    $i, r0            # r0 = address of i
ld    (r0), r0          # r0 = value of i = 1
ld    $a, r1            # r1 = address of pointer a
ld    (r1), r1          # r1 = address of struct d0
ld    20(r1), r1        # r1 = d0[5] = address of d2
ld    (r1), r1          # r1 = leftmost value of d2 = address of d3
ld    (r1, r0, 4), r2   # r2 = d3[i] = d3[1] = 31
ld    $v2, r3           # r3 = address of v2
st    r2, (r3)          # v2 = d3[i] = 31

## C statement 4
S4:
ld    $a, r1            # r1 = address of pointer a
ld    (r1), r1          # r1 = address of struct d0
st    r1, 20(r1)        # d0[5] = address of d0 (replace d2)

## C statement 5
S5:
ld    $i, r0            # r0 = address of i
ld    (r0), r0          # r0 = value of i = 1
ld    $a, r1            # r1 = address of pointer a
ld    (r1), r1          # r1 = address of struct d0
ld    20(r1), r1        # r1 = d0[5] = address of d0
inca  r1                # r1 = r1 + 4 = address of d0[1]
ld    (r1, r0, 4), r2   # r2 = d0[i+1] = d0[2] = 21 
ld    $v3, r3           # r3 = address of v3
st    r2, (r3)          # v3 = d0[2] = 21


halt


## Globals
.pos 0x2000
i:  .long 1
v0: .long 0
v1: .long 0
v2: .long 0
v3: .long 0
a:  .long d0


## Heap (these labels represent dynamic values and are thus not available to code)
.pos 0x3000
d0: .long d1
    .long 20
    .long 21
    .long 22
    .long 23
    .long d2
d2: .long d3
    .long 40
    .long 41
    .long 42
    .long 43
    .long 0
d1: .long 10
    .long 11
    .long 12
    .long 13
d3: .long 30
    .long 31
    .long 32
    .long 33
