// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConstantValueAttribute.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CPInfo

public final class ConstantValueAttribute extends AttributeInfo
{

    ConstantValueAttribute()
    {
    }

    public CPInfo getConstantValue()
    {
        return constantValue;
    }

    void setConstantValue(CPInfo constantValue)
    {
        this.constantValue = constantValue;
    }

    public String getName()
    {
        return "ConstantValue";
    }

    private CPInfo constantValue;
}
