package com.chen.mingz.common.soap.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by chenmingzhi on 17/9/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YBNBusiness {

    public YBNBusiness() {
        nestBusiness = new YBNNestBusiness();
    }

    public YBNBusiness(YBNNestBusiness nestBusiness) {
        this.nestBusiness = nestBusiness;
    }

    @XmlElement(name = "business", namespace = "http://www.molss.gov.cn/")
    private YBNNestBusiness nestBusiness;

    public YBNNestBusiness getNestBusiness() {
        return nestBusiness;
    }

    public void setNestBusiness(YBNNestBusiness nestBusiness) {
        this.nestBusiness = nestBusiness;
    }

    @Override
    public String toString() {
        return "YBNBusiness{" +
                "nestBusiness=" + nestBusiness +
                '}';
    }
}
