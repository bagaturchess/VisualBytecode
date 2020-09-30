package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import java.util.Map;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

public class ParameterViewNode extends AnnotatedViewNode {
    
    public ParameterViewNode(TreeNodeBaseImpl parent, String name,
                             Map<String, AnnotationRecord> annotations) {
        super(parent, name, annotations);
    }
    
    public Object getNodeIdentifier() {
        return getNodeName();
    }
}
