package ca.ubc.cs.cpsc210.machine.test;


import ca.ubc.cs.cpsc210.machine.model.*;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentUnitTest {

    PaymentUnit test;

    @BeforeEach
    public void setup() {
        test = new PaymentUnit();
    }

    @Test
    public void testBankCoin() {

        assertEquals(0, test.getValueOfCoinsBanked());

        test.addCoinsToBanked(Coin.DIME, 1);
        test.addCoinsToBanked(Coin.QUARTER, 2);
        test.addCoinsToBanked(Coin.NICKEL, 3);
        test.addCoinsToBanked(Coin.LOONIE, 4);
        assertEquals(1, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(2, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(3, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(4, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(Coin.DIME.getValue() + Coin.QUARTER.getValue()*2 + Coin.NICKEL.getValue()*3 + Coin.LOONIE.getValue()*4,
                test.getValueOfCoinsBanked());

        test.addCoinsToBanked(Coin.DIME, 2);
        test.addCoinsToBanked(Coin.QUARTER, 3);
        test.addCoinsToBanked(Coin.NICKEL, 4);
        test.addCoinsToBanked(Coin.LOONIE, 5);
        assertEquals(3, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(5, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(7, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(9, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(Coin.DIME.getValue()*3 + Coin.QUARTER.getValue()*5 + Coin.NICKEL.getValue()*7 + Coin.LOONIE.getValue()*9,
                test.getValueOfCoinsBanked());

        test.clearCoinsBanked();
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(0, test.getValueOfCoinsBanked());

    }

    @Test
    public void testInsertCoin() {
        assertEquals(0, test.getValueOfCoinsInserted());

        test.insertCoin(Coin.LOONIE);
        test.insertCoin(Coin.LOONIE);
        test.insertCoin(Coin.NICKEL);
        test.insertCoin(Coin.LOONIE);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.QUARTER);
        test.insertCoin(Coin.QUARTER);
        assertEquals(Coin.LOONIE.getValue()*3 + Coin.QUARTER.getValue()*2 + Coin.DIME.getValue() + Coin.NICKEL.getValue(),
                test.getValueOfCoinsInserted());

        test.insertCoin(Coin.LOONIE);
        test.insertCoin(Coin.NICKEL);
        assertEquals(Coin.LOONIE.getValue()*4 + Coin.QUARTER.getValue()*2 + Coin.DIME.getValue() + Coin.NICKEL.getValue()*2,
                test.getValueOfCoinsInserted());

        List<Coin> expectedReturn = new ArrayList<>();
        expectedReturn.add(Coin.LOONIE);
        expectedReturn.add(Coin.LOONIE);
        expectedReturn.add(Coin.NICKEL);
        expectedReturn.add(Coin.LOONIE);
        expectedReturn.add(Coin.DIME);
        expectedReturn.add(Coin.QUARTER);
        expectedReturn.add(Coin.QUARTER);
        expectedReturn.add(Coin.LOONIE);
        expectedReturn.add(Coin.NICKEL);

        assertEquals(expectedReturn, test.cancelTransaction());
    }

    @Test
    public void testMakePurchaseFullChange() {
        test.addCoinsToBanked(Coin.DIME, 2);
        test.addCoinsToBanked(Coin.QUARTER, 3);
        test.addCoinsToBanked(Coin.LOONIE, 5);
        test.addCoinsToBanked(Coin.NICKEL, 1);

        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);

        List<Coin> expectedChange = new ArrayList<>();
        expectedChange.add(Coin.QUARTER);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.NICKEL);

        assertEquals(expectedChange, test.makePurchase(10));
        assertEquals(6, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(2, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(5, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
    }

    @Test
    public void testMakePurchaseNotFullChange() {
        test.addCoinsToBanked(Coin.QUARTER, 3);
        test.addCoinsToBanked(Coin.LOONIE, 2);

        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);
        test.insertCoin(Coin.DIME);

        List<Coin> expectedChange = new ArrayList<>();
        expectedChange.add(Coin.QUARTER);
        expectedChange.add(Coin.DIME);

        assertEquals(expectedChange, test.makePurchase(10));
        assertEquals(4, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(2, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));
        assertEquals(2, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
    }

    @Test
    public void testMakePurchaseUseUpMostCoinsInBank() {
        test.addCoinsToBanked(Coin.QUARTER, 1);
        test.addCoinsToBanked(Coin.LOONIE, 2);
        test.addCoinsToBanked(Coin.NICKEL, 1);


        for (int i = 1; i <= 26; i++){
            test.insertCoin(Coin.DIME);
        }

        assertEquals(test.getValueOfCoinsInserted(), 260);

        List<Coin> expectedChange = new ArrayList<>();
        expectedChange.add(Coin.LOONIE);
        expectedChange.add(Coin.LOONIE);
        expectedChange.add(Coin.QUARTER);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.NICKEL);

        assertEquals(expectedChange, test.makePurchase(10));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(24, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));

    }

    @Test
    public void testMake2Purchases() {
        test.addCoinsToBanked(Coin.QUARTER, 1);
        test.addCoinsToBanked(Coin.LOONIE, 2);
        test.addCoinsToBanked(Coin.NICKEL, 1);


        for (int i = 1; i <= 26; i++){
            test.insertCoin(Coin.DIME);
        }

        assertEquals(test.getValueOfCoinsInserted(), 260);

        List<Coin> expectedChange = new ArrayList<>();
        expectedChange.add(Coin.LOONIE);
        expectedChange.add(Coin.LOONIE);
        expectedChange.add(Coin.QUARTER);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.NICKEL);

        assertEquals(expectedChange, test.makePurchase(10));

        test.insertCoin(Coin.LOONIE);
        test.insertCoin(Coin.LOONIE);

        expectedChange = new ArrayList<>();
        expectedChange.add(Coin.LOONIE);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);
        expectedChange.add(Coin.DIME);

        assertEquals(expectedChange, test.makePurchase(20));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.QUARTER));
        assertEquals(1, test.getNumberOfCoinsBankedOfType(Coin.LOONIE));
        assertEquals(16, test.getNumberOfCoinsBankedOfType(Coin.DIME));
        assertEquals(0, test.getNumberOfCoinsBankedOfType(Coin.NICKEL));
    }
}