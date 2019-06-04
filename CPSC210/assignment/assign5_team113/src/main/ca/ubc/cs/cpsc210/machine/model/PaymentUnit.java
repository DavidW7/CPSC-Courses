package ca.ubc.cs.cpsc210.machine.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the payment unit in a vending machine.
 */
public class PaymentUnit {
    private int numLoonies;   // number of loonies banked in machine for making change
    private int numQuarters;  // number of quarters banked in machine for making change
    private int numDimes;     // number of dimes banked in machine for making change
    private int numNickels;   // number of nickels banked in machine for making change
    private List<Coin> currentTransaction;   // list of coins inserted into machine during current transaction
    private List<Coin> currentChange; // list of coins making up current change needed to return

    // EFFECTS: constructs a payment unit with no banked coins and no coins inserted into the machine
    // as part of a payment
    public PaymentUnit() {
        numLoonies = 0;
        numQuarters = 0;
        numDimes = 0;
        numNickels = 0;
        currentTransaction = new ArrayList<>();
        currentChange = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: clears all the coins banked in the unit
    public void clearCoinsBanked() {
        numLoonies = 0;
        numQuarters = 0;
        numDimes = 0;
        numNickels = 0;
    }

    // REQUIRES: number > 0
    // MODIFIES: this
    // EFFECTS: adds number coins of type c to the banked coins in the unit
    public void addCoinsToBanked(Coin c, int number) {
        if (c == Coin.LOONIE) {
            numLoonies += number;
        } else if (c == Coin.QUARTER) {
            numQuarters += number;
        } else if (c == Coin.DIME) {
            numDimes += number;
        } else if (c == Coin.NICKEL) {
            numNickels += number;
        }
    }

    // EFFECTS: returns number of coins banked of the given type, or return -1 if coin type invalid
    public int getNumberOfCoinsBankedOfType(Coin c) {
        if (c == Coin.LOONIE) {
            return numLoonies;
        } else if (c == Coin.QUARTER) {
            return numQuarters;
        } else if (c == Coin.DIME) {
            return numDimes;
        } else if (c == Coin.NICKEL) {
            return numNickels;
        }
        return -1;
    }

    // EFFECTS: returns the total value of all coins banked in the unit
    public int getValueOfCoinsBanked() {
        int val = numLoonies*Coin.LOONIE.getValue() + numQuarters*Coin.QUARTER.getValue() + numDimes*Coin.DIME.getValue() + numNickels*Coin.NICKEL.getValue();
        return val;
    }

    // MODIFIES: this
    // EFFECTS: adds coin c to the unit as a part of a transaction
    public void insertCoin(Coin c) {
        currentTransaction.add(c);
    }

    // EFFECTS: returns value of coins inserted for current transaction
    public int getValueOfCoinsInserted() {
        int val = 0;
        for (Coin c : currentTransaction) {
            val += c.getValue();
        }
        return val;
    }

    // MODIFIES: this
    // EFFECTS: coins inserted for current transaction are cleared; list of coins
    // inserted for current transaction is returned in the order in which they were inserted.
    public List<Coin> cancelTransaction() {
        List<Coin> toBeReturned = currentTransaction;
        currentTransaction = new ArrayList<>();
        return toBeReturned;
    }

    // REQUIRES: cost <= total value of coins inserted as part of current transaction
    // MODIFIES: this
    // EFFECTS: adds coins inserted to coins banked in unit and returns list of coins that will be provided as change.
    // Coins of largest possible value are used when determining the change.  Change in full is not guaranteed -
    // will provide only as many coins as are banked in the machine, without going over the amount due.
    public List<Coin> makePurchase(int cost) {
        int changeVal = getValueOfCoinsInserted() - cost;

        for (Coin c : currentTransaction) {
            addCoinsToBanked(c, 1);
        }

        while (numLoonies > 0) {
            if (changeVal / Coin.LOONIE.getValue() > 0) {
                currentChange.add(Coin.LOONIE);
                changeVal -= Coin.LOONIE.getValue();
                numLoonies--;
            } else {
                break;
            }
        }

        while (numQuarters > 0) {
            if (changeVal / Coin.QUARTER.getValue() > 0) {
                currentChange.add(Coin.QUARTER);
                changeVal -= Coin.QUARTER.getValue();
                numQuarters--;
            } else {
                break;
            }
        }

        while (numDimes > 0) {
            if (changeVal / Coin.DIME.getValue() > 0) {
                currentChange.add(Coin.DIME);
                changeVal -= Coin.DIME.getValue();
                numDimes--;
            } else {
                break;
            }
        }

        while (numNickels > 0) {
            if (changeVal / Coin.NICKEL.getValue() > 0) {
                currentChange.add(Coin.NICKEL);
                changeVal -= Coin.NICKEL.getValue();
                numNickels--;
            } else {
                break;
            }
        }

        List<Coin> returnChange = currentChange;
        currentChange = new ArrayList<>();
        currentTransaction = new ArrayList<>();

        return returnChange;
    }


}
