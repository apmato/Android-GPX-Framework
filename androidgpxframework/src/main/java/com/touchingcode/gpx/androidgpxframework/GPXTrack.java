package com.touchingcode.gpx.androidgpxframework;

import java.util.ArrayList;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXTrack extends GPXElement{

    private String name;
    private String comment;
    private String desc;
    private String source;
    private ArrayList<GPXLink> links = new ArrayList<>();
    private String type;
    private GPXExtensions extensions;
    private ArrayList<GPXTrackSegment> tracksegments = new ArrayList<>();
    private String numberValue;

    @Override
    public GPXTrack initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        name = textForSingleChildElementNamed("name", element);
        comment = textForSingleChildElementNamed("cmt", element);
        desc = textForSingleChildElementNamed("desc", element);
        source = textForSingleChildElementNamed("src", element);

        childElementsOfClass(GPXLink.class, element, links);

        numberValue = textForSingleChildElementNamed("number", element);
        type = textForSingleChildElementNamed("type", element);
        extensions = (GPXExtensions) childElementOfClass(GPXExtensions.class, element);

        childElementsOfClass(GPXTrackSegment.class, element, tracksegments);

        return this;
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

    @Override
    public String tagName() {
        return "trk";
    }

    public GPXLink newLinkWithHref(String href) {
        GPXLink link = GPXLink.linkWithHref(href);
        addLink(link);
        return link;
    }

    public void addLink(GPXLink link) {
        if (!links.contains(link)) {
            links.add(link);
        }
    }

    public void addLinks(ArrayList<GPXLink> array) {
        for (GPXLink link : array) {
          addLink(link);
        }
    }

    public void removeLink(GPXLink link) {
        if(links.contains(link)) {
            links.remove(link);
        }
    }

    public GPXTrackSegment newTrackSegment() {
        GPXTrackSegment tracksegment = new GPXTrackSegment();
        addTracksegment(tracksegment);
        return tracksegment;
    }

    public void addTracksegment(GPXTrackSegment tracksegment) {
        if(!tracksegments.contains(tracksegment)){
            tracksegments.add(tracksegment);
        }
    }

    public void addTracksegments(ArrayList<GPXTrackSegment> array) {
        for (GPXTrackSegment tracksegment : array) {
           addTracksegment(tracksegment);
        }
    }

    public void removeTracksegment(GPXTrackSegment tracksegment) {
        if(tracksegments.contains(tracksegment)){
            tracksegments.remove(tracksegment);
        }
    }

    public ArrayList<GPXTrackSegment> getTracksegments() {
        return tracksegments;
    }

    public GPXTrackPoint newTrackpointWithLatitude(Double latitude, Double longitude) {
        GPXTrackSegment trackSegment;
        if(tracksegments.size() == 0) {
           newTrackSegment();
        }
        trackSegment = tracksegments.get(tracksegments.size() - 1);
        return trackSegment.newTrackpointWithLatitude(latitude, longitude);
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

        for (GPXTrackSegment tracksegment : tracksegments) {
           tracksegment.gpx(gpx, indentationLevel);
        }
    }
}