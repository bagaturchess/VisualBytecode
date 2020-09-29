package com.fmi.bytecode.parser;

public final class Instruction extends CFNode implements Constants
{
    private static final int[][] OPTIMIZED_OPCODES;
    private CodeAttribute ownerCodeAttribute;
    private int index;
    private int opcode;
    private int lineNumber;
    private int originalOffset;
    private Object operand;
    
    static {
        (OPTIMIZED_OPCODES = new int[256][])[21] = new int[] { 26, 27, 28, 29 };
        Instruction.OPTIMIZED_OPCODES[22] = new int[] { 30, 31, 32, 33 };
        Instruction.OPTIMIZED_OPCODES[23] = new int[] { 34, 35, 36, 37 };
        Instruction.OPTIMIZED_OPCODES[24] = new int[] { 38, 39, 40, 41 };
        Instruction.OPTIMIZED_OPCODES[25] = new int[] { 42, 43, 44, 45 };
        Instruction.OPTIMIZED_OPCODES[54] = new int[] { 59, 60, 61, 62 };
        Instruction.OPTIMIZED_OPCODES[55] = new int[] { 63, 64, 65, 66 };
        Instruction.OPTIMIZED_OPCODES[56] = new int[] { 67, 68, 69, 70 };
        Instruction.OPTIMIZED_OPCODES[57] = new int[] { 71, 72, 73, 74 };
        Instruction.OPTIMIZED_OPCODES[58] = new int[] { 75, 76, 77, 78 };
    }
    
    Instruction(final CFFactory factory, final CodeAttribute owner) {
        this.index = -1;
        this.opcode = -1;
        this.lineNumber = 0;
        this.originalOffset = -1;
        this.ownerCodeAttribute = owner;
        this.opcode = 0;
    }
    
    public Instruction init(final int opcode) {
        if (Debug.DEBUG_CF && Instruction.INSTRUCTION_OPERANDS[opcode] != 1) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = null;
        return this;
    }
    
    public int getIndex() {
        return this.index;
    }
    
    void setIndex(final int index) {
        this.index = index;
    }
    
    public int getLineNumber() {
        return this.lineNumber;
    }
    
    public void setLineNumber(final int lineNumber) {
        if (Debug.DEBUG_CF && lineNumber < 0) {
            throw new CFException(14);
        }
        this.lineNumber = lineNumber;
    }
    
    public int getOriginalOffset() {
        return this.originalOffset;
    }
    
    void setOriginalOffset(final int originalOffset) {
        this.originalOffset = originalOffset;
    }
    
    public Instruction init(final int opcode, final LocalVariable lv) {
        if (Debug.DEBUG_CF && (Instruction.INSTRUCTION_OPERANDS[opcode] != 4 || lv == null)) {
            throw new CFException(14);
        }
        if (Instruction.OPTIMIZED_OPCODES[opcode] != null) {
            final int lvIndex = lv.getIndex();
            if (lvIndex < Instruction.OPTIMIZED_OPCODES[opcode].length) {
                this.opcode = Instruction.OPTIMIZED_OPCODES[opcode][lvIndex];
                this.operand = null;
                return this;
            }
        }
        this.opcode = opcode;
        this.operand = lv;
        return this;
    }
    
    Instruction init(final int opcode, final Instruction branch) {
        if (Debug.DEBUG_CF && Instruction.INSTRUCTION_OPERANDS[opcode] != 5) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        throw new Error();
    }
    
    public Instruction init(final int opcode, final String label) {
        if (Debug.DEBUG_CF && label == null) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = label;
        return this;
    }
    
    public Instruction init(final int opcode, final int x) {
        if (Debug.DEBUG_CF && Instruction.INSTRUCTION_OPERANDS[opcode] != 2) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = this.ownerCodeAttribute.getFactory().createInteger(x);
        return this;
    }
    
    public Instruction init(final int opcode, final CPInfo x) {
        if (Debug.DEBUG_CF && (Instruction.INSTRUCTION_OPERANDS[opcode] != 3 || x == null)) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = x;
        return this;
    }
    
    public Instruction init(final int opcode, final LocalVariable lv, final int x) {
        if (Debug.DEBUG_CF && Instruction.INSTRUCTION_OPERANDS[opcode] != 8) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = new Object[] { lv, this.ownerCodeAttribute.getFactory().createInteger(x) };
        return this;
    }
    
    public Instruction init(final int opcode, final CPInfo cpInfo, final int x) {
        if (Debug.DEBUG_CF && (Instruction.INSTRUCTION_OPERANDS[opcode] != 9 || cpInfo == null)) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = new Object[] { cpInfo, this.ownerCodeAttribute.getFactory().createInteger(x) };
        return this;
    }
    
    Instruction init(final int opcode, final TableSwitchTable t) {
        if (Instruction.INSTRUCTION_OPERANDS[opcode] != 6) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = t;
        return this;
    }
    
    Instruction init(final int opcode, final LookupSwitchTable t) {
        if (Instruction.INSTRUCTION_OPERANDS[opcode] != 7) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = t;
        return this;
    }
    
    public int getOpcode() {
        return this.opcode;
    }
    
    public String getMnemonic() {
        try {
            return Instruction.MNEMONIC[this.opcode];
        }
        catch (ArrayIndexOutOfBoundsException ae) {
            return "???(opcode=" + this.opcode + ")";
        }
    }
    
    public boolean isReturn() {
        switch (this.opcode) {
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public Instruction getOperandInstruction() {
        try {
            return this.ownerCodeAttribute.getInstructionByLabel((String)this.operand, true);
        }
        catch (ClassCastException cce) {
            throw new CFException(19, (Object)"getOperandInstruction()");
        }
    }
    
    public int getOperandInt() {
        try {
            switch (this.opcode) {
                case 16:
                case 17:
                case 188: {
                    return (int)this.operand;
                }
                case 132:
                case 185:
                case 197: {
                    return (int)((Object[])this.operand)[1];
                }
            }
        }
        catch (ClassCastException ex) {}
        throw new CFException(19, (Object)"getOperandInt()");
    }
    
    public LocalVariable getOperandLocalVariable() {
        if (this.opcode == 132) {
            return (LocalVariable)((Object[])this.operand)[0];
        }
        try {
            return (LocalVariable)this.operand;
        }
        catch (ClassCastException cce) {
            throw new CFException(19, (Object)"getOperandLocalVariable()");
        }
    }
    
    public AbstractSwitchTable getOperandAbstractSwitchTable() {
        try {
            return (AbstractSwitchTable)this.operand;
        }
        catch (ClassCastException cce) {
            throw new CFException(19, (Object)"getOperandAbstractSwitchTable()");
        }
    }
    
    public TableSwitchTable getOperandTableSwitchTable() {
        try {
            return (TableSwitchTable)this.operand;
        }
        catch (ClassCastException cce) {
            throw new CFException(19, (Object)"getOperandTableSwitchTable()");
        }
    }
    
    public LookupSwitchTable getOperandLookupSwitchTable() {
        try {
            return (LookupSwitchTable)this.operand;
        }
        catch (ClassCastException cce) {
            throw new CFException(19, (Object)"getOperandLookupSwitchTable()");
        }
    }
    
    public CPInfo getOperandCPInfo() {
        if (this.opcode == 185 || this.opcode == 197) {
            return (CPInfo)((Object[])this.operand)[0];
        }
        try {
            return (CPInfo)this.operand;
        }
        catch (ClassCastException cce) {
            throw new CFException(19, (Object)"getOperandCPInfo()");
        }
    }
    
    public CodeAttribute getOwnerCodeAttribute() {
        return this.ownerCodeAttribute;
    }
    
    public String toString() {
        final StringBuffer b = new StringBuffer();
        b.append(this.getMnemonic());
        if (this.operand != null && !(this.operand instanceof AbstractSwitchTable)) {
            if (this.operand instanceof Object[]) {
                final Object[] x = (Object[])this.operand;
                b.append(' ').append(x[0]).append(' ').append(x[1]);
            }
            else if (this.operand instanceof Instruction) {
                b.append(" [").append(((Instruction)this.operand).getIndex()).append("]");
            }
            else {
                b.append(' ').append(this.operand);
            }
        }
        return b.toString();
    }
    
    int getLength(final int offset) {
        switch (this.opcode) {
            case 170: {
                final TableSwitchTable table = (TableSwitchTable)this.operand;
                final int padding = 4 - (offset & 0x3) & 0x3;
                return padding + 12 + 4 * table.getNBranches();
            }
            case 171: {
                final LookupSwitchTable table2 = (LookupSwitchTable)this.operand;
                final int padding = 4 - (offset & 0x3) & 0x3;
                return padding + 8 + 8 * table2.getNBranches();
            }
            case 132: {
                final Object[] operandArray = (Object[])this.operand;
                final int operand0 = ((LocalVariable)operandArray[0]).getIndex();
                final int operand2 = (int)operandArray[1];
                return (operand0 > 255 || operand2 > 255) ? 6 : 3;
            }
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
                final int operand3 = ((LocalVariable)this.operand).getIndex();
                return (operand3 > 255) ? 4 : 2;
            }
            case 167:
            case 168:
            case 200:
            case 201: {
                return 3;
            }
            default: {
                return Instruction.INSTRUCTION_LENGTH[this.opcode];
            }
        }
    }
    
    public boolean canBeWide() {
        switch (this.opcode) {
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
            case 132:
            case 169: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public Instruction initLDC(final CPInfo x) {
        if (Debug.DEBUG_CF) {
            final int tag = x.getTag();
            if (tag != 6 && tag != 4 && tag != 3 && tag != 5 && tag != 8) {
                throw new CFException(14);
            }
        }
        final int tag = x.getTag();
        if (tag == 5 || tag == 6) {
            this.opcode = 20;
        }
        else if (x.getIndex() > 255) {
            this.opcode = 19;
        }
        else {
            this.opcode = 18;
        }
        this.operand = x;
        return this;
    }
    
    public void init(final int opcode, final AbstractSwitchTable abstractSwitchTable) {
        if (opcode != 170 && opcode != 171) {
            throw new CFException(14);
        }
        this.opcode = opcode;
        this.operand = abstractSwitchTable;
    }
    
    public int getStackDecrease() {
        if (Instruction.STACK_DECREASE[this.opcode] != -1) {
            return Instruction.STACK_DECREASE[this.opcode];
        }
        switch (this.opcode) {
            case 179: {
                final String d = ((CPInfo)this.operand).getValueNameAndType().getValueDescriptor().toPlainString();
                return (d.equals("J") || d.equals("D")) ? 2 : 1;
            }
            case 181: {
                final String d = ((CPInfo)this.operand).getValueNameAndType().getValueDescriptor().toPlainString();
                return (d.equals("J") || d.equals("D")) ? 3 : 2;
            }
            case 185: {
                final String d = ((CPInfo)((Object[])this.operand)[0]).getValueNameAndType().getValueDescriptor().toPlainString();
                return getArgumentsSize(d, true);
            }
            case 182:
            case 183:
            case 184: {
                final String d = ((CPInfo)this.operand).getValueNameAndType().getValueDescriptor().toPlainString();
                return getArgumentsSize(d, this.opcode != 184);
            }
            case 197: {
                final int dimensions = (int)((Object[])this.operand)[1];
                return dimensions;
            }
            default: {
                throw new CFException(87, new Object[] { Instruction.MNEMONIC[this.opcode], new Integer(this.opcode) });
            }
        }
    }
    
    public int getStackIncrease() {
        if (Instruction.STACK_INCREASE[this.opcode] != -1) {
            return Instruction.STACK_INCREASE[this.opcode];
        }
        switch (this.opcode) {
            case 178:
            case 180: {
                final String d = ((CPInfo)this.operand).getValueNameAndType().getValueDescriptor().toPlainString();
                return (d.equals("J") || d.equals("D")) ? 2 : 1;
            }
            case 185: {
                final String d = ((CPInfo)((Object[])this.operand)[0]).getValueNameAndType().getValueDescriptor().toPlainString();
                return getReturnTypeSize(d);
            }
            case 182:
            case 183:
            case 184: {
                final String d = ((CPInfo)this.operand).getValueNameAndType().getValueDescriptor().toPlainString();
                return getReturnTypeSize(d);
            }
            case 197: {
                return (int)((Object[])this.operand)[0];
            }
            default: {
                throw new CFException(88, new Object[] { Instruction.MNEMONIC[this.opcode], new Integer(this.opcode) });
            }
        }
    }
    
    private static int getReturnTypeSize(final String methodDescriptor) {
        final String s = methodDescriptor.substring(methodDescriptor.indexOf(41) + 1);
        return (s.equals("J") || s.equals("D")) ? 2 : (s.equals("V") ? 0 : 1);
    }
    
    private static int getArgumentsSize(final String methodDescriptor, final boolean countThisPointer) {
        int i = 1;
        int r = countThisPointer ? 1 : 0;
        boolean isArray = false;
    Label_0072:
        while (true) {
            switch (methodDescriptor.charAt(i)) {
                case ')': {
                    break Label_0072;
                }
                case 'L': {
                    i = methodDescriptor.indexOf(59, i) + 1;
                    ++r;
                    isArray = false;
                    continue;
                }
                case '[': {
                    ++i;
                    isArray = true;
                    continue;
                }
                case 'D':
                case 'J': {
                    ++i;
                    r += (isArray ? 1 : 2);
                    isArray = false;
                    continue;
                }
                default: {
                    ++i;
                    ++r;
                    isArray = false;
                    continue;
                }
            }
        }
        return r;
    }
    
    public Instruction getPreviousInstruction() {
        if (this.index == 0) {
            return null;
        }
        return this.ownerCodeAttribute.getInstructionByIndex(this.index - 1);
    }
    
    public Instruction getNextInstruction() {
        if (this.index == this.ownerCodeAttribute.getNInstructions() - 1) {
            return null;
        }
        return this.ownerCodeAttribute.getInstructionByIndex(this.index + 1);
    }
    
    public Instruction addLabel(final String label) {
        this.ownerCodeAttribute.addLabel(label, this);
        return this;
    }
    
    public Instruction cloneInstruction(final String labelPrefix) {
        final Instruction r = this.ownerCodeAttribute.createInstruction();
        r.opcode = this.opcode;
        switch (Instruction.INSTRUCTION_OPERANDS[this.opcode]) {
            case 5: {
                r.operand = String.valueOf(labelPrefix) + this.ownerCodeAttribute.getCanonicalLabel((String)this.operand);
                break;
            }
            case 6:
            case 7: {
                r.operand = ((AbstractSwitchTable)this.operand).cloneAbstractSwitchTable(this, labelPrefix);
                break;
            }
            default: {
                r.operand = this.operand;
                break;
            }
        }
        final String canonicalLabel = this.ownerCodeAttribute.getCanonicalLabelOfInstruction(this);
        if (canonicalLabel != null) {
            r.addLabel(String.valueOf(labelPrefix) + canonicalLabel);
        }
        r.setLineNumber(this.getLineNumber());
        return r;
    }
    
    void checkBranches() {
        switch (Constants.INSTRUCTION_OPERANDS[this.opcode]) {
            case 5: {
                this.ownerCodeAttribute.getInstructionByLabel((String)this.operand, true);
                break;
            }
            case 6:
            case 7: {
                ((AbstractSwitchTable)this.operand).checkBranches();
                break;
            }
        }
    }
    
    public CFNode getParentNode() {
        return (CFNode)this.ownerCodeAttribute;
    }
    
    public CFNode[] getChildNodes() {
        if (this.opcode == 170 || this.opcode == 171) {
            return new CFNode[] { (CFNode)this.operand };
        }
        return Constants.CF_NODE_ARRAY_0;
    }
}