package com.chen.mingz.common.soap.common;

public class YlzAttributes extends org.xml.sax.helpers.AttributesImpl {

    //String one = atts.getValue(0);
    //String two = atts.getValue(1);
    private String attr;
    private String value;

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public YlzAttributes(String attr, String value) {
        this.attr = attr;
        this.value = value;
    }

    @Override
    public int getLength() {
        return 1;
    }

    @Override
    public String getURI(int index) {
        return super.getURI(0);
    }

    @Override
    public String getLocalName(int index) {
        return this.attr;
    }

    @Override
    public String getQName(int index) {
        return this.attr;
    }

    @Override
    public String getType(int index) {
        return "CDATA";
    }

    @Override
    public String getValue(int index) {
        return this.value;
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
        return this.value;
    }

    @Override
    public String getValue(String qName) {
        return this.value;
    }

}
