// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationFilter.java

package com.fmi.bytecode.annotations.tool.tool.indexing;

import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;

public interface AnnotationFilter
{

    public abstract boolean isPossitive();

    public abstract boolean isAccepted(AnnotationRecord annotationrecord);
}
