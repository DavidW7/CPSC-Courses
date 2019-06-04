package ca.ubc.cs.cpsc210.translink.tests.model;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test the RouteManager
 */
class RouteManagerTest {
    private LatLon l1;
    private LatLon l2;
    private LatLon l3;
    private Route r1;
    private Route r2;
    private Route r3;

    @BeforeEach
    void setup() {
        l1 = new LatLon(49.234251, -123.090373);
        l2 = new LatLon(49.232737, -123.090232);
        l3 = new LatLon(49.228131, -123.090506);

        RouteManager.getInstance().clearRoutes();

        r1 = RouteManager.getInstance().getRouteWithNumber("1");
        r2 = RouteManager.getInstance().getRouteWithNumber("2");
        r3 = RouteManager.getInstance().getRouteWithNumber("3");
    }

    @Test
    void testBasic() {
        Route r43 = new Route("43");
        Route r = RouteManager.getInstance().getRouteWithNumber("43");
        assertEquals(r43, r);
    }

    @Test
    public void testRouteSet() {
        assertEquals(3, RouteManager.getInstance().getNumRoutes());

        assertEquals(r1, RouteManager.getInstance().getRouteWithNumber("1"));
        assertEquals(r2, RouteManager.getInstance().getRouteWithNumber("2"));
        assertEquals(r3, RouteManager.getInstance().getRouteWithNumber("3"));

        Route r4 = RouteManager.getInstance().getRouteWithNumber("4", "r4");
        assertEquals(r3, RouteManager.getInstance().getRouteWithNumber("3"));
        assertEquals(r4, RouteManager.getInstance().getRouteWithNumber("4"));
        assertEquals("r4", RouteManager.getInstance().getRouteWithNumber("4").getName());

        assertEquals(4, RouteManager.getInstance().getNumRoutes());

        Route r4New = RouteManager.getInstance().getRouteWithNumber("4", "r4New");
        assertEquals(4, RouteManager.getInstance().getNumRoutes());
        assertEquals(r4New, RouteManager.getInstance().getRouteWithNumber("4"));
        assertEquals("r4New", RouteManager.getInstance().getRouteWithNumber("4").getName());

        StringBuilder testIterator = new StringBuilder();
        for (Route s : RouteManager.getInstance()) {
            testIterator.append(s.getNumber());
        }

        assertEquals("1234", testIterator.toString());

        RouteManager.getInstance().clearRoutes();
        assertEquals(0, RouteManager.getInstance().getNumRoutes());
    }
}

