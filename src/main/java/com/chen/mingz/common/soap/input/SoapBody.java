package com.chen.mingz.common.soap.input;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class SoapBody {

    @XmlElement(name = "business", namespace = "http://www.molss.gov.cn/")
    private InBusiness business;

    public InBusiness getBusiness() {
        return business;
    }

    public void setBusiness(InBusiness business) {
        this.business = business;
    }


}
