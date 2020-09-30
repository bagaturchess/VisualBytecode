package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.annotations.input.ConstructorModel;
import com.fmi.bytecode.parser.internal.MethodInfo;


public class ConstructorModelImpl extends MethodModelImpl
    implements ConstructorModel
{

    public ConstructorModelImpl(MethodInfo info, ClassModel classModel)
    {
        super(info, classModel);
    }

    protected void setReturnType(MethodInfo methodInfo)
    {
        rawReturnType = "void";
    }
}
