package com.fmi.bytecode.annotations.file.impl;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.file.FileInfo;
import com.fmi.bytecode.annotations.util.ComparisonUtils;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Krasimir Topchiyski
 */
abstract public class FileInfoImpl implements FileInfo {
    
	private String fileName;
	private String relativePath;
	private String fullPath;
	private FileInfo parentFile;
	
	protected Map<String, List<AnnotationRecord>> classLevelAnnotations;
		
    /** 
     * Gets the relative path of the current file in the containing archive/folder.
     * The root of the path is the containig archive/folder.
     * 
     * @see com.fmi.bytecode.annotations.file.FileInfo#getRelativePath()
     */
    public String getRelativePath() {
    	return relativePath;
    }
    
    public void setRelativePath(String path) {
    	relativePath = path;
    }

    /** 
     * Gets the full path of the current file. 
     * 
     * @see com.fmi.bytecode.annotations.file.FileInfo#getFullPath()
     */
    public String getFullPath() {
    	return fullPath;
    }
    
    public void setFullPath(String path) {
    	fullPath = path;
    }
    
    /**
     * Gets the file name (the extension is counted as a part of the name)
     * @see com.fmi.bytecode.annotations.file.FileInfo#getName()
     */
    public String getName() {
    	return fileName;
    }
    
    public void setName(String name) {
    	fileName = name;
    }

    public FileInfo getParent() {
    	return parentFile;
    }
    
    public void setParent(FileInfo parent) {
    	parentFile = parent;
    }
    
	public Map<String, List<AnnotationRecord>> getClassLevelAnnotations() {
		if(classLevelAnnotations == null){
			fillAnnotationsMap();
		}
		return classLevelAnnotations;
	}
	
	abstract void fillAnnotationsMap();
    
    public String toString() {
        String output = "\n File name: "+fullPath;
        return output;
    }

    public boolean equals(Object obj) {
      	if (this == obj) {
		    return true;
		}
		if (obj instanceof FileInfo) {
			FileInfo f = (FileInfo)obj;
	        if(!f.getName().equals(fileName)){
	        	return false;
	        }
	        if(!ComparisonUtils.compareObjects(f.getFullPath(),fullPath)){
	        	return false;
	        }
	        if(!ComparisonUtils.compareObjects(f.getRelativePath(),relativePath)){
	        	return false;
	        }
	        if(!ComparisonUtils.compareObjects(f.getParent(),parentFile)){
	        	return false;
	        }
	        return true;
	    }    
        return false;
    }

}
