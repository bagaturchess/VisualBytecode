// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FieldInfo.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            MemberInfo, ConstantValueAttribute, CFException, ClassFile, 
//            CFFactory, CPInfo

public final class FieldInfo extends MemberInfo
{

    FieldInfo(CFFactory factory, ClassFile ownerClassFile, int accessFlags, CPInfo name, CPInfo descriptor)
    {
        super(factory, ownerClassFile, accessFlags, name, descriptor);
    }

    public ConstantValueAttribute getConstantValueAttribute()
    {
        return (ConstantValueAttribute)super.getAttribute("ConstantValue");
    }

    public ConstantValueAttribute createConstantValueAttribute()
    {
        ConstantValueAttribute cva = getConstantValueAttribute();
        if(cva != null)
        {
            return cva;
        } else
        {
            cva = new ConstantValueAttribute();
            super.addAttribute(cva);
            return cva;
        }
    }

    public CPInfo getConstantValue()
    {
        ConstantValueAttribute cva = getConstantValueAttribute();
        if(cva == null)
            return null;
        else
            return cva.getConstantValue();
    }

    public void setConstantValue(CPInfo x)
    {
        createConstantValueAttribute().setConstantValue(x);
    }

    public static void checkFieldDescriptor(String s)
    {
        if(s == null)
            throw new CFException(13);
        int ls = s.length();
        int i;
        for(i = 0; i < ls && s.charAt(i) == '['; i++);
        if(i == ls)
            throw new CFException(12, s);
        char ch = s.charAt(i);
        switch(ch)
        {
        case 66: // 'B'
        case 67: // 'C'
        case 68: // 'D'
        case 70: // 'F'
        case 73: // 'I'
        case 74: // 'J'
        case 83: // 'S'
        case 90: // 'Z'
            if(i != ls - 1)
                throw new CFException(12, s);
            else
                return;

        case 76: // 'L'
            int indexOfSemiColon = s.indexOf(';');
            if(indexOfSemiColon != ls - 1 || indexOfSemiColon <= i + 1)
                throw new CFException(12, s);
            else
                return;
        }
        throw new CFException(12, s);
    }

    public static String convertTypeToJLS(String fieldDescriptor)
    {
        checkFieldDescriptor(fieldDescriptor);
        int dimension = 0;
        for(; fieldDescriptor.startsWith("["); fieldDescriptor = fieldDescriptor.substring(1))
            dimension++;

        StringBuffer buffer = new StringBuffer();
        switch(fieldDescriptor.charAt(0))
        {
        case 66: // 'B'
            buffer.append("byte");
            break;

        case 67: // 'C'
            buffer.append("char");
            break;

        case 68: // 'D'
            buffer.append("double");
            break;

        case 70: // 'F'
            buffer.append("float");
            break;

        case 73: // 'I'
            buffer.append("int");
            break;

        case 74: // 'J'
            buffer.append("long");
            break;

        case 83: // 'S'
            buffer.append("short");
            break;

        case 90: // 'Z'
            buffer.append("boolean");
            break;

        case 76: // 'L'
            buffer.append(fieldDescriptor.substring(1, fieldDescriptor.length() - 1).replace('/', '.'));
            break;

        default:
            throw new CFException(11, fieldDescriptor);
        }
        for(int i = 0; i < dimension; i++)
            buffer.append("[]");

        return buffer.toString();
    }

    public static String convertTypeFromJLS(String jlsType)
    {
        if(jlsType == null)
            throw new CFException(69, "jlsType");
        if(jlsType.indexOf(' ') != -1)
            throw new CFException(20);
        StringBuffer arrayPrefixBuffer = new StringBuffer();
        for(; jlsType.endsWith("[]"); jlsType = jlsType.substring(0, jlsType.length() - 2))
            arrayPrefixBuffer.append('[');

        String arrayPrefix = arrayPrefixBuffer.toString();
        if(jlsType.equals("int"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('I').toString();
        if(jlsType.equals("short"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('S').toString();
        if(jlsType.equals("char"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('C').toString();
        if(jlsType.equals("byte"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('B').toString();
        if(jlsType.equals("boolean"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('Z').toString();
        if(jlsType.equals("long"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('J').toString();
        if(jlsType.equals("float"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('F').toString();
        if(jlsType.equals("double"))
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('D').toString();
        else
            return (new StringBuilder(String.valueOf(arrayPrefix))).append('L').append(jlsType.replace('.', '/')).append(';').toString();
    }

    public CPInfo toCPInfo(ClassFile cf)
    {
        return cf.createCPInfoMemberRef(9, getOwnerClassFile().getName(), super.getName(), super.getDescriptor());
    }

    public String toString()
    {
        return (new StringBuilder("[FIELD ")).append(getDescriptor()).append(' ').append(getOwnerClassFile().getName()).append('.').append(' ').append(getName()).append("]").toString();
    }
}
