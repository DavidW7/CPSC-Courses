.pos 0x100
# loop to find average
# don't override: r0 = s address, r3 = i
start:			ld $initialStack, r5	# r5 = &initialStack, initialize stack pointer
pt2start:		ld $s, r0				# r0 = &s
				ld (r0), r4				# r0 = s	
				ld $n, r3				# r3 = &n
				ld (r3), r3				# r3 = n
				dec r3					# r3 = n-1 = i
part1:			ld 4(r4), r1			# r1 = s->grade[0]
				ld 8(r4), r2			# r2 = s->grade[1]
				add r2, r1				# r1 = s->grade[0] + s->grade[1]
				ld 12(r4), r2			# r2 = s->grade[2]
				add r2, r1				# r1 = s->grade[0] + s->grade[1] + s->grade[2]
				ld 16(r4), r2			# r2 = s->grade[3]
				add r2, r1				# r1 = sum(s->grade)
				shr $2, r1 				# r1 = sum(s->grade)/4 = mean(s->grade)
				st r1, 20(r4)			# s->average = r1
part2:			beq r3, pt6start_i		# start sort if no more students left
part2Loop:		dec r3					# r3 = i - 1 = current student left 
				inca r0					# r0 = (s + 1)
				ld (r0), r4				# r4 = s_data
				br part1				# find next student average
# end of finding average, reload registers
# don't override: r2 = j, r3 = i 
# loop i
pt6start_i:		ld $n, r3				# r3 = &n
				ld (r3), r3				# r3 = n
				dec r3					# r3 = i = n-1
pt6check_i:		bgt r3, pt6start_j		# if i>0, restart j loop
				br endpt6				# end loop if condition fail
pt6start_j:		ld $1, r2				# r2 = j = 1
pt6check_j:		mov r2, r0				# r0 = j
				not r0					# r0 = ~j
				inc r0 					# r0 = -j
				add r3, r0				# r0 = i - j
				beq r0, pt6body			# go to body of loop if i = j
				bgt r0, pt6body			# go to body of loop if i > j
				br end_j_restart_i		# end j loop
end_j_restart_i:dec r3					# i--
				br pt6check_i			# restart i loop 
pt6body:		mov r2, r4				# r4 = r3 = j
				dec r4					# r4 = j - 1
comparePt4:		ld $s, r0				# r0 = &s
				ld (r0, r2, 4), r1		# r1 = s[j]
				ld (r0, r4, 4), r4		# r4 = s[j-1]
				ld 20(r1), r1			# r1 = s[j]->average
				ld 20(r4), r4 			# r4 = s[j-1]->average
				not r1					# r1 = ~r1
				inc r1 					# r1 = -s[j]->average
				add r4, r1				# r1 = s[j-1]->average - s[j]->average
				bgt r1, swap	 		# if (s[j]->average < s[j-1]->average), swap
				br notSwap				# else condition
swap:			ld $-12, r0				# r0 = -12
				add r0, r5				# allocate stack
				mov r2, r4				# r4 = r3 = j
				dec r4					# r4 = j - 1
				ld $s, r0				# r0 = &s
				st r0, 0(r5)			# 0(r5) = &s = a
				st r4, 4(r5)			# 4(r5) = j - 1 = b
				st r2, 8(r5)			# 8(r5) = j = c
				gpc $6, r6				# set return address
				j swapPt3				# jump to swapPt3
				ld $12, r0				# r0 = 12
				add r0, r5				# restore stack pointer
				br endJloop				# do stuff at end of j loop
notSwap:		br endJloop				# do stuff at end of j loop
endJloop:		inc r2					# j++
				br pt6check_j			# restart j loop
endpt6:			br pt7					# end i loop, array pointer is sorted
# get median number, reload registers
# don't override: 
pt7:			ld $n, r3				# r3 = &n
				ld (r3), r3				# r3 = n
				shr $1, r3				# r3 = r3/2 = middle of n if n is odd
				ld $s, r0				# r0 = &s
				ld (r0, r3, 4), r4		# r0 = s[n/2]	
				ld (r4), r0				# r0 = s[n/2]->sid
				ld $m, r1				# r1 = &m
				st r0, (r1)				# m = r0
				halt

.pos 0x200
# encapsulated swap
swapPt3:		ld $-4, r0 				# r0 = -4
				add r0, r5				# move back 1 int to store return
				st r6, (r5)				# store return address
				ld 4(r5), r0			# r0 = a
				ld 8(r5), r4			# r4 = b
				ld 12(r5), r1			# r3 = c
				ld (r0, r1, 4), r7		# r1 = a[c]
				ld (r0, r4, 4), r6		# r6 = a[b]
				st r, (r0, r4, 4)		# a[b] = r1
				st r6, (r0, r1, 4)		# a[c] = r6
				ld (r5), r6				# reload stack pointer to r6
				ld $4, r0				# r0 = 4
				add r0, r5				# restore stack pointer
				j (r6)					# jump back
				
.pos 0x1000
# heap
n: 				.long 3					# just one student
m: 				.long 0 				# put the answer here
s: 				.long baseS 			# address of the array of students
				.long testoS			# 
				.long testooS			#
baseS: 			.long 1234				# student ID 
 				.long 89 				# grade 0
 				.long 60 				# grade 1
 				.long 78 				# grade 2
 				.long 90 				# grade 3
 				.long 0 				# computed average
testoS:			.long 1233				# student ID
 				.long 60 				# grade 0
 				.long 60 				# grade 1
 				.long 78 				# grade 2
 				.long 90 				# grade 3
 				.long 0 				# computed average
testooS:		.long 1232				# student ID , this guy is median
 				.long 70 				# grade 0
 				.long 60 				# grade 1
 				.long 78 				# grade 2
 				.long 90 				# grade 3
 				.long 0 				# computed average
				
.pos 0x2000
# stack
ye:				.long 0					# stack
yee:			.long 0					# stack
yeee:			.long 0					# stack
yeeee:			.long 0					# stack
initialStack:	.long 0					# yeehaw