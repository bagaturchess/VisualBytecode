package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.LeafNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

public class ClassNode extends LeafNode implements SavableNode {
    
    private ClassInfo classInfo;
    private File contentFile;
    
    //String name -> Name of ๒๕ๅ class without package
    public ClassNode(TreeNodeBaseImpl parent, String name, ClassInfo classInfo, 
                                                            File contentFile) {
        super(parent, name);
        this.classInfo = classInfo;
        this.contentFile = contentFile;
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public File getContentFile() {
        return contentFile;
    }
    
    public ClassInfo getNodeIdentifier() {
        return getClassInfo();
    }

    public String getClassName() {
        return getClassInfo().getName(); 
    }
    
    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
        List<ClassInfo> classInfos = new ArrayList<ClassInfo>();
        classInfos.add(classInfo);
        SaveUnit unit = new SaveUnit(getProjectModel(), getContentFile(), classInfos);
        saveUnits.add(unit);
        return saveUnits;
    }
}
