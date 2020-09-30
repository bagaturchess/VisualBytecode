package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.annotations.input.FieldModel;
import com.fmi.bytecode.parser.internal.FieldInfo;


public class FieldModelImpl extends ClassMemberModelImpl
    implements FieldModel
{

    public FieldModelImpl(FieldInfo fieldInfo, ClassModel classModel)
    {
        super(fieldInfo, classModel);
        rawType = fieldInfo.getDescriptor();
    }

    public String getRawType()
    {
        return rawType;
    }

    private String rawType;
}
