package com.fmi.bytecode.annotations.input;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;


public final class ByteCodeReaderFactory {
	
	
	public static final ByteCodeReader createByteCodeReader() throws Exception {
		return new com.fmi.bytecode.annotations.input.adapter.ByteCodeReaderImpl();
	}
	
	
	public static void main(String[] args) {
		try {
			ByteCodeReader bcr = createByteCodeReader();
			InputStream is = new FileInputStream(new File("/com/fmi/bytecode/annotations/input/ByteCodeReaderFactory.class"));
			
			ClassModel cm = bcr.parseClassInformation(is);
			
			System.out.println(cm.getAccessFlags());
			System.out.println(cm.getName());
			System.out.println(cm.getPackageName());
			System.out.println(cm.getSuperClassName());
			System.out.println(cm.getAnnotations());
			System.out.println(cm.getClass());
			System.out.println(cm.getConstructors());
			System.out.println(cm.getFields());
			System.out.println(cm.getInterfaceNames());
			System.out.println(cm.getMethods());
			
			throw new ReadingException("test");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
