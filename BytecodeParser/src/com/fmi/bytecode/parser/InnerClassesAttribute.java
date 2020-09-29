// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   InnerClassesAttribute.java

package com.fmi.bytecode.parser;


// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, InnerClassesAttributeEntry, ClassFile, CPInfo

public final class InnerClassesAttribute extends AttributeInfo
{

    InnerClassesAttribute(ClassFile owner)
    {
        nEntries = 0;
        entries = new InnerClassesAttributeEntry[8];
        this.owner = owner;
    }

    void init(ClassFile owner)
    {
        this.owner = owner;
    }

    public String getName()
    {
        return "InnerClasses";
    }

    public InnerClassesAttributeEntry addEntry(CPInfo innerClass, CPInfo outerClass, CPInfo innerName, int innerAccessFlags)
    {
        if(nEntries == entries.length)
        {
            InnerClassesAttributeEntry entriesOld[] = entries;
            entries = new InnerClassesAttributeEntry[2 * nEntries];
            System.arraycopy(entriesOld, 0, entries, 0, nEntries);
        }
        InnerClassesAttributeEntry entry = new InnerClassesAttributeEntry(this, innerClass, outerClass, innerName, innerAccessFlags);
        entries[nEntries++] = entry;
        return entry;
    }

    public int getNEntries()
    {
        return nEntries;
    }

    public InnerClassesAttributeEntry getEntry(int index)
    {
        return entries[index];
    }

    public InnerClassesAttributeEntry[] getEntries()
    {
        InnerClassesAttributeEntry r[] = new InnerClassesAttributeEntry[nEntries];
        System.arraycopy(entries, 0, r, 0, nEntries);
        return r;
    }

    private static final String S_INNER_CLASSES = "InnerClasses";
    private ClassFile owner;
    private int nEntries;
    private InnerClassesAttributeEntry entries[];
}
