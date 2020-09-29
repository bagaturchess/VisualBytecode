package com.fmi.bytecode.parser;

public final class MethodInfo extends MemberInfo
{
    private String returnType;
    private String[] arguments;
    private String jlsString;
    
    MethodInfo(final CFFactory factory, final ClassFile ownerClassFile, final int accessFlags, final CPInfo name, final CPInfo descriptor) {
        super(factory, ownerClassFile, accessFlags, name, descriptor);
        this.returnType = null;
        this.arguments = null;
        this.jlsString = null;
    }
    
    public String getReturnType() {
        if (this.returnType == null) {
            this.returnType = parseMethodDescriptorReturnType(this.getDescriptor());
        }
        return this.returnType;
    }
    
    public String[] getArguments() {
        if (this.arguments == null) {
            this.arguments = parseMethodDescriptorArguments(this.getDescriptor());
        }
        final String[] r = new String[this.arguments.length];
        System.arraycopy(this.arguments, 0, r, 0, this.arguments.length);
        return r;
    }
    
    public String toString() {
        return "[METHOD " + this.getOwnerClassFile().getName() + '.' + this.getName() + this.getDescriptor() + "]";
    }
    
    public CodeAttribute getCodeAttribute() {
        CodeAttribute r;
        try {
            r = (CodeAttribute)super.getAttribute("Code");
        }
        catch (ClassCastException cce) {
            throw new CFException(22);
        }
        return r;
    }
    
    public CodeAttribute createCodeAttribute() {
        CodeAttribute ca = this.getCodeAttribute();
        if (ca != null) {
            return ca;
        }
        ca = new CodeAttribute(this.factory, this);
        super.addAttribute((AttributeInfo)ca);
        return ca;
    }
    
    public ExceptionsAttribute getExceptionsAttribute() {
        return (ExceptionsAttribute)super.getAttribute("Exceptions");
    }
    
    public ExceptionsAttribute createExceptionsAttribute() {
        ExceptionsAttribute r = this.getExceptionsAttribute();
        if (r != null) {
            return r;
        }
        r = new ExceptionsAttribute();
        super.addAttribute((AttributeInfo)r);
        return r;
    }
    
    public static String[] parseMethodDescriptorArguments(final String s) {
        try {
            int i = 1;
            final String[] temp = new String[s.length()];
            int nTemp = 0;
        Label_0064:
            while (true) {
                int j;
                for (j = i; s.charAt(j) == '['; ++j) {}
                switch (s.charAt(j)) {
                    case ')': {
                        break Label_0064;
                    }
                    case 'L': {
                        j = s.indexOf(59, j) + 1;
                        break;
                    }
                    default: {
                        ++j;
                        break;
                    }
                }
                temp[nTemp++] = s.substring(i, j);
                i = j;
            }
            final String[] r = new String[nTemp];
            System.arraycopy(temp, 0, r, 0, nTemp);
            return r;
        }
        catch (RuntimeException e) {
            throw e;
        }
    }
    
    public static String parseMethodDescriptorReturnType(final String s) {
        return s.substring(s.indexOf(41) + 1);
    }
    
    public static void checkMethodDescriptor(final String s) {
        if (s == null) {
            throw new CFException(23);
        }
        int indexOfClosingBracket = s.indexOf(41);
        if (indexOfClosingBracket == -1) {
            throw new CFException(24, (Object)s);
        }
        ++indexOfClosingBracket;
        checkMethodParameters(s.substring(0, indexOfClosingBracket));
        checkMethodReturnType(s.substring(indexOfClosingBracket));
    }
    
    public static void checkMethodParameters(final String s) {
        if (s == null) {
            throw new CFException(30);
        }
        if (!s.startsWith("(")) {
            throw new CFException(31, (Object)s);
        }
        final int indexOfClosingBracket = s.indexOf(41);
        int index = 1;
        int dimensions = 0;
        int slots = 0;
        while (index < indexOfClosingBracket) {
            switch (s.charAt(index)) {
                case 'B':
                case 'C':
                case 'F':
                case 'I':
                case 'S':
                case 'Z': {
                    ++index;
                    ++slots;
                    dimensions = 0;
                    continue;
                }
                case 'D':
                case 'J': {
                    ++index;
                    slots += ((dimensions == 0) ? 2 : 1);
                    dimensions = 0;
                    continue;
                }
                case 'L': {
                    index = s.indexOf(59, index);
                    if (index == -1) {
                        throw new CFException(29, (Object)s);
                    }
                    ++index;
                    ++slots;
                    dimensions = 0;
                    continue;
                }
                case '[': {
                    ++dimensions;
                    ++index;
                    continue;
                }
                default: {
                    throw new CFException(27, new Object[] { s, new Character(s.charAt(index)), new Integer(index) });
                }
            }
        }
        if (index != indexOfClosingBracket) {
            throw new CFException(28, (Object)s);
        }
        if (dimensions != 0) {
            throw new CFException(26, (Object)s);
        }
        if (slots > 255) {
            throw new CFException(25, (Object)s);
        }
    }
    
    public static void checkMethodReturnType(final String s) {
        if ("V".equals(s)) {
            return;
        }
        try {
            FieldInfo.checkFieldDescriptor(s);
        }
        catch (CFException cfe) {
            throw new CFException(3, (Object)s);
        }
    }
    
    public int getNInitialOccupiedLocalVariables() {
        int r = this.isStatic() ? 0 : 1;
        this.getArguments();
        for (int i = 0; i < this.arguments.length; ++i) {
            r += ((this.arguments[i].equals("J") || this.arguments[i].equals("D")) ? 2 : 1);
        }
        return r;
    }
    
    public static String convertParametersToJLS(final String parametersDescriptor) {
        checkMethodParameters(parametersDescriptor);
        final String[] arguments = parseMethodDescriptorArguments(parametersDescriptor);
        final StringBuffer buffer = new StringBuffer();
        buffer.append("(");
        for (int i = 0; i < arguments.length; ++i) {
            if (i != 0) {
                buffer.append(", ");
            }
            buffer.append(FieldInfo.convertTypeToJLS(arguments[i]));
        }
        buffer.append(")");
        return buffer.toString();
    }
    
    public String toJLSString() {
        if (this.jlsString != null) {
            return this.jlsString;
        }
        final String name = this.getName();
        if (name.equals("<clinit>")) {
            return this.jlsString = "static { ... }";
        }
        final String descriptor = this.getDescriptor();
        final String[] parameters = parseMethodDescriptorArguments(descriptor);
        final String returnType = parseMethodDescriptorReturnType(descriptor);
        final StringBuffer buffer = new StringBuffer();
        boolean isConstructor;
        if (name.equals("<init>")) {
            String className = this.getOwnerClassFile().getName();
            final String[] a = StringUtils.split(className, '/');
            className = a[a.length - 1];
            buffer.append(className);
            isConstructor = true;
        }
        else {
            buffer.append(name);
            isConstructor = false;
        }
        buffer.append('(');
        for (int i = 0; i < parameters.length; ++i) {
            if (i != 0) {
                buffer.append(", ");
            }
            buffer.append(FieldInfo.convertTypeToJLS(parameters[i]));
        }
        buffer.append(')');
        if (!isConstructor) {
            buffer.append(" : ");
            if (returnType.equals("V")) {
                buffer.append("void ");
            }
            else {
                buffer.append(FieldInfo.convertTypeToJLS(returnType)).append(' ');
            }
        }
        return this.jlsString = buffer.toString();
    }
}