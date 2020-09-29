package com.fmi.bytecode.annotations.gui.businesslogic.treenodes;

import com.fmi.bytecode.annotations.element.ClassInfo;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;

public class TreeUtils {
   
    public static TreeNodeBaseImpl findNodeByIDInSubTree(TreeNodeBaseImpl root, 
                                                         Object id) {
        TreeNodeBaseImpl result = null;
        if (root.getNodeIdentifier().equals(id)) {
            result = root;
        } else {
            int childCount = root.getChildCount();
            for (int i = 0; i < childCount; i++) {
                TreeNodeBaseImpl currentChild = (TreeNodeBaseImpl) root.getChildAt(i);
                result = findNodeByIDInSubTree(currentChild, id);
                if (result != null) {
                    break;
                }
            }
        }
        return result;
    }
    
    public static TreePath getPath(TreeNode node) {
        List<TreeNode> nodes = new ArrayList<TreeNode>();
        
        TreeNode curr = node;
        do {
            nodes.add(0, curr);
            curr = curr.getParent();
        } while (curr != null);
        
        return new TreePath(nodes.toArray());
    }
    
    //for savable units
    //for given node(for example Project node) collect all classes
    public static void collectClassInfos(TreeNodeBaseImpl node, List<ClassInfo> classes) {
        Object id = node.getNodeIdentifier();
        if (id instanceof ClassInfo) {
            classes.add((ClassInfo) id);
        }
        
        int childCnt = node.getChildCount();
        for (int i = 0; i < childCnt; i++) {
            TreeNodeBaseImpl child = (TreeNodeBaseImpl) node.getChildAt(i);
            collectClassInfos(child, classes);
        }
    }
}
