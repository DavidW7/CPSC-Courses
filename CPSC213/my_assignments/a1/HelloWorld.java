class HelloWorld 
{
	byte[] yeet = new byte[4];
    public static void main(String args[])
    {
        System.out.println("Hello World");
		int place3 = 0x12ab34cd & 0x000000ff;
		int place2 = (0x12ab34cd >> 8) & 0x000000ff;
		int place1 = (0x12ab34cd >> 16) & 0x000000ff;
		int place0 = (0x12ab34cd >> 24) & 0x000000ff;
		byte[] yeet = {(byte)place0, (byte)place1, (byte)place2, (byte)place3};
		System.out.println(yeet[0]+","+yeet[1]+","+yeet[2]+","+yeet[3]);
    }
	
  
}