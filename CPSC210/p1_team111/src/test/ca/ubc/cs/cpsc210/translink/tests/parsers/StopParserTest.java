package ca.ubc.cs.cpsc210.translink.tests.parsers;

import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.StopParser;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * Tests for the StopParser
 */
class StopParserTest {
    @BeforeEach
    void setup() {
        StopManager.getInstance().clearStops();
    }

    @Test
    void testStopParserNormal() {
        StopParser p = new StopParser("stops.json");

        try {
            p.parse();
        } catch (IOException e) {
            fail("exception not expected");
        } catch (JSONException e) {
            fail("exception not expected");
        } catch (StopDataMissingException e) {
            fail("exception not expected");
        } finally {
            assertEquals(8524, StopManager.getInstance().getNumStops());

            assertEquals(StopManager.getInstance().getStopWithNumber(50011).getName(),
                    "EB DAVIE ST FS HOWE ST");
            assertEquals(StopManager.getInstance().getStopWithNumber(50013).getLocn(),
                    new LatLon(49.27353, -123.12733));
            assertEquals(StopManager.getInstance().getStopWithNumber(54488).getName(),
                    "NB HIGHLAND BLVD FS MELBOURNE AVE");
            assertEquals(StopManager.getInstance().getStopWithNumber(57282).getLocn(),
                    new LatLon(49.09556, -122.61033));
        }
    }

    @Test
    public void testThrowDataMissingExceptionMissingField() {
        StopParser p = new StopParser("testStopParserMissingField.json");
        try {
            p.parse();
            fail("should have stopData exception thrown");
        } catch (IOException e) {
            fail("exception not expected");
        } catch (JSONException e) {
            fail("exception not expected");
        } catch (StopDataMissingException e) {
            // expected
        } finally {
            assertEquals(2, StopManager.getInstance().getNumStops());
        }
    }

    // TODO: design more tests
}
