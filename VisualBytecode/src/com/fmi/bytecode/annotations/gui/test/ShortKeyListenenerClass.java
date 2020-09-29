package com.fmi.bytecode.annotations.gui.test;

import java.awt.Event;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class ShortKeyListenenerClass implements KeyListener {
    private Window frame;
    
    public ShortKeyListenenerClass(Window frame) {
        this.frame = frame;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {}
}
