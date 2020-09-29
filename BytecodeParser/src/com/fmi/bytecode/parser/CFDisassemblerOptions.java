// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFDisassemblerOptions.java

package com.fmi.bytecode.parser;


public final class CFDisassemblerOptions
{

    public CFDisassemblerOptions()
    {
        includeLineNumbers = true;
        includeLocalVariables = true;
        calculateMaxStack = false;
        html = false;
        htmlStylesheet = null;
    }

    static final CFDisassemblerOptions DEFAULT = new CFDisassemblerOptions();
    public boolean includeLineNumbers;
    public boolean includeLocalVariables;
    public boolean calculateMaxStack;
    public boolean html;
    public String htmlStylesheet;

}
