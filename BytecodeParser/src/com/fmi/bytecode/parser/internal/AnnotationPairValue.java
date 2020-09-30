// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationPairValue.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AnnotationStruct, CPInfo

public class AnnotationPairValue
{

    public AnnotationPairValue(int tag)
    {
        this.tag = tag;
    }

    public void setConstant(CPInfo constant)
    {
        this.constant = constant;
    }

    public void setNested(AnnotationStruct nested)
    {
        this.nested = nested;
    }

    public CPInfo getConstant()
    {
        return constant;
    }

    public AnnotationStruct getNested()
    {
        return nested;
    }

    public CPInfo getEnumTypeAndName()
    {
        return enumTypeAndName;
    }

    public void setEnumTypeAndName(CPInfo enumTypeAndName)
    {
        this.enumTypeAndName = enumTypeAndName;
    }

    public AnnotationPairValue[] getArray()
    {
        return array;
    }

    public void setArray(AnnotationPairValue array[])
    {
        this.array = array;
    }

    public int getTag()
    {
        return tag;
    }

    public int getLenght()
    {
        if(enumTypeAndName != null)
            return 5;
        if(constant != null)
            return 3;
        if(nested != null)
            return nested.getLenght();
        if(array != null)
        {
            int l = 3;
            int valuesCount = array.length;
            for(int i = 0; i < valuesCount; i++)
                l += array[i].getLenght();

            return l;
        } else
        {
            throw new RuntimeException("Incorrect annotation pair value!");
        }
    }

    public String toString()
    {
        if(constant != null)
            return constant.toPlainString();
        if(nested != null)
            return "nested annotation";
        if(array != null)
            return "array";
        else
            return (new StringBuilder("null: ")).append(super.toString()).toString();
    }

    public CPInfo getNameIdx()
    {
        return nameIdx;
    }

    public void setNameIdx(CPInfo nameIdx)
    {
        this.nameIdx = nameIdx;
    }

    private int tag;
    private CPInfo constant;
    private CPInfo enumTypeAndName;
    private AnnotationStruct nested;
    private AnnotationPairValue array[];
    private CPInfo nameIdx;
}
