package com.fmi.bytecode.annotations.input;

public interface ClassModel extends AnnotatableElementModel {
	/**
	 * delimeter is /
	 */
	public String getSuperClassName();
	/**
	 * delimeter is /
	 */
	public String getPackageName();
	public boolean isParametrised();
	public boolean isAnnotation();
	public boolean isInterface();
	public boolean isEnum();
	
	/**
	 * null or array
	 */
	public String[] getInterfaceNames();
	
	public MethodModel[] getMethods();
	public FieldModel[] getFields();
	public ConstructorModel[] getConstructors();
}
