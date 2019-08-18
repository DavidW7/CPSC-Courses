.pos 0x100
start:			ld $0, r0				# r0 = 0, we'll use r0 for i
				ld $c, r1				# r1 = &c
				ld (r1), r1				# r1 = c, in case c initialized as not zero
				ld $n, r2				# r2 = &n
				ld (r2), r2				# r2 = n
				ld $a, r3				# r3 = &a
				ld $b, r4				# r4 = &b
forCond:		mov r0, r7				# r7 = r0 = i
				not r7
				inc r7					# r7 = -i
				add r2, r7				# r7 = n - i
				bgt r7, continueFor		# (0 < n - i) == (i < n)
endFor:			br end					# end for
continueFor:	ld (r3, r0, 4), r5		# r5 = a[i], i know r5 and r6 are reserved 								
										# but since we're not jumping to another function, i'll take it
				ld (r4, r0, 4), r6		# r6 = b[i]
				not r6
				inc r6					# r6 = -b[i]
				add r5, r6				# r6 = a[i] - b[i]
				bgt r6, if				# (0 < a[i] - b[i]) == (b[i] < a[i])
endIf:			inc r0					# r0++ == i++
				br forCond				# loop back
if:				inc r1					# r1++ == c+= 1
				br endIf				# end of if block
end:			ld $i, r4				# r4 = &i
				st r0, (r4)				# i = r0
				ld $c, r4				# r4 = &c
				st r1, (r4)				# c = r1
				halt
				
.pos 0x1000
i:				.long 0xffffffff				# i = -1
n: 				.long 0x00000005				# n = 5
a:				.long 0x0000000a				# a[0] = 10
				.long 0x00000014				# a[1] = 20
				.long 0x0000001e				# a[2] = 30
				.long 0x00000028				# a[3] = 40
				.long 0x00000032				# a[4] = 50
b:				.long 0x0000000b				# b[0] = 11
				.long 0x00000014				# b[1] = 20
				.long 0x0000001c				# b[2] = 28
				.long 0x0000002c				# b[3] = 44
				.long 0x00000030				# b[4] = 48
c:				.long 0x00000000				# c = 0