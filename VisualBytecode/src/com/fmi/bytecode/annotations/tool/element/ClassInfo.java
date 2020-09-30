package com.fmi.bytecode.annotations.tool.element;

import java.util.Map;

import com.fmi.bytecode.annotations.tool.file.FileInfo;

/**
 * The <code>ClassInfo</code> interface represents a java class/interface.  
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface ClassInfo extends ElementInfo {


    /**
	 * Gets the superclass of the current class.
     * The returned result will be a ClassInfo object, representing the superclass.
     * NULL will be retuned, if there is no super declared or the superclass is not 
     * a part of the multitude of classes processed by the tool.
     *
	 * @return an <code>com.fmi.bytecode.annotations.tool.element.ClassInfo</code> object 
     * or NULL, if there is no super declared or the superclass is not part of 
     * the processed multitude of classes
     * @see com.fmi.bytecode.annotations.tool.element.ClassInfo
	 */
	public ClassInfo getSuperclass();

	/**
	 * Gets the superclass name of the current class 
	 * @return fully qualified class name of the superclass or null if there is no super
	 */
	public String getSuperclassName();
	
	/**
	 * Gets all implemented interfaces' names
	 * 
	 * @return an array with implemented interfaces, null if there are no interfaces to implement
	 */
	public String[] getInterfacesNames();

	/**
	* Gets all implemented interfaces.
	* The returned result is a Map with a fully qualified interface name 
	* as a key and a ClassInfo object as a value. The value could be NULL,
	* if the superinterface is not a part of the multitude of classes processed by the tool.
	* If there are no superinterfaces, an empty Map will be returned.
	* 
	* @return an Map 
	*/
    public Map<String,ClassInfo> getInterfaces();
        
	/**
	 * Gets the package name for the current class. 
	 * @return a fully qalified package name	 
	 */
	public String getPackage();
	
	/**
	 * Gets all class's methods
	 * @return array with <code>com.fmi.bytecode.annotations.tool.element.MethodInfo</code> objects
	 * @see com.fmi.bytecode.annotations.tool.element.MethodInfo
	 */
	public MethodInfo[] getMethods();
        
	/**
	 * Gets all class' methods with the given name
     * @param methodName method name
	 * @return array with <code>com.fmi.bytecode.annotations.tool.element.MethodInfo</code> objects. 
     * If there is no class method with such a name, NULL is returned.
	 * @see com.fmi.bytecode.annotations.tool.element.MethodInfo
	 */
	public MethodInfo[] getMethod(String methodName);

	/**
	 * Gets all class' methods with name, starting with the given string
     * @param methodName starting string, which is used for method filtering 
	 * @return array with <code>com.fmi.bytecode.annotations.tool.element.MethodInfo</code> objects. 
     * If there is no class method with such a name, NULL is returned.
	 * @see com.fmi.bytecode.annotations.tool.element.MethodInfo
	 */
	public MethodInfo[] getMethodStartingWith(String methodName);
        
    /**
	 * Gets the class method with the given name and parameters
     * @param methodName method name
     * @param parameterTypes array with parameter types, in the order they are declared in the method
	 * @return a <code>com.fmi.bytecode.annotations.tool.element.MethodInfo</code> object
	 * @see com.fmi.bytecode.annotations.tool.element.MethodInfo
	 */
	public MethodInfo getMethod(String methodName, String[] parameterTypes);
	
	/**
	 * Gets all class's constructors
	 * @return array with <code>com.fmi.bytecode.annotations.tool.element.ConstructorInfo</code> objects
	 * @see com.fmi.bytecode.annotations.tool.element.ConstructorInfo
	 */
	public ConstructorInfo[] getConstructors();


    /**
     * Gets the class constructor with the given parameters         
     * @param parameterTypes array with parameter types, in the order they are declared in the constructor
	 * @return a <code>com.fmi.bytecode.annotations.tool.element.ConstructorInfo</code> object
	 * @see com.fmi.bytecode.annotations.tool.element.ConstructorInfo
	 */
	public ConstructorInfo getConstructor(String[] parameterTypes);

	/**
	 * Gets all class's fields
	 * @return an array with <code>com.fmi.bytecode.annotations.tool.element.FieldInfo</code> objects
	 * @see com.fmi.bytecode.annotations.tool.element.FieldInfo
	 */
	public FieldInfo[] getFields();
	
	/**
	 * Gets the issued field, if there is no such field, null is returned
	 * @return FieldInfo object or null, if there is no such field
	 * @see com.fmi.bytecode.annotations.tool.element.FieldInfo
	 */
	public FieldInfo getField(String fieldName);
	
	/**
	 * Checks if the current class declares an annotation
	 * @return true if the class is an annotation, otherwise false
	 */
	public boolean isAnnotation();
	
	/**
	 * Checks if the current class is an interface
	 * @return true if the class is an interface, otherwise false
	 */
	public boolean isInterface();
        
    /**
	 * Checks if the current class is an enumeration class
	 * @return true if the class is an enumeration, otherwise false
	 */
	public boolean isEnum();

    /**
	 * Checks if the current class is an anonymous class
	 * @return true if the class is an anonymous class, otherwise false
	 */
    public boolean isAnonymousClass();
    
    /**
	 * Checks if the current class generic declarations in its signature
	 * @return true if the class has generic declarations, otherwise false
	 */
    public boolean isParametrised();
        
    /** Gets an array of <code>FileInfo</code> objects, representing the files, containing the current class.
     *  If the class file is contained in a JAR file, then the JAR file is returned (JARFile). If the class
     *  is in a separate class file, then the class file is returned (JavaClassFile). The content of the WAR files is 
     *  decomposed in JARFiles and ClassFiles.
     *  The empty array is returned, if the class is given to the tool as a stream (there is no file representation). 
     *
     *  @return an array of <code>FileInfo</code> objects        
     */
    public FileInfo[] getContainingFiles();
    
}