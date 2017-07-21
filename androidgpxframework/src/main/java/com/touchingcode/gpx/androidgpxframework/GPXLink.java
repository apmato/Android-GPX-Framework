package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXLink extends GPXElement {

    private String text;
    private String mimetype;
    private String href;

    @Override
    public GPXLink initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        text = textForSingleChildElementNamed("text", element);
        mimetype = textForSingleChildElementNamed("type", element);
        href = valueOfAttributeNamed("href" , element);

        return this;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public String tagName() {
        return "link";
    }

    public static GPXLink linkWithHref(String href) {
        GPXLink link = new GPXLink();
        link.href = href;

        return link;
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel) {
        String attribute = "";
         if(href != null) {
             attribute = attribute + " href=\"" + href + "\"";
         }
         gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel){
         gpxAddValue(gpx, text, "text", indentationLevel);
         gpxAddValue(gpx, mimetype, "type", indentationLevel);
    }
}
