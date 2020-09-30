package com.fmi.bytecode.annotations.tool.element;

/**
 * The <code>ClassMemberInfo</code> interface provides a common interface for the class elements
 * <b> The implementors of the interface must be considered as immutable objects. 
 * It is up to the implementation to decide how to enforce the immutability. </b>
 * 
 * @author Krasimir Topchiyski
 */

public interface ClassMemberInfo {

	/**
	 * Gets a <code>com.fmi.bytecode.annotations.tool.element.ElementInfo</code> object, which 
	 * declares the current class member (field, method or constructor)
	 * 
	 * @return a <code>com.fmi.bytecode.annotations.tool.element.ClassInfo</code> object
	 * @see <code>com.fmi.bytecode.annotations.tool.element.ClassInfo</code>
	 */
	public ElementInfo getOwner();
}
