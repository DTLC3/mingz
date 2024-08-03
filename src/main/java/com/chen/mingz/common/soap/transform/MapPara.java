package com.chen.mingz.common.soap.transform;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType//(propOrder = { "new", "old", "name", "require", "def" })
public class MapPara {

    @XmlAttribute(name = "new")
    private String now;
    @XmlAttribute(name = "old")
    private String old;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "require")
    private String require;
    @XmlAttribute(name = "def")
    private String def;


    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MapPara [now=" + now + ", old=" + old + ", require=" + require + ", name=" + name + ", def=" + def
                + "]";
    }


}
