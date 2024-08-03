package com.chen.mingz.common.basic.model;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 7/10/18.
 */
@Data
public class QueryResult implements Serializable {

    private static final long serialVersionUID = 50801734869L;


    private Long total;

    private List rows;

    public QueryResult() {
        total = 0L;
        rows = new ArrayList<>();
    }

    public QueryResult(Long total, List<Map<String, Object>> rows) {
        this.total = total;
        this.rows = rows;
    }

}
