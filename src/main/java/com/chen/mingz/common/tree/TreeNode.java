package com.chen.mingz.common.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenmingzhi on 18/2/28.
 */
public class TreeNode implements TreeBasic {

    private String id;

    private String name;

    private Map<String, Object> attributes = new HashMap<String, Object>();

    private String parentId;

    private TreeNode parent = null;

    private List<TreeNode> children = new ArrayList<TreeNode>();

    private String checked = "false";

    public TreeNode() {

    }

    public TreeNode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    /**
     * 添加子节点
     */
    public void addChild(TreeNode TreeNode) {
        TreeNode.setParent(this);       //设置当前节点为添加节点的父节点
        children.add(TreeNode);           //添加进链表中
    }

    /**
     * 是否包含子节点
     */
    public boolean hasChildren() {
        return children.size() > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TreeNode)) return false;

        TreeNode treeNode = (TreeNode) o;

        if (id != null ? !id.equals(treeNode.id) : treeNode.id != null) return false;
        if (name != null ? !name.equals(treeNode.name) : treeNode.name != null) return false;
        if (attributes != null ? !attributes.equals(treeNode.attributes) : treeNode.attributes != null) return false;
        if (parentId != null ? !parentId.equals(treeNode.parentId) : treeNode.parentId != null) return false;
        if (parent != null ? !parent.equals(treeNode.parent) : treeNode.parent != null) return false;
        if (children != null ? !children.equals(treeNode.children) : treeNode.children != null) return false;
        return checked != null ? checked.equals(treeNode.checked) : treeNode.checked == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (checked != null ? checked.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", attributes=" + attributes +
                ", parentId='" + parentId + '\'' +
                ", parent=" + parent +
                ", children=" + children +
                ", checked='" + checked + '\'' +
                '}';
    }
}
