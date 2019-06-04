package ca.ubc.cs.cpsc210.servicecard.model;

// Represents a card used to purchase food services at a university
public class FoodServicesCard {
    // NOTE TO CPSC 210 STUDENTS: normally, the 'final' keyword would be used in the declaration
    // of constants.  We omit it here to allow us to run tests against your code to
    // check that it works when different values are assigned.
    public static int POINTS_NEEDED_FOR_CASH_BACK = 2000;    // points needed to get cash-back reward
    public static int REWARD_POINTS_PER_CENT_CHARGED = 1;    // points earned for each cent charged to card
    public static int CASH_BACK_REWARD = 10;                 // reward in cents

    // add fields to represent changing properties of a food services card

    private int cardBalance;
    private int points;



    // REQUIRES: initialBalance >= 0
    // EFFECTS: constructs food service card with given initial balance in cents and zero reward points
    public FoodServicesCard(int initialBalance) {
        this.cardBalance = initialBalance;
        this.points = 0;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: adds amount cents to balance on card
    public void reload(int amount) {
        this.cardBalance += amount;
    }

    // MODIFIES: this
    // EFFECTS: if there is sufficient balance on the account
    //            - subtracts amount cents from balance
    //            - adds reward points and then
    //                - if eligible, adds cash back reward(s) to account and deducts corresponding reward points
    //            - returns true
    //          otherwise, returns false
    public boolean makePurchase(int amount) {
        if (amount <= cardBalance) {
            this.cardBalance -= amount;
            this.points += amount * REWARD_POINTS_PER_CENT_CHARGED;
            while (points >= POINTS_NEEDED_FOR_CASH_BACK) {
                this.points -= POINTS_NEEDED_FOR_CASH_BACK;
                this.cardBalance += CASH_BACK_REWARD;
            }
            return true;
        } else {
            return false;
        }
    }

    // EFFECTS: returns remaining balance in cents
    public int getBalance() {
        return this.cardBalance;
    }

    // EFFECTS: returns number of unused reward points
    public int getRewardPoints() {
        return this.points;
    }
}
