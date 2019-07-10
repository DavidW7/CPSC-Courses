.pos 0x100
doit:			#set up
				ld $a, r0					# r0 = address of a
				ld $s, r1					# r1 = address of s
				ld $tos, r2					# r2 = address of tos
				ld $tmp, r3 				# r3 = address of tmp
				# to not repeatedly load, we'll consistently use:
				# r5 for val of tos
				# r6 for val of tmp
				#line 9
				ld $0x0, r7					# r7 = 0
				st r7, (r3)					# tmp = r7
				ld (r3), r6					# r6 = tmp
				#line 10
				st r7, (r2)					# tos = r7 = 0
				ld (r2), r5					# r5 = tos
				#line 11
				ld (r0), r4					# r4 = a[0]
				st r4, (r1, r5, 4)			# s[tos] = r4
				#line 12
				inc r5						# r5++
				st r5, (r2)					# tos = r5
				#line 13
				ld 4(r0), r4				# r4 = a[1]
				st r4, (r1, r5, 4)			# s[tos] = r4
				#line 14
				inc r5						# r5++
				st r5, (r2)					# tos = r5
				#line 15
				ld 8(r0), r4				# r4 = a[2]
				st r4, (r1, r5, 4)			# s[tos] = r4
				#line 16
				inc r5						# r5++
				st r5, (r2)					# tos = r5
				#line 17
				dec r5						# r5-- 
				st r5, (r2)					# tos = r5
				#line 18
				ld(r1, r5, 4), r4			# r4 = s[tos]
				mov r4, r6					# r6 = r4 
				st r6, (r3)					# tmp = r6
				#line 19
				dec r5						# r5-- 
				st r5, (r2)					# tos = r5
				#line 20
				ld(r1, r5, 4), r4			# r4 = s[tos]
				add r4, r6					# r6 = r4 + r6, where r6 = prev val of tmp
				st r6, (r3)					# tmp = r6
				#line 21
				dec r5						# r5-- 
				st r5, (r2)					# tos = r5
				#line 22
				ld(r1, r5, 4), r4			# r4 = s[tos]
				add r4, r6					# r6 = r4 + r6, where r6 = prev val of tmp
				st r6, (r3)					# tmp = r6
				halt
				

.pos 0x1000
a:				.long 0x00000003			# a[0]
				.long 0x00000005			# a[1]
				.long 0x00000008			# a[2]
.pos 0x2000
s:				.long 0xffffffff			# s[0]
				.long 0xffffffff			# s[1]
				.long 0xffffffff			# s[2]
				.long 0xffffffff			# s[3]
				.long 0xffffffff			# s[4]
.pos 0x3000
tos:			.long 0xffffffff			# tos
.pos 0x4000
tmp:			.long 0xffffffff			# tmp