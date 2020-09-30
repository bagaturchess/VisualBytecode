package com.fmi.bytecode.annotations.tool.file;

import java.util.Map;

import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;

import java.util.List;


/**
 * The <code>FileInfo</code> interface is the root of the hierarchy,
 * which represents the processed files.
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface FileInfo {
    
    /**
     * Gets the file name, without the path (file name plus extension)
     * @return a String with the file name
     */
    public String getName();
    
    /**
     * Gets the relative path of the file to the containing archive or to the given folder root.
     * The returned string contains the file name at the end.
     * @return The returned path is in the following format:
     *		for files in a WAR: [filename of the archive]!<separator char>[relative path in the archive]<separator char>[filename]
     * 		for files in folders: <separator char>[relative path to the root folder]<separator char>[filename]
     * 		for root files: <empty string>  
     */
    public String getRelativePath();

    /**
     * Gets the full filesystem path of the file.
     */
    public String getFullPath();
    
    /**
     * Gets all class level annotations from all classes in the current file
     * @return a Map with annotation type as a key and a list 
     * with <code>com.fmi.bytecode.annotations.tool.element.AnnotationRecord<code> objects
     */
    public Map<String, List<AnnotationRecord>> getClassLevelAnnotations();
  
    /**
     * Gets the parent (WAR file or folder) of the current file.
     * If the current file is a root file (directly given to the tool), NULL is returned.     
     *
     * @return a FileInfo object
     */
    public FileInfo getParent();
    
}
