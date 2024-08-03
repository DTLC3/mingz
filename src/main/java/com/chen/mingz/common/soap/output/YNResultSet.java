package com.chen.mingz.common.soap.output;


import com.chen.mingz.common.soap.input.ListRows;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YNResultSet {

    public YNResultSet() {

    }

    public YNResultSet(String name) {
        this.name = name;
    }

    public YNResultSet(String name, List<ListRows> list) {
        this.name = name;
        this.list = list;
    }

    @XmlAttribute(name = "name")
    private String name;

    @XmlElement(name = "result")
    private List<ListRows> list = new ArrayList<ListRows>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ListRows> getList() {
        return list;
    }

    public void setList(List<ListRows> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "YNResultSet{" +
                "name='" + name + '\'' +
                ", list=" + list +
                '}';
    }
}