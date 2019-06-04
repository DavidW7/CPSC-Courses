package ca.ubc.cs.cpsc210.servicecard.tests;

import ca.ubc.cs.cpsc210.servicecard.model.FoodServicesCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ubc.cs.cpsc210.servicecard.model.FoodServicesCard.*;
import static org.junit.jupiter.api.Assertions.*;

// Unit tests for FoodServiceCard class
public class FoodServicesCardTest {
    public static final int INITIAL_BALANCE = 10000;
    private FoodServicesCard testCard;

    @BeforeEach
    public void runBefore() {
        testCard = new FoodServicesCard(INITIAL_BALANCE);
    }

    @Test
    public void testConstructor() {
        assertEquals( INITIAL_BALANCE, testCard.getBalance());
        assertEquals(0 , testCard.getRewardPoints());
    }

    @Test
    public void testReload() {
        testCard.reload(1000);
        assertEquals(1000 + INITIAL_BALANCE, testCard.getBalance());
    }

    @Test
    //boundary case to fail the purchase
    public void testMakePurchaseFalseBound() {
        assertFalse(testCard.makePurchase(INITIAL_BALANCE + 1));
    }

    @Test
    //boundary case for just enough money for a purchase, check if all the purchase has been converted to points or not
    public void testMakePurchaseTrueBound() {
        assertTrue(testCard.makePurchase(INITIAL_BALANCE));
        assertEquals(CASH_BACK_REWARD * INITIAL_BALANCE / POINTS_NEEDED_FOR_CASH_BACK, testCard.getBalance());
    }

    @Test
    //more than enough money for purchase
    public void testMakePurchaseTrue() {
        int purchase = INITIAL_BALANCE - 1;
        int leftoverBalance = INITIAL_BALANCE - purchase;
        int totalPoints = purchase * REWARD_POINTS_PER_CENT_CHARGED;
        while (totalPoints >= POINTS_NEEDED_FOR_CASH_BACK) {
            totalPoints -= POINTS_NEEDED_FOR_CASH_BACK;
            leftoverBalance += CASH_BACK_REWARD;
        }
        assertTrue(testCard.makePurchase(INITIAL_BALANCE - 1));
        assertEquals(testCard.getBalance(), leftoverBalance);
        assertEquals(testCard.getRewardPoints(), totalPoints);
    }
}