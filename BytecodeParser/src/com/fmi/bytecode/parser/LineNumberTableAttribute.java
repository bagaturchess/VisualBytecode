// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LineNumberTableAttribute.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CFException, Instruction, CodeAttribute, 
//            CFFactory

public final class LineNumberTableAttribute extends AttributeInfo
{

    LineNumberTableAttribute(CFFactory factory)
    {
        this.factory = factory;
    }

    public String getName()
    {
        return "LineNumberTable";
    }

    LineNumberTableAttribute init(CodeAttribute ownerCodeAttribute, String startPCLabels[], int lineNumbers[])
    {
        if(Debug.DEBUG_CF && startPCLabels.length != lineNumbers.length)
        {
            throw new CFException(14);
        } else
        {
            this.ownerCodeAttribute = ownerCodeAttribute;
            nLineNumberEntries = startPCLabels.length;
            this.startPCLabels = startPCLabels;
            this.lineNumbers = lineNumbers;
            return this;
        }
    }

    public Instruction[] getStartPCsArray()
    {
        Instruction r[] = new Instruction[nLineNumberEntries];
        for(int i = 0; i < nLineNumberEntries; i++)
        {
            r[i] = ownerCodeAttribute.getInstructionByLabel(startPCLabels[i]);
            if(Debug.DEBUG_CF && r[i] == null)
                throw new CFException(18, startPCLabels[i]);
        }

        return r;
    }

    public int[] getLineNumbersArray()
    {
        int r[] = new int[nLineNumberEntries];
        System.arraycopy(lineNumbers, 0, r, 0, nLineNumberEntries);
        return r;
    }

    public CodeAttribute getOwnerCodeAttribute()
    {
        return ownerCodeAttribute;
    }

    private CFFactory factory;
    private CodeAttribute ownerCodeAttribute;
    private int nLineNumberEntries;
    private String startPCLabels[];
    private int lineNumbers[];
}
