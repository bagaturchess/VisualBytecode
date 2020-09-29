package com.fmi.bytecode.annotations.input;

import com.fmi.bytecode.annotations.tool.ReadingException;
import com.fmi.bytecode.annotations.tool.impl.ClassInfoReaderImpl;

public final class ByteCodeReaderFactory {
	
	private static final String DEFAULT_BYTECODE_READER_CLASSNAME = "com.fmi.bytecode.annotations.input.adapter.ByteCodeReaderImpl";
	
	public static final ByteCodeReader createByteCodeReader() throws Exception {
		return createByteCodeReader(DEFAULT_BYTECODE_READER_CLASSNAME);
	}
	
	public static final ByteCodeReader createByteCodeReader(String byteCodeReaderClassName) throws Exception {
		ByteCodeReader result;			
		if (byteCodeReaderClassName == null) {
			throw new ReadingException("ByteCodeReader class name is null.");
		}
		Class bcReaderClass = ClassInfoReaderImpl.class.getClassLoader().loadClass(byteCodeReaderClassName);
		result = (ByteCodeReader) bcReaderClass.newInstance();
		return result;
	}
}
