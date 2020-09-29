// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ClassFileAnnotationsIndexImpl.java

package com.fmi.bytecode.annotations.tool.indexing;

import com.fmi.bytecode.annotations.element.ClassInfo;
import java.util.ArrayList;

// Referenced classes of package com.fmi.bytecode.annotations.tool.indexing:
//            AnnotationsIndexImpl

public class ClassFileAnnotationsIndexImpl extends AnnotationsIndexImpl
{

    public ClassFileAnnotationsIndexImpl(ClassInfo _classInfo)
    {
        distibuteAnnotations(_classInfo, new ArrayList());
        createTree();
    }
}
