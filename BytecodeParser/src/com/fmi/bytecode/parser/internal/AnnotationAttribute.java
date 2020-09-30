// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationAttribute.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, AnnotationStruct, Attributed

public class AnnotationAttribute extends AttributeInfo
{

    protected AnnotationAttribute(String name, Attributed owner)
    {
        this.name = name;
        this.owner = owner;
    }

    public AnnotationAttribute(String name, int numAnnotations, Attributed owner)
    {
        this(name, owner);
        this.numAnnotations = numAnnotations;
        annotations = new AnnotationStruct[numAnnotations];
        next = 0;
    }

    public Attributed getOwner()
    {
        return owner;
    }

    public void addAnnotation(AnnotationStruct annotation)
    {
        if(numAnnotations > 0)
            annotations[next++] = annotation;
    }

    public AnnotationStruct[] getAnnotations()
    {
        return annotations;
    }

    public String getName()
    {
        return name;
    }

    public int getAttributeType()
    {
        return name.equals("RuntimeVisibleAnnotations") ? 10 : 11;
    }

    public boolean isVisible()
    {
        return name.equals("RuntimeVisibleAnnotations");
    }

    public int getLenght()
    {
        int l = 2;
        int annotCount = annotations.length;
        for(int i = 0; i < annotCount; i++)
            l += annotations[i].getLenght();

        return l;
    }

    protected AnnotationStruct annotations[];
    protected int numAnnotations;
    protected int next;
    protected String name;
    protected Attributed owner;
}
