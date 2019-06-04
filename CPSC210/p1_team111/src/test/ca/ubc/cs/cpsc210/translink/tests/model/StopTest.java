package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.exception.RouteException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StopTest {
    private Route r1;
    private Route r2;
    private Route r3;
    private Stop s1;
    private Stop s2;
    private Stop s3;
    private LatLon l1;
    private LatLon l2;
    private LatLon l3;
    private Arrival a0;
    private Arrival a1;
    private Arrival a2;
    private Arrival a3;
    private Arrival a4;
    private Bus b1;
    private Bus b2;
    private Bus b3;

    @BeforeEach
    public void setup() {
        r1 = new Route("1");
        r2 = new Route("2");
        r3 = new Route("3");
        l1 = new LatLon(1, 2);
        l2 = new LatLon(2, 3);
        l3 = new LatLon(3, 4);
        s1 = new Stop(1, "First stop", l1);
        s2 = new Stop(2, "Second stop", l2);
        s3 = new Stop(3, "Third stop", l3);
        a0 = new Arrival(5, "d0", r1);
        a1 = new Arrival(10, "d1", r1);
        a2 = new Arrival(21, "d2", r1);
        a3 = new Arrival(30, "d3", r1);
        a4 = new Arrival(40, "d4", r1);
        b1 = new Bus(r1, 1, 2, "d1", "t1");
        b2 = new Bus(r2, 2, 3, "d2", "t2");
        b3 = new Bus(r1, 3, 4, "d3", "t3");
    }

    @Test
    public void testEquals() {
        Stop sTest = new Stop(1, "Test", l2);
        assertTrue(sTest.equals(s1));
        assertFalse(sTest.equals(s2));
        assertTrue(s1.equals(s1));
        assertFalse(s1.equals(null));
    }

    @Test
    public void testName() {
        assertEquals("First stop", s1.getName());
        assertEquals("Second stop", s2.getName());
        s1.setName("test name 1");
        s2.setName("test name 2");
        s1.setName("test name 3");
        assertEquals("test name 3", s1.getName());
        assertEquals("test name 2", s2.getName());
    }

    @Test
    public void testLocn() {
        assertEquals(l1, s1.getLocn());
        assertEquals(l2, s2.getLocn());
        s1.setLocn(l2);
        s2.setLocn(l1);
        s1.setLocn(l3);
        assertEquals(l3, s1.getLocn());
        assertEquals(l1, s2.getLocn());
    }

    @Test
    public void testNumber() {
        assertEquals(1, s1.getNumber());
        assertEquals(2, s2.getNumber());
        assertEquals(3, s3.getNumber());
    }

    @Test
    public void testArrival() {
        s1.addArrival(a1);
        s1.addArrival(a3);
        s1.addArrival(a2);
        s1.addArrival(a4);
        s1.addArrival(a0);

        StringBuilder testIterator = new StringBuilder();
        for (Arrival a : s1) {
            testIterator.append(a.getDestination());
        }

        assertEquals("d0d1d2d3d4", testIterator.toString());

        s1.clearArrivals();
        testIterator = new StringBuilder();
        for (Arrival a : s1) {
            testIterator.append(a.getDestination());
        }

        assertEquals("", testIterator.toString());
    }

    @Test
    public void testRoute() {
        assertEquals(0, s1.getRoutes().size());
        assertEquals(0, s2.getRoutes().size());

        s1.addRoute(r1);
        s1.addRoute(r2);
        s1.addRoute(r3);
        assertTrue(s1.onRoute(r1));
        assertEquals(3, s1.getRoutes().size());
        assertEquals(0, s2.getRoutes().size());

        s1.addRoute(r1);
        s2.addRoute(r2);
        assertTrue(s1.onRoute(r1));
        assertEquals(3, s1.getRoutes().size());
        assertEquals(1, s2.getRoutes().size());

        s1.removeRoute(r1);
        assertFalse(s1.onRoute(r1));
        assertEquals(2, s1.getRoutes().size());
        assertEquals(1, s2.getRoutes().size());

        s1.removeRoute(r1);
        s2.removeRoute(r1);
        assertFalse(s1.onRoute(r1));
        assertEquals(2, s1.getRoutes().size());
        assertEquals(1, s2.getRoutes().size());
        assertEquals(0, s3.getRoutes().size());

        s1.addRoute(r1);
        assertTrue(s1.onRoute(r1));
    }

    @Test
    public void testBusExceptionThrown() {
        try {
            s1.addRoute(r1);
            s1.addBus(b2);
            fail("Should not add successfully");
        } catch (RouteException e) {
            //pass, do nothing
        }
    }

    @Test
    public void testBus() {
        try {
            s1.addRoute(r1);
            s2.addRoute(r2);
            s1.addBus(b1);
            s2.addBus(b2);
            s1.addBus(b3);

            assertEquals(2, s1.getBuses().size());
            assertEquals(1, s2.getBuses().size());

            s1.clearBuses();

            assertEquals(0, s1.getBuses().size());
            assertEquals(1, s2.getBuses().size());
        } catch (RouteException e) {
            fail("No exceptions expected");
        }
    }

}
