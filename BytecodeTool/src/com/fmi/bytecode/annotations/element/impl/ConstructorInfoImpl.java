package com.fmi.bytecode.annotations.element.impl;


import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.element.ConstructorInfo;
import com.fmi.bytecode.annotations.input.ConstructorModel;


/**
 * Bytecode library MethodInfo class wrapper that implements the
 * <code>com.fmi.bytecode.annotations.element.ConstructorInfo<code> interface.
 * 
 * @author Krasimir Topchiyski
 */
public class ConstructorInfoImpl extends MethodInfoImpl implements
		ConstructorInfo {

	@SuppressWarnings("unchecked")
	public ConstructorInfoImpl(ConstructorModel constructorModel, ClassInfo owner) {
		super(constructorModel, owner);
	}

	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		return super.hashCode();
	}
}
