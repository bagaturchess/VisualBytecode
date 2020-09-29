package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.ContextMenuSearchResultActions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ClassNode;

public class ContextMenuClassActions extends ContextMenuActionPerformerImpl {
    
    private ClassNode selectedClassNode;
    private MainFrame mainFrame;
    
    public ContextMenuClassActions(ClassNode selectedClassNode) {
        this.selectedClassNode = selectedClassNode;
        mainFrame = GUIComponentsRepository.getComponentsRepository().getMainFrame();
    }

    public void perform(String command) {
        if (command.equals("AnnotationSearch")) {
            List<String> allPackages = new ArrayList<String>();
            List<String> allAnnotations = new ArrayList<String>();
            
            ProjectModel projectModel = selectedClassNode.getProjectModel();
            allPackages.add(selectedClassNode.getClassInfo().getName());
            allAnnotations.addAll(projectModel.getAllAnnotations());
            
            openAnnotationsSearchDialog(mainFrame, projectModel, 
                                        selectedClassNode.getContentFile(), 
                                        selectedClassNode.getClassInfo().getName(), 
                                        allPackages, allAnnotations, false);
        } else if (command.equals("Export")) {
            new ContextMenuSearchResultActions(selectedClassNode).perform(command);   
        } 
    }
}