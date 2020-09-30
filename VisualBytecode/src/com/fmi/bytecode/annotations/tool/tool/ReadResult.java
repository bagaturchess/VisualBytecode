package com.fmi.bytecode.annotations.tool.tool;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.file.FileInfo;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationFilter;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationsIndex;

/**
 * The <code>ReadResult</code> has to provide the common root (single store) of 
 * the processed class information.
 * All implementors of ReadResult have to provide immutable objects.
 *
 * @author Krasimir Topchiyski
 */
public interface ReadResult {
    
    /**
     * Gets all processed files.
     * The inputed folders and files are traversed and presented with FileInfo objects.
     * Only root files and folders are presented in the returned array. To get all processed files, you 
     * have to traverse the tree of the FileInfo objects.
     *
     * @return a map with file name as a key and FileInfo object as a value
     */
    public FileInfo[] getProcessedFiles();
   
    /**
     * Returns all processed classes
     * @return immutable Map with a class name as a key and <code>com.fmi.bytecode.annotations.tool.element.ClassInfo</code>
     * as a value
     * @see com.fmi.bytecode.annotations.tool.element.ClassInfo
     */    
    public Map<String,ClassInfo> getClasses();

    /**
     * Gets an immutable <code>com.fmi.bytecode.annotations.tool.element.element.ClassInfo</code> object,
     * by its fully qualified class name
     * @param className fully qualified Java language standard class name
     * @return an immutable com.fmi.bytecode.annotations.tool.element.element.ClassInfo object
     */
    public ClassInfo getClass(String className);
    
    /**
     * Gets all class level annotations from all classes
     * @return an immutable Map with annotation type as a key and a list 
     * with <code>com.fmi.bytecode.annotations.tool.element.AnnotationRecord<code> objects
     */        
    public Map<String, List<AnnotationRecord>> getClassLevelAnnotations();
    
    /**
     * Gets an array with descriptions of the problems, found in processing
     * 
     * @return an array with problem descriptions
     */
    public String[] getProcessingProblems();
    
    
    public AnnotationsIndex getAnnotationsIndex(final String p0, final File p1);
    
    public AnnotationsIndex getAnnotationsIndex(final String p0, final File p1, final List<AnnotationFilter> p2);

}
