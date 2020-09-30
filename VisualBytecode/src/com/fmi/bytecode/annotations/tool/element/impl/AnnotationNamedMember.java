package com.fmi.bytecode.annotations.tool.element.impl;

import com.fmi.bytecode.annotations.input.AnnotationModel;
import com.fmi.bytecode.annotations.input.NamedMemberModel;
import com.fmi.bytecode.annotations.tool.element.AnnotationRecord;
import com.fmi.bytecode.annotations.tool.element.EnumConstant;
import com.fmi.bytecode.annotations.tool.element.NamedMember;
import com.fmi.bytecode.annotations.tool.util.ComparisonUtils;
import com.fmi.bytecode.annotations.input.EnumTypeModel;

/**
 * Bytecode library <code>AnnotationPairValue</code> class wrapper that implements 
 * the <code>com.fmi.bytecode.annotations.tool.anotation.NamedMember</code> interface
 * 
 * @author Krasimir Topchiyski
 */
public class AnnotationNamedMember implements NamedMember {

	private AnnotationRecord owner;
	private String name;	
	private Object objValue;
	private boolean isValueDefault;
	private int tag;
	
	public AnnotationNamedMember(
        com.fmi.bytecode.annotations.input.NamedMemberModel member,
        AnnotationRecord _owner) {
		owner = _owner;
		this.name = member.getName();		
		this.isValueDefault = false;
		this.tag = member.getMemberTag();
		objValue = convertValue(member.getMemberValue(), tag);
	}

	private Object convertValue(Object value, int tag) {
		switch(tag)
		{			
			case NamedMemberModel.TAG_ENUM:
				EnumTypeModel enumType = (EnumTypeModel)value;
				value = new EnumConstantImpl(enumType.getEnumerationTypeName(), enumType.getEnumerationLiteral());
			break;
			
			case NamedMemberModel.TAG_ARRAY:
				NamedMemberModel[] values = (NamedMemberModel[])value;
				int l = values.length;
				AnnotationNamedMember[] recs = new AnnotationNamedMember[l];
				for (int i = 0; i < values.length; ++i) {
					recs[i] = new AnnotationNamedMember(values[i], owner);
				}
				value = recs;
			break;
			
			case NamedMemberModel.TAG_ANNOTATION:
				AnnotationModel annotationModel = (AnnotationModel) value;
				value = new AnnotationRecordImpl(annotationModel, owner.getOwner());
			break;
		}
		
		return value;
	}

	public boolean isValueDefaulted() {
		return isValueDefault;
	}
	
	public String getName() {
		return name;
	}

	public int getMemberTag() {
		return tag;
	}

	public Object getMemberValue() {
		return objValue;
	}

	public boolean getBooleanValue() {
		return ((Boolean)objValue).booleanValue();
	}

	public byte getByteValue() {
		return ((Byte)objValue).byteValue();
	}

	public char getCharValue() {
		return ((Character)objValue).charValue();
	}

	public double getDoubleValue() {
		return ((Double)objValue).doubleValue();
	}

	public float getFloatValue() {
		return ((Float)objValue).floatValue();
	}

	public int getIntValue() {
		return ((Integer)objValue).intValue();
	}

	public long getLongValue() {
		return ((Long)objValue).longValue();
	}

	public short getShortValue() {
		return ((Short)objValue).shortValue();
	}

	public String getStringValue() {
		return (String)objValue;
	}

	public EnumConstant getEnumConstantValue() {
		return (EnumConstant)objValue;
	}

	public AnnotationRecord getAnnotationValue() {
		return (AnnotationRecord)objValue;
	}

	public NamedMember[] getMemberArrayValue() {
		return (AnnotationNamedMember[])objValue;
	}

	public int getMemberArrayLength() {
		return ((AnnotationNamedMember[])objValue).length;
	}
	
	public boolean equals(Object obj) {
		if (obj == null || ! (obj instanceof NamedMember))
			return false;
		
		NamedMember target = (NamedMember)obj;
		
		if (!ComparisonUtils.compareObjects(name, target.getName()))
			return false;
		
		Object targetObjValue = target.getMemberValue();
		
		if (objValue.getClass().isArray())
		{
			if (targetObjValue.getClass().isArray())
			{
				return ComparisonUtils.compareUnorderedArrays((Object[])objValue, (Object[])targetObjValue);
			}
			return false;
		} else if (!ComparisonUtils.compareObjects(objValue, targetObjValue))
			return false;
		return true;
	}

	public int hashCode(){
		return super.hashCode();
	}
	
	private class EnumConstantImpl implements EnumConstant {
		
		private String typeName;
		private String value;
		
		public EnumConstantImpl(String enumNameAndType, String value) {
			this.typeName = enumNameAndType;
			this.value = value;
		}

		public String getEnumerationTypeName() {
			return typeName;
		}

		public String getEnumerationLiteral() {
			return value;
		}
		
		public boolean equals(Object obj) {
			if (obj == null || ! (obj instanceof EnumConstant))
				return false;
			EnumConstant target = (EnumConstant)obj;
			if (!ComparisonUtils.compareObjects(target.getEnumerationLiteral(),getEnumerationLiteral()))
				return false;
			if (!ComparisonUtils.compareObjects(target.getEnumerationTypeName(),getEnumerationTypeName()))
				return false;
			return true;
		}
		
		public int hashCode(){
			return super.hashCode();
		}
		
		public String toString() {
			String result = "";
			result += "EnumConstant[" + typeName + "=" + value + "]";
			return result;
		}
	}
	
	public String toString() {
		String result = "";
		result += "AnnotationMember[" + name + "=" + objValue + "]";
		return result;
	}
}
