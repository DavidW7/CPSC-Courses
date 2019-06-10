package ca.ubc.cs.cpsc210.translink.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import ca.ubc.cs.cpsc210.translink.BusesAreUs;
import ca.ubc.cs.cpsc210.translink.R;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.Stop;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static ca.ubc.cs.cpsc210.translink.util.Geometry.rectangleContainsPoint;
import static ca.ubc.cs.cpsc210.translink.util.SphericalGeometry.distanceBetween;

// A plotter for bus stop locations
public class BusStopPlotter extends MapViewOverlay implements Iterable<Stop> {
    /**
     * clusterer
     */
    private RadiusMarkerClusterer stopClusterer;
    /**
     * maps each stop to corresponding marker on map
     */
    private Map<Stop, Marker> stopMarkerMap = new HashMap<>();
    /**
     * marker for stop that is nearest to user (null if no such stop)
     */
    private Marker nearestStnMarker;
    private Activity activity;
    private StopInfoWindow stopInfoWindow;

    private Drawable stopIconDrawable;
    private Drawable closestStopIconDrawable;

    /**
     * Constructor
     *
     * @param activity the application context
     * @param mapView  the map view on which buses are to be plotted
     */
    public BusStopPlotter(Activity activity, MapView mapView) {
        super(activity.getApplicationContext(), mapView);
        this.activity = activity;
        nearestStnMarker = null;
        stopInfoWindow = new StopInfoWindow((StopSelectionListener) activity, mapView);
        newStopClusterer();
        stopIconDrawable = activity.getResources().getDrawable(R.drawable.stop_icon);
        closestStopIconDrawable = activity.getResources().getDrawable(R.drawable.closest_stop_icon);

    }

    public RadiusMarkerClusterer getStopClusterer() {
        return stopClusterer;
    }

    /**
     * Mark all visible stops in stop manager onto map.
     */
    public void markStops(Location currentLoc) {
        Stop nearest = initializeMarkStops();

        for (Stop s : StopManager.getInstance()) {
            if (rectangleContainsPoint(super.northWest, super.southEast, s.getLocn())) {
                Marker m;
                if (stopMarkerMap.containsKey(s)) {
                    m = stopMarkerMap.get(s);
                } else {
                    m = new Marker(super.mapView);
                    initializeMarker(m, s, stopIconDrawable);
                }
                stopClusterer.add(m);
                nearest = findNearest(s, nearest, new LatLon(currentLoc.getLatitude(), currentLoc.getLongitude()));
            }
        }

        updateMarkerOfNearest(nearest);

    }

    private Stop initializeMarkStops() {
        this.newStopClusterer();
        super.updateVisibleArea();

        return new Stop(0, "", new LatLon(0, 0));
    }

    private Stop findNearest(Stop s, Stop nearestSoFar, LatLon currentLocn) {
        Double shortestDist = (double) StopManager.RADIUS;
        Double newDist = distanceBetween(s.getLocn(), currentLocn);
        Double oldDist = distanceBetween(nearestSoFar.getLocn(), currentLocn);

        if (newDist <= shortestDist && newDist < oldDist) {
            return s;
        } else {
            return nearestSoFar;
        }

    }

    private void initializeMarker(Marker m, Stop s, Drawable stopIconDrawable) {
        m.setIcon(stopIconDrawable);
        m.setPosition(new GeoPoint(s.getLocn().getLatitude(), s.getLocn().getLongitude()));
        m.setRelatedObject(s);
        m.setInfoWindow(stopInfoWindow);
        setMarker(s, m);

        StringBuilder title = new StringBuilder();
        title.append(s.getNumber());
        title.append(" ");
        title.append(s.getName());
        for (Route r : s.getRoutes()) {
            title.append("\n");
            title.append(r.getNumber());
        }
        m.setTitle(title.toString());
    }

    /**
     * Create a new stop cluster object used to group stops that are close by to reduce screen clutter
     */
    private void newStopClusterer() {
        Drawable clusterIconD = activity.getResources().getDrawable(R.drawable.stop_cluster);

        stopClusterer = new RadiusMarkerClusterer(activity);
        stopClusterer.getTextPaint().setTextSize(20.0F * BusesAreUs.dpiFactor());
        int zoom = mapView == null ? 16 : mapView.getZoomLevel();
        if (zoom == 0) {
            zoom = MapDisplayFragment.DEFAULT_ZOOM;
        }
        int radius = 1000 / zoom;

        stopClusterer.setRadius(radius);
        Bitmap clusterIcon = ((BitmapDrawable) clusterIconD).getBitmap();
        stopClusterer.setIcon(clusterIcon);
    }

    /**
     * Update marker of nearest stop (called when user's location has changed).  If nearest is null,
     * no stop is marked as the nearest stop.
     *
     * @param nearest stop nearest to user's location (null if no stop within StopManager.RADIUS metres)
     */
    public void updateMarkerOfNearest(Stop nearest) {

        if (getMarker(nearest) != null) {
            if (nearestStnMarker != null) {
                nearestStnMarker.setIcon(stopIconDrawable);
            }
            nearestStnMarker = stopMarkerMap.get(nearest);
            nearestStnMarker.setIcon(closestStopIconDrawable);
        }
    }

    /**
     * Manage mapping from stops to markers using a map from stops to markers.
     * The mapping in the other direction is done using the Marker.setRelatedObject() and
     * Marker.getRelatedObject() methods.
     */
    private Marker getMarker(Stop stop) {
        return stopMarkerMap.get(stop);
    }

    private void setMarker(Stop stop, Marker marker) {
        stopMarkerMap.put(stop, marker);
    }

    private void clearMarker(Stop stop) {
        stopMarkerMap.remove(stop);
    }

    private void clearMarkers() {
        stopMarkerMap.clear();
    }


    @Override
    public Iterator<Stop> iterator() {
        return stopMarkerMap.keySet().iterator();
    }
}