package com.fmi.bytecode.annotations.tool.element.impl;

import static com.fmi.bytecode.annotations.tool.util.ConvertionUtils.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fmi.bytecode.annotations.input.AnnotationModel;
import com.fmi.bytecode.annotations.input.MethodModel;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.util.ComparisonUtils;

/**
 * Bytecode library MethodInfo class wrapper that implements 
 * the <code>com.fmi.bytecode.annotations.tool.element.MethodInfo<code> interface.
 * It extends the <code>com.fmi.bytecode.annotations.tool.element.ConstructorInfo</code>
 * by adding a return type 
 * 
 * @author Krasimir Topchiyski
 */
public class MethodInfoImpl extends ElementInfoImpl implements com.fmi.bytecode.annotations.tool.element.MethodInfo {
	
	private ClassInfo owner;
	private String rawReturnType;	
	private String returnType;
	private String canonicalReturnType;
	private String genericReturnType;
	private boolean isReturnGeneric;
	private String[] exceptions;
	private String[] rawParams;
	private String[] parameters;
	private String[] canonicalParameters;
	private Map<String, AnnotationRecord>[] parameterAnnotations;
	private boolean[] genericParamsFlags;
	private String[] genericParams;
	private String signature;
	
	public MethodInfoImpl(MethodModel methodModel, ClassInfo owner) {
		super(methodModel.getAnnotations());

		this.owner = owner;
		this.name = methodModel.getName();
		this.modifiers = methodModel.getAccessFlags();
		this.rawParams = methodModel.getRawParams();
		this.exceptions = methodModel.getExceptions();
		rawReturnType = methodModel.getRawReturnType();
		
		copyParamterAnnotation(methodModel);

	}
	
	private void parseGenericReturnType(){
		if(signature==null){
			isReturnGeneric = false;
			genericReturnType = getCanonicalReturnType();
		}else{			
			String genRt = getMethodReturnType(signature);
			isReturnGeneric = (genRt!=null);
			if(isReturnGeneric){
				genericReturnType = genRt; 
			} else {
				genericReturnType = getCanonicalReturnType();
			}			
		}		
	}

	public String getReturnType() {
		if(returnType == null){
			returnType = convertToJLS(rawReturnType);
		}
		return returnType;
	}

	public String getCanonicalReturnType() {
		if(canonicalReturnType == null){
			canonicalReturnType = convertToCanonical(rawReturnType);
		}
		return canonicalReturnType;
	}

	public String getGenericReturnType() {
		if(genericReturnType == null){
			parseGenericReturnType();
		}
		return genericReturnType;
	}

	public boolean isReturnTypeParametrised() {
		if(genericReturnType == null){
			parseGenericReturnType();
		}
		return isReturnGeneric;
	}

	private void parseGenericParameters(String signature) {

		genericParams = getCanonicalParameters().clone();
		genericParamsFlags = new boolean[genericParams.length];
		Arrays.fill(genericParamsFlags, false);

		if (signature != null) {
			String[] params = getMethodParams(signature, genericParamsFlags);
			for (int j = 0; j < genericParamsFlags.length; j++) {
				if (genericParamsFlags[j]) {
					genericParams[j] = params[j];
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void copyParamterAnnotation(MethodModel methodModel) {
		Map<String,AnnotationModel>[] paramAnnotation = methodModel.getParameterAnnotations();
		int paramsCount = paramAnnotation.length;
		parameterAnnotations = new HashMap[paramsCount];
		for (int i=0; i<paramsCount; i++) {
			parameterAnnotations[i] = convertParameterAnnotationModel(paramAnnotation[i]);
		}
	}

	private Map<String, AnnotationRecord> convertParameterAnnotationModel(
			Map<String, AnnotationModel> annotationModels) {

		Map<String, AnnotationRecord> result = new HashMap<String, AnnotationRecord>();

		Iterator keys = annotationModels.keySet().iterator();
		while (keys.hasNext()) {
			String annotationName = (String) keys.next();
			com.fmi.bytecode.annotations.input.AnnotationModel annotationModel = (com.fmi.bytecode.annotations.input.AnnotationModel) annotationModels
					.get(annotationName);

			AnnotationRecordImpl annotationRecord = new AnnotationRecordImpl(
					annotationModel, this);
			result.put(annotationRecord.getTypeName(), annotationRecord);
		}
		return result;
	}

	public String[] getParameters() {
		if (parameters == null) {
			ArrayList<String> params = new ArrayList<String>();
			for (String p : rawParams) {
				String rt = convertToJLS(p);
				params.add(rt);
			}
			parameters = params.toArray(new String[params.size()]);
		}
		return parameters;
	}

	public String[] getCanonicalParameters() {
		if (canonicalParameters == null) {
			ArrayList<String> canonicalParams = new ArrayList<String>();
			for (String p : rawParams) {
				String crt = convertToCanonical(p);
				canonicalParams.add(crt);
			}
			canonicalParameters = canonicalParams
					.toArray(new String[canonicalParams.size()]);
		}
		return canonicalParameters;
	}

	public String[] getGenericParameters() {
		if (genericParams == null) {
			parseGenericParameters(signature);
		}
		return genericParams;
	}

	public boolean[] getParametrisedParameters() {
		if (genericParamsFlags == null) {
			parseGenericParameters(signature);
		}
		return genericParamsFlags;
	}

	public String[] getExceptions() {
		return exceptions;
	}

	public ClassInfo getOwner() {
		return owner;
	}

	public Map<String, AnnotationRecord>[] getParameterAnnotations() {
		return parameterAnnotations;
	}
	
	public boolean equals(Object obj) {
		
		if (!super.equals(obj)) {
			return false;
		}
        
        if(obj instanceof com.fmi.bytecode.annotations.tool.element.MethodInfo){
        	
        	MethodInfoImpl mi = (MethodInfoImpl)obj;
            
			if (!ComparisonUtils.compareObjects(signature, mi.signature)) {
				return false;
			}

			if (!ComparisonUtils.compareUnorderedArrays(mi.getExceptions(),
					exceptions)) {
				return false;
			}

			if (!ComparisonUtils
					.compareUnorderedArrays(rawParams, mi.rawParams)) {
				return false;
			}

			if (!ComparisonUtils.compareUnorderedArrays(mi
					.getParameterAnnotations(), parameterAnnotations)) {
				return false;
			}

        	if(!ComparisonUtils.compareObjects(rawReturnType, mi.rawReturnType)) {
                return false;
            } 
            
        	return true;
        }
        
        return false;
    }
	
	public int hashCode(){
		return super.hashCode();
	}
}
