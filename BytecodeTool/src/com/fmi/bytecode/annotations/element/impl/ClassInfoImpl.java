package com.fmi.bytecode.annotations.element.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.element.ConstructorInfo;
import com.fmi.bytecode.annotations.file.FileInfo;
import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.annotations.input.ConstructorModel;
import com.fmi.bytecode.annotations.input.FieldModel;
import com.fmi.bytecode.annotations.input.MethodModel;
import com.fmi.bytecode.annotations.tool.ReadResult;
import com.fmi.bytecode.annotations.util.ComparisonUtils;

/**
 * Bytecode library ClassFile class wrapper that implements 
 * the <code>com.fmi.bytecode.annotations.element.ClassInfo</code> interface
 * 
 * @author Krasimir Topchiyski
 */
public class ClassInfoImpl extends ElementInfoImpl implements ClassInfo {
	
	private ClassInfo superClass;	
	private String packageName;
	private String superClassName;
	private Map<String, FieldInfoImpl> fields;	
	private MethodInfoImpl[] methods;
	private ConstructorInfoImpl[] constructors;
	private String[] interfaceNames;
	private List<FileInfo> containingFiles;
	private Map<String, ClassInfo> interfaces; 
	private boolean isParametrised;
	private ReadResult globalResult;
	private boolean isAnnotation;
	private boolean isInterface;
	private boolean isEnum;
	
	public ClassInfoImpl(ClassModel classModel, ReadResult result) {	
		
		super(classModel.getAnnotations());
		
		this.name = classModel.getName().replace('/', '.');
		this.modifiers = classModel.getAccessFlags();
		if(classModel.getSuperClassName()!=null){
			this.superClassName = classModel.getSuperClassName().replace('/', '.');
		}
		if(classModel.getPackageName()!=null){
			this.packageName = classModel.getPackageName().replace('/', '.');
		}
		this.isParametrised = classModel.isParametrised();
		this.globalResult = result;
		this.isAnnotation = classModel.isAnnotation();
		this.isInterface = classModel.isInterface();
		this.isEnum = classModel.isEnum();
		
		String[] bmInterfaceNames = classModel.getInterfaceNames();
		if (bmInterfaceNames != null) {
			int j = bmInterfaceNames.length;
			this.interfaceNames = new String[j];
			for (int i = 0; i < j; i++) {
				this.interfaceNames[i] = bmInterfaceNames[i].replace('/', '.');
			}
		} else {
			this.interfaceNames = new String[0];
		}
		
		MethodModel[] methods = classModel.getMethods();
		this.methods = new MethodInfoImpl[methods.length];
		for (int i=0; i<methods.length; i++) {
			this.methods[i] = new MethodInfoImpl(methods[i], this);
		}
		
		ConstructorModel[] constructors = classModel.getConstructors();
		this.constructors = new ConstructorInfoImpl[constructors.length];
		for (int i=0; i<constructors.length; i++) {
			this.constructors[i] = new ConstructorInfoImpl(constructors[i], this);
		}
		
		FieldModel[] fields = classModel.getFields();
		this.fields = new HashMap<String, FieldInfoImpl>();
		for (int i=0; i<fields.length; i++) {
			this.fields.put(fields[i].getName(), new FieldInfoImpl(fields[i], this));
		}
	}
	
	public ClassInfo getSuperclass() {
		if(superClass == null) {
			superClass = globalResult.getClass(getSuperclassName());
		}
		return superClass;
	}

	public String getSuperclassName() {
		return superClassName;
	}

	public String[] getInterfacesNames() {
		return interfaceNames;
	}

	public Map<String, ClassInfo> getInterfaces() {
		if(interfaces == null){
			interfaces = new HashMap<String, ClassInfo>();
			for(String i: getInterfacesNames()){
				ClassInfo ci = globalResult.getClass(i);
				interfaces.put(i, ci);
			}
		}
		return interfaces;
	}

	public String getPackage() {
		return packageName;
	}

	public com.fmi.bytecode.annotations.element.MethodInfo[] getMethods() {
		return methods;
	}
	
	public com.fmi.bytecode.annotations.element.MethodInfo[] getMethodStartingWith(String methodName) {
        ArrayList<com.fmi.bytecode.annotations.element.MethodInfo> result = 
        	new ArrayList<com.fmi.bytecode.annotations.element.MethodInfo>();
        for(com.fmi.bytecode.annotations.element.MethodInfo m: methods){
            if(m.getName().startsWith(methodName)){
                result.add(m);
            }
        }
        if(result.size()==0){
            return null;
        } else {
            return result.toArray(new com.fmi.bytecode.annotations.element.MethodInfo[result.size()]);
        }
	}	

	public com.fmi.bytecode.annotations.element.MethodInfo[] getMethod(String methodName) {
        ArrayList<com.fmi.bytecode.annotations.element.MethodInfo> result = new ArrayList<com.fmi.bytecode.annotations.element.MethodInfo>();
        for(com.fmi.bytecode.annotations.element.MethodInfo m: methods){
            if(m.getName().equals(methodName)){
                result.add(m);
            }
        }
        if(result.size()==0){
            return null;
        } else {
            return result.toArray(new com.fmi.bytecode.annotations.element.MethodInfo[result.size()]);
        }
	}

	public com.fmi.bytecode.annotations.element.MethodInfo getMethod(String methodName, String[] parameterTypes) {
        for(com.fmi.bytecode.annotations.element.MethodInfo m: methods){            
            if(m.getName().equals(methodName)){
                if(ComparisonUtils.compareOrderedArrays(m.getParameters(), parameterTypes)){
                    return m;
                }
            }
        }
        
        return null;
	}

	public ConstructorInfo[] getConstructors() {
		return constructors;
	}

	public ConstructorInfo getConstructor(String[] parameterTypes) {
        for(ConstructorInfo c: constructors){                
            String[] params = c.getParameters();                
            if(ComparisonUtils.compareOrderedArrays(params, parameterTypes)){
                return c;
            }
        }
        
        return null;
	}

	public com.fmi.bytecode.annotations.element.FieldInfo[] getFields() {		
		return fields.values().toArray(new FieldInfoImpl[fields.size()]);
	}
	
	public com.fmi.bytecode.annotations.element.FieldInfo getField(String fieldName) {		
		return fields.get(fieldName);
	}
	
	public boolean isAnnotation() {
		return this.isAnnotation;
	}

	public boolean isInterface() {
		return this.isInterface;
	}

	public boolean isEnum() {
		return this.isEnum;
	}

	public boolean isAnonymousClass() {
		// TODO implement it
		return false;
	}

	public boolean isParametrised() {		
		return isParametrised;
	}

	public FileInfo[] getContainingFiles() {
		return containingFiles == null ?
				new FileInfo[0] :
				containingFiles.toArray(new FileInfo[containingFiles.size()]);
	}
	
	public void addContainingFile(FileInfo file) {
		if(containingFiles == null){
			containingFiles = new ArrayList<FileInfo>();
		}
		containingFiles.add(file);
	}
	
	public boolean equals(Object obj) {
        
        if(!super.equals(obj)){
            return false;
        }
        if(obj instanceof ClassInfo){
            ClassInfo ci = (ClassInfo)obj;
//            if(isAnonymous != ci.isAnonymousClass()){
//                return false;
//            }
           
            if(isParametrised() != ci.isParametrised()){
                return false;
            }

            if(isAnnotation() != ci.isAnnotation()){
                return false;
            }
            
            if(isEnum() != ci.isEnum()){
                return false;
            }
            
            if(!ComparisonUtils.compareObjects(getSuperclassName(), ci.getSuperclassName())) {
                return false;
            }
            
            if(!ComparisonUtils.compareObjects(getPackage(), ci.getPackage())) {
                return false;
            }            
            
            if(!ComparisonUtils.compareUnorderedArrays(getInterfacesNames(), ci.getInterfacesNames())){
                return false;
            }
            
            if(!ComparisonUtils.compareUnorderedArrays(getConstructors(), ci.getConstructors())){
                return false;
            }
            
            if(!ComparisonUtils.compareUnorderedArrays(getMethods(), ci.getMethods())){
                return false;
            }
            
            if(!ComparisonUtils.compareUnorderedArrays(getFields(), ci.getFields())){
                return false;
            }
            return true;
        }
        return false;
    }
	
	public int hashCode(){
		return super.hashCode();
	}
	
	public String toString() {
		String result = "";
		
		result += "Information about class: " + name + " begin ...\r\n";
		result += "File(s): " + containingFiles + "\r\n";
		result += "Package: " + packageName + "\r\n";
		result += "Superclass: " + superClassName + "\r\n";
		result += "Interfaces: " + getInterfaces().keySet() + "\r\n";
		result += "Is parametrised: " + isParametrised + "\r\n";
		result += "Is annotation: " + isAnnotation + "\r\n";
		result += "Is interface: " + isInterface + "\r\n";
		result += "Is enum: " + isEnum + "\r\n";
		result += "Class level annotations: " + super.toString() + "\r\n";
		result += "Constructor annotations: " + Arrays.deepToString(constructors) + "\r\n";
		result += "Field annotations: " + fields.values() + "\r\n";
		result += "Method annotations: " + Arrays.deepToString(methods) + "\r\n";
		result += "Information about class: " + name + " end.\r\n";
		
		return result;
	}
}

