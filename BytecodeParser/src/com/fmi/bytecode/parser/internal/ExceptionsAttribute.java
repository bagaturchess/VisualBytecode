// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionsAttribute.java

package com.fmi.bytecode.parser.internal;

import java.util.ArrayList;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CPInfo, CFException, Constants

public final class ExceptionsAttribute extends AttributeInfo
{

    ExceptionsAttribute()
    {
        exceptions = new ArrayList();
    }

    public void addException(CPInfo e)
    {
        if(Debug.DEBUG_CF && (e == null || e.getTag() != 7))
        {
            throw new CFException(14);
        } else
        {
            exceptions.add(e);
            return;
        }
    }

    public int getNExceptions()
    {
        return exceptions.size();
    }

    public CPInfo getException(int index)
    {
        return (CPInfo)exceptions.get(index);
    }

    public CPInfo[] getExceptionsArray()
    {
        return (CPInfo[])exceptions.toArray(Constants.CPINFO_ARRAY_0);
    }

    public String getName()
    {
        return "Exceptions";
    }

    private ArrayList exceptions;
}
