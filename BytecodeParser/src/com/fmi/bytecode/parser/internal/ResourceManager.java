package com.fmi.bytecode.parser.internal;


import java.text.MessageFormat;


public final class ResourceManager
{
    //public static final ResourceBundle bundle;
    private static final Object[] OBJECT_ARRAY_0;
    /*public static final int CANCEL_MNEMONIC = 0;
    public static final int CF_ATTRIBUTE_INFO_ALREADY_EXISTS = 1;
    public static final int CF_BAD_ATYPE = 2;
    public static final int CF_BAD_RETURN_TYPE = 3;
    public static final int CF_CODE_WITHOUT_INSTRUCTIONS = 4;
    public static final int CF_DISASSEMBLER_ATTRIBUTES_NOT_DISASSEMBLED = 5;
    public static final int CF_DISASSEMBLER_ATTRIBUTE_NOT_DISASSEMBLED = 6;
    public static final int CF_DISASSEMBLER_CALCULATED_MAX_STACK = 7;
    public static final int CF_DISASSEMBLER_NO_CODE = 8;
    public static final int CF_DUPLICATE_FIELD = 9;
    public static final int CF_EOF_REACHED = 10;
    public static final int CF_FIELD_BAD_FIRST_CHAR = 11;
    public static final int CF_FIELD_DESCRIPTOR_BAD = 12;
    public static final int CF_FIELD_DESCRIPTOR_NULL = 13;
    public static final int CF_ILLEGAL_ARGUMENT = 14;
    public static final int CF_ILLEGAL_NEWARRAY_OPERAND = 15;
    public static final int CF_INCOMPATIBLE_CP_TAG = 16;
    public static final int CF_INDEX_OUT_OF_BOUNDS = 17;
    public static final int CF_INSTRUCTION_WITH_LABEL_NOT_FOUND = 18;
    public static final int CF_INVALID_USAGE_OF_METHOD = 19;
    public static final int CF_JLS_TYPE_CONTAINS_SPACES = 20;
    public static final int CF_LABEL_IS_ALREADY_PRESENT = 21;
    public static final int CF_METHOD_CODE_DEFERRED = 22;
    public static final int CF_METHOD_DESCRIPTOR_NULL = 23;
    public static final int CF_METHOD_DESCRIPTOR_WITHOUT_BRACKET = 24;
    public static final int CF_METHOD_PARAMETERS_255_SLOTS = 25;
    public static final int CF_METHOD_PARAMETERS_ARRAY_ITEM_TYPE = 26;
    public static final int CF_METHOD_PARAMETERS_BAD_CHAR = 27;
    public static final int CF_METHOD_PARAMETERS_BAD_DESCRIPTOR = 28;
    public static final int CF_METHOD_PARAMETERS_L_CLOSED = 29;
    public static final int CF_METHOD_PARAMETERS_NULL = 30;
    public static final int CF_METHOD_PARAMETERS_OPENING_BRACKET = 31;
    public static final int CF_MODIFIER_ANT_ATTRIBUTE_REQUIRED = 32;
    public static final int CF_MODIFIER_ANT_COULD_NOT_LOAD_PROPERTIES = 33;
    public static final int CF_MODIFIER_ANT_FILE_DELETED = 34;
    public static final int CF_MODIFIER_ANT_FILE_DOESNT_EXIST = 35;
    public static final int CF_MODIFIER_ANT_FILE_IS_A_DIRECTORY = 36;
    public static final int CF_MODIFIER_ANT_INCOMPATIBLE_IN_AND_OUT = 37;
    public static final int CF_MODIFIER_ANT_LOADING_CONFIGURATION = 38;
    public static final int CF_MODIFIER_ANT_MODIFICATION_COMPLETED = 39;
    public static final int CF_MODIFIER_ANT_UNABLE_TO_DELETE = 40;
    public static final int CF_MODIFIER_BAD_PARAMETER_FIRST_CHAR = 41;
    public static final int CF_MODIFIER_BAD_RETURN_TYPE_FIRST_CHAR = 42;
    public static final int CF_MODIFIER_CHECKING = 43;
    public static final int CF_MODIFIER_DONE = 44;
    public static final int CF_MODIFIER_INCONSISTENT_FILES = 45;
    public static final int CF_MODIFIER_INPUT = 46;
    public static final int CF_MODIFIER_INPUT_FILE_DOES_NOT_EXIST = 47;
    public static final int CF_MODIFIER_INPUT_OUTPUT = 48;
    public static final int CF_MODIFIER_METHOD = 49;
    public static final int CF_MODIFIER_MODIFICATION_CANCELLED = 50;
    public static final int CF_MODIFIER_MODIFYING = 51;
    public static final int CF_MODIFIER_OUTPUT = 52;
    public static final int CF_MODIFIER_PARAMETER_BAD_LENGTHS = 53;
    public static final int CF_MODIFIER_PARAMETER_DEPENDENCY = 54;
    public static final int CF_MODIFIER_PARAMETER_ELEMENT_NULL = 55;
    public static final int CF_MODIFIER_USAGE = 56;
    public static final int CF_NO_CANONICAL_LABEL = 57;
    public static final int CF_NO_CLASS_VALUE = 58;
    public static final int CF_NO_DESCRIPTOR_VALUE = 59;
    public static final int CF_NO_DOUBLE_VALUE = 60;
    public static final int CF_NO_FLOAT_VALUE = 61;
    public static final int CF_NO_INSTRUCTION_BOUND = 62;
    public static final int CF_NO_INT_VALUE = 63;
    public static final int CF_NO_LABEL_BOUND = 64;
    public static final int CF_NO_LONG_VALUE = 65;
    public static final int CF_NO_NAME_AND_TYPE_VALUE = 66;
    public static final int CF_NO_NAME_VALUE = 67;
    public static final int CF_NO_STRING_VALUE = 68;
    public static final int CF_NULL = 69;
    public static final int CF_OPERAND_CP_INFO_AND_INT_BAD = 70;
    public static final int CF_PARSER_BAD_ATTRIBUTE_LENGTH = 71;
    public static final int CF_PARSER_BAD_MAGIC_NUMBER = 72;
    public static final int CF_PARSER_INVALID_CODE_SIZE = 73;
    public static final int CF_PARSER_INVALID_CP_TAG = 74;
    public static final int CF_PARSER_INVALID_INSTRUCTION_LENGTH = 75;
    public static final int CF_PARSER_PADDING_0 = 76;
    public static final int CF_PARSER_UNKNOWN_OPERANDS = 77;
    public static final int CF_REF_INSTRUCTION_NOT_FOUND = 78;
    public static final int CF_SERIALIZER_ENDPC_NOT_RESOLVED = 79;
    public static final int CF_SERIALIZER_INCONSISTENT_STACK_SIZES = 80;
    public static final int CF_SERIALIZER_INVALID_CP_TAG = 81;
    public static final int CF_SERIALIZER_NEGATIVE_STACK_SIZE = 82;
    public static final int CF_SERIALIZER_NO_SUPER_CLASS = 83;
    public static final int CF_SERIALIZER_NO_THIS_CLASS = 84;
    public static final int CF_SERIALIZER_OPERAND_NOT_INTEGER = 85;
    public static final int CF_SERIALIZER_UNKNOWN_OPCODE = 86;
    public static final int CF_UNKNOWN_STACK_DECREASE = 87;
    public static final int CF_UNKNOWN_STACK_INCREASE = 88;
    public static final int CF_USAGE = 89;
    */
    
    /*public static final int CLASSES = 90;
    public static final int GUI_FC_ACCESS_DENIED = 91;
    public static final int GUI_FC_ADD = 92;
    public static final int GUI_FC_BYTES = 93;
    public static final int GUI_FC_CANT_CREATE_DIR = 94;
    public static final int GUI_FC_CANT_CREATE_FILE = 95;
    public static final int GUI_FC_CAN_READ = 96;
    public static final int GUI_FC_CAN_WRITE = 97;
    public static final int GUI_FC_CREATE_NEW_DIRECTORY = 98;
    public static final int GUI_FC_CREATE_NEW_FILE = 99;
    public static final int GUI_FC_DIRECTORY_NAME = 100;
    public static final int GUI_FC_EXCEPTION = 101;
    public static final int GUI_FC_FILENAME = 102;
    public static final int GUI_FC_FILE_PROPERTIES = 103;
    public static final int GUI_FC_FILE_SIZE = 104;
    public static final int GUI_FC_HIDDEN = 105;
    public static final int GUI_FC_LAST_MODIFIED = 106;
    public static final int GUI_FC_NAME = 107;
    public static final int GUI_FC_NONE = 108;
    public static final int GUI_FC_NOT_APPLICABLE = 109;
    public static final int GUI_FC_PERMISSIONS = 110;
    public static final int GUI_FC_QUICK_VIEW = 111;
    public static final int GUI_PE_ALL_PROPERTIES = 112;
    public static final int GUI_PE_ALL_PROPERTIES_MNEMONIC = 113;
    public static final int GUI_PE_COPY_TO_CLIPBOARD = 114;
    public static final int GUI_PE_COPY_TO_CLIPBOARD_MNEMONIC = 115;
    public static final int GUI_PE_DESTINATION = 116;
    public static final int GUI_PE_DONE = 117;
    public static final int GUI_PE_EXCEPTION = 118;
    public static final int GUI_PE_EXPORTED_PROPERTIES = 119;
    public static final int GUI_PE_EXPORT_PROPERTIES = 120;
    public static final int GUI_PE_EXPORT_TO_A_FILE = 121;
    public static final int GUI_PE_EXPORT_TO_A_FILE_MNEMONIC = 122;
    public static final int GUI_PE_FIX_CR_LF = 123;
    public static final int GUI_PE_FIX_CR_LF_MNEMONIC = 124;
    public static final int GUI_PE_NAME = 125;
    public static final int GUI_PE_NEW_PROPERTY = 126;
    public static final int GUI_PE_OPTIONS = 127;
    public static final int GUI_PE_PROPERTIES_EXPORTED_CLIPBOARD = 128;
    public static final int GUI_PE_PROPERTIES_EXPORTED_FILE = 129;
    public static final int GUI_PE_REMOVE_PROPERTIES = 130;
    public static final int GUI_PE_REMOVE_PROPERTY = 131;
    public static final int GUI_PE_REMOVE_Q = 132;
    public static final int GUI_PE_SELECTED_PROPERTIES = 133;
    public static final int GUI_PE_SELECTED_PROPERTIES_MNEMONIC = 134;
    public static final int GUI_PE_SELECT_AN_OUTPUT_FILE = 135;
    public static final int GUI_PE_THERE_IS_NO_OUTPUT_FILE = 136;
    public static final int GUI_PE_UNABLE_TO_EXPORT_PROPERTIES = 137;
    public static final int GUI_PE_VALUE = 138;
    public static final int GUI_WIZ_ALLOW_TO_PROPAGATE = 139;
    public static final int GUI_WIZ_ALLOW_TO_PROPAGATE_MNEMONIC = 140;
    public static final int GUI_WIZ_BASE_DIR_OR_JAR = 141;
    public static final int GUI_WIZ_BLA_BLA = 142;
    public static final int GUI_WIZ_BYTECODE_MODIFIER = 143;
    public static final int GUI_WIZ_CONFIRM_EXIT = 144;
    public static final int GUI_WIZ_DIR_FOR_GENERATED = 145;
    public static final int GUI_WIZ_EXIT_QUESTION = 146;
    public static final int GUI_WIZ_FINISHED = 147;
    public static final int GUI_WIZ_FINISHED_1 = 148;
    public static final int GUI_WIZ_FINISHED_2 = 149;
    public static final int GUI_WIZ_FINISHED_3 = 150;
    public static final int GUI_WIZ_FIX_SUID = 151;
    public static final int GUI_WIZ_FIX_SUID_MNEMONIC = 152;
    public static final int GUI_WIZ_IDENTIFIER = 153;
    public static final int GUI_WIZ_METHODS_WITH_ARGUMENTS = 154;
    public static final int GUI_WIZ_METHODS_WITH_ARGUMENTS_MNEMONIC = 155;
    public static final int GUI_WIZ_MODIFY = 156;
    public static final int GUI_WIZ_MODIFY_MNEMONIC = 157;
    public static final int GUI_WIZ_OUTPUT_DIR_OR_JAR = 158;
    public static final int GUI_WIZ_PROPERTIES = 159;
    public static final int GUI_WIZ_SETTINGS = 160;
    public static final int GUI_WIZ_SPEED = 161;
    public static final int GUI_WIZ_USE_SAP_HEADING = 162;
    public static final int METHODS = 163;
    public static final int MISC_BAD_IDENTIFIER = 164;
    public static final int MISC_IDENTIFIER_CONTAINS_SPACE = 165;
    public static final int MISC_IDENTIFIER_IS_EMPTY = 166;
    public static final int MISC_IDENTIFIER_IS_NULL = 167;
    public static final int MISC_METHOD_DOES_NOT_EXIST = 168;
    public static final int OPEN = 169;
    public static final int OPEN_MNEMONIC = 170;
    public static final int SAVE = 171;
    public static final int SAVE_MNEMONIC = 172;
    public static final int TIME = 173;
    public static final int TRACING_32_PLUGINS = 174;
    public static final int TRACING_PLUGIN_ALREADY_IN_USE = 175;
    public static final int TRACING_PLUGIN_CANT_BE_MODIFIED = 176;
    public static final int TRACING_PLUGIN_NOT_IN_USE = 177;
    */
    private static final String[] PROPERTY_NAMES;
    
    static {
        //bundle = ResourceBundle.getBundle("com.sap.engine.library.bytecode.res.res");
        OBJECT_ARRAY_0 = new Object[0];
        PROPERTY_NAMES = new String[] { "CANCEL_MNEMONIC", "CF_ATTRIBUTE_INFO_ALREADY_EXISTS", "CF_BAD_ATYPE", "CF_BAD_RETURN_TYPE", "CF_CODE_WITHOUT_INSTRUCTIONS", "CF_DISASSEMBLER_ATTRIBUTES_NOT_DISASSEMBLED", "CF_DISASSEMBLER_ATTRIBUTE_NOT_DISASSEMBLED", "CF_DISASSEMBLER_CALCULATED_MAX_STACK", "CF_DISASSEMBLER_NO_CODE", "CF_DUPLICATE_FIELD", "CF_EOF_REACHED", "CF_FIELD_BAD_FIRST_CHAR", "CF_FIELD_DESCRIPTOR_BAD", "CF_FIELD_DESCRIPTOR_NULL", "CF_ILLEGAL_ARGUMENT", "CF_ILLEGAL_NEWARRAY_OPERAND", "CF_INCOMPATIBLE_CP_TAG", "CF_INDEX_OUT_OF_BOUNDS", "CF_INSTRUCTION_WITH_LABEL_NOT_FOUND", "CF_INVALID_USAGE_OF_METHOD", "CF_JLS_TYPE_CONTAINS_SPACES", "CF_LABEL_IS_ALREADY_PRESENT", "CF_METHOD_CODE_DEFERRED", "CF_METHOD_DESCRIPTOR_NULL", "CF_METHOD_DESCRIPTOR_WITHOUT_BRACKET", "CF_METHOD_PARAMETERS_255_SLOTS", "CF_METHOD_PARAMETERS_ARRAY_ITEM_TYPE", "CF_METHOD_PARAMETERS_BAD_CHAR", "CF_METHOD_PARAMETERS_BAD_DESCRIPTOR", "CF_METHOD_PARAMETERS_L_CLOSED", "CF_METHOD_PARAMETERS_NULL", "CF_METHOD_PARAMETERS_OPENING_BRACKET", "CF_MODIFIER_ANT_ATTRIBUTE_REQUIRED", "CF_MODIFIER_ANT_COULD_NOT_LOAD_PROPERTIES", "CF_MODIFIER_ANT_FILE_DELETED", "CF_MODIFIER_ANT_FILE_DOESNT_EXIST", "CF_MODIFIER_ANT_FILE_IS_A_DIRECTORY", "CF_MODIFIER_ANT_INCOMPATIBLE_IN_AND_OUT", "CF_MODIFIER_ANT_LOADING_CONFIGURATION", "CF_MODIFIER_ANT_MODIFICATION_COMPLETED", "CF_MODIFIER_ANT_UNABLE_TO_DELETE", "CF_MODIFIER_BAD_PARAMETER_FIRST_CHAR", "CF_MODIFIER_BAD_RETURN_TYPE_FIRST_CHAR", "CF_MODIFIER_CHECKING", "CF_MODIFIER_DONE", "CF_MODIFIER_INCONSISTENT_FILES", "CF_MODIFIER_INPUT", "CF_MODIFIER_INPUT_FILE_DOES_NOT_EXIST", "CF_MODIFIER_INPUT_OUTPUT", "CF_MODIFIER_METHOD", "CF_MODIFIER_MODIFICATION_CANCELLED", "CF_MODIFIER_MODIFYING", "CF_MODIFIER_OUTPUT", "CF_MODIFIER_PARAMETER_BAD_LENGTHS", "CF_MODIFIER_PARAMETER_DEPENDENCY", "CF_MODIFIER_PARAMETER_ELEMENT_NULL", "CF_MODIFIER_USAGE", "CF_NO_CANONICAL_LABEL", "CF_NO_CLASS_VALUE", "CF_NO_DESCRIPTOR_VALUE", "CF_NO_DOUBLE_VALUE", "CF_NO_FLOAT_VALUE", "CF_NO_INSTRUCTION_BOUND", "CF_NO_INT_VALUE", "CF_NO_LABEL_BOUND", "CF_NO_LONG_VALUE", "CF_NO_NAME_AND_TYPE_VALUE", "CF_NO_NAME_VALUE", "CF_NO_STRING_VALUE", "CF_NULL", "CF_OPERAND_CP_INFO_AND_INT_BAD", "CF_PARSER_BAD_ATTRIBUTE_LENGTH", "CF_PARSER_BAD_MAGIC_NUMBER", "CF_PARSER_INVALID_CODE_SIZE", "CF_PARSER_INVALID_CP_TAG", "CF_PARSER_INVALID_INSTRUCTION_LENGTH", "CF_PARSER_PADDING_0", "CF_PARSER_UNKNOWN_OPERANDS", "CF_REF_INSTRUCTION_NOT_FOUND", "CF_SERIALIZER_ENDPC_NOT_RESOLVED", "CF_SERIALIZER_INCONSISTENT_STACK_SIZES", "CF_SERIALIZER_INVALID_CP_TAG", "CF_SERIALIZER_NEGATIVE_STACK_SIZE", "CF_SERIALIZER_NO_SUPER_CLASS", "CF_SERIALIZER_NO_THIS_CLASS", "CF_SERIALIZER_OPERAND_NOT_INTEGER", "CF_SERIALIZER_UNKNOWN_OPCODE", "CF_UNKNOWN_STACK_DECREASE", "CF_UNKNOWN_STACK_INCREASE", "CF_USAGE", "CLASSES", "GUI_FC_ACCESS_DENIED", "GUI_FC_ADD", "GUI_FC_BYTES", "GUI_FC_CANT_CREATE_DIR", "GUI_FC_CANT_CREATE_FILE", "GUI_FC_CAN_READ", "GUI_FC_CAN_WRITE", "GUI_FC_CREATE_NEW_DIRECTORY", "GUI_FC_CREATE_NEW_FILE", "GUI_FC_DIRECTORY_NAME", "GUI_FC_EXCEPTION", "GUI_FC_FILENAME", "GUI_FC_FILE_PROPERTIES", "GUI_FC_FILE_SIZE", "GUI_FC_HIDDEN", "GUI_FC_LAST_MODIFIED", "GUI_FC_NAME", "GUI_FC_NONE", "GUI_FC_NOT_APPLICABLE", "GUI_FC_PERMISSIONS", "GUI_FC_QUICK_VIEW", "GUI_PE_ALL_PROPERTIES", "GUI_PE_ALL_PROPERTIES_MNEMONIC", "GUI_PE_COPY_TO_CLIPBOARD", "GUI_PE_COPY_TO_CLIPBOARD_MNEMONIC", "GUI_PE_DESTINATION", "GUI_PE_DONE", "GUI_PE_EXCEPTION", "GUI_PE_EXPORTED_PROPERTIES", "GUI_PE_EXPORT_PROPERTIES", "GUI_PE_EXPORT_TO_A_FILE", "GUI_PE_EXPORT_TO_A_FILE_MNEMONIC", "GUI_PE_FIX_CR_LF", "GUI_PE_FIX_CR_LF_MNEMONIC", "GUI_PE_NAME", "GUI_PE_NEW_PROPERTY", "GUI_PE_OPTIONS", "GUI_PE_PROPERTIES_EXPORTED_CLIPBOARD", "GUI_PE_PROPERTIES_EXPORTED_FILE", "GUI_PE_REMOVE_PROPERTIES", "GUI_PE_REMOVE_PROPERTY", "GUI_PE_REMOVE_Q", "GUI_PE_SELECTED_PROPERTIES", "GUI_PE_SELECTED_PROPERTIES_MNEMONIC", "GUI_PE_SELECT_AN_OUTPUT_FILE", "GUI_PE_THERE_IS_NO_OUTPUT_FILE", "GUI_PE_UNABLE_TO_EXPORT_PROPERTIES", "GUI_PE_VALUE", "GUI_WIZ_ALLOW_TO_PROPAGATE", "GUI_WIZ_ALLOW_TO_PROPAGATE_MNEMONIC", "GUI_WIZ_BASE_DIR_OR_JAR", "GUI_WIZ_BLA_BLA", "GUI_WIZ_BYTECODE_MODIFIER", "GUI_WIZ_CONFIRM_EXIT", "GUI_WIZ_DIR_FOR_GENERATED", "GUI_WIZ_EXIT_QUESTION", "GUI_WIZ_FINISHED", "GUI_WIZ_FINISHED_1", "GUI_WIZ_FINISHED_2", "GUI_WIZ_FINISHED_3", "GUI_WIZ_FIX_SUID", "GUI_WIZ_FIX_SUID_MNEMONIC", "GUI_WIZ_IDENTIFIER", "GUI_WIZ_METHODS_WITH_ARGUMENTS", "GUI_WIZ_METHODS_WITH_ARGUMENTS_MNEMONIC", "GUI_WIZ_MODIFY", "GUI_WIZ_MODIFY_MNEMONIC", "GUI_WIZ_OUTPUT_DIR_OR_JAR", "GUI_WIZ_PROPERTIES", "GUI_WIZ_SETTINGS", "GUI_WIZ_SPEED", "GUI_WIZ_USE_SAP_HEADING", "METHODS", "MISC_BAD_IDENTIFIER", "MISC_IDENTIFIER_CONTAINS_SPACE", "MISC_IDENTIFIER_IS_EMPTY", "MISC_IDENTIFIER_IS_NULL", "MISC_METHOD_DOES_NOT_EXIST", "OPEN", "OPEN_MNEMONIC", "SAVE", "SAVE_MNEMONIC", "TIME", "TRACING_32_PLUGINS", "TRACING_PLUGIN_ALREADY_IN_USE", "TRACING_PLUGIN_CANT_BE_MODIFIED", "TRACING_PLUGIN_NOT_IN_USE" };
    }
    
    private ResourceManager() {
    }
    
    public static String get(final int resourceId) {
        return get(resourceId, ResourceManager.OBJECT_ARRAY_0);
    }
    
    public static String get(final int resourceId, final Object argument) {
        return get(resourceId, new Object[] { argument });
    }
    
    public static String get(final int resourceId, final int argument) {
        return get(resourceId, new Object[] { new Integer(argument) });
    }
    
    public static String get(final int resourceId, final Object argument0, final int argument1) {
        return get(resourceId, new Object[] { argument0, new Integer(argument1) });
    }
    
    public static String get(final int resourceId, final Object argument0, final Object argument1) {
        return get(resourceId, new Object[] { argument0, argument1 });
    }
    
    public static String get(final int resourceId, final int argument0, final int argument1) {
        return get(resourceId, new Object[] { new Integer(argument0), new Integer(argument1) });
    }
    
    public static String get(final int resourceId, final Object[] arguments) {
        try {
            final MessageFormat mf = new MessageFormat(ResourceManager.PROPERTY_NAMES[resourceId]);
            return mf.format(arguments);
        }
        catch (Exception e) {
            e.printStackTrace();
            final StringBuffer buffer = new StringBuffer();
            buffer.append("Exception in i18n, cannot get message with id '" + resourceId + "'.");
            if (arguments != null && arguments.length > 0) {
                buffer.append("Arguments:");
                for (int i = 0; i < arguments.length; ++i) {
                    buffer.append(' ').append(arguments[i]);
                }
                buffer.append('.');
            }
            return buffer.toString();
        }
    }
}