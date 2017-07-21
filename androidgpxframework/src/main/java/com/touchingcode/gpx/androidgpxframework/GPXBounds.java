package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXBounds extends GPXElement{

    private String minLatitudeValue;
    private String minLongitudeValue;
    private String maxLatitudeValue;
    private String maxLongitudeValue;

    @Override
    public GPXBounds initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        minLatitudeValue = valueOfAttributeNamed("minlat", element);
        minLongitudeValue = valueOfAttributeNamed("minlon", element);
        maxLatitudeValue = valueOfAttributeNamed("maxlat", element);
        maxLongitudeValue = valueOfAttributeNamed("maxlon", element);

        return this;
    }

    public GPXBounds boundsWithMinLatitude(Double minLatitude, Double minLongitude, Double maxLatitude, Double maxLongitude) {
        GPXBounds bounds = new GPXBounds();
        bounds.setMinLatitude(minLatitude);
        bounds.setMinLongitude(minLongitude);
        bounds.setMaxLatitude(maxLatitude);
        bounds.setMaxLongitude(maxLongitude);

        return bounds;
    }

    public String getMinLatitude() {
        return minLatitudeValue;
    }

    public void setMinLatitude(Double minLatitudeValue) {
        this.minLatitudeValue = GPXType.valueForLatitude(minLatitudeValue);
    }

    public String getMinLongitude() {
        return minLongitudeValue;
    }

    public void setMinLongitude(Double minLongitudeValue) {
        this.minLongitudeValue = GPXType.valueForLongitude(minLongitudeValue);
    }

    public String getMaxLatitude() {
        return maxLatitudeValue;
    }

    public void setMaxLatitude(Double maxLatitudeValue) {
        this.maxLatitudeValue = GPXType.valueForLatitude(maxLatitudeValue);
    }

    public String getMaxLongitude() {
        return maxLongitudeValue;
    }

    public void setMaxLongitude(Double maxLongitudeValue) {
        this.maxLongitudeValue = GPXType.valueForLongitude(maxLongitudeValue);
    }

    @Override
    public String tagName() {
        return "bounds";
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel) {
        StringBuilder attribute = new StringBuilder();
        if (minLatitudeValue != null) {
            attribute.append(" minlat=\"" + minLatitudeValue + "\"");
        }
        if (minLongitudeValue != null)  {
            attribute.append(" minlon=\"" + minLongitudeValue + "\"");
        }
        if (maxLatitudeValue != null)  {
            attribute.append(" maxlat=\"" + maxLatitudeValue + "\"");
        }
        if (maxLongitudeValue != null)  {
            attribute.append(" maxlot=\"" + maxLongitudeValue + "\"");
        }

        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }
}
