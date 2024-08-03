package com.chen.mingz.common.soap.transform;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "misweb-config")
@XmlType
public class TransformXml {

    @XmlElement(name = "in-head")
    private XmlHead inHead;

    @XmlElement(name = "in-body")
    private XmlBody inBody;

    @XmlElement(name = "out-head")
    private XmlHead outHead;

    @XmlElement(name = "out-body")
    private XmlBody outBody;
	
    @XmlTransient
    private boolean inSame = true;

    @XmlTransient
    private boolean outSame = true;

    public XmlHead getInHead() {
        return inHead;
    }

    public XmlBody getInBody() {
        return inBody;
    }

    public void setInBody(XmlBody inBody) {
        this.inBody = inBody;
    }

    public XmlHead getOutHead() {
        return outHead;
    }

    public void setOutHead(XmlHead outHead) {
        this.outHead = outHead;
    }

    public XmlBody getOutBody() {
        return outBody;
    }

    public void setOutBody(XmlBody outBody) {
        this.outBody = outBody;
    }

    public void setInHead(XmlHead inHead) {
        this.inHead = inHead;
    }

    public boolean isInSame() {
        return inSame;
    }

    public void setInSame(boolean inSame) {
        this.inSame = inSame;
    }

    public boolean isOutSame() {
        return outSame;
    }

    public void setOutSame(boolean outSame) {
        this.outSame = outSame;
    }

    @Override
    public String toString() {
        return "TransformXml [inHead=" + inHead + ", inBody=" + inBody + ", outHead=" + outHead + ", outBody=" + outBody
                + ", inSame=" + inSame + ", outSame=" + outSame + "]";
    }


}
