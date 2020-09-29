package com.fmi.bytecode.annotations.gui.utils.xml;

public class XMLException extends Exception {
    
    public XMLException(String message) {
        super(message);
    }
    
    public XMLException(String message, Exception ex) {
        super(message, ex);
    }
}
