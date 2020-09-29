package com.fmi.bytecode.annotations.gui.utils;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.awt.Window;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class GUIUtils {
    public static void centerComponent(Container component) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = component.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        component.setLocation( ( screenSize.width - frameSize.width ) / 2,
                               ( screenSize.height - frameSize.height ) / 2 );
    }
    
   /* public static void fullScreenComponent(Container component) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //component.setLocation(screenSize.width,  screenSize.height);
        component.setBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));
    }
*/
   public static void fullScreenComponent(FullScreenable component) {
       Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       //component.setLocation(screenSize.width,  screenSize.height);
       component.set(new Rectangle(0, 0, screenSize.width, screenSize.height));
   }
}
