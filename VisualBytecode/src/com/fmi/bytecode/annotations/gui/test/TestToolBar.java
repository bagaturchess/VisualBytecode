package com.fmi.bytecode.annotations.gui.test;

import java.awt.Dimension;

import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JToolBar;

public class TestToolBar extends JFrame {
    private JToolBar jToolBar1 = new JToolBar();
    private JToolBar jToolBar2 = new JToolBar();

    public TestToolBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( null );
        this.setSize(new Dimension(400, 458));
        jToolBar1.setBounds(new Rectangle(0, 0, 225, 55));
        jToolBar2.setBounds(new Rectangle(0, 55, 230, 45));
        this.getContentPane().add(jToolBar2, null);
        this.getContentPane().add(jToolBar1, null);
    }
    
    public static void main(String[] args) {
        new TestToolBar().setVisible(true);
    }
}
