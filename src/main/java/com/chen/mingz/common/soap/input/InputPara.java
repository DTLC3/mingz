package com.chen.mingz.common.soap.input;

import java.io.Serializable;

public class InputPara implements Serializable {

    private static final long serialVersionUID = 6266819779466150610L;

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
