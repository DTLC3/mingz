package com.chen.mingz.common.tree;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 18/5/24.
 */
@Data
public class TreeChildren {


    private Map<String, Object> node;

    private List<TreeChildren> children = new ArrayList<>();

    public TreeChildren(Map<String, Object> node) {
        this.node = node;
    }

    public void addChildren(TreeChildren child) {
        this.children.add(child);
    }

}
