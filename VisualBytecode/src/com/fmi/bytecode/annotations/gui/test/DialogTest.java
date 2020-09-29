package com.fmi.bytecode.annotations.gui.test;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.MainFrameActionListener;

import com.fmi.bytecode.annotations.gui.graphical.GUIComponentsRepository;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.ClosableTabbedPane;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.IconProxy;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.CheckboxGroup;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;

import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.plaf.ToolBarUI;

public class DialogTest extends JDialog {
    private JSplitPane jSplitPane1 = new JSplitPane();
    private ClosableTabbedPane jTabbedPane1 = new ClosableTabbedPane(GUIComponentsRepository.CLOSE_TAB);
    private JButton jButton1 = new JButton();
    
    private JToolBar toolBar = new JToolBar();
    private JButton buttonNew = new JButton();
    private JButton buttonClose = new JButton();
    private JButton buttonOpen = new JButton();
    private JButton buttonSave = new JButton();
    private JButton buttonHelp = new JButton();  
   
    private JScrollPane jScrollPane2 = new JScrollPane();
    private JTree jTree2 = new JTree();
    private TestActionListener mainFrameActionListener;
    private JSplitPane jSplitPane2 = new JSplitPane();
    private JPopupMenu jPopupMenu1 = new JPopupMenu();
    private JMenuItem jMenuItem1 = new JMenuItem();

    public DialogTest() {
        this(null, "", false);
    }

    public DialogTest(Frame parent, String title, boolean modal) {
        
        super(parent, title, modal);
        try {
            mainFrameActionListener = new TestActionListener(null);
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // classPopup.show(jTabbedPane1.getComponent(0), 0, 0);
    }

    private void jbInit() throws Exception {
        this.setSize(new Dimension(539, 523));
        this.getContentPane().setLayout( null );
        jSplitPane1.setBounds(new Rectangle(0, 0, 530, 490));
        jSplitPane1.setDividerSize(2);
        jSplitPane1.setDividerLocation(240);
        jSplitPane1.setOrientation(JSplitPane.VERTICAL_SPLIT);


                
        jButton1.setIcon(GUIComponentsRepository.IMAGE_EXIT);


        toolBar.setAlignmentX((float) 0.0);
        toolBar.setAlignmentY((float) 0.0);
        toolBar.setBounds(new Rectangle(1, 1, 149, 140));
        jScrollPane2.getViewport().add(jTree2, null);
        jTabbedPane1.addTab("jScrollPane2",
            //new IconProxy(GUIComponentsRepository.IMAGE_EXIT),
            jScrollPane2);
        jSplitPane1.add(jTabbedPane1, JSplitPane.BOTTOM);


       //new IconProxy(GUIComponentsRepository.IMAGE_EXIT),
            jTabbedPane1.addTab("jScrollPane2", jScrollPane2);
        jSplitPane1.add(jTabbedPane1, JSplitPane.BOTTOM);


        // jSplitPane2.add(jToolBar1, JSplitPane.LEFT);
        jSplitPane1.add(jSplitPane2, JSplitPane.TOP);
        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 

        jSplitPane2.add(toolBar, JSplitPane.LEFT);
        
        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 
            jTabbedPane1.addTab("Second", new JButton());
        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 
            jTabbedPane1.addTab("Third", new JButton());

        jTabbedPane1.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        if (jTabbedPane1.getTabCount() == 0) {
                            return;
                        }

                        if (!evt.isPopupTrigger()) {
                            IconProxy iconProxy = 
                                (IconProxy)jTabbedPane1.getIconAt(jTabbedPane1.getSelectedIndex());

                            if (iconProxy.contains(evt.getX(), evt.getY())) {
                                jTabbedPane1.removeTabAt(jTabbedPane1.getSelectedIndex());
                            }
                        }
                    }
                });

        this.getContentPane().add(jSplitPane1, null);

        buttonNew.setToolTipText("New Project");
        buttonNew.setActionCommand("NewProject");
        buttonNew.addActionListener(mainFrameActionListener);
        buttonNew.setIcon(GUIComponentsRepository.IMAGE_NEW);

        buttonClose.setToolTipText("Close Project");
        buttonClose.setActionCommand("CloseProject");
        // jSplitPane2.add(jToolBar1, JSplitPane.LEFT);
        jSplitPane1.add(jSplitPane2, JSplitPane.TOP);
        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 
        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 
            jTabbedPane1.addTab("Third", new JButton());
        jPopupMenu1.add(jMenuItem1);

        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 
        jTabbedPane1.addTab("Third", new JButton());

        jTabbedPane1.addMouseListener(new MouseAdapter() {
                    public void mouseReleased(MouseEvent evt) {
                        if (jTabbedPane1.getTabCount() == 0) {
                            return;
                        }

                        if (!evt.isPopupTrigger()) {
                            IconProxy iconProxy = 
                                (IconProxy)jTabbedPane1.getIconAt(jTabbedPane1.getSelectedIndex());

                            if (iconProxy.contains(evt.getX(), evt.getY())) {
                                jTabbedPane1.removeTabAt(jTabbedPane1.getSelectedIndex());
                            }
                        }
                    }
                });


        this.getContentPane().add(jSplitPane1, null);

        buttonNew.setToolTipText("New Project");
        buttonNew.setActionCommand("NewProject");
        buttonNew.addActionListener(mainFrameActionListener);
        buttonNew.setIcon(GUIComponentsRepository.IMAGE_NEW);

        buttonClose.setToolTipText("Close Project");
        buttonClose.setActionCommand("CloseProject");
        //TODO:buttonClose.addActionListener(mainFrameActionListener);
        buttonClose.setIcon(GUIComponentsRepository.CLOSE_MENU);

        buttonOpen.setToolTipText("Open Project");
        buttonOpen.setActionCommand("OpenProject");
        buttonOpen.addActionListener(mainFrameActionListener);
        buttonOpen.setIcon(GUIComponentsRepository.IMAGE_OPEN);

        buttonSave.setToolTipText("Save");
        buttonSave.setIcon(GUIComponentsRepository.IMAGE_SAVE);

        buttonHelp.setToolTipText("About");
        buttonHelp.setIcon(GUIComponentsRepository.IMAGE_HELP);

        jSplitPane2.setDividerLocation(150);
        jPopupMenu1.setLabel("jPopupMenu1");
        jMenuItem1.setText("PinkoMenu");
        toolBar.add(buttonNew);
        toolBar.add(buttonClose);
        toolBar.add(buttonOpen);
        toolBar.add(buttonSave);
        //toolBar.addTab("Second", new JButton());
        //new IconProxy(GUIComponentsRepository.CLOSE_TAB), 
        jTabbedPane1.addTab("Third", new JButton());
        jPopupMenu1.add(jMenuItem1);

        //toolBar.setVisible(true);

    }
    public static void main(String[] args) {
        new DialogTest().setVisible(true);
    }
}
