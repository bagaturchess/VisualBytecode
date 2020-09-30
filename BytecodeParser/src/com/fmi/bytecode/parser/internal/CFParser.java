package com.fmi.bytecode.parser.internal;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public final class CFParser implements Constants
{
    private CFFactory factory;
    private Instruction[] offsetTable;
    
    CFParser(final CFFactory factory) {
        this.factory = factory;
    }
    
    public ClassFile parse(final byte[] bytecode, final CFParserOptions options) throws IOException {
        return this.parse0(bytecode.clone(), options);
    }
    
    public ClassFile parse(final String filename, final CFParserOptions options) throws IOException {
        final FileInputStream fis = new FileInputStream(filename);
        try {
            return this.parse(fis, options);
        }
        finally {
            fis.close();
        }
    }
    
    public ClassFile parse(final File file, final CFParserOptions options) throws IOException {
        final FileInputStream fis = new FileInputStream(file);
        try {
            return this.parse(fis, options);
        }
        finally {
            fis.close();
        }
    }
    
    public ClassFile parse(final InputStream in0, final CFParserOptions options) throws IOException {
        return this.parse0(ArrayUtils.readFully(in0), options);
    }
    
    ClassFile parse0(final byte[] bytecode, CFParserOptions options) throws IOException {
        options = ((options == null) ? CFParserOptions.DEFAULT : options);
        final ByteArrayDataInput in = new ByteArrayDataInput(bytecode);
        final ClassFile cf = new ClassFile(this.factory, bytecode);
        cf.setOffsetStartAndEnd(0, bytecode.length);
        final int magic = in.readInt();
        if (magic != -889275714) {
            throw new CFException(72, new Object[] { "0x" + StringUtils.toHexString((long)magic, 8), "0x" + StringUtils.toHexString(-889275714L, 8) });
        }
        final int minorVersion = in.readUnsignedShort();
        final int majorVersion = in.readUnsignedShort();
        cf.setVersion(majorVersion, minorVersion);
        for (int ncp = in.readUnsignedShort(), i = 1; i < ncp; ++i) {
            final int offsetStart = in.getPosition();
            final int tag = in.readUnsignedByte();
            CPInfo x = null;
            switch (tag) {
                case 7:
                case 8: {
                    final int value0 = in.readUnsignedShort();
                    x = new CPInfo(cf, tag, value0, 0, (String)null, i);
                    break;
                }
                case 9:
                case 10:
                case 11:
                case 12: {
                    final int value0 = in.readUnsignedShort();
                    final int value2 = in.readUnsignedShort();
                    x = new CPInfo(cf, tag, value0, value2, (String)null, i);
                    break;
                }
                case 3:
                case 4: {
                    final int value0 = in.readInt();
                    x = new CPInfo(cf, tag, value0, 0, (String)null, i);
                    break;
                }
                case 5:
                case 6: {
                    final int value0 = in.readInt();
                    final int value2 = in.readInt();
                    x = new CPInfo(cf, tag, value0, value2, (String)null, i);
                    cf.addCPInfo(x);
                    ++i;
                    break;
                }
                case 1: {
                    final String s = in.readUTF();
                    x = new CPInfo(cf, tag, 0, 0, s, i);
                    break;
                }
                default: {
                    throw new CFException(74, new Object[] { new Integer(tag), new Integer(i) });
                }
            }
            final int offsetEnd = in.getPosition();
            x.setOffsetStartAndEnd(offsetStart, offsetEnd);
            cf.addCPInfo(x);
        }
        cf.setAccessFlags(in.readUnsignedShort());
        cf.setThisClass(cf.getCPInfo(in.readUnsignedShort()));
        cf.setSuperClass(cf.getCPInfo(in.readUnsignedShort()));
        for (int nInterfaces = in.readUnsignedShort(), j = 0; j < nInterfaces; ++j) {
            cf.addInterface(cf.getCPInfo(in.readUnsignedShort()));
        }
        for (int nFields = in.readUnsignedShort(), k = 0; k < nFields; ++k) {
            final int offsetStart2 = in.getPosition();
            final int accessFlags = in.readUnsignedShort();
            final CPInfo name = cf.getCPInfo(in.readUnsignedShort());
            final CPInfo descriptor = cf.getCPInfo(in.readUnsignedShort());
            final FieldInfo f = new FieldInfo(this.factory, cf, accessFlags, name, descriptor);
            this.parseAttributes(in, cf, (Attributed)f, options);
            cf.addFieldInfo(f);
            final int offsetEnd2 = in.getPosition();
            f.setOffsetStartAndEnd(offsetStart2, offsetEnd2);
        }
        for (int nMethods = in.readUnsignedShort(), l = 0; l < nMethods; ++l) {
            final int offsetStart3 = in.getPosition();
            final int accessFlags2 = in.readUnsignedShort();
            final CPInfo name2 = cf.getCPInfo(in.readUnsignedShort());
            final CPInfo descriptor2 = cf.getCPInfo(in.readUnsignedShort());
            final MethodInfo m = new MethodInfo(this.factory, cf, accessFlags2, name2, descriptor2);
            this.parseAttributes(in, cf, (Attributed)m, options);
            cf.addMethodInfo(m);
            final int offsetEnd3 = in.getPosition();
            m.setOffsetStartAndEnd(offsetStart3, offsetEnd3);
        }
        this.parseAttributes(in, cf, (Attributed)cf, options);
        return cf;
    }
    
    private void parseAttributes(final ByteArrayDataInput in, final ClassFile cf, final Attributed attributed, final CFParserOptions options) throws IOException {
        for (int nAttributes = in.readUnsignedShort(), i = 0; i < nAttributes; ++i) {
            final int offsetStart = in.getPosition();
            final AttributeInfo a = this.parseAttributeInfo(in, cf, attributed, options);
            attributed.addAttribute(a);
            final int offsetEnd = in.getPosition();
            a.setOffsetStartAndEnd(offsetStart, offsetEnd);
        }
    }
    
    private AttributeInfo parseAttributeInfo(final ByteArrayDataInput in, final ClassFile cf, final Attributed attributed, final CFParserOptions options) throws IOException {
        final CPInfo name = cf.getCPInfo(in.readUnsignedShort());
        final int attributeLength = in.readInt();
        if (options.whatToParse == 3) {
            return this.parseUnknownAttribute(in, name, attributeLength);
        }
        final String s = name.getString();
        if (s.equals("Code")) {
            return this.parseCodeAttribute(in, cf, name, attributeLength, (MethodInfo)attributed, options);
        }
        if (s.equals("LineNumberTable")) {
            final LineNumberTableAttribute lineNumberTableAttribute = new LineNumberTableAttribute(this.factory);
            final int n = in.readUnsignedShort();
            final int[] startPCs = new int[n];
            final String[] startPCLabels = new String[n];
            final int[] lineNumbers = new int[n];
            final Instruction[] instructions = ((CodeAttribute)attributed).getInstructionsArray();
            int j = 0;
            int currentLineNumber = 0;
            for (int i = 0; i < n; ++i) {
                final int offset = in.readUnsignedShort();
                startPCs[i] = offset;
                startPCLabels[i] = this.factory.createGeneratedLabel(offset);
                lineNumbers[i] = in.readUnsignedShort();
                while (j < instructions.length && instructions[j].getOriginalOffset() < offset) {
                    instructions[j].setLineNumber(currentLineNumber);
                    ++j;
                }
                currentLineNumber = lineNumbers[i];
            }
            while (j < instructions.length) {
                instructions[j].setLineNumber(currentLineNumber);
                ++j;
            }
            return (AttributeInfo)lineNumberTableAttribute.init((CodeAttribute)attributed, startPCLabels, lineNumbers);
        }
        if (s.equals("SourceFile")) {
            final SourceFileAttribute sourceFileAttribute = new SourceFileAttribute();
            if (attributeLength != 2) {
                throw new CFException(71, new Object[] { "SourceFile", new Integer(2), new Integer(attributeLength) });
            }
            sourceFileAttribute.setSourceFile(cf.getCPInfo(in.readUnsignedShort()));
            return (AttributeInfo)sourceFileAttribute;
        }
        else if (s.equals("Synthetic")) {
            final SyntheticAttribute syntheticAttribute = new SyntheticAttribute();
            if (attributeLength != 0) {
                throw new CFException(71, new Object[] { "Synthetic", new Integer(0), new Integer(attributeLength) });
            }
            return (AttributeInfo)syntheticAttribute;
        }
        else if (s.equals("Deprecated")) {
            final DeprecatedAttribute deprecatedAttribute = new DeprecatedAttribute();
            if (attributeLength != 0) {
                throw new CFException(71, new Object[] { "Deprecated", new Integer(0), new Integer(attributeLength) });
            }
            return (AttributeInfo)deprecatedAttribute;
        }
        else if (s.equals("ConstantValue")) {
            final ConstantValueAttribute constantValueAttribute = new ConstantValueAttribute();
            if (attributeLength != 2) {
                throw new CFException(71, new Object[] { "ConstantValue", new Integer(2), new Integer(attributeLength) });
            }
            constantValueAttribute.setConstantValue(cf.getCPInfo(in.readUnsignedShort()));
            return (AttributeInfo)constantValueAttribute;
        }
        else {
            if (s.equals("Exceptions")) {
                final ExceptionsAttribute exceptionsAttribute = new ExceptionsAttribute();
                for (int n = in.readUnsignedShort(), k = 0; k < n; ++k) {
                    exceptionsAttribute.addException(cf.getCPInfo(in.readUnsignedShort()));
                }
                return (AttributeInfo)exceptionsAttribute;
            }
            if (s.equals("LocalVariableTable")) {
                final LocalVariableTableAttribute localVariableTableAttribute = new LocalVariableTableAttribute(this.factory, (CodeAttribute)attributed);
                this.parseLocalVariableTableEntries(localVariableTableAttribute, in, cf, attributed);
                return (AttributeInfo)localVariableTableAttribute;
            }
            if (s.equals("LocalVariableTypeTable")) {
                final LocalVariableTypeTableAttribute localVariableTypeTableAttribute = new LocalVariableTypeTableAttribute(this.factory, (CodeAttribute)attributed);
                this.parseLocalVariableTableEntries((LocalVariableTableAttribute)localVariableTypeTableAttribute, in, cf, attributed);
                return (AttributeInfo)localVariableTypeTableAttribute;
            }
            if (s.equals("InnerClasses")) {
                final InnerClassesAttribute innerClassesAttribute = new InnerClassesAttribute(cf);
                for (int numberOfClasses = in.readUnsignedShort(), k = 0; k < numberOfClasses; ++k) {
                    final int offsetStart = in.getPosition();
                    final CPInfo innerClass = cf.getCPInfo(in.readUnsignedShort());
                    final CPInfo outerClass = cf.getCPInfo(in.readUnsignedShort());
                    final CPInfo innerName = cf.getCPInfo(in.readUnsignedShort());
                    final int innerAccessFlags = in.readUnsignedShort();
                    final InnerClassesAttributeEntry entry = innerClassesAttribute.addEntry(innerClass, outerClass, innerName, innerAccessFlags);
                    final int offsetEnd = in.getPosition();
                    entry.setOffsetStartAndEnd(offsetStart, offsetEnd);
                }
                return (AttributeInfo)innerClassesAttribute;
            }
            if (s.equals("COM.SAP.Modified")) {
                if (attributeLength == 2) {
                    return (AttributeInfo)new SAPModifiedAttribute(in.readUnsignedShort());
                }
                for (int l = 0; l < attributeLength; ++l) {
                    in.readByte();
                }
                return (AttributeInfo)new SAPModifiedAttribute(0);
            }
            else {
                if (s.equals("RuntimeVisibleAnnotations") || s.equals("RuntimeInvisibleAnnotations")) {
                    return this.parseAnnotationAttribute(in, cf, name, attributeLength, options, attributed);
                }
                if (s.equals("RuntimeVisibleParameterAnnotations") || s.equals("RuntimeInvisibleParameterAnnotations")) {
                    return this.parseAnnotationParameterAttribute(in, cf, name, attributeLength, options, attributed);
                }
                if (s.equals("AnnotationDefault")) {
                    return (AttributeInfo)new AnnotationDefaultAttribute(this.parseAnnotationPairValue(in, cf));
                }
                if (s.equals("Signature")) {
                    return (AttributeInfo)new SignatureAttribute(cf.getCPInfo(in.readUnsignedShort()));
                }
                if (s.equals("EnclosingMethod")) {
                    final CPInfo enclosingClass = cf.getCPInfo(in.readUnsignedShort());
                    CPInfo enclosingMethod = null;
                    final int methodIdx = in.readUnsignedShort();
                    if (methodIdx != 0) {
                        enclosingMethod = cf.getCPInfo(methodIdx);
                    }
                    return (AttributeInfo)new EnclosingMethodAttribute(enclosingClass, enclosingMethod);
                }
                if (s.equals("SourceDebugExtension")) {
                    final byte[] debugExtensionBytes = new byte[attributeLength];
                    in.readFully(debugExtensionBytes);
                    final String debugExtension = new String(debugExtensionBytes, "UTF-8");
                    return (AttributeInfo)new SourceDebugExtensionAttribute((Attributed)cf, debugExtension);
                }
                return this.parseUnknownAttribute(in, name, attributeLength);
            }
        }
    }
    
    private void parseLocalVariableTableEntries(final LocalVariableTableAttribute localVariableTableAttribute, final ByteArrayDataInput in, final ClassFile cf, final Attributed attributed) throws IOException {
        for (int n = in.readUnsignedShort(), i = 0; i < n; ++i) {
            final int offsetStart = in.getPosition();
            final int startPCOffset = in.readUnsignedShort();
            final String startPCLabel = this.factory.createGeneratedLabel(startPCOffset);
            final int endPCOffset = startPCOffset + in.readUnsignedShort();
            final String endPCLabel = this.factory.createGeneratedLabel(endPCOffset);
            final int nameIndex = in.readUnsignedShort();
            final CPInfo variableName = cf.getCPInfo(nameIndex);
            final int descriptorIndex = in.readUnsignedShort();
            final CPInfo variableDescriptor = cf.getCPInfo(descriptorIndex);
            final LocalVariable localVariable = this.factory.createLocalVariable(in.readUnsignedShort());
            final LocalVariableTableEntry entry = localVariableTableAttribute.addEntry(startPCLabel, endPCLabel, variableName, variableDescriptor, localVariable);
            final int offsetEnd = in.getPosition();
            entry.setOffsetStartAndEnd(offsetStart, offsetEnd);
        }
    }
    
    private AttributeInfo parseAnnotationAttribute(final ByteArrayDataInput in, final ClassFile cf, final CPInfo attributeName, final int attributeLength, final CFParserOptions options, final Attributed attributed) throws IOException {
        final int num = in.readUnsignedShort();
        final AnnotationAttribute attr = new AnnotationAttribute(attributeName.getString(), num, attributed);
        for (int i = 0; i < num; ++i) {
            final AnnotationStruct annotation = this.parseAnnotation(in, cf);
            attr.addAnnotation(annotation);
        }
        return (AttributeInfo)attr;
    }
    
    private AttributeInfo parseAnnotationParameterAttribute(final ByteArrayDataInput in, final ClassFile cf, final CPInfo attributeName, final int attributeLength, final CFParserOptions options, final Attributed attributed) throws IOException {
        final int numParams = in.readUnsignedByte();
        final ParameterAnnotationAttribute attr = new ParameterAnnotationAttribute(attributeName.getString(), numParams);
        for (int i = 0; i < numParams; ++i) {
            final int num = in.readUnsignedShort();
            final AnnotationStruct[] annotations = new AnnotationStruct[num];
            for (int j = 0; j < num; ++j) {
                annotations[j] = this.parseAnnotation(in, cf);
            }
            attr.setAnnotations(i, annotations);
        }
        return (AttributeInfo)attr;
    }
    
    private AnnotationStruct parseAnnotation(final ByteArrayDataInput in, final ClassFile cf) throws IOException {
        final CPInfo type = cf.getCPInfo(in.readUnsignedShort());
        final AnnotationStruct annotation = new AnnotationStruct(type);
        for (int numPairs = in.readUnsignedShort(), j = 0; j < numPairs; ++j) {
            final CPInfo elementNameIdx = cf.getCPInfo(in.readUnsignedShort());
            final String elementName = elementNameIdx.toPlainString();
            final AnnotationPairValue value = this.parseAnnotationPairValue(in, cf);
            value.setNameIdx(elementNameIdx);
            annotation.addElementValuePair(elementName, (Object)value);
        }
        return annotation;
    }
    
    private AnnotationPairValue parseAnnotationPairValue(final ByteArrayDataInput in, final ClassFile cf) throws IOException {
        final int tag = in.readUnsignedByte();
        final AnnotationPairValue value = new AnnotationPairValue(tag);
        switch (tag) {
            case 66:
            case 67:
            case 68:
            case 70:
            case 73:
            case 74:
            case 83:
            case 90:
            case 115: {
                value.setConstant(cf.getCPInfo(in.readUnsignedShort()));
                break;
            }
            case 101: {
                value.setEnumTypeAndName(cf.getCPInfo(in.readUnsignedShort()));
                value.setConstant(cf.getCPInfo(in.readUnsignedShort()));
                break;
            }
            case 99: {
                value.setConstant(cf.getCPInfo(in.readUnsignedShort()));
                break;
            }
            case 64: {
                value.setNested(this.parseAnnotation(in, cf));
                break;
            }
            case 91: {
                final int numValues = in.readUnsignedShort();
                final AnnotationPairValue[] values = new AnnotationPairValue[numValues];
                for (int i = 0; i < numValues; ++i) {
                    values[i] = this.parseAnnotationPairValue(in, cf);
                }
                value.setArray(values);
                break;
            }
        }
        return value;
    }
    
    private AttributeInfo parseUnknownAttribute(final ByteArrayDataInput in, final CPInfo name, final int attributeLength) throws IOException {
        final byte[] value = new byte[attributeLength];
        in.readFully(value);
        return (AttributeInfo)new UnknownAttribute(this.factory, name, value);
    }
    
    private AttributeInfo parseCodeAttribute(final ByteArrayDataInput in, final ClassFile cf, final CPInfo attributeName, final int attributeLength, final MethodInfo m, final CFParserOptions options) throws IOException {
        if (options.whatToParse == 2) {
            return this.parseUnknownAttribute(in, attributeName, attributeLength);
        }
        final CodeAttribute codeAttribute = new CodeAttribute(this.factory, m);
        codeAttribute.setOriginalMaxStack(in.readUnsignedShort());
        codeAttribute.setMaxLocals(in.readUnsignedShort());
        final int codeLength = in.readInt();
        this.offsetTable = new Instruction[codeLength];
        int offset = 0;
        boolean wide = false;
        while (offset < codeLength) {
            final int offsetStart = in.getPosition();
            final int opcode = in.readUnsignedByte();
            if (opcode == 196) {
                wide = true;
            }
            else {
                int offset2 = wide ? (offset + 2) : (offset + 1);
                final Instruction x = new Instruction(this.factory, codeAttribute);
                switch (CFParser.INSTRUCTION_OPERANDS[opcode]) {
                    case 1: {
                        x.init(opcode);
                        break;
                    }
                    case 3: {
                        final int cpIndex = (opcode == 18) ? in.readUnsignedByte() : in.readUnsignedShort();
                        offset2 += ((opcode == 18) ? 1 : 2);
                        final CPInfo cpInfo = cf.getCPInfo(cpIndex);
                        x.init(opcode, cpInfo);
                        break;
                    }
                    case 9: {
                        final int cpIndex = in.readUnsignedShort();
                        final CPInfo cpInfo = cf.getCPInfo(cpIndex);
                        final int arg = in.readUnsignedByte();
                        if (opcode == 197) {
                            offset2 += 3;
                        }
                        else {
                            if (opcode != 185) {
                                throw new CFException(70);
                            }
                            final int temp = in.readUnsignedByte();
                            if (temp != 0) {
                                throw new CFException(76, (Object)"invokeinterface");
                            }
                            offset2 += 4;
                        }
                        x.init(opcode, cpInfo, arg);
                        break;
                    }
                    case 5: {
                        int branchOffset = (opcode == 200 || opcode == 201) ? in.readInt() : in.readShort();
                        offset2 += ((opcode == 200 || opcode == 201) ? 4 : 2);
                        branchOffset += offset;
                        x.init(opcode, this.factory.createGeneratedLabel(branchOffset));
                        break;
                    }
                    case 2: {
                        final int arg2 = (opcode == 17) ? in.readShort() : in.readByte();
                        offset2 += ((opcode == 17) ? 2 : 1);
                        x.init(opcode, arg2);
                        break;
                    }
                    case 4: {
                        final int lvIndex = wide ? in.readUnsignedShort() : in.readUnsignedByte();
                        offset2 += (wide ? 2 : 1);
                        x.init(opcode, this.factory.createLocalVariable(lvIndex));
                        break;
                    }
                    case 8: {
                        final int lvIndex = wide ? in.readUnsignedShort() : in.readUnsignedByte();
                        final int arg3 = wide ? in.readShort() : in.readByte();
                        offset2 += (wide ? 4 : 2);
                        x.init(opcode, this.factory.createLocalVariable(lvIndex), arg3);
                        break;
                    }
                    case 6: {
                        final TableSwitchTable table = new TableSwitchTable(this.factory);
                        final int padding = AbstractSwitchTable.getPadding(offset);
                        for (int i = 0; i < padding; ++i) {
                            final int temp = in.readUnsignedByte();
                            if (temp != 0) {
                                throw new CFException(76, (Object)"tableswitch");
                            }
                        }
                        offset2 += padding;
                        final String defaultBranchLabel = this.factory.createGeneratedLabel(in.readInt() + offset);
                        final int low = in.readInt();
                        final int high = in.readInt();
                        final int n = high - low + 1;
                        offset2 += 12 + 4 * n;
                        final String[] branchLabels = new String[n];
                        for (int j = 0; j < n; ++j) {
                            branchLabels[j] = this.factory.createGeneratedLabel(in.readInt() + offset);
                        }
                        table.init(x, low, high, defaultBranchLabel, branchLabels);
                        x.init(opcode, table);
                        break;
                    }
                    case 7: {
                        final LookupSwitchTable table2 = new LookupSwitchTable(this.factory);
                        final int padding = AbstractSwitchTable.getPadding(offset);
                        for (int i = 0; i < padding; ++i) {
                            final int temp = in.readUnsignedByte();
                            if (temp != 0) {
                                throw new CFException(76, (Object)"lookupswitch");
                            }
                        }
                        offset2 += padding;
                        final String defaultBranchLabel = this.factory.createGeneratedLabel(in.readInt() + offset);
                        final int nPairs = in.readInt();
                        final int[] matches = new int[nPairs];
                        final String[] branchLabels2 = new String[nPairs];
                        offset2 += 8 + 8 * nPairs;
                        for (int k = 0; k < nPairs; ++k) {
                            matches[k] = in.readInt();
                            branchLabels2[k] = this.factory.createGeneratedLabel(in.readInt() + offset);
                        }
                        table2.init(x, defaultBranchLabel, matches, branchLabels2, nPairs);
                        x.init(opcode, table2);
                        break;
                    }
                    default: {
                        throw new CFException(77, new Object[] { new Integer(opcode), CFParser.MNEMONIC[opcode], m });
                    }
                }
                x.addLabel(this.factory.createGeneratedLabel(offset));
                wide = false;
                (this.offsetTable[offset] = x).setOriginalOffset(offset);
                codeAttribute.addInstruction(x);
                offset = offset2;
                final int offsetEnd = in.getPosition();
                x.setOffsetStartAndEnd(offsetStart, offsetEnd);
            }
        }
        if (offset != codeLength) {
            throw new CFException(73, new Object[] { new Integer(codeLength), new Integer(offset) });
        }
        for (int exceptionTableLength = in.readUnsignedShort(), l = 0; l < exceptionTableLength; ++l) {
            final int offsetStart2 = in.getPosition();
            final String startPCLabel = this.factory.createGeneratedLabel(in.readUnsignedShort());
            final String endPCLabel = this.factory.createGeneratedLabel(in.readUnsignedShort());
            final String handlerPCLabel = this.factory.createGeneratedLabel(in.readUnsignedShort());
            final CPInfo catchType = cf.getCPInfo(in.readUnsignedShort());
            final ExceptionHandler handler = codeAttribute.addExceptionHandler(startPCLabel, endPCLabel, false, handlerPCLabel, catchType);
            final int offsetEnd2 = in.getPosition();
            handler.setOffsetStartAndEnd(offsetStart2, offsetEnd2);
        }
        this.parseAttributes(in, cf, (Attributed)codeAttribute, options);
        if (Debug.DEBUG_CF) {
            codeAttribute.checkBranches();
        }
        return (AttributeInfo)codeAttribute;
    }
}