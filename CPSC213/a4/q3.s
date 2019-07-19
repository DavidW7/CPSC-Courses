.pos 0x1000
code:
					#set up
					ld $i, r0			# r0 = address of i
					ld (r0), r0			# r0 = i
					ld $s, r1			# r1 = address of s
					#v0 = s.x[i]
					ld $v0, r2			# r2 = address of v0
					ld (r1, r0, 4), r3	# r3 = s.x[i]
					st r3, (r2)			# v0 = r3
					#v1 = s.y[i]
					ld $v1, r2			# r2 = address of v1
					ld 8(r1), r3		# r3 = address of s_y
					ld (r3, r0, 4), r4	# r4 = s.y[i]
					st r4, (r2)			# v1 = r4
					#v2 = s.z->x[i]
					ld $v2, r2			# r2 = address of v2
					ld 12(r1), r3		# r3 = address of s_z
					ld (r3, r0, 4), r4	# r4 = s.z->x[0]
					st r4, (r2)			# v2 = r4
					#v3 = s.z->z->y[i]
					ld $v3, r2			# r2 = address of v3
					ld 12(r1), r3		# r3 = address of s_z
					ld 12(r3), r4		# r4 = address of s_z_z
					ld 8(r4), r5		# r5 = address of s_z_z_y
					ld (r5, r0, 4), r6	# r6 = s.z->z->y[i]
					st r6, (r2)			# v3 = r6
					halt
					

.pos 0x2000
static:
i:					.long 0
v0:					.long 0
v1:					.long 0
v2:					.long 0
v3:					.long 0
s:					.long 0				# x[0]
					.long 0				# x[1]
					.long s_y			# y
					.long s_z			# z

.pos 0x3000
heap:
s_y:				.long 0				# s.y[0]
					.long 0 			# s.y[1]
s_z:				.long 0 			# s.z->x[0]
					.long 0 			# s.z->x[1]
					.long 0				# s.z->y
					.long s_z_z			# s.z->z
s_z_z:				.long 0 			# s.z->z->x[0]
					.long 0 			# s.z->z->x[1]
					.long s_z_z_y 		# s.z->z->y
					.long 0 			# s.z->z->z
s_z_z_y:			.long 0 			# s.z->z->y[0]
					.long 0 			# s.z->z->y[1]
