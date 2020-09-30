package com.fmi.bytecode.annotations.tool.element;

import java.util.Map;


/**
 * Interface defining the representation API of an annotation value.
 * Purpose of this API is to directly represent the information content as
 * present on class file level, not to replace the reflection capabilities of
 * JSE 5.0 and beyond. Thus, this and associated types describe mere data
 * records that do not link into the type store of a running Java VM.
 * <p>
 * Annotation value representation is straightforward, provided the concepts
 * of annotation values are known. This interface does not provide documentation
 * on usage or meaning of annotations in general, or on semantics of specific
 * annotation types in particular.<p>
 * Types are denoted by fully qualified Java type names.
 * <p>
 * Implementations of this interface shall be thread-safe.
 * <p>
 * Annotation values are to be treated as immutable. This applies to the value
 * as a whole as to any sub-part of it.
 * Implementations are free to decide on the effort spent for technically
 * securing immutability.
 * <p>
 * Except if explicitly supported and documented by an implementation, an
 * annotation value representation is homogeneous in implementation type and
 * a single value shall not be made up of a mixture of implementation types
 * for the same interface type.
 * <p>
 * Reference documentation is <i>Chapter 4: The class File Format</i> of the
 * Java Virtual Machine Specification, 2<sup>nd</sup> edition, as extended in
 * <a href="http://jcp.org/en/jsr/detail?id=924">JSR-924</a>
 * for the JSE 5.0 release,
 * <a name="VM2+"><a href="http://java.sun.com/docs/books/vmspec/2nd-edition/ClassFileFormat-Java5.pdf">[VMspec2+;&sect;4]</a></a>.
 *
 * @author Krasimir Topchiyski
 */
public interface AnnotationRecord extends ClassMemberInfo
{	
  /**
   * Returns the annotation value's type name. It is a fully qualified Java
   * type.
   *
   * @return annotation's type name
   */
  String getTypeName();
  /**
   * Returns whether this annotation value is classified as
   * <i>'runtime visible'</i>.
   * @return <code>true</code> iff annotation value is visible at runtime
   */
  boolean isRuntimeVisible();

  /**
   * Returns map of all named members where the key is the member name
   * @return map of all named members 
   */
  Map<String, NamedMember> getNamedMembersMap();
  
  /**
   * Returns the number of name/value pairs making up this annotation value.
   * @return number of name/value pairs
   */
  int getMembersLength();

  /**
   * Returns the name/value pair of an annotation member of a given name.
   * The result is <code>null</code> if there is no such member.
   * <p>
   * Neither the result nor any data (transitively) referenced from the result
   * must be modified.
   *
   * @param idx member index
   * @return member with given name, or <code>null</code>
   */
  NamedMember getMember( String name );
}
