package com.fmi.bytecode.parser.internal;

import java.util.Iterator;
import java.util.Collection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;

public final class CFSerializer implements Constants
{
    private CFFactory factory;
    private Instruction[] instructions;
    private int[] offsetTable;
    
    CFSerializer(final CFFactory factory) {
        this.factory = factory;
    }
    
    public void serialize(final String filename, final ClassFile cf, final CFSerializerOptions options) throws IOException {
        this.serialize(new File(filename), cf, options);
    }
    
    public void serialize(final File file, final ClassFile cf, final CFSerializerOptions options) throws IOException {
        file.getParentFile().mkdirs();
        final DataOutputStream dis = new DataOutputStream(new FileOutputStream(file));
        try {
            this.serialize0(dis, cf, options);
        }
        finally {
            dis.close();
        }
        dis.close();
    }
    
    public void serialize(final DataOutput out, final ClassFile cf, final CFSerializerOptions options) throws IOException {
        this.serialize0(out, cf, options);
    }
    
    public void serialize(final OutputStream out, final ClassFile cf, final CFSerializerOptions options) throws IOException {
        this.serialize0(new DataOutputStream(out), cf, options);
    }
    
    public byte[] serializeToByteArray(final ClassFile cf, final CFSerializerOptions options) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final DataOutputStream out2 = new DataOutputStream(out);
        this.serialize0(out2, cf, options);
        out2.flush();
        return out.toByteArray();
    }
    
    private void serialize0(final DataOutput out, final ClassFile cf, CFSerializerOptions options) throws IOException {
        options = ((options == null) ? CFSerializerOptions.DEFAULT : options);
        out.writeInt(-889275714);
        out.writeShort(cf.getMinorVersion());
        out.writeShort(cf.getMajorVersion());
        final CPInfo[] cp = cf.getCPInfosArray();
        final int ncp = cp.length;
        out.writeShort(ncp);
        for (int i = 1; i < ncp; ++i) {
            final CPInfo x = cp[i];
            final int tag = x.getTag();
            out.writeByte(tag);
            switch (tag) {
                case 7: {
                    out.writeShort(x.getValueNameIndex());
                    break;
                }
                case 8: {
                    out.writeShort(x.getValueStringIndex());
                    break;
                }
                case 9:
                case 10:
                case 11: {
                    out.writeShort(x.getValueClassIndex());
                    out.writeShort(x.getValueNameAndTypeIndex());
                    break;
                }
                case 12: {
                    out.writeShort(x.getValueNameIndex());
                    out.writeShort(x.getValueDescriptorIndex());
                    break;
                }
                case 3:
                case 4: {
                    out.writeInt(x.getValueInt());
                    break;
                }
                case 5:
                case 6: {
                    out.writeLong(x.getValueLong());
                    ++i;
                    break;
                }
                case 1: {
                    out.writeUTF(x.getString());
                    break;
                }
                default: {
                    throw new CFException(81, (Object)new Integer(tag));
                }
            }
        }
        out.writeShort(cf.getAccessFlags());
        final CPInfo thisClass = cf.getThisClass();
        if (Debug.DEBUG_CF && thisClass == null) {
            throw new CFException(84);
        }
        out.writeShort(thisClass.getIndex());
        final CPInfo superClass = cf.getSuperClass();
        if (Debug.DEBUG_CF && superClass == null) {
            throw new CFException(83);
        }
        out.writeShort(superClass.getIndex());
        final int nInterfaces = cf.getNInterfaces();
        out.writeShort(nInterfaces);
        for (int j = 0; j < nInterfaces; ++j) {
            out.writeShort(cf.getInterface(j).getIndex());
        }
        final FieldInfo[] fields = cf.getFieldsArray();
        final int nFields = fields.length;
        out.writeShort(nFields);
        for (int k = 0; k < nFields; ++k) {
            this.serializeMember(out, cf, (MemberInfo)fields[k], options);
        }
        final MethodInfo[] methods = cf.getMethodsArray();
        final int nMethods = methods.length;
        out.writeShort(nMethods);
        for (int l = 0; l < nMethods; ++l) {
            this.serializeMember(out, cf, (MemberInfo)methods[l], options);
        }
        this.serializeAttributes(out, cf, (Attributed)cf, options);
    }
    
    private void serializeMember(final DataOutput out, final ClassFile cf, final MemberInfo x, final CFSerializerOptions options) throws IOException {
        out.writeShort(x.getAccessFlags());
        out.writeShort(x.getNameCPInfo().getIndex());
        out.writeShort(x.getDescriptorCPInfo().getIndex());
        this.serializeAttributes(out, cf, (Attributed)x, options);
    }
    
    private void serializeAttributes(final DataOutput out, final ClassFile cf, final Attributed attributed, final CFSerializerOptions options) throws IOException {
        final int nAttributes = attributed.getNAttributes();
        out.writeShort(nAttributes);
        final AttributeInfo ca = attributed.getAttribute("Code");
        if (ca != null) {
            this.serializeAttribute(out, cf, attributed, ca, options);
        }
        for (int i = 0; i < nAttributes; ++i) {
            final AttributeInfo a = attributed.getAttribute(i);
            if (a != ca) {
                this.serializeAttribute(out, cf, attributed, a, options);
            }
        }
    }
    
    private void serializeAttribute(final DataOutput out, final ClassFile cf, final Attributed attributed, final AttributeInfo a, final CFSerializerOptions options) throws IOException {
        final String name = a.getName();
        out.writeShort(cf.getCPInfoUtf8(name).getIndex());
        if (a instanceof CodeAttribute) {
            this.serializeCodeAttribute(out, cf, (CodeAttribute)a, options);
        }
        else if (a instanceof LineNumberTableAttribute) {
            if (!options.recomputeLineNumberTable) {
                final LineNumberTableAttribute lineNumberTableAttribute = (LineNumberTableAttribute)a;
                final Instruction[] startPCs = lineNumberTableAttribute.getStartPCsArray();
                final int[] lineNumbers = lineNumberTableAttribute.getLineNumbersArray();
                final int n = startPCs.length;
                out.writeInt(2 + 4 * n);
                out.writeShort(n);
                for (int i = 0; i < n; ++i) {
                    final Instruction x = startPCs[i];
                    out.writeShort((x == null) ? this.offsetTable[this.offsetTable.length - 1] : this.offsetTable[x.getIndex()]);
                    out.writeShort(lineNumbers[i]);
                }
            }
            else {
                int n2 = 0;
                final int[] startPCs2 = new int[this.instructions.length];
                final int[] lineNumbers = new int[this.instructions.length];
                int lastLineNumber = -1;
                for (int i = 0; i < this.instructions.length; ++i) {
                    final int lineNumber = this.instructions[i].getLineNumber();
                    if (lineNumber != lastLineNumber) {
                        startPCs2[n2] = this.offsetTable[i];
                        lastLineNumber = (lineNumbers[n2] = lineNumber);
                        ++n2;
                    }
                }
                out.writeInt(2 + 4 * n2);
                out.writeShort(n2);
                for (int i = 0; i < n2; ++i) {
                    out.writeShort(startPCs2[i]);
                    out.writeShort(lineNumbers[i]);
                }
            }
        }
        else if (a instanceof LocalVariableTableAttribute) {
            final LocalVariableTableAttribute localVariableTableAttribute = (LocalVariableTableAttribute)a;
            final LocalVariableTableEntry[] entries = localVariableTableAttribute.getEntriesArray();
            out.writeInt(2 + 10 * entries.length);
            out.writeShort(entries.length);
            for (int j = 0; j < entries.length; ++j) {
                final int startOffset = this.offsetTable[entries[j].getStartPC().getIndex()];
                final Instruction endPC = entries[j].getEndPC();
                final int endOffset = (endPC == null) ? this.offsetTable[this.offsetTable.length - 1] : this.offsetTable[endPC.getIndex()];
                out.writeShort(startOffset);
                out.writeShort(endOffset - startOffset);
                out.writeShort(entries[j].getName().getIndex());
                out.writeShort(entries[j].getDescriptor().getIndex());
                out.writeShort(entries[j].getLocalVariable().getIndex());
            }
        }
        else if (a instanceof ExceptionsAttribute) {
            final ExceptionsAttribute exceptionsAttribute = (ExceptionsAttribute)a;
            final int nExceptions = exceptionsAttribute.getNExceptions();
            out.writeInt(2 * nExceptions + 2);
            out.writeShort(nExceptions);
            for (int j = 0; j < nExceptions; ++j) {
                out.writeShort(exceptionsAttribute.getException(j).getIndex());
            }
        }
        else if (a instanceof ConstantValueAttribute) {
            out.writeInt(2);
            out.writeShort(((ConstantValueAttribute)a).getConstantValue().getIndex());
        }
        else if (a instanceof SyntheticAttribute || a instanceof DeprecatedAttribute) {
            out.writeInt(0);
        }
        else if (a instanceof SourceFileAttribute) {
            out.writeInt(2);
            out.writeShort(((SourceFileAttribute)a).getSourceFile().getIndex());
        }
        else if (a instanceof InnerClassesAttribute) {
            final InnerClassesAttributeEntry[] entries2 = ((InnerClassesAttribute)a).getEntries();
            final int nEntries = entries2.length;
            out.writeInt(8 * nEntries + 2);
            out.writeShort(nEntries);
            for (final InnerClassesAttributeEntry entry : entries2) {
                out.writeShort(entry.getInnerClass().getIndex());
                out.writeShort(entry.getOuterClass().getIndex());
                out.writeShort(entry.getInnerNameCPInfo().getIndex());
                out.writeShort(entry.getInnerAccessFlags());
            }
        }
        else if (a instanceof SignatureAttribute) {
            out.writeInt(2);
            out.writeShort(((SignatureAttribute)a).getSignatureIndex().getIndex());
        }
        else if (a instanceof AnnotationAttribute) {
            final AnnotationAttribute annotAttr = (AnnotationAttribute)a;
            out.writeInt(annotAttr.getLenght());
            final AnnotationStruct[] annotations = annotAttr.getAnnotations();
            final int annotCount = annotations.length;
            out.writeShort(annotCount);
            for (int k = 0; k < annotCount; ++k) {
                this.serializeAnnotaiton(annotations[k], out);
            }
        }
        else if (a instanceof ParameterAnnotationAttribute) {
            final ParameterAnnotationAttribute annotAttr2 = (ParameterAnnotationAttribute)a;
            out.writeInt(annotAttr2.getLenght());
            final AnnotationStruct[][] allAnnotations = annotAttr2.getAllAnnotations();
            final int paramsCount = allAnnotations.length;
            out.writeByte(paramsCount);
            for (int k = 0; k < paramsCount; ++k) {
                final int annotCount2 = allAnnotations[k].length;
                out.writeShort(annotCount2);
                for (int l = 0; l < annotCount2; ++l) {
                    this.serializeAnnotaiton(allAnnotations[k][l], out);
                }
            }
        }
        else if (a instanceof AnnotationDefaultAttribute) {
            final AnnotationDefaultAttribute annotAttr3 = (AnnotationDefaultAttribute)a;
            out.writeInt(annotAttr3.getValue().getLenght());
            this.serializeAnnotaitonMember(annotAttr3.getValue(), out);
        }
        else if (a instanceof EnclosingMethodAttribute) {
            final EnclosingMethodAttribute enclosingMethodAttr = (EnclosingMethodAttribute)a;
            out.writeInt(4);
            out.writeShort(enclosingMethodAttr.getEnclosingClass().getIndex());
            final CPInfo enclosingMethod = enclosingMethodAttr.getEnclosingMethod();
            if (enclosingMethod == null) {
                out.writeShort(0);
            }
            else {
                out.writeShort(enclosingMethod.getIndex());
            }
        }
        else if (a instanceof SourceDebugExtensionAttribute) {
            final SourceDebugExtensionAttribute srcDbgAttr = (SourceDebugExtensionAttribute)a;
            final byte[] debugExtensionBytes = srcDbgAttr.getDebugExtension().getBytes("UTF-8");
            out.writeInt(debugExtensionBytes.length);
            out.write(debugExtensionBytes);
        }
        else {
            final byte[] value = ((UnknownAttribute)a).getValue();
            out.writeInt(value.length);
            out.write(value);
        }
    }
    
    private void serializeAnnotaiton(final AnnotationStruct annotation, final DataOutput out) throws IOException {
        out.writeShort(annotation.getType().getIndex());
        final Collection<AnnotationPairValue> annotationMembers = annotation.getElementValuePairs().values();
        out.writeShort(annotationMembers.size());
        for (final AnnotationPairValue member : annotationMembers) {
            out.writeShort(member.getNameIdx().getIndex());
            this.serializeAnnotaitonMember(member, out);
        }
    }
    
    private void serializeAnnotaitonMember(final AnnotationPairValue member, final DataOutput out) throws IOException {
        out.writeByte(member.getTag());
        if (member.getEnumTypeAndName() != null) {
            out.writeShort(member.getEnumTypeAndName().getIndex());
            out.writeShort(member.getConstant().getIndex());
        }
        else if (member.getConstant() != null) {
            out.writeShort(member.getConstant().getIndex());
        }
        else if (member.getNested() != null) {
            this.serializeAnnotaiton(member.getNested(), out);
        }
        else if (member.getArray() != null) {
            final AnnotationPairValue[] members = member.getArray();
            out.writeShort(members.length);
            for (int i = 0; i < members.length; ++i) {
                this.serializeAnnotaitonMember(members[i], out);
            }
        }
    }
    
    private void serializeCodeAttribute(final DataOutput out, final ClassFile cf, final CodeAttribute ca, final CFSerializerOptions options) throws IOException {
        final ByteArrayOutputStream out2 = new ByteArrayOutputStream();
        final DataOutputStream out3 = new DataOutputStream(out2);
        this.instructions = ca.getInstructionsArray();
        final ExceptionHandler[] exceptionHandlers = ca.getExceptionHandlersArray();
        this.offsetTable = calculateOffsets(this.instructions);
        final int[][] successorTable = calculateSuccessorsTable(this.instructions);
        final int[] stackSizes = calculateStackSizes(this.instructions, exceptionHandlers, successorTable);
        final int nInstructions = this.instructions.length;
        final int codeLength = this.offsetTable[nInstructions];
        final int maxStack = options.recomputeMaxStack ? calculateMaxStack(stackSizes) : ca.getOriginalMaxStack();
        int maxLocals;
        if (options.recomputeMaxLocals) {
            maxLocals = ca.calculateMaxLocals();
            if (options.preserveOriginalMaxLocalsIfGreaterThanComputed) {
                maxLocals = Math.max(maxLocals, ca.getOriginalMaxLocals());
            }
        }
        else {
            maxLocals = ca.getOriginalMaxLocals();
        }
        out3.writeShort(maxStack);
        out3.writeShort(maxLocals);
        out3.writeInt(codeLength);
        for (int i = 0; i < nInstructions; ++i) {
            final Instruction x = this.instructions[i];
            final int opcode = x.getOpcode();
            switch (CFSerializer.INSTRUCTION_OPERANDS[opcode]) {
                case 1: {
                    out3.writeByte(opcode);
                    break;
                }
                case 2: {
                    out3.writeByte(opcode);
                    final int operand0 = x.getOperandInt();
                    switch (opcode) {
                        case 16: {
                            out3.writeByte(operand0);
                            continue;
                        }
                        case 17: {
                            out3.writeShort(operand0);
                            continue;
                        }
                        case 188: {
                            out3.writeByte(operand0);
                            continue;
                        }
                        default: {
                            throw new CFException(85);
                        }
                    }
                }
                case 3: {
                    final CPInfo operandCPInfo = x.getOperandCPInfo();
                    final int operandIndex = operandCPInfo.getIndex();
                    if (opcode == 18) {
                        out3.writeByte(18);
                        out3.writeByte(operandIndex);
                        break;
                    }
                    out3.writeByte(opcode);
                    out3.writeShort(operandIndex);
                    break;
                }
                case 4: {
                    final int operand0 = x.getOperandLocalVariable().getIndex();
                    if (operand0 > 255) {
                        out3.writeByte(196);
                        out3.writeByte(opcode);
                        out3.writeShort(operand0);
                        break;
                    }
                    out3.writeByte(opcode);
                    out3.writeByte(operand0);
                    break;
                }
                case 5: {
                    final Instruction branch = x.getOperandInstruction();
                    out3.writeByte(opcode);
                    if (opcode == 200 || opcode == 201) {
                        out3.writeInt(this.offsetTable[branch.getIndex()] - this.offsetTable[x.getIndex()]);
                        break;
                    }
                    out3.writeShort(this.offsetTable[branch.getIndex()] - this.offsetTable[x.getIndex()]);
                    break;
                }
                case 6: {
                    final int xOffset = this.offsetTable[x.getIndex()];
                    out3.writeByte(opcode);
                    final TableSwitchTable table = x.getOperandTableSwitchTable();
                    for (int padding = AbstractSwitchTable.getPadding(xOffset), j = 0; j < padding; ++j) {
                        out3.writeByte(0);
                    }
                    out3.writeInt(this.offsetTable[table.getDefaultBranch().getIndex()] - xOffset);
                    final int low = table.getLowValue();
                    final int high = table.getHighValue();
                    final int nBranches = table.getNBranches();
                    out3.writeInt(low);
                    out3.writeInt(high);
                    for (int k = 0; k < nBranches; ++k) {
                        out3.writeInt(this.offsetTable[table.getBranchByIndex(k).getIndex()] - xOffset);
                    }
                    break;
                }
                case 7: {
                    final int xOffset = this.offsetTable[x.getIndex()];
                    out3.writeByte(opcode);
                    final LookupSwitchTable table2 = x.getOperandLookupSwitchTable();
                    for (int padding = AbstractSwitchTable.getPadding(xOffset), j = 0; j < padding; ++j) {
                        out3.writeByte(0);
                    }
                    out3.writeInt(this.offsetTable[table2.getDefaultBranch().getIndex()] - xOffset);
                    final int nBranches2 = table2.getNBranches();
                    out3.writeInt(nBranches2);
                    for (int l = 0; l < nBranches2; ++l) {
                        out3.writeInt(table2.getMatch(l));
                        out3.writeInt(this.offsetTable[table2.getBranchByIndex(l).getIndex()] - xOffset);
                    }
                    break;
                }
                case 8: {
                    final int lvIndex = x.getOperandLocalVariable().getIndex();
                    final int increment = x.getOperandInt();
                    if (lvIndex > 255 || increment > 255) {
                        out3.writeByte(196);
                        out3.writeByte(opcode);
                        out3.writeShort(lvIndex);
                        out3.writeShort(increment);
                        break;
                    }
                    out3.writeByte(opcode);
                    out3.writeByte(lvIndex);
                    out3.writeByte(increment);
                    break;
                }
                case 9: {
                    final int cpIndex = x.getOperandCPInfo().getIndex();
                    final int value = x.getOperandInt();
                    out3.writeByte(opcode);
                    out3.writeShort(cpIndex);
                    out3.writeByte(value);
                    if (opcode == 185) {
                        out3.writeByte(0);
                        break;
                    }
                    break;
                }
                default: {
                    throw new CFException(86, new Object[] { CFSerializer.MNEMONIC[opcode], new Integer(opcode) });
                }
            }
        }
        final int nExceptionHandlers = ca.getNExceptionHandlers();
        out3.writeShort(nExceptionHandlers);
        for (int m = 0; m < nExceptionHandlers; ++m) {
            final ExceptionHandler handler = ca.getExceptionHandler(m);
            out3.writeShort(this.offsetTable[handler.getStartPC().getIndex()]);
            final Instruction endPCInclusive = handler.getEndPCInclusive();
            if (endPCInclusive == null) {
                throw new CFException(79, (Object)ca.getOwnerMethod());
            }
            final Instruction endPCExclusive = endPCInclusive.getNextInstruction();
            out3.writeShort((endPCExclusive == null) ? this.offsetTable[this.offsetTable.length - 1] : this.offsetTable[endPCExclusive.getIndex()]);
            out3.writeShort(this.offsetTable[handler.getHandlerPC().getIndex()]);
            out3.writeShort(handler.getCatchType().getIndex());
        }
        this.serializeAttributes(out3, cf, (Attributed)ca, options);
        out3.flush();
        final byte[] ba = out2.toByteArray();
        out.writeInt(ba.length);
        out.write(ba);
    }
    
    public static int[] calculateOffsets(final Instruction[] instructions) {
        final int nInstructions = instructions.length;
        final int[] r = new int[nInstructions + 1];
        int offset = 0;
        for (int i = 0; i < nInstructions; ++i) {
            r[i] = offset;
            final Instruction x = instructions[i];
            final int opcode = x.getOpcode();
            switch (opcode) {
                case 21:
                case 22:
                case 23:
                case 24:
                case 25:
                case 54:
                case 55:
                case 56:
                case 57:
                case 58:
                case 169: {
                    final int lvIndex = x.getOperandLocalVariable().getIndex();
                    offset += ((lvIndex > 255) ? 4 : 2);
                    break;
                }
                case 132: {
                    final int lvIndex = x.getOperandLocalVariable().getIndex();
                    final int increment = x.getOperandInt();
                    offset += ((lvIndex > 255 || increment > 255) ? 6 : 3);
                    break;
                }
                case 170:
                case 171: {
                    final AbstractSwitchTable table = x.getOperandAbstractSwitchTable();
                    final int padding = 4 - (offset + 1 & 0x3) & 0x3;
                    offset += ((opcode == 170) ? (padding + 13 + 4 * table.getNBranches()) : (padding + 9 + 8 * table.getNBranches()));
                    break;
                }
                default: {
                    final int l = CFSerializer.INSTRUCTION_LENGTH[opcode];
                    if (l < 0) {
                        throw new CFException(75, new Object[] { CFSerializer.MNEMONIC[opcode], new Integer(opcode) });
                    }
                    offset += l;
                    break;
                }
            }
        }
        r[nInstructions] = offset;
        return r;
    }
    
    public static int[][] calculateSuccessorsTable(final Instruction[] instructions) {
        final int nInstructions = instructions.length;
        final int[][] r = new int[nInstructions][];
        for (int i = 0; i < nInstructions; ++i) {
            final int opcode = instructions[i].getOpcode();
            switch (opcode) {
                case 169:
                case 172:
                case 173:
                case 174:
                case 175:
                case 176:
                case 177:
                case 191: {
                    r[i] = CFSerializer.INT_ARRAY_0;
                    break;
                }
                case 167:
                case 200: {
                    r[i] = new int[] { instructions[i].getOperandInstruction().getIndex() };
                    break;
                }
                case 153:
                case 154:
                case 155:
                case 156:
                case 157:
                case 158:
                case 159:
                case 160:
                case 161:
                case 162:
                case 163:
                case 164:
                case 165:
                case 166:
                case 168:
                case 198:
                case 199:
                case 201: {
                    r[i] = new int[] { instructions[i].getOperandInstruction().getIndex(), i + 1 };
                    break;
                }
                case 170:
                case 171: {
                    final AbstractSwitchTable table = instructions[i].getOperandAbstractSwitchTable();
                    r[i] = table.getSuccessorIndicesArray();
                    break;
                }
                default: {
                    if (i < nInstructions - 1) {
                        r[i] = new int[] { i + 1 };
                        break;
                    }
                    r[i] = CFSerializer.INT_ARRAY_0;
                    break;
                }
            }
        }
        return r;
    }
    
    public static int[] calculateStackSizes(final Instruction[] instructions, final ExceptionHandler[] exceptionHandlers, final int[][] successorsTable) {
        final int nInstructions = instructions.length;
        final int nExceptionHandlers = exceptionHandlers.length;
        final int[] stackSize = new int[nInstructions];
        final int[] status = new int[nInstructions];
        for (int i = 0; i < nExceptionHandlers; ++i) {
            final int h = exceptionHandlers[i].getHandlerPC().getIndex();
            status[h] = (stackSize[h] = 1);
        }
        status[stackSize[0] = 0] = 1;
        boolean isFinished;
        do {
            isFinished = true;
            for (int j = 0; j < nInstructions; ++j) {
                if (status[j] == 1) {
                    isFinished = false;
                    status[j] = 2;
                    final int[] successors = successorsTable[j];
                    for (int k = 0; k < successors.length; ++k) {
                        final int successor = successors[k];
                        int expectedStackSize = stackSize[j] - instructions[j].getStackDecrease() + instructions[j].getStackIncrease();
                        if (k == 1) {
                            final int opcode = instructions[j].getOpcode();
                            if (opcode == 168 || opcode == 201) {
                                expectedStackSize = 0;
                            }
                        }
                        if (expectedStackSize < 0) {
                            if (Debug.DEBUG_CF) {
                                dumpCode(instructions, successorsTable, status, stackSize);
                            }
                            throw new CFException(82, (Object)new Integer(j));
                        }
                        if (status[successor] == 0) {
                            isFinished = false;
                            stackSize[successor] = expectedStackSize;
                            status[successor] = 1;
                        }
                        else if (stackSize[successor] != expectedStackSize) {
                            if (Debug.DEBUG_CF) {
                                dumpCode(instructions, successorsTable, status, stackSize);
                            }
                            throw new CFException(80, new Object[] { instructions[0].getOwnerCodeAttribute().getOwnerMethod(), new Integer(stackSize[successor]), new Integer(expectedStackSize), CFSerializer.MNEMONIC[instructions[j].getOpcode()], new Integer(successor), CFSerializer.MNEMONIC[instructions[successor].getOpcode()] });
                        }
                    }
                }
            }
        } while (!isFinished);
        return stackSize;
    }
    
    private static void dumpCode(final Instruction[] instructions, final int[][] successorsTable, final int[] status, final int[] stackSize) {
        final int nInstructions = instructions.length;
        final int[] offsetTable = calculateOffsets(instructions);
        System.out.println("DUMP === START STACK SIZES DUMP FOR " + instructions[0].getOwnerCodeAttribute().getOwnerMethod());
        for (int k = 0; k < nInstructions; ++k) {
            System.out.print("DUMP   [index:" + k + "] [offset: _" + offsetTable[k] + "] '" + instructions[k] + "' stack: " + stackSize[k] + ((status[k] == 1) ? ", MARKED" : ((status[k] == 0) ? ", NOT PROCESSED" : "")) + ", successors: (");
            for (int u = 0; u < successorsTable[k].length; ++u) {
                System.out.print(" " + successorsTable[k][u]);
            }
            System.out.println(" )");
        }
        System.out.println("DUMP === END STACK SIZES DUMP ");
    }
    
    public static int calculateMaxStack(final int[] stackSizes) {
        int r = 0;
        for (int nInstructions = stackSizes.length, i = 0; i < nInstructions; ++i) {
            if (r < stackSizes[i]) {
                r = stackSizes[i];
            }
        }
        return r;
    }
}