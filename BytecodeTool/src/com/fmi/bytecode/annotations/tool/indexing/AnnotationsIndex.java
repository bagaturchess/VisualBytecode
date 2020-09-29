// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationsIndex.java

package com.fmi.bytecode.annotations.tool.indexing;

import java.util.List;
import java.util.Map;

public interface AnnotationsIndex
{

    public abstract List getRootClasses();

    public abstract List getRootPackages();

    public abstract Map getRepresentedClasses();

    public abstract Map getAnnotations();

    public abstract Map getConstructorAnnotations();

    public abstract Map getClassAnnotations();

    public abstract Map getMethodAnnotations();

    public abstract Map getFieldAnnotations();

    public abstract Map getMethodParamAnnotations();
}
