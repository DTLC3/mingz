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
public class XmlBody {

    @XmlElement(name = "map")
    private List<MapPara> paraList = new ArrayList<MapPara>();

    @XmlElement(name = "maplist")
    private List<MapList> mapList = new ArrayList<MapList>();

    @XmlTransient
    private Map<String, String> paraMap = new HashMap<String, String>();
    @XmlTransient
    private Map<String, String> paraRequire = new HashMap<String, String>();
    @XmlTransient
    private Map<String, MapList> mapMap = new HashMap<String, MapList>();

    public List<MapPara> getParaList() {
        return paraList;
    }

    public void setParaList(List<MapPara> paraList) {
        this.paraList = paraList;
    }

    public Map<String, String> getParaMap() {
        return paraMap;
    }

    public void setParaMap(Map<String, String> paraMap) {
        this.paraMap = paraMap;
    }

    public List<MapList> getMapList() {
        return mapList;
    }

    public void setMapList(List<MapList> mapList) {
        this.mapList = mapList;
    }



    public Map<String, String> getParaRequire() {
        return paraRequire;
    }

    public void setParaRequire(Map<String, String> paraRequire) {
        this.paraRequire = paraRequire;
    }

    public Map<String, MapList> getMapMap() {
        return mapMap;
    }

    public void setMapMap(Map<String, MapList> mapMap) {
        this.mapMap = mapMap;
    }

    @Override
    public String toString() {
        return "XmlBody [paraList=" + paraList + ", mapList=" + mapList + ", paraMap=" + paraMap + ", paraRequire="
                + paraRequire + ", mapMap=" + mapMap + "]";
    }



}
