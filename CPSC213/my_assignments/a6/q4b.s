.pos 0x100
start:
    ld $sb, r5					# initialize stack pointer
    inca    r5					# r5 = address of word after stack
    gpc $6, r6					# r6 = return address after jump
    j main						# jump to main
    halt

f:
    deca r5						# allocate stack for 1 word
    ld $0, r0					# r0 = 0 = count = output 
    ld 4(r5), r1				# r1 = input = x[count] = a
    ld $0x80000000, r2			# r2 = 0x80000000 = b
f_loop:
    beq r1, f_end				# continue loop if (a != 0)
    mov r1, r3					# r3 = a
    and r2, r3					# r3 = check if most significant bit of a is 1 or 0 
    beq r3, f_if1				# go to f_if1 if (r3 = 0), otherwise count++ beforehand
    inc r0						# count++
f_if1:
    shl $1, r1					# r1 = r1 << 1 = r1 * 2
    br f_loop					# restart f_loop
f_end:
    inca r5						# deallocate stack
    j(r6)						# return

main:
    deca r5						# allocate stack for 1 word
    deca r5						# allocate stack fot 1 more word
    st r6, 4(r5)				# store return address on stack
    ld $8, r4					# r4 = 8 = initialize count
main_loop:			
    beq r4, main_end			# continue loop if (r4 != 0)
    dec r4						# count--
    ld $x, r0					# r0 = &x
    ld (r0,r4,4), r0			# r0 = x[count]
    deca r5						# allocate stack for 1 word
    st r0, (r5)					# store x[count] to stack
    gpc $6, r6					# save return address
    j f							# jump to f with x[count] as input
    inca r5						# deallocate stack
    ld $y, r1					# r1 = &y
    st r0, (r1,r4,4)			# y[count] = return from f
    br main_loop				# restart main_loop
main_end:
    ld 4(r5), r6				# reload return address
    inca r5						# deallocate return address
    inca r5						# deallocate return address
    j (r6)						# return

.pos 0x2000
x:
    .long 1
    .long 2
    .long 3
    .long -1
    .long -2
    .long 0
    .long 184
    .long 340057058

y:
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0

.pos 0x8000
# These are here so you can see (some of) the stack contents.
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
    .long 0
sb: .long 0

