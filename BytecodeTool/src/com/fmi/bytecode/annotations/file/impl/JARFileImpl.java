package com.fmi.bytecode.annotations.file.impl;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.file.JARFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Krasimir Topchiyski
 */
public class JARFileImpl extends FileInfoImpl implements JARFile {
    
	private Map<String, ClassInfo> classes = new HashMap<String, ClassInfo>();

    public Map<String, ClassInfo> getClasses() {
    	return Collections.unmodifiableMap(classes);
    }
    
    public void addClass(ClassInfo ci) {    	
    	classes.put(ci.getName(), ci);
    }
    
    public void setClasses(ClassInfo[] cis) {
        classes.clear();
        if(cis!=null){            
            for(ClassInfo ci: cis){
                addClass(ci);
            }
        }    	
    }
    
    void fillAnnotationsMap(){
    	classLevelAnnotations = new HashMap<String, List<AnnotationRecord>>();
    	for(ClassInfo c : classes.values()){
    		  Map<String, AnnotationRecord> anns = c.getAnnotations();
    		  for(AnnotationRecord ar : anns.values()) {
    		  String annName = ar.getTypeName();
    		  List<AnnotationRecord> lst = classLevelAnnotations.get(annName);
    		    if(lst == null){
    		      lst = new ArrayList<AnnotationRecord>();
    		      classLevelAnnotations.put(annName, lst);
    		    }
    		    lst.add(ar);    			
    		  }
    	}
    }
    
    public String toString() {
        String output = super.toString() + "\n File type: JAR_FILE";
        return output;
    }

    public boolean equals(Object obj) {
    	
      	if (!super.equals(obj)) {
		    return false;
		}
		if (obj instanceof JARFile) {
			JARFile f = (JARFile)obj;
		    if(!f.getClasses().equals(classes)){
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
