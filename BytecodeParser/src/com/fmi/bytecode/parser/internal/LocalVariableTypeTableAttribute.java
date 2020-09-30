// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalVariableTypeTableAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            LocalVariableTableAttribute, CFFactory, CodeAttribute

public class LocalVariableTypeTableAttribute extends LocalVariableTableAttribute
{

    protected LocalVariableTypeTableAttribute(CFFactory factory, CodeAttribute codeAttribute)
    {
        super(factory, codeAttribute);
        super.signatureTable = true;
    }

    public String getName()
    {
        return "LocalVariableTypeTable";
    }

    public int getAttributeType()
    {
        return 17;
    }
}
