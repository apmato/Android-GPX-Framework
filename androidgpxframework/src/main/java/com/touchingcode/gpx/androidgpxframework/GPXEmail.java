package com.touchingcode.gpx.androidgpxframework;

/**
 * Android version of the iOS-GPX-Framework from Watanabe Toshinori, Pierre-Loup
 * @author Yoshiki Kuno
 */
public class GPXEmail extends GPXElement{

    private String emailID;
    private String domain;

    @Override
    public  GPXEmail initWithXMLElement(TBXML.TBXMLElement element, GPXElement parent) {
        emailID = valueOfAttributeNamed("id" , element);
        domain = valueOfAttributeNamed("domain" , element);

        return this;
    }

    public GPXEmail emailWithID(String emailID, String domain) {
        GPXEmail email = new GPXEmail();
        email.emailID = emailID;
        email.domain = domain;

        return email;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    @Override
    public String tagName() {
        return "email";
    }

    public void addOpenTagToGpx(StringBuilder gpx, int indentationLevel) {
        String attribute = "";
        if (emailID != null) {
            attribute = " id=\"" + emailID + "\"";
        }
        if (!domain.isEmpty()) {
            attribute = attribute + " domain=\"" + domain + "@\"";
        }
        gpx.append(indentForIndentationLevel(indentationLevel) + "<" + tagName() + attribute + ">\r\n");
    }
}
