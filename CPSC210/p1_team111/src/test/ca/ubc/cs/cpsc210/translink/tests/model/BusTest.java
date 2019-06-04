package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BusTest {
    private Route r1;
    private Route r2;
    private Route r3;
    private LatLon l1;
    private LatLon l2;
    private LatLon l3;
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
        b1 = new Bus(r1, 1, 2, "d1", "t1");
        b2 = new Bus(r2, 2, 3, "d2", "t2");
        b3 = new Bus(r3, 3, 4, "d3", "t3");
    }

    @Test
    public void testGetRoute() {
        assertEquals(r1, b1.getRoute());
        assertEquals(r2, b2.getRoute());
        assertEquals(r3, b3.getRoute());
    }

    @Test
    public void testGetLatLon() {
        assertEquals(l1, b1.getLatLon());
        assertEquals(l2, b2.getLatLon());
        assertEquals(l3, b3.getLatLon());
    }

    @Test
    public void testGetDestination() {
        assertEquals("d1", b1.getDestination());
        assertEquals("d2", b2.getDestination());
        assertEquals("d3", b3.getDestination());
    }

    @Test
    public void testGetTime() {
        assertEquals("t1", b1.getTime());
        assertEquals("t2", b2.getTime());
        assertEquals("t3", b3.getTime());
    }

}
