package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.ContextMenuSearchResultActions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.AnnotationsSearchDialog;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectContentNode;


public class ContextMenuProjectContentActions extends ContextMenuActionPerformerImpl {
    private ProjectContentNode selectedProjectContentNode;
    private MainFrame mainFrame;
    
    
    public ContextMenuProjectContentActions(ProjectContentNode selectedProjectContentNode) {
        this.selectedProjectContentNode = selectedProjectContentNode;
        mainFrame = GUIComponentsRepository.getComponentsRepository().getMainFrame();
    }

    public void perform(String command) {
        if (command.equals("AnnotationSearch")) {
            List<String> allPackages = new ArrayList<String>();
            List<String> allAnnotations = new ArrayList<String>();
            
            ProjectModel projectModel = selectedProjectContentNode.getProjectModel();
            allPackages.addAll(projectModel.getAllPackages());
            allAnnotations.addAll(projectModel.getAllAnnotations());
            
            openAnnotationsSearchDialog(mainFrame, projectModel,
                                        selectedProjectContentNode.getContentFile(),AnnotationsSearchDialog.ALL_PKG, 
                                        allPackages, allAnnotations, true);
        } else if (command.equals("Export")) {
            new ContextMenuSearchResultActions(selectedProjectContentNode).perform(command);   
        } 
    }
}
