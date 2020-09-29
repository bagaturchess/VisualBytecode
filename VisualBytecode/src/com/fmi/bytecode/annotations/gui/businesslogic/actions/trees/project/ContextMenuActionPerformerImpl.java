package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.AnnotationsSearchDialog;

import java.awt.Frame;

import java.awt.event.ActionEvent;

import java.io.File;

import java.util.List;

import javax.swing.JOptionPane;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

public abstract class ContextMenuActionPerformerImpl implements ContextMenuActionPerformer {
    
    public void actionPerformed(ActionEvent e) {
        perform(e.getActionCommand());
    }
    
    protected void openAnnotationsSearchDialog(Frame parent, 
                                               ProjectModel projectModel, 
                                               File content, String packageName, 
                                               List<String> allPackages, 
                                               List<String> allAnnotations,
                                               boolean enablePackageCombobox) {
        if (allAnnotations.size() > 1) {
            new AnnotationsSearchDialog(parent, projectModel, content, 
                                        packageName, allPackages, allAnnotations, 
                                        enablePackageCombobox);
        } else {
            JOptionPane.showMessageDialog(GUIComponentsRepository.getComponentsRepository().getMainFrame(), 
                                          "There is no sense to search for annotations in the selected classes set.\r\n" +
                                          "The set is empty or it doesn't contain any annotations.",
                                          "Information", JOptionPane.INFORMATION_MESSAGE);
        }
    }                                
}
