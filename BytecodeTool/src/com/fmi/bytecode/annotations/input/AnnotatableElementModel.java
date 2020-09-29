package com.fmi.bytecode.annotations.input;

import java.util.Map;

public interface AnnotatableElementModel {
	/**
	 * delimeter is / for class name
	 */
	public String getName();
	public int getAccessFlags();
	public Map<String, AnnotationModel> getAnnotations();
}
