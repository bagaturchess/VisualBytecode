package com.fmi.bytecode.annotations.gui.graphical.mainframe;


//import client.themes.LookAndFeelManager;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;

import com.fmi.bytecode.annotations.gui.utils.FullScreenable;
import com.fmi.bytecode.annotations.gui.utils.GUIUtils;

public class AnnotationToolGUI {
    public AnnotationToolGUI() {
        MainFrame frame = new MainFrame();
        GUIUtils.fullScreenComponent(frame);
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        /*try {
           LookAndFeelManager.initLookAndFeel();
           UIManager.setLookAndFeel(Toolkit.getDefaultToolkit());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        new AnnotationToolGUI();
    }
}
