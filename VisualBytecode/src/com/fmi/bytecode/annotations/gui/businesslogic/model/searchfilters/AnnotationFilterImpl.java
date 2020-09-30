package com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters;

import java.util.Map;

import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.MethodInfo;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationFilter;

public abstract class AnnotationFilterImpl implements AnnotationFilter {
    private AnnotationFilterModel filter;
    
    public AnnotationFilterImpl(AnnotationFilterModel filter) {
        this.filter = filter;
    }

    public abstract boolean isPossitive();

    public abstract boolean isAccepted(AnnotationRecord annotationRecord);
    
    protected boolean isParameterAnnotation(MethodInfo methodInfo, 
                                            String annotationName) {
        boolean result = false;
        Map<String, AnnotationRecord>[] parametersAnnotations = methodInfo.getParameterAnnotations();
        if (parametersAnnotations != null) {
            for (int i = 0; i < parametersAnnotations.length; i++) {
                Map<String, AnnotationRecord> parameterAnnotations = parametersAnnotations[i];
                if (parameterAnnotations != null) {
                    for (String annName : parameterAnnotations.keySet()) {
                        AnnotationRecord currAnnotation = parameterAnnotations.get(annName);
                        if (annotationName == null || currAnnotation.getTypeName().equals(annotationName)) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }

    protected boolean isMethodAnnotation(MethodInfo methodInfo, 
                                         String annotationName) {
        boolean result = false;
        Map<String, AnnotationRecord> annotations = methodInfo.getAnnotations();
        if (annotations != null) {
            for (String annName : annotations.keySet()) {
                AnnotationRecord currAnnotation = annotations.get(annName);
                if (currAnnotation.getTypeName().equals(annotationName)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public AnnotationFilterModel getFilter() {
        return filter;
    }
}
