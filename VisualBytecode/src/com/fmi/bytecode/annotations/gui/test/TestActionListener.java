package com.fmi.bytecode.annotations.gui.test;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.LoadProjectApplier;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.BrowseFileSystemDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.NewProjectDialog;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TestActionListener implements ActionListener {
    private Frame parent;
    private LoadProjectApplier loadProjectApplier;
    
    public TestActionListener(Frame parent) {
        this.parent = parent;
        //loadProjectApplier = new LoadProjectApplier(parent.getProjectTree());
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //System.out.println(command);
        if (command.equals("NewProject")) {
            //new NewProjectDialog(parent);
        } else if (command.equals("OpenProject")) {
           //System.out.println(command);
           // new BrowseFileSystemDialog(parent,
             //                          BrowseFileSystemDialog.FILES_ONLY,
               //                        loadProjectApplier,
                 //                      BrowseFileSystemDialog.FILTERS_PROJECT);
        } //else if (command.equals("CloseProject")) {
           //((MainFrame) parent.getProjectTree())
          //ProjectModel projectModel = selectedProjectNode.getProjectModel();
           
         
           // ModelsRepository.getModelsRepository().removeProject(projectModel);
           // parent.getProjectTree().updateUI();
        //}     
    }
}    