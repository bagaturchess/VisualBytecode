package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.ByteCodeReader;
import com.fmi.bytecode.annotations.input.ClassModel;
import com.fmi.bytecode.annotations.input.ReadingException;
import com.fmi.bytecode.parser.internal.CFFactory;
import com.fmi.bytecode.parser.internal.CFParser;

import java.io.IOException;
import java.io.InputStream;


public class ByteCodeReaderImpl
    implements ByteCodeReader
{

    public ByteCodeReaderImpl()
    {
    }

    public ClassModel parseClassInformation(InputStream bytecode)
        throws ReadingException
    {
        CFFactory factory = new CFFactory();
        CFParser parser = factory.createCFParser();
        com.fmi.bytecode.parser.internal.ClassFile parsedClass;
        try
        {
            parsedClass = parser.parse(bytecode, null);
        }
        catch(IOException e)
        {
            throw new ReadingException(e);
        }
        return new ClassModelImpl(parsedClass);
    }
}
