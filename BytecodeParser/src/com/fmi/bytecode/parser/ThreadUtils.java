// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ThreadUtils.java

package com.fmi.bytecode.parser;


public final class ThreadUtils
{

    private ThreadUtils()
    {
    }

    public static void fork(Runnable runnable)
    {
        (new Thread(runnable)).start();
    }
}
