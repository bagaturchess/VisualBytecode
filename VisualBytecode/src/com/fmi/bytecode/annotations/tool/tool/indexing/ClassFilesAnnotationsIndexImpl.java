// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassFilesAnnotationsIndexImpl.java

package com.fmi.bytecode.annotations.tool.tool.indexing;

import java.util.List;

import com.fmi.bytecode.annotations.tool.element.ClassInfo;

// Referenced classes of package com.fmi.bytecode.annotations.tool.tool.indexing:
//            AnnotationsIndexImpl

public class ClassFilesAnnotationsIndexImpl extends AnnotationsIndexImpl
{

    public ClassFilesAnnotationsIndexImpl()
    {
    }

    public void addClassFileAnnotations(ClassInfo ci, List annFilters)
    {
        distibuteAnnotations(ci, annFilters);
    }

    public void createTree()
    {
        super.createTree();
    }
}
