package com.fmi.bytecode.annotations.gui.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
A TabbedPane that shows an close-button whenever the mouse is over the right corner.

@author Fritz Ritzberger
*/

public class CloseTabPane extends JTabbedPane implements MouseListener,
                        MouseMotionListener, ActionListener {
    private JButton close;
    private static Icon closeIcon = UIManager.getIcon("InternalFrame.closeIcon");

    public CloseTabPane() {
    this(JTabbedPane.BOTTOM);
    }
    
    public CloseTabPane(int tabPlacement) {
        super(tabPlacement);
        
        close = new JButton(closeIcon);
        //System.err.println("close icon is "+closeIcon);
        close.setBorder(null);
        close.addActionListener(this);
        
        addMouseListener(this);
        addMouseMotionListener(this);
        close.addMouseListener(this);
        close.addMouseMotionListener(this);
    }
    
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == this)
        showPopup(e);
    }
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == close)
            removeCloseButton();
        }
    public void mouseClicked(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    
    public void mouseMoved(MouseEvent e) {
        if (e.getSource() == this)
            showPopup(e);
        else
            e.consume();
    }
    
    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == this)
            showPopup(e);
        else
            e.consume();
    }
    
    private void showPopup(MouseEvent e) {
        Rectangle r = getUI().getTabBounds(this, getSelectedIndex());
        int deltaX = r.width - closeIcon.getIconWidth() - 2/*littleBitLeft*/;
        int deltaY = (r.height - closeIcon.getIconHeight()) / 2;
        r.x += deltaX;
        r.width -= deltaX;
        r.y += deltaY;
        r.height -= deltaY;
        
        if (r.contains(e.getPoint())) {
            if (close.isVisible() == false) {
                JLayeredPane layeredPane = SwingUtilities.getRootPane(this).getLayeredPane();
                Point p = SwingUtilities.convertPoint(this, r.x, r.y, layeredPane);
                close.setBounds(p.x, p.y, closeIcon.getIconWidth(), closeIcon.getIconHeight());
                layeredPane.add(close, JLayeredPane.POPUP_LAYER);
                close.setVisible(true);
            }
        } else {
            removeCloseButton();
        }
    }
    
    private void removeCloseButton() {
        if (close.isVisible()) {
            close.setVisible(false);
            JLayeredPane layeredPane = SwingUtilities.getRootPane(this).getLayeredPane();
            layeredPane.remove(close);
        }
    }
    
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == close)
            closeSelectedTab();
    }
    
    protected void closeSelectedTab() {
        int ret = JOptionPane.showConfirmDialog(
        this,
        "Really close?",
        "Message",
        JOptionPane.OK_CANCEL_OPTION);
        if (ret == JOptionPane.OK_OPTION) {
            remove(getSelectedIndex());
        }
    }
    
    
    // test main
    public static void main(String [] args) {
    CloseTabPane tp = new CloseTabPane();
        for (int i = 1; i <= 10; i++) {
            tp.addTab("Tab "+i, new JButton("Button "+i));
        }
        JFrame f = new JFrame();
        f.getContentPane().add(tp);
        f.setSize(200, 200);
        f.setVisible(true);
    }

}
   
