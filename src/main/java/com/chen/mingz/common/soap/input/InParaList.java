package com.chen.mingz.common.soap.input;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class InParaList {

    @XmlAttribute(name = "name")
    private String name;


    @XmlElement(name = "row")
    private List<ListRows> list = new ArrayList<>();

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


}
