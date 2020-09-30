package com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.element.ClassMemberInfo;
import com.fmi.bytecode.annotations.tool.element.ConstructorInfo;
import com.fmi.bytecode.annotations.tool.element.ElementInfo;
import com.fmi.bytecode.annotations.tool.element.FieldInfo;
import com.fmi.bytecode.annotations.tool.element.MethodInfo;

public class NegativeAnnotationFilter extends AnnotationFilterImpl {

    private Set<String> rejectedClasses;

    public NegativeAnnotationFilter(AnnotationFilterModel filter) {
        super(filter);
        rejectedClasses = new HashSet<String>();
    }
    
    public boolean isPossitive() {
        return false;
    }
    
    public boolean isAccepted(AnnotationRecord annotationRecord) {
        boolean result = false;
        ClassInfo clazz = getAnnotationClassOwner(annotationRecord);
        String className = clazz.getName();
        if (rejectedClasses.contains(className)) {
            result = false;
        } else {
            boolean accepted = negativeAccept(clazz);
            if (accepted) {
                result = true;
            } else {
                rejectedClasses.add(className);
                result = false;
            }
        }
        return result;
    }
    
    private boolean negativeAccept(ClassInfo annClass) {
        boolean result = true;
        String filterAnnotationName = getFilter().getAnnotationName();
        
        List<FilterLevelType> levels = getFilter().getLevel();
        for (FilterLevelType level : levels) {
            if (FilterLevelType.CLASS.equals(level)) {
                if (containsInClass(filterAnnotationName, annClass)) {
                    result = false;
                    break;
                }
            } else if (FilterLevelType.CONSTRUCTOR.equals(level)) {
                if (containsInConstructors(filterAnnotationName, annClass.getConstructors())) {
                    result = false;
                    break;
                }
            } else if (FilterLevelType.FIELD.equals(level)) {
                if (containsInFields(filterAnnotationName, annClass.getFields())) {
                    result = false;
                    break;
                }
            } else if (FilterLevelType.METHOD.equals(level)) {
                if (containsInMethods(filterAnnotationName, annClass.getMethods())) {
                    result = false;
                    break;
                }
            } else if (FilterLevelType.PARAMETER.equals(level)) {
                if (containsInMethodsParams(filterAnnotationName, annClass.getMethods())) {
                    result = false;
                    break;
                }
            }
        }
            
        return result;
    }
    
    private ClassInfo getAnnotationClassOwner(AnnotationRecord annotationRecord) {
        ClassInfo clazz = null;
        ElementInfo element = annotationRecord.getOwner();
        if (element instanceof ClassInfo) {
            clazz = (ClassInfo) element;
        } else if (element instanceof ClassMemberInfo) {
            clazz = (ClassInfo) ((ClassMemberInfo) element).getOwner();
        } else {
            throw new IllegalStateException();
        }
        return clazz;
    }

    private boolean containsInClass(String searchAnnName, ClassInfo annClass) {
        Map<String, AnnotationRecord> anns = annClass.getAnnotations();
        return containsAnnotation(anns, searchAnnName);
    }

    private boolean containsInConstructors(String searchAnnName, 
                                           ConstructorInfo[] constructorInfos) {
        return containsAnnotation(constructorInfos, searchAnnName);
    }

    private boolean containsInFields(String searchAnnName, FieldInfo[] fieldInfos) {
        return containsAnnotation(fieldInfos, searchAnnName);
    }

    private boolean containsInMethods(String searchAnnName, 
                                      MethodInfo[] methodInfos) {
        return containsAnnotation(methodInfos, searchAnnName);
    }

    private boolean containsInMethodsParams(String searchAnnName, 
                                            MethodInfo[] methodInfos) {
        boolean result = false;
        for (int i = 0; i < methodInfos.length; i++) {
            MethodInfo method = methodInfos[i];
            if (isParameterAnnotation(method, searchAnnName)) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    private boolean containsAnnotation(ElementInfo[] elements, String searchAnnName) {
        boolean result = false;
        for (int i = 0; i < elements.length; i++) {
            ElementInfo element = elements[i];
            Map<String, AnnotationRecord> anns = element.getAnnotations();
            if (containsAnnotation(anns, searchAnnName)) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    private boolean containsAnnotation(Map<String, AnnotationRecord> anns, String searchAnnName) {
        boolean result = false;
        for (String annName : anns.keySet()) {
            if (searchAnnName == null || annName.equals(searchAnnName)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
