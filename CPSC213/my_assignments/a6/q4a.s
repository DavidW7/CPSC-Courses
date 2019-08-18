.pos 0x0
                 ld   $sb, r5				# initialize stack pointer
                 inca r5					# r5 = address of word after stack
                 gpc  $6, r6                # r6 = return address after jump
                 j    0x300             	# jump to 0x300
                 halt                     
.pos 0x100
                 .long 0x00001000         	# int *e pointing to e[10]
.pos 0x200									# bar
                 ld   (r5), r0				# r0 = (r5) = c
                 ld   4(r5), r1				# r1 = 4(r5) = d
                 ld   $0x100, r2          	# r2 = 0x100 
                 ld   (r2), r2				# r2 = 0x01000 = address to f[10]
                 ld   (r2, r1, 4), r3       # r3 = e[d]
                 add  r3, r0              	# r0 = c + e[d]
                 st   r0, (r2, r1, 4)     	# e[d] = r0
                 j    (r6)					# return 
.pos 0x300									# foo
                 ld   $-12, r0				# r0 = -12
                 add  r0, r5              	# move stack pointer back 3 words
                 st   r6, 8(r5)				# 8(r5) = r6 = save return address to free r6
                 ld   $1, r0				# r0 = 1
                 st   r0, (r5)				# (r5) = r0 = a
                 ld   $2, r0				# r0 = 2
                 st   r0, 4(r5)				# 4(r5) = r0 = b
                 ld   $-8, r0				# r0 = -8
                 add  r0, r5                # move stack pointer back 2 words
                 ld   $3, r0				# r0 = 3
                 st   r0, (r5)				# (r5) = r0 = c
                 ld   $4, r0				# r0 = 4
                 st   r0, 4(r5)				# 4(r5) = r0 = d
                 gpc  $6, r6                # store return address to r6  
                 j    0x200 				# jump to 0x200 with c and d as params
                 ld   $8, r0				# r0 = 8
                 add  r0, r5              	# deallocate stack
                 ld   (r5), r1				# r1 = (r5) = a
                 ld   4(r5), r2				# r2 = 4(r5) = b
                 ld   $-8, r0				# r0 = -8
                 add  r0, r5              	# move stack pointer back 2 words
                 st   r1, (r5)				# (r5) = r1 = overwrite c with a
                 st   r2, 4(r5)				# 4(r5) = r2 = overwrite d with b
                 gpc  $6, r6                # store return address
                 j    0x200					# jump to 0x200 with a and b as params
                 ld   $8, r0				# r0 = 8
                 add  r0, r5              	# deallocate stack
                 ld   8(r5), r6				# r6 = 8(r5) = put back return address
                 ld   $12, r0				# r0 = 12
                 add  r0, r5             	# deallocate stack 
                 j    (r6)					# return
.pos 0x1000									# int f[10]
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
