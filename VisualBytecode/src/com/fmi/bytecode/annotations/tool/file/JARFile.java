package com.fmi.bytecode.annotations.tool.file;

import java.util.Map;

import com.fmi.bytecode.annotations.tool.element.ClassInfo;

/**
 * The <code>JARFile</code> interface represents 
 * a processed JAR archive file.
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface JARFile extends FileInfo {
    
    /**
     * Returns all classes in the current archive file
     * @return a Map with a class name as a key and <code>com.fmi.bytecode.annotations.tool.element.ClassInfo</code>
     * as a value
     * @see com.fmi.bytecode.annotations.tool.element.ClassInfo
     */        
    public Map<String,ClassInfo> getClasses();
    
}
