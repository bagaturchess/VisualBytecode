package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuActionPerformerImpl;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.ExportToXMLDialog;

import java.util.List;

import javax.swing.JOptionPane;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

public class ContextMenuSearchResultActions extends ContextMenuActionPerformerImpl {
   
    private SavableNode node;
    
    public ContextMenuSearchResultActions(SavableNode node) {
        this.node = node;
    }

    public void perform(String command) {
        //System.out.println("Export: " + command + "  ->  " + node.getUnits());
        if (command.equals("Export")) {
            List<SaveUnit> units = node.getUnits();
            
            if (units == null || units.size() < 1) {
                JOptionPane.showMessageDialog(GUIComponentsRepository.getComponentsRepository().getMainFrame(), 
                             "There is no meta information to be exported in the selected node.",
                             "Information", JOptionPane.INFORMATION_MESSAGE);
            } else {               
                new ExportToXMLDialog(GUIComponentsRepository.getComponentsRepository().getMainFrame(), 
                                      units);
            }                          
        }
    }
}
