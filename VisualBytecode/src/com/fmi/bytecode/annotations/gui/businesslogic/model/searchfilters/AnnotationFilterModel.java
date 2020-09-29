package com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters;

import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;


public class AnnotationFilterModel {
    
    private boolean isPositive;
    private String annotationName;
    private List<FilterLevelType> level;
        
    public AnnotationFilterModel(boolean isPositive, String annotationName,
                                 List<FilterLevelType> level) throws ModelException {
        /*if (annotationName == null || annotationName.trim().equals("")) 
            throw new ModelException("Annotation name is not selected.");*/
        if (level.size() == 0) 
            throw new ModelException("Even one annotation level have to be chosen.");
            
        this.isPositive = isPositive;
        this.annotationName = annotationName;
        this.level = level;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public String getAnnotationName() {
        return annotationName;
    }

    public List<FilterLevelType> getLevel() {
        return level;
    }
    
    public String toString() {
       String pos = isPositive ? "+" : "-";
       return pos + " " + (annotationName == null ? "Any Annotation (*)" : annotationName)
              + " " +  level.toString();
    }
    
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof AnnotationFilterModel) {
            AnnotationFilterModel otherModel = (AnnotationFilterModel) obj;
            if (//otherModel.isPositive() == isPositive() 
                //&& 
                otherModel.getAnnotationName() == null
                || getAnnotationName() == null
                || otherModel.getAnnotationName().equals(getAnnotationName()) 
                //&& otherModel.getLevel().equals(getLevel())
                ) {
                result = true;
            }
        }
        return result;
    }
    
/*    public boolean isIncluded(AnnotationFilterModel otherFilter) {
        return otherFilter.getAnnotationName().equals(getAnnotationName()) 
               && otherFilter.isPositive() == isPositive() 
               && getLevel().containsAll(otherFilter.getLevel());
    }
*/    
    public int hashCode() {
        return getAnnotationName().hashCode();   
    }
  
}
