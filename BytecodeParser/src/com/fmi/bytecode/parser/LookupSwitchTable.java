// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LookupSwitchTable.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            AbstractSwitchTable, CFFactory, Instruction

public final class LookupSwitchTable extends AbstractSwitchTable
{

    LookupSwitchTable(CFFactory factory)
    {
        super(factory);
    }

    void init(Instruction ownerInstruction, String defaultBranchLabel, int matches[], String branchLabels[], int nBranches)
    {
        super.init0(ownerInstruction, defaultBranchLabel, branchLabels, nBranches);
        this.matches = new int[nBranches];
        System.arraycopy(matches, 0, this.matches, 0, nBranches);
    }

    public int getMatch(int index)
    {
        return matches[index];
    }

    public int[] getMatchesArray()
    {
        int n = getNBranches();
        int r[] = new int[n];
        System.arraycopy(matches, 0, r, 0, n);
        return r;
    }

    public Instruction getBranchByKey(int key)
    {
        for(int i = 0; i < getNBranches(); i++)
            if(key == matches[i])
                return getBranchByIndex(i);

        return getDefaultBranch();
    }

    AbstractSwitchTable cloneAbstractSwitchTable(Instruction x, String labelPrefix)
    {
        return cloneLookupSwitchTable(x, labelPrefix);
    }

    LookupSwitchTable cloneLookupSwitchTable(Instruction x, String labelPrefix)
    {
        LookupSwitchTable r = new LookupSwitchTable(getFactory());
        r.matches = new int[matches.length];
        System.arraycopy(matches, 0, r.matches, 0, matches.length);
        super.transferFields(r, x, labelPrefix);
        return r;
    }

    public String toString()
    {
        return "LookupSwitchTable";
    }

    private int matches[];
}
