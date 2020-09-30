// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CFException.java

package com.fmi.bytecode.parser.internal;

public class CFException extends RuntimeException
{

    public CFException(String message)
    {
        super(message);
    }

    public CFException(int messageId)
    {
        super(ResourceManager.get(messageId));
    }

    public CFException(int messageId, Object argument)
    {
        super(ResourceManager.get(messageId, argument));
    }

    public CFException(int messageId, Object arguments[])
    {
        super(ResourceManager.get(messageId, arguments));
    }
}
