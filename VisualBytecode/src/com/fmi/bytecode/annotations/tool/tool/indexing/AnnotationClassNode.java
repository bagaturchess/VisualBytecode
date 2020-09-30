// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationClassNode.java

package com.fmi.bytecode.annotations.tool.tool.indexing;

import com.fmi.bytecode.annotations.tool.element.ClassInfo;

// Referenced classes of package com.fmi.bytecode.annotations.tool.tool.indexing:
//            Node, AnnotationPackageNode

public class AnnotationClassNode extends Node
{

    AnnotationClassNode(ClassInfo _classInfo, AnnotationPackageNode _parent, String _name)
    {
        super(_parent, _name);
        if(getParentPackage() != null)
            getParentPackage().addClassChild(this);
        classInfo = _classInfo;
    }

    public ClassInfo getClassInfo()
    {
        return classInfo;
    }

    private ClassInfo classInfo;
}
