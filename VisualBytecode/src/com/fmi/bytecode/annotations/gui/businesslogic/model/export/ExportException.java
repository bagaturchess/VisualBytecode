package com.fmi.bytecode.annotations.gui.businesslogic.model.export;

public class ExportException extends Exception {
    
    public ExportException(String message) {
        super(message);
    }
    
    public ExportException(String message, Exception ex) {
        super(message, ex);
    }
}
