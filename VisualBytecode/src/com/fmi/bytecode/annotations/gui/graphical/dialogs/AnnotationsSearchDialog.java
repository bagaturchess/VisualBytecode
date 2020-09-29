package com.fmi.bytecode.annotations.gui.graphical.dialogs;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.AnnotationSearchActionListener;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.DialogsShortKeyListenener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;

import java.awt.Rectangle;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;
import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.AnnotationFilterModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.FilterLevelType;

import com.fmi.bytecode.annotations.gui.utils.GUIUtils;

public class AnnotationsSearchDialog extends JDialog {

    private static final String ALL = "ALL";
    public static final String ALL_PKG = "*";
    
    private JComboBox comboBoxPackages = new JComboBox();
    private JComboBox comboBoxAnnotations = new JComboBox();
    private JList listAnnotationFilters = new JList();
    private JScrollPane scrollPane = new JScrollPane();
    private JPanel panel = new JPanel();
    
    private JLabel labelProject = new JLabel();
    private JLabel labelContent = new JLabel();
    private JLabel labelPackage = new JLabel();
    
    private JTextField textFieldProject = new JTextField();
    private JTextField textFieldContent = new JTextField();
   
    private JCheckBox checkBoxMethod = new JCheckBox();
    private JCheckBox checkBoxClass = new JCheckBox();
    private JCheckBox checkBoxContructor = new JCheckBox();
    private JCheckBox checkBoxField = new JCheckBox();
    private JCheckBox checkBoxParameter = new JCheckBox();
   
    private JPanel panelSearchIn = new JPanel();
    private JPanel panelAnnotationFilter = new JPanel();
   
    private ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton radioButtonPositive = new JRadioButton();
    private JRadioButton radioButtonNegative = new JRadioButton();
    
    private JButton buttonRemove = new JButton();
    private JButton buttonAdd = new JButton();
    private JButton buttonSearch = new JButton();
    private JButton buttonCancel = new JButton();
    
    private AnnotationSearchActionListener annotationSearchActionListener;
    private ProjectModel projectModel;
    private File contentFile;
    private List<String> allPackages;
    private List<String> allAnnotations;
    private String packageName;
   
    private Vector<AnnotationFilterModel> addedAnnotationsFilters;
    private boolean enablePackageCombobox;
    
    public AnnotationsSearchDialog(Frame parent, ProjectModel projectModel, File content,
                                   String packageName, 
                                   List<String> allPackages, List<String> allAnnotations,
                                   boolean enablePackageCombobox) {
        this(parent, "", false);
        this.projectModel = projectModel;
        this.contentFile = content;
        this.packageName = packageName;
        this.allPackages = allPackages;
        this.allAnnotations = allAnnotations;
        this.enablePackageCombobox = enablePackageCombobox;
        if (enablePackageCombobox == false) {
            comboBoxPackages.setEnabled(false);
        }
        this.addedAnnotationsFilters = new Vector<AnnotationFilterModel>();
        fillElements();

        GUIUtils.centerComponent(this); 
        setVisible(true);
    }

    private AnnotationsSearchDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        try {
            annotationSearchActionListener = new AnnotationSearchActionListener(this);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        addKeyListener(new DialogsShortKeyListenener(this));
    }

    private void fillElements() {
        //fill projectName 
        String projectName = projectModel == null ? ALL : projectModel.getProjectName();
        textFieldProject.setText(projectName); 
        
        //fill projectContent
        String projectContent = contentFile == null ? ALL : contentFile.getAbsolutePath();
        textFieldContent.setText(projectContent); 
                 
        //fill packages combobox
        Vector<String> items =  new Vector<String>(allPackages);
        if (packageName.equals(ALL_PKG)) {
            items.add(0, ALL_PKG);
        }
        comboBoxPackages.setModel(new DefaultComboBoxModel(items));
        comboBoxPackages.setSelectedItem(packageName);

        //fill annotations combobox
        Vector<String> annotations = new Vector<String>(allAnnotations);
        annotations.add(0, ALL_PKG);
        comboBoxAnnotations.setModel(new DefaultComboBoxModel(annotations));
    }

    public void addCurrentFilter() throws ModelException {
        AnnotationFilterModel currentFilter = this.getCurrentFilter();
        if (!addedAnnotationsFilters.contains(currentFilter)) {
            addedAnnotationsFilters.add(currentFilter);
            listAnnotationFilters.setListData(addedAnnotationsFilters);
            listAnnotationFilters.setSelectedIndex(addedAnnotationsFilters.size() - 1);
        } else {
            int i = 0;
            for (; i < addedAnnotationsFilters.size(); i++) {
                if (currentFilter.equals(addedAnnotationsFilters.get(i))) {
                    break;
                }
            }
            listAnnotationFilters.setSelectedIndex(i);
            
            if (currentFilter.getAnnotationName() == null
                || addedAnnotationsFilters.get(i).getAnnotationName() == null) {
                throw new ModelException("If the filter contains asteriks (search for any annotations) " +
                                         "\r\nit is not permitted to have other filters.");                
            } else {            
                throw new ModelException("Annotation " + currentFilter.getAnnotationName() + 
                          " is already included.\r\nTo change its ptoperties " +
                          "remove it from the list and construct it again.");
            }
        }
    }
    
    private AnnotationFilterModel getCurrentFilter() throws ModelException {
        boolean isPositive = radioButtonPositive.isSelected();
        String annotationName = (String) comboBoxAnnotations.getSelectedItem();
        annotationName = annotationName.equals(ALL_PKG) ? null : annotationName;
        List<FilterLevelType> level = new ArrayList<FilterLevelType>();
        if (checkBoxClass.isSelected()) {
            level.add(FilterLevelType.CLASS);
        }
        if (checkBoxMethod.isSelected()) {
            level.add(FilterLevelType.METHOD);
        } 
        if (checkBoxField.isSelected()) {    
            level.add(FilterLevelType.FIELD);
        } 
        if (checkBoxParameter.isSelected()) {
            level.add(FilterLevelType.PARAMETER);
        }
        if (checkBoxContructor.isSelected()) {
            level.add(FilterLevelType.CONSTRUCTOR);
        }
        return new AnnotationFilterModel(isPositive, annotationName, level);
    }
    
    public void removeCurrentFilter() {
        int[] selectedIndxes      = listAnnotationFilters.getSelectedIndices();
        int countElementsToRemove = selectedIndxes.length;
        
        if (countElementsToRemove > 0) {        
            for (int i = countElementsToRemove - 1; i >= 0; i--) {
                addedAnnotationsFilters.remove(selectedIndxes[i]);
            }
            listAnnotationFilters.setListData(addedAnnotationsFilters);
            
            int maxRemovedElementIndx = selectedIndxes[countElementsToRemove - 1];
            int newElementIndx = maxRemovedElementIndx - countElementsToRemove + 1;
            //System.out.println(newElementIndx);
            int newVectorSize = addedAnnotationsFilters.size();    
            if (newVectorSize > 0 ) {
                if (newElementIndx >= newVectorSize) {
                    newElementIndx = newVectorSize - 1;
                }
                if (newElementIndx >= 0) {
                    listAnnotationFilters.setSelectedIndex(newElementIndx);
                } 
            }
        }           
    } 
    
    private void jbInit() throws Exception {
        this.setSize(new Dimension(609, 559));
        this.getContentPane().setLayout(null);
        this.setModal(true);
        this.setResizable(false);
        this.setTitle("Find Annotations");
        
        labelProject.setText("Project:");
        labelProject.setBounds(new Rectangle(25, 30, 50, 15));
        labelProject.setFont(new Font("Arial", 0, 11));
        labelContent.setText("Content:");
        labelContent.setBounds(new Rectangle(25, 60, 50, 15));
        labelContent.setFont(new Font("Arial", 0, 11));
        labelPackage.setText("Package:");
        labelPackage.setBounds(new Rectangle(25, 87, 50, 15));
        labelPackage.setFont(new Font("Arial", 0, 11));
        
        textFieldProject.setBounds(new Rectangle(85, 30, 445, 20));
        textFieldProject.setEnabled(false);
        textFieldProject.setEditable(false);
        textFieldProject.setSelectionColor(new Color(49, 106, 96));
        textFieldContent.setBounds(new Rectangle(85, 55, 445, 20));
        textFieldContent.setEditable(false);
        textFieldContent.setEnabled(false);
        textFieldContent.setSelectionColor(new Color(49, 106, 96));
        
        comboBoxPackages.setBounds(new Rectangle(85, 85, 445, 20));
        
        panelSearchIn.setBounds(new Rectangle(25, 10, 555, 125));
        panelSearchIn.setBorder(BorderFactory.createTitledBorder("Search In"));
        panelSearchIn.setLayout(null);


        panelAnnotationFilter.setBounds(new Rectangle(25, 145, 555, 115));
        panelAnnotationFilter.setLayout(null);
        panelAnnotationFilter.setBorder(BorderFactory.createTitledBorder("Create Annotation Filter"));      
        
        checkBoxMethod.setText("Method (M)");
        checkBoxMethod.setBounds(new Rectangle(420, 35, 90, 20));
        checkBoxMethod.setSelected(true);
        checkBoxMethod.setFont(new Font("Arial", 0, 11));
        
        checkBoxClass.setText("Class (C)");
        checkBoxClass.setBounds(new Rectangle(420, 15, 85, 20));
        checkBoxClass.setSelected(true);
        checkBoxClass.setFont(new Font("Arial", 0, 11));
        
        checkBoxContructor.setText("Constructor (I)");
        checkBoxContructor.setBounds(new Rectangle(420, 70, 120, 20));
        checkBoxContructor.setSelected(true);
        checkBoxContructor.setFont(new Font("Arial", 0, 11));
        
        checkBoxField.setText("Field (F)");
        checkBoxField.setBounds(new Rectangle(420, 55, 90, 15));
        checkBoxField.setSelected(true);
        checkBoxField.setFont(new Font("Arial", 0, 11));
        
        checkBoxParameter.setText("Parameter (P)");
        checkBoxParameter.setBounds(new Rectangle(420, 90, 105, 20));
        checkBoxParameter.setSelected(true);
        checkBoxParameter.setFont(new Font("Arial", 0, 11));
        
        comboBoxAnnotations.setBounds(new Rectangle(30, 75, 365, 20));

        comboBoxAnnotations.setLightWeightPopupEnabled(false);
        radioButtonPositive.setText("Positive (+)");
        radioButtonPositive.setBounds(new Rectangle(30, 30, 95, 20));
        radioButtonPositive.setSelected(true);
        radioButtonPositive.setFont(new Font("Arial", 0, 11));
        
        radioButtonNegative.setText("Negative (-)");
        radioButtonNegative.setBounds(new Rectangle(30, 50, 100, 20));
        radioButtonNegative.setFont(new Font("Arial", 0, 11));

        buttonRemove.setText("Remove From Filter");
        buttonRemove.setBounds(new Rectangle(325, 275, 145, 25));
        buttonRemove.setActionCommand("Remove");
        buttonRemove.addActionListener(annotationSearchActionListener);
        
        buttonAdd.setText("Add To Filter");
        buttonAdd.setBounds(new Rectangle(130, 275, 145, 25));
        buttonAdd.setActionCommand("Add");
        buttonAdd.addActionListener(annotationSearchActionListener);
        
        buttonSearch.setText("Search");
        buttonSearch.setBounds(new Rectangle(195, 490, 75, 25));
        buttonSearch.setActionCommand("Search");
        buttonSearch.addActionListener(annotationSearchActionListener);
        
        buttonCancel.setText("Cancel");
        buttonCancel.setBounds(new Rectangle(305, 490, 85, 25));
        buttonCancel.setActionCommand("Cancel");
        buttonCancel.addActionListener(annotationSearchActionListener);

        panel.setBounds(new Rectangle(50, 295, 10, 10));
        scrollPane.setBounds(new Rectangle(25, 320, 555, 130));
        buttonGroup.add(radioButtonPositive);
        buttonGroup.add(radioButtonNegative);

        scrollPane.getViewport().add(listAnnotationFilters, null);

        panelAnnotationFilter.add(radioButtonNegative, null);
        panelAnnotationFilter.add(radioButtonPositive, null);
        panelAnnotationFilter.add(comboBoxAnnotations, null);
        panelAnnotationFilter.add(checkBoxField, null);
        panelAnnotationFilter.add(checkBoxParameter, null);
        panelAnnotationFilter.add(checkBoxContructor, null);
        panelAnnotationFilter.add(checkBoxMethod, null);
        panelAnnotationFilter.add(checkBoxClass, null);
        this.getContentPane().add(panel, null);
        this.getContentPane().add(panelSearchIn, null);
        this.getContentPane().add(panelAnnotationFilter, null);
        this.getContentPane().add(scrollPane, null);
        this.getContentPane().add(buttonCancel, null);
        this.getContentPane().add(buttonSearch, null);
        this.getContentPane().add(buttonAdd, null);
        this.getContentPane().add(buttonRemove, null);
        panelSearchIn.add(textFieldContent, null);
        panelSearchIn.add(textFieldProject, null);
        panelSearchIn.add(labelContent, null);
        panelSearchIn.add(labelProject, null);
        panelSearchIn.add(labelPackage, null);
        panelSearchIn.add(comboBoxPackages, null);
    }

    public File getContentFile() {
        return contentFile;
    }

    public List<AnnotationFilterModel> getAddedAnnotationsFilters() {
        return addedAnnotationsFilters;
    }

    public String getPackageName() {
        String selectedPackage = comboBoxPackages.getSelectedItem().toString();
        return selectedPackage.equals(ALL_PKG) ? null : selectedPackage;
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }
}
