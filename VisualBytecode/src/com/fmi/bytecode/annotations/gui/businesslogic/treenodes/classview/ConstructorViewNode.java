package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import com.fmi.bytecode.annotations.element.ConstructorInfo;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.MutableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;

public class ConstructorViewNode extends MethodViewNode {
       
    public ConstructorViewNode(TreeNodeBaseImpl parent, ConstructorInfo constructorInfo) {
        super(parent, constructorInfo);
    }

    public ConstructorInfo getConstructorInfo() {
        return (ConstructorInfo) getElementInfo();
    }

    public ConstructorInfo getNodeIdentifier() {
        return getConstructorInfo() ;
    }
}
