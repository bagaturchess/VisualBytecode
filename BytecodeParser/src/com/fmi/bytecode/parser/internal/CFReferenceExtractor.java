// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFReferenceExtractor.java

package com.fmi.bytecode.parser.internal;

import java.io.IOException;
import java.util.ArrayList;

// Referenced classes of package com.fmi.bytecode.parser:
//            ClassFile, CPInfo, CFFactory, CFParser

public class CFReferenceExtractor
{

    public CFReferenceExtractor()
    {
    }

    public static String[] getClassReferences(ClassFile cf)
    {
        int ncp = cf.getCPSize();
        ArrayList result = new ArrayList();
        for(int i = 0; i < ncp; i++)
        {
            CPInfo x = cf.getCPInfo(i);
            int tag = x.getTag();
            if(tag == 7)
                result.add(x.getValueName().getString());
        }

        return (String[])result.toArray(new String[result.size()]);
    }

    public static String[] getClassReferences(byte bytecode[])
        throws IOException
    {
        CFFactory factory = CFFactory.getThreadLocalInstance();
        CFParser parser = factory.createCFParser();
        ClassFile cf = parser.parse(bytecode, null);
        return getClassReferences(cf);
    }

    public static String[] getClassReferences(String filename)
        throws IOException
    {
        CFFactory factory = CFFactory.getThreadLocalInstance();
        CFParser parser = factory.createCFParser();
        ClassFile cf = parser.parse(filename, null);
        return getClassReferences(cf);
    }
}
