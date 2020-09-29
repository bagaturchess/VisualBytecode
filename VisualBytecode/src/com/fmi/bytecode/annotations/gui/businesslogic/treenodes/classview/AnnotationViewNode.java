package com.fmi.bytecode.annotations.gui.businesslogic.treenodes.classview;

import com.fmi.bytecode.annotations.element.AnnotationRecord;

import com.fmi.bytecode.annotations.element.NamedMember;
import com.fmi.bytecode.annotations.element.impl.AnnotationNamedMember;

import com.fmi.bytecode.annotations.input.NamedMemberModel;

import java.util.Map;

import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.MutableNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.StaticTextNode;
import com.fmi.bytecode.annotations.gui.businesslogic.treenodes.common.TreeNodeBaseImpl;

public class AnnotationViewNode extends MutableNode {

    //annotationRecord -> model of anniotation
    private AnnotationRecord annotationRecord;
    
    public AnnotationViewNode(TreeNodeBaseImpl parent, AnnotationRecord annotationRecord) {
        super(parent, annotationRecord.getTypeName());
        this.annotationRecord = annotationRecord;
        initSubTree();
    }

    public AnnotationRecord getAnnotationRecord() {
        return annotationRecord;
    }
    
    private void initSubTree() {
        //get fileds of annotation
        Map<String, NamedMember> annotMembers 
                                        = annotationRecord.getNamedMembersMap();        
        for (String memberName : annotMembers.keySet()) {
            NamedMember member = annotMembers.get(memberName);
            processSingleMember(this, member);
        }
    }

    private void processSingleMember(TreeNodeBaseImpl parent, NamedMember member) {
        String memberName = member.getName();
        Object memberValue = member.getMemberValue();
        
        int memberType = member.getMemberTag();        
        switch (memberType) {
            case NamedMemberModel.TAG_ANNOTATION:
                new AnnotationViewNode(parent, (AnnotationRecord) memberValue);
                return;
            case NamedMemberModel.TAG_ARRAY:
                StaticTextNode membersArrayNode = new StaticTextNode(parent,
                                        memberName + "=array");
                NamedMember[] members = member.getMemberArrayValue();
                for (int i = 0; i < members.length; i++) {
                    NamedMember currentMember = members[i];
                    processSingleMember(membersArrayNode, currentMember);
                }
                return;
            /*case NamedMemberModel.TAG_REF:
                break;*/
            case NamedMemberModel.TAG_ENUM:
                 memberValue = 
                        member.getEnumConstantValue().getEnumerationLiteral();
                 break;
        }
        
        String nodeName = memberName != null ?
                            memberName + "=" + memberValue.toString() :
                            memberValue.toString();
        new AnnotationAttributeViewNode(parent, nodeName, member);
    }
    
    public boolean equals(Object obj) {
        boolean result = false;
        if (obj instanceof AnnotationViewNode) {
            AnnotationViewNode other = (AnnotationViewNode) obj;
            if (other.getAnnotationRecord().equals(getAnnotationRecord())) {
                result = true;
            }
        }
        return result;
    }
    
    public int hashCode() {
        return getAnnotationRecord().hashCode();
    }

    public AnnotationRecord getNodeIdentifier() {
        return getAnnotationRecord();
    }
}
