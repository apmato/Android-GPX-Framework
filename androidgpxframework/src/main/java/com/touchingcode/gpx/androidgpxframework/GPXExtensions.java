package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXExtensions extends GPXElement {

	@Override
	public GPXExtensions initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        super.initWithXMLElement(element, parent);

		return this;
	}

	@Override
	public String tagName() {
        return "extensions";
	}

	public void addChildTagToGpx(StringBuilder gpx, int indentationLevel) {
    	super.addChildTagToGpx(gpx, indentationLevel);
	}
}
