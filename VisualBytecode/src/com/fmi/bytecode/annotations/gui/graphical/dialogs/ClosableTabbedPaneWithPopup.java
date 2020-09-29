package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.TabbedPaneContextMenuActionPerformerImpl;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuActionListener;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ContextMenuActionPerformer;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;

public class ClosableTabbedPaneWithPopup extends JRootPane {

    private TabbedPane tabbedPane;
    private RightClickGlassPane rightClickGlassPane;
    
    private JPopupMenu classAllpopup;
    
    private ContextMenuActionListener ctxMenuListener;
    private ContextMenuActionPerformer selectedNodeActionPerformer;

    public ClosableTabbedPaneWithPopup(Icon closeButtonIcon) {
        this(closeButtonIcon, null);
    }
    
    public ClosableTabbedPaneWithPopup(Icon closeButtonIcon, 
                                       ContextMenuActionPerformer _selectedNodeActionPerformer) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        tabbedPane = new TabbedPane(closeButtonIcon);
        rightClickGlassPane = new RightClickGlassPane(tabbedPane);
        rightClickGlassPane.setVisible(false);
        getContentPane().add(tabbedPane);
        setGlassPane(rightClickGlassPane);
        
        selectedNodeActionPerformer = _selectedNodeActionPerformer;
        if (selectedNodeActionPerformer == null) {
            selectedNodeActionPerformer = new TabbedPaneContextMenuActionPerformerImpl(tabbedPane);
        }
        
        ctxMenuListener = new ContextMenuActionListener();
        ctxMenuListener.setContextMenuActionPerformer(selectedNodeActionPerformer);

        classAllpopup = createClassAllPopup();
    }
    
    private JPopupMenu createClassAllPopup() {
        
        classAllpopup = new JPopupMenu();
        
        JMenuItem closeAllTabsMenuItem = new JMenuItem(" Close All");
        closeAllTabsMenuItem.setActionCommand("CloseAllTabs");
        closeAllTabsMenuItem.addActionListener(ctxMenuListener);
        closeAllTabsMenuItem.setIcon(GUIComponentsRepository.CLOSE_ALL);
        
        classAllpopup.add(closeAllTabsMenuItem);
        
        return classAllpopup;
    }

    public RightClickGlassPane getRightClickGlassPane() {
        return rightClickGlassPane;
    }

    private void showGlassPane(boolean show) {
        getRightClickGlassPane().setPopupMenu(classAllpopup);
        getRightClickGlassPane().setVisible(show);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    private class TabbedPane extends JTabbedPane {
        
        private Icon icon;
        
        public TabbedPane(Icon closeButtonIcon) {
            enableEvents(AWTEvent.MOUSE_EVENT_MASK);
            icon = closeButtonIcon;
        }
        
        protected void processMouseEvent(MouseEvent evt) {
            int tabNumber = getUI().tabForCoordinate(this, evt.getX(), evt.getY());
        
            if (tabNumber > -1) {
                showGlassPane(true);
            } else {
                showGlassPane(false);
            }
               
            if (evt.getID() == MouseEvent.MOUSE_CLICKED && tabNumber > -1
                && ((IconProxy) getIconAt(tabNumber)).contains(evt.getX(), evt.getY())) {
        
                int oldSelTab = getSelectedIndex();
                int oldTabCount = getTabCount();
                removeTabAt(tabNumber);
                
                if (tabNumber < oldSelTab && oldSelTab > 0 
                    && oldSelTab != (oldTabCount - 1)) {
                    
                    setSelectedIndex(oldSelTab - 1);
                }
                return;
            }
            
            super.processMouseEvent(evt);
        }
        
        public void addTab(String title, Component component) {
            addTab(title, new IconProxy(icon), component);
        }    
    } 
}
