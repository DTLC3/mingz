package com.chen.mingz.common.soap.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YRBody {

    public YRBody() {

    }

    public YRBody(YRBusiness business) {
        this.business = business;
    }

    @XmlElement(name = "business", namespace = "http://www.molss.gov.cn/")
    private YRBusiness business;

    public YRBusiness getBusiness() {
        return business;
    }

    public void setBusiness(YRBusiness business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "YRBody{" +
                "business=" + business +
                '}';
    }
}
