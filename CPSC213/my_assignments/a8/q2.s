.pos 0x0										# foo
                 ld   $0x1028, r5				# inintialize stack pointer, save to r5
                 ld   $0xfffffff4, r0			# r0 = -12
                 add  r0, r5					# allocate stack for 3 variables (12 blocks), for input to bar
                 ld   $0x200, r0				# r0 = 0x200 = &w
                 ld   0x0(r0), r0				# r0 = memory at 0x200 = w
                 st   r0, 0x0(r5)				# 0(r5) = r0
                 ld   $0x204, r0				# r0 = 0x400 = &x
                 ld   0x0(r0), r0				# r0 = memory at 0x204 = x
                 st   r0, 0x4(r5)				# 4(r5) = r0
                 ld   $0x208, r0				# r0 = 0x208 = &y
                 ld   0x0(r0), r0				# r0 = memory at 0x208 = y
                 st   r0, 0x8(r5)				# 8(r5) = r0
                 gpc  $6, r6					# save return address
                 j    0x300						# jump to bar at 0x300
                 ld   $0x20c, r1				# r1 = 0x20c = &z
                 st   r0, 0x0(r1)				# memory at 0x20c = z = output of bar
                 halt
.pos 0x200
                 .long 0x00000000				# w
                 .long 0x00000000				# x
                 .long 0x00000000				# y
                 .long 0x00000000				# z
.pos 0x300										# q2
                 ld   0x0(r5), r0				# r0 = 0(r5) = a
                 ld   0x4(r5), r1				# r1 = 4(r5) = b
                 ld   0x8(r5), r2				# r2 = 8(r5) = c
                 ld   $0xfffffff6, r3			# r3 = -10
                 add  r3, r0					# r0 = aa = 0(r5) - 10
                 mov  r0, r3					# r3 = r0
                 not  r3						# r3 = ~r3
                 inc  r3						# r3 = -r3
                 bgt  r3, L6					# if (10 > 0(r5)), then L6
                 mov  r0, r3					# r3 = 0(R5) - 10
                 ld   $0xfffffff8, r4			# r4 = -8
                 add  r4, r3					# r3 = 0(R5) - 18
                 bgt  r3, L6					# if (18 < 0(r5)), then L6
				 # code chunk above takes care of default case
                 ld   $0x400, r3				# r3 = 0x400, base address of jump table
                 ld   (r3, r0, 4), r3			# r3 = address of switch cases according to aa
                 j    (r3)						# goto switch case
.pos 0x330										
                 add  r1, r2					# r2 = c + b, aa = 0
                 br   L7						# goto L7
                 not  r2						# r2 = ~c, aa = 2
                 inc  r2						# r2 = -c
                 add  r1, r2					# r2 = b - c
                 br   L7						# goto L7
                 not  r2						# r2 = ~c, aa = 4
                 inc  r2						# r2 = -c
                 add  r1, r2					# r2 = b - c
                 bgt  r2, L0					# if (b > c), then goto L0
                 ld   $0x0, r2					# r2 = 0
                 br   L1						# goto L1
L0:              ld   $0x1, r2					# r2 = 1
L1:              br   L7						# goto L7
                 not  r1						# r1 = ~b, aa = 6
                 inc  r1						# r1 = -b
                 add  r2, r1					# r1 = c - b
                 bgt  r1, L2					# if (c > b), then L2
                 ld   $0x0, r2					# r2 = 0
                 br   L3						# goto L3
L2:              ld   $0x1, r2					# r2 = 1
L3:              br   L7						# goto L7
                 not  r2						# r2 = ~c, aa = 8
                 inc  r2						# r2 = -c
                 add  r1, r2					# r2 = b - c
                 beq  r2, L4					# if (b == c), then L4
                 ld   $0x0, r2					# r2 = 0
                 br   L5						# goto L5
L4:              ld   $0x1, r2					# r2 = 1
L5:              br   L7						# goto L7
L6:              ld   $0x0, r2					# r2 = 0, aa = default, 1, 3, 5, 7 
                 br   L7						# goto L7
L7:              mov  r2, r0					# r0 = r2, store output
                 j    0x0(r6)					# return to foo
.pos 0x400
                 .long 0x00000330				# aa = 0
                 .long 0x00000384				# aa = 1
                 .long 0x00000334				# aa = 2
                 .long 0x00000384				# aa = 3
                 .long 0x0000033c				# aa = 4
                 .long 0x00000384				# aa = 5
                 .long 0x00000354				# aa = 6
                 .long 0x00000384				# aa = 7
                 .long 0x0000036c				# aa = 8
.pos 0x1000
                 .long 0x00000000				# this is stack
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
                 .long 0x00000000
