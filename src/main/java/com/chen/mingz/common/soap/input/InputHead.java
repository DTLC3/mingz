package com.chen.mingz.common.soap.input;

import java.io.Serializable;

public class InputHead implements Serializable {

    private static final long serialVersionUID = -6743026266982797117L;
    private String usr;
    private String pwd;
    private String funid;
    private String signature;
    private String channelid;
    private String channelsign;

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getFunid() {
        return funid;
    }

    public void setFunid(String funid) {
        this.funid = funid;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getChannelid() {
        return channelid;
    }

    public void setChannelid(String channelid) {
        this.channelid = channelid;
    }

    public String getChannelsign() {
        return channelsign;
    }

    public void setChannelsign(String channelsign) {
        this.channelsign = channelsign;
    }

    @Override
    public String toString() {
        return "InputHead [usr=" + usr + ", pwd=" + pwd + ", funid=" + funid + ", signature=" + signature
                + ", channelid=" + channelid + ", channelsign=" + channelsign + "]";
    }


}
