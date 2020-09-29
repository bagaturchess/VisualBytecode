// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFParserOptions.java

package com.fmi.bytecode.parser;


public final class CFParserOptions
{

    public CFParserOptions()
    {
        whatToParse = 1;
    }

    protected static final CFParserOptions DEFAULT = new CFParserOptions();
    public static final int PARSE_EVERYTHING = 1;
    public static final int PARSE_WITHOUT_CODE = 2;
    public static final int PARSE_WITHOUT_ATTRIBUTES = 3;
    public int whatToParse;

}
