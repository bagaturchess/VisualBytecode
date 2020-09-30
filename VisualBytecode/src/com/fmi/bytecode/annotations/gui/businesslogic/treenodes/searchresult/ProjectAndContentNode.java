package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.searchresult;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import java.util.Map;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.model.searchfilters.FilterLevelType;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.SavableNode;

import com.fmi.bytecode.annotations.gui.businesslogic.model.export.SaveUnit;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.TreeUtils;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.ElementNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.element.ClassMemberInfo;
import com.fmi.bytecode.annotations.tool.element.ElementInfo;

public class ProjectAndContentNode extends ElementNode implements SavableNode {
    private File content;
    private List<AnnotationRecord> annotations;
    private FilterLevelType level;
    
    public ProjectAndContentNode(TreeNodeBaseImpl parent,
                                 ProjectModel projectModel, File content,
                                 List<AnnotationRecord> annotations,
                                 FilterLevelType level) {
        super(parent, generateName(projectModel, content), projectModel);
        this.content = content;
        this.annotations = annotations;
        this.level = level;
        createSubTree();
    }


    private void createSubTree() {
        //for every class -> all annotations for current level(parent = level)
        Map<ClassInfo, List<AnnotationRecord>> childAnnotations 
                                = new HashMap<ClassInfo, List<AnnotationRecord>>();
        for (AnnotationRecord currentAnnotation : annotations) {
            ElementInfo elInfo = currentAnnotation.getOwner();
            ClassInfo currentClass = null;
            
            if (elInfo instanceof ClassInfo) {
                currentClass = (ClassInfo) elInfo;
            } else if (elInfo instanceof ClassMemberInfo) {
                currentClass = (ClassInfo) ((ClassMemberInfo)elInfo).getOwner();
            } else {
                throw new IllegalStateException("Unexpected annotation owner "
                                                + elInfo.getName());
            }
            
            List<AnnotationRecord> annotRecs = childAnnotations.get(currentClass);
            if (annotRecs == null) {
                annotRecs = new ArrayList<AnnotationRecord>();
                childAnnotations.put(currentClass, annotRecs);
            }
            annotRecs.add(currentAnnotation);   
        }
        //      
        for (ClassInfo currentClass : childAnnotations.keySet()) {
            List<AnnotationRecord> classAnnotations = childAnnotations.get(currentClass);
            new PackageAndClassNode(this, currentClass, classAnnotations, level);
        }
    }
    
    private static String generateName(ProjectModel projectModel, File content) {
        return projectModel.getProjectName() + ": " + content.getAbsolutePath();
    }

    public Object getNodeIdentifier() {
        return getProjectModel();
    }

    public File getContent() {
        return content;
    }

    public FilterLevelType getLevel() {
        return level;
    }

    public List<SaveUnit> getUnits() {
        List<SaveUnit> saveUnits = new ArrayList<SaveUnit>();
        List<ClassInfo> classInfos = new ArrayList<ClassInfo>();
        TreeUtils.collectClassInfos(this, classInfos);      
        SaveUnit unit = new SaveUnit(getProjectModel(), getContent(), classInfos);
        saveUnits.add(unit);
        return saveUnits;
    }
}
