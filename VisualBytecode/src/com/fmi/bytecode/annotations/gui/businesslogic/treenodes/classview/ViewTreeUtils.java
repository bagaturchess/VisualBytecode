package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;

import java.util.List;

import javax.swing.tree.TreeNode;

public class ViewTreeUtils {
   
    public static TreeNode createTree(ClassInfo classInfo) {
        return createTree(classInfo, null);
    }

    public static TreeNode createTree(ClassInfo classInfo, 
                                      List<AnnotationRecord> classAnnotations) {
        return new ClassViewNode(null, classInfo, classAnnotations);
    }
}
