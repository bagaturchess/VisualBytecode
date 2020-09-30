package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import java.util.Map;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.MutableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.StaticTextNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ElementInfo;

public abstract class AnnotatedViewNode extends MutableNode {
    
    private Map<String, AnnotationRecord> annotations;
    private ElementInfo elementInfo;
    
    public AnnotatedViewNode(TreeNodeBaseImpl parent, ElementInfo elementInfo) {
        this(parent, elementInfo.getName(), elementInfo.getAnnotations());
        this.elementInfo = elementInfo;
    }
    
    public AnnotatedViewNode(TreeNodeBaseImpl parent, String name, ElementInfo elementInfo) {
        this(parent, name, elementInfo.getAnnotations());
        this.elementInfo = elementInfo;
    }

    public AnnotatedViewNode(TreeNodeBaseImpl parent, String name,
                        Map<String, AnnotationRecord> annotations) {
        super(parent, name);
        this.annotations = annotations;
        initSubTree();
    }
    
    public ElementInfo getElementInfo() {
        return elementInfo;
    }
    
    private void initSubTree() {
        //Map<String, AnnotationRecord> annotations = elementInfo.getAnnotations();
        if (annotations != null  && annotations.size() != 0) {
            StaticTextNode rootAnnotNode = new StaticTextNode(this, StaticTextNode.ANNOTATIONS_NODE);   
            for (String annotName : annotations.keySet()) {
                new AnnotationViewNode(rootAnnotNode, annotations.get(annotName));
            }    
                                                          
        }
    }
}
