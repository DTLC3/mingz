package com.chen.mingz.common.soap.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class InBusiness {

    @XmlElement(name = "para")
    private List<InPara> list = new ArrayList<>();

    @XmlElement(name = "paralist")
    private List<InParaList> paraList = new ArrayList<>();

    public List<InPara> getList() {
        return list;
    }

    public void setList(List<InPara> list) {
        this.list = list;
    }

    public List<InParaList> getParaList() {
        return paraList;
    }

    public void setParaList(List<InParaList> paraList) {
        this.paraList = paraList;
    }


}
