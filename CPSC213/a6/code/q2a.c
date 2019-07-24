int a = 123;
int b = 456;
int c = 0;

while(b) {
  if(b & 1) {
    c += a;
  }
  a = a << 1;
  b = b >> 1;
}
