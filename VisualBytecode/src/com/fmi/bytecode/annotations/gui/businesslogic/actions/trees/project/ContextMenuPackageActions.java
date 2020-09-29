package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.ContextMenuSearchResultActions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.util.ArrayList;
import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.PackageNode;


public class ContextMenuPackageActions extends ContextMenuActionPerformerImpl {
    
    private PackageNode selectedPackageNode;
    private MainFrame mainFrame;
    
    public ContextMenuPackageActions(PackageNode selectedPackageNode) {
        this.selectedPackageNode = selectedPackageNode;
        mainFrame = GUIComponentsRepository.getComponentsRepository().getMainFrame();
    }

    public void perform(String command) {
        if (command.equals("AnnotationSearch")) {
            List<String> allPackages = new ArrayList<String>();
            List<String> allAnnotations = new ArrayList<String>();
            
            ProjectModel projectModel = selectedPackageNode.getProjectModel();
            
            for (String currentPackageName : projectModel.getAllPackages()) {
                if (currentPackageName.startsWith(selectedPackageNode.getPackageName())) {
                    allPackages.add(currentPackageName); 
                }
            }
            allAnnotations.addAll(projectModel.getAllAnnotations());
            
            openAnnotationsSearchDialog(mainFrame, projectModel, 
                                        selectedPackageNode.getContentFile(),
                                        selectedPackageNode.getPackageName(),
                                        allPackages, allAnnotations, true);
        } else if (command.equals("Export")) {
            new ContextMenuSearchResultActions(selectedPackageNode).perform(command);   
        } 
    }
}