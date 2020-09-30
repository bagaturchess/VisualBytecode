package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.TreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.ElementNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

public class PackageNode extends ElementNode  implements SavableNode {
  
    private String packageName;
    private File contentFile;
    
    public PackageNode(TreeNodeBaseImpl parent, String name, File contentFile) {
        super(parent, name);
        if (parent instanceof PackageNode) {
            packageName = ((PackageNode) parent).getPackageName() + "." + name;                
        } else {
            packageName = name;
        }
        this.contentFile = contentFile;
    }

    public String getPackageName() {
        return packageName;
    }
    
    public File getContentFile() {
        return contentFile;
    }
    
    public String getNodeIdentifier() {
        return getPackageName();
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
