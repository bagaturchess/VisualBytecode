package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common;

public abstract class MutableNode extends TreeNodeBaseImpl {

    public MutableNode(TreeNodeBaseImpl parent, String name) {
        super(parent, name);
    }
    
    public boolean getAllowsChildren() {
        return !isLeaf();
    }

    public boolean isLeaf() {
        return getChildCount() == 0;
    }
}
