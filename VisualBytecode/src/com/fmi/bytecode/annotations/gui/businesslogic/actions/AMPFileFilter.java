package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import java.io.File;

import javax.swing.filechooser.FileFilter;


public class AMPFileFilter extends FileFilter {
    
    private String acceptedFileExtension;
    private String description;
    
    public AMPFileFilter(String fileExt, String desc) {
        acceptedFileExtension = fileExt;
        description = desc;
    }

    public boolean accept(File f) { 
        return f.isDirectory() || f.getAbsolutePath().endsWith(acceptedFileExtension);
    }

    public String getDescription() {
        return description;
    }
}
