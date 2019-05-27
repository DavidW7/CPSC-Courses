package ui;

import model.coin;

public class main {
    public static void main() {
        coin myCoin = new coin();
        myCoin.flip();
        myCoin.checkStatus();
    }
}
