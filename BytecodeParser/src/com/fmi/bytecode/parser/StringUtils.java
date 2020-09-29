// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringUtils.java

package com.fmi.bytecode.parser;

import java.io.*;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Properties;

// Referenced classes of package com.sap.engine.library.bytecode.misc:
//            Debug, ResourceManager

public final class StringUtils
{

    private StringUtils()
    {
    }

    public static String[] split(String s, char separator)
    {
        int n = 1;
        int index = 0;
        do
        {
            index = s.indexOf(separator, index);
            if(index == -1)
                break;
            index++;
            n++;
        } while(true);
        String r[] = new String[n];
        index = 0;
        n = 0;
        do
        {
            int index1 = s.indexOf(separator, index);
            if(index1 != -1)
            {
                r[n] = s.substring(index, index1);
                index = index1 + 1;
                n++;
            } else
            {
                r[n] = s.substring(index);
                return r;
            }
        } while(true);
    }

    public static String[] splitAndTrim(String s, char separator)
    {
        String a[] = split(s, separator);
        int n = 0;
        for(int i = 0; i < a.length; i++)
            if(a[i] != null && a[i].length() != 0)
                a[n++] = a[i].trim();

        if(n != a.length)
        {
            String aOld[] = a;
            a = new String[n];
            System.arraycopy(aOld, 0, a, 0, n);
        }
        return a;
    }

    public static String concat(String a[], char separator)
    {
        if(a == null || a.length == 0)
            return "";
        boolean isFirst = true;
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < a.length; i++)
            if(a[i] != null)
            {
                if(isFirst)
                    isFirst = false;
                else
                    buffer.append(separator);
                buffer.append(a[i]);
            }

        return buffer.toString();
    }

    public static boolean startsWith(String a[], String b[])
    {
        if(a.length < b.length)
            return false;
        for(int i = 0; i < b.length; i++)
            if(!a[i].equals(b[i]))
                return false;

        return true;
    }

    public static boolean equals(String a[], String b[])
    {
        if(a.length != b.length)
            return false;
        for(int i = 0; i < a.length; i++)
            if(!a[i].equals(b[i]))
                return false;

        return true;
    }

    public static String[] trimAll(String a[])
    {
        int na = a.length;
        for(int i = 0; i < na; i++)
            a[i] = a[i].trim();

        return a;
    }

    public static String fqNameToShortName(String fqName)
    {
        int index = fqName.lastIndexOf('.');
        return index != -1 ? fqName.substring(index + 1) : fqName;
    }

    public static String fqNameToShortName(Class c)
    {
        return fqNameToShortName(c.getName());
    }

    public static String filePathToExtension(String filepath)
    {
        int lastIndexOfSlash = filepath.lastIndexOf(File.separatorChar);
        int lastIndexOfDot = filepath.lastIndexOf('.');
        return lastIndexOfDot <= lastIndexOfSlash ? "" : filepath.substring(lastIndexOfDot + 1);
    }

    public static String escapeString(String s)
    {
        return escapeToJavaLiteral(s);
    }

    public static String escapeToHTML(String s)
    {
        StringBuffer buffer = new StringBuffer(s.length() * 2);
        char a[] = s.toCharArray();
        int na = a.length;
        for(int i = 0; i < na; i++)
        {
            char ch = a[i];
            if(ch < ' ' || ch > '\377')
                buffer.append("&#").append(ch).append(';');
            else
                switch(ch)
                {
                case 32: // ' '
                    buffer.append("&nbsp;");
                    break;

                case 38: // '&'
                    buffer.append("&amp;");
                    break;

                case 60: // '<'
                    buffer.append("&lt;");
                    break;

                case 62: // '>'
                    buffer.append("&gt;");
                    break;

                case 39: // '\''
                    buffer.append("&apos;");
                    break;

                case 34: // '"'
                    buffer.append("&quot;");
                    break;

                default:
                    buffer.append(ch);
                    break;
                }
        }

        return buffer.toString();
    }

    public static String escapeToJavaLiteral(String s)
    {
        char a[] = s.toCharArray();
        StringBuffer b = new StringBuffer();
        int na = a.length;
        for(int i = 0; i < na; i++)
        {
            char ch = a[i];
            if(ch < ' ' || ch > '\377')
            {
                b.append("\\u");
                b.append(HEX_UPPER_CASE[ch >> 12 & 0xf]);
                b.append(HEX_UPPER_CASE[ch >> 8 & 0xf]);
                b.append(HEX_UPPER_CASE[ch >> 4 & 0xf]);
                b.append(HEX_UPPER_CASE[ch & 0xf]);
            } else
            {
                switch(a[i])
                {
                case 8: // '\b'
                    b.append("\\b");
                    break;

                case 9: // '\t'
                    b.append("\\t");
                    break;

                case 10: // '\n'
                    b.append("\\n");
                    break;

                case 12: // '\f'
                    b.append("\\f");
                    break;

                case 13: // '\r'
                    b.append("\\r");
                    break;

                case 34: // '"'
                    b.append("\\\"");
                    break;

                case 39: // '\''
                    b.append("\\'");
                    break;

                case 92: // '\\'
                    b.append("\\\\");
                    break;

                default:
                    b.append(ch);
                    break;
                }
            }
        }

        return b.toString();
    }

    public static String throwableToString(Throwable throwable)
    {
        StringWriter stringWriter = new StringWriter(256);
        PrintWriter printWriter = new PrintWriter(stringWriter);
        throwable.printStackTrace(printWriter);
        printWriter.close();
        return stringWriter.toString();
    }

    public static String toHexString(long number, int nDigits)
    {
        return toHexString(number, nDigits, true);
    }

    public static String toHexString(long number, int nDigits, boolean useUpperCase)
    {
        if((Debug.DEBUG_CF || Debug.DEBUG_GUI || Debug.DEBUG_MODIFIER || Debug.DEBUG_TRACING) && (nDigits < 1 || nDigits > 16))
            throw new IllegalArgumentException();
        char buffer[] = new char[nDigits];
        int iBuffer = 0;
        char HEX[] = useUpperCase ? HEX_UPPER_CASE : HEX_LOWER_CASE;
        for(int offset = (nDigits - 1) * 4; offset >= 0; offset -= 4)
            buffer[iBuffer++] = HEX[(int)(number >> offset & 15L)];

        return new String(buffer);
    }

    public static String toHexString(byte bytes[], boolean useUpperCase)
    {
        return toHexString(bytes, 0, bytes.length, useUpperCase);
    }

    public static String toHexString(byte bytes[], int start, int end, boolean useUpperCase)
    {
        if(bytes == null || start > end || start < 0 || end > bytes.length)
            throw new IllegalArgumentException();
        char buffer[] = new char[(end - start) * 2];
        int iBuffer = 0;
        char HEX[] = useUpperCase ? HEX_UPPER_CASE : HEX_LOWER_CASE;
        for(int i = start; i < end; i++)
        {
            int b = bytes[i];
            buffer[iBuffer++] = HEX[b >> 4 & 0xf];
            buffer[iBuffer++] = HEX[b & 0xf];
        }

        return new String(buffer);
    }

    public static String[] packageSystemIdentifierToPathOfLabels(String s)
    {
        if(s == null)
            throw new IllegalArgumentException(ResourceManager.get(167));
        if(s.length() == 0)
            throw new IllegalArgumentException(ResourceManager.get(166));
        if(s.indexOf(' ') != -1)
            throw new IllegalArgumentException(ResourceManager.get(165, s));
        int indexOfDot = s.indexOf('.');
        String result[];
        if(indexOfDot == -1)
        {
            String a[] = split(s, '/');
            if(a[a.length - 1].equals("**"))
            {
                result = new String[a.length - 1];
                for(int i = 0; i < a.length - 1; i++)
                    result[i] = (new StringBuilder("{subtree}")).append(a[i]).toString();

            } else
            if(a[a.length - 1].equals("*"))
            {
                result = new String[a.length];
                for(int i = 0; i < a.length - 1; i++)
                    result[i] = (new StringBuilder("{subtree}")).append(a[i]).toString();

                result[a.length - 1] = "{package}";
            } else
            {
                result = new String[a.length + 1];
                for(int i = 0; i < a.length - 1; i++)
                    result[i] = (new StringBuilder("{subtree}")).append(a[i]).toString();

                result[a.length - 1] = "{package}";
                result[a.length] = (new StringBuilder("{class}")).append(a[a.length - 1]).toString();
            }
        } else
        {
            int indexOfOpeningBracket = s.indexOf('(');
            if(indexOfOpeningBracket == -1)
            {
                String a[] = packageSystemIdentifierToPathOfLabels(s.substring(0, indexOfDot));
                if(!a[a.length - 1].startsWith("{class}"))
                    throw new IllegalArgumentException(ResourceManager.get(164, s));
                result = new String[a.length + 1];
                System.arraycopy(a, 0, result, 0, a.length);
                result[a.length] = (new StringBuilder("{field}")).append(s.substring(indexOfDot + 1)).toString();
            } else
            {
                String a[] = packageSystemIdentifierToPathOfLabels(s.substring(0, indexOfDot));
                if(!a[a.length - 1].startsWith("{class}"))
                    throw new IllegalArgumentException(ResourceManager.get(164, s));
                result = new String[a.length + 1];
                System.arraycopy(a, 0, result, 0, a.length);
                String methodDescriptor = s.substring(indexOfOpeningBracket);
                MethodInfo.checkMethodDescriptor(methodDescriptor);
                String methodNameAndDescriptor = s.substring(indexOfDot + 1);
                result[a.length] = (new StringBuilder("{method}")).append(methodNameAndDescriptor).toString();
            }
        }
        return result;
    }

    public static boolean isJLSIdentifier(String s)
    {
        if(s == null)
            return false;
        char ca[] = s.toCharArray();
        int nca = ca.length;
        if(nca == 0)
            return false;
        if(!Character.isJavaIdentifierStart(ca[0]))
            return false;
        for(int i = 1; i < nca; i++)
            if(!Character.isJavaIdentifierPart(ca[i]))
                return false;

        return true;
    }

    public static boolean isJLSFullyQualifiedIdentifier(String s)
    {
        if(s == null)
            return false;
        String a[] = split(s, '.');
        for(int i = 0; i < a.length; i++)
            if(!isJLSIdentifier(a[i]))
                return false;

        return true;
    }

    public static File[] getAncestorsIncludingSelf(File file)
    {
        ArrayList list = new ArrayList();
        for(; file != null; file = file.getParentFile())
            list.add(file);

        return (File[])list.toArray(new File[list.size()]);
    }

    public static Properties parseProperties(String s)
    {
        String pairs[] = splitAndTrim(s, ',');
        Properties r = new Properties();
        for(int i = 0; i < pairs.length; i++)
            if(pairs[i] != null)
            {
                int indexOfEquals = pairs[i].indexOf('=');
                if(indexOfEquals == -1)
                    r.setProperty(URLDecoder.decode(pairs[i]), "");
                else
                    r.setProperty(URLDecoder.decode(pairs[i].substring(0, indexOfEquals)), URLDecoder.decode(pairs[i].substring(indexOfEquals + 1)));
            }

        return r;
    }

    public static final char HEX_UPPER_CASE[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'A', 'B', 'C', 'D', 'E', 'F'
    };
    public static final char HEX_LOWER_CASE[] = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b', 'c', 'd', 'e', 'f'
    };
    static final String PREFIX_SUBTREE = "{subtree}";
    static final String PREFIX_PACKAGE = "{package}";
    static final String PREFIX_CLASS = "{class}";
    static final String PREFIX_METHOD = "{method}";
    static final String PREFIX_FIELD = "{field}";

}
