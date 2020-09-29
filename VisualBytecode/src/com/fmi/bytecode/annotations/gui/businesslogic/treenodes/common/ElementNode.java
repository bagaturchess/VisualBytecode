package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

public abstract class ElementNode extends TreeNodeBaseImpl {
    
    public ElementNode(TreeNodeBaseImpl parent, String name) {
        super(parent, name);
    }

    protected ElementNode(TreeNodeBaseImpl parent, String name, 
                                                   ProjectModel projectModel) {
        super(parent, name, projectModel);
    }
    
    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return false;
    }
}
