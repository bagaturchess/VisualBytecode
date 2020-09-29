package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashSet;

import java.util.Set;


import javax.swing.JDialog;


public class DialogsShortKeyListenener  implements KeyListener {
    private JDialog dialog;
    
    public DialogsShortKeyListenener(JDialog dialog) {
        this.dialog = dialog;
        visitComponents(dialog, new HashSet<Component>());
    }
    
    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        //System.out.println("KeyPressed");
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            //System.out.println("dispose");
            dialog.dispose();
        }     
    }

    public void keyReleased(KeyEvent e) {}
    
    private void visitComponents(JDialog dialog, Set<Component> traversed) {
        visitComponents(dialog.getContentPane(), traversed);
    }
    
    private void visitComponents(Container container, Set<Component> traversed) {
        
        container.addKeyListener(this);
        
        Component[] comps  = container.getComponents();
        for (Component currComp : comps) {
            if (!traversed.contains(currComp)) {
                traversed.add(currComp);
                
                currComp.addKeyListener(this);
                
                /*if (currComp instanceof JComponent) {
                    JComponent jcomp = (JComponent)currComp;
                    visitComponents(jcomp, traversed);
                }*/
                if (currComp instanceof Container) {
                     Container currContainer = (Container) currComp;
                     visitComponents(currContainer, traversed);
                }
                
                /*if (currComp instanceof JScrollPane) {
                    JScrollPane scroll = (JScrollPane)currComp;
                    visitComponents(scroll.getViewport(), traversed);
                }*/
            }
        }
    }
}
