.pos 0x100
				ld $0x0, r0					# r0 = 0
				ld $t, r1					# r1 = address of t
				ld $array, r2				# r2 = address of array
				ld $0x01, r3				# r3 = 1
				ld (r2, r0, 4), r4			# r4 = array[0]
				st r4, (r1, r0, 4)			# t = r4
				ld (r2, r3, 4), r4			# r4 = array[1]
				st r4, (r2, r0, 4)			# array[0] = r4
				ld (r1, r0, 4), r4			# r4 = t
				st r4, (r2, r3, 4)			# array[1] = r4
				halt	
.pos 0x1000
t:				.long 0xffffffff			# t
.pos 0x2000
array:			.long 0xffffffff			# array[0]
				.long 0xffffffff			# array[1]