package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import java.awt.event.ActionListener;

/**
 * Process tree's context menu action for specified command.
 * Implememtation of this will know the selected tree node and 
 * the sequence of specific actions which have to be performed.
 */
public interface ContextMenuActionPerformer extends ActionListener {
    public void perform(String command);
}
