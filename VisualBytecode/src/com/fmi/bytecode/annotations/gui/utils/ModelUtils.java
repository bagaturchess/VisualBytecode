package com.fmi.bytecode.annotations.gui.utils;

import com.fmi.bytecode.annotations.input.ByteCodeReader;
import com.fmi.bytecode.annotations.input.ByteCodeReaderFactory;
import com.fmi.bytecode.annotations.tool.AnnotationsReaderFactory;
import com.fmi.bytecode.annotations.tool.ClassInfoReader;
import com.fmi.bytecode.annotations.tool.ReadResult;

import java.io.File;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import java.util.StringTokenizer;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.ModelException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.fmi.bytecode.annotations.gui.utils.xml.XMLException;
import com.fmi.bytecode.annotations.gui.utils.xml.XMLUtils;

public class ModelUtils {
    public static final String TAG_PROJECT_ROOT = "AnnotationProject";
    public static final String TAG_PROJECT_NAME = "ProjectName";
    public static final String TAG_PROJECT_CONTENT = "ProjectContent";
    public static final String TAG_PROJECT_CONTENT_ITEM = "Content";
    
    public static final String DIRECTOTY_TYPE = "dir";
    public static final String FILE_EXCLUDED_SYMBOLS = "\\/:*?\"<>|"; 
    
    public static void saveProjectModel(ProjectModel prj) throws XMLException, IOException {
        Document doc = createProjectDocument(prj);     
        String projectFilePath = getProjectFileName(prj);
        XMLUtils.save(new File(projectFilePath), doc);
    }

    public static String getProjectFileName(ProjectModel prj) {
        String projectFilePath = prj.getProjectDir() + File.separator +
                                 prj.getProjectName() + ProjectModel.FILE_EXTENSION;
        return projectFilePath;
    }
    
    public static Document createProjectDocument(ProjectModel prj) throws XMLException {
        Document doc = XMLUtils.createNewDocument();
        fillProjectDocument(doc, prj);        
        return doc;
    }
    
    
    public static void fillProjectDocument(Document doc, ProjectModel prj) {
        Element root = doc.createElement(TAG_PROJECT_ROOT);
        doc.appendChild(root);
        XMLUtils.appendChild(root, TAG_PROJECT_NAME, prj.getProjectName());
        //XMLUtils.appendChild(root, "ProjectDir", prj.getProjectDir());
        Element projectContent = XMLUtils.appendChild(root, TAG_PROJECT_CONTENT, "");       
        for (File content: prj.getProjectContent()) {
            XMLUtils.appendChild(projectContent, TAG_PROJECT_CONTENT_ITEM, content.getAbsolutePath());    
        }      
    }
  
    private static Element checkChildNodeUniqueness(Element node, String tagName) 
                                                            throws XMLException {
        List<Element> children = XMLUtils.getChildren(node, tagName);
        if (children.size() == 0)
            throw new XMLException("Missing tag " + tagName);
        else if (children.size() != 1)    
            throw new XMLException("More than one tag " + tagName);
        return children.get(0);    
    }
    
    public static ProjectModel loadProjectModel(File projectFile) throws XMLException, 
                                                                         ModelException {
        Document doc = XMLUtils.loadDocument(projectFile);
        Element root = doc.getDocumentElement();
        if (!TAG_PROJECT_ROOT.equals(root.getNodeName())) {
            throw new XMLException("Project root tag " + root.getNodeName() + " is not equals to " + TAG_PROJECT_ROOT);
        }      
        String projectName = checkChildNodeUniqueness(root, TAG_PROJECT_NAME).getTextContent();
        String projectDir = projectFile.getParentFile().getAbsolutePath();
        Element contentElements = checkChildNodeUniqueness(root, TAG_PROJECT_CONTENT);
        
        List<Element> content = XMLUtils.getChildren(contentElements, TAG_PROJECT_CONTENT_ITEM);
        List<String> projectContent = new ArrayList<String>();
        for (Element currentItem : content) {
            String currentContentItem = currentItem.getTextContent();
            projectContent.add(currentContentItem);   
        }
        return new ProjectModel(projectDir, projectName, projectContent, false);
    }
    
    public static ReadResult processContent(ProjectModel projectModel) throws Exception {
        ByteCodeReader bcReader = ByteCodeReaderFactory.createByteCodeReader();
        ClassInfoReader cif = AnnotationsReaderFactory.getReader(bcReader);
        List<File> contentList =  projectModel.getProjectContent();
        File[] content = new File[contentList.size()];
        for (int i = 0; i < contentList.size(); i++) {
            content[i] = contentList.get(i);
        }
        ReadResult readResult = cif.read(content);
        return readResult;
    }    
    
    private static void validateVolume(String volumeName) throws ModelException {
        File[] roots = File.listRoots();
        for(int i = 0; i < roots.length; i++) {
            if(roots[i].getAbsolutePath().equals(volumeName + File.separator)) {
                break;
            }            
            if (i == roots.length - 1)
                throw new ModelException("Invalid file system volume " + volumeName);
        }
    }
    
    
    public static void validateFileDir(String prjDir) throws ModelException {
        File f = new File(prjDir);
        prjDir = f.getAbsolutePath();
        //System.out.println(prjDir);
        StringTokenizer tokens = new StringTokenizer(prjDir, File.separator);
        if (tokens.hasMoreTokens()) {
            String volume = tokens.nextToken();
            validateVolume(volume);
        }    
        while(tokens.hasMoreTokens()) {
            String currentToken = tokens.nextToken();
            validateFileName(currentToken, FILE_EXCLUDED_SYMBOLS);      
        }
    }
    
    public static void validateFileName(String fileName, String excludedSymbols) throws ModelException {
        for (int i = 0; i < excludedSymbols.length(); i++) {
            //if fileName contains any of excluded chars
            if (fileName.indexOf((int) excludedSymbols.charAt(i)) != -1) {
                String errorMsg  = "File path segment " + fileName + " is incorrect." + "\n" + 
                                   "A file path segment can not contain any of the following characters:" +
                                   "\n" + excludedSymbols;
                throw new ModelException(errorMsg);
            }
        }
    }

    public static void validateFileAndDirectory(String fileDir, String fileName) throws ModelException {
        //fileName
        if (fileName == null || fileName.trim().equals("")) {
            throw new ModelException("Specify file name");
        }
        validateFileName(fileName, FILE_EXCLUDED_SYMBOLS);
                
        //fileDir
        if (fileDir == null || fileDir.trim().equals("")) {
            throw new ModelException("Specify directory");
        }    
        validateFileDir(fileDir);           
    }
    
    private static boolean isFileExists(String filePath){       
       return new File(filePath).exists();
    }
    
    private static String getFileExtension(String filePath) {
        String extension = ""; // dali def = "dir" -> moje li da e razlichno ot file ili dir
        File file = new File(filePath);
        if (file.isFile()) {    
            String fileName = file.getName();
            int dotIndx = fileName.lastIndexOf(".");
            if (dotIndx != -1) //file has no extension
                extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        } else if (file.isDirectory()){
            extension = DIRECTOTY_TYPE;
        }
        return extension;
    }
}
