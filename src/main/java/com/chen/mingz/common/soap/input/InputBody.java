package com.chen.mingz.common.soap.input;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputBody implements Serializable {

    private static final long serialVersionUID = 2968015478228907562L;

    private List<InputPara> para = new ArrayList<>();

    private Map<String, String> paraMap = new HashMap<String, String>();

    private List<InputParaList> paraList = new ArrayList<>();

    private Map<String, InputParaList> listMap = new HashMap<>();

    public List<InputPara> getPara() {
        return para;
    }

    public void setPara(List<InputPara> para) {
        this.para = para;
    }

    public List<InputParaList> getParaList() {
        return paraList;
    }

    public void setParaList(List<InputParaList> paraList) {
        this.paraList = paraList;
    }

    public Map<String, String> getParaMap() {
        return paraMap;
    }

    public void setParaMap(Map<String, String> paraMap) {
        this.paraMap = paraMap;
    }

    public Map<String, InputParaList> getListMap() {
        return listMap;
    }

    public void setListMap(Map<String, InputParaList> listMap) {
        this.listMap = listMap;
    }

    @Override
    public String toString() {
        return "InputBody [para=" + para + ", paraMap=" + paraMap + ", paraList=" + paraList + ", listMap=" + listMap
                + "]";
    }


}
