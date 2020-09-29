package com.fmi.bytecode.annotations.gui.graphical.dialogs;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
 
public class IconProxy implements Icon {
	
    private int x;
    private int y;
    private int xMax;
    private int yMax;
    private int width;
    private int height;
    private Icon icon;
    
    public IconProxy(Icon icon) {
        this.icon = icon;
        width  = icon.getIconWidth();
        height = icon.getIconHeight();
    }
    
    public int getIconWidth() {
        return width;
    }
    
    public int getIconHeight() {
        return height;
    }
    
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x = x;
        this.y = y;
        xMax = x + width;
        yMax = y + height;
        icon.paintIcon(c, g, x, y);
    }
    
    public boolean contains(int x, int y) {
        return x >= this.x && x <= xMax && y >= this.y && y <= yMax;
    }
}


//if imageIcon is the ImageIcon that holds the close button image the tabs should be added like this:

//tabs.addTab(tabName, new IconProxy(imageIcon), component);
//and this listener is added to the JTabbedPane to handle the mouse clicks:
//tabs.addMouseListener(new MouseAdapter() {
   
  /*  public void mouseReleased(MouseEvent evt) {
        if (tabs.getTabCount() == 0) {
            return;
        }
        
        if (!evt.isPopupTrigger()) {
            IconProxy iconProxy = (IconProxy) tabs.getIconAt(
                    tabs.getSelectedIndex());
            
            if (iconProxy.contains(evt.getX(), evt.getY())) {
                tabs.removeTabAt(tabs.getSelectedIndex());
            }
        }
    }
    
}*/




