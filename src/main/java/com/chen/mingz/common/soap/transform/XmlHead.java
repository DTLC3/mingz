package com.chen.mingz.common.soap.transform;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 17/9/20.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class XmlHead {


    @XmlElement(name = "map")
    private List<MapPara> inHead = new ArrayList<MapPara>();

    @XmlTransient
    private Map<String, String> inHeadMap = new HashMap<String, String>();

    @XmlTransient
    private Map<String, String> inRequire = new HashMap<String, String>();

    public List<MapPara> getInHead() {
        return inHead;
    }

    public void setInHead(List<MapPara> inHead) {
        this.inHead = inHead;
    }

    public Map<String, String> getInHeadMap() {
        return inHeadMap;
    }

    public void setInHeadMap(Map<String, String> inHeadMap) {
        this.inHeadMap = inHeadMap;
    }

    public Map<String, String> getInRequire() {
        return inRequire;
    }

    public void setInRequire(Map<String, String> inRequire) {
        this.inRequire = inRequire;
    }

    @Override
    public String toString() {
        return "XmlHead [inHead=" + inHead + ", inHeadMap=" + inHeadMap + ", inRequire=" + inRequire + "]";
    }







}