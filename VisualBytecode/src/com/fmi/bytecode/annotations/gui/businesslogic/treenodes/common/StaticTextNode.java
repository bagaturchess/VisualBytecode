package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common;

//roots
public class StaticTextNode extends ElementNode {
    
    public static final String CLASSES_NODE = "Classes";
    public static final String PARAMETERS_NODE = "Parameters";
    public static final String CONSTRUCTORS_NODE = "Constructors";
    public static final String METHODS_NODE = "Methods";
    public static final String FIELDS_NODE = "Fields";
    public static final String ANNOTATIONS_NODE = "Annotations";
    
    public StaticTextNode(TreeNodeBaseImpl parent, String name) {
        super(parent, name);
    }

    public String getNodeIdentifier() {
        return getNodeName();
    }
}
