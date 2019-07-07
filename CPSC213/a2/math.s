.pos 0x100
					ld $a, r0				# r0 = address of a
					ld $b, r1				# r1 = address of b
					ld $0x0, r2				# r2 = 0
					ld (r1, r2, 4), r3		# r3 = b
					ld (r1, r2, 4), r4		# r4 = b
					inc r3					# r3 = r3 + 1
					inca r3					# r3 = r3 + 4
					shr $0x01, r3			# r3 = r3 / 2
					and r3, r4				# r4 = r3 & r4
					shl $0x02, r4			# r4 = r4 << 2
					st r4, (r0, r2, 4)		# a = r4
					halt
.pos 0x1000
a:					.long 0xffffffff		# a
.pos 0x2000
b:					.long 0xffffffff		# b