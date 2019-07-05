import static java.lang.System.out;

public class Endianness {

  public static int bigEndianValue (Byte[] mem) {
    int j = 0;
    int k = mem.length - 1;
	int l = 0x0ff;
    //out.printf("%x\n", l);
    for (Byte b : mem) {
      int i = (b) << (8*k);
	  int m = l << (8*k);
	  //out.printf("num: %x\n", i);
	  //out.printf("num_and: %x\n", m);
	  i = i & m;
	  //out.printf("num2: %x\n", i);
      j = i+j;
      k = k-1;
      //out.printf("bigeendian: %x\n", j);
	  }
    out.printf("big endian in hex: %x\n", j);
    return j;
  }
  
  public static int littleEndianValue (Byte[] mem) {
    int j = 0;
    int k = 0;
	int l = 0x0ff;
    for (Byte b : mem) {
      int i = (b) << (8*k);
	  int m = l << (8*k);
	  //out.printf("num: %x\n", i);
	  //out.printf("num_and: %x\n", m);
	  i = i & m;
	  //out.printf("num2: %x\n", i);
      j = i+j;
      k++;
      //out.printf("littleendian: %x\n", j);
	  }
    out.printf("little endian in hex: %x\n", j);
    return j;
  }
  
  public static void main (String[] args) {
    Byte mem[] = new Byte[4];
    try {
      for (int i=0; i<4; i++)
        mem [i] = Integer.valueOf (args[i], 16) .byteValue();
    } catch (Exception e) {
      out.printf ("usage: java Endianness n0 n1 n2 n3\n");
      out.printf ("where: n0..n3 are byte values in memory at addresses 0..3 respectively, in hex (no 0x).\n");
      return;
    }
  
    int bi = bigEndianValue    (mem);
    int li = littleEndianValue (mem);
    
    out.printf ("Memory Contents\n");
    out.printf ("  Addr   Value\n");
    for (int i=0; i<4; i++)
      out.printf ("  %3d:   0x%-5x\n", i, mem[i]);
    out.printf ("The big    endian integer value at address 0 is %d\n", bi);
    out.printf ("The little endian integer value at address 0 is %d\n", li);
  }
}