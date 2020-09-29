// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AttributeContainer.java

package com.fmi.bytecode.parser;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, CFException

final class AttributeContainer
{

    AttributeContainer()
    {
        attributes = new AttributeInfo[4];
    }

    void addAttribute(AttributeInfo a)
    {
        if(Debug.DEBUG_CF)
        {
            String name = a.getName();
            for(int i = 0; i < nAttributes; i++)
                if(attributes[i].getName().equals(name))
                    throw new CFException(1, name);

        }
        if(nAttributes == attributes.length)
        {
            AttributeInfo old[] = attributes;
            attributes = new AttributeInfo[2 * nAttributes];
            System.arraycopy(old, 0, attributes, 0, nAttributes);
        }
        attributes[nAttributes] = a;
        nAttributes++;
    }

    int getNAttributes()
    {
        return nAttributes;
    }

    AttributeInfo getAttribute(int index)
    {
        if(Debug.DEBUG_CF && (index < 0 || index >= nAttributes))
            throw new CFException(17, new Object[] {
                "AttributeInfo", new Integer(index), new Integer(nAttributes - 1)
            });
        else
            return attributes[index];
    }

    AttributeInfo getAttribute(String name)
    {
        for(int i = 0; i < nAttributes; i++)
            if(attributes[i].getName().equals(name))
                return attributes[i];

        return null;
    }

    AttributeInfo[] getAttributesArray()
    {
        AttributeInfo r[] = new AttributeInfo[nAttributes];
        System.arraycopy(attributes, 0, r, 0, nAttributes);
        return r;
    }

    void removeAttribute(AttributeInfo a)
    {
        for(int i = 0; i < nAttributes; i++)
            if(a == attributes[i])
            {
                System.arraycopy(attributes, i + 1, attributes, i, nAttributes - i - 1);
                nAttributes--;
                return;
            }

    }

    void removeAttribute(String name)
    {
        for(int i = 0; i < nAttributes; i++)
            if(attributes[i].getName().equals(name))
            {
                System.arraycopy(attributes, i + 1, attributes, i, nAttributes - i - 1);
                nAttributes--;
                return;
            }

    }

    private AttributeInfo attributes[];
    private int nAttributes;
}
