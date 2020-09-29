// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ExceptionHandler.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, CFException, CPInfo, CodeAttribute, 
//            Instruction, Constants

public final class ExceptionHandler extends CFNode
{

    private ExceptionHandler()
    {
    }

    ExceptionHandler(CodeAttribute ownerCodeAttribute, String startPCLabel, String endPCLabel, boolean isEndPCInclusive, String handlerPCLabel, CPInfo catchType)
    {
        if(Debug.DEBUG_CF)
        {
            if(startPCLabel == null || endPCLabel == null || handlerPCLabel == null || catchType == null)
                throw new CFException(14);
            int tag = catchType.getTag();
            if(tag != 7 && tag != 0)
                throw new CFException(14);
        }
        this.ownerCodeAttribute = ownerCodeAttribute;
        this.startPCLabel = startPCLabel;
        this.endPCLabel = endPCLabel;
        this.isEndPCInclusive = isEndPCInclusive;
        this.handlerPCLabel = handlerPCLabel;
        this.catchType = catchType;
    }

    public Instruction getStartPC()
    {
        return ownerCodeAttribute.getInstructionByLabel(startPCLabel);
    }

    public Instruction getHandlerPC()
    {
        return ownerCodeAttribute.getInstructionByLabel(handlerPCLabel);
    }

    public Instruction getEndPCExclusive()
    {
        Instruction x = ownerCodeAttribute.getInstructionByLabel(endPCLabel);
        if(isEndPCInclusive)
            return x.getNextInstruction();
        else
            return x;
    }

    public Instruction getEndPCInclusive()
    {
        Instruction x = ownerCodeAttribute.getInstructionByLabel(endPCLabel);
        if(isEndPCInclusive)
            return x;
        else
            return x != null ? x.getPreviousInstruction() : ownerCodeAttribute.getLastInstruction();
    }

    public CPInfo getCatchType()
    {
        return catchType;
    }

    public ExceptionHandler cloneExceptionHandler(String prefix)
    {
        ExceptionHandler r = new ExceptionHandler();
        r.ownerCodeAttribute = ownerCodeAttribute;
        r.startPCLabel = (new StringBuilder(String.valueOf(prefix))).append(ownerCodeAttribute.getCanonicalLabel(startPCLabel)).toString();
        r.endPCLabel = (new StringBuilder(String.valueOf(prefix))).append(ownerCodeAttribute.getCanonicalLabel(endPCLabel)).toString();
        r.isEndPCInclusive = isEndPCInclusive;
        r.handlerPCLabel = (new StringBuilder(String.valueOf(prefix))).append(ownerCodeAttribute.getCanonicalLabel(handlerPCLabel)).toString();
        r.catchType = catchType;
        return r;
    }

    public CFNode getParentNode()
    {
        return ownerCodeAttribute;
    }

    public CFNode[] getChildNodes()
    {
        return Constants.CF_NODE_ARRAY_0;
    }

    public String toString()
    {
        return (new StringBuilder("ExceptionHandler[")).append(catchType.toPlainString()).append("]").toString();
    }

    private CodeAttribute ownerCodeAttribute;
    private String startPCLabel;
    private String endPCLabel;
    private boolean isEndPCInclusive;
    private String handlerPCLabel;
    private CPInfo catchType;
}
