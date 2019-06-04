package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {
    private Route r1;
    private Route r2;
    private Route r3;
    private Stop s1;
    private Stop s2;
    private Stop s3;
    private LatLon l1;
    private LatLon l2;
    private LatLon l3;

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
    }

    @Test
    public void testNumberAndName() {
        assertEquals("1", r1.getNumber());
        assertEquals("", r1.getName());
        r1.setName("First route");
        assertEquals("First route", r1.getName());

        assertEquals("2", r2.getNumber());
        assertEquals("", r2.getName());
        r2.setName("Second route");
        assertEquals("Second route", r2.getName());
    }

    @Test
    public void testEquals() {
        assertFalse(r1.equals(r2));
        Route rTest = new Route("1");
        rTest.setName("Same name");
        r1.setName("Different name");
        r2.setName("Same name");


        assertTrue(r1.equals(rTest));
        assertFalse(r2.equals(rTest));
        assertFalse(r1.equals(null));
    }

    @Test
    public void testToString() {
        assertEquals("Route 1", r1.toString());
        assertEquals("Route 2", r2.toString());
        assertEquals("Route 3", r3.toString());
    }

    @Test
    public void testStops() {
        assertEquals(0, r1.getStops().size());
        assertEquals(0, r2.getStops().size());

        r1.addStop(s1);
        r1.addStop(s2);
        r1.addStop(s3);
        assertTrue(r1.hasStop(s1));
        assertEquals(3, r1.getStops().size());
        assertEquals(0, r2.getStops().size());

        r1.addStop(s1);
        r2.addStop(s2);
        assertTrue(r1.hasStop(s1));
        assertEquals(3, r1.getStops().size());
        assertEquals(1, r2.getStops().size());

        r1.removeStop(s1);
        assertFalse(r1.hasStop(s1));
        assertEquals(2, r1.getStops().size());
        assertEquals(1, r2.getStops().size());

        r1.removeStop(s1);
        r2.removeStop(s1);
        assertFalse(r1.hasStop(s1));
        assertEquals(2, r1.getStops().size());
        assertEquals(1, r2.getStops().size());
        assertEquals(0, r3.getStops().size());

        r1.addStop(s1);
        assertTrue(r1.hasStop(s1));
        StringBuilder testIterator = new StringBuilder();
        for (Stop s : r1) {
            testIterator.append(s.getName());
        }

        assertEquals("Second stopThird stopFirst stop", testIterator.toString());
    }

    @Test
    public void testPattern() {
        RoutePattern rp1 = new RoutePattern("1", "", "", r1);
        RoutePattern rp2 = new RoutePattern("2", "", "", r1);

        r1.addPattern(rp1);
        r1.addPattern(rp2);
        List<RoutePattern> testRP = new ArrayList<>();
        testRP.add(rp1);
        testRP.add(rp2);
        assertEquals(testRP, r1.getPatterns());

        assertEquals(rp1, r1.getPattern("1"));
        assertEquals(rp1, r1.getPattern("1", "different destination", "different direction"));

        assertEquals(r2.getPatterns().size(), 0);
        assertEquals(r2.getPattern("3"), r2.getPatterns().get(0));
        assertEquals(r2.getPattern("4", "fake destination", "fake direction"), r2.getPatterns().get(1));
        assertEquals(r2.getPatterns().get(0).getDestination(), "");
        assertEquals(r2.getPatterns().get(1).getDestination(), "fake destination");
    }
}
