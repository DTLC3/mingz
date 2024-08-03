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
public class YBNBody {

    public YBNBody() {
        business = new YBNBusiness();
    }

    public YBNBody(YBNBusiness business) {
        this.business = business;
    }

    @XmlElement(name = "business", namespace = "http://www.molss.gov.cn/")
    private YBNBusiness business;

    public YBNBusiness getBusiness() {
        return business;
    }

    public void setBusiness(YBNBusiness business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "YBNBody{" +
                "business=" + business +
                '}';
    }
}
