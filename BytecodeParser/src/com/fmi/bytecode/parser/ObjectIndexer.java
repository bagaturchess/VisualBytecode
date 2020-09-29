// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ObjectIndexer.java

package com.fmi.bytecode.parser;

import java.util.Arrays;

public final class ObjectIndexer
{

    public ObjectIndexer()
    {
        capacityIndexInPrimes = 0;
        capacity = PRIMES[capacityIndexInPrimes];
        threshold = THRESHOLDS[capacityIndexInPrimes];
        nEntries = 0;
        keys = new Object[capacity];
        values = new int[capacity];
        keysOrdered = new Object[128];
    }

    public int getIndex(Object o)
    {
        if(o == null)
            throw new IllegalArgumentException();
        int h = System.identityHashCode(o);
        int h2 = 8 - (h & 7);
        h %= capacity;
        do
        {
            Object k = keys[h];
            if(k == o)
                return values[h];
            if(k == null)
                break;
            h = (h + h2) % capacity;
        } while(true);
        keys[h] = o;
        values[h] = nEntries;
        if(keysOrdered.length == nEntries)
        {
            Object keysOrderedOld[] = keysOrdered;
            keysOrdered = new Object[keysOrderedOld.length * 2];
            System.arraycopy(((Object) (keysOrderedOld)), 0, ((Object) (keysOrdered)), 0, nEntries);
        }
        keysOrdered[nEntries] = o;
        nEntries++;
        if(nEntries >= threshold)
            rehash();
        return nEntries - 1;
    }

    public Object getObject(int index)
    {
        if(index < 0 || index >= nEntries)
            throw new IllegalArgumentException();
        else
            return keysOrdered[index];
    }

    private void rehash()
    {
        Object keysOld[] = keys;
        int valuesOld[] = values;
        int capacityOld = capacity;
        capacityIndexInPrimes++;
        capacity = PRIMES[capacityIndexInPrimes];
        threshold = THRESHOLDS[capacityIndexInPrimes];
        keys = new Object[capacity];
        values = new int[capacity];
        for(int i = 0; i < capacityOld; i++)
        {
            Object k = keysOld[i];
            if(k != null)
            {
                int h = System.identityHashCode(k);
                int h2 = 8 - (h & 7);
                for(h %= capacity; keys[h] != null; h = (h + h2) % capacity);
                keys[h] = k;
                values[h] = valuesOld[i];
            }
        }

    }

    public int size()
    {
        return nEntries;
    }

    public Object[] getObjectsArray()
    {
        Object r[] = new Object[nEntries];
        for(int i = 0; i < capacity; i++)
            if(keys[i] != null)
                r[values[i]] = keys[i];

        return r;
    }

    public void clear()
    {
        nEntries = 0;
        Arrays.fill(keys, null);
        Arrays.fill(keysOrdered, null);
    }

    private static final int PRIMES[] = {
        17, 37, 67, 131, 257, 521, 1031, 2053, 4099, 8209, 
        16411, 32771, 0x10001, 0x2001d, 0x40003, 0x80015, 0x100007, 0x200011, 0x40000f, 0x800009, 
        0x100002b, 0x2000023, 0x400000f, 0x800001d, 0x10000003, 0x2000000b, 0x40000003, 0x7fffffff
    };
    private static final double LOAD_FACTOR = 0.69999999999999996D;
    private static final int THRESHOLDS[];
    private int capacityIndexInPrimes;
    private int capacity;
    private int nEntries;
    private int threshold;
    private Object keys[];
    private int values[];
    private Object keysOrdered[];

    static 
    {
        THRESHOLDS = new int[PRIMES.length];
        for(int i = 0; i < PRIMES.length; i++)
            THRESHOLDS[i] = (int)((double)PRIMES[i] * 0.69999999999999996D);

    }
}
