package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.FilterLevelType;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.LeafNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;

public class PackageAndClassNode extends LeafNode implements SavableNode {
    
    private ClassInfo classInfo;
    private List<AnnotationRecord> classAnnotations; //all annotations for current Level for this class
    private FilterLevelType level;
    
    public PackageAndClassNode(TreeNodeBaseImpl parent, ClassInfo classInfo, 
                               List<AnnotationRecord> classAnnotations, 
                               FilterLevelType level) {
        super(parent, classInfo.getName());
        this.classInfo = classInfo;
        this.classAnnotations = classAnnotations;
        this.level = level;
    }

    public ClassInfo getNodeIdentifier() {
        return getClassInfo();
    }

    public ClassInfo getClassInfo() {
        return classInfo;
    }

    public List<AnnotationRecord> getClassAnnotations() {
        return classAnnotations;
    }

    public FilterLevelType getLevel() {
        return level;
    }
    
    public File getContentFile() {
        return ((ProjectAndContentNode) getParent()).getContent();
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
