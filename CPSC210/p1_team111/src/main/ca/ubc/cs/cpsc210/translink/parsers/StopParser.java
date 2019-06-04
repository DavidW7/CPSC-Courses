package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.StopDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A parser for the data returned by Translink stops query
 */
public class StopParser {
    private String filename;

    public StopParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse stop data from the file and add all stops to stop manager.
     *
     */
    public void parse() throws IOException, StopDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseStops(dataProvider.dataSourceToString());
    }

    /**
     * Parse stop information from JSON response produced by Translink.
     * Stores all stops and routes found in the StopManager and RouteManager.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)</li>
     *     <li>JSON data is not an array</li>
     * </ul>
     * @throws StopDataMissingException when
     * <ul>
     *     <li> JSON data is missing Name, StopNo, Routes or location (Latitude or Longitude) elements for any stop</li>
     * </ul>
     * If a StopDataMissingException is thrown, all stops for which all required data is available
     * are first added to the stop manager.
     */

    public void parseStops(String jsonResponse)
            throws JSONException, StopDataMissingException {

        JSONArray stops = new JSONArray(jsonResponse);
        boolean caughtDataException = false;

        for (int index = 0; index < stops.length(); index++) {
            JSONObject stop = stops.getJSONObject(index);
            try {
                parseStop(stop);
            } catch (StopDataMissingException e) {
                caughtDataException = true;
            }
        }

        if (caughtDataException) {
            throw new StopDataMissingException();
        }
    }

    private void parseStop(JSONObject stop) throws JSONException, StopDataMissingException {
        if (!stop.has("Name")
                || !stop.has("StopNo")
                || !stop.has("Latitude")
                || !stop.has("Longitude")
                || !stop.has("Routes")) {
            throw new StopDataMissingException("Missing stop information");
        }

        String name = stop.getString("Name");
        int stopNo = stop.getInt("StopNo");
        Double lat = stop.getDouble("Latitude");
        Double lon = stop.getDouble("Longitude");
        String routesNotSeparated = stop.getString("Routes");

        List<String> routes = separateRoutes(routesNotSeparated);
        Stop s = StopManager.getInstance().getStopWithNumber(stopNo, name,  new LatLon(lat, lon));
        for (String route : routes) {
            Route r = RouteManager.getInstance().getRouteWithNumber(route);
            s.addRoute(r);
        }
    }

    private List<String> separateRoutes(String routes) {
        List<String> routesList = new ArrayList<>();
        StringBuilder route = new StringBuilder();
        for (char c : routes.toCharArray()) {
            if (c == ',') {
                routesList.add(route.toString());
            } else if (c == ' ') {
                route = new StringBuilder();
            } else {
                route.append(c);
            }
        }
        return routesList;
    }
}
