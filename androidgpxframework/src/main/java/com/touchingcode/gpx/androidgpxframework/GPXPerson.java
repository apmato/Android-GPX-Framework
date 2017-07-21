package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXPerson extends GPXElement{

    private String name;
    private GPXEmail email;
    private GPXLink link;

    @Override
    public GPXPerson initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        name = textForSingleChildElementNamed("name", element);
        email = (GPXEmail) childElementOfClass(GPXEmail.class, element);
        link = (GPXLink) childElementOfClass(GPXLink.class, element);

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GPXEmail getEmail() {
        return email;
    }

    public void setEmail(GPXEmail email) {
        this.email = email;
    }

    public GPXLink getLink() {
        return link;
    }

    public void setLink(GPXLink link) {
        this.link = link;
    }

    @Override
    public String tagName() {
        return "person";
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {
        gpxAddValue(gpx, name, "name", indentationLevel);
        if(email != null){
            email.gpx(gpx, indentationLevel);
        }
        if(link != null){
            link.gpx(gpx, indentationLevel);
        }
    }
}
