package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import java.util.List;

import javax.swing.tree.TreeNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

public abstract class TreeNodeBaseImpl implements TreeNode {

    private TreeNode parent;
    private List<TreeNode> children;
    private String nodeName;
    private ProjectModel projectModel;
        
    public TreeNodeBaseImpl(TreeNodeBaseImpl parent, String name) {
        this.parent = parent;
        children = new ArrayList<TreeNode>();
        nodeName = name;
        if (parent != null) {
            parent.addChild(this);
            this.setProjectModel(parent.getProjectModel());
        }    
    }
    
    protected TreeNodeBaseImpl(TreeNodeBaseImpl parent, String name, 
                                                    ProjectModel projectMolel) {
        this(parent, name);
        this.setProjectModel(projectMolel);
    }
        
    public abstract boolean getAllowsChildren();
    
    public abstract boolean isLeaf();
    
    public abstract Object getNodeIdentifier(); // data object containig unique inf about node
    
    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    public Enumeration children() {
        return Collections.enumeration(children);
    }

    public String getNodeName() {
        return nodeName;
    }

    protected void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }
    
    private void addChild(TreeNode child) {
        children.add(child);
    }
    
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof TreeNodeBaseImpl) {
            TreeNodeBaseImpl node = (TreeNodeBaseImpl) obj;
            if (node.getNodeName().equals(getNodeName())) {
                result = true;
            }
        }
        return result;
    }
    
    public int hashCode() {
        return getNodeName().hashCode();
    }
    
    public String toString() {
        return this.getNodeName();
    }
    
    public void removeChild(Object nodeIdentifier) {
        for (int i = 0; i < children.size(); i++) {
            TreeNodeBaseImpl currChild = (TreeNodeBaseImpl) children.get(i);
            if (currChild.getNodeIdentifier().equals(nodeIdentifier)) {
                children.remove(currChild);
                break;
            }
        }
    }
    
    public void removeChildren() {
        children.clear();
    }

    private void setProjectModel(ProjectModel projectModel) {
        this.projectModel = projectModel;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }
}
