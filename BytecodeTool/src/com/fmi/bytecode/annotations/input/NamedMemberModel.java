package com.fmi.bytecode.annotations.input;


/**
 * Represents one of the name/value pairs making up an annotation value.
 * <p>
 * Reference documentation used herein is the Java language specification <a
 * name="L3"><a
 * href="http://java.sun.com/docs/books/jls/third_edition/html/j3TOC.html">[Lang3]</a></a>.
 * <p/>
 * Discriminated union representing a single annotation member value. The
 * concrete member type is indicated by a tag value, using the same encoding
 * as in the class file.
 * <p>
 * Relevant reference documentation is <i>Chapter 4: The class File Format</i>
 * of the Java Virtual Machine Specification, 2<sup>nd</sup> edition, as
 * extended in <a href="http://jcp.org/en/jsr/detail?id=924">JSR-924</a>
 * for the JSE 5.0 release, <a name="VM2+"><a
 * href="http://java.sun.com/docs/books/vmspec/2nd-edition/ClassFileFormat-Java5.pdf">[VMspec2+;&sect;4]</a></a>.
 * 
 * <a name="MemberFields">
 * <h4>Annotation NamedMemberModel Representation</h4>
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
 * <td>{@link String} representing a Java type: either a fully qualified
 * type name, or the respective descriptor for <code>void</code>.
 * Depending on the creating site, representation format is either the
 * standard Java format of fully qualified names plus <code>'void'</code>,
 * or the class file internal format plus <code>'V'</code> <a
 * href="#VM2+">[VMspec2+;&sect;4.4.3]</a>. </td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_ENUM}</td>
 * <td>A {@link AnnotationModel.EnumTypeModel} instance.</td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_ARRAY}</td>
 * <td>A {@link AnnotationModel.NamedMemberModel} array, where empty arrays are
 * valid. Empty arrays can be given both as <code>null</code> or as an
 * empty array, they are then represented by <code>null</code>. For
 * non-empty arrays, no array member must be <code>null</code>. </td>
 * </tr>
 * <tr valign=top class=TableSubHeadingColor>
 * <td>{@link #TAG_ANNOTATION}</td>
 * <td>A (nested) {@link AnnotationModel} instance.</td>
 * </tr>
 * </table>
 * 
 * @author Krasimir Topchiyski
 */
public interface NamedMemberModel {
	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'byte'
	 */
	static final int TAG_BYTE = 'B';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'char'
	 */
	static final int TAG_CHAR = 'C';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'double'
	 */
	static final int TAG_DOUBLE = 'D';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'float'
	 */
	static final int TAG_FLOAT = 'F';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'int'
	 */
	static final int TAG_INT = 'I';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'long'
	 */
	static final int TAG_LONG = 'J';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'short'
	 */
	static final int TAG_SHORT = 'S';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1,4.4.2]</a>: annotation
	 * member type tag 'boolean'
	 */
	static final int TAG_BOOLEAN = 'Z';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1]</a>: annotation member
	 * type tag 'String'
	 */
	static final int TAG_STRING = 's';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1]</a>: annotation member
	 * type tag 'enumeration constant'
	 */
	static final int TAG_ENUM = 'e';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1]</a>: annotation member
	 * type tag 'class'
	 */
	static final int TAG_CLASS = 'c';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1]</a>: annotation member
	 * type tag '(nested) annotation'
	 */
	static final int TAG_ANNOTATION = '@';

	/**
	 * <a href="#VM2+">[VMspec2+;&sect;4.8.15.1]</a>: annotation member
	 * type tag 'array'
	 */
	static final int TAG_ARRAY = '[';

	/**
	 * L<classname>; - reference an instance of class <classname>
	 */
	static final int TAG_REF	= 'L';
	
	/**
	 * Returns the name of this annotation member. It is a simple name in
	 * the sense of <a href="#L3">[Lang3;&sect;6.2]</a>.
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
	 * NamedMemberModel value, either the object wrapper of a primitive Java type, a
	 * {@link String}, a Java type name as {@link String}, a
	 * {@link AnnotationModel.EnumTypeModel}, an array of tagged member
	 * values, or a nested {@link AnnotationModel}, respectively. See <a
	 * href="#MemberFields">above</a> for details.
	 * <p>
	 * Except if explicitly supported by an implementation, neither the
	 * result nor any data (transitively) referenced from the result must be
	 * modified.
	 * 
	 * @return member value, never <code>null</code>
	 */
	Object getMemberValue();
}
