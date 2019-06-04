package ca.ubc.cs.cpsc210.resourcefinder.model;

// Represents the latitude and longitude of a point on a map
public class GeoPoint {
    private double lat;
    private double lon;

    // EFFECTS: constructs geo-point at given latitude (lat) and longitude (lon)
    public GeoPoint(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLatitude() {
        return lat;
    }

    public double getLongitude() {
        return lon;
    }
}
