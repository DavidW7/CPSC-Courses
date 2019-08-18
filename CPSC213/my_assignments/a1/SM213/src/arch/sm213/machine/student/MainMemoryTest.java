package arch.sm213.machine.student;

import static org.junit.Assert.*;
import org.junit.jupiter.api.*;

import machine.AbstractMainMemory.InvalidAddressException;


public class MainMemoryTest {
	MainMemory tester = new MainMemory(3);
	
	@Test
	public void isAccessAlignedTest() {
		//Base case, normal memory:
		assertTrue(tester.isAccessAligned(4, 2));
		//Edge case, 1 member in memory:
		assertTrue(tester.isAccessAligned(0, 1));
		//Edge case, last member in memory:
		assertTrue(tester.isAccessAligned(20, 10));
		//Edge case, very large memory:
		assertTrue(tester.isAccessAligned(99999999, 99));
		//Error, address doesn't match length:
		assertFalse(tester.isAccessAligned(6, 12));
		//Error, address larger than length:
		assertFalse(tester.isAccessAligned(6, 4));
		//Assume these 3 cases can't be (even though these 3 cases aren't specified as impossible), 
		// because we're testing a private method, and it's impossible to input negative length and address from other methods
		/*
		//Error, address is negative
		assertFalse(tester.isAccessAligned(-2, 6));
		//Error, length is negative
		assertFalse(tester.isAccessAligned(3, -6));
		//Error, length is zero (memory doesn't exist, can't be aligned)
		assertFalse(tester.isAccessAligned(0, 0));	
		*/
	}
	
	@Test
	public void bytesToIntegerTest() {
		//Base case, random numbers:
		assertEquals(tester.bytesToInteger(
				(byte)0x12, 
				(byte)0xab, 
				(byte)0x34, 
				(byte)0xcd), 0x12ab34cd);
		//Edge case, all zeros:
		assertEquals(tester.bytesToInteger(
				(byte)0x00, 
				(byte)0x00, 
				(byte)0x00, 
				(byte)0x00), 0x00000000);
		//Edge case, all ff:
		assertEquals(tester.bytesToInteger(
				(byte)0xff, 
				(byte)0xff, 
				(byte)0xff, 
				(byte)0xff), 0xffffffff);
		//Edge case, negative and positive number mixed:
		assertEquals(tester.bytesToInteger(
				(byte)0x0f, 
				(byte)0xaf, 
				(byte)0x2f, 
				(byte)0x1e), 0x0faf2f1e);
		//Palindrome:
		assertEquals(tester.bytesToInteger(
				(byte)0xfa, 
				(byte)0x01, 
				(byte)0x10, 
				(byte)0xaf), 0xfa0110af);
	}
	
	@Test
	public void integerToBytesTest() {
		//Base case:
		byte[] out1 = {(byte)0x12, (byte)0xab, (byte)0x34, (byte)0xcd};
		assertArrayEquals(tester.integerToBytes(0x12ab34cd), out1);
		//Edge case, all zeroes:
		byte[] out2 = {(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
		assertArrayEquals(tester.integerToBytes(0x00000000), out2);
		//Edge case, all ff:
		byte[] out3 = {(byte)0xff, (byte)0xff, (byte)0xff, (byte)0xff};
		assertArrayEquals(tester.integerToBytes(0xffffffff), out3);
		//Edge case, negative and positive number mixed:
		byte[] out4 = {(byte)0xfa, (byte)0xfb, (byte)0x0b, (byte)0x00};
		assertArrayEquals(tester.integerToBytes(0xfafb0b00), out4);
		//Palindrome: 
		byte[] out5 = {(byte)0xfa, (byte)0x01, (byte)0x10, (byte)0xaf};
		assertArrayEquals(tester.integerToBytes(0xfa0110af), out5);
	}
	
	@Test
	public void setAndGetTest() {
		byte[] nothing = {0, 0, 0};
		//Assume that array is initialize, or it'll get weird stuff that's beyond current course scope
		//Get from nothing:
		/*
		byte[] out1 = {0, 0};
		try {
			assertEquals(tester.get(0, 2), out1);
		} catch (InvalidAddressException e) {
			assertTrue(false);
		}
		*/
		//Base case:
		byte[] out2 = {1, 2, 3};
		try {
			tester.set(0, out2);
			assertArrayEquals(tester.get(0, 3), out2);
		} catch (InvalidAddressException e) {
			assertTrue(false);
		}
		//Get from first members of set:
		byte[] in3 = {2, 3};
		byte[] out3 = {2};
		try {
			tester.set(0, nothing);
			tester.set(0, in3);
			assertArrayEquals(tester.get(0, 1), out3);
		} catch (InvalidAddressException e) {
			assertTrue(false);
		}
		//Get from last members of set:
		byte[] in4 = {1, 4};
		byte[] out4 = {4};
		try {
			tester.set(0, nothing);
			tester.set(1, in4);
			assertArrayEquals(tester.get(2, 1), out4);
		} catch (InvalidAddressException e) {
			assertTrue(false);
		}
		//Make sure set overrides the right stuff with multiple sets:
		byte[] in7 = {1, 4};
		byte[] in8 = {2, 3};
		byte[] outnothing = {0};
		byte[] out7 = {4};
		byte[] out8 = {2};
		try {
			tester.set(0, nothing);
			assertArrayEquals(tester.get(2, 1), outnothing);
			tester.set(0, in7);
			assertArrayEquals(tester.get(1, 1), out7);
			tester.set(1, in8);
			assertArrayEquals(tester.get(1, 1), out8);
		} catch (InvalidAddressException e) {
			assertTrue(false);
		}
		//Error, set from out of of range address:
		byte[] in5 = {2, 3};
		try {
			tester.set(0, nothing);
			tester.set(3, in5);
		} catch (InvalidAddressException e) {
			assertTrue(true);
			try {
				tester.set(-1, in5);
			} catch (InvalidAddressException d) {
				assertTrue(true);
			}
		}
		//Error, get from out of range of set:
		byte[] out6 = {0};
		try {
			tester.set(0, nothing);
			assertArrayEquals(tester.get(3, 1), out6);
		} catch (InvalidAddressException e) {
			assertTrue(true);
			try {
				assertEquals(tester.get(-1, 1), out6);
			} catch (InvalidAddressException d) {
				assertTrue(true);
			}
		}
		
	}
	
}
