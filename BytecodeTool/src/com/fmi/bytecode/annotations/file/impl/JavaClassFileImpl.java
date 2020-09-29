package com.fmi.bytecode.annotations.file.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.file.JavaClassFile;

/**
 *
 * @author Krasimir Topchiyski
 */
public class JavaClassFileImpl extends FileInfoImpl implements JavaClassFile {
    
	private ClassInfo classInf;
	
    public ClassInfo getClazz() {
    	return classInf;
    }
    
    public void setClazz(ClassInfo ci) {
    	classInf = ci;    	
    }
	
	void fillAnnotationsMap(){
		classLevelAnnotations = new HashMap<String, List<AnnotationRecord>>();
		Map<String, AnnotationRecord> anns = classInf.getAnnotations();
		for(AnnotationRecord ar : anns.values()){
			ArrayList<AnnotationRecord> lst = new ArrayList<AnnotationRecord>();
			lst.add(ar);
			classLevelAnnotations.put(ar.getTypeName(), lst);
		}
	}

    public String toString() {
        String output = super.toString() + "\n File type: CLASS_FILE";
        return output;
    }

    public boolean equals(Object obj) {
    	
      	if (!super.equals(obj)) {
		    return false;
		}
		if (obj instanceof JavaClassFile) {
			JavaClassFile f = (JavaClassFile)obj;
	        return f.getClazz().equals(classInf);
	    }    
        return false;
    }
    
	public int hashCode(){
		return super.hashCode();
	}

}
