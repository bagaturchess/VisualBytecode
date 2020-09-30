package com.fmi.bytecode.annotations.tool.tool;

import com.fmi.bytecode.annotations.input.ByteCodeReader;
import com.fmi.bytecode.annotations.input.ByteCodeReaderFactory;
import com.fmi.bytecode.annotations.tool.tool.impl.ClassInfoReaderImpl;

/**
 * The <code>AnnotationsReaderFactory</code> class has to provide the factory of
 * byte code information readers.
 *
 * @author Krasimir Topchiyski
 */
public class AnnotationsReaderFactory {
	/**
	 * Gets an initialized ClassInfoReader instance, ready for usage
	 * @see com.fmi.bytecode.annotations.tool.tool.AnnotationsReaderFactory#getReader()
	 */
	public static ClassInfoReader getReader() {
		ByteCodeReader bcReader = ByteCodeReaderFactory.createByteCodeReader();
		return new ClassInfoReaderImpl(bcReader);
	}
}
