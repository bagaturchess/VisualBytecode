package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import java.util.Map;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.MutableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.StaticTextNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.MethodInfo;

public class MethodViewNode extends AnnotatedViewNode {

    public MethodViewNode(TreeNodeBaseImpl parent, MethodInfo methodInfo) {
        super(parent, methodInfo);
        initSubTree();
    }
    
    public MethodInfo getMethodInfo() {
        return (MethodInfo) getElementInfo();
    }
    
    public void initSubTree() {
        createMethodName();
        
        MethodInfo methodInfo = getMethodInfo();
        String[] params = methodInfo.getParameters();
        Map<String, AnnotationRecord>[] parameterAnnotations 
                                         = methodInfo.getParameterAnnotations();
                                         
        StaticTextNode rootParamNode = null;
        if (parameterAnnotations != null && parameterAnnotations.length > 0) {
            rootParamNode = new StaticTextNode(this, StaticTextNode.PARAMETERS_NODE);
        }
        

        for (int i = 0; i < params.length; i++) {
            String paramName = params[i];
            if (parameterAnnotations != null && parameterAnnotations.length > i) {
                Map<String, AnnotationRecord> paramAnnot = parameterAnnotations[i];
                new ParameterViewNode(rootParamNode, "Parameter" + (i + 1) + ": " + paramName, paramAnnot);
            }
        }       
    }

    private void createMethodName() {
        String methodName = "";
        MethodInfo methodInfo = getMethodInfo();
        methodName = methodInfo.getName();
        methodName += "(";
        String[] params = methodInfo.getParameters();
        for (int i = 0; i < params.length; i++) {
            methodName += params[i];
            if (i != params.length - 1) {
                methodName += ", "; 
            } 
        }
        methodName += ")";
        setNodeName(methodName);
    }

    public MethodInfo getNodeIdentifier() {
        return getMethodInfo();
    }
    
   
}
