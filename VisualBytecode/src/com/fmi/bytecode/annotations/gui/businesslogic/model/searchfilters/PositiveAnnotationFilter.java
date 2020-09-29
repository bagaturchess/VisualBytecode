package com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.element.ConstructorInfo;
import com.fmi.bytecode.annotations.element.ElementInfo;
import com.fmi.bytecode.annotations.element.FieldInfo;
import com.fmi.bytecode.annotations.element.MethodInfo;

import java.util.List;

public class PositiveAnnotationFilter extends AnnotationFilterImpl {
    
    public PositiveAnnotationFilter(AnnotationFilterModel filter) {
        super(filter);
    }
    
    public boolean isPossitive() {
        return true;
    }
    
    public boolean isAccepted(AnnotationRecord annotationRecord) {
        return possitiveAccept(annotationRecord);
    }
    
    protected boolean possitiveAccept(AnnotationRecord annotationRecord) {
        boolean result = false;
        String filterAnnotationName = getFilter().getAnnotationName();
        String annotationName = annotationRecord.getTypeName();
        if (filterAnnotationName == null
                || filterAnnotationName.equals(annotationName)) {
            
            ElementInfo elInfo = annotationRecord.getOwner();
            List<FilterLevelType> levels = getFilter().getLevel();
            for (FilterLevelType level : levels) {
                if (FilterLevelType.CLASS.equals(level)) {
                    if (elInfo instanceof ClassInfo) {
                        result = true;
                        break;
                    }
                } else if (FilterLevelType.CONSTRUCTOR.equals(level)) {
                    if (elInfo instanceof ConstructorInfo) {
                        result = true;
                        break;
                    }
                } else if (FilterLevelType.FIELD.equals(level)) {
                    if (elInfo instanceof FieldInfo) {
                        result = true;
                        break;
                    }
                } else if (FilterLevelType.METHOD.equals(level)) {
                    if (elInfo instanceof MethodInfo) {
                        MethodInfo methodInfo = (MethodInfo) elInfo;
                        if (isMethodAnnotation(methodInfo, annotationRecord.getTypeName())) {
                            result = true;
                            break;
                        }
                    }
                } else if (FilterLevelType.PARAMETER.equals(level)) {
                    if (elInfo instanceof MethodInfo) {
                        MethodInfo methodInfo = (MethodInfo) elInfo;
                        if (isParameterAnnotation(methodInfo, annotationRecord.getTypeName())) {
                            result = true;
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
}
