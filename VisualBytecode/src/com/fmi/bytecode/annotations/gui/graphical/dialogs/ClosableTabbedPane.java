package com.fmi.bytecode.annotations.gui.graphical.dialogs;


import java.awt.AWTEvent;

import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;

public class ClosableTabbedPane extends JTabbedPane {
    
    private Icon icon;
    
    public ClosableTabbedPane(Icon closeButtonIcon) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        icon = closeButtonIcon;
    }
    
    protected void processMouseEvent(MouseEvent evt) {
        int tabNumber = getUI().tabForCoordinate(this, evt.getX(), evt.getY());
 
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


