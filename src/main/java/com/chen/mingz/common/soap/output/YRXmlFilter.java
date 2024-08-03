package com.chen.mingz.common.soap.output;


import com.chen.mingz.common.soap.common.RowAttributes;
import com.chen.mingz.common.soap.common.YlzAttributes;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.XMLFilterImpl;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chenmingzhi on 17/9/19.
 */
public class YRXmlFilter extends XMLFilterImpl {

    private List<String> include = Arrays.asList(new String[]{"Envelope", "business"});

    private boolean isResultSet = false;

    private boolean isFirstBus = true;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String url, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.contains("Envelope")) {
            localName = qName + //" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                    " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                    " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
                    " soap:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\" ";
            super.startElement(url, localName, localName, atts);
            return;
        } else if (!url.equals("") && qName.contains("business") && isFirstBus) { //包换域名显示
            if (qName != null) {
                String[] qNameA = qName.split(":");
                if (qNameA != null && qNameA.length > 0) {
                    String domain = qNameA[0];
                    localName = qName + " xmlns:" + domain + "=\"" + url + "\"";
                }
            }
            isFirstBus = false;
        } else if ((!url.equals("") && "Header".equals(localName)) ||
                (!url.equals("") && "Body".equals(localName)) ||
                (!url.equals("") && "business".equals(localName))) {
            localName = qName;
        } else if ("result".equals(localName)) {
            String value0 = atts.getValue(0);
            if (value0.contains("#")) { //如果包含#号则表示为ResultSet里面的内容
                String[] keys = atts.getValue(0).split("#");
                String[] values = atts.getValue(1).split("#");
                Attributes rowAtts = new RowAttributes(java.util.Arrays.asList(keys),
                        java.util.Arrays.asList(values));
                atts = rowAtts;
            } else {            //转为小写
                String keys = atts.getValue(0);
                String values = atts.getValue(1);
                Attributes newAtts = new YlzAttributes(keys.toLowerCase(), values);
                atts = newAtts;
            }
        }
        super.startElement(url, localName, localName, atts);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (include.contains(localName) ||
                "Header".equals(localName) ||
                "Body".equals(localName)) {
            localName = qName;
        }
        super.endElement(uri, localName, localName);
    }

}
