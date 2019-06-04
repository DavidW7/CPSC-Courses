package tests;

import model.ListOfClowns;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by norm on 2015-09-17.
 */
public class TestListOfClowns {

    // Check out a list of two clowns that start out both sitting down
    @Test
    public void testTwoClownsSame() {
        ListOfClowns cl = new ListOfClowns();
        cl.addClown(false);
        cl.addClown(false);

        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        // After flipping the first (index 0) clown, both clowns should be standing
        cl.flip(0);
        System.out.println(cl.toString());
        assertTrue(cl.allClownsStanding());
    }

    // Check out a list of two clowns that start out one stranding, one sitting
    @Test
    public void testTwoClownsDifferent() {
        ListOfClowns cl = new ListOfClowns();
        cl.addClown(true);
        cl.addClown(false);

        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        // After flipping the second (index 1) clown, they shouldn't both be standing
        cl.flip(1);
        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());
    }

    // Now try 3 clowns
    // Lists of clowns of any length should work!
    @Test
    public void testThreeClownsSame() {
        ListOfClowns cl = new ListOfClowns();
        cl.addClown(false);
        cl.addClown(false);
        cl.addClown(false);

        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        // Do a bunch of flips that should end up with them all standing
        cl.flip(0);
        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        cl.flip(1);
        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        cl.flip(2);
        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        cl.flip(0);
        System.out.println(cl.toString());
        assertFalse(cl.allClownsStanding());

        cl.flip(2);
        System.out.println(cl.toString());
        assertTrue(cl.allClownsStanding());
    }

}
