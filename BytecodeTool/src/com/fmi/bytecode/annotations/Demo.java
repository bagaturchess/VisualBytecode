package com.fmi.bytecode.annotations;


import java.io.File;
import java.util.Map;

import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.tool.AnnotationsReaderFactory;
import com.fmi.bytecode.annotations.tool.ClassInfoReader;
import com.fmi.bytecode.annotations.tool.ReadResult;


public class Demo {
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			printHelp();
			System.exit(-1);
		}
		File input = new File(args[0]);
		if (!input.exists()) {
			printHelp();
			System.exit(-1);
		}
		try {
			ClassInfoReader cif = AnnotationsReaderFactory.getReader();
			ReadResult readResult = cif.read(new File[]{input});
			Map<String,ClassInfo> classes = readResult.getClasses();
			for (String className: classes.keySet()) {
				ClassInfo clazz = classes.get(className);
				System.out.println(clazz);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static void printHelp() {
		String helpMessage = "";
		helpMessage += "Usage: \r\n";
		helpMessage += "	Demo <input-file>\r\n";
		helpMessage += "The <input-file> param may be class file, directory, jar or war file.\r\n";
		System.out.println(helpMessage);
	}
}
