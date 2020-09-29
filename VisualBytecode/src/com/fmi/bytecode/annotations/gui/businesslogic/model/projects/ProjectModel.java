package com.fmi.bytecode.annotations.gui.businesslogic.model.projects;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;
import com.fmi.bytecode.annotations.tool.ReadResult;

import com.fmi.bytecode.annotations.tool.indexing.AnnotationsIndex;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;


import com.fmi.bytecode.annotations.gui.utils.ModelUtils;

public class ProjectModel {
    public static final String FILE_EXTENSION = ".amp"; //amp = annotation model project

    public static final String DEFAULT_PROJECT_NAME = "AMProject";
    
    private String projectDir;
    private String projectName;
    private List<File> projectContent;
    private ReadResult annotationResult; //result from ByteCode tool for all content file of the project
    private AnnotationsIndex projectAnnotationsIndexes;
    private Map<File, AnnotationsIndex> annotationsIndexes; //(content,indx)
    private Set<String> allPackages; //for Combobox
    private Set<String> allAnnotations; //for Combobox
    
    public ProjectModel(String prjDir, String prjName, List<String> prjContent, boolean dummy) 
                                                  throws ModelException {
        init(prjDir, prjName, convertToFileList(prjContent));
    }
    
    public ProjectModel(String prjDir, String prjName, List<File> prjContent) 
                                                  throws ModelException {
        init(prjDir, prjName, prjContent);
    }
    
    private void init(String prjDir, String prjName, List<File> prjContent) 
                                                  throws ModelException {ModelUtils.validateFileAndDirectory(prjDir, prjName);
        this.projectDir = prjDir;
        this.projectName = prjName;
        
        //content
        if (prjContent == null  || prjContent.size() == 0) {
            throw new ModelException("Specify content of the project");
        }    
        Iterator prjIterator = prjContent.iterator();
        projectContent = new Vector<File>(prjContent.size());
        while(prjIterator.hasNext()) {           
            File currentContentItem = (File) prjIterator.next();
            if (!currentContentItem.exists()) {
                throw new ModelException("Invalid content item path: " + "\n" +
                                                currentContentItem.getAbsolutePath());
            }    
            projectContent.add(currentContentItem); 
        }

        try {
            annotationResult = ModelUtils.processContent(this);
            projectAnnotationsIndexes = annotationResult.getAnnotationsIndex(null, null);
            
            annotationsIndexes = new HashMap<File, AnnotationsIndex>();
            for (File contentItem : projectContent) {
                annotationsIndexes.put(contentItem, 
                            annotationResult.getAnnotationsIndex(null, contentItem));
                
            }    
        } catch (Exception e) {
            e.printStackTrace();
            throw new ModelException("Error while processing classes matadata of project " +
                                            getProjectName(), e);
        }
        
        allPackages = new TreeSet<String>();
        Map<String, ClassInfo> classInfo = annotationResult.getClasses();
        for (String currentClassName : classInfo.keySet()) {
            String[] packageAndName = separateNameAndPackage(currentClassName);
            String classPackage = packageAndName[1];
            if (classPackage != null) {
                String currSubPack = "";
                StringTokenizer st = new StringTokenizer(classPackage, ".");
                while (st.hasMoreTokens()) {
                    if (!currSubPack.equals("")) {
                        currSubPack += ".";
                    }
                    currSubPack += st.nextToken();
                    allPackages.add(currSubPack);
                }
            }
        }
        
        allAnnotations = new TreeSet<String>();
        Map<String, List<AnnotationRecord>> annotationRecord = projectAnnotationsIndexes.getAnnotations();
        for (String annotationName : annotationRecord.keySet()) {
            allAnnotations.add(annotationName);
        }
    }



    private List<File> convertToFileList(List<String> prjContent) {
        List<File> prjContentFiles = new ArrayList<File>();
        for (String content : prjContent) {
            prjContentFiles.add(new File(content));
        }
        return prjContentFiles;
    }
    
    public static String[] separateNameAndPackage(String classFullName) {
        String[] nameAndPackage = new String[2];
        String className = classFullName;
        String classPackage = null;
        int lastDot = className.lastIndexOf(".");
        if (lastDot != -1) {
            className = classFullName.substring(lastDot + 1);
            classPackage = classFullName.substring(0, lastDot);
        }
        nameAndPackage[0] = className;
        nameAndPackage[1] = classPackage;
        return nameAndPackage;
    }
    
    private static void saveFile(String prjDir, String prjName) throws ModelException {
        String fileAbsolutePath = prjDir + File.separator + prjName;
        File file = new File(fileAbsolutePath);
        try {
            file.createNewFile();
        } catch(IOException io) {    
            throw new ModelException("The project file " + fileAbsolutePath
                                             + " cannot be saved.", io);
        }    
    }
      
   
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof ProjectModel) {
            ProjectModel otherModel = (ProjectModel) obj;
            if (otherModel.getProjectDir().equals(getProjectDir()) 
                && otherModel.getProjectName().equals(getProjectName()) 
                && otherModel.getProjectContent().equals(getProjectContent())) {
                result = true;
            }
        }
        return result;
    }
    
    public int hashCode() {
        return getProjectDir().hashCode() + 
               getProjectName().hashCode();     
    }
    
    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;    
    }

    public String getProjectDir() {
        return projectDir;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectContent(List<File> projectContent) {
        this.projectContent = projectContent;
    }

    public List<File> getProjectContent() {
        return projectContent;
    }

    public ReadResult getAnnotationResult() {
        return annotationResult;
    }

    public AnnotationsIndex getAnnotationsIndex(File contentName) {
        return annotationsIndexes.get(contentName);
    }
    
    public ProjectModel clone() {
        try {
            return new ProjectModel(getProjectDir(), getProjectName(),
                                    new ArrayList<File>(getProjectContent()));
        } catch (ModelException e) {
            throw new IllegalStateException(e);
        }
    }

    public Set<String> getAllPackages() {
        return allPackages;
    }

    public Set<String> getAllAnnotations() {
        return allAnnotations;
    }
}
