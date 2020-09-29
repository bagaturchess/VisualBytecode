package com.fmi.bytecode.annotations.gui.businesslogic.model;

public class ModelException extends Exception {
   
    public ModelException(String msg) {
        super(msg);
    }
    
    public ModelException(String msg, Exception e) {
        super(msg, e);
    }
}
