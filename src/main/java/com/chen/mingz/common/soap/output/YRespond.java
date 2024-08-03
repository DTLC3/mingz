package com.chen.mingz.common.soap.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Created by chenmingzhi on 17/9/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class YRespond {
    public YRespond() {
        header = new YRHeader();
        body = new YBNBody();
    }

    @XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private YRHeader header;

    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private YBNBody body;

    public YRHeader getHeader() {
        return header;
    }

    public void setHeader(YRHeader header) {
        this.header = header;
    }

    public YBNBody getBody() {
        return body;
    }

    public void setBody(YBNBody body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "YRespond{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
