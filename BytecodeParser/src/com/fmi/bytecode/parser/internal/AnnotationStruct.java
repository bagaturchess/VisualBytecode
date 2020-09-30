// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationStruct.java

package com.fmi.bytecode.parser.internal;

import java.util.*;

// Referenced classes of package com.fmi.bytecode.parser:
//            AnnotationPairValue, CPInfo

public class AnnotationStruct
{

    public AnnotationStruct(CPInfo type)
    {
        elementValuePairs = new HashMap(1);
        this.type = type;
    }

    public void addElementValuePair(String name, Object value)
    {
        elementValuePairs.put(name, value);
    }

    public Map getElementValuePairs()
    {
        return elementValuePairs;
    }

    public CPInfo getType()
    {
        return type;
    }

    public int getLenght()
    {
        Collection values = elementValuePairs.values();
        int l = 4;
        for(Iterator i = values.iterator(); i.hasNext();)
        {
            l += 2;
            AnnotationPairValue value = (AnnotationPairValue)i.next();
            l += value.getLenght();
        }

        return l;
    }

    private CPInfo type;
    private Map elementValuePairs;
}
