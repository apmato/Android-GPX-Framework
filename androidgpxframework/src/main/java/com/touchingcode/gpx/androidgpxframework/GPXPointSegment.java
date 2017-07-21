package com.touchingcode.gpx.androidgpxframework;

import java.util.ArrayList;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXPointSegment extends GPXElement {

    private ArrayList<GPXPoint> points = new ArrayList<>();

    @Override
    public GPXPointSegment initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent){
        childElementsOfClass(GPXPoint.class, element, points);

        return this;
    }

    @Override
    public String tagName(){
        return "ptseg";
    }

    public GPXPoint newPointWithLatitude(Double latitude, Double longitude){

        GPXPoint point = new GPXPoint();
        point.pointWithLatitude(latitude, longitude);

        addPoint(point);
        return point;
    }

    public void addPoint(GPXPoint point){
        if(!points.contains(point)) {
            points.add(point);
        }
    }

    public void addPoints(ArrayList<GPXPoint> array){
        for(GPXPoint point : array) {
            addPoint(point);
        }
    }

    public void removePoint(GPXPoint point) {
        if (points.contains(point)){
            int index = points.indexOf(point);
            points.remove(index);
        }
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel){
        for(GPXPoint point : points) {
            point.gpx(gpx, indentationLevel);
        }
    }
}
