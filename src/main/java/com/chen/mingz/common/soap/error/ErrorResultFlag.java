package com.chen.mingz.common.soap.error;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ErrorResultFlag {

    public ErrorResultFlag() {

    }

    public ErrorResultFlag(String flag) {
        this.flag = flag;
    }

    @XmlAttribute(name = "flag")
    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

}
