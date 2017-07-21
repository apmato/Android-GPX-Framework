package com.touchingcode.gpx.androidgpxframework;

import java.util.ArrayList;
import java.util.Date;

/**
 * Point of interest or named feature on a map.
 * This class contains all coordinates information and GPXRoutePoint and GPXTrackPoint inherit this class.
 *
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXWaypoint extends GPXElement {

    private String name;
    private String comment;
    private String desc;
    private String source;
    private String symbol;
    private String type;
    private ArrayList<GPXLink> links = new ArrayList<>();
    private GPXExtensions extensions;
    private String elevationValue;
    private String timeValue;
    private String magneticVariationValue;
    private String geoidHeightValue;
    private String fixValue;
    private String satellitesValue;
    private String horizontalDilutionValue;
    private String verticalDilutionValue;
    private String positionDilutionValue;
    private String ageOfDGPSDataValue;
    private String DGPSidValue;
    private String latitudeValue;
    private String longitudeValue;

    @Override
    public GPXWaypoint initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {

        elevationValue = textForSingleChildElementNamed("ele", element);
        timeValue = textForSingleChildElementNamed("time", element);
        magneticVariationValue = textForSingleChildElementNamed("magvar", element);
        geoidHeightValue = textForSingleChildElementNamed("geoidheight", element);
        name = textForSingleChildElementNamed("name", element);
        comment = textForSingleChildElementNamed("cmt", element);
        desc = textForSingleChildElementNamed("desc", element);
        source = textForSingleChildElementNamed("src", element);

        childElementsOfClass(GPXLink.class, element, links);

        symbol = textForSingleChildElementNamed("sym", element);
        type = textForSingleChildElementNamed("type", element);
        fixValue = textForSingleChildElementNamed("fix", element);
        satellitesValue = textForSingleChildElementNamed("sat", element);
        horizontalDilutionValue = textForSingleChildElementNamed("hdop", element);
        verticalDilutionValue = textForSingleChildElementNamed("vdop", element);
        positionDilutionValue = textForSingleChildElementNamed("pdop", element);
        ageOfDGPSDataValue = textForSingleChildElementNamed("ageofdgpsdata", element);
        DGPSidValue = textForSingleChildElementNamed("dgpsid", element);
        extensions = (GPXExtensions) childElementOfClass(GPXExtensions.class, element);
        latitudeValue = valueOfAttributeNamed("lat", element);
        longitudeValue = valueOfAttributeNamed("lon", element);

        return this;
    }

    public static GPXWaypoint waypointWithLatitude(Double latitude, Double longitude){
        GPXWaypoint waypoint = new GPXWaypoint();
        waypoint.setLatitude(latitude);
        waypoint.setLongitude(longitude);

        return waypoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GPXExtensions getExtensions() {
        return extensions;
    }

    public void setExtensions(GPXExtensions extensions) {
        this.extensions = extensions;
    }

    public boolean hasElevation(){
        return elevationValue != null;
    }

    public Double getElevation(){
        return GPXType.decimal(elevationValue);
    }

    public void setElevation(Double elevation){
        elevationValue = GPXType.valueForDecimal(elevation);
    }

    public boolean hasTime(){
        return timeValue != null;
    }

    public Date getTime(){
        return GPXType.dateTime(timeValue);
    }

    public void setTime(Date time){
        timeValue = GPXType.valueForDateTime(time);
    }

    public Double getMagneticVariation(){
        return GPXType.degress(magneticVariationValue);
    }

    public void setMagneticVariation(Double magneticVariation){
        magneticVariationValue = GPXType.valueForDegress(magneticVariation);
    }

    public Double getGeoidHeight(){
        return GPXType.decimal(geoidHeightValue);
    }

    public void setGeoidHeight(Double geoidHeight){
        geoidHeightValue = GPXType.valueForDecimal(geoidHeight);
    }

    public GPXLink newLinkWithHref(String href) {
        GPXLink link = GPXLink.linkWithHref(href);
        addLink(link);

        return link;
    }

    public void addLink(GPXLink link){
        if(!links.contains(link)) {
            links.add(link);
        }
    }

    public void addLinks(ArrayList<GPXLink> array){
        for (GPXLink link : array) {
            addLink(link);
        }
    }

    public void removeLink(GPXLink link){
        if(links.contains(link)){
            int index = links.indexOf(link);
            links.remove(index);
        }
    }

    public int getFix(){
        return GPXType.fix(fixValue);
    }

    public void setFix(int fix){
        fixValue = GPXType.valueForFix(fix);
    }

    public  int getSatellites(){
        return GPXType.nonNegativeInteger(satellitesValue);
    }

    public void setSatellites(int satellites){
        satellitesValue = GPXType.valueForNonNegativeInteger(satellites);
    }

    public Double getHorizontalDilution(){
        return GPXType.decimal(horizontalDilutionValue);
    }

    public void setHorizontalDilution(Double horizontalDilution){
        horizontalDilutionValue = GPXType.valueForDecimal(horizontalDilution);
    }

    public Double getVerticalDilution(){
        return GPXType.decimal(verticalDilutionValue);
    }

    public void setVerticalDilution(Double verticalDilution){
        verticalDilutionValue = GPXType.valueForDecimal(verticalDilution);
    }

    public Double getPositionDilution(){
        return GPXType.decimal(positionDilutionValue);
    }

    public void setPositionDilution(Double positionDilution){
        positionDilutionValue = GPXType.valueForDecimal(positionDilution);
    }

    public Double getAgeOfDGPSData(){
        return GPXType.decimal(ageOfDGPSDataValue);
    }

    public void setAgeOfDGPSData(Double ageOfDGPSData){
        ageOfDGPSDataValue = GPXType.valueForDecimal(ageOfDGPSData);
    }

    public int DGPSid(){
        return GPXType.dgpsStation(DGPSidValue);
    }

    public void setDGPSid(int DGPSid){
        DGPSidValue = GPXType.valueForDgpsStation(DGPSid);
    }

    public Double getLatitude(){
        return GPXType.latitude(latitudeValue);
    }

    public void setLatitude(Double latitude) {
        latitudeValue = GPXType.valueForLatitude(latitude);
    }

    public Double getLongitude(){
        return GPXType.longitude(longitudeValue);
    }

    public void setLongitude(Double longitude) {
        longitudeValue = GPXType.valueForLongitude(longitude);
    }

    @Override
    public String tagName() {
        return "wpt";
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel) {
        String attribute = "";
        if (latitudeValue != null) {
            attribute = attribute + " lat=\"" + latitudeValue +  "\"";
        }
        if (longitudeValue != null) {
            attribute = attribute + " lon=\"" + longitudeValue +  "\"";
        }
        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {
        gpxAddValue(gpx, elevationValue, "ele", indentationLevel);
        gpxAddValue(gpx, timeValue, "time", indentationLevel);
        gpxAddValue(gpx, magneticVariationValue, "magvar", indentationLevel);
        gpxAddValue(gpx, geoidHeightValue, "geoidheight", indentationLevel);
        gpxAddValue(gpx, name, "name", indentationLevel);
        gpxAddValue(gpx, comment, "cmt", indentationLevel);
        gpxAddValue(gpx, desc, "desc", indentationLevel);
        gpxAddValue(gpx, source, "src", indentationLevel);
        for (GPXLink link: links) {
            link.gpx(gpx, indentationLevel);
        }
        gpxAddValue(gpx, symbol, "sym", indentationLevel);
        gpxAddValue(gpx, type, "type", indentationLevel);
        gpxAddValue(gpx, fixValue, "fix", indentationLevel);
        gpxAddValue(gpx, satellitesValue, "sat", indentationLevel);
        gpxAddValue(gpx, horizontalDilutionValue, "hdop", indentationLevel);
        gpxAddValue(gpx, verticalDilutionValue, "vdop", indentationLevel);
        gpxAddValue(gpx, positionDilutionValue, "pdop", indentationLevel);
        gpxAddValue(gpx, ageOfDGPSDataValue, "ageofdgpsdata", indentationLevel);
        gpxAddValue(gpx, DGPSidValue, "dgpsid", indentationLevel);
        if(extensions != null) {
            extensions.gpx(gpx, indentationLevel);
        }
    }
}
