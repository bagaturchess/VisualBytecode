package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.DialogsShortKeyListenener;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.ExportToXMLActionListener;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.utils.ExportUtils;
import com.fmi.bytecode.annotations.gui.utils.GUIUtils;

public class ExportToXMLDialog extends JDialog {
    private JTextField fileNameTextField = new JTextField();
    private JTextField fileDirTextField = new JTextField();
    private JTextField schemaDirTextField = new JTextField();
    
    private JButton buttonBrowseDir = new JButton();
    private JButton buttonBrowseScheme = new JButton();
    private JButton buttonSave = new JButton();
    private JButton buttonCancel = new JButton();

    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton defaultSchemeRadioButton = new JRadioButton();
    private JRadioButton customSchemeRadioButton = new JRadioButton();   
   
    private JLabel labelFileName = new JLabel();
    private JLabel labelFileDir = new JLabel();
    private JLabel labelSchemeLoc = new JLabel();

    private List<SaveUnit> units;
    private ExportToXMLActionListener exportActionListener;
    
    public ExportToXMLDialog(Frame parent, List<SaveUnit> units) {
        this(parent, "Export to XML", true, units);
    }

    private ExportToXMLDialog(Frame parent, String title, boolean modal, 
                              List<SaveUnit> units) {
        super(parent, title, modal);
        this.units = units;
        exportActionListener = new ExportToXMLActionListener(this);
        
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        fileNameTextField.setText(ExportUtils.DEFAULT_XML_FILE_NAME);
        fileDirTextField.setText(ExportUtils.DEFAULT_XML_DEFAULT_DIR);
        enableCustomScheme(false);
        addKeyListener(new DialogsShortKeyListenener(this));
        GUIUtils.centerComponent(this);
        setVisible(true);
    }

    public void enableCustomScheme(boolean enable) {
        if (!enable) {
            schemaDirTextField.setText(ExportUtils.DEFAULT_XML_SCHEME); 
        } else {
            schemaDirTextField.setText("");
        }
        schemaDirTextField.setEnabled(enable);
        schemaDirTextField.setEditable(enable);
        buttonBrowseScheme.setEnabled(enable);
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(740, 383));
        this.getContentPane().setLayout( null );
                
        fileNameTextField.setBounds(new Rectangle(30, 50, 680, 20));
        fileDirTextField.setBounds(new Rectangle(30, 107, 580, 20));
        
        buttonBrowseDir.setText("Browse");
        buttonBrowseDir.setBounds(new Rectangle(630, 105, 80, 25));
        buttonBrowseDir.addActionListener(exportActionListener);
        buttonBrowseDir.setActionCommand("BrowseDir");
        
        defaultSchemeRadioButton.setText("Default Schema");
        defaultSchemeRadioButton.setBounds(new Rectangle(30, 155, 250, 25));
        defaultSchemeRadioButton.setSelected(true);
        defaultSchemeRadioButton.setActionCommand("DisableCustom");      
        defaultSchemeRadioButton.addActionListener(exportActionListener);
        
        customSchemeRadioButton.setText("Custom XSL Transformation");
        customSchemeRadioButton.setBounds(new Rectangle(30, 175, 210, 25));
        customSchemeRadioButton.setActionCommand("EnableCustom");
        customSchemeRadioButton.addActionListener(exportActionListener);
        
        labelFileName.setText("XML File Name");
        labelFileName.setBounds(new Rectangle(30, 30, 335, 15));
        labelFileDir.setText("XML File Directory");
        labelFileDir.setBounds(new Rectangle(30, 90, 315, 15));
        labelSchemeLoc.setText("Default Schema or Custom XSL Location");
        labelSchemeLoc.setBounds(new Rectangle(30, 215, 375, 15));
        
        schemaDirTextField.setBounds(new Rectangle(30, 230, 580, 20));
        
        buttonBrowseScheme.setText("Browse");
        buttonBrowseScheme.setBounds(new Rectangle(630, 228, 80, 25));
        buttonBrowseScheme.addActionListener(exportActionListener);
        buttonBrowseScheme.setActionCommand("BrowseSchema");
        
        buttonSave.setText("Export");
        buttonSave.setBounds(new Rectangle(210, 310, 80, 25));
        buttonSave.setActionCommand("Export");
        buttonSave.addActionListener(exportActionListener);
        
        buttonCancel.setText("Cancel");
        buttonCancel.setBounds(new Rectangle(375, 310, 80, 25));
        buttonCancel.setActionCommand("Cancel");
        buttonCancel.addActionListener(exportActionListener);
        
        buttonGroup.add(defaultSchemeRadioButton);
        buttonGroup.add(customSchemeRadioButton);

        this.getContentPane().add(labelSchemeLoc, null);
        this.getContentPane().add(labelFileDir, null);
        this.getContentPane().add(labelFileName, null);
        this.getContentPane().add(buttonCancel, null);
        this.getContentPane().add(buttonSave, null);
        this.getContentPane().add(buttonBrowseScheme, null);
        this.getContentPane().add(schemaDirTextField, null);
        this.getContentPane().add(customSchemeRadioButton, null);
        this.getContentPane().add(defaultSchemeRadioButton, null);
        this.getContentPane().add(buttonBrowseDir, null);
        this.getContentPane().add(fileDirTextField, null);
        this.getContentPane().add(fileNameTextField, null);
    }
    
    public JTextField getFileDirTextField() {
        return fileDirTextField;
    }

    public JTextField getSchemaDirTextField() {
        return schemaDirTextField;
    }

    public List<SaveUnit> getUnits() {
        return units;
    }

    public JTextField getFileNameTextField() {
        return fileNameTextField;
    }
}
