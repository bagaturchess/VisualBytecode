package com.fmi.bytecode.annotations.element;


/**
 * Represents an enumeration constant occurring as annotation value.
 * The concrete member type is indicated by a tag value, using the same
 * encoding as in the class file.
 * <p>
 * Reference documentation is <i>Chapter 4: The class File Format</i> of the
 * Java Virtual Machine Specification, 2<sup>nd</sup> edition, as extended in
 * <a href="http://jcp.org/en/jsr/detail?id=924">JSR-924</a>
 * for the JSE 5.0 release,
 * <a name="VM2+"><a href="http://java.sun.com/docs/books/vmspec/2nd-edition/ClassFileFormat-Java5.pdf">[VMspec2+;&sect;4]</a></a>.
 * <p>
 * This implementation was developed on J2SE 1.4, it should run on any other
 * JSE version, too.  
 *  
 * @author Krasimir Topchiyski
 */
public interface EnumConstant
{
  /**
   * Returns the fully qualified name of the enumeration type.
   * Depending on the site creating this instance, the type name is either
   * in the standard Java format, or in the internal binary format used in
   * class files <a href="VM2+">[VMspec2+;&sect;4.3.1]</a>.
   *
   * @return enumeration type's name
   */
  String getEnumerationTypeName();
  /**
   * Returns the represented enumeration constant, a simple, unqualified
   * name.
   * @return enumeration literal
   */
  String getEnumerationLiteral();
}