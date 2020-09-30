// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationPackageNode.java

package com.fmi.bytecode.annotations.tool.tool.indexing;

import java.util.*;

// Referenced classes of package com.fmi.bytecode.annotations.tool.tool.indexing:
//            Node, AnnotationClassNode

public class AnnotationPackageNode extends Node
{

    AnnotationPackageNode(AnnotationPackageNode _parent, String _name)
    {
        super(_parent, _name);
        subPackages = new ArrayList();
        classes = new ArrayList();
        subPackagesByName = new HashMap();
        if(getParentPackage() != null)
            getParentPackage().addPackageChild(this);
    }

    public List getSubPackages()
    {
        return subPackages;
    }

    public AnnotationPackageNode getSubPackageByName(String name)
    {
        return (AnnotationPackageNode)subPackagesByName.get(name);
    }

    public List getClasses()
    {
        return classes;
    }

    void addPackageChild(AnnotationPackageNode annotationPackageNode)
    {
        subPackages.add(annotationPackageNode);
        subPackagesByName.put(annotationPackageNode.getName(), annotationPackageNode);
    }

    void addClassChild(AnnotationClassNode annotationClassNode)
    {
        classes.add(annotationClassNode);
    }

    private Map subPackagesByName;
    private List subPackages;
    private List classes;
}
