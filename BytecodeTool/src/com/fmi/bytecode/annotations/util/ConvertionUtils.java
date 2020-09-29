package com.fmi.bytecode.annotations.util;

import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 * @author Krasimir Topchiyski
 *
 */
public class ConvertionUtils {
	
	private static String getBasicTypeOrRef(String descriptor){
	    String bt = getBasicType(descriptor.charAt(0)); 
	    if(bt==null){
	    	if(descriptor.charAt(0)=='L'){
	    		return descriptor.substring(1, descriptor.length() - 1).replace('/', '.');
	    	} else {
	    		return null;
	    	}
	    } else {
	    	return bt;
	    }		
	}
	
	private static String getBasicType(char t){		
	    switch(t){
    		case 'B': return "byte";
    		case 'C': return "char";
	    	case 'D': return "double";
	    	case 'F': return "float";
	    	case 'I': return "int";
	    	case 'J': return "long";
	    	case 'S': return "short";
	    	case 'Z': return "boolean";
	    	case 'V': return "void";
	    	default: return null;
	    }
	}

	
	/**
	 * Converts byte code type to Java language standard type,
	 * as the output of the Class.getCanonicalName()
	 * @param descriptor type to be converted
	 * @return String with Java language standard type
	 */
	public static final String convertToCanonical(String descriptor) {	    
		int dimension = 0;
	    while (descriptor.startsWith("[")) {
	    	dimension++;
	    	descriptor = descriptor.substring(1);
	    }
	    StringBuilder buffer = new StringBuilder(descriptor.length());
	    String bt = getBasicTypeOrRef(descriptor); 
	    if(bt==null){
	    	throw new IllegalArgumentException("Invalid type definition detected in bytecode: " + descriptor);
	    } else {
	    	buffer.append(bt);
	    }
	    for (int i = 0; i < dimension; i++) {
	    	buffer.append("[]");
	    }
	    return buffer.toString();
	}

	
	/**
	 * Converts byte code type to Java language standard type
	 * @param descriptor type to be converted
	 * @return String with Java language standard type
	 * @throws ReadException 
	 */
	public static final String convertToJLS(String descriptor) {	    
		
		if(descriptor.charAt(0) == '['){
			return descriptor.replace('/', '.');
		}
		
		String t = getBasicTypeOrRef(descriptor);
		if(t==null){
			throw new IllegalArgumentException("Invalid type definition detected in bytecode: " + descriptor);
		}else{
			return t;
		}	    
	}
	
	/**
	 * Parses a byte code string with parameters
	 * @param signature to parse
	 * @return array of parsed types
	 */
	private static final String[] parseTokens(String signature){				
		ArrayList<Integer> separatorIndexes = new ArrayList<Integer>();
		for(int i=0;i<signature.length();i++){
			switch(signature.charAt(i)){				 
				case 'B': 
	    		case 'C': 
	    		case 'D': 
	    		case 'F': 
	    		case 'I': 
	    		case 'J': 
	    		case 'S': 
	    		case 'Z': 
			   				if((i==0) || (signature.charAt(i-1)!='[')){
			   					separatorIndexes.add(i);
			   				}
	    					break;
	    	   	case 'L': 
	    	   				if(i==0){
	    	   					separatorIndexes.add(0);
	    	   				} else {
	    	   					char c = signature.charAt(i-1);
	    	   					if((c!='[') && (c!='+') && (c!='-')){
	    	   						separatorIndexes.add(i);
	    	   					}
	    	   				}
	    	   				int context = 0;
	    	   				boolean condition = true;
	    	   				do{
	    	   					i++;
	    	   					if(signature.charAt(i)=='<'){
	    	   						context++;
	    	   					} else if(signature.charAt(i)=='>'){
	    	   						context--;
	    	   					}
	    	   					if(context!=0){ //inside generic declaration
	    	   						condition = true;
	    	   					} else { //check if closing ; is reached
	    	   						condition = (signature.charAt(i) != ';');
	    	   					}
	    	   				} while(condition);
							break;
	    	   	case 'T': 
	    	   				if(i==0){
	    	   					separatorIndexes.add(0);
	    	   				} else {
	    	   					char ct = signature.charAt(i-1);
	    	   					if((ct!='[') && (ct!='+') && (ct!='-')){
	    	   						separatorIndexes.add(i);
	    	   					}
	    	   				}
	    	   				do{
	    	   					i++;
	    	   				} while(signature.charAt(i) != ';');
	    	   				break;
							
	    	   	case '[':
	    	   				if(i==0){
	    	   					separatorIndexes.add(0);
	    	   				} else {
	    	   					char cc = signature.charAt(i-1);
	    	   					if((cc!='+') && (cc!='-')){
	    	   						separatorIndexes.add(i);
	    	   					}
	    	   				}
	    	   				do{
	    	   					i++;
	    	   				} while(signature.charAt(i) == '[');
	    	   				i--;//compensate the for i++
	    	   				break;
	    	   	case '*':
	    	   				separatorIndexes.add(i);	    	   				
	    	   				break;
	    	   	case '+':
	   						separatorIndexes.add(i);	   						
	   						break;
	    	   	case '-':
	   						separatorIndexes.add(i);	   						
	   						break;
			}
		}
		
		ArrayList<String> tokens = new ArrayList<String>();
		for(int j=0;j<separatorIndexes.size();j++){
			
			String token = null;
			if(j<(separatorIndexes.size()-1)){
				token = signature.substring(separatorIndexes.get(j), separatorIndexes.get(j+1));
			} else {
				token = signature.substring(separatorIndexes.get(j));
			}
			tokens.add(token);
		}
		
		return tokens.toArray(new String[tokens.size()]);
	}
	
	private static String parseSimpleClSignature(String sig){
				
		//check for generics
		int lg = sig.indexOf('<');
		if(lg != -1){
			StringBuilder buffer = new StringBuilder(sig.length());			
			buffer.append(sig.substring(0, lg+1).replace('/', '.'));
			String[] tks = parseTokens(sig.substring(lg+1, sig.length()-1));
			for(int i=0;i<tks.length;i++){				
				//check for wildcards
				String t = tks[i];
				char wc = t.charAt(0);
				int startIdx = 0;
				boolean wild = false;
				switch(wc){
					case '+': 
							startIdx++;
							buffer.append("? extends ");
							break;
					case '-':
							startIdx++;
							buffer.append("? super ");
							break;					 
					case '*':
							startIdx++;
							buffer.append("?");
							wild = true;
							break;							
				}
				if(!wild){
					buffer.append(parseFieldSignature(t.substring(startIdx)));
				}	
				if(i!=(tks.length-1)){
					buffer.append(",");
				}
			}
			buffer.append(">");
			return buffer.toString();
		} else {
			return sig.replace('/', '.');
		}
		
	}	
	
	/**
	 * Parses a field signature attribute to JLS format
	 * @param sig signature attribute
	 * @return the JLS result
	 */
	public static String parseFieldSignature(String sig){
		
		//determine array size
		int dimension = 0;
	    while (sig.startsWith("[")) {
	    	dimension++;
	    	sig = sig.substring(1);
	    }
	    
	    //remove the terminator symbol
	    if(sig.charAt(sig.length()-1)==';'){
	    	sig = sig.substring(0, sig.length()-1);
	    }
	    
	    char c = sig.charAt(0);
	    sig = sig.substring(1);
	    
	    //check for basic type
	    String bType = getBasicType(c);
	    
	    StringBuilder buffer = new StringBuilder(sig.length());
	    if(bType!=null){
	    	buffer.append(bType);
	    } else {
	    	//check for reference or param type
	    	if((c == 'T') || (c == 'L')){
	    		//check for inner class
	    		int in = sig.indexOf('.');
	    		if(in != -1){
	    			String main = sig.substring(0, in);
	    			buffer.append(parseSimpleClSignature(main));
	    			
	    			StringTokenizer st = new StringTokenizer(sig.substring(in, sig.length()),".");
	    			while(st.hasMoreTokens()){
	    				buffer.append(".");	    				
	    				buffer.append(parseSimpleClSignature(st.nextToken()));
	    			}
	    		} else {
	    			buffer.append(parseSimpleClSignature(sig));
	    		}
	    	}
	    }
	    
	    //append array demension
	    for (int i = 0; i < dimension; i++) {
	    	buffer.append("[]");
	    }
	    
		return buffer.toString();		
	}

	/**
	 * Parses the return type to JLS format, out of a method signature attribute 
	 * @param sig signature attribute
	 * @return the JLS result
	 */
	public static String getMethodReturnType(String signature){
		int si = signature.lastIndexOf(')');
		int ei = signature.indexOf('^');
		String rt = null;
		if(ei!=-1){
			rt = signature.substring(si+1, ei);
		} else {
			rt = signature.substring(si+1);
		}		
		if(isGenericType(rt)){
			return parseFieldSignature(rt);
		} else {
			return null;
		}
	}

	private static boolean isGenericType(String descriptor){
		if(descriptor.indexOf('<')!=-1){
			return true;
		} else {
			while(descriptor.startsWith("[")){
				descriptor = descriptor.substring(1);
			}
			if(descriptor.startsWith("T")){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Parses the method params to JLS format, out of a method signature attribute 
	 * @param sig signature attribute
	 * @return the JLS result
	 */	
	public static String[] getMethodParams(String signature, boolean[] flags){
		int si = signature.indexOf('(');
		int ei = signature.indexOf(')');
		String[] params = parseTokens(signature.substring(si+1, ei));		
		ArrayList<String> p = new ArrayList<String>();
		for(int i=0;i<params.length;i++){
			String t = params[i];
			if(isGenericType(t)){
				flags[i] = true;
			}
			p.add(parseFieldSignature(t));			
		}
		return p.toArray(new String[0]);
	}

}
