package com.touchingcode.gpx.androidgpxframework;

import java.util.Date;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXPoint extends GPXElement{

    private String elevationValue;
    private String timeValue;
    private String latitudeValue;
    private String longitudeValue;

    @Override
    public GPXPoint initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        elevationValue = textForSingleChildElementNamed("ele", element);
        timeValue = textForSingleChildElementNamed("time", element);
        latitudeValue = valueOfAttributeNamed("lat", element);
        longitudeValue = valueOfAttributeNamed("lon", element);

        return this;
    }

    public GPXPoint pointWithLatitude(Double latitude, Double longitude){
        GPXPoint point = new GPXPoint();
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        return point;
    }

    @Override
    public String tagName(){
        return "pt";
    }

    public Double getElevation() {
        return GPXType.decimal(elevationValue);
    }

    public void setElevation(Double elevation){
        elevationValue = GPXType.valueForDecimal(elevation);
    }

    public Date getTime() {
        return GPXType.dateTime(timeValue);
    }

    public void setTime(Date time){
        timeValue = GPXType.valueForDateTime(time);
    }

    public Double getLatitude(){
        return GPXType.latitude(latitudeValue);
    }

    public void setLatitude(Double latitude) {
        latitudeValue = GPXType.valueForLatitude(latitude);
    }

    public Double getLongitude() {
        return GPXType.longitude(longitudeValue);
    }

    public void setLongitude(Double longitude) {
        longitudeValue = GPXType.valueForLongitude(longitude);
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel){
        String attribute = "";
        if (latitudeValue != null) {
           attribute = " lat=\"" + latitudeValue + "\"";
        }
        if (longitudeValue != null) {
           attribute = attribute + " lon=\"" + longitudeValue + "\"";
        }
        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {
        gpxAddValue(gpx, elevationValue, "ele", indentationLevel);
        gpxAddValue(gpx, timeValue, "time", indentationLevel);
    }
}
