package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.TreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.ElementNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

public class ProjectNode extends ElementNode implements SavableNode {
    
    public ProjectNode(TreeNodeBaseImpl parent, ProjectModel projectModel) {
        super(parent, projectModel.getProjectName(), projectModel);
    }
    
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof ProjectNode) {
            ProjectNode node = (ProjectNode) obj;
            if (node.getProjectModel().equals(getProjectModel())) {
                result = true;
            }
        }       
        return result;
    }
    
    public int hashCode() {
        return getProjectModel().hashCode();
    }
    
    /*public String toString() {
        return this.getNodeName();
    }*/

    public ProjectModel getNodeIdentifier() {
        return getProjectModel();
    }
        
    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
         
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ProjectContentNode content = (ProjectContentNode) this.getChildAt(i);
            saveUnits.addAll(content.getUnits());                             
        }
        return saveUnits;
    }
}
