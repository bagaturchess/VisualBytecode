// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TreeFactory.java

package com.fmi.bytecode.annotations.tool.indexing;

import com.fmi.bytecode.annotations.element.ClassInfo;
import java.io.PrintStream;
import java.util.*;

// Referenced classes of package com.fmi.bytecode.annotations.tool.indexing:
//            AnnotationClassNode, AnnotationPackageNode

public class TreeFactory
{

    public TreeFactory()
    {
    }

    static final Object[] createTree(Map classes)
    {
        Object result[] = new Object[2];
        List packageRootsList = new ArrayList();
        Map packageRootsByName = new HashMap();
        List classesRootsList = new ArrayList();
        Map classesRootsByName = new HashMap();
        for(Iterator iterator = classes.keySet().iterator(); iterator.hasNext();)
        {
            String classFullName = (String)iterator.next();
            String nameAndPackage[] = separateNameAndPackage(classFullName);
            String className = nameAndPackage[0];
            String classPackage = nameAndPackage[1];
            ClassInfo classInfo = (ClassInfo)classes.get(classFullName);
            if(classPackage == null)
            {
                AnnotationClassNode cn = new AnnotationClassNode(classInfo, null, className);
                classesRootsList.add(cn);
                classesRootsByName.put(cn.getName(), cn);
            } else
            {
                StringTokenizer st = new StringTokenizer(classPackage, ".");
                String first = st.nextToken();
                AnnotationPackageNode existingRoot = (AnnotationPackageNode)packageRootsByName.get(first);
                if(existingRoot == null)
                {
                    existingRoot = new AnnotationPackageNode(null, first);
                    packageRootsList.add(existingRoot);
                    packageRootsByName.put(existingRoot.getName(), existingRoot);
                }
                addSequence(existingRoot, st, className, classInfo);
            }
        }

        result[0] = classesRootsList;
        result[1] = packageRootsList;
        return result;
    }

    private static void addSequence(AnnotationPackageNode root, StringTokenizer packageParts, String className, ClassInfo classInfo)
    {
        AnnotationPackageNode last;
        AnnotationPackageNode existingNode;
        for(last = root; packageParts.hasMoreTokens(); last = existingNode)
        {
            String currPackagePart = packageParts.nextToken();
            existingNode = last.getSubPackageByName(currPackagePart);
            if(existingNode == null)
            {
                existingNode = new AnnotationPackageNode(null, currPackagePart);
                last.addPackageChild(existingNode);
            }
        }

        new AnnotationClassNode(classInfo, last, className);
    }

    private static String[] separateNameAndPackage(String classFullName)
    {
        String nameAndPackage[] = new String[2];
        String className = classFullName;
        String classPackage = null;
        int lastDot = className.lastIndexOf(".");
        if(lastDot != -1)
        {
            className = classFullName.substring(lastDot + 1);
            classPackage = classFullName.substring(0, lastDot);
        }
        nameAndPackage[0] = className;
        nameAndPackage[1] = classPackage;
        return nameAndPackage;
    }

    public static void main(String args[])
    {
        String t[] = separateNameAndPackage("rosi.krasi.Mechka");
        System.out.println(t[0]);
        System.out.println(t[1]);
    }
}
