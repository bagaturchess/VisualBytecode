package com.fmi.bytecode.annotations.gui.businesslogic.actions;

import com.fmi.bytecode.annotations.gui.businesslogic.actions.appliers.SelectedFileValueFieldApplier;

import com.fmi.bytecode.annotations.gui.graphical.dialogs.BrowseFileSystemDialog;
import com.fmi.bytecode.annotations.gui.graphical.dialogs.ExportToXMLDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import java.util.List;

import javax.swing.JOptionPane;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.ExportConfiguration;
import com.fmi.bytecode.annotations.gui.businesslogic.model.export.ExportModel;

import org.w3c.dom.Document;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.utils.ExportUtils;
import com.fmi.bytecode.annotations.gui.utils.ModelUtils;
import com.fmi.bytecode.annotations.gui.utils.xml.XMLUtils;

public class ExportToXMLActionListener implements ActionListener {
    
    private ExportToXMLDialog xmlDialog;
    
    public ExportToXMLActionListener(ExportToXMLDialog xmlDialog) {
        this.xmlDialog = xmlDialog;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        //System.out.println(command);
        if (command.equals("Export")) {
            export();
        } else if (command.equals("Cancel")) {
            xmlDialog.dispose();
        } else if (command.equals("BrowseDir")) {
            new BrowseFileSystemDialog(xmlDialog, BrowseFileSystemDialog.DIRECTORIES_ONLY,
                                       new SelectedFileValueFieldApplier(xmlDialog.getFileDirTextField()),BrowseFileSystemDialog.FILTERS_NONE);
        } else if (command.equals("BrowseSchema")) {
            new BrowseFileSystemDialog(xmlDialog, BrowseFileSystemDialog.FILES_ONLY,
                                       new SelectedFileValueFieldApplier(xmlDialog.getSchemaDirTextField()),BrowseFileSystemDialog.FILTERS_XSL);
        } else if (command.equals("EnableCustom")) {
            xmlDialog.enableCustomScheme(true);
        } else if (command.equals("DisableCustom")) {
            xmlDialog.enableCustomScheme(false);
        }
        
    }

    private void export() {
        String fileName = xmlDialog.getFileNameTextField().getText();
        String fileDir = xmlDialog.getFileDirTextField().getText();
        String xslLocation = xmlDialog.getSchemaDirTextField().getText();
        List<SaveUnit> units = xmlDialog.getUnits();
        
        if (xslLocation.equals(ExportUtils.DEFAULT_XML_SCHEME)) {
            xslLocation = null;
        }
        
        try {
            ModelUtils.validateFileAndDirectory(fileDir, fileName);
            String fileFullPath = fileDir + File.separator + fileName;
            File exportXMLFile = new File(fileFullPath);
            
            boolean saveFile = true;
            if (new File(fileFullPath).exists()) {
                int option = JOptionPane.showOptionDialog(xmlDialog, 
                     "The file " + fileFullPath + 
                     " specified already exists.\r\nDo you want to replace the existing file?",
                     "Warning",
                     JOptionPane.YES_NO_CANCEL_OPTION,
                     JOptionPane.QUESTION_MESSAGE,
                     null, //Icon
                     new Object[] {"Yes", "No"}, "No");
                     
                if (option != JOptionPane.YES_OPTION) {
                    saveFile = false;
                }
            }
            
            if (saveFile) {
                ExportModel exportModel = new ExportModel(fileName, fileDir, xslLocation, units);
                Document doc = ExportUtils.createExportDoc(exportModel, new ExportConfiguration());
                XMLUtils.validate(doc);
                
                //Make transformation if any
                if (xslLocation != null) {
                    XMLUtils.transformXML(doc, xslLocation, exportXMLFile);
                } else {
                    XMLUtils.save(exportXMLFile, doc);    
                }
                
                JOptionPane.showMessageDialog(xmlDialog, "Meta data was successfully exported into " +
                                              exportModel.getExportFile().getAbsolutePath(), 
                                             "Information", 
                                              JOptionPane.INFORMATION_MESSAGE);
                xmlDialog.dispose();
            }                                              
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(xmlDialog, e.getMessage(), "Error", 
                                          JOptionPane.ERROR_MESSAGE);
        }
    }
}
