package arch.sm213.machine.student;

import machine.AbstractMainMemory;


/**
 * Main Memory of Simple CPU.
 *
 * Provides an abstraction of main memory (DRAM).
 */

public class MainMemory extends AbstractMainMemory {
  private byte [] mem;
  
  /**
   * Allocate memory.
   * @param byteCapacity size of memory in bytes.
   */
  public MainMemory (int byteCapacity) {
    mem = new byte [byteCapacity];
  }
  
  /**
   * Determine whether an address is aligned to specified length.
   * @param address memory address.
   * @param length byte length.
   * @return true iff address is aligned to length.
   */
  @Override protected boolean isAccessAligned (int address, int length) {
    return (address >= 0 & length > 0 & ((address % length) == 0));
  }
  
  /**
   * Convert an sequence of four bytes into a Big Endian integer.
   * @param byteAtAddrPlus0 value of byte with lowest memory address (base address).
   * @param byteAtAddrPlus1 value of byte at base address plus 1.
   * @param byteAtAddrPlus2 value of byte at base address plus 2.
   * @param byteAtAddrPlus3 value of byte at base address plus 3 (highest memory address).
   * @return Big Endian integer formed by these four bytes.
   */
  @Override public int bytesToInteger (byte byteAtAddrPlus0, byte byteAtAddrPlus1, byte byteAtAddrPlus2, byte byteAtAddrPlus3) {
    int at0 = (byteAtAddrPlus0 << 24) & 0xff000000;
    int at1 = (byteAtAddrPlus1 << 16) & 0x00ff0000;
    int at2 = (byteAtAddrPlus2 << 8) & 0x0000ff00;
    int at3 = byteAtAddrPlus3 & 0x000000ff;
    return at0 + at1 + at2 + at3;
  }
  
  /**
   * Convert a Big Endian integer into an array of 4 bytes organized by memory address.
   * @param  i an Big Endian integer.
   * @return an array of byte where [0] is value of low-address byte of the number etc.
   */
  @Override public byte[] integerToBytes (int i) {
	int place3 = i & 0x000000ff;
	//System.out.println(place3 + " " + (byte)place3);
	int place2 = (i >> 8) & 0x000000ff;
	//System.out.println(place2 + " " + (byte)place2);
    int place1 = (i >> 16) & 0x000000ff;
    //System.out.println(place1 + " " + (byte)place1);
    int place0 = (i >> 24) & 0x000000ff;
    //System.out.println(place0 + " " + (byte)place0);
	byte[] out = {(byte)place0, (byte)place1, (byte)place2, (byte)place3};
	return out;
  }
  
  /**
   * Fetch a sequence of bytes from memory.
   * @param address address of the first byte to fetch.
   * @param length  number of bytes to fetch.
   * @throws InvalidAddressException  if any address in the range address to address+length-1 is invalid.
   * @return an array of byte where [0] is memory value at address, [1] is memory value at address+1 etc.
   */
  @Override protected byte[] get (int address, int length) throws InvalidAddressException {
    if (address+length-1 > mem.length-1 || address < 0) { //possible error
    	throw new InvalidAddressException();
    }
	
	byte[] out = new byte[length];
	for (int i=0; i<length; i++) {
		out[i] = mem[address+i];
	}
    return out;
  }
  
  /**
   * Store a sequence of bytes into memory.
   * @param  address                  address of the first byte in memory to recieve the specified value.
   * @param  value                    an array of byte values to store in memory at the specified address.
   * @throws InvalidAddressException  if any address in the range address to address+value.length-1 is invalid.
   */
  @Override protected void set (int address, byte[] value) throws InvalidAddressException {
    if (address+value.length-1 > mem.length || address < 0) { //possible error
    	throw new InvalidAddressException();
    }
    
    for (int i=0; i<value.length; i++) {
    	mem[i+address] = value[i];
    }
  }
  
  /**
   * Determine the size of memory.
   * @return the number of bytes allocated to this memory.
   */
  @Override public int length () {
    return mem.length;
  }
}
