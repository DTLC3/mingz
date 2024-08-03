package com.chen.mingz.common.soap.output;


import com.chen.mingz.common.soap.error.ErrorResultCause;
import com.chen.mingz.common.soap.error.ErrorResultFlag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YRNestBusiness {

    public YRNestBusiness() {

    }

    public YRNestBusiness(ErrorResultCause cause, ErrorResultFlag flag) {
        this.cause = cause;
        this.flag = flag;
    }

    @XmlElement(name = "result")
    private ErrorResultCause cause;

    @XmlElement(name = "result")
    private ErrorResultFlag flag;

    public ErrorResultCause getCause() {
        return cause;
    }

    public void setCause(ErrorResultCause cause) {
        this.cause = cause;
    }

    public ErrorResultFlag getFlag() {
        return flag;
    }

    public void setFlag(ErrorResultFlag flag) {
        this.flag = flag;
    }


}
