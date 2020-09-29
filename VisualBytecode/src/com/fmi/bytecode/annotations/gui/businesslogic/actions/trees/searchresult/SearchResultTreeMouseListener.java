package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuActionListener;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.RightClickGlassPane;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.util.List;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview.ViewTreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeModelImpl;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult.LevelNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult.PackageAndClassNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult.ProjectAndContentNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult.ResultNode;

public class SearchResultTreeMouseListener implements MouseListener{
    
    private JTree searchTree;
    private RightClickGlassPane glassPane;
    private JPopupMenu searchResultTreePopup;
    private JMenuItem exportClassMenuItem = new JMenuItem(" Export to XML");
    
    private ContextMenuActionListener ctxMenuListener = 
                                                new ContextMenuActionListener();
                                                
    public SearchResultTreeMouseListener(JTree searchTree, RightClickGlassPane glassPane) {
        this.searchTree = searchTree;
        this.glassPane = glassPane;
        this.searchResultTreePopup = new JPopupMenu();
        this.searchResultTreePopup.add(exportClassMenuItem);
        exportClassMenuItem.setIcon(GUIComponentsRepository.XML_EXPORT);
        exportClassMenuItem.setActionCommand("Export");
        exportClassMenuItem.addActionListener(ctxMenuListener);
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    
        getGlassPane().setVisible(false);
    
        int selRow = searchTree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = searchTree.getPathForLocation(e.getX(), e.getY());

        if (selPath != null) {
            //System.out.println("[Search Result Tree] on path: " + selPath);
            
            //Get last component node
            Object clickedNode = selPath.getLastPathComponent();
            if(selRow != -1) {
                int btn = e.getButton();
                if(e.getClickCount() == 1) {
                    if (btn == MouseEvent.BUTTON1) {
                       // processLeftButtonOneClick();
                    } else {
                        searchTree.setSelectionPath(selPath);
                        processRightButtonOneClick(clickedNode);
                    }    
                } else if(e.getClickCount() == 2) {
                    if (btn == MouseEvent.BUTTON1) {
                        processLeftButtonDoubleClick(clickedNode);
                    } else {
                        //processRightButtonDoubleClick();
                    } 
                }    
            }
        }
    }

                         
    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
    
    private void processLeftButtonDoubleClick(Object clickedNode) {
        if (clickedNode instanceof PackageAndClassNode) {
            PackageAndClassNode classNode = (PackageAndClassNode) clickedNode;
            ClassInfo classInfo = classNode.getClassInfo();
            List<AnnotationRecord> classAnnotations
                                             = classNode.getClassAnnotations();
            TreeNode root = ViewTreeUtils.createTree(classInfo, classAnnotations);GUIComponentsRepository.getComponentsRepository().getMainFrame().
                addClassViewTreeModel(new TreeModelImpl(root), classInfo);
        } /*else {
            GUIComponentsRepository.getComponentsRepository().getMainFrame().setClassViewTreeModel(new TreeModelImpl(root));
            showViewTree.setModel(new TreeModelImpl(null));
        }*/
        //showViewTree.updateUI();
    }

    
    public void processRightButtonOneClick(Object clickedNode) {
        if (clickedNode instanceof ResultNode) {
            processResultNode((ResultNode) clickedNode);
        } else if (clickedNode instanceof LevelNode) {
            processLevelNode((LevelNode) clickedNode);
        } else if (clickedNode instanceof ProjectAndContentNode) {
            processProjectAndContentNode((ProjectAndContentNode) clickedNode);
        } else if (clickedNode instanceof PackageAndClassNode) {
            processPackageAndClassNode((PackageAndClassNode) clickedNode);
        } else {
            throw new IllegalStateException("Invalid Node Type");
        }
    }

    
    private void processResultNode(ResultNode resultNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuSearchResultActions(resultNode));
        getGlassPane().setPopupMenu(searchResultTreePopup);
        showGlassPane(true);
    }
    
    private void processLevelNode(LevelNode levelNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuSearchResultActions(levelNode));
        getGlassPane().setPopupMenu(searchResultTreePopup);
        showGlassPane(true);
    }
    
    private void processProjectAndContentNode(ProjectAndContentNode prjCntNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuSearchResultActions(prjCntNode));
        getGlassPane().setPopupMenu(searchResultTreePopup);
        showGlassPane(true);
    }
        
    private void processPackageAndClassNode(PackageAndClassNode classNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuSearchResultActions(classNode));
        getGlassPane().setPopupMenu(searchResultTreePopup);
        showGlassPane(true);
    }
    
    private void showGlassPane(boolean show) {
        getGlassPane().setVisible(show);
    }

    private RightClickGlassPane getGlassPane() {
       return glassPane;
    }
}
