package com.chen.mingz.common.basic.model;

import lombok.Data;

import java.util.List;

/**
 * @author chenmingzhi
 * @date 2019-08-12
 */
@Data
public class OrMaybe {

    private String key;

    private List<String> values;

    private String query = "like";

}
