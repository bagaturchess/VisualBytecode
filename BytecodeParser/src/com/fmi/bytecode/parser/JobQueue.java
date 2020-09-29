// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JobQueue.java

package com.fmi.bytecode.parser;


public interface JobQueue
{

    public abstract void addJob(Runnable runnable);

    public abstract boolean isFree();
}
