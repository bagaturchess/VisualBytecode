// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SerialVersionUIDCalculator.java

package com.fmi.bytecode.parser.internal;

import java.io.*;
import java.lang.reflect.Modifier;
import java.security.*;
import java.util.Arrays;
import java.util.Comparator;

// Referenced classes of package com.fmi.bytecode.parser:
//            ClassFile, MethodInfo, FieldInfo, MemberInfo

final class SerialVersionUIDCalculator
{

    private SerialVersionUIDCalculator()
    {
    }

    protected static long computeSerialVersionUID(ClassFile cf)
    {
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            DataOutputStream out = new DataOutputStream(new DigestOutputStream(NULL_OUTPUT_STREAM, digest));
            int cfAccessFlags = cf.getAccessFlags();
            cfAccessFlags &= 0x611;
            String interfaceNames[] = cf.getInterfaceNames();
            for(int i = 0; i < interfaceNames.length; i++)
                interfaceNames[i] = interfaceNames[i].replace('/', '.');

            Arrays.sort(interfaceNames);
            FieldInfo fields[] = cf.getFieldsArray();
            Arrays.sort(fields, MEMBER_COMPARATOR);
            MethodInfo methods[] = cf.getMethodsArray();
            MethodInfo constructors[] = new MethodInfo[methods.length];
            int nConstructors = 0;
            int nMethods = methods.length;
            for(int index = 0; index < nMethods;)
            {
                String name = methods[index].getName();
                if(name.equals("<init>"))
                {
                    constructors[nConstructors++] = methods[index];
                    nMethods--;
                    methods[index] = methods[nMethods];
                } else
                if(name.equals("<clinit>"))
                {
                    nMethods--;
                    methods[index] = methods[nMethods];
                } else
                {
                    index++;
                }
            }

            Arrays.sort(methods, 0, nMethods, MEMBER_COMPARATOR);
            Arrays.sort(constructors, 0, nConstructors, MEMBER_COMPARATOR);
            out.writeUTF(cf.getName().replace('/', '.'));
            if((cfAccessFlags & 0x200) != 0)
            {
                cfAccessFlags &= 0xfffffbff;
                if(methods.length > 0)
                    cfAccessFlags |= 0x400;
            }
            out.writeInt(cfAccessFlags);
            for(int i = 0; i < interfaceNames.length; i++)
                out.writeUTF(interfaceNames[i]);

            for(int i = 0; i < fields.length; i++)
            {
                FieldInfo f = fields[i];
                int m = f.getAccessFlags();
                if(!Modifier.isPrivate(m) || !Modifier.isTransient(m) && !Modifier.isStatic(m))
                {
                    out.writeUTF(f.getName());
                    out.writeInt(m);
                    out.writeUTF(f.getDescriptor());
                }
            }

            if(cf.getMethod("<clinit>", "()V") != null)
            {
                out.writeUTF("<clinit>");
                out.writeInt(8);
                out.writeUTF("()V");
            }
            for(int i = 0; i < nConstructors; i++)
                if(!constructors[i].isPrivate())
                {
                    out.writeUTF("<init>");
                    out.writeInt(constructors[i].getAccessFlags());
                    out.writeUTF(constructors[i].getDescriptor().replace('/', '.'));
                }

            for(int i = 0; i < nMethods; i++)
                if(!methods[i].isPrivate())
                {
                    out.writeUTF(methods[i].getName());
                    out.writeInt(methods[i].getAccessFlags());
                    out.writeUTF(methods[i].getDescriptor().replace('/', '.'));
                }

            out.flush();
            byte hasharray[] = digest.digest();
            int l = Math.min(8, hasharray.length);
            long h = 0L;
            for(int i = 0; i < l; i++)
                h += (long)(hasharray[i] & 0xff) << i * 8;

            return h;
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }
        catch(NoSuchAlgorithmException nsae)
        {
            throw new SecurityException(nsae.getMessage());
        }
        return -1L;
    }

    private static final Comparator MEMBER_COMPARATOR = new Comparator() {

        public int compare(Object o1, Object o2)
        {
            String name1 = ((MemberInfo)o1).getName();
            String name2 = ((MemberInfo)o2).getName();
            return name1.compareTo(name2);
        }

    }
;
    private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream() {

        public void write(int i)
            throws IOException
        {
        }

        public void write(byte abyte0[])
            throws IOException
        {
        }

        public void write(byte abyte0[], int i, int j)
            throws IOException
        {
        }

        public void flush()
            throws IOException
        {
        }

        public void close()
            throws IOException
        {
        }

    }
;

}
