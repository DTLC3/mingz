package com.chen.mingz.office.basic;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenmingzhi
 * @date 2019-10-21
 */

@Data
public class ExcelTitle implements Serializable {


    private static final long serialVersionUID = 323393L;

    private String name;

    private List<ExcelTitle> child;

}
