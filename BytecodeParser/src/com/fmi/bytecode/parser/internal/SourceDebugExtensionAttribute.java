// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SourceDebugExtensionAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, Attributed

public class SourceDebugExtensionAttribute extends AttributeInfo
{

    public SourceDebugExtensionAttribute(Attributed owner, String debugExtension)
    {
        this.owner = owner;
        this.debugExtension = debugExtension;
    }

    public String getName()
    {
        return "SourceDebugExtension";
    }

    public int getAttributeType()
    {
        return 18;
    }

    public String getDebugExtension()
    {
        return debugExtension;
    }

    protected Attributed owner;
    private String debugExtension;
}
