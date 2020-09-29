package com.fmi.bytecode.annotations.gui.test;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

public class ColourBrush {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        JTree tree = new JTree();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(tree, BorderLayout.CENTER);
        
        tree.setCellRenderer(       
            new DefaultTreeCellRenderer() {
                public Component getTreeCellRendererComponent(JTree pTree,
                            Object pValue, boolean pIsSelected, 
                            boolean pIsExpanded, boolean pIsLeaf, 
                            int pRow, boolean pHasFocus) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) pValue;
                    super.getTreeCellRendererComponent(pTree, pValue, pIsSelected,
                                                       pIsExpanded, pIsLeaf, 
                                                       pRow, pHasFocus);
                   if (node.isRoot())
                       setForeground(Color.red);
                    else if (node.getChildCount() > 0)
                       setBackgroundSelectionColor(Color.yellow);
                    else if (pIsLeaf)
                       setBackgroundSelectionColor(Color.green);
                    return (this);
                }
            }
        );
        
        
        frame.setSize(500, 500);
        frame.pack();
        frame.setVisible(true);
    }
}

