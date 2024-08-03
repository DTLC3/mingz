package com.chen.mingz.common.soap.error;


import com.chen.mingz.common.soap.output.YRBody;
import com.chen.mingz.common.soap.output.YRHeader;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class ErrorXml {

    public ErrorXml() {

    }

    public ErrorXml(YRHeader header, YRBody body) {
        this.header = header;
        this.body = body;
    }

    @XmlElement(name = "Header", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private YRHeader header;


    @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private YRBody body;


    public YRHeader getHeader() {
        return header;
    }


    public void setHeader(YRHeader header) {
        this.header = header;
    }


    public YRBody getBody() {
        return body;
    }


    public void setBody(YRBody body) {
        this.body = body;
    }


    @Override
    public String toString() {
        return "ErrorXml [header=" + header + ", body=" + body + "]";
    }


}
