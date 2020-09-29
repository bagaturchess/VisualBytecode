// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttributeInfo.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, Constants

public abstract class AttributeInfo extends CFNode
    implements Constants
{

    AttributeInfo()
    {
    }

    public abstract String getName();

    public int getAttributeType()
    {
        return 0;
    }

    public CFNode getParentNode()
    {
        return null;
    }

    public CFNode[] getChildNodes()
    {
        return Constants.CF_NODE_ARRAY_0;
    }
}
