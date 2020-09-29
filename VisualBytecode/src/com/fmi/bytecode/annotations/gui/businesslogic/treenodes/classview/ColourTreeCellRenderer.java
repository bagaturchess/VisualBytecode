package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import com.fmi.bytecode.annotations.element.AnnotationRecord;

import java.awt.Color;
import java.awt.Component;

import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.TreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;

public class ColourTreeCellRenderer extends DefaultTreeCellRenderer {
    
    private List<AnnotationRecord> colouredAnnotations;
    
    public ColourTreeCellRenderer(List<AnnotationRecord> colouredAnnotations) {
        this.colouredAnnotations = colouredAnnotations;
    }
    
    public Component getTreeCellRendererComponent(JTree pTree,
                                                  Object pValue, boolean pIsSelected, 
                                                  boolean pIsExpanded, boolean pIsLeaf, 
                                                  int pRow, boolean pHasFocus) {
        
        super.getTreeCellRendererComponent(pTree, pValue, pIsSelected,
                                           pIsExpanded, pIsLeaf, 
                                           pRow, pHasFocus);
        
        if (!(pValue instanceof ClassViewNode)) {
            if (colouredAnnotations != null) {
                for (AnnotationRecord annotationRecord : colouredAnnotations) {
                    if (TreeUtils.findNodeByIDInSubTree((TreeNodeBaseImpl) pValue, annotationRecord) != null) {
                        setForeground(Color.red);
                        break;
                    }
                }
            }
        }
        /*if (node.isRoot())
           setForeground(Color.red);
        else if (node.getChildCount() > 0)
           setBackgroundSelectionColor(Color.yellow);
        else if (pIsLeaf)
           setBackgroundSelectionColor(Color.green);*/
        
        return (this);
    }
}
