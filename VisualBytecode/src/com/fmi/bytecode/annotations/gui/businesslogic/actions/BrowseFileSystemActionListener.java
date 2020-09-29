package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.SelectedFileValueApplier;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class BrowseFileSystemActionListener implements ActionListener {
    
    private SelectedFileValueApplier componentApplier;
    // to get selected file
    private JDialog fileChooserDialog; 
    private JFileChooser fileChooser;
    
    
    public BrowseFileSystemActionListener(JDialog fileChooserDialog,
                                          JFileChooser fileChooser,
                                          SelectedFileValueApplier componentApplier) {
        this.fileChooserDialog = fileChooserDialog;
        this.fileChooser = fileChooser;
        this.componentApplier = componentApplier;    
    }


    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //System.out.println(command);
        if (command.equals(JFileChooser.APPROVE_SELECTION)) {
            try {
                componentApplier.apply(fileChooser.getSelectedFile());
                fileChooserDialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(fileChooserDialog, ex.getMessage(),
                                             "Error message", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
            fileChooserDialog.dispose();      
        }
    }
}
