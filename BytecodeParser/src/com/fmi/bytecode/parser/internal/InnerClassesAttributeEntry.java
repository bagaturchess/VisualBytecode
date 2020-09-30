// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InnerClassesAttributeEntry.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, CPInfo, Constants, InnerClassesAttribute

public final class InnerClassesAttributeEntry extends CFNode
{

    InnerClassesAttributeEntry(InnerClassesAttribute owner, CPInfo innerClass, CPInfo outerClass, CPInfo innerName, int innerAccessFlags)
    {
        this.owner = owner;
        this.innerClass = innerClass;
        this.outerClass = outerClass;
        this.innerName = innerName;
        this.innerAccessFlags = innerAccessFlags;
    }

    public CPInfo getInnerClass()
    {
        return innerClass;
    }

    public CPInfo getOuterClass()
    {
        return outerClass;
    }

    public CPInfo getInnerNameCPInfo()
    {
        return innerName;
    }

    public String getInnerName()
    {
        return innerName.toPlainString();
    }

    public int getInnerAccessFlags()
    {
        return innerAccessFlags;
    }

    public CFNode getParentNode()
    {
        return owner;
    }

    public CFNode[] getChildNodes()
    {
        return Constants.CF_NODE_ARRAY_0;
    }

    private InnerClassesAttribute owner;
    private CPInfo innerClass;
    private CPInfo outerClass;
    private CPInfo innerName;
    private int innerAccessFlags;
}
