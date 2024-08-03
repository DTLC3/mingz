package com.chen.mingz.common.soap.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmingzhi on 17/9/19.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
public class YBNNestBusiness {

    public YBNNestBusiness() {
        listResult = new ArrayList<YNResult>();
        listResultSet = new ArrayList<YNResultSet>();
    }

    public YBNNestBusiness(List<YNResult> listResult, List<YNResultSet> listResultSet) {
        this.listResult = listResult;
        this.listResultSet = listResultSet;
    }

    @XmlElement(name = "result")
    private List<YNResult> listResult;

    @XmlElement(name = "resultSet")
    private List<YNResultSet> listResultSet;

    public List<YNResult> getListResult() {
        return listResult;
    }

    public void setListResult(List<YNResult> listResult) {
        this.listResult = listResult;
    }

    public List<YNResultSet> getListResultSet() {
        return listResultSet;
    }

    public void setListResultSet(List<YNResultSet> listResultSet) {
        this.listResultSet = listResultSet;
    }

    @Override
    public String toString() {
        return "YBNNestBusiness{" +
                "listResult=" + listResult +
                ", listResultSet=" + listResultSet +
                '}';
    }
}
