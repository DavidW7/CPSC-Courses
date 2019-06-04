package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parser for routes stored in a compact format in a txt file
 */
public class RouteMapParser {
    private String fileName;

    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }

    private StringBuilder routeNumber;
    private StringBuilder patternName;
    private List<LatLon> elements;
    private StringBuilder la;
    private StringBuilder lo;
    private int readingStatus; // 0 = readingNum, 1 = readingName, 2 = readingLat, 3 = readingLon
    private int firstChars; // 0 = firstChar, 1 = firstDash, 2 = firstSemiCol
    private boolean breakingChar;
    private int numChar;

    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse one route pattern, adding it to the route that is named within it
     * @param str  string representing one route pattern (single line of data from source file)
     *
     * Each line begins with a capital N, which is not part of the route number, followed by the
     * bus route number, a dash, the pattern name, a semicolon, and a series of 0 or more real
     * numbers corresponding to the latitude and longitude (in that order) of a point in the pattern,
     * separated by semicolons. The 'N' that marks the beginning of the line is not part of the bus
     * route number.
     */
    private void parseOnePattern(String str) {
        initializeParams();

        for (char c : str.toCharArray()) {
            if (firstChars == 0 || c == ';') {
                breakingChar = true;
            } else if (c == '-' && firstChars == 1) {
                breakingChar = true;
                firstChars++;
            }

            numChar++;

            if (!breakingChar) {
                notBreakingCharBehaviour(c);
            } else if (breakingChar) {
                breakingCharBehaviourBooleans(c);
                breakingCharBehaviourStatic(str.length());
            }
        }
    }

    private void initializeParams() {
        routeNumber = new StringBuilder();
        patternName = new StringBuilder();
        elements = new ArrayList<>();
        resetLatLon();
        readingStatus = 0; // 0 = readingNum, 1 = readingName, 2 = readingLat, 3 = readingLon
        firstChars = 0; // 0 = firstChar, 1 = firstDash, 2 = firstSemiCol
        breakingChar = true;
        numChar = 0;
    }

    private void notBreakingCharBehaviour(char c) {
        if (readingStatus == 0) {
            routeNumber.append(c);
        } else if (readingStatus == 1) {
            patternName.append(c);
        } else if (readingStatus == 2) {
            la.append(c);
        } else if (readingStatus == 3) {
            lo.append(c);
        }
    }

    private void breakingCharBehaviourBooleans(char c) {
        if (firstChars == 0) {
            firstChars++;
        } else if (c == '-') {
            readingStatus++;
        } else if (c == ';') {
            if (readingStatus == 1) {
                readingStatus++;
                firstChars++;
            } else if (readingStatus == 2) {
                readingStatus++;
            } else if (readingStatus == 3) {
                readingStatus--;
                if (firstChars == 3) {
                    elements.add(new LatLon(Double.parseDouble(la.toString()), Double.parseDouble(lo.toString())));
                    resetLatLon();
                }
            }
        }
    }

    private void breakingCharBehaviourStatic(int len) {
        if (numChar == len) {
            storeRouteMap(routeNumber.toString(), patternName.toString(), elements);
        }
        breakingChar = false;
    }

    private void resetLatLon() {
        la = new StringBuilder();
        lo = new StringBuilder();
    }

    /**
     * Store the parsed pattern into the named route
     * Your parser should call this method to insert each route pattern into the corresponding route object
     * There should be no need to change this method
     *
     * @param routeNumber       the number of the route
     * @param patternName       the name of the pattern
     * @param elements          the coordinate list of the pattern
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}
