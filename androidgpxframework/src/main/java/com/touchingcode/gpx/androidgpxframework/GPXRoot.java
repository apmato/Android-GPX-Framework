package com.touchingcode.gpx.androidgpxframework;

import java.util.ArrayList;

/**
 * This class builds up basic structure of gpx object.
 * All gpx elements will be added to this object.
 *
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXRoot extends GPXElement {

    private String schema = "http://www.topografix.com/GPX/1/1";
    private String version = "1.1";
    private String creator = "http://gpxframework.com";
    private GPXMetadata metadata;
    private ArrayList<GPXWaypoint> waypoints = new ArrayList<>();
    private ArrayList<GPXRoute> routes = new ArrayList<>();
    private ArrayList<GPXTrack> tracks = new ArrayList<>();
    private GPXExtensions extensions;

    @Override
    public GPXRoot initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {

        version = valueOfAttributeNamed("version", element);
        creator = valueOfAttributeNamed("creator", element);
        metadata = (GPXMetadata) childElementOfClass(GPXMetadata.class, element);
        extensions = (GPXExtensions) childElementOfClass(GPXExtensions.class, element);

        childElementsOfClass(GPXWaypoint.class, element, waypoints);
        childElementsOfClass(GPXRoute.class, element, routes);
        childElementsOfClass(GPXTrack.class, element, tracks);

        return this;
    }

    public GPXRoot rootWithCreator(String creatorString) {
        GPXRoot root = new GPXRoot();
        creator = creatorString;
        return root;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public GPXMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(GPXMetadata metadata) {
        this.metadata = metadata;
    }

    public GPXExtensions getExtensions() {
        return extensions;
    }

    public void setExtensions(GPXExtensions extensions) {
        this.extensions = extensions;
    }

    @Override
    public String tagName() {
        return "gpx";
    }

    public GPXWaypoint newWaypointWithLatitude(Double latitude, Double longitude) {
        GPXWaypoint waypoint = GPXWaypoint.waypointWithLatitude(latitude, longitude);
        waypoints.add(waypoint);
        return waypoint;
    }

    public void addWaypoint(GPXWaypoint waypoint) {
        if(!waypoints.contains(waypoint)) {
            waypoints.add(waypoint);
        }
    }

    public void addWaypoints(ArrayList<GPXWaypoint> array) {
        for (GPXWaypoint waypoint : array) {
            addWaypoint(waypoint);
        }
    }

    public void removeWaypoint(GPXWaypoint waypoint) {
        int index = waypoints.indexOf(waypoint);
        waypoints.remove(index);
    }

    public GPXRoute newRoute() {
        GPXRoute route = new GPXRoute();
        addRoute(route);
        return route;
    }

    public void addRoute(GPXRoute route) {
        if (!routes.contains(route)){
            routes.add(route);
        }
    }

    public void addRoutes(ArrayList<GPXRoute> array) {
        for (GPXRoute route : array) {
            addRoute(route);
        }
    }

    public void removeRoute(GPXRoute route) {
        int index = routes.indexOf(route);
        routes.remove(index);
    }

    public GPXTrack newTrack() {
        GPXTrack track = new GPXTrack();
        addTrack(track);
        return track;
    }

    public void addTrack(GPXTrack track) {
        if (!tracks.contains(track)) {
            tracks.add(track);
        }
    }

    public void addTracks(ArrayList<GPXTrack> array) {
        for (GPXTrack track : array) {
            addTrack(track);
        }
    }

    public void removeTrack(GPXTrack track) {
        int index = tracks.indexOf(track);
        tracks.remove(index);
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel) {
        String attribute = "";

        if(schema != null){
            attribute = attribute + " xmlns=\"" + schema + "\"";
        }
        if(version != null) {
            attribute = attribute + " version=\"" + version + "\"";
        }
        if(creator != null) {
            attribute = attribute + " creator=\"" + creator + "\"";
        }
        gpx.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {
        if(metadata != null) {
            metadata.gpx(gpx, indentationLevel);
        }

        for (GPXWaypoint waypoint : waypoints) {
            waypoint.gpx(gpx, indentationLevel);
        }

        for (GPXRoute route : routes) {
            route.gpx(gpx, indentationLevel);
        }

        for (GPXTrack track : tracks) {
            track.gpx(gpx, indentationLevel);
        }

        if(extensions != null) {
            extensions.gpx(gpx, indentationLevel);
        }
    }

    public void removeUnnecessaryTrackPoints(){
        tracks = GPXSmoother.removeUnnecessaryTrackPoints(tracks);
    }
}
