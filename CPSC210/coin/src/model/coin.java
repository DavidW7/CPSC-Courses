package model;

import java.util.Random;

public class coin {
    // true = heads
    // false = tails
    private boolean coinState;

    public coin() {
        this.coinState = true;
    }
    // Requires: nothing
    // Modifies: this
    // Effects: flips coin
    public void flip() {
        Random rand = new Random();
        int val = rand.nextInt(50);
        if (val % 2 == 1) {
            this.coinState = true;
        } else {
            this.conState = false;
        }
    }

    // Requires: nothing
    // Modifies: nothing
    // Effects: check coin status, true if heads, false if tails

    public boolean checkStatus() {
        return this.coinState;
    }
}
