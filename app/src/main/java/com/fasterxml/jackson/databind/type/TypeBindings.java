package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;
import java.io.Serializable;
import java.lang.reflect.TypeVariable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TypeBindings implements Serializable {
    private static final TypeBindings EMPTY = new TypeBindings(NO_STRINGS, NO_TYPES, null);
    private static final String[] NO_STRINGS = new String[0];
    private static final JavaType[] NO_TYPES = new JavaType[0];
    private static final long serialVersionUID = 1;
    private final int _hashCode;
    private final String[] _names;
    private final JavaType[] _types;
    private final String[] _unboundVariables;

    static class TypeParamStash {
        private static final TypeVariable<?>[] VARS_ABSTRACT_LIST = AbstractList.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_ARRAY_LIST = ArrayList.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_COLLECTION = Collection.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_HASH_MAP = HashMap.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_ITERABLE = Iterable.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_LINKED_HASH_MAP = LinkedHashMap.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_LIST = List.class.getTypeParameters();
        private static final TypeVariable<?>[] VARS_MAP = Map.class.getTypeParameters();

        TypeParamStash() {
        }

        public static TypeVariable<?>[] paramsFor1(Class<?> cls) {
            return cls == Collection.class ? VARS_COLLECTION : cls == List.class ? VARS_LIST : cls == ArrayList.class ? VARS_ARRAY_LIST : cls == AbstractList.class ? VARS_ABSTRACT_LIST : cls == Iterable.class ? VARS_ITERABLE : cls.getTypeParameters();
        }

        public static TypeVariable<?>[] paramsFor2(Class<?> cls) {
            return cls == Map.class ? VARS_MAP : cls == HashMap.class ? VARS_HASH_MAP : cls == LinkedHashMap.class ? VARS_LINKED_HASH_MAP : cls.getTypeParameters();
        }
    }

    private TypeBindings(String[] strArr, JavaType[] javaTypeArr, String[] strArr2) {
        if (strArr == null) {
            strArr = NO_STRINGS;
        }
        this._names = strArr;
        if (javaTypeArr == null) {
            javaTypeArr = NO_TYPES;
        }
        this._types = javaTypeArr;
        if (this._names.length != this._types.length) {
            throw new IllegalArgumentException("Mismatching names (" + this._names.length + "), types (" + this._types.length + ")");
        }
        int i = 1;
        for (JavaType hashCode : this._types) {
            i += hashCode.hashCode();
        }
        this._unboundVariables = strArr2;
        this._hashCode = i;
    }

    public static TypeBindings create(Class<?> cls, JavaType javaType) {
        TypeVariable[] paramsFor1 = TypeParamStash.paramsFor1(cls);
        int length = paramsFor1 == null ? 0 : paramsFor1.length;
        if (length != 1) {
            throw new IllegalArgumentException("Can not create TypeBindings for class " + cls.getName() + " with 1 type parameter: class expects " + length);
        }
        return new TypeBindings(new String[]{paramsFor1[0].getName()}, new JavaType[]{javaType}, null);
    }

    public static TypeBindings create(Class<?> cls, JavaType javaType, JavaType javaType2) {
        TypeVariable[] paramsFor2 = TypeParamStash.paramsFor2(cls);
        int length = paramsFor2 == null ? 0 : paramsFor2.length;
        if (length != 2) {
            throw new IllegalArgumentException("Can not create TypeBindings for class " + cls.getName() + " with 2 type parameters: class expects " + length);
        }
        return new TypeBindings(new String[]{paramsFor2[0].getName(), paramsFor2[1].getName()}, new JavaType[]{javaType, javaType2}, null);
    }

    public static TypeBindings create(Class<?> cls, List<JavaType> list) {
        JavaType[] javaTypeArr = (list == null || list.isEmpty()) ? NO_TYPES : (JavaType[]) list.toArray(new JavaType[list.size()]);
        return create((Class) cls, javaTypeArr);
    }

    public static TypeBindings create(Class<?> cls, JavaType[] javaTypeArr) {
        String[] strArr;
        int i = 0;
        if (javaTypeArr != null) {
            switch (javaTypeArr.length) {
                case 1:
                    return create((Class) cls, javaTypeArr[0]);
                case 2:
                    return create(cls, javaTypeArr[0], javaTypeArr[1]);
                default:
                    break;
            }
        }
        javaTypeArr = NO_TYPES;
        TypeVariable[] typeParameters = cls.getTypeParameters();
        if (typeParameters == null || typeParameters.length == 0) {
            strArr = NO_STRINGS;
        } else {
            int length = typeParameters.length;
            strArr = new String[length];
            while (i < length) {
                strArr[i] = typeParameters[i].getName();
                i++;
            }
        }
        if (strArr.length == javaTypeArr.length) {
            return new TypeBindings(strArr, javaTypeArr, null);
        }
        throw new IllegalArgumentException("Can not create TypeBindings for class " + cls.getName() + " with " + javaTypeArr.length + " type parameter" + (javaTypeArr.length == 1 ? "" : "s") + ": class expects " + strArr.length);
    }

    public static TypeBindings createIfNeeded(Class<?> cls, JavaType javaType) {
        TypeVariable[] typeParameters = cls.getTypeParameters();
        int length = typeParameters == null ? 0 : typeParameters.length;
        if (length == 0) {
            return EMPTY;
        }
        if (length != 1) {
            throw new IllegalArgumentException("Can not create TypeBindings for class " + cls.getName() + " with 1 type parameter: class expects " + length);
        }
        return new TypeBindings(new String[]{typeParameters[0].getName()}, new JavaType[]{javaType}, null);
    }

    public static TypeBindings createIfNeeded(Class<?> cls, JavaType[] javaTypeArr) {
        TypeVariable[] typeParameters = cls.getTypeParameters();
        if (typeParameters == null || typeParameters.length == 0) {
            return EMPTY;
        }
        if (javaTypeArr == null) {
            javaTypeArr = NO_TYPES;
        }
        int length = typeParameters.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = typeParameters[i].getName();
        }
        if (strArr.length == javaTypeArr.length) {
            return new TypeBindings(strArr, javaTypeArr, null);
        }
        throw new IllegalArgumentException("Can not create TypeBindings for class " + cls.getName() + " with " + javaTypeArr.length + " type parameter" + (javaTypeArr.length == 1 ? "" : "s") + ": class expects " + strArr.length);
    }

    public static TypeBindings emptyBindings() {
        return EMPTY;
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            TypeBindings typeBindings = (TypeBindings) obj;
            int length = this._types.length;
            if (length != typeBindings.size()) {
                return false;
            }
            JavaType[] javaTypeArr = typeBindings._types;
            for (int i = 0; i < length; i++) {
                if (!javaTypeArr[i].equals(this._types[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public JavaType findBoundType(String str) {
        int i = 0;
        int length = this._names.length;
        while (i < length) {
            if (str.equals(this._names[i])) {
                JavaType javaType = this._types[i];
                if (!(javaType instanceof ResolvedRecursiveType)) {
                    return javaType;
                }
                JavaType selfReferencedType = ((ResolvedRecursiveType) javaType).getSelfReferencedType();
                return selfReferencedType != null ? selfReferencedType : javaType;
            } else {
                i++;
            }
        }
        return null;
    }

    public String getBoundName(int i) {
        return (i < 0 || i >= this._names.length) ? null : this._names[i];
    }

    public JavaType getBoundType(int i) {
        return (i < 0 || i >= this._types.length) ? null : this._types[i];
    }

    public List<JavaType> getTypeParameters() {
        return this._types.length == 0 ? Collections.emptyList() : Arrays.asList(this._types);
    }

    public boolean hasUnbound(String str) {
        if (this._unboundVariables != null) {
            int length = this._unboundVariables.length;
            do {
                length--;
                if (length >= 0) {
                }
            } while (!str.equals(this._unboundVariables[length]));
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this._hashCode;
    }

    public boolean isEmpty() {
        return this._types.length == 0;
    }

    protected Object readResolve() {
        return (this._names == null || this._names.length == 0) ? EMPTY : this;
    }

    public int size() {
        return this._types.length;
    }

    public String toString() {
        if (this._types.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('<');
        int length = this._types.length;
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append(this._types[i].getGenericSignature());
        }
        stringBuilder.append('>');
        return stringBuilder.toString();
    }

    protected JavaType[] typeParameterArray() {
        return this._types;
    }

    public TypeBindings withUnboundVariable(String str) {
        int length = this._unboundVariables == null ? 0 : this._unboundVariables.length;
        String[] strArr = length == 0 ? new String[1] : (String[]) Arrays.copyOf(this._unboundVariables, length + 1);
        strArr[length] = str;
        return new TypeBindings(this._names, this._types, strArr);
    }
}
