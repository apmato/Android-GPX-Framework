package com.touchingcode.gpx.androidgpxframework;

import java.util.Date;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXCopyright extends GPXElement {

    private String license;
    private String author;
    private String yearValue;

    @Override
    public GPXCopyright initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent){
        yearValue = textForSingleChildElementNamed("year", element);
        license = textForSingleChildElementNamed("license", element);
        author = valueOfAttributeNamed("author", element);

        return this;
    }

    public GPXCopyright copyrightWithAuthor(String author){
        GPXCopyright copyright = new GPXCopyright();
        copyright.author = author;

        return copyright;
    }

    @Override
    public String tagName(){
        return "copyright";
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getYear(){
        return GPXType.dateTime(yearValue);
    }

    public void setYear(Date year){
        yearValue = GPXType.valueForDateTime(year);
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel){
        String attribute = "";
        if (author != null) {
            attribute = " author=\"" + author + "\"";
        }
        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel){
        gpxAddValue(gpx, yearValue, "year", indentationLevel);
        gpxAddValue(gpx, license, "license", indentationLevel );
    }
}
