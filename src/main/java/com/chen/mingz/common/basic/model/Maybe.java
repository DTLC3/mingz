package com.chen.mingz.common.basic.model;

import lombok.Data;

import java.util.List;

/**
 * @author chenmingzhi
 * @date 2019-07-23
 */

@Data
public class Maybe {

    private List<String> keys;

    private String value;

    private String query = "query";

}
