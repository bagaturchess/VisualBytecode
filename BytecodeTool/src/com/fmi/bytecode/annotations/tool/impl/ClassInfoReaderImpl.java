package com.fmi.bytecode.annotations.tool.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.fmi.bytecode.annotations.element.AnnotationRecord;
import com.fmi.bytecode.annotations.element.ClassInfo;
import com.fmi.bytecode.annotations.element.impl.ClassInfoImpl;
import com.fmi.bytecode.annotations.file.FileInfo;
import com.fmi.bytecode.annotations.file.impl.FolderInfoImpl;
import com.fmi.bytecode.annotations.file.impl.JARFileImpl;
import com.fmi.bytecode.annotations.file.impl.JavaClassFileImpl;
import com.fmi.bytecode.annotations.file.impl.WARFileImpl;
import com.fmi.bytecode.annotations.input.ByteCodeReader;
import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.annotations.tool.ClassInfoReader;
import com.fmi.bytecode.annotations.tool.ReadResult;
import com.fmi.bytecode.annotations.tool.ReadingException;

/**
 * @author Krasimir Topchiyski
 *
 */
public class ClassInfoReaderImpl implements ClassInfoReader {
	
	private static final String EXTRACTED_WAR_FILE_EXTENSION = ".war";
	private static final String JAR_FILE_EXTENSION = ".jar"; 
	private static final String ZIP_FILE_EXTENSION = ".zip";
	private static final String CLASS_FILE_EXTENSION = ".class";
	
	private static ByteCodeReader bcReader;
	
	
	public ClassInfoReaderImpl(ByteCodeReader _bcReader) {
		bcReader = _bcReader;
	}

	/**
	 * This method is used mostly for tests.
	 * It uses InputStreams as an input.
	 * It requires all streams to be from class files
	 * @param classesStreams array of class InputStreams
	 * @return result information tree
	 * @throws ReadingException if there is any parsing problem
	 */
	/*public ReadResult read(InputStream[] classesStreams) throws ReadingException {
		
		ReadResultImpl result = new ReadResultImpl();
	
		for(InputStream is: classesStreams){
			traverseClassStream(is, result);
		}
		
		return result;
	}*/

	/**
	 * Extracts information out of an array of class, JAR, WAR files and folders
	 * @param an array of JAR,WAR,CLASS files and folders 
	 * @return result information tree
	 * @throws ReadingException if there is any processing problem
	 */
	public ReadResult read(File[] filesToTraverse) throws ReadingException {
		
		checkInput(filesToTraverse);
	
		ReadResultImpl result = new ReadResultImpl();
	
		readInternal(result, filesToTraverse, "", null);
		
		return result;
	}
	
	private boolean isUnpackedWar(File folder){
		if(folder.getName().endsWith(EXTRACTED_WAR_FILE_EXTENSION)) {
			File[] files = folder.listFiles();
			for(File f: files) {
				if(f.isDirectory() && f.getName().equalsIgnoreCase("WEB-INF")) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void checkInput(File[] filesToTraverse) throws ReadingException {		
		for(File f: filesToTraverse){
			boolean isSupported = f.isDirectory()					
					|| f.getName().endsWith(JAR_FILE_EXTENSION)
					|| f.getName().endsWith(CLASS_FILE_EXTENSION)
					|| f.getName().endsWith(ZIP_FILE_EXTENSION);
			if(!isSupported){
				throw new ReadingException("The file: "+f.getName()+" is not a folder, JAR or CLASS file! Only these Files are supported by the tool!");
			}
		}
	}	
	
	//This method is used outside in the ReadResult for the external classes provided through ClassFinder interface
	/*public static ClassInfo traverseClassStream(InputStream is, ReadResultImpl result) throws ReadingException {
				
  	    logMessage("Start parsing of class stream");

		ClassInfoImpl c = parseClassInformation(is, result);
		
		logMessage("Parsing of class stream finished successfully");

		//add external info to the ClassInfo
		result.addClass(c);
		
		//add the class level annotations to the result indexes
		//for(AnnotationRecord ann: c.getAnnotations().values()){
		//	result.addClassLevelAnnotation(ann);
		//}
		
		return c;

	}*/

	private void readInternal(ReadResultImpl rr, File[] files, String relPath, FileInfo parent) throws ReadingException {
		for(File f: files){
			if(f.isDirectory()) {
				traverseFolder(f, rr, relPath, parent);
			} else if(f.getName().endsWith(JAR_FILE_EXTENSION) || f.getName().endsWith(ZIP_FILE_EXTENSION)) {
				traverseZipFile(f,rr,relPath,parent);
			} else if(f.getName().endsWith(CLASS_FILE_EXTENSION)) {
				traverseClassFile(f,rr,relPath,parent);
			}
		}		
	}
	
	private void traverseFolder(File f, ReadResultImpl rr, String relPath, FileInfo parent) throws ReadingException {
		
		File[] fs = f.listFiles();
		if((fs != null) && (fs.length>0)) {
			//unpacked WAR case
			if(isUnpackedWar(f)){
				traverseWarFile(f,rr,relPath,parent);
			} else if(parent == null) { //root folder case
				FolderInfoImpl fi = new FolderInfoImpl();
				fi.setName(f.getName());
				fi.setParent(null);
				fi.setRelativePath("");				
				fi.setFullPath(f.getAbsolutePath());
				rr.addProcessedFile(fi);
				readInternal(rr, fs, File.separator+f.getName(), fi);
			} else { //subfolder case
				readInternal(rr, fs, relPath+File.separator+f.getName(), parent);
			}
		}

	}
	
	private void traverseWarFile(File file, ReadResultImpl result, String relPath, FileInfo parent) throws ReadingException {
		
		File[] fs = file.listFiles();
		if((fs != null) && (fs.length>0)) {
			WARFileImpl wf = new WARFileImpl();
			wf.setParent(parent);
			wf.setName(file.getName());
			wf.setRelativePath(relPath+File.separator+file.getName());	
			wf.setFullPath(file.getAbsolutePath());
			if(parent!=null) {
				((FolderInfoImpl)parent).addFile(wf);
			} else {
				result.addProcessedFile(wf);
			}			
			//constructs WAR file relative path in the format:  <WAR>!\<rel path>\filename
			readInternal(result, fs, file.getName()+"!",wf);
		}
	}
	
	private void traverseClassFile(File file, ReadResultImpl result, String relPath, FileInfo parent) throws ReadingException {
		
		//create class file info object 
		JavaClassFileImpl classFile = new JavaClassFileImpl();
		classFile.setName(file.getName());
		classFile.setParent(parent);
		classFile.setRelativePath(relPath+File.separator+file.getName());
		classFile.setFullPath(file.getAbsolutePath());

		//add file to its parent
		if(parent!=null){
			((WARFileImpl)parent).addFile(classFile);
		} else { //add file to the result			
			result.addProcessedFile(classFile);	
		}
		
		InputStream classStream = null;
		ClassInfoImpl c = null;
		try{
			classStream = new FileInputStream(file);
			
	  	    logMessage("Start parsing of class ["+file.getAbsolutePath()+"]");
	  	    
			c = parseClassInformation(classStream, result);

			logMessage("Parsing of class ["+file.getAbsolutePath()+"] finished successfully");
			
		} catch (FileNotFoundException fe) {
			throw new ReadingException(fe);
		} finally {			
			try {
				if(classStream!=null) {						
					classStream.close();
				}
			} catch (IOException ioe) {
				logException(ioe);
				logMessage("Can not close handle for stream: " + classStream);
			}	
		}
		
		//add external info to the ClassInfo
		c.addContainingFile(classFile);
							
		//add the class to the result indexes
		classFile.setClazz(c);
		result.addClass(c);
		
		//add the class level annotations to the result indexes
		/*for(AnnotationRecord ann: c.getAnnotations().values()){
			result.addClassLevelAnnotation(ann);
		}*/
	}
	
	private void traverseZipFile(File file, ReadResultImpl result, String relPath, FileInfo parent) throws ReadingException {
		
		//create jar or zip file info object 
		JARFileImpl jarFileInfo = new JARFileImpl();
		jarFileInfo.setName(file.getName());
		jarFileInfo.setParent(parent);
		jarFileInfo.setRelativePath(relPath+File.separator+file.getName());
		jarFileInfo.setFullPath(file.getAbsolutePath());
		//add processed file to the parent
		if(parent!=null){
			((WARFileImpl)parent).addFile(jarFileInfo);
		} else { //add processed jar or zip file to the result
			result.addProcessedFile(jarFileInfo);			
		}
		
		//traverses the jar/zip file and fills the result
		ZipFile zip = null;
		try {
			zip = new ZipFile(file);				
			Enumeration<? extends ZipEntry> entries = zip.entries();				
			if(entries!=null){
				while(entries.hasMoreElements()){
					ZipEntry e = entries.nextElement();
					if(!isClassFileEntry(e)){
						continue;
					}
					
					//parses the bytecode for the class
					InputStream entryStream = zip.getInputStream(e);
					ClassInfoImpl c = null;
					try {
				  	    logMessage("Start parsing of class ["+e.getName()+"] in file ["+file.getAbsolutePath()+"]");
				  	    
						c = parseClassInformation(entryStream, result);
				  	    
						logMessage("Parsing of class ["+e.getName()+"] in file ["+file.getAbsolutePath()+"] finished successfully");
				  	    

					} finally {
						entryStream.close();
					}
					
					//add external info to the ClassInfo
					c.addContainingFile(jarFileInfo);
					
					//add the class to the result indexes
					jarFileInfo.addClass(c);
					result.addClass(c);
					
					//add the class level annotations to the result indexes
					/*for(AnnotationRecord ann: c.getAnnotations().values()){						
						result.addClassLevelAnnotation(ann);
					}*/
				}
			}
		} catch (IOException e) {			
			throw new ReadingException(e);
		} finally {
			try {
				if(zip!=null){
					zip.close();
				}
			} catch (IOException e) {
				logException(e);
				logMessage("Can not close handle for file: " + zip.getName());
			}
		}
	}
	
	private boolean isClassFileEntry(ZipEntry entry) {
		if(entry.isDirectory()){
			return false;
		}
		return entry.getName().endsWith(CLASS_FILE_EXTENSION);
	}

	private static ClassInfoImpl parseClassInformation(InputStream bytecode, ReadResult result) throws ReadingException {
		ClassModel cm = bcReader.parseClassInformation(bytecode);
		return new ClassInfoImpl(cm, result);
	}

	
	public static final void logMessage(String message) {
		getLogStream().println(message);
	}

	public static final void logException(Throwable t) {
		t.printStackTrace(getLogStream());
	}
	
	private static final PrintStream getLogStream() {
		return System.out;
	}
}
