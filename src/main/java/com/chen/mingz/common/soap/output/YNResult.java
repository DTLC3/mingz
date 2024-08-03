package com.chen.mingz.common.soap.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by chenmingzhi on 17/9/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YNResult {

    public YNResult() {

    }

    public YNResult(String one, String two) {
        this.one = one;
        this.two = two;
    }

    @XmlAttribute(name = "one")
    private String one;

    @XmlAttribute(name = "two")
    private String two;

    public String getOne() {
        return one;
    }

    public void setOne(String one) {
        this.one = one;
    }

    public String getTwo() {
        return two;
    }

    public void setTwo(String two) {
        this.two = two;
    }

    @Override
    public String toString() {
        return "YNResult{" +
                "one='" + one + '\'' +
                ", two='" + two + '\'' +
                '}';
    }
}
