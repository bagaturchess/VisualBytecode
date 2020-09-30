package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.annotations.input.MethodModel;
import com.fmi.bytecode.parser.*;
import com.fmi.bytecode.parser.internal.AttributeInfo;
import com.fmi.bytecode.parser.internal.CPInfo;
import com.fmi.bytecode.parser.internal.ExceptionsAttribute;
import com.fmi.bytecode.parser.internal.MethodInfo;
import com.fmi.bytecode.parser.internal.ParameterAnnotationAttribute;

import java.util.HashMap;
import java.util.Map;


public class MethodModelImpl extends ClassMemberModelImpl
    implements MethodModel
{

    public MethodModelImpl(MethodInfo methodInfo, ClassModel classModel)
    {
        super(methodInfo, classModel);
        parameterAnnotations = new HashMap[0];
        setReturnType(methodInfo);
        rawParams = methodInfo.getArguments();
        ExceptionsAttribute exAttr = methodInfo.getExceptionsAttribute();
        if(exAttr != null)
        {
            CPInfo exInfos[] = exAttr.getExceptionsArray();
            int l = exInfos.length;
            exceptions = new String[l];
            for(int i = 0; i < l; i++)
                exceptions[i] = exInfos[i].toPlainString();

        } else
        {
            exceptions = new String[0];
        }
        parseParameterAnnotations(methodInfo.getAttributesArray());
    }

    protected void parseParameterAnnotations(AttributeInfo attributes[])
    {
        AttributeInfo aattributeinfo[] = attributes;
        int j = 0;
        for(int k = aattributeinfo.length; j < k; j++)
        {
            AttributeInfo attr = aattributeinfo[j];
            int attrType = attr.getAttributeType();
            if(attrType == 12 || attrType == 13)
            {
                ParameterAnnotationAttribute a = (ParameterAnnotationAttribute)attr;
                boolean isVisible = a.isVisible();
                com.fmi.bytecode.parser.internal.AnnotationStruct allParamAnnot[][] = a.getAllAnnotations();
                int paramsCount = allParamAnnot.length;
                parameterAnnotations = new HashMap[paramsCount];
                for(int i = 0; i < paramsCount; i++)
                {
                    parameterAnnotations[i] = new HashMap();
                    wrapAnnotations(allParamAnnot[i], isVisible, parameterAnnotations[i]);
                }

                return;
            }
        }

    }

    protected void setReturnType(MethodInfo methodInfo)
    {
        rawReturnType = methodInfo.getReturnType();
    }

    public String getRawReturnType()
    {
        return rawReturnType;
    }

    public String[] getRawParams()
    {
        return rawParams;
    }

    public String[] getExceptions()
    {
        return exceptions;
    }

    public Map[] getParameterAnnotations()
    {
        return parameterAnnotations;
    }

    protected String rawReturnType;
    private String exceptions[];
    private String rawParams[];
    private Map parameterAnnotations[];
}
