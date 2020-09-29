// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParameterAnnotationAttribute.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, AnnotationStruct

public class ParameterAnnotationAttribute extends AttributeInfo
{

    public ParameterAnnotationAttribute(String name, int numParameters)
    {
        this.name = name;
        paramAnnotations = new AnnotationStruct[numParameters][];
    }

    public boolean isVisible()
    {
        return name.equals("RuntimeVisibleParameterAnnotations");
    }

    public String getName()
    {
        return name;
    }

    public int getAttributeType()
    {
        return name.equals("RuntimeVisibleParameterAnnotations") ? 12 : 13;
    }

    public void setAnnotations(int paramIdx, AnnotationStruct annotations[])
    {
        paramAnnotations[paramIdx] = annotations;
    }

    public AnnotationStruct[][] getAllAnnotations()
    {
        return paramAnnotations;
    }

    public AnnotationStruct[] getAnnotations(int paramIdx)
    {
        return paramAnnotations[paramIdx];
    }

    public int getLenght()
    {
        int l = 1;
        int paramsCount = paramAnnotations.length;
        for(int i = 0; i < paramsCount; i++)
        {
            l += 2;
            int annotCount = paramAnnotations[i].length;
            for(int j = 0; j < annotCount; j++)
                l += paramAnnotations[i][j].getLenght();

        }

        return l;
    }

    protected String name;
    protected AnnotationStruct paramAnnotations[][];
}
