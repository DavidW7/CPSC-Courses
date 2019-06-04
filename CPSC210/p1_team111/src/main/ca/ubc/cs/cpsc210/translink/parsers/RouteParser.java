package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.parsers.exception.RouteDataMissingException;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Parse route information in JSON format.
 */
public class RouteParser {
    private String filename;

    public RouteParser(String filename) {
        this.filename = filename;
    }

    /**
     * Parse route data from the file and add all route to the route manager.
     *
     */
    public void parse() throws IOException, RouteDataMissingException, JSONException {
        DataProvider dataProvider = new FileDataProvider(filename);

        parseRoutes(dataProvider.dataSourceToString());
    }

    /**
     * Parse route information from JSON response produced by Translink.
     * Stores all routes and route patterns found in the RouteManager.  A pattern that
     * is missing any one of PatternNo, Destination or Direction is silently ignored
     * and not added to the route.
     *
     * @param  jsonResponse    string encoding JSON data to be parsed
     * @throws JSONException   when:
     * <ul>
     *     <li>JSON data does not have expected format (JSON syntax problem)
     *     <li>JSON data is not an array
     * </ul>
     * If a JSONException is thrown, no stops should be added to the stop manager
     *
     * @throws RouteDataMissingException when
     * <ul>
     *  <li>JSON data is missing RouteNo, Name, or Patterns element for any route</li>
     *  <li>The value of the Patterns element is not an array for any route</li>
     * </ul>
     *
     * If a RouteDataMissingException is thrown, all correct routes are first added to the route manager.
     */
    public void parseRoutes(String jsonResponse)
            throws JSONException, RouteDataMissingException {

        JSONArray routes = new JSONArray(jsonResponse);
        boolean caughtMissingRoute = false;

        for (int index = 0; index < routes.length(); index++) {
            JSONObject route = routes.getJSONObject(index);
            try {
                parseRoute(route);
            } catch (RouteDataMissingException e) {
                caughtMissingRoute = true;
            }
        }

        if (caughtMissingRoute) {
            throw new RouteDataMissingException();
        }
    }

    private void parseRoute(JSONObject route) throws JSONException, RouteDataMissingException {
        if (!route.has("RouteNo")
                || !route.has("Name")
                || !route.has("Patterns")) {
            throw new RouteDataMissingException("Route no, name, or pattern is missing information");
        }
        //if (!(route.get("Patterns") instanceof JSONArray)) {
        //    throw new RouteDataMissingException("Pattern in route is not a JSONArray");
        //}

        String routeNumber = route.getString("RouteNo");
        String name = route.getString("Name");
        JSONArray patterns = route.getJSONArray("Patterns");

        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber, name);

        for (int index = 0; index < patterns.length(); index++) {
            JSONObject pattern = patterns.getJSONObject(index);
            parsePattern(r, pattern);
        }
    }

    private void parsePattern(Route r, JSONObject pattern) throws JSONException {
        if (pattern.has("PatternNo")
                && pattern.has("Destination")
                && pattern.has("Direction")) {

            String patternNo = pattern.getString("PatternNo");
            String dest = pattern.getString("Destination");
            String dir = pattern.getString("Direction");

            r.getPattern(patternNo, dest, dir);
        }
    }
}
