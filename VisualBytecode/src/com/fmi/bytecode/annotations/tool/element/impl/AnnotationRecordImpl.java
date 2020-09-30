package com.fmi.bytecode.annotations.tool.element.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fmi.bytecode.annotations.input.AnnotationModel;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.ElementInfo;
import com.fmi.bytecode.annotations.tool.element.NamedMember;
import com.fmi.bytecode.annotations.tool.util.ComparisonUtils;

/**
 * Bytecode library <code>AnnotationStruct</code> class wrapper that implements 
 * the <code>com.fmi.bytecode.annotations.tool.anotation.AnnotationRecord</code> interface
 * 
 * @author Krasimir Topchiyski
 */
public class AnnotationRecordImpl implements AnnotationRecord {
	
	private String type;
	private ElementInfo owner;
	private boolean isVisible;
	private Map<String, NamedMember> namedMembersMap = new HashMap<String, NamedMember>();
	
	public AnnotationRecordImpl(AnnotationModel annotationModel, ElementInfo owner) {
		this.owner = owner;
		this.isVisible = annotationModel.isRuntimeVisible();
		
		// the type should be the fully qualified class name and 
		// the class file format returns it as descriptor (ex. Ljava/lang/Object;)
		this.type = annotationModel.getTypeName().substring(1, annotationModel.getTypeName().length() - 1).replace('/', '.');
		
		Map<String, com.fmi.bytecode.annotations.input.NamedMemberModel> members = annotationModel.getNamedMembersMap();
		Iterator keys = members.keySet().iterator();
		while(keys.hasNext())
		{
			String memberName = (String)keys.next();
			com.fmi.bytecode.annotations.input.NamedMemberModel member
				= (com.fmi.bytecode.annotations.input.NamedMemberModel) members.get(memberName);
			
			namedMembersMap.put(memberName, new AnnotationNamedMember(member, this));
		}		
	}
	
	public ElementInfo getOwner() {
		return this.owner;
	}

	public String getTypeName() {
		return this.type;
	}

	public boolean isRuntimeVisible() {
		return isVisible;
	}

	public int getMembersLength() {
		return namedMembersMap.values().size();
	}

	public NamedMember getMember(String name) {
		return namedMembersMap.get(name);
	}
	
	public Map<String, NamedMember> getNamedMembersMap() {
		return namedMembersMap;
	}
	
	public String toString() {
		return type + namedMembersMap.toString();
	}
	
	public boolean equals(Object obj) {
		if (obj == null || ! (obj instanceof AnnotationRecord))
			return false;
		AnnotationRecord target = (AnnotationRecord)obj;
		if (!ComparisonUtils.compareObjects(type, target.getTypeName()))
			return false;
		if (isVisible != target.isRuntimeVisible())
			return false;
		if (!ComparisonUtils.compareObjects(namedMembersMap, target.getNamedMembersMap()))
			return false;
		return true;
	}
	
	public int hashCode(){
		return super.hashCode();
	}

}
