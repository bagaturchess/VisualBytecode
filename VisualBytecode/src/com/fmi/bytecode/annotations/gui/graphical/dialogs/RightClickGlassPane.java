package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RightClickGlassPane extends JComponent
                                 implements MouseListener, MouseMotionListener {

    protected JPopupMenu popup;
    protected Container contentPane;

    public RightClickGlassPane(Container contentPane) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.contentPane = contentPane;
    }

    public void setPopupMenu(JPopupMenu menu) {
        this.popup = menu;
    }

    // draw some text just so we know the glass pane is installed and visible
    public void paint(Graphics g) {
        //g.drawString("",50,50);
    }

    // catch all mouse events and redispatch them
    public void mouseMoved(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }
    public void mouseDragged(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }
    public void mouseClicked(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }
    public void mouseEntered(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }
    public void mouseExited(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }
    public void mousePressed(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }
    public void mouseReleased(MouseEvent e) {
        redispatchMouseEvent(e, false);
    }

    public void showPopup(Component comp, int x, int y) {
        if (popup != null) {
            try {
                popup.show(comp,x,y);
            } catch (java.awt.IllegalComponentStateException icse) {
                // Do nothing. The mouse cursor is moved outside the gui component
                // before the event processing.
            }
        }
    }

    protected void redispatchMouseEvent(MouseEvent e, boolean repaint) {
        // if it's a popup
        if(e.isPopupTrigger()) {
            // show the popup and return
            showPopup(e.getComponent(), e.getX(), e.getY());
        } else {
            doDispatch(e);
        }
    }

    protected Component getRealComponent(Point pt) {
        // get the mouse click point relative to the content pane
        Point containerPoint =
            SwingUtilities.convertPoint(this, pt, contentPane);

        // find the component that under this point
        Component component = SwingUtilities.getDeepestComponentAt(contentPane,
                                                                   containerPoint.x,
                                                                   containerPoint.y);
        return component;
    }
    
    protected void doDispatch(MouseEvent e) {
        // since it's not a popup we need to redispatch it.

        Component component = getRealComponent(e.getPoint());

        // return if nothing was found
        if (component == null) {
            return;
        }

        // convert point relative to the target component
        Point componentPoint = SwingUtilities.convertPoint(this, e.getPoint(),
                                                           component);

        // redispatch the event
        component.dispatchEvent(new MouseEvent(component, e.getID(),
                                               e.getWhen(), e.getModifiers(),
                                               componentPoint.x, componentPoint.y,
                                               e.getClickCount(), e.isPopupTrigger()));
    }
}

