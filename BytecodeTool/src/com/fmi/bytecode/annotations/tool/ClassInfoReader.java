package com.fmi.bytecode.annotations.tool;

import java.io.File;


/**
 * The <code>ClassInfoReader</code> has to provide the base functionality for extracting
 * class information out of a given multitude of files.
 * The reader can process folders, JAR, WAR or class files. 
 *
 * @author Krasimir Topchiyski
 */
public interface ClassInfoReader {
	
/**
 * This enumeration lists all supported input types
 */	
public static enum InputType{WAR, JAR, CLASS, FOLDER};

/**
 * Extracts information out of an array of class, JAR, WAR files and folders.
 *
 * The reader has to distinguish the given File objects and process them as follows:
 *  1) CLASS  file - process the whole file
 *  2) JAR file - process all class files inside
 *  3) WAR file - process all JAR and class files inside
 *  4) folder - traverse all subfolders and process all found JAR, WAR or class files
 * 
 * The processing of all inputed Files produces one information structure (ReadResult).
 * If one class is found more than ones in the processing multitude of Files, then 
 * a warnning is logged if all occurrences are equal, otherwise if the class appears 
 * in different forms an exception is thrown (ReadingException) and the processing is stopped.
 *
 * @param filesToTraverse an array of files(JAR, WAR or class)or folders to process
 * @return a ReaadResult object, representing whole processed information as a single structure
 * @throws ReadingException thrown if a problem occurs during class information processing
 *
 * @see com.fmi.bytecode.annotations.tool.AnnotationsReaderFactory
 * @see com.fmi.bytecode.annotations.tool.ReadResult
 * @see com.fmi.bytecode.annotations.element.Annotation
 */
public ReadResult read(File[] filesToTraverse) throws ReadingException;

}

