package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.indexing.AnnotationsIndex;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.tree.TreeModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.FilterLevelType;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.StaticTextNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeModelImpl;

public class SearchResultUtils {

    public static TreeModel createTree(Map<ProjectModel, Map<File, AnnotationsIndex>> annIndexes) {
        ResultNode root = new ResultNode("Result");

        new LevelNode(root, FilterLevelType.CLASS, StaticTextNode.CLASSES_NODE, annIndexes);
        new LevelNode(root, FilterLevelType.METHOD, StaticTextNode.METHODS_NODE, annIndexes);
        new LevelNode(root, FilterLevelType.FIELD, StaticTextNode.FIELDS_NODE, annIndexes);
        new LevelNode(root, FilterLevelType.CONSTRUCTOR, StaticTextNode.CONSTRUCTORS_NODE, annIndexes);
        new LevelNode(root, FilterLevelType.PARAMETER, StaticTextNode.PARAMETERS_NODE, annIndexes);
        
        return new TreeModelImpl(root);
    }
    
    public static Map<ProjectModel, Map<File, List<AnnotationRecord>>> 
            extractLevelAnnotations(Map<ProjectModel, Map<File, AnnotationsIndex>> annIndexes, 
                                    FilterLevelType level) {  
            
        Map<ProjectModel, Map<File, List<AnnotationRecord>>> annLevels =
            new HashMap<ProjectModel, Map<File, List<AnnotationRecord>>>();
            
        for (ProjectModel currProject : annIndexes.keySet()) {
             Map<File, AnnotationsIndex> currContentAnnotIndex
                                         = annIndexes.get(currProject);
            for (File content : currContentAnnotIndex.keySet()) {
                AnnotationsIndex ai = currContentAnnotIndex.get(content);
                List<AnnotationRecord> annotations = 
                                       getAnnotationsByLevel(level, ai);
                if (annotations.size() != 0) {
                    Map<File, List<AnnotationRecord>> projectAnnotations = annLevels.get(currProject);
                    if (projectAnnotations == null) {
                        projectAnnotations = new HashMap<File, List<AnnotationRecord>>();
                        annLevels.put(currProject, projectAnnotations);
                    }
                    projectAnnotations.put(content, annotations);
                }
            }
        }
        return annLevels;    
    }
    
    private static List<AnnotationRecord> getAnnotationsByLevel(FilterLevelType level, AnnotationsIndex ai) {
        List<AnnotationRecord> annotations = new ArrayList<AnnotationRecord>();
        
        Map<String, List<AnnotationRecord>> elementsAnnotations = null;
        if (level.equals(FilterLevelType.CLASS)) {
            elementsAnnotations = ai.getClassAnnotations();    
        } else if (level.equals(FilterLevelType.METHOD)) {
            elementsAnnotations = ai.getMethodAnnotations();    
        } else if (level.equals(FilterLevelType.CONSTRUCTOR)) {
            elementsAnnotations = ai.getConstructorAnnotations();    
        } else if (level.equals(FilterLevelType.FIELD)) {
            elementsAnnotations = ai.getFieldAnnotations();    
        } else if (level.equals(FilterLevelType.PARAMETER)) {
            elementsAnnotations = ai.getMethodParamAnnotations();    
        } else {
            throw new IllegalStateException("Wrong annotation Level");
        }
         
        for (String currentAnnotName : elementsAnnotations.keySet()) {
            annotations.addAll(elementsAnnotations.get(currentAnnotName));
        }
        return annotations;
    }
}
