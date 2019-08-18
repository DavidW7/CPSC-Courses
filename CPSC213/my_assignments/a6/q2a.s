.pos 0x100
start:			ld $a, r0				# r0 = &a
				ld (r0), r0				# r0 = a
				ld $b, r1				# r1 = &b
				ld (r1), r1				# r1 = b
				ld $c, r2				# r2 = &c
				ld (r2), r2				# r2 = c, in case c initialized as not zero
				ld $1, r3				# r3 = 1
loop:			beq r1, end				# while (b)
				mov r3, r4				# r4 = 1
				and r1, r4				# r4 = r1 & r4 = b & 1
				beq r4, else			# if ((b & 1) == 0), then else condition
if:				add r0, r2				# r2 = r2 + r0 = c + a
else:			shl $1, r0				# r0 = r0 << 1 = a << 1
				shr $1, r1				# r1 = r1 >> 1 = b >> 1
				br loop					# back to start of loop
end: 			ld $a, r3				# r3 = &a
				st r0, (r3)				# a = r0
				ld $b, r3				# r3 = &b	
				st r1, (r3)				# b = r1
				ld $c, r3				# r3 = &c
				st r2, (r3)				# c = r2
				halt
				
.pos 0x1000
a:				.long 0x0000007B		# 123
b:				.long 0x000001C8		# 456
c:				.long 0x00000000		# 0