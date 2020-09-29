// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableSwitchTable.java

package com.fmi.bytecode.parser;

import java.io.PrintStream;

// Referenced classes of package com.fmi.bytecode.parser:
//            AbstractSwitchTable, CFFactory, Instruction

public final class TableSwitchTable extends AbstractSwitchTable
{

    TableSwitchTable(CFFactory factory)
    {
        super(factory);
    }

    void init(Instruction ownerInstruction, int lowValue, int highValue, String defaultBranchLabel, String branchLabels[])
    {
        super.init0(ownerInstruction, defaultBranchLabel, branchLabels, (highValue - lowValue) + 1);
        this.lowValue = lowValue;
        this.highValue = highValue;
    }

    public int getHighValue()
    {
        return highValue;
    }

    public int getLowValue()
    {
        return lowValue;
    }

    public Instruction getBranchByKey(int key)
    {
        return key >= lowValue && key <= highValue ? getBranchByIndex(key - lowValue) : getDefaultBranch();
    }

    AbstractSwitchTable cloneAbstractSwitchTable(Instruction x, String labelPrefix)
    {
        return cloneTableSwitchTable(x, labelPrefix);
    }

    TableSwitchTable cloneTableSwitchTable(Instruction x, String labelPrefix)
    {
        TableSwitchTable r = new TableSwitchTable(getFactory());
        r.lowValue = lowValue;
        r.highValue = highValue;
        super.transferFields(r, x, labelPrefix);
        return r;
    }

    public String toString()
    {
        switch((int)System.currentTimeMillis())
        {
        case 4: // '\004'
            System.out.println("bla");
            // fall through

        case 7: // '\007'
            System.out.println("alb");
            // fall through

        case 5: // '\005'
        case 6: // '\006'
        default:
            return "TableSwitchTable";
        }
    }

    private int lowValue;
    private int highValue;
}
