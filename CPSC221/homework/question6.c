#include <stdio.h>

int mocha(int n) {int r = 0;for (int i=0; i<=n; i = i+16)for (int j=0; j<i; j++)r++;return r;}
int tea(int n) {int r = 0;for (int i = 1; i < n*n*n; i = i * 2)r++;return r * r;}

int main()
{
    //int old = tea(0);
    int old = mocha(0);
    for (int i = 1; i < 144; i++) {
        if (old != mocha(i)) {
            printf("%d %d\n", i, mocha(i));
            old = mocha(i);
        }
    }
}