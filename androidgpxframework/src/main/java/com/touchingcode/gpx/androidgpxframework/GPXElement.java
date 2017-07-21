package com.touchingcode.gpx.androidgpxframework;

import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides fundamental functionality to create a gpx object and gpx formatted string.
 * Almost all classes inherit this class.
 *
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXElement<T> {

    private GPXElement parent;

    /**
     *
     * Child classes will override this method
     *
     * @param element
     * @param parent
     * @return
     */
    public GPXElement initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        this.parent = parent;
        return  this;
    }

    public String tagName() {
        return null;
    }

    public Object implementClasses() {
        return null;
    }

    public void dealloc(){
        parent = null;
    }

    String valueOfAttributeNamed(String name, TBXML.TBXMLElement element){
        return valueOfAttributeNamed(name, element, false);
    }

    String valueOfAttributeNamed(String name, TBXML.TBXMLElement element, Boolean required) {
        String value = TBXML.valueOfAttributeForElement(name, element);
        if (value == null && required) {
            Log.d("Notification", "kGPXInvalidGPXFormatNotification : " + name + " element require " + name + " attribute.");
        }
        return value;
    }

    String textForSingleChildElementNamed(String name, TBXML.TBXMLElement element){
        return textForSingleChildElementNamed(name, element, false);
    }

    String textForSingleChildElementNamed(String name, TBXML.TBXMLElement element, Boolean required) {
        TBXML.TBXMLElement el = TBXML.childElement(name, element);
        if (el != null){
            return TBXML.textForElement(el);
        } else {
            if(required){
                Log.d("Notification", "kGPXInvalidGPXFormatNotification : " + name + " element require " + name + " elements.");
            }
        }
        return null;
    }

    GPXElement childElementOfClass(Class<? extends GPXElement> _class, TBXML.TBXMLElement element){
        return childElementOfClass(_class, element, false);
    }


    GPXElement childElementOfClass(Class<? extends GPXElement> _class, TBXML.TBXMLElement element, Boolean required) {

        GPXElement firstElement = null;
        try {
            Constructor<?> elementConstructor = _class.getConstructor();
            GPXElement<T> elementClass = (GPXElement<T>) elementConstructor.newInstance();
            TBXML.TBXMLElement el = TBXML.childElement(elementClass.tagName(), element);

            if(el != null) {
                firstElement = elementClass.initWithXMLElement(el, parent);
                TBXML.TBXMLElement secondElement = TBXML.nextSibling(elementClass.tagName(), el);

                if(secondElement != null){
                    Log.d("Notification", "kGPXInvalidGPXFormatNotification : element has more than two " + elementClass.tagName() + " elements.");
                }
            }

            if(required && firstElement == null){
                Log.d("Notification", "kGPXInvalidGPXFormatNotification : element require " + elementClass.tagName() + " element.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return firstElement;
    }

    GPXElement childElementNamed(String name, Class<? extends GPXElement> _class, TBXML.TBXMLElement element){
        return childElementNamed(name, _class, element, false);
    }
    
    GPXElement childElementNamed(String name, Class<? extends GPXElement> _class, TBXML.TBXMLElement element, Boolean required) {

        GPXElement firstElement = null;
        try {
            Constructor<?> elementConstructor = _class.getConstructor();
            GPXElement<T> elementClass = (GPXElement<T>) elementConstructor.newInstance();
            TBXML.TBXMLElement el = TBXML.childElement(name, element);

            if(el != null) {
                firstElement = elementClass.initWithXMLElement(el, parent);
                TBXML.TBXMLElement secondElement = TBXML.nextSibling(name, el);
                if(secondElement != null) {
                    Log.d("Notification", "kGPXInvalidGPXFormatNotification : " + elementClass.tagName() + " element has more than two " + elementClass.tagName() + " elements.");
                }
            }

            if(required && firstElement == null){
                Log.d("Notification", "kGPXInvalidGPXFormatNotification : element require " + elementClass.tagName() + " element.");
            }

        } catch(Exception e){
            e.printStackTrace();
        }
        return firstElement;
    }

    /**
     *
     * This is the fundamental method to create a Root-Object from a XML file.
     * This method has been modified to work without callback.
     *
     * @param _class
     * @param element
     * @param array
     * @param <T>
     */
    <T> void childElementsOfClass(Class<? extends GPXElement> _class, TBXML.TBXMLElement element, ArrayList<GPXElement<T>> array) {

        try {
            Constructor<?> elementConstructor = _class.getConstructor();
            GPXElement<T> elementClass = (GPXElement<T>) elementConstructor.newInstance();
            TBXML.TBXMLElement el = TBXML.childElement(elementClass.tagName(), element);

            while(el != null){
                GPXElement<T> instance = (GPXElement<T>) elementConstructor.newInstance();
                instance.initWithXMLElement(el, parent);
                array.add(instance);
                el = TBXML.nextSibling(elementClass.tagName(), el);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * original name: gpx
     *
     * This method generates XML-data from a GPXRoot-Object and return it in String.
     *
     * @return String
     */
    public String generateGPX(){
        StringBuilder gpx = new StringBuilder();
        gpx(gpx, 0);
        return gpx.toString();
    }

    public void gpx(StringBuilder gpx, int indentationLevel){
        addOpenTagToGpx(gpx, indentationLevel);
        addChildTagToGpx(gpx, indentationLevel + 1);
        addCloseTagToGpx(gpx, indentationLevel);
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel){
        String tagName = tagName();
        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName + ">\r\n");
    }

    public void addChildTagToGpx(StringBuilder gpx, int indentationLevel){
        // Override to subclasses
    }

    public void addCloseTagToGpx(StringBuilder gpx, int indentationLevel){
        String tagName = tagName();
        gpx.append(indentForIndentationLevel(indentationLevel) + "</" + tagName + ">\r\n");
    }

    /**
     *
     * original name: gpxaddPropertyForValuetagNameindentationLevel
     *
     * @param gpx
     * @param value
     * @param tagName
     * @param indentationLevel
     */
    public void gpxAddValue(StringBuilder gpx, String value, String tagName, int indentationLevel) {
        gpxAddValueToTag(gpx, value, "", tagName, "", indentationLevel);
    }

    /**
     *
     * original name: gpxaddPropertyForValuetagNameattributeindentationLevel
     *
     * @param gpx
     * @param value
     * @param tagName
     * @param attribute
     * @param indentationLevel
     */
    public void gpxAddValueWithAttribute(StringBuilder gpx, String value, String tagName, String attribute, int indentationLevel) {
        gpxAddValueToTag(gpx, value, "", tagName, attribute, indentationLevel);
    }

    /**
     *
     * original name: gpxaddPropertyForValuedefaultValuetagNameindentationLevel
     *
     * @param gpx
     * @param value
     * @param defaultValue
     * @param tagName
     * @param indentationLevel
     */
    public void gpxAddValueWithDefaultValue(StringBuilder gpx, String value, String defaultValue, String tagName, int indentationLevel){
        gpxAddValueToTag(gpx, value, defaultValue, tagName, "", indentationLevel);
    }

    /**
     *
     * original name: gpxaddPropertyForValuedefaultValuetagNameattributeindentationLevel
     *
     * @param gpx
     * @param value
     * @param defaultValue
     * @param tagName
     * @param attribute
     * @param indentationLevel
     */
    public void gpxAddValueToTag(StringBuilder gpx, String value, String defaultValue, String tagName, String attribute, int indentationLevel) {

        if (value == null || value.equals(""))
           return;

        if (defaultValue != null && value.equals(defaultValue))
           return;

        Pattern pattern = Pattern.compile("[^a-z0-9.,+-/*!='\"()\\[\\]{}!$%@?_;: #\t\r\n]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()){
            gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName + attribute + "><![CDATA[" + value.replaceAll("]]>", "]]&gt;") + "]]></" + tagName + ">\r\n");
        } else {
            gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName + attribute + ">" + value + "</" + tagName + ">\r\n");
        }
    }

    public String indentForIndentationLevel(int indentationLevel){
        StringBuilder string = new StringBuilder();

        for (int i = 0; i < indentationLevel; ++i) {
            string.append("\t");
        }

        return string.toString();
    }
}
