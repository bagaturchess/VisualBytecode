package com.fmi.bytecode.annotations.tool.element;

/**
 * The <code>FieldInfo</code> interface represents a java class field.
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface FieldInfo extends ElementInfo, ClassMemberInfo {
	
	/**
	 * Gets the field's type as a fully qualified class name
	 * (the returned String is the same as the one of the java.lang.Class.getName()).
	 * 
	 * @return fully qualified Java language standard class name
	 */
	public String getType();
	
	/**
	 * Gets the field's type as a fully qualified class name
	 * (the returned String is the same as the one of the java.lang.Class.getCanonicalName()).
	 * 
	 * @return fully qualified Java language standard class name
	 */
	public String getCanonicalType();

	
	/**
	 * Gets the field's type as a Java language standard type.
	 * If the type is a generic type, it is returned, as it is declared 
	 * (the returned String is the same as the one of the java.lang.reflect.Field.getGenericType().toString()).
	 * If the type is not a generic type, the returned result is the same as 
	 * the one of the getCanonicalType() method. 
	 * 
	 * @return fully qualified Java language standard name
	 */
	public String getGenericType();
	
    /**
	 * Checks if the current field is declared with generic type
	 * @return true if the field is generic, otherwise false
	 */
    public boolean isParametrised();

}

