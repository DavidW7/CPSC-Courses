package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Bus;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.exception.RouteException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Parser for bus data
public class BusParser {

    /**
     * Parse buses from JSON response produced by TransLink query.  All parsed buses are
     * added to the given stop.  Bus location data that is missing any of the required
     * fields (RouteNo, Latitude, Longitude, Destination, RecordedTime) is silently
     * ignored and not added to stop.  Bus that is on route that does not pass through
     * this stop is silently ignored and not added to stop.
     *
     * @param stop            stop to which parsed buses are to be added
     * @param jsonResponse    the JSON response produced by Translink
     * @throws JSONException  when:
     * <ul>
     *     <li>JSON response does not have expected format (JSON syntax problem)</li>
     *     <li>JSON response is not a JSON array</li>
     * </ul>
     */
    public static void parseBuses(Stop stop, String jsonResponse) throws JSONException {
        JSONArray locations = new JSONArray(jsonResponse);

        for (int index = 0; index < locations.length(); index++) {
            JSONObject location = locations.getJSONObject(index);
            parseLocation(stop, location);
        }
    }

    private static void parseLocation(Stop stop, JSONObject location) throws JSONException {
        if (location.has("RouteNo")
                && location.has("Latitude")
                && location.has("Longitude")
                && location.has("Destination")
                && location.has("RecordedTime")) {
            String routeNumber = location.getString("RouteNo");
            Double lat = location.getDouble("Latitude");
            Double lon = location.getDouble("Longitude");
            String dest = location.getString("Destination");
            String time = location.getString("RecordedTime");

            try {
                Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
                Bus b = new Bus(r, lat, lon, dest, time);
                stop.addBus(b);
            } catch (RouteException e) {
                // silent treatment
            }
        }
    }
}
