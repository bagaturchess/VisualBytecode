package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.*;
import com.fmi.bytecode.parser.ClassFile;
import com.fmi.bytecode.parser.MethodInfo;

import java.util.ArrayList;
import java.util.List;


public class ClassModelImpl extends AnnotatableElementModelImpl
    implements ClassModel
{

    public ClassModelImpl(ClassFile _classFile)
    {
        super(_classFile);
        classFile = _classFile;
        parseMethods();
        parseFields();
    }

    public ConstructorModel[] getConstructors()
    {
        return constructors;
    }

    public MethodModel[] getMethods()
    {
        return methods;
    }

    public FieldModel[] getFields()
    {
        return fields;
    }

    public boolean isParametrised()
    {
        return classFile.getAttribute("Signature") != null;
    }

    private void parseMethods()
    {
        MethodInfo mds[] = classFile.getMethodsArray();
        List methodsList = new ArrayList();
        List constructorList = new ArrayList();
        for(int i = 0; i < mds.length; i++)
        {
            String methodName = mds[i].getName();
            if(methodName.equals("<init>"))
                constructorList.add(new ConstructorModelImpl(mds[i], this));
            else
                methodsList.add(new MethodModelImpl(mds[i], this));
        }

        constructors = (ConstructorModel[])constructorList.toArray(new ConstructorModelImpl[constructorList.size()]);
        methods = (MethodModel[])methodsList.toArray(new MethodModelImpl[methodsList.size()]);
    }

    private void parseFields()
    {
        List fieldsList = new ArrayList();
        com.fmi.bytecode.parser.FieldInfo bmFields[] = classFile.getFieldsArray();
        com.fmi.bytecode.parser.FieldInfo afieldinfo[] = bmFields;
        int i = 0;
        for(int j = afieldinfo.length; i < j; i++)
        {
            com.fmi.bytecode.parser.FieldInfo f = afieldinfo[i];
            FieldModel fi = new FieldModelImpl(f, this);
            fieldsList.add(fi);
        }

        fields = (FieldModel[])fieldsList.toArray(new FieldModelImpl[fieldsList.size()]);
    }

    public int getAccessFlags()
    {
        return classFile.getAccessFlags();
    }

    public String[] getInterfaceNames()
    {
        return classFile.getInterfaceNames();
    }

    public String getPackageName()
    {
        return classFile.getPackageName();
    }

    public String getSuperClassName()
    {
        return classFile.getSuperClassName();
    }

    public boolean isAnnotation()
    {
        return classFile.isAnnotation();
    }

    public boolean isEnum()
    {
        return classFile.isEnum();
    }

    public boolean isInterface()
    {
        return classFile.isInterface();
    }

    private MethodModel methods[];
    private ConstructorModel constructors[];
    private FieldModel fields[];
    private ClassFile classFile;
}
