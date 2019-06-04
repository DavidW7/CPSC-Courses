package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.BusParser;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for BusParser
class BusParserTest {
    @Test
    void testBusLocationsParserNormal() throws JSONException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearBuses();

        // Add routes 004 and 014 to stop, otherwise buses running on these
        // routes won't be added to stop
        Route n04 = RouteManager.getInstance().getRouteWithNumber("004");
        Route n14 = RouteManager.getInstance().getRouteWithNumber("014");
        s.addRoute(n04);
        s.addRoute(n14);

        String data = "";

        try {
            data = new FileDataProvider("buslocations.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }

        BusParser.parseBuses(s, data);

        assertEquals(4, s.getBuses().size());

        assertEquals(s.getBuses().get(0).getDestination(), "HASTINGS");
        assertEquals(s.getBuses().get(1).getRoute(), n04);
        assertEquals(s.getBuses().get(2).getTime(), "12:52:22 pm");
        assertEquals(s.getBuses().get(3).getLatLon(), new LatLon(49.284617, -123.114900));
    }

    // TODO: design more tests

    @Test
    public void testThrowRouteException() {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearBuses();

        // Add routes 004 and 014 to stop, otherwise buses running on these
        // routes won't be added to stop
        Route n04 = RouteManager.getInstance().getRouteWithNumber("004");
        Route n14 = RouteManager.getInstance().getRouteWithNumber("014");
        // s.addRoute(n04); // n04 not added to stop
        s.addRoute(n14);

        String data = "";

        try {
            data = new FileDataProvider("buslocations.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the bus locations data");
        }
        try {
            BusParser.parseBuses(s, data);
        } catch (JSONException e) {
            // do nothing
        }

        assertEquals(2, s.getBuses().size());
    }
}
