package com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.RightClickGlassPane;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview.ViewTreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ClassNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.PackageNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeModelImpl;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectContentNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project.ProjectsNode;

public class ProjectTreeMouseListener implements MouseListener {
    private JTree projectTree;
    
    private JPopupMenu projectsPopup = new JPopupMenu();
    private JPopupMenu projectPopup = new JPopupMenu();
    private JPopupMenu contentPopup = new JPopupMenu();
    private JPopupMenu packagePopup = new JPopupMenu();
    private JPopupMenu classPopup = new JPopupMenu();
    
    private JMenuItem annotationSearchPrjsMenuItem = new JMenuItem(" Find Annotations");
    private JMenuItem annotationSearchPrjMenuItem = new JMenuItem(" Find Annotations");
    private JMenuItem annotationSearchCntMenuItem = new JMenuItem(" Find Annotations");
    private JMenuItem annotationSearchPkgMenuItem = new JMenuItem(" Find Annotations");
    private JMenuItem annotationSearchClassMenuItem = new JMenuItem(" Find Annotations");
    private JMenuItem newProjectMenuItem = new JMenuItem(" New Project");
    private JMenuItem addProjectMenuItem = new JMenuItem(" Add Project");
    private JMenuItem closeProjectMenuItem = new JMenuItem(" Close Project");
    private JMenuItem projectPropertiesMenuItem = new JMenuItem(" Project Properties");
    private JMenuItem closeAllMenuItem = new JMenuItem("  Close All");
    private JMenuItem exportPrjsMenuItem = new JMenuItem(" Export to XML");
    private JMenuItem exportPrjMenuItem = new JMenuItem(" Export to XML");
    private JMenuItem exportContentMenuItem = new JMenuItem(" Export to XML");
    private JMenuItem exportPackageMenuItem = new JMenuItem(" Export to XML");
    private JMenuItem exportClassMenuItem = new JMenuItem(" Export to XML");
    
    private ContextMenuActionListener ctxMenuListener = 
                                                new ContextMenuActionListener();
    
    
    public ProjectTreeMouseListener(JTree projectTree) {
        this.projectTree = projectTree;
        initPopups();
    }
    
    private void initPopups() {
        //projects
        projectsPopup.add(annotationSearchPrjsMenuItem);
        projectsPopup.add(exportPrjsMenuItem);
        projectsPopup.addSeparator();
        projectsPopup.add(newProjectMenuItem);
        projectsPopup.add(addProjectMenuItem);
        projectsPopup.addSeparator();
        projectsPopup.add(closeAllMenuItem);
        annotationSearchPrjsMenuItem.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        exportPrjsMenuItem.setIcon(GUIComponentsRepository.XML_EXPORT);
        newProjectMenuItem.setIcon(GUIComponentsRepository.IMAGE_NEW);
        addProjectMenuItem.setIcon(GUIComponentsRepository.IMAGE_OPEN);
        closeAllMenuItem.setIcon(GUIComponentsRepository.CLOSE_ALL);
        //projects-shortKeys
        annotationSearchPrjsMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_F);
        exportPrjsMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_E);
        newProjectMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_N);
        addProjectMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_O);
        closeAllMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_SHIFT_F4);
               
        annotationSearchPrjsMenuItem.setActionCommand("AnnotationSearch");
        exportPrjsMenuItem.setActionCommand("Export");
        newProjectMenuItem.setActionCommand("NewProject");
        addProjectMenuItem.setActionCommand("AddProject");
        closeAllMenuItem.setActionCommand("CloseAll");
        
        
        annotationSearchPrjsMenuItem.addActionListener(ctxMenuListener);
        exportPrjsMenuItem.addActionListener(ctxMenuListener);
        newProjectMenuItem.addActionListener(ctxMenuListener);
        addProjectMenuItem.addActionListener(ctxMenuListener);
        closeAllMenuItem.addActionListener(ctxMenuListener);
       
        //project
        projectPopup.add(annotationSearchPrjMenuItem);
        projectPopup.add(exportPrjMenuItem);
        projectPopup.addSeparator();
        projectPopup.add(projectPropertiesMenuItem);
        projectPopup.add(closeProjectMenuItem);
        annotationSearchPrjMenuItem.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        exportPrjMenuItem.setIcon(GUIComponentsRepository.XML_EXPORT);
        closeProjectMenuItem.setIcon(GUIComponentsRepository.REMOVE_FILE);
        projectPropertiesMenuItem.setIcon(GUIComponentsRepository.PROJECT_PROPS);
        
        annotationSearchPrjMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_F);
        exportPrjMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_E);
        closeProjectMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_F4);
        projectPropertiesMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_P);
                
        annotationSearchPrjMenuItem.setActionCommand("AnnotationSearch");
        exportPrjMenuItem.setActionCommand("Export");
        projectPropertiesMenuItem.setActionCommand("ProjectProps");
        closeProjectMenuItem.setActionCommand("CloseProject");
               
        annotationSearchPrjMenuItem.addActionListener(ctxMenuListener);
        exportPrjMenuItem.addActionListener(ctxMenuListener);
        projectPropertiesMenuItem.addActionListener(ctxMenuListener);
        closeProjectMenuItem.addActionListener(ctxMenuListener);
        
        //content
        contentPopup.add(annotationSearchCntMenuItem);
        contentPopup.add(exportContentMenuItem);
        annotationSearchCntMenuItem.setActionCommand("AnnotationSearch");
        exportContentMenuItem.setActionCommand("Export");
        annotationSearchCntMenuItem.addActionListener(ctxMenuListener);
        exportContentMenuItem.addActionListener(ctxMenuListener);
        annotationSearchCntMenuItem.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        exportContentMenuItem.setIcon(GUIComponentsRepository.XML_EXPORT);
        annotationSearchCntMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_F);     
        exportContentMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_E);
        
        //package
        packagePopup.add(annotationSearchPkgMenuItem);
        packagePopup.add(exportPackageMenuItem);
        annotationSearchPkgMenuItem.setActionCommand("AnnotationSearch");
        exportPackageMenuItem.setActionCommand("Export");
        annotationSearchPkgMenuItem.addActionListener(ctxMenuListener);
        exportPackageMenuItem.addActionListener(ctxMenuListener);
        annotationSearchPkgMenuItem.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        exportPackageMenuItem.setIcon(GUIComponentsRepository.XML_EXPORT);
        annotationSearchPkgMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_F);
        exportPackageMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_E);
        
        //class
        classPopup.add(annotationSearchClassMenuItem);   
        classPopup.add(exportClassMenuItem);
        annotationSearchClassMenuItem.setActionCommand("AnnotationSearch");
        exportClassMenuItem.setActionCommand("Export");
       
        annotationSearchClassMenuItem.addActionListener(ctxMenuListener);
        exportClassMenuItem.addActionListener(ctxMenuListener);
       
        annotationSearchClassMenuItem.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        exportClassMenuItem.setIcon(GUIComponentsRepository.XML_EXPORT);
        annotationSearchClassMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_F);     
        exportClassMenuItem.setAccelerator(GUIComponentsRepository.SK_CTRL_E);
    }
 
    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {
        int selRow = projectTree.getRowForLocation(e.getX(), e.getY());
        TreePath selPath = projectTree.getPathForLocation(e.getX(), e.getY());

        showGlassPane(false);

        if (selPath != null) {
            //System.out.println("[ProjectTreeEvent] on path: " + selPath);
            
            //Get last component node
            Object clickedNode = selPath.getLastPathComponent();
            if(selRow != -1) {
                int btn = e.getButton();
                if(e.getClickCount() == 1) {
                    if (btn == MouseEvent.BUTTON1) {
                        processLeftButtonOneClick();
                    } else {
                        projectTree.setSelectionPath(selPath);
                        processRightButtonOneClick(clickedNode);
                    }    
                } else if(e.getClickCount() == 2) {
                    if (btn == MouseEvent.BUTTON1) {
                        processLeftButtonDoubleClick(clickedNode);
                    } else {
                        processRightButtonDoubleClick();
                    } 
                }    
            }
        }
    }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) { }

    public void mouseExited(MouseEvent e) { }
    
    
    private void processLeftButtonOneClick() {}

    
    private void processLeftButtonDoubleClick(Object clickedNode) {
        if (clickedNode instanceof ClassNode) {
            ClassNode classNode = (ClassNode) clickedNode;
            ClassInfo classInfo = classNode.getClassInfo();
            TreeNode root = ViewTreeUtils.createTree(classInfo);
            GUIComponentsRepository.getComponentsRepository().getMainFrame().
                addClassViewTreeModel(new TreeModelImpl(root), classInfo);
        }/* else {
            showViewTree.setModel(new TreeModelImpl(null));
        }
        showViewTree.updateUI();*/
    }
    
    public void processRightButtonOneClick(Object clickedNode) {
        if (clickedNode instanceof ProjectsNode) {
            processProjectsNode((ProjectsNode) clickedNode);
        } else if (clickedNode instanceof ProjectNode) {
            processProjectNode((ProjectNode) clickedNode);
        } else if (clickedNode instanceof ProjectContentNode) {
            processContentNode((ProjectContentNode) clickedNode);
        } else if (clickedNode instanceof PackageNode) {
            processPackageNode((PackageNode) clickedNode);
        } else if (clickedNode instanceof ClassNode) {
            processClassNode((ClassNode) clickedNode);
        } else {
            throw new IllegalStateException("Invalid Node Type");
        }
    }
    
    private void processRightButtonDoubleClick() {
        
    }
    
    private void processProjectsNode(ProjectsNode projectsNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuProjectsActions(projectsNode));
        getGlassPane().setPopupMenu(projectsPopup);
        showGlassPane(true);
    }
    
    private void processProjectNode(ProjectNode projectNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuProjectActions(projectNode));
        getGlassPane().setPopupMenu(projectPopup);
        showGlassPane(true);
    }
    
    private void processContentNode(ProjectContentNode prjCntNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuProjectContentActions(prjCntNode));
        getGlassPane().setPopupMenu(contentPopup);
        showGlassPane(true);
    }
    
    private void processPackageNode(PackageNode packageNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuPackageActions(packageNode));
        getGlassPane().setPopupMenu(packagePopup);
        showGlassPane(true);
    }
    
    private void processClassNode(ClassNode classNode) {
        ctxMenuListener.setContextMenuActionPerformer(new ContextMenuClassActions(classNode));
        getGlassPane().setPopupMenu(classPopup);
        showGlassPane(true);
    }
    
    private void showGlassPane(boolean show) {
        getGlassPane().setVisible(show);
    }

    private RightClickGlassPane getGlassPane() {
       return GUIComponentsRepository.getComponentsRepository().
                             getMainFrame().getProjectTreeRightClickGlassPane();
    }
}
