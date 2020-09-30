package com.fmi.bytecode.annotations.tool.element;


import java.util.Map;

/**
 * The <code>MethodInfo</code> interface represents a java method.
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface MethodInfo extends ElementInfo, ClassMemberInfo {
    
	/**
	 * Gets annotations declared for method's parameters	 
	 * The returned array is with Maps, which contains annotation type
     * as a key and <code>com.fmi.bytecode.annotations.tool.Annotation</code>
     * object as a value
	 * @return array with Maps
	 * @see com.fmi.bytecode.annotations.tool.element.Annotation
	 */
	public Map<String,AnnotationRecord>[] getParameterAnnotations();
        
	/**
	 * Gets all method parameter types, in the order they are declared
	 * (each parameter type is the same as the one of the java.lang.Class.getName())
	 * @return array with parameter types, in the same order as declared
	 */
	public String[] getParameters();
	
	/**
	 * Gets all method parameter types, in the order they are declared
	 * (each parameter type is the same as the one of the java.lang.Class.getCanonicalName())
	 * @return array with parameter types, in the same order as declared
	 */
	public String[] getCanonicalParameters();
	
	/**
	 * Gets all method parameter types, including the generic signatures, if existing.
	 * (the returned String for a generic parameter is the same as the one 
	 * of the java.lang.reflect.Method.getGenericParameterTypes()[x].toString())
	 * If the parameter is not declared with generic type, the value for it should be 
	 * the same as from the getCanonicalParameters() method.
	 * 
	 * @return array with parameter types, with generics (if available), in the same order as declared
	 */
	public String[] getGenericParameters();
	
	/**
	 * Gets flags for all method parameters. Each flag shows, if the current parameter is 
	 * declared with generic type or not.
	 * The number of the flags is the same as of the parameters and in the same order as 
	 * parameters are declared.
	 * 
	 * @return array with booleans, for each parameter - true if it is declared with generic,
	 * otherwise false
	 */
	public boolean[] getParametrisedParameters();
	
	/**
	 * Gets all declared exceptions for the method
	 * @return Array of exceptions
	 */
	public String[] getExceptions();
	
	/**
	 * Gets the type of the returned result of the method 
	 * (the returned String is the same as the one of the java.lang.Class.getName())
	 * @return fully qualified Java language standard class name, representing the method's return type
	 */
	public String getReturnType();
	
	/**
	 * Gets the type of the returned result of the method 
	 * (the returned String is the same as the one of the java.lang.Class.getCanonicalName())
	 * @return fully qualified Java language standard class name, representing the method's return type
	 */
	public String getCanonicalReturnType();

	
	/**
	 * Gets the type of the returned result of the method, as a Java language standard type. 
	 * If the type is a generic type, it is returned, as it is declared
	 * (the returned String is the same as the one of the java.lang.reflect.Field.getGenericType().toString()).
	 * If the type is not a generic type, the returned result is the same as 
	 * the one of the getCanonicalReturnType() method.
	 * @return fully qualified Java language standard name, representing the method's return type
	 */
	public String getGenericReturnType();
	
    /**
	 * Checks if the return type of the current method is declared with generic type
	 * @return true if the return type is generic, otherwise false
	 */
    public boolean isReturnTypeParametrised();

}

