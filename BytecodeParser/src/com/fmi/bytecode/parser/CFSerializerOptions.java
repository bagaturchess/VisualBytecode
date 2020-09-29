// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFSerializerOptions.java

package com.fmi.bytecode.parser;


public final class CFSerializerOptions
{

    public CFSerializerOptions()
    {
        recomputeLineNumberTable = false;
        recomputeMaxStack = true;
        recomputeMaxLocals = true;
        preserveOriginalMaxLocalsIfGreaterThanComputed = true;
    }

    protected static final CFSerializerOptions DEFAULT = new CFSerializerOptions();
    public boolean recomputeLineNumberTable;
    public boolean recomputeMaxStack;
    public boolean recomputeMaxLocals;
    public boolean preserveOriginalMaxLocalsIfGreaterThanComputed;

}
