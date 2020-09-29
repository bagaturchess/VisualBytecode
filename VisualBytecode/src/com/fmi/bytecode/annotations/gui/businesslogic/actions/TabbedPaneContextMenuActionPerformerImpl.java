package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuActionPerformerImpl;

import javax.swing.JTabbedPane;

public class TabbedPaneContextMenuActionPerformerImpl extends ContextMenuActionPerformerImpl {

    private JTabbedPane tabbedPane;

    public TabbedPaneContextMenuActionPerformerImpl(JTabbedPane _tabbedPane) {
        tabbedPane = _tabbedPane;
    }
    
    public void perform(String command) {
        if ("CloseAllTabs".equals(command)) {
            tabbedPane.removeAll();
        }
    }
}
