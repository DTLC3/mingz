package com.chen.mingz.office.basic;

import com.chen.mingz.common.tree.annotation.TreeCore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenmingzhi
 * @date 2019-10-21
 */

@Data
@TreeCore(id = "id", parentId = "parent")
public class ExcelTitleNode implements Serializable {

    private static final long serialVersionUID = 60323393L;

    private String id;

    private String name;

    private String parent;

    private Integer order;

}
