.pos 0x100
doit: 			
				#set up
				ld $i, r0					# r0 = address of i
				ld $j, r1					# r1 = address of j
				ld $p, r2					# r2 = address of p
				ld $a, r3 					# r3 = address of a
				#line 8
				ld 12(r3), r4				# r4 = a[3]
				st r4, (r0)					# i = r4
				#line 9
				ld (r3, r4, 4), r5			# r5 = a[i=r4]
				st r5, (r0)					# i = r5
				#line 10
				st r1, (r2)					# p = r1 = address of j
				#line 11
				ld (r2), r6					# r6 = p = address of j
				ld $0x04, r7				# r7 = 4
				st r7, (r6)					# j = memory at pointer p = r7 = 4
				#line 12
				ld 8(r3), r4 				# r4 = a[2]
				shl $0x02, r4				# r4 = r4 * 4
				add r3, r4					# r4 = r3 + r4 = address of a + a[2] amount of offset
				st r4, (r2)					# p = r4
				#line 13
				ld (r2), r5					# r5 = p
				ld (r5), r6 				# r6 = value at address of value p = memory at pointer p
				ld 16(r3), r7				# r7 = a[4]
				add r6, r7					# r7 = r6 + r7
				st r7, (r5)					# memory at pointer p = r7
				halt	

.pos 0x1000
i:				.long 0xffffffff			# i
.pos 0x2000
j:				.long 0xffffffff			# j
.pos 0x3000
p:				.long 0xffffffff			# p
.pos 0x4000
a:				.long 0x00000001			# a[0]
				.long 0x00000002			# a[1]
				.long 0x00000003			# a[2]
				.long 0x00000004			# a[3]
				.long 0x00000005			# a[4]
				.long 0x00000006			# a[5]
				.long 0x00000007			# a[6]
				.long 0x00000008			# a[7]
				.long 0x00000009			# a[8]
				.long 0x0000000a			# a[9]



