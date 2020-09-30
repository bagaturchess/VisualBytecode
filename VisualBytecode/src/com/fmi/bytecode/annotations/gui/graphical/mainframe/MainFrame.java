package com.fmi.bytecode.annotations.gui.graphical.mainframe;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.MainFrameActionListener;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.project.ProjectTreeMouseListener;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.trees.searchresult.SearchResultTreeMouseListener;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.RightClickGlassPane;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.TreeModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ModelsRepository;

import java.awt.Color;

import java.awt.Font;
import java.util.List;

import javax.swing.tree.TreePath;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;


import com.fmi.bytecode.annotations.gui.graphical.dialogs.ClosableTabbedPaneWithPopup;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.TreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview.ClassViewNode;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview.ColourTreeCellRenderer;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;


import com.fmi.bytecode.annotations.gui.utils.FullScreenable;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;

public class MainFrame extends JFrame implements FullScreenable {
    private static int searchViewSeqNumber = 1; //search view tab number
    private static boolean isFirstSearchResultTreeAdded = false;
    private static boolean isFirstClassViewTreeAdded = false;
    
    private BorderLayout layoutMain = new BorderLayout();
    private JPanel panelCenter = new JPanel();
    private JLabel statusBar = new JLabel();
    
    private JToolBar toolBarProject = new JToolBar();
    private JButton buttonNewProject = new JButton();
    private JButton buttonCloseProject = new JButton();
    private JButton buttonOpenProject = new JButton();
    private JButton buttonProjectProperties = new JButton();
    private JButton buttonSave = new JButton();
    private JButton buttonHelp = new JButton();
    private JButton buttonFindAnnotations = new JButton();
    private JButton buttonExportXML = new JButton();
    
    private JToolBar toolBarFile = new JToolBar();
    
    private JMenuBar menuBar = new JMenuBar();
    private JMenu menuFile = new JMenu(); 
    private JMenuItem menuItemSave = new JMenuItem();
    private JMenuItem menuItemNewProject = new JMenuItem();
    private JMenuItem menuItemOpenProject = new JMenuItem();
    private JMenuItem menuItemCloseProject = new JMenuItem();
    private JMenuItem menuItemCloseAllProjects = new JMenuItem();
    private JMenuItem menuItemFileExit = new JMenuItem();
    
    private JMenu menuTools = new JMenu(); 
    private JMenuItem menuItemFindAnnotation = new JMenuItem();
    private JMenuItem menuItemExport = new JMenuItem();
    
    private JMenu menuView = new JMenu(); 
    private JMenuItem menuItemProjectProperties = new JMenuItem();
    
    private JMenu menuHelp = new JMenu();
    private JMenuItem menuItemHelpAbout = new JMenuItem();
    private JMenuItem menuItemHelp = new JMenuItem();
    
    private ProjectTreeMouseListener projectTreeMouseListener;
    //TODO: out private SearchResultTreeMouseListener searchResultTreeMouseListener;
    
    private JSplitPane mainSplitPane = new JSplitPane();
    private JScrollPane projectTreeScrollPane = new JScrollPane();
    private JTree projectTree = new JTree();
    private JTabbedPane tabbedPaneProjects = new JTabbedPane();
    
    private JPanel panelProjects = new JPanel();
    
    private JSplitPane searchAnnotResultSplitPane = new JSplitPane();
    //private JScrollPane searchAnnotResultScrollPane = new JScrollPane();
   
    //TODO: out private JScrollPane showAnnotResultScrollPane = new JScrollPane();
    //TODO: out private JTree showAnnotResultTree = new JTree(new TreeModelImpl(null));
    private ClosableTabbedPaneWithPopup tabbedPaneShowAnnotResult = new ClosableTabbedPaneWithPopup(GUIComponentsRepository.CLOSE_TAB);
    
    //private JTree searchAnnotResultTree = new JTree(new TreeModelImpl(null));
    //private JTabbedPane tabbedPaneSearch = new JTabbedPane();
    
    //private ClosableTabbedPane tabbedPaneSearch = new ClosableTabbedPane(GUIComponentsRepository.CLOSE_TAB);
    private ClosableTabbedPaneWithPopup tabbedPaneSearch = new ClosableTabbedPaneWithPopup(GUIComponentsRepository.CLOSE_TAB);
    
    public RightClickGlassPane projectTreeRightClickGlassPane = new RightClickGlassPane(projectTree);
    private JRootPane projectTreeRootPane = new JRootPane();
    
    private MainFrameActionListener mainFrameActionListener;
    private JLabel labelSearchView = new JLabel();
    private JLabel labelClassView = new JLabel();
    
    private JButton labelProjects = new JButton();
    
    public MainFrame() {
        GUIComponentsRepository.getComponentsRepository().setMainFrame(this);
        initRightClickGlassPane();
        try {
            mainFrameActionListener = new MainFrameActionListener(this);
            projectTreeMouseListener = new ProjectTreeMouseListener(projectTree);
            jbInit();
            projectTree.setModel(ModelsRepository.getModelsRepository().getTreeModelProjectsRoot());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRightClickGlassPane() {
        // set as glasspane and make it not visible
        projectTreeRootPane.setGlassPane(projectTreeRightClickGlassPane);
        projectTreeRightClickGlassPane.setVisible(false);
    }

    private void jbInit() throws Exception {
        this.getContentPane().setLayout( layoutMain );
        panelCenter.setLayout(null);
        this.setTitle( "Annotation Tool" );
        this.setResizable(false);
        statusBar.setText("");
        
        this.setJMenuBar(menuBar);
        menuFile.setText("File");
        menuItemNewProject.setText(" New Project");
        menuItemNewProject.setIcon(GUIComponentsRepository.IMAGE_NEW);
        menuItemNewProject.setActionCommand("NewProject");
        menuItemNewProject.addActionListener(mainFrameActionListener);
        menuItemNewProject.setAccelerator(GUIComponentsRepository.SK_CTRL_N);
        
        menuItemOpenProject.setText(" Open Project");
        menuItemOpenProject.setIcon(GUIComponentsRepository.IMAGE_OPEN);
        menuItemOpenProject.setActionCommand("OpenProject");
        menuItemOpenProject.addActionListener(mainFrameActionListener);
        menuItemOpenProject.setAccelerator(GUIComponentsRepository.SK_CTRL_O);  
          
        menuItemCloseProject.setText(" Close Project");
        menuItemCloseProject.setIcon(GUIComponentsRepository.REMOVE_FILE);
        menuItemCloseProject.setActionCommand("CloseProject");
        menuItemCloseProject.addActionListener(mainFrameActionListener);
        menuItemCloseProject.setAccelerator(GUIComponentsRepository.SK_CTRL_F4);
        
        menuItemCloseAllProjects.setText("  Close All");
        menuItemCloseAllProjects.setIcon(GUIComponentsRepository.CLOSE_ALL);
        menuItemCloseAllProjects.setActionCommand("CloseAll");
        menuItemCloseAllProjects.addActionListener(mainFrameActionListener);
        menuItemCloseAllProjects.setAccelerator(GUIComponentsRepository.SK_CTRL_SHIFT_F4);
        
        menuItemFileExit.setText(" Exit");
        menuItemFileExit.addActionListener(mainFrameActionListener);
        menuItemFileExit.setActionCommand("Exit");
        menuItemFileExit.setIcon(GUIComponentsRepository.IMAGE_EXIT);
        menuItemFileExit.setAccelerator(GUIComponentsRepository.SK_ALT_F4);
        
        menuItemSave.setText(" Save");
        menuItemSave.setIcon(GUIComponentsRepository.IMAGE_SAVE);
        menuItemSave.setAccelerator(GUIComponentsRepository.SK_CTRL_S);
        
        menuTools.setText("Tools");
        menuItemFindAnnotation.setText(" Find Annotations");
        menuItemFindAnnotation.addActionListener(mainFrameActionListener);
        menuItemFindAnnotation.setActionCommand("AnnotationSearch");
        menuItemFindAnnotation.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        menuItemFindAnnotation.setAccelerator(GUIComponentsRepository.SK_CTRL_F);
        
        menuItemExport.setText(" Export to XML");
        menuItemExport.addActionListener(mainFrameActionListener);
        menuItemExport.setActionCommand("Export");
        menuItemExport.setIcon(GUIComponentsRepository.XML_EXPORT);
        menuItemExport.setAccelerator(GUIComponentsRepository.SK_CTRL_E);
        
        menuView.setText("View");
        menuItemProjectProperties.setText(" Project Properties");
        menuItemProjectProperties.addActionListener(mainFrameActionListener);
        menuItemProjectProperties.setActionCommand("ProjectProps");
        menuItemProjectProperties.setIcon(GUIComponentsRepository.PROJECT_PROPS);
        menuItemProjectProperties.setAccelerator(GUIComponentsRepository.SK_CTRL_P);
        
        menuHelp.setText("Help"); 
        menuItemHelp.setText("Help");
        menuItemHelp.setActionCommand("Help");
        menuItemHelp.setIcon(GUIComponentsRepository.IMAGE_HELP);
        menuItemHelp.addActionListener(mainFrameActionListener);
        menuItemHelp.setAccelerator(GUIComponentsRepository.SK_F1);
        menuItemHelpAbout.setText("About");
        menuItemHelpAbout.setActionCommand("About");
        menuItemHelpAbout.setIcon(GUIComponentsRepository.IMAGE_ABOUT);
        menuItemHelpAbout.addActionListener(mainFrameActionListener);
        
        buttonNewProject.setToolTipText("New Project");
        buttonNewProject.setActionCommand("NewProject");
        buttonNewProject.addActionListener(mainFrameActionListener);
        buttonNewProject.setIcon(GUIComponentsRepository.IMAGE_NEW);
        
        buttonCloseProject.setToolTipText("Close Project");
        buttonCloseProject.setActionCommand("CloseProject");
        buttonCloseProject.addActionListener(mainFrameActionListener);
        buttonCloseProject.setIcon(GUIComponentsRepository.CLOSE_MENU);
            
        buttonOpenProject.setToolTipText("Open Project");
        buttonOpenProject.setActionCommand("OpenProject");
        buttonOpenProject.addActionListener(mainFrameActionListener);
        buttonOpenProject.setIcon(GUIComponentsRepository.IMAGE_OPEN);
        
        buttonProjectProperties.setToolTipText("Project Properties");
        buttonProjectProperties.setActionCommand("ProjectProps");
        buttonProjectProperties.addActionListener(mainFrameActionListener);
        buttonProjectProperties.setIcon(GUIComponentsRepository.PROJECT_PROPS);
        
        buttonFindAnnotations.setToolTipText("Find Annotations");
        buttonFindAnnotations.setIcon(GUIComponentsRepository.FIND_ANNOTATIONS);
        buttonFindAnnotations.addActionListener(mainFrameActionListener);  
        buttonFindAnnotations.setActionCommand("AnnotationSearch");
        
        buttonExportXML.setToolTipText("Export to XML");
        buttonExportXML.setIcon(GUIComponentsRepository.XML_EXPORT);
        buttonExportXML.addActionListener(mainFrameActionListener);  
        buttonExportXML.setActionCommand("Export");
        
        buttonSave.setToolTipText("Save");
        buttonSave.setIcon(GUIComponentsRepository.IMAGE_SAVE);
        
        buttonHelp.setToolTipText("Help");
        buttonHelp.setIcon(GUIComponentsRepository.IMAGE_HELP);
        buttonHelp.addActionListener(mainFrameActionListener);
        buttonHelp.setActionCommand("Help");
                        
        mainSplitPane.setBounds(new Rectangle(0, 0, 795, 525));
        mainSplitPane.setDividerSize(4);
        mainSplitPane.setDividerLocation(300);

        searchAnnotResultSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        searchAnnotResultSplitPane.setDividerSize(4);
        searchAnnotResultSplitPane.setDividerLocation(400);

        BorderLayout prjLayout = new BorderLayout();
        panelProjects.setLayout(prjLayout);
        
        panelProjects.add(toolBarProject, BorderLayout.PAGE_START);
        panelProjects.add(projectTreeRootPane, BorderLayout.CENTER);
        panelProjects.add(labelProjects, BorderLayout.PAGE_END);
        
        labelSearchView.setText("Annotations search results will be displayed here");
        labelSearchView.setHorizontalAlignment(SwingConstants.CENTER);
        labelSearchView.setHorizontalTextPosition(SwingConstants.CENTER);
        
        labelClassView.setText("Class meta information will be displayed here");
        labelClassView.setHorizontalAlignment(SwingConstants.CENTER);
        labelClassView.setHorizontalTextPosition(SwingConstants.CENTER);
        
        menuFile.add(menuItemNewProject);
        //menuFile.addSeparator();
        menuFile.add(menuItemOpenProject);
        menuFile.addSeparator();
        menuFile.add(menuItemSave);
        menuFile.addSeparator();
        menuFile.add(menuItemCloseProject);
        //menuFile.addSeparator();
        menuFile.add(menuItemCloseAllProjects);
        menuFile.addSeparator();
        menuFile.add(menuItemFileExit);
        
        menuTools.add(menuItemFindAnnotation);
        menuTools.add(menuItemExport);
        
        menuView.add(menuItemProjectProperties);
        
        menuHelp.add(menuItemHelp);
        menuHelp.addSeparator();
        menuHelp.add(menuItemHelpAbout);
        
        menuBar.add(menuFile);
        menuBar.add(menuView);
        menuBar.add(menuTools);
        menuBar.add(menuHelp);
        
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        
        toolBarProject.add(buttonNewProject);
        toolBarProject.add(buttonCloseProject);
        toolBarProject.add(buttonOpenProject);
        toolBarProject.add(buttonProjectProperties);
        toolBarProject.add(buttonFindAnnotations);
        toolBarProject.add(buttonExportXML);
       
        toolBarFile.add(buttonSave);
        toolBarFile.add(buttonHelp);
        
        this.getContentPane().add(toolBarFile, BorderLayout.PAGE_START);
       // this.getContentPane().add(toolBarFile, BorderLayout.NORTH);
        
        labelProjects.setIcon(GUIComponentsRepository.PROJECT_LOGO);
        //labelProjects.setText("Projects View");
        //labelProjects.setIconTextGap(30);
        labelProjects.setForeground(new Color(125,0,0));
        //labelProjects.setBackground(tabbedPaneShowAnnotResult.getBackground());
        //labelProjects.setForeground(tabbedPaneShowAnnotResult.get());
        labelProjects.setFont(new Font(null, Font.BOLD, 16));
        labelProjects.setFocusable(false);        
        labelProjects.setBorderPainted(false);
        
        toolBarProject.setFloatable(false);
        
        projectTreeRootPane.getContentPane().add(projectTreeScrollPane);
        
        //tabbedPaneProjects.addTab("Projects View", GUIComponentsRepository.PROJECT_TAB, projectTreeRootPane);
        tabbedPaneProjects.addTab("Projects View", GUIComponentsRepository.PROJECT_TAB, panelProjects);
        
        projectTreeScrollPane.getViewport().add(projectTree, null);
        //mainSplitPane.add(projectTreeScrollPane, JSplitPane.LEFT);
        mainSplitPane.add(tabbedPaneProjects, JSplitPane.LEFT);
        //mainSplitPane.add(panelProjects, JSplitPane.LEFT);
        //mainSplitPane.add(projectTreeRootPane, JSplitPane.LEFT);
         
        mainSplitPane.add(searchAnnotResultSplitPane, JSplitPane.RIGHT);
         
        //showAnnotResultScrollPane.getViewport().add(showAnnotResultTree, null);
        tabbedPaneShowAnnotResult.getTabbedPane().addTab("Class View", labelClassView);
        searchAnnotResultSplitPane.add(tabbedPaneShowAnnotResult, JSplitPane.TOP);
        //tabbedPaneShowAnnotResult.addTab("Class View", GUIComponentsRepository.PROJECT_PROPS, showAnnotResultScrollPane);
        
        //searchAnnotResultScrollPane.getViewport().add(searchAnnotResultTree, null);
        tabbedPaneSearch.getTabbedPane().addTab("Search View", labelSearchView);
        searchAnnotResultSplitPane.add(tabbedPaneSearch, JSplitPane.BOTTOM);
        //tabbedPaneSearch.addTab("Search View", GUIComponentsRepository.FIND_ANNOTATIONS, searchAnnotResultScrollPane);
          
        
        projectTree.addMouseListener(projectTreeMouseListener);   
        //searchAnnotResultTree.addMouseListener(searchResultTreeMouseListener);
        
        panelCenter.add(mainSplitPane, null);
        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        //addKeyListener(new MainFrameShortKeyListenener(mainFrameActionListener));
       
        this.setIconImage(GUIComponentsRepository.PROJECT_LOGO.getImage());
    }

    public void closeLogo() {
        panelProjects.remove(labelProjects);
    }

   
    public void addSearchResultTreeModel(TreeModel tree) {
        JTree searchAnnotResultTree = new JTree(tree);
        //RightClickGlassPane searchResultTreeRightClickGlassPane 
          //                      = new RightClickGlassPane(searchAnnotResultTree);
        SearchResultTreeMouseListener searchResultTreeMouseListener =
                    new SearchResultTreeMouseListener(searchAnnotResultTree, 
                                                      (RightClickGlassPane) tabbedPaneSearch.getGlassPane());
        searchAnnotResultTree.addMouseListener(searchResultTreeMouseListener);
        JScrollPane searchAnnotResultScrollPane = new JScrollPane();
        searchAnnotResultScrollPane.getViewport().add(searchAnnotResultTree, null);
        searchAnnotResultTree.setModel(tree);
        //searchAnnotResultTree.setRootVisible(false);
        //searchAnnotResultTree.setShowsRootHandles(true);
        searchAnnotResultScrollPane.updateUI();
        searchAnnotResultTree.setVisible(true);
       
        Component[] tabs = tabbedPaneSearch.getComponents();
        if (tabs == null || tabs.length == 0) {
           //System.out.println(tabs.length);
           searchViewSeqNumber = 1;
        }
        
        if (!isFirstSearchResultTreeAdded) {
            if (tabbedPaneSearch.getTabbedPane().getTabCount() != 0) { //Initial tab may be cloased by user
                tabbedPaneSearch.getTabbedPane().removeTabAt(0);
            }
            isFirstSearchResultTreeAdded = true;
        }  
        
        tabbedPaneSearch.getTabbedPane().addTab("Search View " + searchViewSeqNumber++, searchAnnotResultScrollPane);
        tabbedPaneSearch.getTabbedPane().setSelectedComponent(searchAnnotResultScrollPane);
        //tabbedPaneSearch.updateUI();
    }

    public void addClassViewTreeModel(TreeModel tree, ClassInfo classInfo) {
        ClassViewNode root = (ClassViewNode) tree.getRoot();
        List<AnnotationRecord> colouredAnnotations = root.getColouredAnnotations();
         
        JTree classViewTree = new JTree(tree);
        classViewTree.setCellRenderer(new ColourTreeCellRenderer(colouredAnnotations));
         
        if (colouredAnnotations != null && colouredAnnotations.size() > 0) {
           TreeNodeBaseImpl nodeToSelect = TreeUtils.findNodeByIDInSubTree((TreeNodeBaseImpl)tree.getRoot(), colouredAnnotations.get(0));
           TreePath path = TreeUtils.getPath(nodeToSelect);
           classViewTree.setSelectionPath(path);
        }
         
        JScrollPane classViewScrollPane = new JScrollPane();
        classViewScrollPane.getViewport().add(classViewTree, null);
        classViewTree.setModel(tree);
        classViewScrollPane.updateUI();
        classViewTree.setVisible(true);
        
        if (!isFirstClassViewTreeAdded) {
            if (tabbedPaneShowAnnotResult.getTabbedPane().getTabCount() != 0) { //Initial tab may be cloased by user
                tabbedPaneShowAnnotResult.getTabbedPane().removeTabAt(0);
            }
            isFirstClassViewTreeAdded = true;
        }
        
        String className = ProjectModel.separateNameAndPackage(classInfo.getName())[0];
        tabbedPaneShowAnnotResult.getTabbedPane().addTab(className, classViewScrollPane);
        tabbedPaneShowAnnotResult.getTabbedPane().setSelectedComponent(classViewScrollPane);
    }
    
    public RightClickGlassPane getProjectTreeRightClickGlassPane() {
        return projectTreeRightClickGlassPane;
    }
    
    public JTree getProjectTree() {
        return projectTree;
    }

    public void set(Rectangle rect) {
    
        final int rightSubstraction = 5;
        final int bottomSubstraction = 81;
        final int taskBarHeight = 27;
    
        int x = (int)rect.getX();
        int y = (int)rect.getY();
        int width = (int) rect.getWidth();
        int height = (int) rect.getHeight() - taskBarHeight;
        
        this.setBounds(x, y, width, height);
        mainSplitPane.setBounds(x, y,
                width - rightSubstraction, height - bottomSubstraction);
    }
}
