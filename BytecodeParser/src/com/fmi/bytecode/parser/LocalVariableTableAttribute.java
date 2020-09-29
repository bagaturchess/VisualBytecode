// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LocalVariableTableAttribute.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, LocalVariableTableEntry, CFException, CPInfo, 
//            FieldInfo, CFFactory, CodeAttribute, LocalVariable

public class LocalVariableTableAttribute extends AttributeInfo
{

    protected LocalVariableTableAttribute(CFFactory factory, CodeAttribute codeAttribute)
    {
        entries = new LocalVariableTableEntry[16];
        signatureTable = false;
        this.factory = factory;
        ownerCodeAttribute = codeAttribute;
    }

    public String getName()
    {
        return "LocalVariableTable";
    }

    public int getNEntries()
    {
        return nEntries;
    }

    public LocalVariableTableEntry getEntry(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= nEntries))
            throw new CFException(14);
        else
            return entries[index];
    }

    public LocalVariableTableEntry[] getEntriesArray()
    {
        LocalVariableTableEntry r[] = new LocalVariableTableEntry[nEntries];
        System.arraycopy(entries, 0, r, 0, nEntries);
        return r;
    }

    public LocalVariableTableEntry addEntry(String startPCLabel, String endPCLabel, CPInfo name, CPInfo descriptor, LocalVariable localVariable)
    {
        if(Debug.DEBUG_CF)
        {
            if(startPCLabel == null || endPCLabel == null || name == null || descriptor == null || localVariable == null)
                throw new CFException(14);
            if(name.getTag() != 1)
                throw new CFException(16);
            if(descriptor.getTag() != 1)
                throw new CFException(16);
            if(!signatureTable)
                FieldInfo.checkFieldDescriptor(descriptor.getString());
        }
        LocalVariableTableEntry entry = new LocalVariableTableEntry(this, ownerCodeAttribute, startPCLabel, endPCLabel, name, descriptor, localVariable);
        if(entries.length == nEntries)
        {
            LocalVariableTableEntry old[] = entries;
            entries = new LocalVariableTableEntry[nEntries * 2];
            System.arraycopy(old, 0, entries, 0, nEntries);
        }
        entries[nEntries++] = entry;
        return entry;
    }

    private final CFFactory factory;
    private final CodeAttribute ownerCodeAttribute;
    private LocalVariableTableEntry entries[];
    private int nEntries;
    protected boolean signatureTable;
}
