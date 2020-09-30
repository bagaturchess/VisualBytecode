package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.AnnotatableElementModel;
import com.fmi.bytecode.annotations.input.AnnotationModel;
import com.fmi.bytecode.parser.*;
import com.fmi.bytecode.parser.internal.AnnotationPairValue;
import com.fmi.bytecode.parser.internal.AnnotationStruct;

import java.util.*;


public class AnnotationModelImpl
    implements AnnotationModel
{

    public AnnotationModelImpl(AnnotationStruct annotation, boolean isVisible)
    {
        namedMembersMap = new HashMap();
        this.isVisible = isVisible;
        type = annotation.getType().toPlainString();
        retrieveMembers(annotation);
    }

    private void retrieveMembers(AnnotationStruct as)
    {
        String name;
        AnnotationPairValue pairValue;
        for(Iterator i = as.getElementValuePairs().entrySet().iterator(); i.hasNext(); namedMembersMap.put(name, new AnnotationNamedMemberImpl(name, pairValue, this)))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)(java.util.Map.Entry)i.next();
            name = (String)entry.getKey();
            pairValue = (AnnotationPairValue)entry.getValue();
        }

    }

    public AnnotatableElementModel getOwner()
    {
        return owner;
    }

    public String getTypeName()
    {
        return type;
    }

    public boolean isRuntimeVisible()
    {
        return isVisible;
    }

    public Map getNamedMembersMap()
    {
        return namedMembersMap;
    }

    public String toString()
    {
        return namedMembersMap.toString();
    }

    public boolean equals(Object obj)
    {
        if(obj == null || !(obj instanceof AnnotationModel))
            return false;
        AnnotationModel target = (AnnotationModel)obj;
        if(!ComparisonUtils.compareObjects(type, target.getTypeName()))
            return false;
        if(isVisible != target.isRuntimeVisible())
            return false;
        return ComparisonUtils.compareObjects(namedMembersMap, target.getNamedMembersMap());
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    private String type;
    private AnnotatableElementModel owner;
    private boolean isVisible;
    private Map namedMembersMap;
}
