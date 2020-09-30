// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Constants.java

package com.fmi.bytecode.parser.internal;

// Referenced classes of package com.fmi.bytecode.parser:
//            CPInfo, FieldInfo, MethodInfo, Instruction, 
//            ExceptionHandler, CFNode

public interface Constants
{

    public static final int INT_ARRAY_0[] = ArrayUtils.INT_ARRAY_0;
    public static final CPInfo CPINFO_ARRAY_0[] = new CPInfo[0];
    public static final FieldInfo FIELD_INFO_ARRAY_0[] = new FieldInfo[0];
    public static final MethodInfo METHOD_INFO_ARRAY_0[] = new MethodInfo[0];
    public static final Instruction INSTRUCTION_ARRAY_0[] = new Instruction[0];
    public static final ExceptionHandler EXCEPTION_HANDLER_ARRAY_0[] = new ExceptionHandler[0];
    public static final CFNode CF_NODE_ARRAY_0[] = new CFNode[0];
    public static final int MAGIC_NUMBER = 0xcafebabe;
    public static final int CONSTANT_Utf8 = 1;
    public static final int CONSTANT_Integer = 3;
    public static final int CONSTANT_Float = 4;
    public static final int CONSTANT_Long = 5;
    public static final int CONSTANT_Double = 6;
    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_String = 8;
    public static final int CONSTANT_Fieldref = 9;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_InterfaceMethodref = 11;
    public static final int CONSTANT_NameAndType = 12;
    public static final String CONSTANT_MNEMONICS[] = {
        "???", "CONSTANT_Utf8", "???", "CONSTANT_Integer", "CONSTANT_Float", "CONSTANT_Long", "CONSTANT_Double", "CONSTANT_Class", "CONSTANT_String", "CONSTANT_Fieldref", 
        "CONSTANT_Methodref", "CONSTANT_InterfaceMethodref", "CONSTANT_NameAndType"
    };
    public static final int ACC_PUBLIC = 1;
    public static final int ACC_PRIVATE = 2;
    public static final int ACC_PROTECTED = 4;
    public static final int ACC_STATIC = 8;
    public static final int ACC_FINAL = 16;
    public static final int ACC_SUPER = 32;
    public static final int ACC_SYNCHRONIZED = 32;
    public static final int ACC_VOLATILE = 64;
    public static final int ACC_BRIDGE = 64;
    public static final int ACC_TRANSIENT = 128;
    public static final int ACC_VARARGS = 128;
    public static final int ACC_NATIVE = 256;
    public static final int ACC_INTERFACE = 512;
    public static final int ACC_ABSTRACT = 1024;
    public static final int ACC_STRICT = 2048;
    public static final int ACC_SYNTHETIC = 4096;
    public static final int ACC_ANNOTATION = 8192;
    public static final int ACC_ENUM = 16384;
    public static final char C_BYTE = 66;
    public static final char C_CHAR = 67;
    public static final char C_DOUBLE = 68;
    public static final char C_FLOAT = 70;
    public static final char C_INT = 73;
    public static final char C_LONG = 74;
    public static final char C_REF = 76;
    public static final char C_SHORT = 83;
    public static final char C_BOOLEAN = 90;
    public static final char C_ARRAY = 91;
    public static final char C_STRING = 115;
    public static final char C_ENUM = 101;
    public static final char C_CLASS = 99;
    public static final char C_ANNOTATION = 64;
    public static final String ACCESS_FLAG_MNEMONICS[] = {
        "public", "private", "protected", "static", "final", "synchronized", "volatile", "transient", "native", "interface", 
        "abstract", "strictfp", "annotation", "enum"
    };
    public static final int T_BOOLEAN = 4;
    public static final int T_CHAR = 5;
    public static final int T_FLOAT = 6;
    public static final int T_DOUBLE = 7;
    public static final int T_BYTE = 8;
    public static final int T_SHORT = 9;
    public static final int T_INT = 10;
    public static final int T_LONG = 11;
    public static final String T_MNEMONICS[] = {
        "???", "???", "???", "???", "boolean", "char", "float", "double", "byte", "short", 
        "int", "long"
    };
    public static final String S_INIT = "<init>";
    public static final String S_CLININT = "<clinit>";
    public static final String S_CODE = "Code";
    public static final String S_CONSTANT_VALUE = "ConstantValue";
    public static final String S_DEPRECATED = "Deprecated";
    public static final String S_EXCEPTIONS = "Exceptions";
    public static final String S_INNER_CLASSES = "InnerClasses";
    public static final String S_LINE_NUMBER_TABLE = "LineNumberTable";
    public static final String S_LOCAL_VARIABLE_TABLE = "LocalVariableTable";
    public static final String S_SOURCE_FILE = "SourceFile";
    public static final String S_SYNTHETIC = "Synthetic";
    public static final String S_RUNNTIME_VISIBLE_ANNOTATION = "RuntimeVisibleAnnotations";
    public static final String S_RUNNTIME_INVISIBLE_ANNOTATION = "RuntimeInvisibleAnnotations";
    public static final String S_RUNNTIME_VISIBLE_PARAM_ANNOTATION = "RuntimeVisibleParameterAnnotations";
    public static final String S_RUNNTIME_INVISIBLE_PARAM_ANNOTATION = "RuntimeInvisibleParameterAnnotations";
    public static final String S_ANNOTATION_DEFAULT = "AnnotationDefault";
    public static final String S_SIGNATURE = "Signature";
    public static final String S_ENCLOSING_METHOD = "EnclosingMethod";
    public static final String S_LOCAL_VARIABLE_TYPE_TABLE = "LocalVariableTypeTable";
    public static final String S_SOURCE_DEBUG_EXTENSION = "SourceDebugExtension";
    public static final int ATTR_CODE = 1;
    public static final int ATTR_CONSTANT_VALUE = 2;
    public static final int ATTR_DEPRICATED = 3;
    public static final int ATTR_EXCEPTIONS = 4;
    public static final int ATTR_INNER_CLASSES = 5;
    public static final int ATTR_LINE_NUMBER_TABLE = 6;
    public static final int ATTR_LOCAL_VARIABLE_TABLE = 7;
    public static final int ATTR_SOURCE_FILE = 8;
    public static final int ATTR_SYNTHETIC = 9;
    public static final int ATTR_RT_VISIBLE_ANNOTAITON = 10;
    public static final int ATTR_RT_INVISIBLE_ANNOTAITON = 11;
    public static final int ATTR_RT_VISIBLE_PARAM_ANNOTAITON = 12;
    public static final int ATTR_RT_INVISIBLE_PARAM_ANNOTAITON = 13;
    public static final int ATTR_ANNOTAITON_DEFAULT = 14;
    public static final int ATTR_SIGNATURE = 15;
    public static final int ATTR_ENCLOSING_METHOD = 16;
    public static final int ATTR_LOCAL_VARIABLE_TYPE_TABLE = 17;
    public static final int ATTR_SOURCE_DEBUG_EXTENSION = 18;
    public static final int NOP = 0;
    public static final int ACONST_NULL = 1;
    public static final int ICONST_M1 = 2;
    public static final int ICONST_0 = 3;
    public static final int ICONST_1 = 4;
    public static final int ICONST_2 = 5;
    public static final int ICONST_3 = 6;
    public static final int ICONST_4 = 7;
    public static final int ICONST_5 = 8;
    public static final int LCONST_0 = 9;
    public static final int LCONST_1 = 10;
    public static final int FCONST_0 = 11;
    public static final int FCONST_1 = 12;
    public static final int FCONST_2 = 13;
    public static final int DCONST_0 = 14;
    public static final int DCONST_1 = 15;
    public static final int BIPUSH = 16;
    public static final int SIPUSH = 17;
    public static final int LDC = 18;
    public static final int LDC_W = 19;
    public static final int LDC2_W = 20;
    public static final int ILOAD = 21;
    public static final int LLOAD = 22;
    public static final int FLOAD = 23;
    public static final int DLOAD = 24;
    public static final int ALOAD = 25;
    public static final int ILOAD_0 = 26;
    public static final int ILOAD_1 = 27;
    public static final int ILOAD_2 = 28;
    public static final int ILOAD_3 = 29;
    public static final int LLOAD_0 = 30;
    public static final int LLOAD_1 = 31;
    public static final int LLOAD_2 = 32;
    public static final int LLOAD_3 = 33;
    public static final int FLOAD_0 = 34;
    public static final int FLOAD_1 = 35;
    public static final int FLOAD_2 = 36;
    public static final int FLOAD_3 = 37;
    public static final int DLOAD_0 = 38;
    public static final int DLOAD_1 = 39;
    public static final int DLOAD_2 = 40;
    public static final int DLOAD_3 = 41;
    public static final int ALOAD_0 = 42;
    public static final int ALOAD_1 = 43;
    public static final int ALOAD_2 = 44;
    public static final int ALOAD_3 = 45;
    public static final int IALOAD = 46;
    public static final int LALOAD = 47;
    public static final int FALOAD = 48;
    public static final int DALOAD = 49;
    public static final int AALOAD = 50;
    public static final int BALOAD = 51;
    public static final int CALOAD = 52;
    public static final int SALOAD = 53;
    public static final int ISTORE = 54;
    public static final int LSTORE = 55;
    public static final int FSTORE = 56;
    public static final int DSTORE = 57;
    public static final int ASTORE = 58;
    public static final int ISTORE_0 = 59;
    public static final int ISTORE_1 = 60;
    public static final int ISTORE_2 = 61;
    public static final int ISTORE_3 = 62;
    public static final int LSTORE_0 = 63;
    public static final int LSTORE_1 = 64;
    public static final int LSTORE_2 = 65;
    public static final int LSTORE_3 = 66;
    public static final int FSTORE_0 = 67;
    public static final int FSTORE_1 = 68;
    public static final int FSTORE_2 = 69;
    public static final int FSTORE_3 = 70;
    public static final int DSTORE_0 = 71;
    public static final int DSTORE_1 = 72;
    public static final int DSTORE_2 = 73;
    public static final int DSTORE_3 = 74;
    public static final int ASTORE_0 = 75;
    public static final int ASTORE_1 = 76;
    public static final int ASTORE_2 = 77;
    public static final int ASTORE_3 = 78;
    public static final int IASTORE = 79;
    public static final int LASTORE = 80;
    public static final int FASTORE = 81;
    public static final int DASTORE = 82;
    public static final int AASTORE = 83;
    public static final int BASTORE = 84;
    public static final int CASTORE = 85;
    public static final int SASTORE = 86;
    public static final int POP = 87;
    public static final int POP2 = 88;
    public static final int DUP = 89;
    public static final int DUP_X1 = 90;
    public static final int DUP_X2 = 91;
    public static final int DUP2 = 92;
    public static final int DUP2_X1 = 93;
    public static final int DUP2_X2 = 94;
    public static final int SWAP = 95;
    public static final int IADD = 96;
    public static final int LADD = 97;
    public static final int FADD = 98;
    public static final int DADD = 99;
    public static final int ISUB = 100;
    public static final int LSUB = 101;
    public static final int FSUB = 102;
    public static final int DSUB = 103;
    public static final int IMUL = 104;
    public static final int LMUL = 105;
    public static final int FMUL = 106;
    public static final int DMUL = 107;
    public static final int IDIV = 108;
    public static final int LDIV = 109;
    public static final int FDIV = 110;
    public static final int DDIV = 111;
    public static final int IREM = 112;
    public static final int LREM = 113;
    public static final int FREM = 114;
    public static final int DREM = 115;
    public static final int INEG = 116;
    public static final int LNEG = 117;
    public static final int FNEG = 118;
    public static final int DNEG = 119;
    public static final int ISHL = 120;
    public static final int LSHL = 121;
    public static final int ISHR = 122;
    public static final int LSHR = 123;
    public static final int IUSHR = 124;
    public static final int LUSHR = 125;
    public static final int IAND = 126;
    public static final int LAND = 127;
    public static final int IOR = 128;
    public static final int LOR = 129;
    public static final int IXOR = 130;
    public static final int LXOR = 131;
    public static final int IINC = 132;
    public static final int I2L = 133;
    public static final int I2F = 134;
    public static final int I2D = 135;
    public static final int L2I = 136;
    public static final int L2F = 137;
    public static final int L2D = 138;
    public static final int F2I = 139;
    public static final int F2L = 140;
    public static final int F2D = 141;
    public static final int D2I = 142;
    public static final int D2L = 143;
    public static final int D2F = 144;
    public static final int I2B = 145;
    public static final int I2C = 146;
    public static final int I2S = 147;
    public static final int LCMP = 148;
    public static final int FCMPL = 149;
    public static final int FCMPG = 150;
    public static final int DCMPL = 151;
    public static final int DCMPG = 152;
    public static final int IFEQ = 153;
    public static final int IFNE = 154;
    public static final int IFLT = 155;
    public static final int IFGE = 156;
    public static final int IFGT = 157;
    public static final int IFLE = 158;
    public static final int IF_ICMPEQ = 159;
    public static final int IF_ICMPNE = 160;
    public static final int IF_ICMPLT = 161;
    public static final int IF_ICMPGE = 162;
    public static final int IF_ICMPGT = 163;
    public static final int IF_ICMPLE = 164;
    public static final int IF_ACMPEQ = 165;
    public static final int IF_ACMPNE = 166;
    public static final int GOTO = 167;
    public static final int JSR = 168;
    public static final int RET = 169;
    public static final int TABLESWITCH = 170;
    public static final int LOOKUPSWITCH = 171;
    public static final int IRETURN = 172;
    public static final int LRETURN = 173;
    public static final int FRETURN = 174;
    public static final int DRETURN = 175;
    public static final int ARETURN = 176;
    public static final int RETURN = 177;
    public static final int GETSTATIC = 178;
    public static final int PUTSTATIC = 179;
    public static final int GETFIELD = 180;
    public static final int PUTFIELD = 181;
    public static final int INVOKEVIRTUAL = 182;
    public static final int INVOKESPECIAL = 183;
    public static final int INVOKESTATIC = 184;
    public static final int INVOKEINTERFACE = 185;
    public static final int XXXUNUSEDXXX = 186;
    public static final int NEW = 187;
    public static final int NEWARRAY = 188;
    public static final int ANEWARRAY = 189;
    public static final int ARRAYLENGTH = 190;
    public static final int ATHROW = 191;
    public static final int CHECKCAST = 192;
    public static final int INSTANCEOF = 193;
    public static final int MONITORENTER = 194;
    public static final int MONITOREXIT = 195;
    public static final int WIDE = 196;
    public static final int MULTIANEWARRAY = 197;
    public static final int IFNULL = 198;
    public static final int IFNONNULL = 199;
    public static final int GOTO_W = 200;
    public static final int JSR_W = 201;
    public static final int BREAKPOINT = 202;
    public static final int IMPDEP1 = 254;
    public static final int IMPDEP2 = 255;
    public static final String MNEMONIC[] = {
        "nop", "aconst_null", "iconst_m1", "iconst_0", "iconst_1", "iconst_2", "iconst_3", "iconst_4", "iconst_5", "lconst_0", 
        "lconst_1", "fconst_0", "fconst_1", "fconst_2", "dconst_0", "dconst_1", "bipush", "sipush", "ldc", "ldc_w", 
        "ldc2_w", "iload", "lload", "fload", "dload", "aload", "iload_0", "iload_1", "iload_2", "iload_3", 
        "lload_0", "lload_1", "lload_2", "lload_3", "fload_0", "fload_1", "fload_2", "fload_3", "dload_0", "dload_1", 
        "dload_2", "dload_3", "aload_0", "aload_1", "aload_2", "aload_3", "iaload", "laload", "faload", "daload", 
        "aaload", "baload", "caload", "saload", "istore", "lstore", "fstore", "dstore", "astore", "istore_0", 
        "istore_1", "istore_2", "istore_3", "lstore_0", "lstore_1", "lstore_2", "lstore_3", "fstore_0", "fstore_1", "fstore_2", 
        "fstore_3", "dstore_0", "dstore_1", "dstore_2", "dstore_3", "astore_0", "astore_1", "astore_2", "astore_3", "iastore", 
        "lastore", "fastore", "dastore", "aastore", "bastore", "castore", "sastore", "pop", "pop2", "dup", 
        "dup_x1", "dup_x2", "dup2", "dup2_x1", "dup2_x2", "swap", "iadd", "ladd", "fadd", "dadd", 
        "isub", "lsub", "fsub", "dsub", "imul", "lmul", "fmul", "dmul", "idiv", "ldiv", 
        "fdiv", "ddiv", "irem", "lrem", "frem", "drem", "ineg", "lneg", "fneg", "dneg", 
        "ishl", "lshl", "ishr", "lshr", "iushr", "lushr", "iand", "land", "ior", "lor", 
        "ixor", "lxor", "iinc", "i2l", "i2f", "i2d", "l2i", "l2f", "l2d", "f2i", 
        "f2l", "f2d", "d2i", "d2l", "d2f", "i2b", "i2c", "i2s", "lcmp", "fcmpl", 
        "fcmpg", "dcmpl", "dcmpg", "ifeq", "ifne", "iflt", "ifge", "ifgt", "ifle", "if_icmpeq", 
        "if_icmpne", "if_icmplt", "if_icmpge", "if_icmpgt", "if_icmple", "if_acmpeq", "if_acmpne", "goto", "jsr", "ret", 
        "tableswitch", "lookupswitch", "ireturn", "lreturn", "freturn", "dreturn", "areturn", "return", "getstatic", "putstatic", 
        "getfield", "putfield", "invokevirtual", "invokespecial", "invokestatic", "invokeinterface", "xxxunusedxxx", "new", "newarray", "anewarray", 
        "arraylength", "athrow", "checkcast", "instanceof", "monitorenter", "monitorexit", "wide", "multianewarray", "ifnull", "ifnonnull", 
        "goto_w", "jsr_w", "breakpoint", "unknown_203", "unknown_204", "unknown_205", "unknown_206", "unknown_207", "unknown_208", "unknown_209", 
        "unknown_210", "unknown_211", "unknown_212", "unknown_213", "unknown_214", "unknown_215", "unknown_216", "unknown_217", "unknown_218", "unknown_219", 
        "unknown_220", "unknown_221", "unknown_222", "unknown_223", "unknown_224", "unknown_225", "unknown_226", "unknown_227", "unknown_228", "unknown_229", 
        "unknown_230", "unknown_231", "unknown_232", "unknown_233", "unknown_234", "unknown_235", "unknown_236", "unknown_237", "unknown_238", "unknown_239", 
        "unknown_240", "unknown_241", "unknown_242", "unknown_243", "unknown_244", "unknown_245", "unknown_246", "unknown_247", "unknown_248", "unknown_249", 
        "unknown_250", "unknown_251", "unknown_252", "unknown_253", "impdep_1", "impdep_2"
    };
    public static final int INSTRUCTION_LENGTH[] = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 2, 3, 2, 3, 
        3, 2, 2, 2, 2, 2, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 3, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 3, 3, 3, 3, 3, 3, 3, 
        3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 
        -1, -1, 1, 1, 1, 1, 1, 1, 3, 3, 
        3, 3, 3, 3, 3, 5, -1, 3, 2, 3, 
        1, 1, 3, 3, 1, 1, 1, 4, 3, 3, 
        5, 5, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, 1, 1
    };
    public static final int OPERAND_NONE = 1;
    public static final int OPERAND_INTEGER = 2;
    public static final int OPERAND_CPINFO = 3;
    public static final int OPERAND_LOCAL_VARIABLE = 4;
    public static final int OPERAND_INSTRUCTION = 5;
    public static final int OPERAND_TABLE_SWITCH_TABLE = 6;
    public static final int OPERAND_LOOKUP_SWITCH_TABLE = 7;
    public static final int OPERAND_LOCAL_VARIABLE_AND_INTEGER = 8;
    public static final int OPERAND_CPINFO_AND_INTEGER = 9;
    public static final int OPERAND_INVALID = -1;
    public static final int INSTRUCTION_OPERANDS[] = {
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 2, 2, 3, 3, 
        3, 4, 4, 4, 4, 4, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 4, 4, 4, 4, 4, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 8, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 5, 5, 5, 5, 5, 5, 5, 
        5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 
        6, 7, 1, 1, 1, 1, 1, 1, 3, 3, 
        3, 3, 3, 3, 3, 9, -1, 3, 2, 3, 
        1, 1, 3, 3, 1, 1, -1, 9, 5, 5, 
        5, 5, 1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, 1, 1
    };
    public static final int STACK_DECREASE[] = {
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 
        2, 2, 2, 2, 1, 2, 1, 2, 1, 1, 
        1, 1, 1, 2, 2, 2, 2, 1, 1, 1, 
        1, 2, 2, 2, 2, 1, 1, 1, 1, 3, 
        4, 3, 4, 3, 3, 3, 3, 1, 2, 1, 
        2, 3, 2, 3, 4, 2, 2, 4, 2, 4, 
        2, 4, 2, 4, 2, 4, 2, 4, 2, 4, 
        2, 4, 2, 4, 2, 4, 1, 2, 1, 2, 
        2, 3, 2, 3, 2, 3, 2, 4, 2, 4, 
        2, 4, 0, 1, 1, 1, 2, 2, 2, 1, 
        1, 1, 2, 2, 2, 1, 1, 1, 4, 2, 
        2, 4, 4, 1, 1, 1, 1, 1, 1, 2, 
        2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 
        1, 1, 1, 2, 1, 2, 1, 0, 0, -1, 
        1, -1, -1, -1, -1, -1, -1, 0, 1, 1, 
        1, 1, 1, 1, 1, 1, 0, -1, 1, 1, 
        0, 0, 0, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -2, -2
    };
    public static final int STACK_INCREASE[] = {
        0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 
        2, 1, 1, 1, 2, 2, 1, 1, 1, 1, 
        2, 1, 2, 1, 2, 1, 1, 1, 1, 1, 
        2, 2, 2, 2, 1, 1, 1, 1, 2, 2, 
        2, 2, 1, 1, 1, 1, 1, 2, 1, 2, 
        1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 
        3, 4, 4, 5, 6, 2, 1, 2, 1, 2, 
        1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 
        1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 
        1, 2, 1, 2, 1, 2, 1, 2, 1, 2, 
        1, 2, 0, 2, 1, 2, 1, 1, 2, 1, 
        2, 2, 1, 2, 1, 1, 1, 1, 1, 1, 
        1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 
        0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 
        -1, 0, -1, -1, -1, -1, -1, 1, 1, 1, 
        1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 
        0, 1, 0, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
        -1, -1, -1, -1, -1, -1
    };

}
