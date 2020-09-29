package com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers;

import java.io.File;

/**
 * To apply selected File to GUI component (textField, List) 
 *
 */
 
public interface SelectedFileValueApplier {
    public void apply(File selectedFile) throws Exception;
}
