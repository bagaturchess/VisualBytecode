package com.fmi.bytecode.annotations.element;


// --------------------------------------------------------------------------
/**
 * Discriminated union representing a single annotation member value. The
 * concrete member type is indicated by a tag value, using the same encoding as
 * in the class file.
 * <p>
 * Relevant reference documentation is <i>Chapter 4: The class File Format</i>
 * of the Java Virtual Machine Specification, 2<sup>nd</sup> edition, as
 * extended in <a href="http://jcp.org/en/jsr/detail?id=924">JSR-924</a> for
 * the JSE 5.0 release, <a name="VM2+"><a
 * href="http://java.sun.com/docs/books/vmspec/2nd-edition/ClassFileFormat-Java5.pdf">[VMspec2+;&sect;4]</a></a>.
 * 
 * <a name="MemberFields">
 * <h4>Annotation MemberModel Representation</h4>
 * </a>
 * 
 * Depending on the member discriminator, values are represented as follows,
 * were <code>null</code> must not occur as value in any case: <table>
 * <tr valign=top class=TableSubHeadingColor>
 * <th>tag value</th>
 * <th>value representation</th>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_BYTE}, {@link #TAG_CHAR}, {@link #TAG_DOUBLE},
 * {@link #TAG_FLOAT}, {@link #TAG_INT}, {@link #TAG_LONG},
 * {@link #TAG_SHORT}, {@link #TAG_BOOLEAN} </td>
 * <td>The wrapper type of the respective primitive Java type. </td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_STRING}</td>
 * <td>{@link String} constant.</td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_CLASS}</td>
 * <td>{@link String} representing a Java type: either a fully qualified type
 * name, or the respective descriptor for <code>void</code>. Depending on the
 * creating site, representation format is either the standard Java format of
 * fully qualified names plus <code>'void'</code>, or the class file internal
 * format plus <code>'V'</code> <a href="#VM2+">[VMspec2+;&sect;4.4.3]</a>.
 * </td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_ENUM}</td>
 * <td>A {@link AnnotationRecord.EnumConstant} instance.</td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_ARRAY}</td>
 * <td>A {@link AnnotationRecord.NamedMember} array, where empty arrays are valid.
 * Empty arrays can be given both as <code>null</code> or as an empty array,
 * they are then represented by <code>null</code>. For non-empty arrays, no
 * array member must be <code>null</code>. </td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_ANNOTATION}</td>
 * <td>A (nested) {@link AnnotationRecord} instance.</td>
 * </tr>
 * </table>
 * 
 * @author Krasimir Topchiyski
 */
public interface NamedMember {
	
	/**
	  * Returns the name of this annotation member. It is a simple name in the
	  * sense of <a href="#L3">[Lang3;&sect;6.2]</a>.
	  *
	  * @return annotation member name
	  */
	String getName();
	  
	/**
	 * Indicates the type of this annotation member, one of the
	 * <code>TAG_&hellip;</code> constants.
	 * 
	 * @return member type discriminator
	 */
	int getMemberTag();

	/**
	 * MemberModel value, either the object wrapper of a primitive Java type, a
	 * {@link String}, a Java type name as {@link String}, a
	 * {@link AnnotationRecord.EnumConstant}, an array of tagged member values,
	 * or a nested {@link AnnotationRecord}, respectively. See <a
	 * href="#MemberFields">above</a> for details.
	 * <p>
	 * Except if explicitly supported by an implementation, neither the result
	 * nor any data (transitively) referenced from the result must be modified.
	 * 
	 * @return member value, never <code>null</code>
	 */
	Object getMemberValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>boolean</code> value.
	 * 
	 * @return <code>boolean</code> value of a boolean-valued member
	 */
	boolean getBooleanValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>byte</code> value.
	 * 
	 * @return <code>byte</code> value of a byte-valued member
	 */
	byte getByteValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>char</code> value.
	 * 
	 * @return <code>char</code> value of a character-valued member
	 */
	char getCharValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>double</code> value.
	 * 
	 * @return <code>double</code> value of a double-valued member
	 */
	double getDoubleValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>float</code> value.
	 * 
	 * @return <code>float</code> value of a float-valued member
	 */
	float getFloatValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>int</code> value.
	 * 
	 * @return <code>int</code> value of an integer-valued member
	 */
	int getIntValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>long</code> value.
	 * 
	 * @return <code>long</code> value of a long-valued member
	 */
	long getLongValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as <code>short</code> value.
	 * 
	 * @return <code>short</code> value of a short integer-valued member
	 */
	short getShortValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as {@link String} value. It can be invoked on tags
	 * {@link #TAG_STRING} and {@link #TAG_CLASS}.
	 * 
	 * @return {@link String} value of a string-valued member
	 */
	String getStringValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as {@link AnnotationRecord.EnumConstant} value.
	 * 
	 * @return {@link AnnotationRecord.EnumConstant} value of an member holding
	 *         an enumeration constant
	 */
	EnumConstant getEnumConstantValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as {@link AnnotationRecord} value.
	 * 
	 * @return {@link AnnotationRecord} value of an member holding an annotation
	 *         value
	 */
	AnnotationRecord getAnnotationValue();

	/**
	 * Convenience variant of {@link #getMemberValue()} that directly returns
	 * this member's value as {@link AnnotationRecord.NamedMember} array.
	 * <p>
	 * Except if explicitly supported by an implementation, neither the result
	 * array nor any data (transitively) referenced from the result must be
	 * modified.
	 * 
	 * @return {@link AnnotationRecord.NamedMember} array value of an member holding
	 *         an array of annotation members
	 */
	NamedMember[] getMemberArrayValue();

	/**
	 * Convenience function returning the length of this member's value if
	 * treated as {@link AnnotationRecord.NamedMember} array.
	 * 
	 * @return array length of an member holding an array of annotation members
	 */
	int getMemberArrayLength();

	/**
	 * Returns true if the annotation member value is taken from the defaults in
	 * the annotation class
	 * 
	 * @return true if it is a default value
	 */
	boolean isValueDefaulted();
}
