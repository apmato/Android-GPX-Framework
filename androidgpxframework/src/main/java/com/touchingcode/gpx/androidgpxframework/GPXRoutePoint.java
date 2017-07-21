package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXRoutePoint extends GPXWaypoint {

	public static GPXRoutePoint routepointWithLatitude(Double latitude, Double longitude) {
         GPXRoutePoint routepoint = new GPXRoutePoint();
         routepoint.setLatitude(latitude);
         routepoint.setLongitude(longitude);

    	 return routepoint;
	}

	@Override
	public String tagName() {
        return "rtept";
	}
}
