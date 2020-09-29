package com.fmi.bytecode.annotations.gui.businesslogic.treenodes;


import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import java.util.List;


public interface SavableNode {
    
    //public ProjectModel getProjectModel();
    
    //public File getContentFile();
    
    //public List<ClassInfo> getClasses();
    
    public List<SaveUnit> getUnits();
}
