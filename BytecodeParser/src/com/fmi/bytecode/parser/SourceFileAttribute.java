// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SourceFileAttribute.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CPInfo

public final class SourceFileAttribute extends AttributeInfo
{

    SourceFileAttribute()
    {
    }

    public CPInfo getSourceFile()
    {
        return sourceFile;
    }

    void setSourceFile(CPInfo sourceFile)
    {
        this.sourceFile = sourceFile;
    }

    public String getName()
    {
        return "SourceFile";
    }

    private CPInfo sourceFile;
}
