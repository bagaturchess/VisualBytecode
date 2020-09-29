package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextMenuActionListener implements ActionListener {
    
    private ContextMenuActionPerformer selectedNodeActionPerformer;
    
    public ContextMenuActionListener() {
    }

    public void actionPerformed(ActionEvent e) {
        selectedNodeActionPerformer.perform(e.getActionCommand());
    }

    public void setContextMenuActionPerformer(ContextMenuActionPerformer selectedNodeActionPerformer) {
        this.selectedNodeActionPerformer = selectedNodeActionPerformer;
    }
}
