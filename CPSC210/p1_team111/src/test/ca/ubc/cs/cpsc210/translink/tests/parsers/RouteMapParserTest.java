package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.parsers.RouteMapParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test the parser for route pattern map information
 */
class RouteMapParserTest {
    @BeforeEach
    void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    void testRouteParserNormal() {
        RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        p.parse();
        assertEquals(1232, countNumRoutePatterns());

        Route r1 = RouteManager.getInstance().getRouteWithNumber("100");
        Route r2 = RouteManager.getInstance().getRouteWithNumber("101");
        Route r3 = RouteManager.getInstance().getRouteWithNumber("C43");

        assertEquals(r1.getPattern("W1-71").getPath().get(1).getLatitude(), 49.199405);
        assertEquals(r2.getPattern("EB1B4").getPath().get(2).getLongitude(), -122.950261);
        assertEquals(r3.getPattern("WBSUN").getPath().get(6).getLatitude(), 49.217115);
    }

    @Test
    public void testRouteParserSelected() {
        RouteMapParser p = new RouteMapParser("testRouteMapParser.txt");
        p.parse();
        assertEquals(5, countNumRoutePatterns());
    }
    /**
     * Helper to count number of route patterns
     * @return  total number of route patterns found in all routes in route manager
     */
    private int countNumRoutePatterns() {
        int count = 0;
        for (Route r : RouteManager.getInstance()) {
            for (RoutePattern rp : r.getPatterns()) {
                count ++;
            }
        }
        return count;
    }
}
