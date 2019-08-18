.pos 0x100
doit:			#set up 
				ld $a, r0					# r0 = address of a
				ld $p, r1 					# r1 = address of p
				ld $b, r2 					# r2 = address of b
				#line 8
				ld $0x03, r3				# r3 = 3
				st r3, (r0)					# a = r3
				#line 9 
				st r0, (r1)					# p = address of a
				#line 10
				ld (r1), r3					# r3 = val stored at pointer p, which happens to be address
				ld (r3), r4					# r4 = val stored at address which is the val of p
				dec r4						# r4--
				st r4, (r3)					# val at address of r3 = r4
				#line 12
				st r2, (r1)					# p = address of b[0]
				#line 13
				ld (r1), r3					# r3 = p
				inca r3						# r3 = r3 + 4
				st r3, (r1)					# p = r3
				#line 14
				ld (r0), r4					# r4 = a
				ld (r2, r4, 4), r5			# r5 = b[a]
				ld (r1), r6					# r6 = p
				st r5, (r6, r4, 4)			# p[a] = r5
				#line 15
				ld (r1), r6					# r6 = p
				ld $0x03, r4				# r4 = 3
				ld $0, r5					# r5 = 0
				ld (r2, r5, 4), r7			# r7 = b[0]
				st r7, (r6, r4, 4)			# *(p+3) = r7
				halt
				
				


.pos 0x2000
a:				.long 0xffffffff			# a
.pos 0x3000
p:				.long 0xffffffff			# p
.pos 0x4000
b:				.long 0x00000001			# b[0]
				.long 0x00000002			# b[1]
				.long 0x00000003			# b[2]
				.long 0x00000004			# b[3]
				.long 0x00000005			# b[4]