package com.fmi.bytecode.annotations.input;

import java.io.InputStream;

import com.fmi.bytecode.annotations.tool.ReadingException;

public interface ByteCodeReader {

	public ClassModel parseClassInformation(InputStream bytecode)
		throws ReadingException;
}
