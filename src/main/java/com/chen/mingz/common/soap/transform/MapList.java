package com.chen.mingz.common.soap.transform;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType//(propOrder = { "name","require", "maplist"})
public class MapList {

    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "require")
    private String require;

    @XmlElement(name = "maplist")
    private MapList child;

    @XmlElement(name = "map")
    private List<MapPara> maplist = new ArrayList<MapPara>();

    @XmlTransient
    private Map<String, String> mapMap = new HashMap<String, String>();

    @XmlTransient
    private Map<String, String> mapRequire = new HashMap<String, String>();


    public Map<String, String> getMapRequire() {
        return mapRequire;
    }

    public void setMapRequire(Map<String, String> mapRequire) {
        this.mapRequire = mapRequire;
    }

    public Map<String, String> getMapMap() {
        return mapMap;
    }

    public void setMapMap(Map<String, String> mapMap) {
        this.mapMap = mapMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public List<MapPara> getMaplist() {
        return maplist;
    }

    public void setMaplist(List<MapPara> maplist) {
        this.maplist = maplist;
    }

    public MapList getChild() {
        return child;
    }

    public void setChild(MapList child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "MapList [name=" + name + ", require=" + require + ", child=" + child + ", maplist=" + maplist
                + ", mapMap=" + mapMap + "]";
    }

}
