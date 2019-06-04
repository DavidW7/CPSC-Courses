package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.model.exception.StopException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ca.ubc.cs.cpsc210.translink.util.SphericalGeometry.distanceBetween;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Test the StopManager
 */
class StopManagerTest {
    private LatLon l1;
    private LatLon l2;
    private LatLon l3;
    private Stop s1;
    private Stop s2;
    private Stop s3;

    @BeforeEach
    void setup() {
        l1 = new LatLon(49.234251, -123.090373);
        l2 = new LatLon(49.232737, -123.090232);
        l3 = new LatLon(49.228131, -123.090506);

        StopManager.getInstance().clearStops();

        s1 = StopManager.getInstance().getStopWithNumber(1);
        s2 = StopManager.getInstance().getStopWithNumber(2);
        s3 = StopManager.getInstance().getStopWithNumber(3);

        s1.setLocn(l1);
        s2.setLocn(l2);
        s3.setLocn(l3);
    }

    @Test
    void testBasic() {
        Stop s9999 = new Stop(9999, "My house", new LatLon(-49.2, 123.2));
        Stop r = StopManager.getInstance().getStopWithNumber(9999);
        assertEquals(s9999, r);
    }

    @Test
    public void testSelectedStop() {
        assertEquals(3, StopManager.getInstance().getNumStops());
        assertEquals(null, StopManager.getInstance().getSelected());

        try {
            StopManager.getInstance().setSelected(s1);
        } catch (StopException e) {
            fail("did not expect exception");
        }

        assertEquals(s1, StopManager.getInstance().getSelected());

        Stop sFail = new Stop(0, "test", l1);

        try {
            StopManager.getInstance().setSelected(sFail);
            fail("stop not added to stop manager yet");
        } catch (StopException e) {
            //pass, do nothing
        }

        StopManager.getInstance().clearSelectedStop();
        assertEquals(null, StopManager.getInstance().getSelected());
    }

    @Test
    public void testStopSet() {
        assertEquals(3, StopManager.getInstance().getNumStops());

        assertEquals(s1, StopManager.getInstance().getStopWithNumber(1));
        assertEquals(s2, StopManager.getInstance().getStopWithNumber(2));
        assertEquals(s3, StopManager.getInstance().getStopWithNumber(3));

        Stop s4 = StopManager.getInstance().getStopWithNumber(4, "s4", l1);
        assertEquals(s3, StopManager.getInstance().getStopWithNumber(3));
        assertEquals(s4, StopManager.getInstance().getStopWithNumber(4));
        assertEquals(l1, StopManager.getInstance().getStopWithNumber(4).getLocn());

        assertEquals(4, StopManager.getInstance().getNumStops());

        Stop s4New = StopManager.getInstance().getStopWithNumber(4, "s4New", l2);
        assertEquals(4, StopManager.getInstance().getNumStops());
        assertEquals(s4New, StopManager.getInstance().getStopWithNumber(4));
        assertEquals(l2, StopManager.getInstance().getStopWithNumber(4).getLocn());

        s1.setName("me");
        s2.setName("too");
        s3.setName("thanks");
        StringBuilder testIterator = new StringBuilder();
        int testIteratorNum = 0;
        for (Stop s : StopManager.getInstance()) {
            testIterator.append(s.getName());
            testIteratorNum += s.getNumber();
        }

        assertEquals("metoothankss4New", testIterator.toString());
        assertEquals(1+2+3+4, testIteratorNum);

        StopManager.getInstance().clearStops();
        assertEquals(0, StopManager.getInstance().getNumStops());
    }

    @Test
    public void testFindNearestTo() {
        LatLon testLocn = new LatLon(49.232923,-123.091430);
        double testTo1 = distanceBetween(testLocn, l1);
        double testTo2 = distanceBetween(testLocn, l2);
        double testTo3 = distanceBetween(testLocn, l3);

        Stop sShortest;
        if (testTo1 < testTo2) {
            if (testTo1 < testTo3) {
                sShortest = s1;
            } else {
                sShortest = s3;
            }
        } else {
            if (testTo2 < testTo3) {
                sShortest = s2;
            } else {
                sShortest = s3;
            }
        }
        assertEquals(sShortest, StopManager.getInstance().findNearestTo(testLocn));
    }
}
