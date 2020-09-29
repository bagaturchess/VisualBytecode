package com.fmi.bytecode.annotations.input;

public interface FieldModel extends AnnotatableElementModel {
	public String getRawType();

	public String getSignature();

	public boolean isGeneric();
}
