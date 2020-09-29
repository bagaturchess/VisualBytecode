package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.DialogsShortKeyListenener;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;

import java.awt.Rectangle;

import javax.swing.JDialog;

import com.fmi.bytecode.annotations.gui.utils.GUIUtils;

import com.fmi.bytecode.annotations.gui.utils.ResourceUtils;

import java.awt.BorderLayout;

import java.io.IOException;

import java.io.InputStream;

import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLDocument;


public class HelpDialog extends JDialog {
    
    private static final String HELP_HTML_FILE = "/com/fmi/bytecode/annotations/gui/graphical/dialogs/help/UserGuide.htm";
    
    private JScrollPane jScrollPane1 = new JScrollPane();
    private JEditorPane jEditorPane1;

    public HelpDialog(Frame parent) {
        super(parent, "Help", true);
        
        URL helpURL = ResourceUtils.getResourceURL(HELP_HTML_FILE);
        try {
            jEditorPane1 = new JEditorPane(helpURL);
            jEditorPane1.setEditable(false);
            
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        GUIUtils.centerComponent(this);
        addKeyListener(new DialogsShortKeyListenener(this));
        setVisible(true);
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(730, 600));
        jScrollPane1.getViewport().add(jEditorPane1, null);
        this.getContentPane().add(jScrollPane1);
    }
}