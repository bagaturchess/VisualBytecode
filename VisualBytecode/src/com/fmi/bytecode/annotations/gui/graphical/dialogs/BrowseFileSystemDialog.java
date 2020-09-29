package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.BrowseFileSystemActionListener;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.AMPFileFilter;
import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.SelectedFileValueApplier;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.Rectangle;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFileChooser;

import javax.swing.filechooser.FileFilter;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.utils.GUIUtils;

public class BrowseFileSystemDialog extends JDialog {

    public static final int FILES_ONLY = JFileChooser.FILES_ONLY;
    public static final int DIRECTORIES_ONLY = JFileChooser.DIRECTORIES_ONLY;
    public static final int FILES_AND_DIRECTORIES = JFileChooser.FILES_AND_DIRECTORIES;
    
    private static final AMPFileFilter FILTER_JAR = new AMPFileFilter(".jar", "Jar File Containg Annotated Classes (*.jar)");
    private static final AMPFileFilter FILTER_WAR = new AMPFileFilter(".war", "War File Containg Annotated Classes (*.war)");
    private static final AMPFileFilter FILTER_CLASS = new AMPFileFilter(".class", "Class File Containg Annotations (*.class)");
    private static final AMPFileFilter FILTER_PROJECT = new AMPFileFilter(ProjectModel.FILE_EXTENSION, "Annotation Tool Project File (*" +ProjectModel.FILE_EXTENSION + ")");
    private static final AMPFileFilter FILTER_XML = new AMPFileFilter(".xml", "XML file for expoting meta information (*.xml)");
    private static final AMPFileFilter FILTER_XSL = new AMPFileFilter(".xsl", "XSL file for custom transformation (*.xsl)");
    
    public static final List<FileFilter> FILTERS_PROJECT = new ArrayList<FileFilter>();
    public static final List<FileFilter> FILTERS_CLASS_WAR_JAR = new ArrayList<FileFilter>();
    public static final List<FileFilter> FILTERS_NONE = new ArrayList<FileFilter>();
    public static final List<FileFilter> FILTERS_XML = new ArrayList<FileFilter>();
    public static final List<FileFilter> FILTERS_XSL = new ArrayList<FileFilter>();
    
    static {
        FILTERS_PROJECT.add(FILTER_PROJECT);
        FILTERS_CLASS_WAR_JAR.add(FILTER_CLASS);
        FILTERS_CLASS_WAR_JAR.add(FILTER_WAR);
        FILTERS_CLASS_WAR_JAR.add(FILTER_JAR);
        FILTERS_XML.add(FILTER_XML);
        FILTERS_XSL.add(FILTER_XSL);
    }
    
    private JFileChooser fileChooserFileSystem = new JFileChooser();
    private int selectionMode;
    private SelectedFileValueApplier componentApplier; //projectDirFieldApplier;
    
    public BrowseFileSystemDialog(Frame parent, int selectionMode, 
                                  SelectedFileValueApplier componentApplier,
                                  List<FileFilter> filters) {
        this(parent, "", false, selectionMode, componentApplier, filters);
        this.setVisible(true);
    }

    public BrowseFileSystemDialog(Dialog parent, int selectionMode, 
                                  SelectedFileValueApplier componentApplier,
                                  List<FileFilter> filters) {
        this(parent, "", false, selectionMode, componentApplier, filters);
        this.setVisible(true);
    }
    
    private BrowseFileSystemDialog(Frame parent, String title, boolean modal, 
                                  int selectionMode, 
                                  SelectedFileValueApplier componentApplier,
                                  List<FileFilter> filters) {
        super(parent, title, modal);
        init(selectionMode, componentApplier, filters);
    }

    private BrowseFileSystemDialog(Dialog parent, String title, boolean modal, 
                                  int selectionMode, 
                                  SelectedFileValueApplier componentApplier,
                                  List<FileFilter> filters) {
        super(parent, title, modal);
        init(selectionMode, componentApplier, filters);
    }
    
    private void init (int selectionMode, SelectedFileValueApplier componentApplier,
                      List<FileFilter> filters) {
        this.selectionMode = selectionMode;
        this.componentApplier = componentApplier;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (FileFilter filter : filters) {
            fileChooserFileSystem.addChoosableFileFilter(filter);
        }
        GUIUtils.centerComponent(this); 
    }
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(581, 385));
        this.getContentPane().setLayout( null );
        this.setTitle("Browse");
        this.setModal(true);
        this.setResizable(false);
        fileChooserFileSystem.setBounds(new Rectangle(35, 15, 500, 326));
        this.getContentPane().add(fileChooserFileSystem, null);
        fileChooserFileSystem.setFileSelectionMode(selectionMode);
        fileChooserFileSystem.addActionListener(
            new BrowseFileSystemActionListener(this, fileChooserFileSystem, 
                                               componentApplier));
    }
    
}
