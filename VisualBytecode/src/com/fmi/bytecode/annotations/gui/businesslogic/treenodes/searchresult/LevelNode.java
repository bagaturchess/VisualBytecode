package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.indexing.AnnotationsIndex;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.FilterLevelType;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.MutableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;


public class LevelNode extends MutableNode implements SavableNode {
    
    private Map<ProjectModel, Map<File, AnnotationsIndex>> annIndexes;
    private boolean isEmpty = false;
    private FilterLevelType level;
    
    public LevelNode(TreeNodeBaseImpl parent, FilterLevelType level, String levelName, 
                     Map<ProjectModel, Map<File, AnnotationsIndex>> annIndexes) {
        super(parent, levelName);
        this.annIndexes = annIndexes;
        this.level = level;
        
        Map<ProjectModel, Map<File, List<AnnotationRecord>>> annLevels = SearchResultUtils.extractLevelAnnotations(annIndexes, level);
        if (annLevels.size() == 0) {
            setNodeName(levelName + " (No result for this level)");
            isEmpty = true;
        } else {
            createSubTree(annLevels);
        }
    }

    private void createSubTree(Map<ProjectModel, Map<File, List<AnnotationRecord>>> annLevels) {
        for (ProjectModel currentProject : annLevels.keySet()) {
            for (File currentContent : annLevels.get(currentProject).keySet()) {
                new ProjectAndContentNode(this, currentProject, currentContent, 
                                          annLevels.get(currentProject).get(currentContent),
                                          level);                
            }
        }
    }
    
    public boolean isLeaf() {
        return isEmpty;
    }

    public Object getNodeIdentifier() {
        return getNodeName();
    }

    public FilterLevelType getLevel() {
        return level;
    }
    
    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
         
        int childCount = this.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ProjectAndContentNode content = (ProjectAndContentNode) this.getChildAt(i);
            saveUnits.addAll(content.getUnits());                             
        }
        return saveUnits;
    }
}
