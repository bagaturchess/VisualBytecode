package com.fmi.bytecode.annotations.input;

import java.util.Map;

public interface MethodModel extends AnnotatableElementModel {
	public String getRawReturnType();
	
	public String[] getRawParams();
	
	public String[] getExceptions();
	
	/**
	 * Gets annotations declared for method's parameters	 
	 * The returned array is with Maps, which contains annotation type
     * as a key and <code>com.fmi.bytecode.annotations.input.AnnotationModel</code>
     * object as a value
	 * @return array with Maps
	 * @see com.fmi.bytecode.annotations.input.AnnotationModel
	 */
	public Map<String,AnnotationModel>[] getParameterAnnotations();
}
