package com.fmi.bytecode.annotations.gui.businesslogic.model.export;

import com.fmi.bytecode.annotations.element.ClassInfo;

import java.io.File;

import java.util.HashSet;
import java.util.List;

import java.util.Set;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

public class SaveUnit {

    private ProjectModel projectModel;
    private File contentFile;
    private Set<ClassInfo> classInfos;
    
    public SaveUnit(ProjectModel projectModel, File contentFile, List<ClassInfo> classInfos) {
        this.projectModel = projectModel;
        this.contentFile  = contentFile;
        this.classInfos = new HashSet<ClassInfo>(classInfos);
    }
    
    public ProjectModel getProjectModel() {
        return projectModel;
    }
    
    public File getContentFile() {
        return contentFile;
    }
    
    public Set<ClassInfo> getClasses() {
        return classInfos;
    }
    
    public String toString() {
        return "SavableNode: prj=" + projectModel.getProjectName()
                            + " content=" + contentFile.getAbsolutePath()
                            + " classes=" + classInfos;
    }
}
