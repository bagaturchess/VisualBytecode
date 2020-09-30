package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.project;


import java.io.File;

import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.model.projects.ProjectModel;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationClassNode;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationPackageNode;
import com.fmi.bytecode.annotations.tool.tool.indexing.AnnotationsIndex;


public class ProjectTreeUtils {
    
    public static ProjectNode createTree(ProjectModel prjModel, 
                                      TreeNodeBaseImpl parent) {
        ProjectNode project = new ProjectNode(parent, prjModel);
        for (File contentItem : prjModel.getProjectContent()) {
            ProjectContentNode projectContentNode = 
                    new ProjectContentNode(project, contentItem);
            AnnotationsIndex ai = prjModel.getAnnotationsIndex(contentItem);
            createContentTree(projectContentNode, ai, contentItem);
        }
        return project;
    }
    
    private static void createContentTree(TreeNodeBaseImpl parent, 
                                          AnnotationsIndex ai,
                                          File contentFile) {
        List<AnnotationClassNode> rootClasses = ai.getRootClasses();
        List<AnnotationPackageNode> rootPackages = ai.getRootPackages();
        
        List<AnnotationPackageNode> subPackages = rootPackages;
        for (AnnotationPackageNode subPackage : subPackages) {
            PackageNode newParent = new PackageNode(parent, subPackage.getName(),
                                                    contentFile);
            addSubPackages(subPackage, newParent, contentFile);
        }
        List<AnnotationClassNode> subClasses = rootClasses;
        for (AnnotationClassNode subClass : subClasses) {
            new ClassNode(parent, subClass.getName(), subClass.getClassInfo(),
                                                    contentFile);
        }
    }
    
    private static void addSubPackages(AnnotationPackageNode annotParent, 
                                       PackageNode parent, File contentFile) {
        List<AnnotationPackageNode> subPackages = annotParent.getSubPackages();
        for (AnnotationPackageNode subPackage : subPackages) {
            PackageNode newParent = new PackageNode(parent, subPackage.getName(), 
                                                    contentFile);
            addSubPackages(subPackage, newParent, contentFile);
        }
        List<AnnotationClassNode> subClasses = annotParent.getClasses();
        for (AnnotationClassNode subClass : subClasses) {
            new ClassNode(parent, subClass.getName(), subClass.getClassInfo(),
                          contentFile);
        }
    }
}
