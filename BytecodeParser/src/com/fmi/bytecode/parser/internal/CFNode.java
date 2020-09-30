// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFNode.java

package com.fmi.bytecode.parser.internal;


public abstract class CFNode
{

    CFNode()
    {
    }

    public abstract CFNode getParentNode();

    public abstract CFNode[] getChildNodes();

    public final int getOffsetStart()
    {
        return offsetStart;
    }

    public final int getOffsetEnd()
    {
        return offsetEnd;
    }

    void setOffsetStartAndEnd(int offsetStart, int offsetEnd)
    {
        this.offsetStart = offsetStart;
        this.offsetEnd = offsetEnd;
    }

    private int offsetStart;
    private int offsetEnd;
}
