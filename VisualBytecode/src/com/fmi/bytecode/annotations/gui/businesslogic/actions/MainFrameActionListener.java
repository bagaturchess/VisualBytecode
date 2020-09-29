package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.LoadProjectApplier;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.ContextMenuSearchResultActions;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.AboutDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.BrowseFileSystemDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.HelpDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.NewProjectDialog;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import javax.swing.tree.TreePath;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectNode;

public class MainFrameActionListener implements ActionListener {
    private MainFrame parent;
    private LoadProjectApplier loadProjectApplier;
    
    public MainFrameActionListener(MainFrame parent) {
        this.parent = parent;
        loadProjectApplier = new LoadProjectApplier(parent.getProjectTree());
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        //System.out.println(command);
        
        if (command.equals("NewProject")) {
            new NewProjectDialog(parent, "New Project");
        } else if (command.equals("OpenProject")) {
            new BrowseFileSystemDialog(parent, BrowseFileSystemDialog.FILES_ONLY,
                                       loadProjectApplier,BrowseFileSystemDialog.FILTERS_PROJECT);
        } else if (command.equals("CloseAll")) {
            ModelsRepository.getModelsRepository().removeProjects();
            parent.getProjectTree().updateUI();
        } else if (command.equals("CloseProject")) {     
            TreePath path = parent.getProjectTree().getSelectionPath();
            if (path != null && path.getLastPathComponent() instanceof ProjectNode) {
                ProjectModel projectModel = ((ProjectNode) path.getLastPathComponent()).getProjectModel();
                ModelsRepository.getModelsRepository().removeProject(projectModel);
                parent.getProjectTree().updateUI();    
            } else {
                JOptionPane.showMessageDialog(parent, 
                             "There is no selected project to close.",
                             "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (command.equals("Help")) {
            new HelpDialog(parent);
        } else if (command.equals("About")) {
            JOptionPane.showMessageDialog(parent, new AboutDialog(), "About", JOptionPane.PLAIN_MESSAGE);
        } else if (command.equals("Exit")) {
            System.exit(0);
        } else if (command.equals("AnnotationSearch")) {
            new SearchAnnotationAction(parent.getProjectTree()).openSearchDialog();
        } else if (command.equals("ProjectProps")) {
            TreePath path = parent.getProjectTree().getSelectionPath();
            if (path != null && path.getLastPathComponent() instanceof ProjectNode) {
                ProjectModel projectModel = ((ProjectNode) path.getLastPathComponent()).getProjectModel();                
                new NewProjectDialog(parent, projectModel, "Project Properties");   
            } else {
                JOptionPane.showMessageDialog(parent, 
                             "There is no selected project.",
                             "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (command.equals("Export")) {
            TreePath path = parent.getProjectTree().getSelectionPath();
            if (path != null && path.getLastPathComponent() instanceof SavableNode) {
                SavableNode node = (SavableNode) path.getLastPathComponent();                
               /* if (node.getUnits().size() == 0) {
                    JOptionPane.showMessageDialog(parent, 
                                 "There is no meta information to be exported in the selected node.",
                                 "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {*/
                    new ContextMenuSearchResultActions(node).perform(command); 
                //}    
            } else {
                JOptionPane.showMessageDialog(parent, 
                             "There is no selected savable node in Projects View.",
                             "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
