package com.chen.mingz.common.soap.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YRBusiness {

    public YRBusiness() {

    }

    public YRBusiness(YRNestBusiness nestBusiness) {
        this.nestBusiness = nestBusiness;
    }

    @XmlElement(name = "business", namespace = "http://www.molss.gov.cn/")
    private YRNestBusiness nestBusiness;

    public YRNestBusiness getNestBusiness() {
        return nestBusiness;
    }

    public void setNestBusiness(YRNestBusiness nestBusiness) {
        this.nestBusiness = nestBusiness;
    }


}
