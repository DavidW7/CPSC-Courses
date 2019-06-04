package ca.ubc.cs.cpsc210.translink.ui;

import android.content.Context;
import ca.ubc.cs.cpsc210.translink.BusesAreUs;
import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.model.StopManager;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static ca.ubc.cs.cpsc210.translink.util.Geometry.*;

// A bus route drawer
public class BusRouteDrawer extends MapViewOverlay {
    /**
     * overlay used to display bus route legend text on a layer above the map
     */
    private BusRouteLegendOverlay busRouteLegendOverlay;
    /**
     * overlays used to plot bus routes
     */
    private List<Polyline> busRouteOverlays;

    /**
     * Constructor
     *
     * @param context the application context
     * @param mapView the map view
     */
    public BusRouteDrawer(Context context, MapView mapView) {
        super(context, mapView);
        busRouteLegendOverlay = createBusRouteLegendOverlay();
        busRouteOverlays = new ArrayList<>();
    }

    /**
     * Plot each visible segment of each route pattern of each route going through the selected stop.
     */
    public void plotRoutes(int zoomLevel) {
        intializePlotRoutes();

        if (StopManager.getInstance().getSelected() != null) {
            for (Route r : StopManager.getInstance().getSelected().getRoutes()) {
                busRouteLegendOverlay.add(r.getNumber());
                for (RoutePattern rp : r.getPatterns()) {
                    routePatternBehaviour(rp, r.getNumber(), zoomLevel);
                }
            }
        }
    }

    private void routePatternBehaviour(RoutePattern rp, String routeNum, int zoomLevel) {
        Polyline pl = new Polyline(context);
        List<GeoPoint> locnsGeo = new ArrayList<>();
        for (int i = 0; i < rp.getPath().size() - 1; i++) {
            LatLon ll1 = rp.getPath().get(i);
            LatLon ll2 = rp.getPath().get(i + 1);

            if (getCases(ll1, ll2) == 1) {
                busRouteOverlays.add(endLine(pl, ll1, ll2, routeNum, zoomLevel, locnsGeo));
            } else if (getCases(ll1, ll2) == 2) {
                locnsGeo = new ArrayList<>();
                pl = new Polyline(context);
                locnsGeo.add(gpFromLatLon(ll1));
            } else if (getCases(ll1, ll2) == 3) {
                locnsGeo.add(gpFromLatLon(ll1));
            }
        }
    }

    private Polyline endLine(Polyline pl, LatLon ll1, LatLon ll2, String routeNum,
                             int zoomLevel, List<GeoPoint> locnsGeo) {
        locnsGeo.add(gpFromLatLon(ll1));
        locnsGeo.add(gpFromLatLon(ll2));
        pl.setPoints(locnsGeo);
        pl.setWidth(getLineWidth(zoomLevel));
        pl.setColor(busRouteLegendOverlay.getColor(routeNum));
        return pl;
    }

    private int getCases(LatLon ll1, LatLon ll2) {
        if (rectangleContainsPoint(super.northWest, super.southEast, ll1)
                && rectangleIntersectsLine(super.northWest, super.southEast, ll1, ll2)) {
            return 1;
        } else if (!rectangleContainsPoint(super.northWest, super.southEast, ll1)
                && rectangleIntersectsLine(super.northWest, super.southEast, ll1, ll2)) {
            return 2;
        } else if (rectangleContainsPoint(super.northWest, super.southEast, ll1)
                && !rectangleIntersectsLine(super.northWest, super.southEast, ll1, ll2)) {
            return 3;
        }
        return 0;
    }

    private void intializePlotRoutes() {
        busRouteLegendOverlay = createBusRouteLegendOverlay();
        busRouteOverlays = new ArrayList<>();
        updateVisibleArea();
    }

    public List<Polyline> getBusRouteOverlays() {
        return Collections.unmodifiableList(busRouteOverlays);
    }

    public BusRouteLegendOverlay getBusRouteLegendOverlay() {
        return busRouteLegendOverlay;
    }


    /**
     * Create text overlay to display bus route colours
     */
    private BusRouteLegendOverlay createBusRouteLegendOverlay() {
        ResourceProxy rp = new DefaultResourceProxyImpl(context);
        return new BusRouteLegendOverlay(rp, BusesAreUs.dpiFactor());
    }

    /**
     * Get width of line used to plot bus route based on zoom level
     *
     * @param zoomLevel the zoom level of the map
     * @return width of line used to plot bus route
     */
    private float getLineWidth(int zoomLevel) {
        if (zoomLevel > 14) {
            return 7.0f * BusesAreUs.dpiFactor();
        } else if (zoomLevel > 10) {
            return 5.0f * BusesAreUs.dpiFactor();
        } else {
            return 2.0f * BusesAreUs.dpiFactor();
        }
    }
}
