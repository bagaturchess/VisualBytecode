package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.DialogsShortKeyListenener;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.NewProjectActionListener;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.SelectedFileValueFieldApplier;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.SelectedFileValueListApplier;

import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.io.File;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.utils.GUIUtils;

public class NewProjectDialog extends JDialog {
    private JLabel labelProjectName = new JLabel();
    private JTextField textFieldProjectName = new JTextField();
    private JTextField textFieldProjectDir = new JTextField();
    private JLabel labelProjectDir = new JLabel();
    private JButton browseButton = new JButton();
    private Vector<File> contentItemsList = new Vector<File>();
    private JList listIncludedContent = new JList(contentItemsList);
    private JLabel labelIncludedContent = new JLabel();
    private JButton buttonAdd = new JButton();
    private JButton buttonRemove = new JButton();
    private JButton buttonApply = new JButton();
    private JButton buttonCancel = new JButton();
    private NewProjectActionListener projectActionListener;
    private JScrollPane scrollPane1 = new JScrollPane();
    //private JLabel jLabel1 = new JLabel();
    private ProjectModel projectModel;

    public NewProjectDialog(Frame parent, String title) {
        this(parent, title, false);
        setVisible(true);
    }

    public NewProjectDialog(Frame parent, ProjectModel prjModel, String title) {
        this(parent, title, false);
        projectModel = prjModel;
        getTextFieldProjectName().setText(prjModel.getProjectName());
        getTextFieldProjectDir().setText(prjModel.getProjectDir());
        contentItemsList.addAll(prjModel.getProjectContent());
        getListIncludedContent().setListData(contentItemsList);
        getListIncludedContent().setSelectedIndex(0); 
        setVisible(true);      
    }
    
    private NewProjectDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            projectActionListener = new NewProjectActionListener(
                    new SelectedFileValueFieldApplier(textFieldProjectDir),//set chosen dir as value of field
                    new SelectedFileValueListApplier(listIncludedContent, 
                                                     contentItemsList),
                    this, listIncludedContent, contentItemsList);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        addKeyListener(new DialogsShortKeyListenener(this));

        GUIUtils.centerComponent(this);
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(740, 476));
        this.getContentPane().setLayout( null );
        //this.setTitle("New Project");
        this.setModal(true);
        this.setResizable(false);
        labelProjectName.setText("Project Name");
        labelProjectName.setBounds(new Rectangle(30, 20, 120, 15));
        labelProjectName.setToolTipText("Enter Project Name");
        labelProjectName.setHorizontalAlignment(SwingConstants.LEFT);
        textFieldProjectName.setText(ProjectModel.DEFAULT_PROJECT_NAME);
        
        //jTextFieldProjectName.addKeyListener(new DialogsShortKeyListenener(this));
        
        textFieldProjectName.setBounds(new Rectangle(30, 40, 680, 20));
        textFieldProjectDir.setBounds(new Rectangle(30, 110, 580, 20));
        labelProjectDir.setText("Project Directory");
        labelProjectDir.setBounds(new Rectangle(30, 90, 120, 15));
        labelProjectDir.setToolTipText("Choose Project Directory");
        browseButton.setText("Browse");
        browseButton.setBounds(new Rectangle(630, 108, 80, 25));
        browseButton.addActionListener(projectActionListener);
        labelIncludedContent.setText("Included Content (dir/ files/ archives)");
        labelIncludedContent.setBounds(new Rectangle(30, 175, 400, 15));
        buttonAdd.setText("Add");
        buttonAdd.setBounds(new Rectangle(630, 215, 80, 25));
        buttonAdd.addActionListener(projectActionListener);
        buttonAdd.setToolTipText("Add Content");
        buttonRemove.setText("Remove");
        buttonRemove.setBounds(new Rectangle(630, 275, 80, 25));
        buttonRemove.addActionListener(projectActionListener);
        buttonRemove.setToolTipText("Remove Content");
        buttonApply.setText("Apply");
        buttonApply.setBounds(new Rectangle(235, 395, 80, 25));
        buttonApply.addActionListener(projectActionListener);
        buttonApply.setToolTipText("Apply Changes");
        buttonCancel.setText("Cancel");
        buttonCancel.setBounds(new Rectangle(370, 395, 80, 25));
        buttonCancel.addActionListener(projectActionListener);
        buttonCancel.setToolTipText("Cancel");
        scrollPane1.setBounds(new Rectangle(30, 195, 580, 150));
        //jLabel1.setText("Enter the name and location for the new project and specify the project content to use");
        //jLabel1.setBounds(new Rectangle(30, 5, 695, 15));
        scrollPane1.getViewport().add(listIncludedContent, null);
        //this.getContentPane().add(jLabel1, null);
        this.getContentPane().add(scrollPane1, null);
        this.getContentPane().add(buttonCancel, null);
        this.getContentPane().add(buttonApply, null);
        this.getContentPane().add(buttonRemove, null);
        this.getContentPane().add(buttonAdd, null);
        this.getContentPane().add(labelIncludedContent, null);
        this.getContentPane().add(browseButton, null);
        this.getContentPane().add(labelProjectDir, null);
        this.getContentPane().add(textFieldProjectDir, null);
        this.getContentPane().add(textFieldProjectName, null);
        this.getContentPane().add(labelProjectName, null);
    }

    public JTextField getTextFieldProjectName() {
        return textFieldProjectName;
    }

   
    public JTextField getTextFieldProjectDir() {
        return textFieldProjectDir;
    }

    public void setContentItemsList(Vector<File> contentItemsList) {
        this.contentItemsList = contentItemsList;
    }


    public JList getListIncludedContent() {
        return listIncludedContent;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }

    public void setProjectModel(ProjectModel _projectModel) {
        projectModel = _projectModel;
    }
}
