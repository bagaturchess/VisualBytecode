// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UnknownAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CPInfo, CFFactory

public final class UnknownAttribute extends AttributeInfo
{

    UnknownAttribute(CFFactory factory, CPInfo name, byte value[])
    {
        this.factory = factory;
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name.getString();
    }

    public CPInfo getNameCPInfo()
    {
        return name;
    }

    public int getAttributeLength()
    {
        return value.length;
    }

    public byte[] getValue()
    {
        return value;
    }

    private CFFactory factory;
    private CPInfo name;
    private byte value[];
}
