package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.parsers.RouteParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Tests for the RouteParser
 */
class RouteParserTest {
    @BeforeEach
    void setup() {
        RouteManager.getInstance().clearRoutes();
    }

    @Test
    void testRouteParserNormal() {
        RouteParser p = new RouteParser("allroutes.json");
        try {
            p.parse();
            assertEquals(229, RouteManager.getInstance().getNumRoutes());

            assertEquals(RouteManager.getInstance().getRouteWithNumber("004").getPatterns().size(), 15);
            assertEquals(RouteManager.getInstance().getRouteWithNumber("321").getName(),
                    "WHITE ROCK/NEWTON/SURREY CTRL STN");
        } catch (IOException e) {
            fail("exception not expected");
        } catch (JSONException e) {
            fail("exception not expected");
        } catch (RouteDataMissingException e) {
            fail("exception not expected");
        }
    }

    // TODO: design more tests

    @Test
    public void testThrowDataMissingExceptionMissingField() {
        RouteParser p = new RouteParser("testRouteParserMissingField.json");
        try {
            p.parse();
            fail("should have routeData exception thrown");
        } catch (IOException e) {
            fail("exception not expected");
        } catch (JSONException e) {
            fail("exception not expected");
        } catch (RouteDataMissingException e) {
            // expected
        } finally {
            assertEquals(2, RouteManager.getInstance().getNumRoutes());
        }
    }


    /*
    @Test
    public void testThrowDataMissingExceptionNotJSONArray() {
        RouteParser p = new RouteParser("testRouteParserNotJSONArray.json");
        try {
            p.parse();
            RouteManager r = RouteManager.getInstance();
            fail("should have routeData exception thrown");
        } catch (IOException e) {
            fail("exception not expected");
        } catch (JSONException e) {
            fail("exception not expected");
        } catch (RouteDataMissingException e) {
            // expected
        } finally {
            assertEquals(2, RouteManager.getInstance().getNumRoutes());
        }
    }
    */

}
