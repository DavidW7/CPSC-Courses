goto:		ld $7, r1				# r1 = size = 4*2
			ld $0, r0				# r0 = random address
			ld $0x2f62696e, r2		
			st r2, (r0)				# (r0) = /bin
			ld $0x2f73680a, r2		
			st r2, 4(r0)			# (r0) = /sh
			sys $2					# system call: exec	