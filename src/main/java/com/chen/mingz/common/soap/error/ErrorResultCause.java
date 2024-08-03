package com.chen.mingz.common.soap.error;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class ErrorResultCause {

    public ErrorResultCause() {

    }

    public ErrorResultCause(String cause) {
        this.cause = cause;
    }

    @XmlAttribute(name = "cause")
    private String cause;

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }


}
