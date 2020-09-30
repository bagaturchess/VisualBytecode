// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CPInfo.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, Constants, CFException, ClassFile

public final class CPInfo extends CFNode
    implements Constants
{

    CPInfo(ClassFile owner, int tag, int value0, int value1, String valueString, int index)
    {
        plainStringCache = null;
        this.owner = owner;
        this.tag = tag;
        this.value0 = value0;
        this.value1 = value1;
        this.valueString = valueString;
        this.index = index;
    }

    public int getTag()
    {
        return tag;
    }

    public int getValueInt()
    {
        if(Debug.DEBUG_CF && tag != 3 && tag != 4)
            throw new CFException(63);
        else
            return value0;
    }

    public long getValueLong()
    {
        if(Debug.DEBUG_CF && tag != 5 && tag != 6)
            throw new CFException(65);
        else
            return (long)value0 << 32 | (long)value1 & 0xffffffffL;
    }

    public float getValueFloat()
    {
        if(Debug.DEBUG_CF && tag != 4)
            throw new CFException(61);
        else
            return Float.intBitsToFloat(value0);
    }

    public double getValueDouble()
    {
        if(Debug.DEBUG_CF && tag != 6)
            throw new CFException(60);
        else
            return Double.longBitsToDouble((long)value0 << 32 | (long)value1 & 0xffffffffL);
    }

    public CPInfo getValueString()
    {
        if(Debug.DEBUG_CF && tag != 8)
            throw new CFException(68);
        else
            return owner.getCPInfo(value0);
    }

    public int getValueStringIndex()
    {
        if(Debug.DEBUG_CF && tag != 8)
            throw new CFException(68);
        else
            return value0;
    }

    public String getString()
    {
        return valueString;
    }

    public CPInfo getValueName()
    {
        if(Debug.DEBUG_CF && tag != 7 && tag != 12)
            throw new CFException(67);
        else
            return owner.getCPInfo(value0);
    }

    public int getValueNameIndex()
    {
        if(Debug.DEBUG_CF && tag != 7 && tag != 12)
            throw new CFException(67);
        else
            return value0;
    }

    public CPInfo getValueClass()
    {
        if(Debug.DEBUG_CF && tag != 9 && tag != 10 && tag != 11)
            throw new CFException(58);
        else
            return owner.getCPInfo(value0);
    }

    public int getValueClassIndex()
    {
        if(Debug.DEBUG_CF && tag != 9 && tag != 10 && tag != 11)
            throw new CFException(58);
        else
            return value0;
    }

    public CPInfo getValueDescriptor()
    {
        if(Debug.DEBUG_CF && tag != 12)
            throw new CFException(59);
        else
            return owner.getCPInfo(value1);
    }

    public int getValueDescriptorIndex()
    {
        if(Debug.DEBUG_CF && tag != 12)
            throw new CFException(59);
        else
            return value1;
    }

    public CPInfo getValueNameAndType()
    {
        if(Debug.DEBUG_CF && tag != 9 && tag != 10 && tag != 11)
            throw new CFException(66);
        else
            return owner.getCPInfo(value1);
    }

    public int getValueNameAndTypeIndex()
    {
        if(Debug.DEBUG_CF && tag != 9 && tag != 10 && tag != 11)
            throw new CFException(66);
        else
            return value1;
    }

    public int getIndex()
    {
        return index;
    }

    public String toString()
    {
        switch(tag)
        {
        case 0: // '\0'
            return "<zeroth>";

        case 7: // '\007'
            return (new StringBuilder("<class ")).append(getValueName().getString()).append(">").toString();

        case 9: // '\t'
            return (new StringBuilder("<field ")).append(getValueClass().getValueName().getString()).append('.').append(getValueNameAndType().getValueName().getString()).append(" ").append(getValueNameAndType().getValueDescriptor().getString()).append(">").toString();

        case 10: // '\n'
            return (new StringBuilder("<method ")).append(getValueClass().getValueName().getString()).append('.').append(getValueNameAndType().getValueName().getString()).append(getValueNameAndType().getValueDescriptor().getString()).append(">").toString();

        case 11: // '\013'
            return (new StringBuilder("<interface_method ")).append(getValueClass().getValueName().getString()).append('.').append(getValueNameAndType().getValueName().getString()).append(getValueNameAndType().getValueDescriptor().getString()).append(">").toString();

        case 12: // '\f'
            return (new StringBuilder("<name_and_type ")).append(getValueName().getString()).append(" ").append(getValueDescriptor().getString()).append(">").toString();

        case 8: // '\b'
            return (new StringBuilder("<string \"")).append(getValueString().getString()).append("\">").toString();

        case 3: // '\003'
            return (new StringBuilder("<integer ")).append(getValueInt()).append(">").toString();

        case 5: // '\005'
            return (new StringBuilder("<long ")).append(getValueLong()).append(">").toString();

        case 6: // '\006'
            return (new StringBuilder("<double ")).append(getValueDouble()).append(">").toString();

        case 4: // '\004'
            return (new StringBuilder("<float ")).append(getValueFloat()).append(">").toString();

        case 1: // '\001'
            return (new StringBuilder("<utf8 \"")).append(getString()).append("\">").toString();

        case 2: // '\002'
        default:
            return "<???>";
        }
    }

    public String toPlainString()
    {
        if(plainStringCache != null)
            return plainStringCache;
        switch(tag)
        {
        case 0: // '\0'
            plainStringCache = "<zeroth>";
            break;

        case 7: // '\007'
            plainStringCache = getValueName().getString();
            break;

        case 9: // '\t'
            plainStringCache = (new StringBuilder(String.valueOf(getValueClass().getValueName().getString()))).append('.').append(getValueNameAndType().getValueName().getString()).append(" ").append(getValueNameAndType().getValueDescriptor().getString()).toString();
            break;

        case 10: // '\n'
            plainStringCache = (new StringBuilder(String.valueOf(getValueClass().getValueName().getString()))).append('.').append(getValueNameAndType().getValueName().getString()).append(getValueNameAndType().getValueDescriptor().getString()).toString();
            break;

        case 11: // '\013'
            plainStringCache = (new StringBuilder(String.valueOf(getValueClass().getValueName().getString()))).append('.').append(getValueNameAndType().getValueName().getString()).append(getValueNameAndType().getValueDescriptor().getString()).toString();
            break;

        case 12: // '\f'
            plainStringCache = (new StringBuilder(String.valueOf(getValueName().getString()))).append(" ").append(getValueDescriptor()).toString();
            break;

        case 8: // '\b'
            plainStringCache = getValueString().getString();
            break;

        case 3: // '\003'
            plainStringCache = Integer.toString(getValueInt());
            break;

        case 5: // '\005'
            plainStringCache = Long.toString(getValueLong());
            break;

        case 6: // '\006'
            plainStringCache = Double.toString(getValueDouble());
            break;

        case 4: // '\004'
            plainStringCache = Float.toString(getValueFloat());
            break;

        case 1: // '\001'
            plainStringCache = getString();
            break;

        case 2: // '\002'
        default:
            plainStringCache = "<???>";
            break;
        }
        return plainStringCache;
    }

    public String toJasminString()
    {
        switch(tag)
        {
        case 0: // '\0'
            return "any";

        case 7: // '\007'
            return getValueName().getString();

        case 9: // '\t'
            return (new StringBuilder(String.valueOf(getValueClass().getValueName().getString()))).append('/').append(getValueNameAndType().getValueName().getString()).append(" ").append(getValueNameAndType().getValueDescriptor().getString()).toString();

        case 10: // '\n'
            return (new StringBuilder(String.valueOf(getValueClass().getValueName().getString()))).append('/').append(getValueNameAndType().getValueName().getString()).append(getValueNameAndType().getValueDescriptor().getString()).toString();

        case 11: // '\013'
            return (new StringBuilder(String.valueOf(getValueClass().getValueName().getString()))).append('/').append(getValueNameAndType().getValueName().getString()).append(getValueNameAndType().getValueDescriptor().getString()).toString();

        case 12: // '\f'
            return (new StringBuilder(String.valueOf(getValueName().getString()))).append(" ").append(getValueDescriptor()).toString();

        case 8: // '\b'
            return (new StringBuilder(String.valueOf('"'))).append(getValueString().getString()).append('"').toString();

        case 3: // '\003'
            return Integer.toString(getValueInt());

        case 5: // '\005'
            return Long.toString(getValueLong());

        case 6: // '\006'
            return Double.toString(getValueDouble());

        case 4: // '\004'
            return Float.toString(getValueFloat());

        case 1: // '\001'
            return (new StringBuilder(String.valueOf('"'))).append(getString()).append('"').toString();

        case 2: // '\002'
        default:
            return "???";
        }
    }

    public ClassFile getOwner()
    {
        return owner;
    }

    public CFNode getParentNode()
    {
        return owner;
    }

    public CFNode[] getChildNodes()
    {
        return new CFNode[0];
    }

    private ClassFile owner;
    private int tag;
    private int value0;
    private int value1;
    private String valueString;
    private int index;
    private String plainStringCache;
}
