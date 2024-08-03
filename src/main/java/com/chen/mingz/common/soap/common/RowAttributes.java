package com.chen.mingz.common.soap.common;

import java.util.ArrayList;
import java.util.List;

public class RowAttributes extends org.xml.sax.helpers.AttributesImpl {

    private List<String> keys = new ArrayList<String>();

    private List<String> values = new ArrayList<String>();

    public RowAttributes(List<String> keys, List<String> values) {
        this.keys = keys;
        this.values = values;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public int getLength() {
        return keys.size();
    }

    @Override
    public String getURI(int index) {
        return super.getURI(0);
    }

    @Override
    public String getLocalName(int index) {
        return keys.get(index);
    }

    @Override
    public String getQName(int index) {
        return keys.get(index);
    }

    @Override
    public String getType(int index) {
        return "CDATA";
    }

    @Override
    public String getValue(int index) {
        String value = values.get(index);
        if (value.equals("<br>")) {
            value = "";
        }
        return value;
    }

    @Override
    public int getIndex(String uri, String localName) {
        return 0;
    }

    @Override
    public int getIndex(String qName) {
        return 0;
    }

    @Override
    public String getType(String uri, String localName) {
        return "CDATA";
    }

    @Override
    public String getType(String qName) {
        return "CDATA";
    }

    @Override
    public String getValue(String uri, String localName) {
        return "";
    }

    @Override
    public String getValue(String qName) {
        return "";
    }
}
