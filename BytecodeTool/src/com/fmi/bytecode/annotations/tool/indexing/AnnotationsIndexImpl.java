// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AnnotationsIndexImpl.java

package com.fmi.bytecode.annotations.tool.indexing;

import com.fmi.bytecode.annotations.element.*;
import java.util.*;

// Referenced classes of package com.fmi.bytecode.annotations.tool.indexing:
//            AnnotationsIndex, AnnotationFilter, TreeFactory

public class AnnotationsIndexImpl
    implements AnnotationsIndex
{

    AnnotationsIndexImpl()
    {
        rootClasses = new ArrayList();
        rootPackages = new ArrayList();
        classes = new HashMap();
        annotations = new HashMap();
        classAnnotations = new HashMap();
        constructorAnnotations = new HashMap();
        fieldAnnotations = new HashMap();
        methodAnnotations = new HashMap();
        methodParamAnnotations = new HashMap();
    }

    public Map getRepresentedClasses()
    {
        return Collections.unmodifiableMap(classes);
    }

    public Map getAnnotations()
    {
        return Collections.unmodifiableMap(annotations);
    }

    public Map getClassAnnotations()
    {
        return Collections.unmodifiableMap(classAnnotations);
    }

    public Map getConstructorAnnotations()
    {
        return Collections.unmodifiableMap(constructorAnnotations);
    }

    public Map getFieldAnnotations()
    {
        return Collections.unmodifiableMap(fieldAnnotations);
    }

    public Map getMethodAnnotations()
    {
        return Collections.unmodifiableMap(methodAnnotations);
    }

    public Map getMethodParamAnnotations()
    {
        return Collections.unmodifiableMap(methodParamAnnotations);
    }

    protected void distibuteAnnotations(ClassInfo classInfo, List annFilters)
    {
        int acceptionsCount = 0;
        acceptionsCount += distibuteClassAnnotations(classInfo, annFilters);
        acceptionsCount += distibuteConstructorAnnotations(classInfo, annFilters);
        acceptionsCount += distibuteFieldAnnotations(classInfo, annFilters);
        acceptionsCount += distibuteMethodAnnotations(classInfo, annFilters);
        acceptionsCount += distibuteMethodParamAnnotations(classInfo, annFilters);
        if(acceptionsCount > 0 || annFilters.size() == 0)
            classes.put(classInfo.getName(), classInfo);
    }

    private int distibuteClassAnnotations(ClassInfo classInfo, List annFilters)
    {
        return addAnnotation(classInfo.getAnnotations(), classAnnotations, annFilters);
    }

    private int distibuteConstructorAnnotations(ClassInfo classInfo, List annFilters)
    {
        return addAnnotations(classInfo.getConstructors(), constructorAnnotations, annFilters);
    }

    private int distibuteFieldAnnotations(ClassInfo classInfo, List annFilters)
    {
        return addAnnotations(classInfo.getFields(), fieldAnnotations, annFilters);
    }

    private int distibuteMethodAnnotations(ClassInfo classInfo, List annFilters)
    {
        return addAnnotations(classInfo.getMethods(), methodAnnotations, annFilters);
    }

    private int distibuteMethodParamAnnotations(ClassInfo classInfo, List annFilters)
    {
        int acceptionsCount = 0;
        MethodInfo mis[] = classInfo.getMethods();
        for(int i = 0; i < mis.length; i++)
        {
            Map methodParamsAnn[] = mis[i].getParameterAnnotations();
            for(int j = 0; j < methodParamsAnn.length; j++)
            {
                Map source = methodParamsAnn[j];
                acceptionsCount += addAnnotation(source, methodParamAnnotations, annFilters);
            }

        }

        return acceptionsCount;
    }

    protected int addAnnotations(ElementInfo eis[], Map target, List annFilters)
    {
        int acceptionsCount = 0;
        for(int i = 0; i < eis.length; i++)
            acceptionsCount += addAnnotation(eis[i].getAnnotations(), target, annFilters);

        return acceptionsCount;
    }

    protected int addAnnotation(Map source, Map target, List annFilters)
    {
        int acceptionsCount = 0;
        for(Iterator iterator = source.keySet().iterator(); iterator.hasNext();)
        {
            String annName = (String)iterator.next();
            AnnotationRecord ann = (AnnotationRecord)source.get(annName);
            if(isAnnotationAccepted(ann, annFilters))
            {
                acceptionsCount++;
                addAnnotation(ann, target);
                addAnnotation(ann, annotations);
            }
        }

        return acceptionsCount;
    }

    private void addAnnotation(AnnotationRecord ann, Map target)
    {
        List list = (List)target.get(ann.getTypeName());
        if(list == null)
        {
            list = new ArrayList();
            target.put(ann.getTypeName(), list);
        }
        list.add(ann);
    }

    private boolean isAnnotationAccepted(AnnotationRecord annotation, List annFilters)
    {
        boolean result = annFilters.size() == 0;
        for(Iterator iterator = annFilters.iterator(); iterator.hasNext();)
        {
            AnnotationFilter filter = (AnnotationFilter)iterator.next();
            if(filter.isAccepted(annotation))
                result = true;
            if(!filter.isPossitive() && !filter.isAccepted(annotation))
            {
                result = false;
                break;
            }
        }

        return result;
    }

    public List getRootClasses()
    {
        return rootClasses;
    }

    public List getRootPackages()
    {
        return rootPackages;
    }

    public void createTree()
    {
        Object result[] = TreeFactory.createTree(classes);
        rootClasses = (List)result[0];
        rootPackages = (List)result[1];
    }

    private List rootClasses;
    private List rootPackages;
    private Map classes;
    private Map annotations;
    protected Map classAnnotations;
    protected Map constructorAnnotations;
    protected Map fieldAnnotations;
    protected Map methodAnnotations;
    protected Map methodParamAnnotations;
}
