package com.chen.mingz.common.basic.model;

import lombok.Data;

/**
 * Created by chenmingzhi on 18/5/30.
 */
@Data
public class Between {

    public Between(String first, String second) {
        this.first = first;
        this.second = second;
    }

    private String first;

    private String second;


}
