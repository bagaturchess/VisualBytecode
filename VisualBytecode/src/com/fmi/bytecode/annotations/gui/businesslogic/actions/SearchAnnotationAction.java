package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuClassActions;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuPackageActions;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuProjectActions;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuProjectContentActions;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuProjectsActions;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ClassNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.PackageNode;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectContentNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectsNode;

public class SearchAnnotationAction {
    private JTree tree;
    
    public SearchAnnotationAction(JTree tree) {
        this.tree = tree;
    }

    public void openSearchDialog() {
        TreePath path = tree.getSelectionPath();
        if (path != null) {
            Object clickedNode = path.getLastPathComponent();
            String command ="AnnotationSearch";
            if (clickedNode instanceof ProjectsNode) {
                new ContextMenuProjectsActions((ProjectsNode) clickedNode).perform(command);
            } else if (clickedNode instanceof ProjectNode) {
                new ContextMenuProjectActions((ProjectNode) clickedNode).perform(command);
            } else if (clickedNode instanceof ProjectContentNode) {
                new ContextMenuProjectContentActions((ProjectContentNode) clickedNode).perform(command);
            } else if (clickedNode instanceof PackageNode) {
                new ContextMenuPackageActions((PackageNode) clickedNode).perform(command);
            } else if (clickedNode instanceof ClassNode) {
                new ContextMenuClassActions((ClassNode) clickedNode).perform(command);
            } else {
                throw new IllegalStateException("Invalid Node Type");
            }
        } else {
            JOptionPane.showMessageDialog(GUIComponentsRepository.getComponentsRepository().getMainFrame(), 
                   "There is no selected tree node to search in.",
                   "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
