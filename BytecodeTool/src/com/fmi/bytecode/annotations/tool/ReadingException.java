package com.fmi.bytecode.annotations.tool;

/**
 * This is the base exception for the bytecode annotations processing API.
 * It is thrown if any class info processing problem occurs.
 * 
 * @author Krasimir Topchiyski
 */
public class ReadingException extends Exception {
    
    /** Serialization class version tag. */
    private static final long serialVersionUID = 5016083824960724957L;

    public ReadingException() {
	super();
    }

    public ReadingException(String message) {
	super(message);
    }

    public ReadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReadingException(Throwable cause) {
        super(cause);
    }
}