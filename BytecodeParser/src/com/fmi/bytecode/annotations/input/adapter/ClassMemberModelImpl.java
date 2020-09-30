package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.parser.internal.MemberInfo;
import com.fmi.bytecode.parser.internal.SignatureAttribute;


public class ClassMemberModelImpl extends AnnotatableElementModelImpl
{

    public ClassMemberModelImpl(MemberInfo memberInfo, ClassModel _classModel)
    {
        super(memberInfo);
        classModel = _classModel;
        isGeneric = memberInfo.getAttribute("Signature") != null;
        if(isGeneric)
            signature = ((SignatureAttribute)memberInfo.getAttribute("Signature")).getSignature();
    }

    public ClassModel getOwner()
    {
        return classModel;
    }

    public String getSignature()
    {
        return signature;
    }

    public boolean isGeneric()
    {
        return isGeneric;
    }

    private ClassModel classModel;
    private String signature;
    private boolean isGeneric;
}
