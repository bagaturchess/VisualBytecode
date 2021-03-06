package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.MutableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.FieldInfo;

public class FieldViewNode extends AnnotatedViewNode {
        
    public FieldViewNode(TreeNodeBaseImpl parent, FieldInfo fieldInfo) {
        super(parent,
            fieldInfo.getName() + " (type: " + fieldInfo.getType() + ")",
            fieldInfo);
    }

    public FieldInfo getFieldInfo() {
        return (FieldInfo) getElementInfo();
    }

    public FieldInfo getNodeIdentifier() {
        return getFieldInfo();
    }
}
