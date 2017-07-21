package com.touchingcode.gpx.androidgpxframework;

import java.util.ArrayList;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXRoute extends GPXElement {

    private String name;
    private String comment;
    private String desc;
    private String source;
    private ArrayList<GPXLink> links = new ArrayList<>();
    private String type;
    private GPXExtensions extensions;
    private ArrayList<GPXRoutePoint> routepoints = new ArrayList<>();
    private String numberValue;

    @Override
    public GPXRoute initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        name = textForSingleChildElementNamed("name", element);
        comment = textForSingleChildElementNamed("cmt", element);
        desc = textForSingleChildElementNamed("desc", element);
        source = textForSingleChildElementNamed("src", element);

        childElementsOfClass(GPXLink.class, element, links);

        numberValue = textForSingleChildElementNamed("number", element);
        type = textForSingleChildElementNamed("type", element);
        extensions = (GPXExtensions) childElementOfClass(GPXExtensions.class, element);

        childElementsOfClass(GPXRoutePoint.class, element, routepoints);

        return this;
    }

    @Override
    public String tagName(){
        return "rte";
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

    public int getNumber(String numberValue) {
        return GPXType.nonNegativeInteger(numberValue);
    }

    public void setNumber(int number) {
        numberValue = GPXType.valueForNonNegativeInteger(number);
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

    public GPXRoutePoint newRoutepointWithLatitude(Double latitude, Double longitude) {
        GPXRoutePoint routepoint = GPXRoutePoint.routepointWithLatitude(latitude, longitude);
        addRoutepoint(routepoint);
        return routepoint;
    }

    public void addRoutepoint(GPXRoutePoint routepoint) {
        if(!routepoints.contains(routepoint)){
            routepoints.add(routepoint);
        }
    }

    public void addRoutepoints(ArrayList<GPXRoutePoint> array){
        for (GPXRoutePoint routepoint : array) {
           addRoutepoint(routepoint);
        }
    }

    public void removeRoutepoint(GPXRoutePoint routepoint){
        if(routepoints.contains(routepoint)){
            int index = routepoints.indexOf(routepoint);
            routepoints.remove(index);
        }
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {

        gpxAddValue(gpx, name, "name", indentationLevel);
        gpxAddValue(gpx, comment, "cmt", indentationLevel);
        gpxAddValue(gpx, desc, "desc", indentationLevel);
        gpxAddValue(gpx, source, "src", indentationLevel);

        for (GPXLink link : links) {
            link.gpx(gpx, indentationLevel);
        }

        gpxAddValue(gpx, numberValue, "number", indentationLevel);
        gpxAddValue(gpx, type, "type", indentationLevel);

        if (extensions != null) {
            extensions.gpx(gpx, indentationLevel);
        }

        for (GPXRoutePoint routepoint : routepoints) {
            routepoint.gpx(gpx, indentationLevel);
        }
    }
}
