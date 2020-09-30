// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ByteArrayDataInput.java

package com.fmi.bytecode.parser.internal;

import java.io.*;

// Referenced classes of package com.fmi.bytecode.parser:
//            CFException

final class ByteArrayDataInput
    implements DataInput
{

    ByteArrayDataInput(byte data[])
    {
        this.data = data;
        nData = data.length;
        pData = 0;
    }

    int getPosition()
    {
        return pData;
    }

    public void readFully(byte b[])
        throws IOException
    {
        int length = b.length;
        System.arraycopy(data, pData, b, 0, length);
        pData += length;
        if(Debug.DEBUG_CF)
            checkEOF();
    }

    public void readFully(byte b[], int off, int len)
        throws IOException
    {
        System.arraycopy(data, pData, b, off, len);
        pData += len;
        if(Debug.DEBUG_CF)
            checkEOF();
    }

    public int skipBytes(int n)
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public boolean readBoolean()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public byte readByte()
        throws IOException
    {
        byte r = data[pData];
        pData++;
        if(Debug.DEBUG_CF)
            checkEOF();
        return r;
    }

    public int readUnsignedByte()
        throws IOException
    {
        int r = data[pData++] & 0xff;
        if(Debug.DEBUG_CF)
            checkEOF();
        return r;
    }

    public short readShort()
        throws IOException
    {
        short r = (short)(data[pData++] << 8 | data[pData++] & 0xff);
        if(Debug.DEBUG_CF)
            checkEOF();
        return r;
    }

    public int readUnsignedShort()
        throws IOException
    {
        int r = (data[pData++] << 8 | data[pData++] & 0xff) & 0xffff;
        if(Debug.DEBUG_CF)
            checkEOF();
        return r;
    }

    public char readChar()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public int readInt()
        throws IOException
    {
        int r = (data[pData++] & 0xff) << 24 | (data[pData++] & 0xff) << 16 | (data[pData++] & 0xff) << 8 | data[pData++] & 0xff;
        if(Debug.DEBUG_CF)
            checkEOF();
        return r;
    }

    public long readLong()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public float readFloat()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public double readDouble()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public String readLine()
        throws IOException
    {
        throw new UnsupportedOperationException();
    }

    public String readUTF()
        throws IOException
    {
        String r = DataInputStream.readUTF(this);
        if(Debug.DEBUG_CF)
            checkEOF();
        return r;
    }

    private void checkEOF()
    {
        if(pData > nData)
            throw new CFException(10);
        else
            return;
    }

    private byte data[];
    private int nData;
    private int pData;
}
