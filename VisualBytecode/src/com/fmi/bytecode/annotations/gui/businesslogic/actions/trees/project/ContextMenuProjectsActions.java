package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.LoadProjectApplier;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.ContextMenuSearchResultActions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.AnnotationsSearchDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.BrowseFileSystemDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.NewProjectDialog;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;

import javax.swing.JTree;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectsNode;


public class ContextMenuProjectsActions extends ContextMenuActionPerformerImpl {
    private ProjectsNode selectedProjectsNode;
    private MainFrame mainFrame;
    private LoadProjectApplier loadProjectApplier;
    private JTree projectsTree;
    
    public ContextMenuProjectsActions(ProjectsNode selectedProjectsNode) {
        this.selectedProjectsNode = selectedProjectsNode;
        this.mainFrame = GUIComponentsRepository.getComponentsRepository().getMainFrame();
        this.projectsTree = mainFrame.getProjectTree();
        loadProjectApplier = new LoadProjectApplier(projectsTree);
    }

    public void perform(String command) {
        if (command.equals("NewProject")){
            new NewProjectDialog(mainFrame, "New Project");
        } else if (command.equals("AddProject")) {
            new BrowseFileSystemDialog(mainFrame, BrowseFileSystemDialog.FILES_ONLY,
                                       loadProjectApplier,BrowseFileSystemDialog.FILTERS_PROJECT);

        } else if (command.equals("CloseAll")) {
            ModelsRepository.getModelsRepository().removeProjects();
            projectsTree.updateUI();
        } else if (command.equals("AnnotationSearch")) {
            List<String> allPackages = new ArrayList<String>();
            List<String> allAnnotations = new ArrayList<String>();
            
            Set<ProjectModel> projectModels = selectedProjectsNode.getProjectModels();
            for (ProjectModel currentProject : projectModels) {
                allPackages.addAll(currentProject.getAllPackages());
                allAnnotations.addAll(currentProject.getAllAnnotations());
            }
            
            openAnnotationsSearchDialog(mainFrame, null, null, AnnotationsSearchDialog.ALL_PKG, 
                                        allPackages, allAnnotations, true);
        } else if (command.equals("Export")) {
            new ContextMenuSearchResultActions(selectedProjectsNode).perform(command);   
        } 
    }
}