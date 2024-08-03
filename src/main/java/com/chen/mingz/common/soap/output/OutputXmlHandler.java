package com.chen.mingz.common.soap.output;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * Created by chenmingzhi on 17/9/19.
 */
public class OutputXmlHandler extends DefaultHandler {

    private RespondXml respond = new RespondXml();

    private Map<String, String> result = new HashMap<String, String>();

    private Map<String, List<Map<String, String>>> resultSet = new HashMap<String, List<Map<String, String>>>();

    private String attrName = "";

    private boolean resultSetFlag = false;

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if ("result".equals(qName) && resultSetFlag == false) {
            result.put(attributes.getQName(0), attributes.getValue(0));
        } else if (resultSetFlag) {
            Map<String, String> innerMap = new HashMap<String, String>();
            for (int i = 0, length = attributes.getLength(); i < length; ++i) {
                innerMap.put(attributes.getQName(i), attributes.getValue(i));
            }
            resultSet.get(attrName).add(innerMap);
        } else if ("resultSet".equals(qName)) {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            String name = attributes.getValue(0);
            attrName = name;
            resultSetFlag = true;
            List<Map<String, String>> innerResult = new ArrayList<Map<String, String>>();
            resultSet.put(name, innerResult);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("resultSet")) {
            resultSetFlag = false;
        }

    }

    public RespondXml getRespond() {
        respond.setResult(result);
        respond.setResultSet(resultSet);
        return respond;
    }

}
