package com.fmi.bytecode.annotations.tool.file;

/**
 * The <code>WARFile</code> interface represents 
 * a processed WAR archive file.
 * The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. 
 * 
 * @author Krasimir Topchiyski
 */
public interface WARFile extends JARFile {
    
    /**
     * Gets all class, zip and jar files found in the WAR file, as JavaClassFile, JARFile and FolderInfo objects
     *
     * @return array with FileInfo entries
     */
    public FileInfo[] getFiles();
}
