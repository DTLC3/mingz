package com.chen.mingz.common.soap.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ListRows {
    public ListRows() {

    }

    public ListRows(String keys, String values) {
        this.keys = keys;
        this.values = values;
    }

    @XmlAttribute(name = "keys")
    private String keys;

    @XmlAttribute(name = "values")
    private String values;

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }


}
