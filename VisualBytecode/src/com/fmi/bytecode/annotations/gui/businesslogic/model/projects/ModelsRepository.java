package com.fmi.bytecode.annotations.gui.businesslogic.model.projects;

import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;
import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import java.io.File;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import java.util.Set;

import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectTreeUtils;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectsNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeModelImpl;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;

public class ModelsRepository {   
    private static ModelsRepository repository;
    private Set<ProjectModel> allProjectsModels;
    
    public ProjectsNode TREE_PROJECTS_ROOT;
    
    public static boolean firstProjectAdded = false;
    
    private ModelsRepository() {
        allProjectsModels = new HashSet<ProjectModel>();
        TREE_PROJECTS_ROOT = new ProjectsNode("Projects");
    }
    
    public static final ModelsRepository getModelsRepository() {
        if (repository == null)
            repository = new ModelsRepository();
        return repository;
    }
    
    public void addProjectModel(ProjectModel projectModel) {
        //String projectDirAndName = createProjectIdentifier(projectModel);
        if (allProjectsModels.contains(projectModel)) {
            throw new IllegalStateException("Attempt to add an existing project model with identifier " +
                                             projectModel.getProjectName());
        } 
        //if (!allProjectsModels.containsKey(projectDirAndName)) {
        allProjectsModels.add(projectModel);
        //in case of addPtroject -> add nodes for the new Project
        ProjectTreeUtils.createTree(projectModel, TREE_PROJECTS_ROOT);
        if (!firstProjectAdded) {
            GUIComponentsRepository.getComponentsRepository().getMainFrame().closeLogo();
            firstProjectAdded = true;
        }
        //} 
    }

    public void updateProjectModel(ProjectModel oldProjectModel, ProjectModel newProjectModel) throws ModelException {
        //String oldProjectIdentifier = createProjectIdentifier(oldProjectModel);
        //String newProjectIdentifier = createProjectIdentifier(newProjectModel);
        
        if (!allProjectsModels.contains(oldProjectModel)) {
            throw new IllegalStateException("Attempt to update non existing project model " +
                                             oldProjectModel.getProjectName());
        }
        
        if (!oldProjectModel.equals(newProjectModel)) {
            /*if (allProjectsModels.containsKey(newProjectIdentifier)) {
                throw new ProjectModelException("This project exists yet");
            }*/
            allProjectsModels.remove(oldProjectModel);
            allProjectsModels.add(newProjectModel);
            TREE_PROJECTS_ROOT.removeChild(oldProjectModel);
            ProjectTreeUtils.createTree(newProjectModel, TREE_PROJECTS_ROOT);
        }           
    }

    /*public String createProjectIdentifier(ProjectModel projectModel) {
        String projectDirAndName =
                projectModel.getProjectDir() + File.separator + projectModel.getProjectName();
        return projectDirAndName;
    }*/

    /*private ProjectModel getProjectModel(String projectDirAndName) {
        return allProjectsModels.get(projectDirAndName);
    }*/

    public ProjectsNode getTreeProjectsRoot() {
        return TREE_PROJECTS_ROOT;
    }
    
    public TreeModel getTreeModelProjectsRoot() {
        return new TreeModelImpl(TREE_PROJECTS_ROOT);
    }
    
    public void removeProject(ProjectModel projectModel) {
        //String projectDirAndName = createProjectIdentifier(projectModel);
        allProjectsModels.remove(projectModel);
        TREE_PROJECTS_ROOT.removeChild(projectModel);
    }
    
    public void removeProjects() {
        allProjectsModels.clear();   
        TREE_PROJECTS_ROOT.removeChildren();
    }

    public Set<ProjectModel> getAllProjectsModels() {
        return allProjectsModels;
    }

    public boolean isFirstProjectAdded() {
        return firstProjectAdded;
    }
}
