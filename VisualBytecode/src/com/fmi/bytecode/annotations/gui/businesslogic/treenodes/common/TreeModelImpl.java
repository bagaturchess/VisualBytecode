package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class TreeModelImpl implements TreeModel {
    
    private TreeNode root;
    
    public TreeModelImpl(TreeNode root) {
        this.root = root;
    }

    public Object getRoot() {
        return root;
    }

    public Object getChild(Object parent, int index) {
        return ((TreeNode) parent).getChildAt(index);
    }

    public int getChildCount(Object parent) {
        return ((TreeNode) parent).getChildCount();
    }

    public boolean isLeaf(Object node) {
        return ((TreeNode) node).isLeaf();
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((TreeNode) parent).getIndex((TreeNode) child);
    }

    public void addTreeModelListener(TreeModelListener l) {
    }

    public void removeTreeModelListener(TreeModelListener l) {
    }
    
    public void valueForPathChanged(TreePath path, Object newValue) {
    }
}
