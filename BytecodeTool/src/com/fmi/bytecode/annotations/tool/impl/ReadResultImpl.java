package com.fmi.bytecode.annotations.tool.impl;


import com.fmi.bytecode.annotations.tool.indexing.AnnotationFilter;
import java.io.File;

import com.fmi.bytecode.annotations.tool.indexing.ClassFileAnnotationsIndexImpl;
import com.fmi.bytecode.annotations.element.impl.ClassInfoImpl;
import java.util.Collections;
import com.fmi.bytecode.annotations.element.AnnotationRecord;
import java.util.HashMap;
import java.util.ArrayList;
import com.fmi.bytecode.annotations.tool.indexing.ClassFilesAnnotationsIndexImpl;
import com.fmi.bytecode.annotations.tool.indexing.AnnotationsIndex;
import com.fmi.bytecode.annotations.element.ClassInfo;
import java.util.Map;
import com.fmi.bytecode.annotations.file.FileInfo;
import com.fmi.bytecode.annotations.input.ReadingException;

import java.util.List;
import com.fmi.bytecode.annotations.tool.ReadResult;

public class ReadResultImpl implements ReadResult
{
    private List<FileInfo> files;
    private Map<String, ClassInfo> classes;
    private List<String> problems;
    private Map<String, AnnotationsIndex> classFilesAnnotations;
    private ClassFilesAnnotationsIndexImpl classFilesAnnotationsIndex;
    
    public ReadResultImpl() {
        this.files = new ArrayList<FileInfo>();
        this.classes = new HashMap<String, ClassInfo>();
        this.problems = new ArrayList<String>();
        this.classFilesAnnotations = new HashMap<String, AnnotationsIndex>();
        this.classFilesAnnotationsIndex = new ClassFilesAnnotationsIndexImpl();
    }
    
    public FileInfo[] getProcessedFiles() {
        return this.files.toArray(new FileInfo[this.files.size()]);
    }
    
    public Map<String, List<AnnotationRecord>> getClassLevelAnnotations() {
        return (Map<String, List<AnnotationRecord>>)this.classFilesAnnotationsIndex.getClassAnnotations();
    }
    
    public Map<String, ClassInfo> getClasses() {
        return Collections.unmodifiableMap((Map<? extends String, ? extends ClassInfo>)this.classes);
    }
    
    public ClassInfo getClass(final String className) {
        final ClassInfo ci = this.classes.get(className);
        return ci;
    }
    
    public String[] getProcessingProblems() {
        return this.problems.toArray(new String[this.problems.size()]);
    }
    
    public void addProcessedFile(final FileInfo fi) {
        this.files.add(fi);
    }
    
    void addClass(final ClassInfo ci) throws ReadingException {
        final ClassInfoImpl old = (ClassInfoImpl)this.classes.get(ci.getName());
        if (old != null) {
            final FileInfo[] fi = old.getContainingFiles();
            String files = "";
            if (fi != null) {
                final FileInfo[] array = fi;
                for (int i = 0; i < array.length; ++i) {
                    final FileInfo f = array[i];
                    files = String.valueOf(files) + f.getFullPath() + ", ";
                }
            }
            String newFile = "class stream";
            final FileInfo[] cfs = ci.getContainingFiles();
            if (cfs != null && cfs.length > 0) {
                old.addContainingFile(cfs[0]);
                newFile = cfs[0].getFullPath();
            }
            if (!old.equals((Object)ci)) {
                this.problems.add("There are different versions of the class [" + ci.getName() + "]! " + " One version can be found in: " + files + " And the other in: " + newFile);
            }
        }
        else {
            this.classes.put(ci.getName(), ci);
            final AnnotationsIndex cfa = (AnnotationsIndex)new ClassFileAnnotationsIndexImpl(ci);
            this.classFilesAnnotations.put(ci.getName(), cfa);
            this.classFilesAnnotationsIndex.addClassFileAnnotations(ci, (List)new ArrayList());
        }
    }
    
    public AnnotationsIndex getAnnotationsIndex(final String classNamePrefix, final File containingFileName) {
        return this.getAnnotationsIndex(classNamePrefix, containingFileName, new ArrayList<AnnotationFilter>());
    }
    
    public AnnotationsIndex getAnnotationsIndex(final String classNamePrefix, final File containingFile, final List<AnnotationFilter> annFilters) {
        final ClassFilesAnnotationsIndexImpl classFilesAnnotationsIndex = new ClassFilesAnnotationsIndexImpl();
        for (final String className : this.classes.keySet()) {
            final ClassInfo ci = this.classes.get(className);
            if (classNamePrefix == null || ci.getName().startsWith(classNamePrefix)) {
                final FileInfo[] containersFiles = ci.getContainingFiles();
                for (int i = 0; i < containersFiles.length; ++i) {
                    final FileInfo fi = containersFiles[i];
                    if (this.isContainingFile(fi, containingFile)) {
                        classFilesAnnotationsIndex.addClassFileAnnotations(ci, (List)annFilters);
                        break;
                    }
                }
            }
        }
        classFilesAnnotationsIndex.createTree();
        return (AnnotationsIndex)classFilesAnnotationsIndex;
    }
    
    private boolean isContainingFile(final FileInfo fi, final File containingFile) {
        boolean result = false;
        FileInfo curFileInfo = fi;
        while (containingFile != null && !new File(curFileInfo.getFullPath()).equals(containingFile)) {
            curFileInfo = curFileInfo.getParent();
            if (curFileInfo == null) {
                return result;
            }
        }
        result = true;
        return result;
    }
}