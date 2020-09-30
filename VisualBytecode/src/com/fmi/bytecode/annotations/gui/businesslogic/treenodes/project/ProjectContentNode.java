package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.TreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.ElementNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

public class ProjectContentNode extends ElementNode implements SavableNode {

    //field for ProjectContentNode identifier
    private File contentFile;
    
    public ProjectContentNode(TreeNodeBaseImpl parent, File contentFile) {
        super(parent, contentFile.getAbsolutePath());
        this.contentFile = contentFile;
    }

    public File getContentFile() {
        return contentFile;
    }

    public File getNodeIdentifier() {
        return getContentFile();
    }

    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
        List<ClassInfo> classInfos = new ArrayList<ClassInfo>();
        TreeUtils.collectClassInfos(this, classInfos);      
        SaveUnit unit = new SaveUnit(getProjectModel(), getContentFile(), classInfos);
        saveUnits.add(unit);
        return saveUnits;
    }
}
