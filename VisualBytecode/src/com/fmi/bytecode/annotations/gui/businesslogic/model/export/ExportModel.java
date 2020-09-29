package com.fmi.bytecode.annotations.gui.businesslogic.model.export;

import java.io.File;

import java.util.List;


public class ExportModel {

    private String fileName;
    private String fileDir;
    private String xslLocation;
    private List<SaveUnit> units;
    
    private File exportFile;
    private File xslFile;
    
    public ExportModel(String fileName, String fileDir, String xslLocation, 
                       List<SaveUnit> units) throws ExportException {
        this.fileName = fileName;
        this.fileDir = fileDir;
        this.xslLocation = xslLocation;
        this.units = units;
        init();
    }

    public File getExportFile() {
        return exportFile; 
    }

    //TODO:
    private void init() throws ExportException {
        String fileFullPath = fileDir + File.separator + fileName;
        exportFile = new File(fileFullPath);
        
        if (xslLocation != null) {
            xslFile = new File(xslLocation);
            if (!xslFile.exists()) {
                throw new ExportException("Invalid xsl file location\r\n" + xslLocation);
            }
        }
    }

    public List<SaveUnit> getUnits() {
        return units;
    }

    public String getXslLocation() {
        return xslLocation;
    }
}
