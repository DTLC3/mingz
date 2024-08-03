package com.chen.mingz.common.soap.output;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

public class RespondXmlFilter extends XMLFilterImpl {
    private boolean ignoreNamespace = false;
    private boolean isRootElement = true;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String url, String localName, String qName, Attributes atts) throws SAXException {
        if (this.ignoreNamespace) {
            url = "";
        }
        if (this.isRootElement) {
            this.isRootElement = false;
            localName = qName;
        } else if (!url.equals("") && !"Header".equals(localName) && !"Body".equals(localName)) {
            if (qName != null) {
                String[] qNameA = qName.split(":");
                if (qNameA != null && qNameA.length > 0) {
                    String domain = qNameA[0];
                    localName = qName + " xmlns:" + domain + "=\"" + url + "\"";
                }
            }
        } else if ((!url.equals("") && "Header".equals(localName)) ||
                (!url.equals("") && "Body".equals(localName))) {
            localName = qName;
        }
        super.startElement(url, localName, localName, atts);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (this.ignoreNamespace) uri = "";
        if ("Header".equals(localName) ||
                "Body".equals(localName) ||
                "business".equals(localName) ||
                "Envelope".equals(localName)) {
            localName = qName;
        }
        super.endElement(uri, localName, localName);
    }
}
