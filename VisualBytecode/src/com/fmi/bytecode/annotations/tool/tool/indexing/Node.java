// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Node.java

package com.fmi.bytecode.annotations.tool.tool.indexing;


// Referenced classes of package com.fmi.bytecode.annotations.tool.tool.indexing:
//            AnnotationPackageNode

class Node
{

    Node(AnnotationPackageNode _parent, String _name)
    {
        parent = _parent;
        name = _name;
    }

    public AnnotationPackageNode getParentPackage()
    {
        return parent;
    }

    public String getName()
    {
        return name;
    }

    private AnnotationPackageNode parent;
    private String name;
}
