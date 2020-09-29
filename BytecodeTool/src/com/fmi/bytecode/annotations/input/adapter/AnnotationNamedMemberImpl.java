package com.fmi.bytecode.annotations.input.adapter;


import com.fmi.bytecode.annotations.input.*;
import com.fmi.bytecode.annotations.util.ComparisonUtils;
import com.fmi.bytecode.parser.AnnotationPairValue;


public class AnnotationNamedMemberImpl
    implements NamedMemberModel
{
    private class EnumTypeImpl
        implements EnumTypeModel
    {

        public String getEnumerationTypeName()
        {
            return typeName;
        }

        public String getEnumerationLiteral()
        {
            return value;
        }

        public boolean equals(Object obj)
        {
            if(obj == null || !(obj instanceof EnumTypeModel))
                return false;
            EnumTypeModel target = (EnumTypeModel)obj;
            if(!ComparisonUtils.compareObjects(target.getEnumerationLiteral(), getEnumerationLiteral()))
                return false;
            return ComparisonUtils.compareObjects(target.getEnumerationTypeName(), getEnumerationTypeName());
        }

        public int hashCode()
        {
            return super.hashCode();
        }

        private String typeName;
        private String value;
        final AnnotationNamedMemberImpl this$0;

        public EnumTypeImpl(String enumNameAndType, String value)
        {
        	super();
            this$0 = AnnotationNamedMemberImpl.this;
            typeName = enumNameAndType;
            this.value = value;
        }
    }


    public AnnotationNamedMemberImpl(String name, AnnotationPairValue value, AnnotationModel owner)
    {
        this.name = name;
        this.owner = owner;
        isValueDefault = false;
        tag = value.getTag();
        parseValue(value);
    }

    public boolean isValueDefaulted()
    {
        return isValueDefault;
    }

    private void parseValue(AnnotationPairValue value)
    {
        switch(value.getTag())
        {
        default:
            break;

        case 73: // 'I'
            objValue = new Integer(value.getConstant().getValueInt());
            break;

        case 66: // 'B'
            objValue = new Byte((byte)value.getConstant().getValueInt());
            break;

        case 67: // 'C'
            objValue = new Character((char)value.getConstant().getValueInt());
            break;

        case 83: // 'S'
            objValue = new Short((short)value.getConstant().getValueInt());
            break;

        case 90: // 'Z'
            objValue = new Boolean(value.getConstant().getValueInt() > 0);
            break;

        case 68: // 'D'
            objValue = new Double(value.getConstant().getValueDouble());
            break;

        case 70: // 'F'
            objValue = new Float(value.getConstant().getValueFloat());
            break;

        case 74: // 'J'
            objValue = new Long(value.getConstant().getValueLong());
            break;

        case 115: // 's'
            objValue = new String(value.getConstant().getString());
            break;

        case 101: // 'e'
            objValue = new EnumTypeImpl(value.getEnumTypeAndName().getString(), value.getConstant().getString());
            break;

        case 91: // '['
            AnnotationPairValue values[] = value.getArray();
            int l = values.length;
            AnnotationNamedMemberImpl recs[] = new AnnotationNamedMemberImpl[l];
            for(int i = 0; i < l; i++)
                recs[i] = new AnnotationNamedMemberImpl(null, values[i], owner);

            objValue = recs;
            break;

        case 64: // '@'
            objValue = new AnnotationModelImpl(value.getNested(), owner.isRuntimeVisible());
            break;

        case 99: // 'c'
            String className = value.getConstant().getString();
            int len = className.length();
            objValue = len >= 2 ? ((Object) (className.substring(1, len - 1).replace('/', '.'))) : ((Object) (className));
            break;

        case 76: // 'L'
            objValue = value.getConstant().getString();
            break;
        }
    }

    public String getName()
    {
        return name;
    }

    public int getMemberTag()
    {
        return tag;
    }

    public Object getMemberValue()
    {
        return objValue;
    }

    public String toString()
    {
        return objValue.toString();
    }

    public boolean equals(Object obj)
    {
        if(obj == null || !(obj instanceof NamedMemberModel))
            return false;
        NamedMemberModel target = (NamedMemberModel)obj;
        if(!ComparisonUtils.compareObjects(name, target.getName()))
            return false;
        Object targetObjValue = target.getMemberValue();
        if(objValue.getClass().isArray())
            if(targetObjValue.getClass().isArray())
                return ComparisonUtils.compareUnorderedArrays((Object[])objValue, (Object[])targetObjValue);
            else
                return false;
        return ComparisonUtils.compareObjects(objValue, targetObjValue);
    }

    public int hashCode()
    {
        return super.hashCode();
    }

    private AnnotationModel owner;
    private String name;
    private Object objValue;
    private boolean isValueDefault;
    private int tag;
}
