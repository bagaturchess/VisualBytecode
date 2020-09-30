package com.fmi.bytecode.parser.internal;

import java.util.Vector;
import java.net.URLEncoder;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Hashtable;
import java.util.HashSet;

public final class CFDisassembler implements Constants
{
    private static final String[] NAMES_OF_RECOGNIZED_ATTRIBUTES;
    private static final String CSS_CLASSES_PREFIX = "jasmin_";
    private static final String CSS_COMMENT = "jasmin_comment";
    private static final String CSS_DIRECTIVE = "jasmin_directive";
    private static final String CSS_INSTRUCTION = "jasmin_instruction";
    private static final String CSS_LINE_NUMBER = "jasmin_line_number";
    private static final String CSS_LABEL = "jasmin_label";
    private static final String CSS_LABEL_REF = "jasmin_label_ref";
    private static final String ANCHOR_PREFIX_METHOD = "method_";
    private static final String ANCHOR_PREFIX_LINE = "_line_";
    private static final String ANCHOR_PREFIX_LABEL = "_label_";
    private CFFactory factory;
    private CFDisassemblerOptions options;
    private StringBuffer buffer;
    private HashSet targetedInstructions;
    private Hashtable targetToLabel;
    private Hashtable instructionToLineNumber;
    
    static {
        NAMES_OF_RECOGNIZED_ATTRIBUTES = new String[] { "Code", "ConstantValue", "Exceptions", "LineNumberTable", "LocalVariableTable", "SourceFile" };
    }
    
    CFDisassembler(final CFFactory factory) {
        this.buffer = new StringBuffer(4096);
        this.targetedInstructions = new HashSet();
        this.targetToLabel = new Hashtable();
        this.instructionToLineNumber = new Hashtable();
        this.factory = factory;
    }
    
    public void disassemble(final File file, final ClassFile cf, final CFDisassemblerOptions options) throws IOException {
        this.disassemble(new PrintStream(new FileOutputStream(file)), cf, options);
    }
    
    public void disassemble(final String filename, final ClassFile cf, final CFDisassemblerOptions options) throws IOException {
        this.disassemble(new PrintStream(new FileOutputStream(filename)), cf, options);
    }
    
    public void disassemble(final PrintStream out, final ClassFile cf, CFDisassemblerOptions options) {
        options = ((options == null) ? CFDisassemblerOptions.DEFAULT : options);
        this.options = options;
        if (options.html) {
            this.buffer.append("<html>\n<head>\n");
            if (options.htmlStylesheet != null) {
                this.buffer.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + URLEncoder.encode(options.htmlStylesheet) + "\">\n");
            }
            else {
                this.buffer.append("<style>\n");
                this.buffer.append("  body {\n");
                this.buffer.append("    background-color: black;\n");
                this.buffer.append("    color: yellow;\n");
                this.buffer.append("    font-family: monospace;\n");
                this.buffer.append("  }\n");
                this.buffer.append("  .jasmin_comment {\n");
                this.buffer.append("    color: #00ff00;\n");
                this.buffer.append("  }\n");
                this.buffer.append("  .jasmin_directive {\n");
                this.buffer.append("    color: white;\n");
                this.buffer.append("  }\n");
                this.buffer.append("  .jasmin_instruction {\n");
                this.buffer.append("    color: yellow;\n");
                this.buffer.append("  }\n");
                this.buffer.append("  .jasmin_line_number {\n");
                this.buffer.append("    color: gray;\n");
                this.buffer.append("    text-decoration: none;\n");
                this.buffer.append("  }\n");
                this.buffer.append("  .jasmin_label {\n");
                this.buffer.append("    color: gray;\n");
                this.buffer.append("    text-decoration: none;\n");
                this.buffer.append("  }\n");
                this.buffer.append("  .jasmin_label_ref:hover {\n");
                this.buffer.append("    color: blue;\n");
                this.buffer.append("    text-decoration: underline;\n");
                this.buffer.append("  }\n");
                this.buffer.append("</style>\n");
            }
            this.buffer.append("</head>\n<body>\n");
        }
        this.appendFormattedText("; Jasmin decompiler output", "jasmin_comment");
        this.appendNewLine();
        this.appendNewLine();
        final String sourceFile = cf.getSourceFile();
        if (sourceFile != null) {
            this.appendFormattedText(".source ", "jasmin_directive");
            this.appendFormattedText(sourceFile, null);
            this.appendNewLine();
        }
        this.appendFormattedText(".class ", "jasmin_directive");
        this.disassembleAccessFlags(cf.getAccessFlags(), true);
        this.appendFormattedText(cf.getName(), null);
        this.disassembleUnrecognizedAttributeInfos((Attributed)cf);
        this.appendNewLine();
        this.appendFormattedText(".super", "jasmin_directive");
        this.appendFormattedText(" " + cf.getSuperClassName(), null);
        this.appendNewLine();
        for (int nInterfaces = cf.getNInterfaces(), i = 0; i < nInterfaces; ++i) {
            this.appendFormattedText(".implements ", "jasmin_directive");
            this.appendFormattedText(cf.getInterface(i).toPlainString(), null);
            this.appendNewLine();
        }
        this.appendNewLine();
        for (int nFields = cf.getNFields(), j = 0; j < nFields; ++j) {
            final FieldInfo f = cf.getField(j);
            this.appendFormattedText(".field", "jasmin_directive");
            this.appendFormattedText(" ", null);
            this.disassembleAccessFlags(f.getAccessFlags(), false);
            this.appendFormattedText(String.valueOf(f.getName()) + " " + f.getDescriptor(), null);
            final ConstantValueAttribute constantValueAttribute = f.getConstantValueAttribute();
            if (constantValueAttribute != null) {
                this.appendFormattedText(" = " + toJasminString(constantValueAttribute.getConstantValue()), null);
            }
            this.disassembleUnrecognizedAttributeInfos((Attributed)f);
            this.appendNewLine();
        }
        this.appendNewLine();
        for (int nMethods = cf.getNMethods(), k = 0; k < nMethods; ++k) {
            final MethodInfo m = cf.getMethod(k);
            this.disassembleMethod(m);
        }
        this.appendNewLine();
        out.print(this.buffer.toString());
    }
    
    private void disassembleMethod(final MethodInfo m) {
        this.appendFormattedText(".method", "jasmin_directive");
        this.appendFormattedText(" ", null);
        this.disassembleAccessFlags(m.getAccessFlags(), false);
        this.appendFormattedText(String.valueOf(m.getName()) + m.getDescriptor(), null);
        this.disassembleUnrecognizedAttributeInfos((Attributed)m);
        this.appendNewLine();
        final ExceptionsAttribute exceptionsAttribute = m.getExceptionsAttribute();
        if (exceptionsAttribute != null) {
            for (int nExceptions = exceptionsAttribute.getNExceptions(), i = 0; i < nExceptions; ++i) {
                this.appendFormattedText("  ", null);
                this.appendFormattedText(".throws", "jasmin_directive");
                this.appendFormattedText(" ", null);
                this.appendFormattedText(exceptionsAttribute.getException(i).toPlainString(), null);
                this.appendNewLine();
            }
        }
        final CodeAttribute ca = m.getCodeAttribute();
        if (ca == null) {
            this.appendFormattedText("    ; " + ResourceManager.get(8), "jasmin_comment");
            this.appendNewLine();
            this.appendFormattedText(".end method", "jasmin_directive");
            this.appendNewLine();
            this.appendNewLine();
            return;
        }
        final Instruction[] instructions = ca.getInstructionsArray();
        final ExceptionHandler[] exceptionHandlers = ca.getExceptionHandlersArray();
        final int[][] successorsTable = CFSerializer.calculateSuccessorsTable(instructions);
        final int[] stackSizes = CFSerializer.calculateStackSizes(instructions, exceptionHandlers, successorsTable);
        this.appendFormattedText("  .limit stack ", "jasmin_directive");
        this.appendFormattedText(Integer.toString(ca.getOriginalMaxStack()), null);
        if (this.options.calculateMaxStack) {
            final int maxStack = CFSerializer.calculateMaxStack(stackSizes);
            this.appendFormattedText(" ; " + ResourceManager.get(7) + " = " + maxStack, "jasmin_comment");
        }
        this.appendNewLine();
        this.appendFormattedText("  .limit locals ", "jasmin_directive");
        this.appendFormattedText(Integer.toString(ca.getOriginalMaxLocals()), null);
        this.appendNewLine();
        this.targetedInstructions.clear();
        boolean isEndOfCodeTargeted = false;
        this.targetToLabel.clear();
        this.instructionToLineNumber.clear();
        this.targetedInstructions.clear();
        for (int j = 0; j < instructions.length; ++j) {
            final Instruction x = instructions[j];
            final int opcode = x.getOpcode();
            switch (CFDisassembler.INSTRUCTION_OPERANDS[opcode]) {
                case 5: {
                    this.targetedInstructions.add(x.getOperandInstruction());
                    break;
                }
                case 6:
                case 7: {
                    final AbstractSwitchTable table = x.getOperandAbstractSwitchTable();
                    this.targetedInstructions.add(table.getDefaultBranch());
                    final Instruction[] branches = table.getBranchesArray();
                    for (int k = 0; k < branches.length; ++k) {
                        this.targetedInstructions.add(branches[k]);
                    }
                    break;
                }
            }
        }
        for (int j = 0; j < exceptionHandlers.length; ++j) {
            this.targetedInstructions.add(exceptionHandlers[j].getStartPC());
            final Instruction endPC = exceptionHandlers[j].getEndPCInclusive().getNextInstruction();
            if (endPC == null) {
                isEndOfCodeTargeted = true;
            }
            else {
                this.targetedInstructions.add(endPC);
            }
            this.targetedInstructions.add(exceptionHandlers[j].getHandlerPC());
        }
        LocalVariableTableAttribute localVariableTableAttribute = null;
        if (this.options.includeLocalVariables) {
            localVariableTableAttribute = ca.getLocalVariableTableAttribute();
            if (localVariableTableAttribute != null) {
                final LocalVariableTableEntry[] entries = localVariableTableAttribute.getEntriesArray();
                for (int l = 0; l < entries.length; ++l) {
                    this.targetedInstructions.add(entries[l].getStartPC());
                    final Instruction endPC2 = entries[l].getEndPC();
                    if (endPC2 == null) {
                        isEndOfCodeTargeted = true;
                    }
                    else {
                        this.targetedInstructions.add(endPC2);
                    }
                }
            }
        }
        int counter = 0;
        for (int l = 0; l < instructions.length; ++l) {
            if (this.targetedInstructions.contains(instructions[l])) {
                this.targetToLabel.put(instructions[l], "L" + counter);
                ++counter;
            }
        }
        final String endOfCodeLabel = isEndOfCodeTargeted ? ("L" + counter) : null;
        if (this.options.includeLineNumbers) {
            LineNumberTableAttribute lineNumberTableAttribute = null;
            try {
                lineNumberTableAttribute = (LineNumberTableAttribute)ca.getAttribute("LineNumberTable");
            }
            catch (ClassCastException ex) {}
            if (lineNumberTableAttribute != null) {
                final Instruction[] startPCs = lineNumberTableAttribute.getStartPCsArray();
                final int[] lineNumbers = lineNumberTableAttribute.getLineNumbersArray();
                for (int n = lineNumbers.length, i2 = 0; i2 < n; ++i2) {
                    this.instructionToLineNumber.put(startPCs[i2], Integer.toString(lineNumbers[i2]));
                }
            }
        }
        if (localVariableTableAttribute != null) {
            final LocalVariableTableEntry[] entries2 = localVariableTableAttribute.getEntriesArray();
            for (int i3 = 0; i3 < entries2.length; ++i3) {
                final LocalVariableTableEntry entry = entries2[i3];
                this.appendFormattedText("  .var ", "jasmin_directive");
                this.appendFormattedText(Integer.toString(entry.getLocalVariable().getIndex()), null);
                this.appendFormattedText(" is ", "jasmin_directive");
                this.appendFormattedText(String.valueOf(entry.getName().toPlainString()) + " " + entry.getDescriptor().toPlainString(), null);
                this.appendFormattedText(" from ", "jasmin_directive");
                this.appendLabelRef(m, this.targetToLabel.get(entry.getStartPC()).toString());
                this.appendFormattedText(" to ", "jasmin_directive");
                final Instruction endPC3 = entries2[i3].getEndPC();
                if (endPC3 == null) {
                    this.appendLabelRef(m, endOfCodeLabel);
                }
                else {
                    this.appendLabelRef(m, this.targetToLabel.get(endPC3).toString());
                }
                this.appendNewLine();
            }
        }
        for (int i4 = 0; i4 < instructions.length; ++i4) {
            if (this.options.includeLineNumbers) {
                final Object lineNumber = this.instructionToLineNumber.get(instructions[i4]);
                if (lineNumber != null) {
                    this.appendFormattedText("  .line ", "jasmin_directive");
                    if (this.options.html) {
                        this.buffer.append("<a id=\"" + URLEncoder.encode("method_" + m.getName() + m.getDescriptor() + "_line_" + lineNumber.toString()) + "\">");
                    }
                    this.appendFormattedText(lineNumber.toString(), "jasmin_line_number");
                    if (this.options.html) {
                        this.buffer.append("</a>");
                    }
                    this.appendNewLine();
                }
            }
            final Object label = this.targetToLabel.get(instructions[i4]);
            if (label != null) {
                this.appendLabel(m, label.toString());
                this.appendNewLine();
            }
            this.appendFormattedText("  ", null);
            final Instruction x2 = instructions[i4];
            final int opcode2 = x2.getOpcode();
            switch (CFDisassembler.INSTRUCTION_OPERANDS[opcode2]) {
                case 5: {
                    this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + this.targetToLabel.get(x2.getOperandInstruction()), "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                case 7: {
                    final LookupSwitchTable table2 = x2.getOperandLookupSwitchTable();
                    this.appendFormattedText(CFDisassembler.MNEMONIC[opcode2], "jasmin_instruction");
                    this.appendNewLine();
                    final Instruction[] branches2 = table2.getBranchesArray();
                    final int[] matches = table2.getMatchesArray();
                    for (int j2 = 0; j2 < branches2.length; ++j2) {
                        this.appendFormattedText("                " + matches[j2] + ": " + this.targetToLabel.get(branches2[j2]), "jasmin_instruction");
                        this.appendNewLine();
                    }
                    this.appendFormattedText("                default: " + this.targetToLabel.get(table2.getDefaultBranch()), "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                case 6: {
                    final TableSwitchTable table3 = x2.getOperandTableSwitchTable();
                    this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + table3.getLowValue(), "jasmin_instruction");
                    this.appendNewLine();
                    final Instruction[] branches2 = table3.getBranchesArray();
                    for (int j3 = 0; j3 < branches2.length; ++j3) {
                        this.appendFormattedText("               " + this.targetToLabel.get(branches2[j3]), "jasmin_instruction");
                        this.appendNewLine();
                    }
                    this.appendFormattedText("                default: " + this.targetToLabel.get(table3.getDefaultBranch()), "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                case 3: {
                    final CPInfo y = x2.getOperandCPInfo();
                    this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + toJasminString(y), "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                case 2: {
                    if (opcode2 == 188) {
                        this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + CFDisassembler.T_MNEMONICS[x2.getOperandInt()], "jasmin_instruction");
                    }
                    else {
                        this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + x2.getOperandInt(), "jasmin_instruction");
                    }
                    this.appendNewLine();
                    break;
                }
                case 9: {
                    this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + x2.getOperandCPInfo().toPlainString() + " " + x2.getOperandInt(), "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                case 4: {
                    final int lv = x2.getOperandLocalVariable().getIndex();
                    this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + lv, "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                case 8: {
                    final int lv = x2.getOperandLocalVariable().getIndex();
                    final int d = x2.getOperandInt();
                    this.appendFormattedText(String.valueOf(CFDisassembler.MNEMONIC[opcode2]) + " " + lv + " " + d, "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
                default: {
                    this.appendFormattedText(x2.toString(), "jasmin_instruction");
                    this.appendNewLine();
                    break;
                }
            }
        }
        if (isEndOfCodeTargeted) {
            this.appendLabel(m, endOfCodeLabel);
            this.appendNewLine();
        }
        for (int i4 = 0; i4 < exceptionHandlers.length; ++i4) {
            final CPInfo caught = exceptionHandlers[i4].getCatchType();
            final Instruction endPCExclusive = exceptionHandlers[i4].getEndPCInclusive();
            this.appendFormattedText("  .catch ", "jasmin_directive");
            this.appendFormattedText((caught.getIndex() == 0) ? "all" : caught.toPlainString(), null);
            this.appendFormattedText(" from ", "jasmin_directive");
            this.appendLabelRef(m, this.targetToLabel.get(exceptionHandlers[i4].getStartPC()).toString());
            this.appendFormattedText(" to ", "jasmin_directive");
            this.appendLabelRef(m, ((endPCExclusive == null) ? "null" : this.targetToLabel.get(endPCExclusive.getNextInstruction())).toString());
            this.appendFormattedText(" using ", "jasmin_directive");
            this.appendLabelRef(m, this.targetToLabel.get(exceptionHandlers[i4].getHandlerPC()).toString());
            this.appendNewLine();
        }
        this.appendFormattedText(".end method", "jasmin_directive");
        this.appendNewLine();
        this.appendNewLine();
    }
    
    private void disassembleAccessFlags(final int accessFlags, final boolean isClassFile) {
        int mask = 1;
        for (int i = 0; i < 12; ++i) {
            if ((accessFlags & mask) != 0x0 && (!isClassFile || mask != 32)) {
                this.appendFormattedText(String.valueOf(CFDisassembler.ACCESS_FLAG_MNEMONICS[i]) + " ", null);
            }
            mask <<= 1;
        }
    }
    
    private void disassembleUnrecognizedAttributeInfos(final Attributed a) {
        final AttributeInfo[] attributeInfos = a.getAttributesArray();
        final Vector temp = new Vector();
        for (int i = 0; i < attributeInfos.length; ++i) {
            boolean isRecognized = false;
            final String name = attributeInfos[i].getName();
            for (int j = 0; j < CFDisassembler.NAMES_OF_RECOGNIZED_ATTRIBUTES.length; ++j) {
                if (name.equals(CFDisassembler.NAMES_OF_RECOGNIZED_ATTRIBUTES[j])) {
                    isRecognized = true;
                    break;
                }
            }
            if (!isRecognized) {
                temp.add(name);
            }
        }
        final int nTemp = temp.size();
        if (nTemp > 1) {
            final StringBuffer buffer1 = new StringBuffer();
            buffer1.append(temp.get(0));
            for (int k = 1; k < nTemp; ++k) {
                buffer1.append(", ").append(temp.get(k));
            }
            final String listOfAttributeNames = buffer1.toString();
            this.appendFormattedText(" ; " + ResourceManager.get(5, (Object)listOfAttributeNames), "jasmin_comment");
        }
        else if (nTemp == 1) {
            this.appendFormattedText(" ; " + ResourceManager.get(6, temp.get(0)), "jasmin_comment");
        }
    }
    
    private static String toJasminString(final CPInfo x) {
        switch (x.getTag()) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7: {
                return x.toPlainString();
            }
            case 10:
            case 11: {
                final CPInfo nameAndType = x.getValueNameAndType();
                return String.valueOf(x.getValueClass().toPlainString()) + "/" + nameAndType.getValueName().toPlainString() + nameAndType.getValueDescriptor().toPlainString();
            }
            case 9: {
                final CPInfo nameAndType = x.getValueNameAndType();
                return String.valueOf(x.getValueClass().toPlainString()) + "/" + nameAndType.getValueName().toPlainString() + " " + nameAndType.getValueDescriptor().toPlainString();
            }
            case 1:
            case 8: {
                return "\"" + StringUtils.escapeString(x.toPlainString()) + "\"";
            }
            default: {
                return "??? ; Invalid CPInfo, tag=" + x.getTag() + ", index=" + x.getIndex() + ", ncp=" + x.getOwner().getCPSize();
            }
        }
    }
    
    private void appendFormattedText(final String s, final String t) {
        if (this.options.html) {
            if (t == null) {
                this.buffer.append(StringUtils.escapeToHTML(s));
            }
            else {
                this.buffer.append("<span class=\"" + t + "\">").append(StringUtils.escapeToHTML(s)).append("</span>");
            }
        }
        else {
            this.buffer.append(s);
        }
    }
    
    private void appendLabel(final MethodInfo m, final String label) {
        if (this.options.html) {
            this.buffer.append("<a id=\"" + URLEncoder.encode("method_" + m.getName() + m.getDescriptor() + "_label_" + label) + "\" class=\"" + "jasmin_label" + "\">" + label + ":</a>");
        }
        else {
            this.buffer.append(label).append(':');
        }
    }
    
    private void appendLabelRef(final MethodInfo m, final String label) {
        if (this.options.html) {
            this.buffer.append("<a href=\"#" + URLEncoder.encode("method_" + m.getName() + m.getDescriptor() + "_label_" + label) + "\" class=\"" + "jasmin_label_ref" + "\">" + label + "</a>");
        }
        else {
            this.buffer.append(label);
        }
    }
    
    private void appendNewLine() {
        if (this.options.html) {
            this.buffer.append("<br>\n");
        }
        else {
            this.buffer.append('\n');
        }
    }
    
    private static void printUsage() {
        System.out.println(String.valueOf(ResourceManager.get(89)) + ":");
        System.out.println("\tjava " + CFDisassembler.class.getName() + " filename");
        System.out.println("\tjava " + CFDisassembler.class.getName() + " -j filename");
    }
}
