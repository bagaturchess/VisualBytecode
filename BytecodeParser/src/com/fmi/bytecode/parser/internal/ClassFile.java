// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassFile.java

package com.fmi.bytecode.parser.internal;

import java.io.*;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, Attributed, Constants, CPInfo, 
//            FieldInfo, MethodInfo, AttributeContainer, CFException, 
//            SourceFileAttribute, CFFactory, CFSerializer, CFDisassembler, 
//            AttributeInfo, SerialVersionUIDCalculator

public final class ClassFile extends CFNode
    implements Attributed, Constants
{

    ClassFile(CFFactory factory, byte originalBytecode[])
    {
        ncp = 0;
        cp = new CPInfo[1024];
        nInterfaces = 0;
        interfaces = Constants.CPINFO_ARRAY_0;
        nFields = 0;
        fields = new FieldInfo[8];
        nMethods = 0;
        methods = new MethodInfo[8];
        attributeContainer = new AttributeContainer();
        this.originalBytecode = originalBytecode;
        minorVersion = 3;
        majorVersion = 45;
        this.factory = factory;
        addCPInfo(new CPInfo(this, 0, 0, 0, null, 0));
    }

    public byte[] getOriginalBytecode()
    {
        return originalBytecode != null ? (byte[])originalBytecode.clone() : null;
    }

    public int getMinorVersion()
    {
        return minorVersion;
    }

    public int getMajorVersion()
    {
        return majorVersion;
    }

    public int getAccessFlags()
    {
        return accessFlags;
    }

    public CPInfo getThisClass()
    {
        return thisClass;
    }

    public CPInfo getSuperClass()
    {
        return superClass;
    }

    public String getName()
    {
        return thisClass.getValueName().toPlainString();
    }

    public String getPackageName()
    {
        String fullName = getName();
        int idx = fullName.lastIndexOf('/');
        if(idx == -1)
            return null;
        else
            return fullName.substring(0, idx);
    }

    public String getSuperClassName()
    {
        try
        {
            return superClass.getValueName().toPlainString();
        }
        catch(CFException cfe)
        {
            return (new StringBuilder("<CPInfo #")).append(superClass.getIndex()).append(">").toString();
        }
    }

    public int getCPSize()
    {
        return ncp;
    }

    public CPInfo[] getCPInfosArray()
    {
        CPInfo r[] = new CPInfo[ncp];
        System.arraycopy(cp, 0, r, 0, ncp);
        return r;
    }

    public CPInfo getCPInfo(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= ncp))
            throw new CFException(17, new Object[] {
                "CPInfo", new Integer(index), new Integer(ncp - 1)
            });
        else
            return cp[index];
    }

    public int getCPIndex(CPInfo x)
    {
        for(int i = 0; i < ncp; i++)
            if(cp[i] == x)
                return i;

        return -1;
    }

    public CPInfo getCPInfoUtf8(String s)
    {
        if(Debug.DEBUG_CF && s == null)
            throw new CFException(69, "s");
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 1 && cp[i].getString().equals(s))
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoUtf8(String s)
    {
        if(Debug.DEBUG_CF && s == null)
            throw new CFException(69, "s");
        CPInfo x = getCPInfoUtf8(s);
        if(x != null)
            return x;
        x = new CPInfo(this, 1, 0, 0, s, ncp);
        addCPInfo(x);
        if(Debug.DEBUG_CF && x.getTag() != 1)
            throw new CFException(16);
        else
            return x;
    }

    public CPInfo getCPInfoString(String s)
    {
        if(Debug.DEBUG_CF && s == null)
            throw new CFException(69, "s");
        CPInfo y = getCPInfoUtf8(s);
        if(y == null)
            return null;
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 8 && cp[i].getValueString() == y)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoString(String s)
    {
        if(Debug.DEBUG_CF && s == null)
            throw new CFException(69, "s");
        CPInfo x = getCPInfoString(s);
        if(x != null)
            return x;
        CPInfo y = createCPInfoUtf8(s);
        x = new CPInfo(this, 8, y.getIndex(), 0, null, ncp);
        addCPInfo(x);
        if(Debug.DEBUG_CF && x.getTag() != 8)
            throw new CFException(16);
        else
            return x;
    }

    public CPInfo getCPInfoClass(String className)
    {
        CPInfo y = getCPInfoUtf8(className);
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 7 && cp[i].getValueName() == y)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoClass(String className)
    {
        CPInfo x = getCPInfoClass(className);
        if(x != null)
            return x;
        CPInfo y = createCPInfoUtf8(className);
        x = new CPInfo(this, 7, y.getIndex(), 0, null, ncp);
        addCPInfo(x);
        if(Debug.DEBUG_CF && x.getTag() != 7)
            throw new CFException(16);
        else
            return x;
    }

    public CPInfo getCPInfoInteger(int value)
    {
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 3 && cp[i].getValueInt() == value)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoInteger(int value)
    {
        CPInfo x = getCPInfoInteger(value);
        if(x != null)
        {
            return x;
        } else
        {
            x = new CPInfo(this, 3, value, 0, null, ncp);
            addCPInfo(x);
            return x;
        }
    }

    public CPInfo getCPInfoLong(long value)
    {
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 5 && cp[i].getValueLong() == value)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoLong(long value)
    {
        CPInfo x = getCPInfoLong(value);
        if(x != null)
        {
            return x;
        } else
        {
            x = new CPInfo(this, 5, (int)(value >> 32), (int)value, null, ncp);
            addCPInfo(x);
            addCPInfo(x);
            return x;
        }
    }

    public CPInfo getCPInfoFloat(float valueFloat)
    {
        int value = Float.floatToIntBits(valueFloat);
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 4 && cp[i].getValueInt() == value)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoFloat(float valueFloat)
    {
        CPInfo x = getCPInfoFloat(valueFloat);
        if(x != null)
        {
            return x;
        } else
        {
            x = new CPInfo(this, 4, Float.floatToIntBits(valueFloat), 0, null, ncp);
            addCPInfo(x);
            return x;
        }
    }

    public CPInfo getCPInfoMemberRef(int tag, CPInfo valueClass, CPInfo valueNameAndType)
    {
        if(Debug.DEBUG_CF)
        {
            if(valueClass == null)
                throw new CFException(69, "valueClass");
            if(valueNameAndType == null)
                throw new CFException(69, "valueNameAndType");
            if(valueClass.getTag() != 7)
                throw new CFException(16);
            if(valueNameAndType.getTag() != 12)
                throw new CFException(16);
        }
        int i;
        switch(tag)
        {
        default:
            throw new CFException(16);

        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
            i = 0;
            break;
        }
        for(; i < ncp; i++)
            if(cp[i].getTag() == tag && cp[i].getValueClass() == valueClass && cp[i].getValueNameAndType() == valueNameAndType)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoMemberRef(int tag, CPInfo valueClass, CPInfo valueNameAndType)
    {
        if(Debug.DEBUG_CF)
        {
            if(valueClass == null || valueNameAndType == null)
                throw new CFException(14);
            if(valueClass.getTag() != 7)
                throw new CFException(16);
            if(valueNameAndType.getTag() != 12)
                throw new CFException(16);
        }
        CPInfo x = getCPInfoMemberRef(tag, valueClass, valueNameAndType);
        if(x != null)
        {
            return x;
        } else
        {
            x = new CPInfo(this, tag, valueClass.getIndex(), valueNameAndType.getIndex(), null, ncp);
            addCPInfo(x);
            return x;
        }
    }

    public CPInfo createCPInfoMemberRef(int tag, String className, String memberName, String descriptorString)
    {
        CPInfo valueClass = createCPInfoClass(className);
        CPInfo valueName = createCPInfoUtf8(memberName);
        CPInfo valueDescriptor = createCPInfoUtf8(descriptorString);
        CPInfo valueNameAndType = createCPInfoNameAndType(valueName, valueDescriptor);
        return createCPInfoMemberRef(tag, valueClass, valueNameAndType);
    }

    public CPInfo createCPInfoMemberRef(int tag, CPInfo valueClass, String memberName, String descriptorString)
    {
        CPInfo valueName = createCPInfoUtf8(memberName);
        CPInfo valueDescriptor = createCPInfoUtf8(descriptorString);
        CPInfo valueNameAndType = createCPInfoNameAndType(valueName, valueDescriptor);
        return createCPInfoMemberRef(tag, valueClass, valueNameAndType);
    }

    public CPInfo getCPInfoNameAndType(CPInfo name, CPInfo descriptor)
    {
        for(int i = 0; i < ncp; i++)
            if(cp[i].getTag() == 12 && cp[i].getValueName() == name && cp[i].getValueDescriptor() == descriptor)
                return cp[i];

        return null;
    }

    public CPInfo createCPInfoNameAndType(CPInfo name, CPInfo descriptor)
    {
        CPInfo x = getCPInfoNameAndType(name, descriptor);
        if(x != null)
        {
            return x;
        } else
        {
            x = new CPInfo(this, 12, name.getIndex(), descriptor.getIndex(), null, ncp);
            addCPInfo(x);
            return x;
        }
    }

    public int getNInterfaces()
    {
        return nInterfaces;
    }

    public CPInfo getInterface(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= nInterfaces))
            throw new IndexOutOfBoundsException((new StringBuilder("Interface index for this class must be in {0..")).append(nInterfaces - 1).append("}.").toString());
        else
            return interfaces[index];
    }

    public CPInfo[] getInterfacesArray()
    {
        if(nInterfaces == 0)
        {
            return Constants.CPINFO_ARRAY_0;
        } else
        {
            CPInfo r[] = new CPInfo[nInterfaces];
            System.arraycopy(interfaces, 0, r, 0, nInterfaces);
            return r;
        }
    }

    public String[] getInterfaceNames()
    {
        if(nInterfaces == 0)
            return ArrayUtils.STRING_ARRAY_0;
        String r[] = new String[nInterfaces];
        for(int i = 0; i < nInterfaces; i++)
            r[i] = interfaces[i].getValueName().toPlainString();

        return r;
    }

    public void addInterface(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 7))
            throw new CFException(14);
        if(nInterfaces == interfaces.length)
        {
            CPInfo interfacesOld[] = interfaces;
            interfaces = new CPInfo[2 * nInterfaces + 8];
            System.arraycopy(interfacesOld, 0, interfaces, 0, nInterfaces);
        }
        interfaces[nInterfaces++] = x;
    }

    public int getNFields()
    {
        return nFields;
    }

    public FieldInfo getField(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= nFields))
            throw new CFException(16);
        else
            return fields[index];
    }

    public FieldInfo[] getFieldsArray()
    {
        FieldInfo r[] = new FieldInfo[nFields];
        System.arraycopy(fields, 0, r, 0, nFields);
        return r;
    }

    void addFieldInfo(FieldInfo f)
    {
        if(nFields == fields.length)
        {
            FieldInfo fieldsOld[] = fields;
            fields = new FieldInfo[2 * nFields + 8];
            System.arraycopy(fieldsOld, 0, fields, 0, nFields);
        }
        fields[nFields++] = f;
    }

    public FieldInfo getField(String name)
    {
        for(int i = 0; i < nFields; i++)
            if(fields[i].getName().equals(name))
                return fields[i];

        return null;
    }

    public int getNMethods()
    {
        return nMethods;
    }

    public MethodInfo getMethod(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= nMethods))
            throw new CFException(16);
        else
            return methods[index];
    }

    public MethodInfo[] getMethodsArray()
    {
        MethodInfo r[] = new MethodInfo[nMethods];
        System.arraycopy(methods, 0, r, 0, nMethods);
        return r;
    }

    void setVersion(int majorVersion, int minorVersion)
    {
        this.minorVersion = minorVersion;
        this.majorVersion = majorVersion;
    }

    void addCPInfo(CPInfo x)
    {
        if(ncp == cp.length)
        {
            CPInfo old[] = cp;
            cp = new CPInfo[ncp * 2];
            System.arraycopy(old, 0, cp, 0, ncp);
        }
        cp[ncp] = x;
        ncp++;
    }

    void setAccessFlags(int accessFlags)
    {
        this.accessFlags = accessFlags;
    }

    public void setThisClass(CPInfo thisClass)
    {
        if(Debug.DEBUG_CF)
        {
            if(thisClass == null)
                throw new CFException(69, "thisClass");
            if(thisClass.getTag() != 7)
                throw new CFException(16);
        }
        this.thisClass = thisClass;
    }

    public void setThisClass(String className)
    {
        thisClass = createCPInfoClass(className);
    }

    public void setSuperClass(CPInfo superClass)
    {
        if(Debug.DEBUG_CF && !getName().equals("java/lang/Object") && (superClass == null || superClass.getTag() != 7))
        {
            throw new CFException(14);
        } else
        {
            this.superClass = superClass;
            return;
        }
    }

    public void setSuperClass(String className)
    {
        superClass = createCPInfoClass(className);
    }

    void addMethodInfo(MethodInfo m)
    {
        if(nMethods == methods.length)
        {
            MethodInfo methodsOld[] = methods;
            methods = new MethodInfo[2 * nMethods + 8];
            System.arraycopy(methodsOld, 0, methods, 0, nMethods);
        }
        methods[nMethods++] = m;
    }

    public MethodInfo getMethod(String name, String descriptor)
    {
        if(Debug.DEBUG_CF)
        {
            if(name == null)
                throw new CFException(69, "name");
            MethodInfo.checkMethodDescriptor(descriptor);
        }
        for(int i = 0; i < nMethods; i++)
            if(methods[i].getName().equals(name) && methods[i].getDescriptor().equals(descriptor))
                return methods[i];

        return null;
    }

    public MethodInfo createMethod(String name, String descriptor)
    {
        if(Debug.DEBUG_CF)
        {
            if(name == null)
                throw new CFException(69, "name");
            MethodInfo.checkMethodDescriptor(descriptor);
        }
        MethodInfo m = getMethod(name, descriptor);
        if(m != null)
        {
            return m;
        } else
        {
            m = new MethodInfo(factory, this, 0, createCPInfoUtf8(name), createCPInfoUtf8(descriptor));
            addMethodInfo(m);
            return m;
        }
    }

    public FieldInfo createField(String name, String descriptor)
    {
        if(Debug.DEBUG_CF)
        {
            if(name == null)
                throw new CFException(69, "name");
            FieldInfo.checkFieldDescriptor(descriptor);
        }
        FieldInfo f = getField(name);
        if(f != null)
        {
            if(!f.getDescriptor().equals(descriptor))
                throw new CFException(9, new Object[] {
                    (new StringBuilder(String.valueOf(name))).append(' ').append(descriptor).toString(), (new StringBuilder(String.valueOf(f.getName()))).append(' ').append(f.getDescriptor()).toString()
                });
            else
                return f;
        } else
        {
            f = new FieldInfo(factory, this, 0, createCPInfoUtf8(name), createCPInfoUtf8(descriptor));
            addFieldInfo(f);
            return f;
        }
    }

    public SourceFileAttribute getSourceFileAttribute()
    {
        return (SourceFileAttribute)attributeContainer.getAttribute("SourceFile");
    }

    public SourceFileAttribute createSourceFileAttribute()
    {
        SourceFileAttribute sfa = getSourceFileAttribute();
        if(sfa != null)
        {
            return sfa;
        } else
        {
            sfa = new SourceFileAttribute();
            addAttribute(sfa);
            return sfa;
        }
    }

    public String getSourceFile()
    {
        SourceFileAttribute sfa = getSourceFileAttribute();
        if(sfa == null)
            return null;
        CPInfo sf = sfa.getSourceFile();
        if(sf == null)
            return null;
        else
            return sf.toPlainString();
    }

    public void setSourceFile(String sourceFile)
    {
        createSourceFileAttribute().setSourceFile(createCPInfoUtf8(sourceFile));
    }

    public boolean isPublic()
    {
        return (accessFlags & 1) != 0;
    }

    public boolean isFinal()
    {
        return (accessFlags & 0x10) != 0;
    }

    public boolean isInterface()
    {
        return (accessFlags & 0x200) != 0;
    }

    public boolean isAnnotation()
    {
        return (accessFlags & 0x2000) != 0;
    }

    public boolean isEnum()
    {
        return (accessFlags & 0x4000) != 0;
    }

    public boolean isAbstract()
    {
        return (accessFlags & 0x400) != 0;
    }

    public void setPublic(boolean b)
    {
        if(b)
            accessFlags |= 1;
        else
            accessFlags &= -2;
    }

    public void setFinal(boolean b)
    {
        if(b)
            accessFlags |= 0x10;
        else
            accessFlags &= 0xffffffef;
    }

    public void setInterface(boolean b)
    {
        if(b)
            accessFlags |= 0x200;
        else
            accessFlags &= 0xfffffdff;
    }

    public void setAbstract(boolean b)
    {
        if(b)
            accessFlags |= 0x400;
        else
            accessFlags &= 0xfffffbff;
    }

    public String toString()
    {
        return (new StringBuilder("[CLASSFILE ")).append(getName()).append("]").toString();
    }

    public byte[] serializeToByteArray()
        throws IOException
    {
        return factory.createCFSerializer().serializeToByteArray(this, null);
    }

    public void serialize(File file)
        throws IOException
    {
        factory.createCFSerializer().serialize(file, this, null);
    }

    public void serialize(String filename)
        throws IOException
    {
        factory.createCFSerializer().serialize(filename, this, null);
    }

    public void disassemble(File file)
        throws IOException
    {
        factory.createCFDisassembler().disassemble(file, this, null);
    }

    public void disassemble(String filename)
        throws IOException
    {
        factory.createCFDisassembler().disassemble(filename, this, null);
    }

    public void disassemble(PrintStream out)
        throws IOException
    {
        factory.createCFDisassembler().disassemble(out, this, null);
    }

    public void disassembleToSystemOut()
        throws IOException
    {
        factory.createCFDisassembler().disassemble(System.out, this, null);
    }

    public String disassembleToString()
        throws IOException
    {
        ByteArrayOutputStream out0 = new ByteArrayOutputStream();
        disassemble(new PrintStream(out0));
        return new String(out0.toByteArray());
    }

    public void addAttribute(AttributeInfo a)
    {
        createCPInfoUtf8(a.getName());
        attributeContainer.addAttribute(a);
    }

    public int getNAttributes()
    {
        return attributeContainer.getNAttributes();
    }

    public AttributeInfo getAttribute(int index)
    {
        return attributeContainer.getAttribute(index);
    }

    public AttributeInfo getAttribute(String name)
    {
        return attributeContainer.getAttribute(name);
    }

    public AttributeInfo[] getAttributesArray()
    {
        return attributeContainer.getAttributesArray();
    }

    public void removeAttribute(String name)
    {
        attributeContainer.removeAttribute(name);
    }

    public void removeAttribute(AttributeInfo a)
    {
        attributeContainer.removeAttribute(a);
    }

    public long computeSerialVersionUID()
    {
        return SerialVersionUIDCalculator.computeSerialVersionUID(this);
    }

    public CFNode getParentNode()
    {
        return null;
    }

    public CFNode[] getChildNodes()
    {
        AttributeInfo attributes[] = attributeContainer.getAttributesArray();
        int nAttributes = attributes.length;
        CFNode r[] = new CPInfo[ncp + nFields + nMethods + nAttributes];
        for(int i = 0; i < ncp; i++)
            r[i] = cp[i];

        for(int i = 0; i < nFields; i++)
            r[ncp + i] = fields[i];

        for(int i = 0; i < nMethods; i++)
            r[ncp + nFields + i] = methods[i];

        for(int i = 0; i < nAttributes; i++)
            r[ncp + nFields + nMethods + i] = attributes[i];

        return r;
    }

    private final CFFactory factory;
    private final byte originalBytecode[];
    private int minorVersion;
    private int majorVersion;
    private int ncp;
    private CPInfo cp[];
    private int accessFlags;
    private CPInfo thisClass;
    private CPInfo superClass;
    private int nInterfaces;
    private CPInfo interfaces[];
    private int nFields;
    private FieldInfo fields[];
    private int nMethods;
    private MethodInfo methods[];
    private AttributeContainer attributeContainer;
}
