// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFFactory.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            LocalVariable, CFParser, CFDisassembler, CFSerializer, 
//            ClassFile, CFException

public final class CFFactory
{

    public CFFactory()
    {
        localVariables = new LocalVariable[0x10000];
        integers = new Integer[1000];
        parser = null;
        serializer = null;
        disassembler = null;
        generatedLabels = new String[256];
    }

    public static CFFactory getThreadLocalInstance()
    {
        return (CFFactory)threadLocalFactory.get();
    }

    public CFParser createCFParser()
    {
        return parser != null ? parser : (parser = new CFParser(this));
    }

    public CFDisassembler createCFDisassembler()
    {
        return disassembler != null ? disassembler : (disassembler = new CFDisassembler(this));
    }

    public CFSerializer createCFSerializer()
    {
        return serializer != null ? serializer : (serializer = new CFSerializer(this));
    }

    public ClassFile createClassFile()
    {
        return new ClassFile(this, new byte[0]);
    }

    public LocalVariable createLocalVariable(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= localVariables.length))
            throw new CFException(17, new Object[] {
                "Local variable", new Integer(index), new Integer(65535)
            });
        if(localVariables[index] == null)
            localVariables[index] = new LocalVariable(index);
        return localVariables[index];
    }

    public Integer createInteger(int x)
    {
        return x >= 0 && x < integers.length ? (integers[x] = new Integer(x)) : new Integer(x);
    }

    public String createGeneratedLabel(int offset)
    {
        if(offset < 256)
        {
            if(generatedLabels[offset] == null)
                return generatedLabels[offset] = (new StringBuilder("_")).append(offset).toString();
            else
                return generatedLabels[offset];
        } else
        {
            return (new StringBuilder("_")).append(offset).toString();
        }
    }

    private static final boolean USE_GENERATED_LABELS_CACHE = true;
    private static final ThreadLocal threadLocalFactory = new ThreadLocal() {

        protected Object initialValue()
        {
            return new CFFactory();
        }

    }
;
    private LocalVariable localVariables[];
    private Integer integers[];
    private CFParser parser;
    private CFSerializer serializer;
    private CFDisassembler disassembler;
    private static final int N_GENERATED_LABELS_CACHE = 256;
    private static final String GENERATED_LABELS_PREFIX = "_";
    private String generatedLabels[];

}
