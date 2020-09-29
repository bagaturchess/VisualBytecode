package com.fmi.bytecode.annotations.input.adapter;

import com.fmi.bytecode.annotations.input.AnnotatableElementModel;
import com.fmi.bytecode.parser.*;

import java.util.HashMap;
import java.util.Map;

public class AnnotatableElementModelImpl
    implements AnnotatableElementModel
{

    public AnnotatableElementModelImpl(MemberInfo memberInfo)
    {
        annotationsMap = new HashMap();
        name = memberInfo.getName();
        modifiers = memberInfo.getAccessFlags();
        parseAnnotations(memberInfo.getAttributesArray());
    }

    public AnnotatableElementModelImpl(ClassFile classInfo)
    {
        annotationsMap = new HashMap();
        name = classInfo.getName();
        parseAnnotations(classInfo.getAttributesArray());
    }

    public String getName()
    {
        return name;
    }

    public int getAccessFlags()
    {
        return modifiers;
    }

    public Map getAnnotations()
    {
        return annotationsMap;
    }

    protected void parseAnnotations(AttributeInfo bmAttributes[])
    {
        AttributeInfo aattributeinfo[] = bmAttributes;
        int i = 0;
        for(int j = aattributeinfo.length; i < j; i++)
        {
            AttributeInfo attr = aattributeinfo[i];
            int attrType = attr.getAttributeType();
            if(attrType == 10 || attrType == 11)
            {
                AnnotationAttribute bmAnnotAttr = (AnnotationAttribute)attr;
                wrapAnnotations(bmAnnotAttr.getAnnotations(), bmAnnotAttr.isVisible(), annotationsMap);
                return;
            }
        }

    }

    protected void wrapAnnotations(AnnotationStruct bmAnnotations[], boolean isVisible, Map annotationsMap)
    {
        AnnotationStruct aannotationstruct[] = bmAnnotations;
        int i = 0;
        for(int j = aannotationstruct.length; i < j; i++)
        {
            AnnotationStruct bmAnnotation = aannotationstruct[i];
            AnnotationModelImpl annotRec = new AnnotationModelImpl(bmAnnotation, isVisible);
            annotationsMap.put(annotRec.getTypeName(), annotRec);
        }

    }

    private String name;
    private int modifiers;
    private Map annotationsMap;
}
