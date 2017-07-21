package com.touchingcode.gpx.androidgpxframework;

import java.util.Date;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXMetadata extends GPXElement{

    private String name;
    private String desc;
    private GPXAuthor author;
    private GPXCopyright copyright;
    private GPXLink link;
    private String keyword;
    private GPXBounds bounds;
    private GPXExtensions extensions;
    private String timeValue;

    @Override
    public GPXMetadata initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent){
        name = textForSingleChildElementNamed("name", element);
        desc = textForSingleChildElementNamed("desc", element);
        author = (GPXAuthor) childElementOfClass(GPXAuthor.class, element);
        copyright = (GPXCopyright) childElementOfClass(GPXCopyright.class, element);
        link = (GPXLink) childElementOfClass(GPXLink.class, element);
        timeValue = textForSingleChildElementNamed("time", element);
        keyword = textForSingleChildElementNamed("keyword", element);
        bounds = (GPXBounds) childElementOfClass(GPXBounds.class, element);
        extensions = (GPXExtensions) childElementOfClass(GPXExtensions.class, element);

        return this;
    }

    @Override
    public String tagName(){
        return "metadata";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public GPXAuthor getAuthor() {
        return author;
    }

    public void setAuthor(GPXAuthor author) {
        this.author = author;
    }

    public GPXCopyright getCopyright() {
        return copyright;
    }

    public void setCopyright(GPXCopyright copyright) {
        this.copyright = copyright;
    }

    public GPXLink getLink() {
        return link;
    }

    public void setLink(GPXLink link) {
        this.link = link;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public GPXBounds getBounds() {
        return bounds;
    }

    public void setBounds(GPXBounds bounds) {
        this.bounds = bounds;
    }

    public GPXExtensions getExtensions() {
        return extensions;
    }

    public void setExtensions(GPXExtensions extensions) {
        this.extensions = extensions;
    }

    public Date getYear(){
        return GPXType.dateTime(timeValue);
    }

    public void setYear(Date year){
        timeValue = GPXType.valueForDateTime(year);
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {

        gpxAddValue(gpx, name, "name", indentationLevel);
        gpxAddValue(gpx, desc, "desc", indentationLevel);

        if(author != null){
            author.gpx(gpx, indentationLevel);
        }

        if(copyright != null){
            copyright.gpx(gpx, indentationLevel);
        }

        if(link != null){
            link.gpx(gpx, indentationLevel);
        }

        gpxAddValueWithDefaultValue(gpx, timeValue, "0", "time", indentationLevel);
        gpxAddValue(gpx, keyword, "keyword", indentationLevel);

        if(bounds != null){
            bounds.gpx(gpx, indentationLevel);
        }

        if(extensions != null){
            extensions.gpx(gpx, indentationLevel);
        }
    }
}
