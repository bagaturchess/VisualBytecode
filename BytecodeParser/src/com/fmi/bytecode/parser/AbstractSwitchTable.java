// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractSwitchTable.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, Instruction, CodeAttribute, CFException, 
//            Constants, CFFactory

public abstract class AbstractSwitchTable extends CFNode
{

    AbstractSwitchTable(CFFactory factory)
    {
        this.factory = factory;
    }

    protected final void init0(Instruction ownerInstruction, String defaultBranchLabel, String branchLabels[], int nBranches)
    {
        this.ownerInstruction = ownerInstruction;
        ownerCodeAttribute = ownerInstruction.getOwnerCodeAttribute();
        this.defaultBranchLabel = defaultBranchLabel;
        branchesLabels = branchLabels;
        this.nBranches = nBranches;
    }

    public final Instruction getDefaultBranch()
    {
        return ownerCodeAttribute.getInstructionByLabel(defaultBranchLabel);
    }

    public final int getNBranches()
    {
        return nBranches;
    }

    public final Instruction getBranchByIndex(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= nBranches))
            throw new CFException(17, new Object[] {
                "Switch branch", new Integer(index), new Integer(nBranches - 1)
            });
        else
            return ownerCodeAttribute.getInstructionByLabel(branchesLabels[index]);
    }

    public abstract Instruction getBranchByKey(int i);

    public final Instruction[] getBranchesArray()
    {
        Instruction r[] = new Instruction[nBranches];
        for(int i = 0; i < nBranches; i++)
            r[i] = ownerCodeAttribute.getInstructionByLabel(branchesLabels[i]);

        return r;
    }

    public final Instruction[] getSuccessorsArray()
    {
        Instruction r[] = new Instruction[nBranches + 1];
        for(int i = 0; i < nBranches; i++)
            r[i] = ownerCodeAttribute.getInstructionByLabel(branchesLabels[i]);

        r[nBranches] = ownerCodeAttribute.getInstructionByLabel(defaultBranchLabel);
        return r;
    }

    public final int[] getSuccessorIndicesArray()
    {
        int r[] = new int[nBranches + 1];
        for(int i = 0; i < nBranches; i++)
            r[i] = ownerCodeAttribute.getInstructionByLabel(branchesLabels[i], true).getIndex();

        r[nBranches] = ownerCodeAttribute.getInstructionByLabel(defaultBranchLabel, true).getIndex();
        return r;
    }

    public static int getPadding(int offset)
    {
        return 3 - (offset & 3);
    }

    CFFactory getFactory()
    {
        return factory;
    }

    protected void transferFields(AbstractSwitchTable r, Instruction x, String labelPrefix)
    {
        r.factory = factory;
        r.ownerCodeAttribute = ownerCodeAttribute;
        r.ownerInstruction = x;
        r.defaultBranchLabel = (new StringBuilder(String.valueOf(labelPrefix))).append(ownerCodeAttribute.getCanonicalLabel(defaultBranchLabel)).toString();
        r.nBranches = nBranches;
        r.branchesLabels = new String[nBranches];
        for(int i = 0; i < nBranches; i++)
            r.branchesLabels[i] = (new StringBuilder(String.valueOf(labelPrefix))).append(ownerCodeAttribute.getCanonicalLabel(branchesLabels[i])).toString();

    }

    abstract AbstractSwitchTable cloneAbstractSwitchTable(Instruction instruction, String s);

    void checkBranches()
    {
        ownerCodeAttribute.getInstructionByLabel(defaultBranchLabel, true);
        for(int i = 0; i < nBranches; i++)
            ownerCodeAttribute.getInstructionByLabel(branchesLabels[i], true);

    }

    public final CFNode[] getChildNodes()
    {
        return Constants.CF_NODE_ARRAY_0;
    }

    public final CFNode getParentNode()
    {
        return ownerInstruction;
    }

    public abstract String toString();

    private CFFactory factory;
    private CodeAttribute ownerCodeAttribute;
    private Instruction ownerInstruction;
    private String defaultBranchLabel;
    private String branchesLabels[];
    private int nBranches;
}
