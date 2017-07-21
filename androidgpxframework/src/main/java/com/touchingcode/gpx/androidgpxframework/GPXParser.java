package com.touchingcode.gpx.androidgpxframework;

import java.net.URI;
import java.net.URL;

/**
 * This class is used to read existing GPX files or String.
 *
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXParser {

	public static GPXRoot parseGPXAtURL(URL url) {
        GPXRoot root = new GPXRoot();
        try {
            TBXML xml = new TBXML(url);
            root.initWithXMLElement(xml.rootXMLElement(), null);
        } catch (Exception e){
            e.getStackTrace();
        }
        return root;
	}

    public static GPXRoot parseGPXAtPath(URI uri) {
        GPXRoot root = new GPXRoot();
        try {
            TBXML xml = new TBXML(uri);
            root.initWithXMLElement(xml.rootXMLElement(), null);
        } catch (Exception e){
            e.getStackTrace();
        }
        return root;
    }

    public static GPXRoot parseGPXWithString(String string) {
        GPXRoot root = new GPXRoot();
        try {
            TBXML xml = new TBXML(string);
            root.initWithXMLElement(xml.rootXMLElement(), null);
        } catch (Exception e){
            e.getStackTrace();
        }
        return root;
    }
}
