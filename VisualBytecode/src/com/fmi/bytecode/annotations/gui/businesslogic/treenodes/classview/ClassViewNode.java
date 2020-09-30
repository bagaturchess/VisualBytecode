package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import java.util.List;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.StaticTextNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.element.ConstructorInfo;
import com.fmi.bytecode.annotations.tool.element.FieldInfo;
import com.fmi.bytecode.annotations.tool.element.MethodInfo;
import com.fmi.bytecode.annotations.tool.file.FileInfo;


public class ClassViewNode extends AnnotatedViewNode {
 
    private List<AnnotationRecord> colouredAnnotations;
    
    //classInfo.getName() -> name of class with package          
    public ClassViewNode(TreeNodeBaseImpl parent, ClassInfo classInfo, 
                         List<AnnotationRecord> colouredAnnotations) {
        super(parent, getClassName2Display(classInfo), classInfo);
        initSubTree();
        this.colouredAnnotations = colouredAnnotations;
    }

	private static String getClassName2Display(ClassInfo classInfo) {
		FileInfo[] containingFiles = classInfo.getContainingFiles();
		if (containingFiles == null || containingFiles.length == 0) {
			return classInfo.getName();
		}
		
		String filesNames = "";
		for (int i=0; i<containingFiles.length; i++) {
			FileInfo fi = containingFiles[i];
			filesNames += fi.getFullPath();
			if (i != containingFiles.length - 1) {
				filesNames += ", ";
			}
		}
		
		return classInfo.getName() + " (" + filesNames + ")";
	}

    public ClassInfo getClassInfo() {
        return (ClassInfo) getElementInfo();
    }
    
    private void initSubTree() {
        MethodInfo[] methods = getClassInfo().getMethods();
        if (methods != null && methods.length > 0) {
            StaticTextNode rootMethodsNode = new StaticTextNode(this, StaticTextNode.METHODS_NODE);                                                                
            for (int i = 0; i < methods.length; i++) {
                new MethodViewNode(rootMethodsNode, methods[i]);
            }
        }
        
        FieldInfo[] fields = getClassInfo().getFields();
        if (fields != null && fields.length > 0) {
            StaticTextNode rootFieldsNode = new StaticTextNode(this, StaticTextNode.FIELDS_NODE);                                                                
            for (int i = 0; i < fields.length; i++) {
                new FieldViewNode(rootFieldsNode, fields[i]);
            }
        }
        
        ConstructorInfo[] constructors = getClassInfo().getConstructors();
        if (constructors != null && constructors.length > 0) {
            StaticTextNode rootConstructorsNode = new StaticTextNode(this, StaticTextNode.CONSTRUCTORS_NODE);                                                                
            for (int i = 0; i < constructors.length; i++) {
                new ConstructorViewNode(rootConstructorsNode, constructors[i]);
            }
        }
    }

    public ClassInfo getNodeIdentifier() {
        return getClassInfo();
    }

    public List<AnnotationRecord> getColouredAnnotations() {
        return colouredAnnotations;
    }
}
