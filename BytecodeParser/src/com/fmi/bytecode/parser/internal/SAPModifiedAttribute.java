// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SAPModifiedAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo

public final class SAPModifiedAttribute extends AttributeInfo
{

    public SAPModifiedAttribute(int version)
    {
        this.version = version;
    }

    public int getVersion()
    {
        return version;
    }

    public String getName()
    {
        return "COM.SAP.Modified";
    }

    public static final String S_SAP_MODIFIED = "COM.SAP.Modified";
    private int version;
}
