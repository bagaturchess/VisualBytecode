package com.fmi.bytecode.annotations.input;


import java.io.InputStream;


public interface ByteCodeReader {
	public ClassModel parseClassInformation(InputStream bytecode) throws ReadingException;
}
