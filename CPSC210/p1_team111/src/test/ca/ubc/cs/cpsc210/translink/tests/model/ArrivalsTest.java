package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test Arrivals
 */
class ArrivalsTest {
    private Route route;
    private Arrival arrival;

    private Route r1;
    private Route r2;
    private Route r3;
    private Arrival a1;
    private Arrival a2;
    private Arrival a3;

    @BeforeEach
    void setup() {
        route = RouteManager.getInstance().getRouteWithNumber("43");
        arrival = new Arrival(23, "Home", route);

        r1 = new Route("1");
        r2 = new Route("2");
        r3 = new Route("3");
        a1 = new Arrival(10, "d1", r1);
        a2 = new Arrival(21, "d2", r2);
        a3 = new Arrival(30, "d3", r3);
    }

    @Test
    void testConstructor() {
        assertEquals(23, arrival.getTimeToStopInMins());
        assertEquals(route, arrival.getRoute());
    }

    @Test
    public void testGetRoute() {
        assertEquals(r1, a1.getRoute());
        assertEquals(r2, a2.getRoute());
        assertEquals(r3, a3.getRoute());
    }
    @Test
    public void testGetDestination() {
        assertEquals("d1", a1.getDestination());
        assertEquals("d2", a2.getDestination());
        assertEquals("d3", a3.getDestination());
    }

    @Test
    public void testStatus() {
        assertEquals(" ", a1.getStatus());
        assertEquals(" ", a2.getStatus());
        assertEquals(" ", a3.getStatus());

        a1.setStatus("*");
        a2.setStatus("-");
        a3.setStatus("+");

        assertEquals("*", a1.getStatus());
        assertEquals("-", a2.getStatus());
        assertEquals("+", a3.getStatus());
    }

    @Test
    public void testTimeToStopInMinutes() {
        assertEquals(10, a1.getTimeToStopInMins());
        assertEquals(21, a2.getTimeToStopInMins());
        assertEquals(30, a3.getTimeToStopInMins());

        assertEquals(11, a2.compareTo(a1));
        assertEquals(9, a3.compareTo(a2));
        assertEquals(20, a3.compareTo(a1));
    }
}
