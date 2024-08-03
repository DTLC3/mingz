package com.chen.mingz.common.soap.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class InputParaList implements Serializable {

    private static final long serialVersionUID = -7811058576519347119L;

    private String name;


    List<InputRow> list = new ArrayList<InputRow>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InputRow> getList() {
        return list;
    }

    public void setList(List<InputRow> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "InputParaList [name=" + name + ", list=" + list + "]";
    }


}
