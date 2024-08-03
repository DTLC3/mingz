package com.chen.mingz.common.soap.output;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RespondXml {

    Map<String, String> result = new HashMap<String, String>();

    Map<String, List<Map<String, String>>> resultSet = new HashMap<String, List<Map<String, String>>>();

    public Map<String, String> getResult() {
        return result;
    }

    public void setResult(Map<String, String> result) {
        this.result = result;
    }

    public Map<String, List<Map<String, String>>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(Map<String, List<Map<String, String>>> resultSet) {
        this.resultSet = resultSet;
    }

    @Override
    public String toString() {
        return "RespondXml [result=" + result + ", resultSet=" + resultSet + "]";
    }


}
