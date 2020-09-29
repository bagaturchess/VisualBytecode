package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.ContextMenuSearchResultActions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.AnnotationsSearchDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.NewProjectDialog;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectNode;

public class ContextMenuProjectActions extends ContextMenuActionPerformerImpl {
    
    private ProjectNode selectedProjectNode;
    
    public ContextMenuProjectActions(ProjectNode selectedProjectNode) {
        this.selectedProjectNode = selectedProjectNode;
    }

    public void perform(String command) {
        ProjectModel projectModel = selectedProjectNode.getProjectModel();
        MainFrame mainFrame = GUIComponentsRepository.getComponentsRepository().getMainFrame();
        
        if (command.equals("ProjectProps")) {
            new NewProjectDialog(mainFrame, projectModel, "Project Properties");
        } else if (command.equals("CloseProject")) {
            ModelsRepository.getModelsRepository().removeProject(projectModel);
            mainFrame.getProjectTree().updateUI();
        } else if (command.equals("AnnotationSearch")) {
            List<String> allPackages = new ArrayList<String>();
            List<String> allAnnotations = new ArrayList<String>();
            
            allPackages.addAll(projectModel.getAllPackages());
            allAnnotations.addAll(projectModel.getAllAnnotations());
       
            openAnnotationsSearchDialog(mainFrame, projectModel, null, AnnotationsSearchDialog.ALL_PKG, 
                                        allPackages, allAnnotations, true);
        } else if (command.equals("Export")) {
            new ContextMenuSearchResultActions(selectedProjectNode).perform(command);   
        } 
    }
}