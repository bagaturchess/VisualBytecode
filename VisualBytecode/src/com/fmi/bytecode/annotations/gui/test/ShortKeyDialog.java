package com.fmi.bytecode.annotations.gui.test;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.Frame;

import java.awt.MenuShortcut;
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;

import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class ShortKeyDialog extends JDialog {
    private JMenuBar jMenuBar1 = new JMenuBar();
    private JMenu jMenu1 = new JMenu();
    private JMenuItem jMenuItem1 = new JMenuItem();
    private JMenuItem jMenuItem2 = new JMenuItem();
    private JMenuItem jMenuItem3 = new JMenuItem();

    public ShortKeyDialog() {
        this(null, "", false);
    }

    public ShortKeyDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            jbInit();
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(458, 489));
        this.getContentPane().setLayout( null );
        this.setJMenuBar(jMenuBar1);
        jMenu1.setText("File");
        jMenuItem1.setText("New");
        jMenuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.SHIFT_MASK | Event.CTRL_MASK, false));
        jMenuItem2.setText("Open");
        jMenuItem2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, Event.CTRL_MASK, false));
        jMenuItem3.setText("Exit");
        jMenuItem3.setMnemonic('F');
        jMenuItem3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, false));
        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenuBar1.add(jMenu1);
        
        addKeyListener(new ShortKeyListenenerClass(this));
    }
    public static void main(String[] args) {
        new ShortKeyDialog();
    }

}
