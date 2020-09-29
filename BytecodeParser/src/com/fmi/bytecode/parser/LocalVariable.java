// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalVariable.java

package com.fmi.bytecode.parser;


public final class LocalVariable
{

    LocalVariable(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return index;
    }

    public int hashCode()
    {
        return index * 0x1dc8cd;
    }

    public String toString()
    {
        return (new StringBuilder("(Local variable ")).append(index).append(")").toString();
    }

    public boolean equals(Object obj)
    {
        try
        {
            return ((LocalVariable)obj).index == index;
        }
        catch(ClassCastException classcastexception) { }
        catch(NullPointerException nullpointerexception) { }
        return false;
    }

    private int index;
}
