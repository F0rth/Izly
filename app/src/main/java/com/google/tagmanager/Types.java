package com.google.tagmanager;

import com.google.analytics.midtier.proto.containertag.TypeSystem.Value;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class Types {
    private static Boolean DEFAULT_BOOLEAN = new Boolean(false);
    private static Double DEFAULT_DOUBLE = new Double(0.0d);
    private static Long DEFAULT_INT64 = new Long(0);
    private static List<Object> DEFAULT_LIST = new ArrayList(0);
    private static Map<Object, Object> DEFAULT_MAP = new HashMap();
    private static TypedNumber DEFAULT_NUMBER = TypedNumber.numberWithInt64(0);
    private static final Object DEFAULT_OBJECT = null;
    private static String DEFAULT_STRING = new String("");
    private static Value DEFAULT_VALUE = objectToValue(DEFAULT_STRING);

    private Types() {
    }

    public static Value functionIdToValue(String str) {
        Value value = new Value();
        value.type = 5;
        value.functionId = str;
        return value;
    }

    public static Boolean getDefaultBoolean() {
        return DEFAULT_BOOLEAN;
    }

    public static Double getDefaultDouble() {
        return DEFAULT_DOUBLE;
    }

    public static Long getDefaultInt64() {
        return DEFAULT_INT64;
    }

    public static List<Object> getDefaultList() {
        return DEFAULT_LIST;
    }

    public static Map<Object, Object> getDefaultMap() {
        return DEFAULT_MAP;
    }

    public static TypedNumber getDefaultNumber() {
        return DEFAULT_NUMBER;
    }

    public static Object getDefaultObject() {
        return DEFAULT_OBJECT;
    }

    public static String getDefaultString() {
        return DEFAULT_STRING;
    }

    public static Value getDefaultValue() {
        return DEFAULT_VALUE;
    }

    private static double getDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        Log.e("getDouble received non-Number");
        return 0.0d;
    }

    private static long getInt64(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        Log.e("getInt64 received non-Number");
        return 0;
    }

    private static boolean isDoubleableNumber(Object obj) {
        return (obj instanceof Double) || (obj instanceof Float) || ((obj instanceof TypedNumber) && ((TypedNumber) obj).isDouble());
    }

    private static boolean isInt64ableNumber(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || ((obj instanceof TypedNumber) && ((TypedNumber) obj).isInt64());
    }

    public static Value macroReferenceToValue(String str, int... iArr) {
        Value value = new Value();
        value.type = 4;
        value.macroReference = str;
        value.containsReferences = true;
        value.escaping = (int[]) iArr.clone();
        return value;
    }

    public static Boolean objectToBoolean(Object obj) {
        return obj instanceof Boolean ? (Boolean) obj : parseBoolean(objectToString(obj));
    }

    public static Double objectToDouble(Object obj) {
        return isDoubleableNumber(obj) ? Double.valueOf(getDouble(obj)) : parseDouble(objectToString(obj));
    }

    public static Long objectToInt64(Object obj) {
        return isInt64ableNumber(obj) ? Long.valueOf(getInt64(obj)) : parseInt64(objectToString(obj));
    }

    public static TypedNumber objectToNumber(Object obj) {
        return obj instanceof TypedNumber ? (TypedNumber) obj : isInt64ableNumber(obj) ? TypedNumber.numberWithInt64(getInt64(obj)) : isDoubleableNumber(obj) ? TypedNumber.numberWithDouble(Double.valueOf(getDouble(obj))) : parseNumber(objectToString(obj));
    }

    public static String objectToString(Object obj) {
        return obj == null ? DEFAULT_STRING : obj.toString();
    }

    public static Value objectToValue(Object obj) {
        Value value = new Value();
        if (obj instanceof Value) {
            return (Value) obj;
        }
        boolean z;
        if (obj instanceof String) {
            value.type = 1;
            value.string = (String) obj;
            z = false;
        } else if (obj instanceof List) {
            value.type = 2;
            List<Object> list = (List) obj;
            List arrayList = new ArrayList(list.size());
            r2 = false;
            for (Object objectToValue : list) {
                Value objectToValue2 = objectToValue(objectToValue);
                if (objectToValue2 == DEFAULT_VALUE) {
                    return DEFAULT_VALUE;
                }
                r2 = r2 || objectToValue2.containsReferences;
                arrayList.add(objectToValue2);
            }
            value.listItem = (Value[]) arrayList.toArray(new Value[0]);
            z = r2;
        } else if (obj instanceof Map) {
            value.type = 3;
            Set<Entry> entrySet = ((Map) obj).entrySet();
            List arrayList2 = new ArrayList(entrySet.size());
            List arrayList3 = new ArrayList(entrySet.size());
            r2 = false;
            for (Entry entry : entrySet) {
                Value objectToValue3 = objectToValue(entry.getKey());
                Value objectToValue4 = objectToValue(entry.getValue());
                if (objectToValue3 == DEFAULT_VALUE || objectToValue4 == DEFAULT_VALUE) {
                    return DEFAULT_VALUE;
                }
                r2 = r2 || objectToValue3.containsReferences || objectToValue4.containsReferences;
                arrayList2.add(objectToValue3);
                arrayList3.add(objectToValue4);
            }
            value.mapKey = (Value[]) arrayList2.toArray(new Value[0]);
            value.mapValue = (Value[]) arrayList3.toArray(new Value[0]);
            z = r2;
        } else if (isDoubleableNumber(obj)) {
            value.type = 1;
            value.string = obj.toString();
            z = false;
        } else if (isInt64ableNumber(obj)) {
            value.type = 6;
            value.integer = getInt64(obj);
            z = false;
        } else if (obj instanceof Boolean) {
            value.type = 8;
            value.boolean_ = ((Boolean) obj).booleanValue();
            z = false;
        } else {
            Log.e("Converting to Value from unknown object type: " + (obj == null ? "null" : obj.getClass().toString()));
            return DEFAULT_VALUE;
        }
        value.containsReferences = z;
        return value;
    }

    private static Boolean parseBoolean(String str) {
        return "true".equalsIgnoreCase(str) ? Boolean.TRUE : "false".equalsIgnoreCase(str) ? Boolean.FALSE : DEFAULT_BOOLEAN;
    }

    private static Double parseDouble(String str) {
        TypedNumber parseNumber = parseNumber(str);
        return parseNumber == DEFAULT_NUMBER ? DEFAULT_DOUBLE : Double.valueOf(parseNumber.doubleValue());
    }

    private static Long parseInt64(String str) {
        TypedNumber parseNumber = parseNumber(str);
        return parseNumber == DEFAULT_NUMBER ? DEFAULT_INT64 : Long.valueOf(parseNumber.longValue());
    }

    private static TypedNumber parseNumber(String str) {
        try {
            return TypedNumber.numberWithString(str);
        } catch (NumberFormatException e) {
            Log.e("Failed to convert '" + str + "' to a number.");
            return DEFAULT_NUMBER;
        }
    }

    public static Value templateToValue(Value... valueArr) {
        Value value = new Value();
        value.type = 7;
        value.templateToken = new Value[valueArr.length];
        boolean z = false;
        for (int i = 0; i < valueArr.length; i++) {
            value.templateToken[i] = valueArr[i];
            z = z || valueArr[i].containsReferences;
        }
        value.containsReferences = z;
        return value;
    }

    public static Boolean valueToBoolean(Value value) {
        return objectToBoolean(valueToObject(value));
    }

    public static Double valueToDouble(Value value) {
        return objectToDouble(valueToObject(value));
    }

    public static Long valueToInt64(Value value) {
        return objectToInt64(valueToObject(value));
    }

    public static TypedNumber valueToNumber(Value value) {
        return objectToNumber(valueToObject(value));
    }

    public static Object valueToObject(Value value) {
        int i = 0;
        if (value == null) {
            return DEFAULT_OBJECT;
        }
        Value[] valueArr;
        int length;
        switch (value.type) {
            case 1:
                return value.string;
            case 2:
                ArrayList arrayList = new ArrayList(value.listItem.length);
                valueArr = value.listItem;
                length = valueArr.length;
                while (i < length) {
                    Object valueToObject = valueToObject(valueArr[i]);
                    if (valueToObject == DEFAULT_OBJECT) {
                        return DEFAULT_OBJECT;
                    }
                    arrayList.add(valueToObject);
                    i++;
                }
                return arrayList;
            case 3:
                if (value.mapKey.length != value.mapValue.length) {
                    Log.e("Converting an invalid value to object: " + value.toString());
                    return DEFAULT_OBJECT;
                }
                Map linkedHashMap = new LinkedHashMap(value.mapValue.length);
                while (i < value.mapKey.length) {
                    Object valueToObject2 = valueToObject(value.mapKey[i]);
                    Object valueToObject3 = valueToObject(value.mapValue[i]);
                    if (valueToObject2 == DEFAULT_OBJECT || valueToObject3 == DEFAULT_OBJECT) {
                        return DEFAULT_OBJECT;
                    }
                    linkedHashMap.put(valueToObject2, valueToObject3);
                    i++;
                }
                return linkedHashMap;
            case 4:
                Log.e("Trying to convert a macro reference to object");
                return DEFAULT_OBJECT;
            case 5:
                Log.e("Trying to convert a function id to object");
                return DEFAULT_OBJECT;
            case 6:
                return Long.valueOf(value.integer);
            case 7:
                StringBuffer stringBuffer = new StringBuffer();
                valueArr = value.templateToken;
                length = valueArr.length;
                while (i < length) {
                    String valueToString = valueToString(valueArr[i]);
                    if (valueToString == DEFAULT_STRING) {
                        return DEFAULT_OBJECT;
                    }
                    stringBuffer.append(valueToString);
                    i++;
                }
                return stringBuffer.toString();
            case 8:
                return Boolean.valueOf(value.boolean_);
            default:
                Log.e("Failed to convert a value of type: " + value.type);
                return DEFAULT_OBJECT;
        }
    }

    public static String valueToString(Value value) {
        return objectToString(valueToObject(value));
    }
}
