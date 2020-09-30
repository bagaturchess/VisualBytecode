package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.LeafNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.NamedMember;
import com.fmi.bytecode.annotations.tool.element.impl.AnnotationNamedMember;

public class AnnotationAttributeViewNode extends LeafNode {
    
    private NamedMember annotationNamedMember;
    
    public AnnotationAttributeViewNode(TreeNodeBaseImpl parent, String name,
                                       NamedMember annotationNamedMember) {
        super(parent, name);
        this.annotationNamedMember = annotationNamedMember;
    }

    public NamedMember getAnnotationNamedMember() {
        return annotationNamedMember;
    }


    public NamedMember getNodeIdentifier() {
        return annotationNamedMember;
    }
}
