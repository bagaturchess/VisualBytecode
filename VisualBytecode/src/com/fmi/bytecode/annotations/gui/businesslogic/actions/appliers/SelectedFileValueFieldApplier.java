package com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers;

import java.io.File;

import javax.swing.JTextField;

public class SelectedFileValueFieldApplier implements SelectedFileValueApplier {
    
    private JTextField field;
    
    public SelectedFileValueFieldApplier(JTextField field) {
        this.field = field;
    }

    public void apply(File selectedFile) {
        field.setText(selectedFile.getAbsolutePath());
    }
    
}
