package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common;

public abstract class LeafNode extends TreeNodeBaseImpl {
    
    public LeafNode(TreeNodeBaseImpl parent, String name) {
        super(parent, name);
    }

    public boolean getAllowsChildren() {
        return false;
    }

    public boolean isLeaf() {
        return true;
    }
}
