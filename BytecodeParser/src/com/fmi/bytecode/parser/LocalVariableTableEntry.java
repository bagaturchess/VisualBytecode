// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalVariableTableEntry.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, CFException, CodeAttribute, Constants, 
//            LocalVariableTableAttribute, CPInfo, LocalVariable, Instruction

public final class LocalVariableTableEntry extends CFNode
{

    LocalVariableTableEntry(LocalVariableTableAttribute owner, CodeAttribute ownerCodeAttribute, String startPCLabel, String endPCLabel, CPInfo name, CPInfo descriptor, LocalVariable localVariable)
    {
        if(owner == null)
            throw new CFException(69, "owner");
        if(ownerCodeAttribute == null)
        {
            throw new CFException(69, "ownerCodeAttribute");
        } else
        {
            this.owner = owner;
            this.ownerCodeAttribute = ownerCodeAttribute;
            this.startPCLabel = startPCLabel;
            this.endPCLabel = endPCLabel;
            this.name = name;
            this.descriptor = descriptor;
            this.localVariable = localVariable;
            return;
        }
    }

    public Instruction getStartPC()
    {
        return ownerCodeAttribute.getInstructionByLabel(startPCLabel);
    }

    public Instruction getEndPC()
    {
        return ownerCodeAttribute.getInstructionByLabel(endPCLabel);
    }

    public CPInfo getName()
    {
        return name;
    }

    public CPInfo getDescriptor()
    {
        return descriptor;
    }

    public CPInfo getSignature()
    {
        return descriptor;
    }

    public LocalVariable getLocalVariable()
    {
        return localVariable;
    }

    public CFNode getParentNode()
    {
        return owner;
    }

    public CFNode[] getChildNodes()
    {
        return Constants.CF_NODE_ARRAY_0;
    }

    private CodeAttribute ownerCodeAttribute;
    private LocalVariableTableAttribute owner;
    private String startPCLabel;
    private String endPCLabel;
    private CPInfo name;
    private CPInfo descriptor;
    private LocalVariable localVariable;
}
