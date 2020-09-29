package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.ElementNode;

//root
public class ProjectsNode extends ElementNode implements SavableNode {
 
    public ProjectsNode(String name) {
        super(null, name);
    }

    public Set<ProjectModel> getNodeIdentifier() {
        return getProjectModels();
    }
    
    public Set<ProjectModel> getProjectModels() {
        return ModelsRepository.getModelsRepository().getAllProjectsModels();
    }

    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
         
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ProjectNode content = (ProjectNode) this.getChildAt(i);
            saveUnits.addAll(content.getUnits());                             
        }
        return saveUnits;
    }
}
