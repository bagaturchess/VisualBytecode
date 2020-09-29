// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MemberInfo.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFNode, Attributed, AttributeContainer, CPInfo, 
//            CFException, MethodInfo, FieldInfo, AttributeInfo, 
//            ClassFile, SyntheticAttribute, CFFactory

public abstract class MemberInfo extends CFNode
    implements Attributed
{

    MemberInfo(CFFactory factory, ClassFile ownerClassFile, int accessFlags, CPInfo name, CPInfo descriptor)
    {
        attributeContainer = new AttributeContainer();
        if(Debug.DEBUG_CF)
        {
            if(name == null || descriptor == null || descriptor.getTag() != 1)
                throw new CFException(14);
            if(this instanceof MethodInfo)
                MethodInfo.checkMethodDescriptor(descriptor.getString());
            else
                FieldInfo.checkFieldDescriptor(descriptor.getString());
        }
        this.factory = factory;
        this.ownerClassFile = ownerClassFile;
        this.accessFlags = accessFlags;
        this.name = name;
        this.descriptor = descriptor;
    }

    public final int getAccessFlags()
    {
        return accessFlags;
    }

    public final CPInfo getNameCPInfo()
    {
        return name;
    }

    public final String getName()
    {
        return name.toPlainString();
    }

    public final CPInfo getDescriptorCPInfo()
    {
        return descriptor;
    }

    public final String getDescriptor()
    {
        return descriptor.toPlainString();
    }

    public final ClassFile getOwnerClassFile()
    {
        return ownerClassFile;
    }

    public final boolean isPublic()
    {
        return (accessFlags & 1) != 0;
    }

    public final boolean isProtected()
    {
        return (accessFlags & 4) != 0;
    }

    public final boolean isPrivate()
    {
        return (accessFlags & 2) != 0;
    }

    public final boolean isStatic()
    {
        return (accessFlags & 8) != 0;
    }

    public final boolean isFinal()
    {
        return (accessFlags & 0x10) != 0;
    }

    public final boolean isVolatile()
    {
        return (accessFlags & 0x40) != 0;
    }

    public final boolean isTransient()
    {
        return (accessFlags & 0x80) != 0;
    }

    public final boolean isSynchronized()
    {
        return (accessFlags & 0x20) != 0;
    }

    public final boolean isNative()
    {
        return (accessFlags & 0x100) != 0;
    }

    public final boolean isStrictFP()
    {
        return (accessFlags & 0x800) != 0;
    }

    public final void setPublic(boolean b)
    {
        accessFlags &= -8;
        if(b)
            accessFlags |= 1;
    }

    public final void setProtected(boolean b)
    {
        accessFlags &= -8;
        if(b)
            accessFlags |= 4;
    }

    public final void setPrivate(boolean b)
    {
        accessFlags &= -8;
        if(b)
            accessFlags |= 2;
    }

    public final void setStatic(boolean b)
    {
        if(b)
            accessFlags |= 8;
        else
            accessFlags &= -9;
    }

    public final void setFinal(boolean b)
    {
        if(b)
            accessFlags |= 0x10;
        else
            accessFlags &= 0xffffffef;
    }

    public final void setVolatile(boolean b)
    {
        if(b)
            accessFlags |= 0x40;
        else
            accessFlags &= 0xffffffbf;
    }

    public final void setTransient(boolean b)
    {
        if(b)
            accessFlags |= 0x80;
        else
            accessFlags &= 0xffffff7f;
    }

    public final void setSynchronized(boolean b)
    {
        if(b)
            accessFlags |= 0x20;
        else
            accessFlags &= 0xffffffdf;
    }

    public final void setNative(boolean b)
    {
        if(b)
            accessFlags |= 0x100;
        else
            accessFlags &= 0xfffffeff;
    }

    public final void setStrictFP(boolean b)
    {
        if(b)
            accessFlags |= 0x800;
        else
            accessFlags &= 0xfffff7ff;
    }

    public final void setAccessFlags(int flags)
    {
        accessFlags = flags;
    }

    public final void addAttribute(AttributeInfo a)
    {
        ownerClassFile.createCPInfoUtf8(a.getName());
        attributeContainer.addAttribute(a);
    }

    public final int getNAttributes()
    {
        return attributeContainer.getNAttributes();
    }

    public final AttributeInfo getAttribute(int index)
    {
        return attributeContainer.getAttribute(index);
    }

    public final AttributeInfo getAttribute(String name)
    {
        return attributeContainer.getAttribute(name);
    }

    public final AttributeInfo[] getAttributesArray()
    {
        return attributeContainer.getAttributesArray();
    }

    public final void removeAttribute(String name)
    {
        attributeContainer.removeAttribute(name);
    }

    public final void removeAttribute(AttributeInfo a)
    {
        attributeContainer.removeAttribute(a);
    }

    public final CFNode getParentNode()
    {
        return ownerClassFile;
    }

    public final CFNode[] getChildNodes()
    {
        return getAttributesArray();
    }

    public final SyntheticAttribute getSyntheticAttribute()
    {
        return (SyntheticAttribute)attributeContainer.getAttribute("Synthetic");
    }

    public final void createSyntheticAttribute()
    {
        ownerClassFile.createCPInfoUtf8("Synthetic");
        if(getSyntheticAttribute() == null)
            attributeContainer.addAttribute(new SyntheticAttribute());
    }

    public final void removeSyntheticAttribute()
    {
        SyntheticAttribute a = getSyntheticAttribute();
        if(a != null)
            attributeContainer.removeAttribute(a);
    }

    private static final int ACC_PUBLIC_PROTECTED_PRIVATE_MASK = -8;
    protected CFFactory factory;
    private ClassFile ownerClassFile;
    private int accessFlags;
    private CPInfo name;
    private CPInfo descriptor;
    private AttributeContainer attributeContainer;
}
