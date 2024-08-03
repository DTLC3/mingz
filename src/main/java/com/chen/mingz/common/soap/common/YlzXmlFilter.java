package com.chen.mingz.common.soap.common;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import java.util.Arrays;
import java.util.List;

public class YlzXmlFilter extends XMLFilterImpl {
    private boolean ignoreNamespace = false;
    private String rootNamespace = null;
    private boolean isRootElement = true;
    private List<String> include = Arrays.asList(new String[]{"Envelope", "system", "business"});

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String url, String localName, String qName, Attributes atts) throws SAXException {
        if (this.ignoreNamespace) {
            url = "";
        }
        if ("Envelope".equals(localName)) {
            super.startElement(url, localName, qName, atts);
            return;
        } else if (!url.equals("") && include.contains(localName)) {
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
        } else if ("para".equals(localName)) {
            Attributes newAtts = new YlzAttributes(atts.getValue(0), atts.getValue(1));
            atts = newAtts;
        } else if ("row".equals(localName)) {
            if (atts.getLength() == 2) {
                String[] keys = atts.getValue(0).split("#");
                String[] values = atts.getValue(1).split("#");
                Attributes rowAtts = new RowAttributes(java.util.Arrays.asList(keys),
                        java.util.Arrays.asList(values));
                atts = rowAtts;
            }
        }
        super.startElement(url, localName, localName, atts);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (this.ignoreNamespace) uri = "";
        if (include.contains(localName) ||
                "Header".equals(localName) ||
                "Body".equals(localName)) {
            localName = qName;
        }
        super.endElement(uri, localName, localName);
    }

    @Override
    public void startPrefixMapping(String prefix, String url) throws SAXException {
        if (this.isRootElement && "http://schemas.xmlsoap.org/soap/envelope/".equals(url)) {
            super.startPrefixMapping(prefix, url);
        }
    }
}
