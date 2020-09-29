package com.fmi.bytecode.annotations.gui.test;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.NewProjectDialog;

import com.fmi.bytecode.annotations.gui.graphical.mainframe.MainFrame;

public class MainFrameTest {
    public MainFrameTest() {
       MainFrame mf = new MainFrame();
        mf.setVisible(true);
       
      //NewProjectDialog npd = new NewProjectDialog();
      //npd.setVisible(true);

    }

    public static void main(String[] args) {
        MainFrameTest mainFrameTest = new MainFrameTest();
    }
}
