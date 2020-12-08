package com.github.vizaizai.scholar.infrastructure.util.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liaochongwei
 * @date 2020/9/14 10:30
 */
public class TreeNode<I> {
    protected I id;
    protected I parentId;
    protected List<TreeNode<I>> children;

    public TreeNode() {
    }

    public TreeNode(I id, I parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public void addChild(TreeNode<I> node) {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        this.children.add(node);
    }

    public I getId() {
        return id;
    }

    public void setId(I id) {
        this.id = id;
    }

    public I getParentId() {
        return parentId;
    }

    public void setParentId(I parentId) {
        this.parentId = parentId;
    }

    public List<TreeNode<I>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<I>> children) {
        this.children = children;
    }
}
