package com.fmi.bytecode.annotations.element.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ElementInfo;
import com.fmi.bytecode.annotations.input.AnnotationModel;
import com.fmi.bytecode.annotations.util.ComparisonUtils;

/**
 * Annotation processing general functionality for bytecode 
 * library wrapper classes: ClassInfoBM, MethodInfoBM, 
 * ConstructorInfoBM, FieldInfoBM.
 * 
 * @author Krasimir Topchiyski
 */
public abstract class ElementInfoImpl implements ElementInfo {
	
	protected Map<String, AnnotationRecord> annotations;
	protected String name;
	protected int modifiers;
	
	public ElementInfoImpl(Map<String, AnnotationModel> annotationsModel) {
		annotations = annotationsModel == null ? new HashMap<String, AnnotationRecord>() : convertAnnotationModel(annotationsModel);
	}

	private Map<String, AnnotationRecord> convertAnnotationModel(Map<String, AnnotationModel> annotationModels) {
		
		Map<String, AnnotationRecord> result = new HashMap<String, AnnotationRecord>();
		
		Iterator keys = annotationModels.keySet().iterator();
		while(keys.hasNext())
		{
			String annotationName = (String)keys.next();
			com.fmi.bytecode.annotations.input.AnnotationModel annotationModel
				= (com.fmi.bytecode.annotations.input.AnnotationModel) annotationModels.get(annotationName);
			
			AnnotationRecordImpl annotationRecord = new AnnotationRecordImpl(annotationModel, this);
			result.put(annotationRecord.getTypeName(), annotationRecord);
		}	
		return result;
	}
	
	public String getName(){
		return name;
	}

	public int getModifiers(){
		return modifiers;
	}

	public Map<String, AnnotationRecord> getAnnotations() {
		return annotations;
	}

	public AnnotationRecord getAnnotation(String annotationType) {
		return annotations.get(annotationType);
	}
	
	public boolean isSynthetic(){
		return (SYNTHETIC_MODIFIER & getModifiers()) != 0;
	}
	
	public boolean equals(Object obj) {
      	if (this == obj) {
		    return true;
		}
		if (obj instanceof ElementInfo) {
			ElementInfo el = (ElementInfo)obj;
	        if(el.getModifiers() != getModifiers()){
	        	return false;
	        }
	        
	        if(!ComparisonUtils.compareObjects(getName(),el.getName())){
	        	return false;
	        }
	        
	        if(!el.getAnnotations().equals(annotations)){
	            return false;
	        }       
	        return true;
	    }    
        return false;
    }

	public String toString() {
		String result = "";
		result += "ElementName: " + name + ", annotations:" + annotations;
		return result;
	}
}
