// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CodeAttribute.java

package com.fmi.bytecode.parser.internal;

import java.util.Enumeration;
import java.util.Hashtable;

// Referenced classes of package com.fmi.bytecode.parser:
//            AttributeInfo, Attributed, Instruction, ExceptionHandler, 
//            AttributeContainer, MethodInfo, ClassFile, LocalVariableTableAttribute, 
//            CFException, LocalVariable, FieldInfo, CPInfo, 
//            CFFactory

public final class CodeAttribute extends AttributeInfo
    implements Attributed
{

    CodeAttribute(CFFactory factory, MethodInfo ownerMethod)
    {
        instructions = new Instruction[128];
        exceptionHandlers = new ExceptionHandler[8];
        attributeContainer = new AttributeContainer();
        hLabelToInstruction = new Hashtable();
        hInstructionToLabel = new Hashtable();
        this.factory = factory;
        this.ownerMethod = ownerMethod;
    }

    public int getOriginalMaxLocals()
    {
        return originalMaxLocals;
    }

    void setMaxLocals(int maxLocals)
    {
        originalMaxLocals = maxLocals;
    }

    public int getOriginalMaxStack()
    {
        return originalMaxStack;
    }

    void setOriginalMaxStack(int originalMaxStack)
    {
        this.originalMaxStack = originalMaxStack;
    }

    void addInstruction(Instruction x)
    {
        if(nInstructions == instructions.length)
        {
            Instruction old[] = instructions;
            instructions = new Instruction[nInstructions * 2];
            System.arraycopy(old, 0, instructions, 0, nInstructions);
        }
        x.setIndex(nInstructions);
        instructions[nInstructions] = x;
        nInstructions++;
    }

    public int getNInstructions()
    {
        return nInstructions;
    }

    public Instruction[] getInstructionsArray()
    {
        Instruction r[] = new Instruction[nInstructions];
        System.arraycopy(instructions, 0, r, 0, nInstructions);
        return r;
    }

    public Instruction getInstructionByIndex(int index)
    {
        return instructions[index];
    }

    public int getNExceptionHandlers()
    {
        return nExceptionHandlers;
    }

    public ExceptionHandler getExceptionHandler(int index)
    {
        return exceptionHandlers[index];
    }

    public ExceptionHandler[] getExceptionHandlersArray()
    {
        ExceptionHandler r[] = new ExceptionHandler[nExceptionHandlers];
        System.arraycopy(exceptionHandlers, 0, r, 0, nExceptionHandlers);
        return r;
    }

    public void addExceptionHandler(ExceptionHandler handler)
    {
        if(nExceptionHandlers == exceptionHandlers.length)
        {
            ExceptionHandler old[] = exceptionHandlers;
            exceptionHandlers = new ExceptionHandler[nExceptionHandlers * 2];
            System.arraycopy(old, 0, exceptionHandlers, 0, nExceptionHandlers);
        }
        exceptionHandlers[nExceptionHandlers] = handler;
        nExceptionHandlers++;
    }

    public ExceptionHandler addExceptionHandler(String startPCLabel, String endPCLabel, boolean isEndPCInclusive, String handlerPCLabel, CPInfo catchType)
    {
        if(nExceptionHandlers == exceptionHandlers.length)
        {
            ExceptionHandler old[] = exceptionHandlers;
            exceptionHandlers = new ExceptionHandler[nExceptionHandlers * 2];
            System.arraycopy(old, 0, exceptionHandlers, 0, nExceptionHandlers);
        }
        ExceptionHandler handler = new ExceptionHandler(this, startPCLabel, endPCLabel, isEndPCInclusive, handlerPCLabel, catchType);
        exceptionHandlers[nExceptionHandlers] = handler;
        nExceptionHandlers++;
        return handler;
    }

    public MethodInfo getOwnerMethod()
    {
        return ownerMethod;
    }

    public String getName()
    {
        return "Code";
    }

    public int getNAttributes()
    {
        return attributeContainer.getNAttributes();
    }

    public AttributeInfo getAttribute(int index)
    {
        return attributeContainer.getAttribute(index);
    }

    public AttributeInfo getAttribute(String name)
    {
        return attributeContainer.getAttribute(name);
    }

    public void addAttribute(AttributeInfo a)
    {
        ownerMethod.getOwnerClassFile().createCPInfoUtf8(a.getName());
        attributeContainer.addAttribute(a);
    }

    public AttributeInfo[] getAttributesArray()
    {
        return attributeContainer.getAttributesArray();
    }

    public void removeAttribute(String name)
    {
        attributeContainer.removeAttribute(name);
    }

    public void removeAttribute(AttributeInfo a)
    {
        attributeContainer.removeAttribute(a);
    }

    public LocalVariableTableAttribute getLocalVariableTableAttribute()
    {
        return (LocalVariableTableAttribute)getAttribute("LocalVariableTable");
    }

    public LocalVariableTableAttribute createLocalVariableTableAttribute()
    {
        LocalVariableTableAttribute r = getLocalVariableTableAttribute();
        if(r == null)
        {
            r = new LocalVariableTableAttribute(factory, this);
            addAttribute(r);
        }
        return r;
    }

    public Instruction getFirstInstruction()
    {
        if(nInstructions == 0)
            throw new CFException(4);
        else
            return instructions[0];
    }

    public Instruction getLastInstruction()
    {
        if(nInstructions == 0)
            throw new CFException(4);
        else
            return instructions[nInstructions - 1];
    }

    Instruction createInstruction()
    {
        return new Instruction(factory, this);
    }

    public void insert(Instruction x[], int start, int end, int index)
    {
        if(Debug.DEBUG_CF)
        {
            if(x == null)
                throw new CFException(14);
            if(start < 0)
                throw new CFException(14);
            if(start >= end)
                throw new CFException(14);
            if(end >= x.length)
                throw new CFException(14);
            if(index < 0)
                throw new CFException(14);
            if(index > nInstructions)
                throw new CFException(14);
            for(int i = start; i < end; i++)
            {
                if(x[i] == null)
                    throw new CFException(14);
                if(x[i].getOwnerCodeAttribute() != this)
                    throw new CFException(14);
            }

        }
        int requiredLength = (nInstructions + end) - start;
        if(instructions.length < requiredLength)
        {
            Instruction old[] = instructions;
            instructions = new Instruction[requiredLength + 128];
            System.arraycopy(old, 0, instructions, 0, nInstructions);
        }
        System.arraycopy(instructions, index, instructions, (index + end) - start, nInstructions - index);
        System.arraycopy(x, start, instructions, index, end - start);
        nInstructions += end - start;
        for(int i = 0; i < nInstructions; i++)
            instructions[i].setIndex(i);

    }

    public void insertBefore(Instruction x[], int start, int end, Instruction refInstruction)
    {
        for(int i = 0; i < nInstructions; i++)
            if(instructions[i] == refInstruction)
            {
                insert(x, start, end, i);
                return;
            }

        throw new CFException(78);
    }

    public void insertAfter(Instruction x[], int start, int end, Instruction refInstruction)
    {
        for(int i = 0; i < nInstructions; i++)
            if(instructions[i] == refInstruction)
            {
                insert(x, start, end, i + 1);
                return;
            }

        throw new CFException(78);
    }

    public void insertLast(Instruction x[], int start, int end)
    {
        insert(x, start, end, nInstructions);
    }

    public int calculateMaxLocals()
    {
        int max = ownerMethod.getNInitialOccupiedLocalVariables();
        for(int i = 0; i < nInstructions; i++)
        {
            Instruction x = instructions[i];
            int opcode = x.getOpcode();
            int lv;
            switch(opcode)
            {
            case 21: // '\025'
            case 23: // '\027'
            case 25: // '\031'
            case 54: // '6'
            case 56: // '8'
            case 58: // ':'
            case 169: 
                lv = x.getOperandLocalVariable().getIndex();
                break;

            case 22: // '\026'
            case 24: // '\030'
            case 55: // '7'
            case 57: // '9'
                lv = x.getOperandLocalVariable().getIndex() + 1;
                break;

            case 26: // '\032'
            case 34: // '"'
            case 42: // '*'
            case 59: // ';'
            case 67: // 'C'
            case 75: // 'K'
                lv = 0;
                break;

            case 27: // '\033'
            case 30: // '\036'
            case 35: // '#'
            case 38: // '&'
            case 43: // '+'
            case 60: // '<'
            case 63: // '?'
            case 68: // 'D'
            case 71: // 'G'
            case 76: // 'L'
                lv = 1;
                break;

            case 28: // '\034'
            case 31: // '\037'
            case 36: // '$'
            case 39: // '\''
            case 44: // ','
            case 61: // '='
            case 64: // '@'
            case 69: // 'E'
            case 72: // 'H'
            case 77: // 'M'
                lv = 2;
                break;

            case 29: // '\035'
            case 32: // ' '
            case 37: // '%'
            case 40: // '('
            case 45: // '-'
            case 62: // '>'
            case 65: // 'A'
            case 70: // 'F'
            case 73: // 'I'
            case 78: // 'N'
                lv = 3;
                break;

            case 33: // '!'
            case 41: // ')'
            case 66: // 'B'
            case 74: // 'J'
                lv = 4;
                break;

            case 132: 
                lv = x.getOperandLocalVariable().getIndex();
                break;

            default:
                lv = -1;
                break;
            }
            max = lv + 1 <= max ? max : lv + 1;
        }

        return max;
    }

    CFFactory getFactory()
    {
        return factory;
    }

    public void addLabel(String label, Instruction x)
    {
        if(Debug.DEBUG_CF)
        {
            if(x == null || label == null || x.getOwnerCodeAttribute() != this)
                throw new CFException(14);
            if(hLabelToInstruction.containsKey(label))
                throw new CFException(21, label);
        }
        hInstructionToLabel.put(x, label);
        hLabelToInstruction.put(label, x);
    }

    public Instruction getInstructionByLabel(String label)
    {
        if(Debug.DEBUG_CF && label == null)
        {
            throw new CFException(69, "label");
        } else
        {
            Instruction r = (Instruction)hLabelToInstruction.get(label);
            return r;
        }
    }

    public Instruction getInstructionByLabel(String label, boolean mustResolve)
    {
        Instruction r = getInstructionByLabel(label);
        if(mustResolve && r == null)
            throw new CFException(18, label);
        else
            return r;
    }

    public void transferLabels(Instruction x, Instruction y)
    {
        if(Debug.DEBUG_CF && (x == null || y == null || x.getOwnerCodeAttribute() != this || y.getOwnerCodeAttribute() != this || x == y))
            throw new CFException(14);
        for(Enumeration _enum = hInstructionToLabel.keys(); _enum.hasMoreElements();)
        {
            Object instruction = _enum.nextElement();
            Object label = hInstructionToLabel.get(instruction);
            if(instruction == x)
                hInstructionToLabel.put(y, label);
        }

        for(Enumeration _enum = hLabelToInstruction.keys(); _enum.hasMoreElements();)
        {
            Object label = _enum.nextElement();
            Object instruction = hLabelToInstruction.get(label);
            if(instruction == x)
                hLabelToInstruction.put(label, y);
        }

    }

    public String getCanonicalLabelOfInstruction(Instruction x)
    {
        return (String)hInstructionToLabel.get(x);
    }

    public String getCanonicalLabel(String label)
    {
        if(Debug.DEBUG_CF && label == null)
            throw new CFException(69, "label");
        Object x = hLabelToInstruction.get(label);
        if(Debug.DEBUG_CF && x == null)
            throw new CFException(62, label);
        String r = (String)hInstructionToLabel.get(x);
        if(Debug.DEBUG_CF && r == null)
            throw new CFException(64, new Object[] {
                label, x
            });
        else
            return r;
    }

    public Instruction createNOP()
    {
        return createInstruction().init(0);
    }

    public Instruction createDUP()
    {
        return createInstruction().init(89);
    }

    public Instruction createDUP_X1()
    {
        return createInstruction().init(90);
    }

    public Instruction createDUP_X2()
    {
        return createInstruction().init(91);
    }

    public Instruction createDUP2()
    {
        return createInstruction().init(92);
    }

    public Instruction createDUP2_X1()
    {
        return createInstruction().init(93);
    }

    public Instruction createDUP2_X2()
    {
        return createInstruction().init(94);
    }

    public Instruction createPOP()
    {
        return createInstruction().init(87);
    }

    public Instruction createPOP2()
    {
        return createInstruction().init(88);
    }

    public Instruction createSWAP()
    {
        return createInstruction().init(95);
    }

    public Instruction createATHROW()
    {
        return createInstruction().init(191);
    }

    public Instruction createBASTORE()
    {
        return createInstruction().init(84);
    }

    public Instruction createCASTORE()
    {
        return createInstruction().init(85);
    }

    public Instruction createSASTORE()
    {
        return createInstruction().init(86);
    }

    public Instruction createIASTORE()
    {
        return createInstruction().init(79);
    }

    public Instruction createFASTORE()
    {
        return createInstruction().init(81);
    }

    public Instruction createLASTORE()
    {
        return createInstruction().init(80);
    }

    public Instruction createDASTORE()
    {
        return createInstruction().init(82);
    }

    public Instruction createAASTORE()
    {
        return createInstruction().init(83);
    }

    public Instruction createBALOAD()
    {
        return createInstruction().init(51);
    }

    public Instruction createCALOAD()
    {
        return createInstruction().init(52);
    }

    public Instruction createSALOAD()
    {
        return createInstruction().init(53);
    }

    public Instruction createIALOAD()
    {
        return createInstruction().init(46);
    }

    public Instruction createFALOAD()
    {
        return createInstruction().init(48);
    }

    public Instruction createLALOAD()
    {
        return createInstruction().init(47);
    }

    public Instruction createDALOAD()
    {
        return createInstruction().init(49);
    }

    public Instruction createAALOAD()
    {
        return createInstruction().init(50);
    }

    public Instruction createACONST_NULL()
    {
        return createInstruction().init(1);
    }

    public Instruction createILOAD(LocalVariable lv)
    {
        return createInstruction().init(21, lv);
    }

    public Instruction createLLOAD(LocalVariable lv)
    {
        return createInstruction().init(22, lv);
    }

    public Instruction createFLOAD(LocalVariable lv)
    {
        return createInstruction().init(23, lv);
    }

    public Instruction createDLOAD(LocalVariable lv)
    {
        return createInstruction().init(24, lv);
    }

    public Instruction createALOAD(LocalVariable lv)
    {
        return createInstruction().init(25, lv);
    }

    public Instruction createISTORE(LocalVariable lv)
    {
        return createInstruction().init(54, lv);
    }

    public Instruction createLSTORE(LocalVariable lv)
    {
        return createInstruction().init(55, lv);
    }

    public Instruction createFSTORE(LocalVariable lv)
    {
        return createInstruction().init(56, lv);
    }

    public Instruction createDSTORE(LocalVariable lv)
    {
        return createInstruction().init(57, lv);
    }

    public Instruction createASTORE(LocalVariable lv)
    {
        return createInstruction().init(58, lv);
    }

    public Instruction createRET()
    {
        return createInstruction().init(169);
    }

    public Instruction createMONITORENTER()
    {
        return createInstruction().init(194);
    }

    public Instruction createMONITOREXIT()
    {
        return createInstruction().init(195);
    }

    public Instruction createLOAD(LocalVariable lv, String descriptor)
    {
        FieldInfo.checkFieldDescriptor(descriptor);
        switch(descriptor.charAt(0))
        {
        case 66: // 'B'
        case 67: // 'C'
        case 73: // 'I'
        case 83: // 'S'
        case 90: // 'Z'
            return createILOAD(lv);

        case 74: // 'J'
            return createLLOAD(lv);

        case 70: // 'F'
            return createFLOAD(lv);

        case 68: // 'D'
            return createDLOAD(lv);

        case 76: // 'L'
        case 91: // '['
            return createALOAD(lv);
        }
        throw new CFException(11, descriptor);
    }

    public Instruction createSTORE(LocalVariable lv, String descriptor)
    {
        FieldInfo.checkFieldDescriptor(descriptor);
        switch(descriptor.charAt(0))
        {
        case 66: // 'B'
        case 67: // 'C'
        case 73: // 'I'
        case 83: // 'S'
        case 90: // 'Z'
            return createISTORE(lv);

        case 74: // 'J'
            return createLSTORE(lv);

        case 70: // 'F'
            return createFSTORE(lv);

        case 68: // 'D'
            return createDSTORE(lv);

        case 76: // 'L'
        case 91: // '['
            return createASTORE(lv);
        }
        throw new CFException(11, descriptor);
    }

    public Instruction createInvokeStatic(CPInfo x)
    {
        if(Debug.DEBUG_CF)
        {
            if(x == null)
                throw new CFException(69, "name");
            if(x.getTag() != 10)
                throw new CFException(16);
        }
        return createInstruction().init(184, x);
    }

    public Instruction createInvokeStatic(String className, String methodName, String methodDescriptor)
    {
        return createInstruction().init(184, ownerMethod.getOwnerClassFile().createCPInfoMemberRef(10, className, methodName, methodDescriptor));
    }

    public Instruction createInvokeVirtual(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 10))
            throw new CFException(14);
        else
            return createInstruction().init(182, x);
    }

    public Instruction createInvokeVirtual(String className, String methodName, String methodDescriptor)
    {
        return createInstruction().init(182, ownerMethod.getOwnerClassFile().createCPInfoMemberRef(10, className, methodName, methodDescriptor));
    }

    public Instruction createInvokeSpecial(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 10))
            throw new CFException(14);
        else
            return createInstruction().init(183, x);
    }

    public Instruction createInvokeSpecial(String className, String methodName, String methodDescriptor)
    {
        return createInstruction().init(183, ownerMethod.getOwnerClassFile().createCPInfoMemberRef(10, className, methodName, methodDescriptor));
    }

    public Instruction createInvokeInterface(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 11))
            throw new CFException(14);
        else
            return createInstruction().init(185, x, MethodInfo.parseMethodDescriptorArguments(x.getValueNameAndType().getValueDescriptor().toPlainString()).length + 1);
    }

    public Instruction createInvokeInterface(String className, String methodName, String methodDescriptor)
    {
        return createInstruction().init(185, ownerMethod.getOwnerClassFile().createCPInfoMemberRef(11, className, methodName, methodDescriptor), MethodInfo.parseMethodDescriptorArguments(methodDescriptor).length + 1);
    }

    public Instruction createPutStatic(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 9))
            throw new CFException(14);
        else
            return createInstruction().init(179, x);
    }

    public Instruction createPutStatic(String className, String fieldName, String fieldDescriptor)
    {
        return createPutStatic(ownerMethod.getOwnerClassFile().createCPInfoMemberRef(9, className, fieldName, fieldDescriptor));
    }

    public Instruction createGetStatic(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 9))
            throw new CFException(14);
        else
            return createInstruction().init(178, x);
    }

    public Instruction createGetStatic(String className, String fieldName, String fieldDescriptor)
    {
        return createGetStatic(ownerMethod.getOwnerClassFile().createCPInfoMemberRef(9, className, fieldName, fieldDescriptor));
    }

    public Instruction createPutField(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 9))
            throw new CFException(14);
        else
            return createInstruction().init(181, x);
    }

    public Instruction createPutField(String className, String fieldName, String fieldDescriptor)
    {
        return createPutField(ownerMethod.getOwnerClassFile().createCPInfoMemberRef(9, className, fieldName, fieldDescriptor));
    }

    public Instruction createGetField(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 9))
            throw new CFException(14);
        else
            return createInstruction().init(180, x);
    }

    public Instruction createGetField(String className, String fieldName, String fieldDescriptor)
    {
        return createGetField(ownerMethod.getOwnerClassFile().createCPInfoMemberRef(9, className, fieldName, fieldDescriptor));
    }

    public Instruction createLDC(CPInfo x)
    {
        if(Debug.DEBUG_CF && x == null)
            throw new CFException(14);
        int opcode;
        switch(x.getTag())
        {
        case 5: // '\005'
        case 6: // '\006'
            opcode = 20;
            break;

        case 3: // '\003'
        case 4: // '\004'
        case 8: // '\b'
            opcode = x.getIndex() <= 255 ? 18 : 19;
            break;

        case 7: // '\007'
        default:
            throw new CFException(14);
        }
        return createInstruction().init(opcode, x);
    }

    public Instruction createLDC(int x)
    {
        switch(x)
        {
        case -1: 
            return createInstruction().init(2);

        case 0: // '\0'
            return createInstruction().init(3);

        case 1: // '\001'
            return createInstruction().init(4);

        case 2: // '\002'
            return createInstruction().init(5);

        case 3: // '\003'
            return createInstruction().init(6);

        case 4: // '\004'
            return createInstruction().init(7);

        case 5: // '\005'
            return createInstruction().init(8);
        }
        if(x >= -128 && x <= 127)
            return createInstruction().init(16, x);
        if(x >= -32768 && x <= 32767)
            return createInstruction().init(17, x);
        else
            return createLDC(ownerMethod.getOwnerClassFile().createCPInfoInteger(x));
    }

    public Instruction createLDC(String s)
    {
        return createLDC(ownerMethod.getOwnerClassFile().createCPInfoString(s));
    }

    public Instruction createGOTO(Instruction x)
    {
        return createBranch(167, x);
    }

    public Instruction createGOTO(String label)
    {
        return createBranch(167, label);
    }

    public Instruction createBranch(int opcode, Instruction x)
    {
        if(Debug.DEBUG_CF && (INSTRUCTION_OPERANDS[opcode] != 5 || x == null || x.getOwnerCodeAttribute() != this))
            throw new CFException(14);
        else
            return createInstruction().init(opcode, x);
    }

    public Instruction createBranch(int opcode, String label)
    {
        if(Debug.DEBUG_CF && (INSTRUCTION_OPERANDS[opcode] != 5 || label == null))
            throw new CFException(14);
        else
            return createInstruction().init(opcode, label);
    }

    public Instruction createRETURN()
    {
        switch(MethodInfo.parseMethodDescriptorReturnType(ownerMethod.getDescriptor()).charAt(0))
        {
        case 86: // 'V'
            return createInstruction().init(177);

        case 66: // 'B'
        case 67: // 'C'
        case 73: // 'I'
        case 83: // 'S'
        case 90: // 'Z'
            return createInstruction().init(172);

        case 74: // 'J'
            return createInstruction().init(173);

        case 70: // 'F'
            return createInstruction().init(174);

        case 68: // 'D'
            return createInstruction().init(175);
        }
        return createInstruction().init(176);
    }

    public Instruction createCHECKCAST(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 7))
            throw new CFException(16);
        else
            return createInstruction().init(192, x);
    }

    public Instruction createCHECKCAST(String className)
    {
        return createInstruction().init(192, ownerMethod.getOwnerClassFile().createCPInfoClass(className));
    }

    public Instruction createINSTANCEOF(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 7))
            throw new CFException(14);
        else
            return createInstruction().init(193, x);
    }

    public Instruction createINSTANCEOF(String className)
    {
        return createInstruction().init(193, ownerMethod.getOwnerClassFile().createCPInfoClass(className));
    }

    public Instruction createANEWARRAY(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 7))
            throw new CFException(14);
        else
            return createInstruction().init(189, x);
    }

    public Instruction createANEWARRAY(String className)
    {
        return createInstruction().init(189, ownerMethod.getOwnerClassFile().createCPInfoClass(className));
    }

    public Instruction createNEWARRAY(String descriptor)
    {
        if(descriptor == null)
            throw new CFException(14);
        if(descriptor.length() == 1)
            switch(descriptor.charAt(0))
            {
            case 90: // 'Z'
                return createInstruction().init(188, 4);

            case 66: // 'B'
                return createInstruction().init(188, 8);

            case 67: // 'C'
                return createInstruction().init(188, 5);

            case 83: // 'S'
                return createInstruction().init(188, 9);

            case 73: // 'I'
                return createInstruction().init(188, 10);

            case 70: // 'F'
                return createInstruction().init(188, 6);

            case 74: // 'J'
                return createInstruction().init(188, 11);

            case 68: // 'D'
                return createInstruction().init(188, 7);
            }
        throw new CFException(15, descriptor);
    }

    public Instruction createNEWARRAY(int atype)
    {
        switch(atype)
        {
        case 4: // '\004'
        case 5: // '\005'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
        case 9: // '\t'
        case 10: // '\n'
        case 11: // '\013'
            return createInstruction().init(188, atype);
        }
        throw new CFException(2, new Integer(atype));
    }

    public Instruction createNEW(CPInfo x)
    {
        if(Debug.DEBUG_CF && (x == null || x.getTag() != 7))
            throw new CFException(14);
        else
            return createInstruction().init(187, x);
    }

    public Instruction createNEW(String className)
    {
        return createNEW(ownerMethod.getOwnerClassFile().createCPInfoClass(className));
    }

    public void checkBranches()
    {
        for(int i = 0; i < nInstructions; i++)
            instructions[i].checkBranches();

    }

    private CFFactory factory;
    private MethodInfo ownerMethod;
    private Instruction instructions[];
    private int nInstructions;
    private ExceptionHandler exceptionHandlers[];
    private int nExceptionHandlers;
    private AttributeContainer attributeContainer;
    private int originalMaxStack;
    private int originalMaxLocals;
    private Hashtable hLabelToInstruction;
    private Hashtable hInstructionToLabel;
}
