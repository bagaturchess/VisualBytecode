package com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers;

import java.io.File;

import java.util.Vector;

import javax.swing.JList;

public class SelectedFileValueListApplier implements SelectedFileValueApplier {
    
    private JList contentList;
    private Vector<File> contentItemList;
    
    public SelectedFileValueListApplier(JList contentList, Vector<File> contentItemList) {
        this.contentList     = contentList;
        this.contentItemList = contentItemList;
    }

    public void apply(File selectedFile) {
        //String newFilePath = selectedFile.getAbsolutePath();
        if (!contentItemList.contains(selectedFile)) {
            contentItemList.add(selectedFile);
            contentList.setListData(contentItemList);
            contentList.setSelectedIndex(contentItemList.size() - 1);
        }    
    }
}
