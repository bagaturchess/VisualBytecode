// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnclosingMethodAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CPInfo

public class EnclosingMethodAttribute extends AttributeInfo
{

    public EnclosingMethodAttribute(CPInfo enclosingClass, CPInfo enclosingMethod)
    {
        this.enclosingClass = enclosingClass;
        this.enclosingMethod = enclosingMethod;
    }

    public int getAttributeType()
    {
        return 16;
    }

    public String getName()
    {
        return "EnclosingMethod";
    }

    public CPInfo getEnclosingClass()
    {
        return enclosingClass;
    }

    public CPInfo getEnclosingMethod()
    {
        return enclosingMethod;
    }

    private CPInfo enclosingClass;
    private CPInfo enclosingMethod;
}
