package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.Arrival;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.ArrivalsParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.ArrivalsDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Test the ArrivalsParser
 */
class ArrivalsParserTest {
    @Test
    void testArrivalsParserNormal() throws JSONException, ArrivalsDataMissingException {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("arrivals43.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }

        ArrivalsParser.parseArrivals(s, data);

        int count = 0;
        for (Arrival a : s) {
            assertTrue(a.getTimeToStopInMins() <= 120);
            count++;
        }
        assertEquals(6, count);
    }

    @Test
    public void testThrowDataMissingExceptionMissingField() {
        Stop s = StopManager.getInstance().getStopWithNumber(51479);
        s.clearArrivals();
        String data = "";
        try {
            data = new FileDataProvider("testArrivalParserMissingField.json").dataSourceToString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't read the arrivals data");
        }

        try {
            ArrivalsParser.parseArrivals(s, data);
            fail("should have routeData exception thrown");
        } catch (JSONException e) {
            fail("exception not expected");
        } catch (ArrivalsDataMissingException e) {
            // expected
        } finally {
            int count = 0;
            for (Arrival a : s) {
                assertTrue(a.getTimeToStopInMins() <= 120);
                count++;
            }
            assertEquals(0, count);
        }
    }
}
