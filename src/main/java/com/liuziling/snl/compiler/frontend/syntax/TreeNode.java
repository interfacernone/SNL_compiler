package com.liuziling.snl.compiler.frontend.syntax;

public class TreeNode {
    private TreeNode[] children;
    private String value;
    private int width;
    private boolean printed;

    public TreeNode(String value) {
        this(null, value);
    }

    private TreeNode(TreeNode[] children, String value) {
        this.children = children;
        setValue(value);
    }

    boolean hasChild() {
        if (children == null) return false;
        for (TreeNode n : children)
            if (n != null)
                return true;
        return false;
    }

    boolean hasChildNotPrinted() {
        if (children == null) return false;
        for (TreeNode n : children)
            if (n != null)
                if (!n.printed)
                    return true;
        return false;
    }

    TreeNode[] getChildren() {
        return children;
    }

    public void setChildren(TreeNode... nodes) {
        this.children = nodes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        width = value.length();
    }

    int getWidth() {
        return width;
    }

    void setPrinted(boolean printed) {
        this.printed = printed;
    }

    @Override
    public String toString() {
        return "[TreeNode value=" + value + "]";
    }
}
