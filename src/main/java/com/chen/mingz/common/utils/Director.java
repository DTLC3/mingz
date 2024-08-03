package com.chen.mingz.common.utils;


import com.chen.mingz.common.tree.annotation.TreeCore;
import com.chen.mingz.common.tree.annotation.TreeLeaf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenmingzhi on 10/11/18.
 */

@TreeCore(id = "namePath", parentId = "parentPath")
public class Director {

    @TreeLeaf(name = "name")
    private String name;

    @TreeLeaf(name = "namePath")
    private String namePath;

    @TreeLeaf(name = "parent")
    private String parent;

    @TreeLeaf(name = "parentPath")
    private String parentPath;

    @TreeLeaf(name = "folder")
    private boolean folder = false;

    @TreeLeaf(serialize = false)
    private List<Director> child = new ArrayList<>();

    public Director() {
    }

    public Director(String name, String namePath) {
        this.name = name;
        this.namePath = namePath;
    }

    public Director(String name, String namePath, boolean folder) {
        this.name = name;
        this.namePath = namePath;
        this.folder = folder;
    }

    public Director(String name, String namePath, String parent, String parentPath) {
        this.name = name;
        this.namePath = namePath;
        this.parent = parent;
        this.parentPath = parentPath;
    }

    public Director(String name, String namePath, String parent, String parentPath, boolean folder) {
        this.name = name;
        this.namePath = namePath;
        this.parent = parent;
        this.parentPath = parentPath;
        this.folder = folder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public List<Director> getChild() {
        return child;
    }

    public void setChild(List<Director> child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return "Director{" +
                "name='" + name + '\'' +
                ", namePath='" + namePath + '\'' +
                ", parent='" + parent + '\'' +
                ", parentPath='" + parentPath + '\'' +
                ", folder=" + folder +
                ", child=" + child +
                '}';
    }
}
