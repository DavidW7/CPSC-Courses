package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoutePatternTest {
    private Route r1;
    private Route r2;
    private LatLon l1;
    private LatLon l2;
    private LatLon l3;
    private List<LatLon> p1;
    private List<LatLon> p2;
    private RoutePattern rp1;
    private RoutePattern rp2;

    @BeforeEach
    public void setup() {
        r1 = new Route("1");
        r2 = new Route("2");
        l1 = new LatLon(1, 2);
        l2 = new LatLon(2, 3);
        l3 = new LatLon(3, 4);
        p1 = new ArrayList<>();
        p1.add(l1);
        p1.add(l2);
        p2 = new ArrayList<>();
        p2.add(l2);
        p2.add(l3);
        rp1 = new RoutePattern("rp1", "dest1", "dir1", r1);
        rp2 = new RoutePattern("rp2", "dest2", "dir2", r2);
    }

    @Test
    public void testPath() {
        assertEquals(0, rp1.getPath().size());
        rp1.setPath(p1);
        assertEquals(p1, rp1.getPath());
        rp2.setPath(p2);
        assertEquals(p2, rp2.getPath());
    }

    @Test
    public void testEquals() {
        RoutePattern rpTest = new RoutePattern("rp1","dest2", "dir2", r2);
        assertTrue(rpTest.equals(rp1));
        assertFalse(rpTest.equals(rp2));
        assertTrue(rp1.equals(rp1));
        assertFalse(rp1.equals(null));
    }

    @Test
    public void testGetName() {
        assertEquals("rp1", rp1.getName());
        assertEquals("rp2", rp2.getName());
    }

    @Test
    public void testDestination() {
        assertEquals("dest1", rp1.getDestination());
        assertEquals("dest2", rp2.getDestination());
        rp1.setDestination("testDestination1");
        rp2.setDestination("testDestination2");
        rp1.setDestination("testDestination3");
        assertEquals("testDestination3", rp1.getDestination());
        assertEquals("testDestination2", rp2.getDestination());
    }

    @Test
    public void testDirection() {
        assertEquals("dir1", rp1.getDirection());
        assertEquals("dir2", rp2.getDirection());
        rp1.setDirection("testDirection1");
        rp2.setDirection("testDirection5");
        rp1.setDirection("testDirection4");
        assertEquals("testDirection4", rp1.getDirection());
        assertEquals("testDirection5", rp2.getDirection());
    }
}
