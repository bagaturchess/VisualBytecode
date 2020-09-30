package com.fmi.bytecode.annotations.tool.element.impl;

import static com.fmi.bytecode.annotations.tool.util.ConvertionUtils.*;

import com.fmi.bytecode.annotations.input.FieldModel;
import com.fmi.bytecode.annotations.tool.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.element.FieldInfo;
import com.fmi.bytecode.annotations.tool.util.ComparisonUtils;

/**
 * Bytecode library FieldInfo class wrapper that implements 
 * the <code>com.fmi.bytecode.annotations.tool.element.FieldInfo<code> interface.
 * 
 * @author Krasimir Topchiyski
 */
public class FieldInfoImpl extends ElementInfoImpl implements FieldInfo {
	
	private String rawType;
	private String signature;
	private String type;
	private String canonicalType;
	private String genericType;
	private ClassInfo owner;
	private boolean isGeneric;
	
	public FieldInfoImpl(FieldModel fieldModel, ClassInfo owner) {
		super(fieldModel.getAnnotations());
		
		this.owner = owner;
		this.name = fieldModel.getName();
		this.modifiers = fieldModel.getAccessFlags();
		this.isGeneric = fieldModel.isGeneric();
		this.rawType = fieldModel.getRawType();
		if (this.isGeneric){
			this.signature = fieldModel.getSignature();
		}		
	}
	
	public String getType() {
		if(type == null){
			type = convertToJLS(rawType);
		}
		return type;
	}
	
	public String getCanonicalType() {
		if(canonicalType == null){
			canonicalType = convertToCanonical(rawType);
		}
		return canonicalType;
	}
	
	public String getGenericType() {
		if(genericType == null){
			if(isGeneric){
				genericType = parseFieldSignature(signature);				
			} else {
				genericType = getCanonicalType();
			}
		}	
		return genericType;
	}
	
	public ClassInfo getOwner() {
		return owner;
	}
	
	public boolean isParametrised() {		 
		return isGeneric;
	}
	
	public boolean equals(Object obj) {
        if(!super.equals(obj)){
            return false;
        }
        
        if(obj instanceof FieldInfo){
            FieldInfoImpl fi = (FieldInfoImpl)obj;
            
            if(!ComparisonUtils.compareObjects(rawType, fi.rawType)) {
                return false;
            }
            
            if(!ComparisonUtils.compareObjects(signature, fi.signature)) {
                return false;
            }

            return true;
        }
        return false;
    }
	
	public int hashCode(){
		return super.hashCode();
	}

}
