// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationDefaultAttribute.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, AnnotationPairValue

public class AnnotationDefaultAttribute extends AttributeInfo
{

    public AnnotationDefaultAttribute(AnnotationPairValue value)
    {
        this.value = value;
    }

    public String getName()
    {
        return "AnnotationDefault";
    }

    public int getAttributeType()
    {
        return 14;
    }

    public AnnotationPairValue getValue()
    {
        return value;
    }

    private AnnotationPairValue value;
}
