// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SignatureAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CPInfo

public class SignatureAttribute extends AttributeInfo
{

    public SignatureAttribute(CPInfo signatureIndex)
    {
        this.signatureIndex = signatureIndex;
    }

    public CPInfo getSignatureIndex()
    {
        return signatureIndex;
    }

    public String getSignature()
    {
        return signatureIndex.getString();
    }

    public String getName()
    {
        return "Signature";
    }

    public int getAttributeType()
    {
        return 15;
    }

    private CPInfo signatureIndex;
}
