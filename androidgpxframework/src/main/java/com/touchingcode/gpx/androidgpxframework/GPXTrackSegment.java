package com.touchingcode.gpx.androidgpxframework;

import java.util.ArrayList;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXTrackSegment extends GPXElement {

    private ArrayList<GPXTrackPoint> trackpoints = new ArrayList<>();
    private GPXExtensions extensions;

    @Override
    public GPXTrackSegment initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent){
        extensions = (GPXExtensions) childElementOfClass(GPXExtensions.class, element);
        childElementsOfClass(GPXTrackPoint.class, element, trackpoints);

        return this;
    }

    public GPXExtensions getExtensions() {
        return extensions;
    }

    public void setExtensions(GPXExtensions extensions) {
        this.extensions = extensions;
    }

    @Override
    public String tagName() {
        return "trkseg";
    }

    public GPXTrackPoint newTrackpointWithLatitude(Double latitude, Double longitude){
        GPXTrackPoint trackpoint = GPXTrackPoint.trackpointWithLatitude(latitude, longitude);
        addTrackpoint(trackpoint);
        return trackpoint;
    }

    public void addTrackpoint(GPXTrackPoint trackpoint){
        if (!trackpoints.contains(trackpoint)) {
            trackpoints.add(trackpoint);
        }
    }

    public void addTrackpoints(ArrayList<GPXTrackPoint> array) {
        for (GPXTrackPoint trackpoint : array) {
            addTrackpoint(trackpoint);
        }
    }

    public void removeTrackpoint(GPXTrackPoint trackpoint) {
        if(trackpoints.contains(trackpoint)){
            trackpoints.remove(trackpoint);
        }
    }

    public ArrayList<GPXTrackPoint> getTrackpoints() {
        return trackpoints;
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {
        if(extensions != null){
            extensions.gpx(gpx, indentationLevel);
        }

        for(GPXTrackPoint trackpoint : trackpoints){
            trackpoint.gpx(gpx, indentationLevel);
        }
    }
}