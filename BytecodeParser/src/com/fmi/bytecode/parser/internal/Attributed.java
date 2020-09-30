// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Attributed.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo

public interface Attributed
{

    public abstract void addAttribute(AttributeInfo attributeinfo);

    public abstract int getNAttributes();

    public abstract AttributeInfo getAttribute(int i);

    public abstract AttributeInfo getAttribute(String s);

    public abstract AttributeInfo[] getAttributesArray();

    public abstract void removeAttribute(String s);

    public abstract void removeAttribute(AttributeInfo attributeinfo);
}
