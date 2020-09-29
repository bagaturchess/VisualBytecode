package com.fmi.bytecode.annotations.file;

import com.fmi.bytecode.annotations.element.ClassInfo;

/**
 * The <code>JavaClassFile</code> interface represents a processed <b>.class</b>file
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface JavaClassFile extends FileInfo {
    
   /**
     * Gets a <code>com.fmi.bytecode.annotations.element.ClassInfo</code> object
     * out of the processed file
     * @return a com.fmi.bytecode.annotations.element.ClassInfo object
     * @see com.fmi.bytecode.annotations.element.ClassInfo
     */
    public ClassInfo getClazz();    
    
}
