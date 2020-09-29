package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.SelectedFileValueApplier;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.BrowseFileSystemDialog;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.NewProjectDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.Vector;

import javax.swing.JList;

import javax.swing.JOptionPane;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.utils.ModelUtils;


public class NewProjectActionListener implements ActionListener {
    private SelectedFileValueApplier projectDirFieldApplier;
    private SelectedFileValueApplier projectContentApplier;
    private NewProjectDialog projectDialog;
    private JList projectContentList;
    private Vector<File> projectContentItemsList;
    
    public NewProjectActionListener(SelectedFileValueApplier projectDirFieldApplier,
                                    SelectedFileValueApplier projectContentApplier,
                                    NewProjectDialog projectDialog, 
                                    JList projectContentList, 
                                    Vector<File> projectContentItemsList) {
        this.projectDirFieldApplier  = projectDirFieldApplier;
        this.projectContentApplier   = projectContentApplier;
        this.projectDialog           = projectDialog;
        this.projectContentList      = projectContentList;
        this.projectContentItemsList = projectContentItemsList;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //System.out.println(command); 
        if (command.equals("Browse")) {
            new BrowseFileSystemDialog(projectDialog, BrowseFileSystemDialog.DIRECTORIES_ONLY,
                                       projectDirFieldApplier,BrowseFileSystemDialog.FILTERS_NONE);
        } else if (command.equals("Add")) {
            new BrowseFileSystemDialog(projectDialog, BrowseFileSystemDialog.FILES_AND_DIRECTORIES,
                                       projectContentApplier,BrowseFileSystemDialog.FILTERS_NONE);
        } else if (command.equals("Remove")) {
            removeListContent();
        } else if (command.equals("Cancel")) {
            projectDialog.dispose();
        } else if (command.equals("Apply")) {
            try {          
                String prjName = projectDialog.getTextFieldProjectName().getText();
                String prjDir = projectDialog.getTextFieldProjectDir().getText();
                ProjectModel newProjectModel 
                                = new ProjectModel(prjDir, prjName, projectContentItemsList);               
                ProjectModel oldProjectModel = projectDialog.getProjectModel();

                boolean saveFile = true;
                
                String newProjectFilePath = ModelUtils.getProjectFileName(newProjectModel);
                String oldProjectFilePath = ""; 
                if (oldProjectModel != null) {
                    oldProjectFilePath = ModelUtils.getProjectFileName(oldProjectModel);
                }
            
                if (!newProjectFilePath.equals(oldProjectFilePath)) {
                    if (new File(newProjectFilePath).exists()) {
                        int option = JOptionPane.showOptionDialog(projectDialog, 
                             "The file " + newProjectFilePath + 
                             " you specified already exists.\r\nDo you want to replace the existing file?",
                             "Warning",
                             JOptionPane.YES_NO_CANCEL_OPTION,
                             JOptionPane.QUESTION_MESSAGE,
                             null, //Icon
                             new Object[] {"Yes", "No"}, "No");
                        
                        if (option != JOptionPane.YES_OPTION) {
                            saveFile = false;
                        }
                    }
                }
                
                if (saveFile) {
                    if (oldProjectModel == null) {
                        ModelsRepository.getModelsRepository().addProjectModel(newProjectModel);
                    } else {
                        ModelsRepository.getModelsRepository().updateProjectModel(oldProjectModel, newProjectModel);
                    }
                    projectDialog.setProjectModel(newProjectModel);

                    GUIComponentsRepository.getComponentsRepository().getMainFrame().getProjectTree().updateUI();
                    ModelUtils.saveProjectModel(newProjectModel);
                    
                    JOptionPane.showMessageDialog(projectDialog, "The project "+ newProjectModel.getProjectName() +
                                                  " was successfully saved",
                                                 "Information", JOptionPane.INFORMATION_MESSAGE);
                }                                   
            } catch (Exception f) {
                JOptionPane.showMessageDialog(projectDialog, f.getMessage(),
                                             "Error message", JOptionPane.ERROR_MESSAGE);
            }    
        }    
    }

    private void removeListContent() {
        int[] selectedIndxes      = projectContentList.getSelectedIndices();
        int countElementsToRemove = selectedIndxes.length;
        
        if (countElementsToRemove > 0) {        
            for (int i = countElementsToRemove - 1; i >= 0; i--) {
                projectContentItemsList.remove(selectedIndxes[i]);
            }
            projectContentList.setListData(projectContentItemsList);
            
            int maxRemovedElementIndx = selectedIndxes[countElementsToRemove - 1];
            int newElementIndx = maxRemovedElementIndx - countElementsToRemove + 1;
            //System.out.println(newElementIndx);
            int newVectorSize = projectContentItemsList.size();    
            if (newVectorSize > 0 ) {
                if (newElementIndx >= newVectorSize) {
                    newElementIndx = newVectorSize - 1;
                }
                if (newElementIndx >= 0) {
                    projectContentList.setSelectedIndex(newElementIndx);
                } 
            }
        }           
    }
}
