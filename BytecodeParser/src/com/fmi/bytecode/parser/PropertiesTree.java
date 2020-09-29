// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropertiesTree.java

package com.fmi.bytecode.parser;

import java.io.PrintStream;
import java.util.*;

// Referenced classes of package com.sap.engine.library.bytecode.misc:
//            StringUtils

public final class PropertiesTree
{

    public PropertiesTree()
    {
        mapNameToPathsAndValues = new TreeMap();
    }

    public void put(String path[], String name, Object value)
    {
        ArrayList list = (ArrayList)(ArrayList)mapNameToPathsAndValues.get(name);
        if(list == null)
        {
            list = new ArrayList();
            mapNameToPathsAndValues.put(name, list);
        }
        int nList = list.size();
        for(int i = 0; i < nList; i++)
        {
            Object pathValuePair[] = (Object[])list.get(i);
            if(pathValuePair[0].equals(path))
            {
                pathValuePair[1] = value;
                return;
            }
        }

        list.add(((Object) (new Object[] {
            path, value
        })));
    }

    public Object get(String path[], String name)
    {
        ArrayList list = (ArrayList)(ArrayList)mapNameToPathsAndValues.get(name);
        if(list == null)
            return null;
        int nList = list.size();
        int bestPathLength = -1;
        Object bestValue = null;
        for(int i = 0; i < nList; i++)
        {
            Object pathValuePair[] = (Object[])list.get(i);
            String currentPath[] = (String[])pathValuePair[0];
            if(StringUtils.startsWith(path, currentPath) && currentPath.length > bestPathLength)
            {
                bestPathLength = currentPath.length;
                bestValue = pathValuePair[1];
            }
        }

        return bestValue;
    }

    public boolean hasDescendants(String path[])
    {
        for(Iterator it = mapNameToPathsAndValues.values().iterator(); it.hasNext();)
        {
            ArrayList list = (ArrayList)(ArrayList)it.next();
            int nList = list.size();
            for(int i = 0; i < nList; i++)
            {
                Object pathValuePair[] = (Object[])list.get(i);
                String currentPath[] = (String[])pathValuePair[0];
                if(StringUtils.startsWith(currentPath, path))
                    return true;
            }

        }

        return false;
    }

    public static void main(String args[])
        throws Exception
    {
        PropertiesTree t = new PropertiesTree();
        t.put(StringUtils.split("a/b/c", '/'), "name", "value0");
        t.put(StringUtils.split("a/b/c/d", '/'), "name", "value1");
        System.out.println(t.get(StringUtils.split("a/b/c/d/.dssadf/asd.fasd/adfa/d", '/'), "name"));
    }

    private Map mapNameToPathsAndValues;
}
