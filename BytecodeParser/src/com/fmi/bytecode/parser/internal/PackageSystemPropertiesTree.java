package com.fmi.bytecode.parser.internal;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;


public final class PackageSystemPropertiesTree
{

    public PackageSystemPropertiesTree()
    {
        tree = new PropertiesTree();
        allPackageSystemIdentifiers = new ArrayList();
        hPathOfLabelsCache = new HashMap();
    }

    public PackageSystemPropertiesTree(Properties p)
    {
        this();
        for(Enumeration _enum = p.propertyNames(); _enum.hasMoreElements();)
        {
            String path = (String)_enum.nextElement();
            String nameValuePairs = p.getProperty(path);
            String a[] = StringUtils.splitAndTrim(nameValuePairs, ',');
            for(int i = 0; i < a.length; i++)
            {
                int indexOfEquals = a[i].indexOf('=');
                String name;
                String value;
                if(indexOfEquals == -1)
                {
                    name = a[i];
                    value = "";
                } else
                {
                    name = a[i].substring(0, indexOfEquals).trim();
                    value = a[i].substring(indexOfEquals + 1).trim();
                }
                put(path, name, value);
            }

        }

    }

    public void put(String packageSystemIdentifier, String propertyName, Object propertyValue)
    {
        String path[] = getPathOfLabels(packageSystemIdentifier);
        tree.put(path, propertyName, propertyValue);
        allPackageSystemIdentifiers.add(packageSystemIdentifier);
    }

    public Object get(String packageSystemIdentifier, String propertyName)
    {
        String path[] = getPathOfLabels(packageSystemIdentifier);
        return tree.get(path, propertyName);
    }

    public boolean hasDescendants(String packageSystemIdentifier)
    {
        String path[] = getPathOfLabels(packageSystemIdentifier);
        return tree.hasDescendants(path);
    }

    public String[] getAllPackageSystemIdentifiers()
    {
        return (String[])allPackageSystemIdentifiers.toArray(new String[allPackageSystemIdentifiers.size()]);
    }

    private String[] getPathOfLabels(String packageSystemIdentifier)
    {
        String r[] = (String[])hPathOfLabelsCache.get(packageSystemIdentifier);
        if(r == null)
        {
            r = StringUtils.packageSystemIdentifierToPathOfLabels(packageSystemIdentifier);
            hPathOfLabelsCache.put(packageSystemIdentifier, r);
        }
        return r;
    }

    /**
     * @deprecated Method checkAllPackageSystemIdentifiersExist is deprecated
     */

    public void checkAllPackageSystemIdentifiersExist(URL baseURL)
        throws IOException
    {
        String identifiers[] = getAllPackageSystemIdentifiers();
        for(int i = 0; i < identifiers.length; i++)
        {
            String path[] = getPathOfLabels(identifiers[i]);
            if(path.length != 0)
            {
                String s = path[path.length - 1];
                if(s.startsWith("{class}"))
                {
                    String className = identifiers[i];
                    URL url = new URL(baseURL, (new StringBuilder(String.valueOf(className))).append(".class").toString());
                    CFFactory.getThreadLocalInstance().createCFParser().parse(url.openStream(), null);
                } else
                if(s.startsWith("{method}"))
                {
                    int indexOfDot = identifiers[i].indexOf('.');
                    String className = identifiers[i].substring(0, indexOfDot);
                    URL url = new URL(baseURL, (new StringBuilder(String.valueOf(className))).append(".class").toString());
                    ClassFile cf = CFFactory.getThreadLocalInstance().createCFParser().parse(url.openStream(), null);
                    int indexOfOpeningBracket = identifiers[i].indexOf('(');
                    com.fmi.bytecode.parser.internal.MethodInfo method = cf.getMethod(identifiers[i].substring(indexOfDot + 1, indexOfOpeningBracket), identifiers[i].substring(indexOfOpeningBracket));
                    if(method == null)
                        throw new CFException(168, identifiers[i]);
                }
            }
        }

    }

    public ArrayList getErrorsAboutNonExistingEntries(URL baseURLs[])
    {
        ArrayList list = new ArrayList();
        String identifiers[] = getAllPackageSystemIdentifiers();
        HashSet processedIdentifiers = new HashSet();
        for(int i = 0; i < identifiers.length; i++)
        {
            if(!processedIdentifiers.add(identifiers[i]))
                continue;
            String path[] = getPathOfLabels(identifiers[i]);
            if(path.length == 0)
                continue;
            String s = path[path.length - 1];
            if(s.startsWith("{class}"))
            {
                InputStream in = openStream(baseURLs, (new StringBuilder(String.valueOf(identifiers[i]))).append(".class").toString());
                if(in == null)
                    list.add((new StringBuilder("Class '")).append(identifiers[i]).append("' not found.").toString());
                else
                    try
                    {
                        CFFactory.getThreadLocalInstance().createCFParser().parse(in, null);
                    }
                    catch(IOException e)
                    {
                        list.add((new StringBuilder("Cannot parse class '")).append(identifiers[i]).append("' - ").append(e).toString());
                    }
                continue;
            }
            if(!s.startsWith("{method}"))
                continue;
            int indexOfDot = identifiers[i].indexOf('.');
            String className = identifiers[i].substring(0, indexOfDot);
            InputStream in = openStream(baseURLs, (new StringBuilder(String.valueOf(className))).append(".class").toString());
            if(in == null)
            {
                list.add((new StringBuilder("Class '")).append(identifiers[i]).append("' not found.").toString());
                continue;
            }
            ClassFile cf = null;
            try
            {
                cf = CFFactory.getThreadLocalInstance().createCFParser().parse(in, null);
            }
            catch(IOException e)
            {
                list.add((new StringBuilder("Cannot parse class '")).append(identifiers[i]).append(" - ").append(e).toString());
                continue;
            }
            int indexOfOpeningBracket = identifiers[i].indexOf('(');
            com.fmi.bytecode.parser.internal.MethodInfo method = cf.getMethod(identifiers[i].substring(indexOfDot + 1, indexOfOpeningBracket), identifiers[i].substring(indexOfOpeningBracket));
            if(method == null)
                list.add((new StringBuilder("Method ")).append(identifiers[i]).append(" not found.").toString());
        }

        return list;
    }

    private InputStream openStream(URL baseURLs[], String relativeName)
    {
        InputStream r = null;
        for(int i = 0; i < baseURLs.length;)
            try
            {
                r = (new URL(baseURLs[i], relativeName)).openStream();
                break;
            }
            catch(IOException ioexception)
            {
                i++;
            }

        return r;
    }

    private final PropertiesTree tree;
    private final ArrayList allPackageSystemIdentifiers;
    private final Map hPathOfLabelsCache;
}
