package com.fmi.bytecode.annotations.input.adapter;


import java.util.Arrays;
import java.util.List;


/**
 *
 * @author Krasimir Topchiyski
 */
final class ComparisonUtils {
    
    public static <T> boolean compareObjects(T s1, T s2) {
        if((s1==null) && (s2==null)){
            return true;
        }  
        if((s1==null) || (s2==null)){
            return false;
        }  
        return s1.equals(s2);
    }

    public static <T> boolean compareUnorderedArrays(T[] a1, T[] a2) {
        if((a1==null) && (a2==null)){
            return true;
        }  
        if((a1==null) || (a2==null)){
            return false;
        }  
  
        if (a1.length != a2.length) {
            return false;
        }
        
        List<T> l1 = Arrays.asList(a1);
        List<T> l2 = Arrays.asList(a2);        
        return l1.containsAll(l2);        
    }

    public static <T> boolean compareOrderedArrays(T[] a1, T[] a2) {
        if((a1==null) && (a2==null)){
            return true;
        }  
        if((a1==null) || (a2==null)){
            return false;
        }  
  
        if (a1.length != a2.length) {
            return false;
        }
        
        for(int i=0;i<a1.length;i++){
            if(!a1[i].equals(a2[i])){
                return false;
            }
        }
        
        return true;
    }
}
