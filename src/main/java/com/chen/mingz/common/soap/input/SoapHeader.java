package com.chen.mingz.common.soap.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class SoapHeader {

    @XmlElement(name = "system", namespace = "http://www.molss.gov.cn/")
    private InSystem system;

    public InSystem getSystem() {
        return system;
    }

    public void setSystem(InSystem system) {
        this.system = system;
    }


}
