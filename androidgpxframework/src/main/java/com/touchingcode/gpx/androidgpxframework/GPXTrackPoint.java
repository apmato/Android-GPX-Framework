package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXTrackPoint extends GPXWaypoint {

	public static GPXTrackPoint trackpointWithLatitude(Double latitude, Double longitude) {
        GPXTrackPoint trackpoint = new GPXTrackPoint();
	    trackpoint.setLatitude(latitude);
	    trackpoint.setLongitude(longitude);

	    return trackpoint;
	}

	@Override
	public String tagName() {
        return "trkpt";
	}
}
