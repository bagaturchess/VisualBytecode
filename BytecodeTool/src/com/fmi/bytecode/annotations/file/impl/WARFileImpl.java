package com.fmi.bytecode.annotations.file.impl;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.file.FileInfo;
import com.fmi.bytecode.annotations.file.JARFile;
import com.fmi.bytecode.annotations.file.JavaClassFile;
import com.fmi.bytecode.annotations.file.WARFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Krasimir Topchiyski
 */
public class WARFileImpl extends JARFileImpl implements WARFile {
    
	private ArrayList<FileInfo> files = new ArrayList<FileInfo>();
	private Map<String, ClassInfo> classes = null;
		
    public FileInfo[] getFiles() {
    	return files.toArray(new FileInfo[files.size()]);
    }
    
    public void addFile(FileInfo f) {    	
    	files.add(f);
    }
    
    public void setFiles(FileInfo[] fs) {
        files.clear();
        if(fs!=null){
        	for(FileInfo f : fs) {
        		addFile(f);
        	}
        }    	
    }
    
    public Map<String, ClassInfo> getClasses() {
    	if(classes == null){
    		classes = new HashMap<String, ClassInfo>();
    		for(FileInfo f: files) {
    			if(f instanceof JavaClassFile){
    				ClassInfo ci = ((JavaClassFile)f).getClazz();
    				classes.put(ci.getName(),ci);
    			} else {
    				Map<String, ClassInfo> tmpClasses = ((JARFile)f).getClasses();
    				classes.putAll(tmpClasses);
    			}
    		}
    	}
    	return classes;
    }
    
    void fillAnnotationsMap(){
    	classLevelAnnotations = new HashMap<String, List<AnnotationRecord>>();
    	for(FileInfo f : files){
    		Map<String, List<AnnotationRecord>> anns = f.getClassLevelAnnotations();
    		for(String key : anns.keySet()){
    			List<AnnotationRecord> lst = classLevelAnnotations.get(key);
    			if(lst == null){
    				lst = new ArrayList<AnnotationRecord>();
    				classLevelAnnotations.put(key, lst);
    			}
    			lst.addAll(anns.get(key));    			
    		}
    	}
    }
    
    public String toString() {
        String output = super.toString() + "\n File type: WAR_FILE";
        return output;
    }

    public boolean equals(Object obj) {
    	
      	if (!super.equals(obj)) {
		    return false;
		}
		if (obj instanceof WARFile) {
			WARFile f = (WARFile)obj;
		    if(!f.getFiles().equals(files)){
		    	return false;
		    }
	        return true;
	    }    
        return false;
    }

	public int hashCode(){
		return super.hashCode();
	}

}
