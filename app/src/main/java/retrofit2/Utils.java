package retrofit2;

import defpackage.nw;
import defpackage.om;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.NoSuchElementException;
import okhttp3.ResponseBody;

final class Utils {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    static final class GenericArrayTypeImpl implements GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = type;
        }

        public final boolean equals(Object obj) {
            return (obj instanceof GenericArrayType) && Utils.equals(this, (GenericArrayType) obj);
        }

        public final Type getGenericComponentType() {
            return this.componentType;
        }

        public final int hashCode() {
            return this.componentType.hashCode();
        }

        public final String toString() {
            return Utils.typeToString(this.componentType) + "[]";
        }
    }

    static final class ParameterizedTypeImpl implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        public ParameterizedTypeImpl(Type type, Type type2, Type... typeArr) {
            int i;
            int i2 = 1;
            int i3 = 0;
            if (type2 instanceof Class) {
                i = type == null ? 1 : 0;
                if (((Class) type2).getEnclosingClass() != null) {
                    i2 = 0;
                }
                if (i != i2) {
                    throw new IllegalArgumentException();
                }
            }
            this.ownerType = type;
            this.rawType = type2;
            this.typeArguments = (Type[]) typeArr.clone();
            Type[] typeArr2 = this.typeArguments;
            i = typeArr2.length;
            while (i3 < i) {
                Type type3 = typeArr2[i3];
                if (type3 == null) {
                    throw new NullPointerException();
                }
                Utils.checkNotPrimitive(type3);
                i3++;
            }
        }

        public final boolean equals(Object obj) {
            return (obj instanceof ParameterizedType) && Utils.equals(this, (ParameterizedType) obj);
        }

        public final Type[] getActualTypeArguments() {
            return (Type[]) this.typeArguments.clone();
        }

        public final Type getOwnerType() {
            return this.ownerType;
        }

        public final Type getRawType() {
            return this.rawType;
        }

        public final int hashCode() {
            return (Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode()) ^ Utils.hashCodeOrZero(this.ownerType);
        }

        public final String toString() {
            StringBuilder stringBuilder = new StringBuilder((this.typeArguments.length + 1) * 30);
            stringBuilder.append(Utils.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append(Utils.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; i++) {
                stringBuilder.append(", ").append(Utils.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    static final class WildcardTypeImpl implements WildcardType {
        private final Type lowerBound;
        private final Type upperBound;

        public WildcardTypeImpl(Type[] typeArr, Type[] typeArr2) {
            if (typeArr2.length > 1) {
                throw new IllegalArgumentException();
            } else if (typeArr.length != 1) {
                throw new IllegalArgumentException();
            } else if (typeArr2.length == 1) {
                if (typeArr2[0] == null) {
                    throw new NullPointerException();
                }
                Utils.checkNotPrimitive(typeArr2[0]);
                if (typeArr[0] != Object.class) {
                    throw new IllegalArgumentException();
                }
                this.lowerBound = typeArr2[0];
                this.upperBound = Object.class;
            } else if (typeArr[0] == null) {
                throw new NullPointerException();
            } else {
                Utils.checkNotPrimitive(typeArr[0]);
                this.lowerBound = null;
                this.upperBound = typeArr[0];
            }
        }

        public final boolean equals(Object obj) {
            return (obj instanceof WildcardType) && Utils.equals(this, (WildcardType) obj);
        }

        public final Type[] getLowerBounds() {
            if (this.lowerBound == null) {
                return Utils.EMPTY_TYPE_ARRAY;
            }
            return new Type[]{this.lowerBound};
        }

        public final Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        public final int hashCode() {
            return (this.lowerBound != null ? this.lowerBound.hashCode() + 31 : 1) ^ (this.upperBound.hashCode() + 31);
        }

        public final String toString() {
            return this.lowerBound != null ? "? super " + Utils.typeToString(this.lowerBound) : this.upperBound == Object.class ? "?" : "? extends " + Utils.typeToString(this.upperBound);
        }
    }

    private Utils() {
    }

    static ResponseBody buffer(ResponseBody responseBody) throws IOException {
        om nwVar = new nw();
        responseBody.source().a(nwVar);
        return ResponseBody.create(responseBody.contentType(), responseBody.contentLength(), nwVar);
    }

    static <T> T checkNotNull(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static void checkNotPrimitive(Type type) {
        if ((type instanceof Class) && ((Class) type).isPrimitive()) {
            throw new IllegalArgumentException();
        }
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        return genericDeclaration instanceof Class ? (Class) genericDeclaration : null;
    }

    private static boolean equal(Object obj, Object obj2) {
        return obj == obj2 || (obj != null && obj.equals(obj2));
    }

    static boolean equals(Type type, Type type2) {
        Object obj = type;
        Object obj2 = type2;
        while (obj != obj2) {
            if (obj instanceof Class) {
                return obj.equals(obj2);
            }
            if (obj instanceof ParameterizedType) {
                if (!(obj2 instanceof ParameterizedType)) {
                    return false;
                }
                ParameterizedType parameterizedType = (ParameterizedType) obj;
                ParameterizedType parameterizedType2 = (ParameterizedType) obj2;
                return equal(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType()) && parameterizedType.getRawType().equals(parameterizedType2.getRawType()) && Arrays.equals(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
            } else if (obj instanceof GenericArrayType) {
                if (!(obj2 instanceof GenericArrayType)) {
                    return false;
                }
                GenericArrayType genericArrayType = (GenericArrayType) obj2;
                obj = ((GenericArrayType) obj).getGenericComponentType();
                obj2 = genericArrayType.getGenericComponentType();
            } else if (obj instanceof WildcardType) {
                if (!(obj2 instanceof WildcardType)) {
                    return false;
                }
                WildcardType wildcardType = (WildcardType) obj;
                WildcardType wildcardType2 = (WildcardType) obj2;
                return Arrays.equals(wildcardType.getUpperBounds(), wildcardType2.getUpperBounds()) && Arrays.equals(wildcardType.getLowerBounds(), wildcardType2.getLowerBounds());
            } else if (!(obj instanceof TypeVariable)) {
                return false;
            } else {
                if (!(obj2 instanceof TypeVariable)) {
                    return false;
                }
                TypeVariable typeVariable = (TypeVariable) obj;
                TypeVariable typeVariable2 = (TypeVariable) obj2;
                return typeVariable.getGenericDeclaration() == typeVariable2.getGenericDeclaration() && typeVariable.getName().equals(typeVariable2.getName());
            }
        }
        return true;
    }

    static Type getCallResponseType(Type type) {
        if (type instanceof ParameterizedType) {
            return getParameterUpperBound(0, (ParameterizedType) type);
        }
        throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
    }

    static Type getGenericSupertype(Type type, Class<?> cls, Class<?> cls2) {
        Type type2 = type;
        Class<?> cls3 = cls;
        while (cls2 != cls3) {
            if (cls2.isInterface()) {
                Class[] interfaces = cls3.getInterfaces();
                int length = interfaces.length;
                for (int i = 0; i < length; i++) {
                    if (interfaces[i] == cls2) {
                        return cls3.getGenericInterfaces()[i];
                    }
                    if (cls2.isAssignableFrom(interfaces[i])) {
                        type2 = cls3.getGenericInterfaces()[i];
                        cls3 = interfaces[i];
                        break;
                    }
                }
            }
            if (cls3.isInterface()) {
                return cls2;
            }
            Class cls4 = cls3;
            while (cls4 != Object.class) {
                cls3 = cls4.getSuperclass();
                if (cls3 == cls2) {
                    return cls4.getGenericSuperclass();
                }
                if (cls2.isAssignableFrom(cls3)) {
                    type2 = cls4.getGenericSuperclass();
                } else {
                    Class<?> cls5 = cls3;
                }
            }
            return cls2;
        }
        return type2;
    }

    static Type getParameterUpperBound(int i, ParameterizedType parameterizedType) {
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        if (i < 0 || i >= actualTypeArguments.length) {
            throw new IllegalArgumentException("Index " + i + " not in range [0," + actualTypeArguments.length + ") for " + parameterizedType);
        }
        Type type = actualTypeArguments[i];
        return type instanceof WildcardType ? ((WildcardType) type).getUpperBounds()[0] : type;
    }

    static Class<?> getRawType(Type type) {
        Object obj = type;
        while (obj != null) {
            if (obj instanceof Class) {
                return (Class) obj;
            }
            if (obj instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) obj).getRawType();
                if (rawType instanceof Class) {
                    return (Class) rawType;
                }
                throw new IllegalArgumentException();
            } else if (obj instanceof GenericArrayType) {
                return Array.newInstance(getRawType(((GenericArrayType) obj).getGenericComponentType()), 0).getClass();
            } else {
                if (obj instanceof TypeVariable) {
                    return Object.class;
                }
                if (obj instanceof WildcardType) {
                    obj = ((WildcardType) obj).getUpperBounds()[0];
                } else {
                    throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + obj + "> is of type " + obj.getClass().getName());
                }
            }
        }
        throw new NullPointerException("type == null");
    }

    static Type getSupertype(Type type, Class<?> cls, Class<?> cls2) {
        if (cls2.isAssignableFrom(cls)) {
            return resolve(type, cls, getGenericSupertype(type, cls, cls2));
        }
        throw new IllegalArgumentException();
    }

    static boolean hasUnresolvableType(Type type) {
        Object obj = type;
        while (!(obj instanceof Class)) {
            if (obj instanceof ParameterizedType) {
                for (Type hasUnresolvableType : ((ParameterizedType) obj).getActualTypeArguments()) {
                    if (hasUnresolvableType(hasUnresolvableType)) {
                        return true;
                    }
                }
                return false;
            } else if (obj instanceof GenericArrayType) {
                obj = ((GenericArrayType) obj).getGenericComponentType();
            } else if (obj instanceof TypeVariable) {
                return true;
            } else {
                if (obj instanceof WildcardType) {
                    return true;
                }
                throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + obj + "> is of type " + (obj == null ? "null" : obj.getClass().getName()));
            }
        }
        return false;
    }

    static int hashCodeOrZero(Object obj) {
        return obj != null ? obj.hashCode() : 0;
    }

    private static int indexOf(Object[] objArr, Object obj) {
        for (int i = 0; i < objArr.length; i++) {
            if (obj.equals(objArr[i])) {
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    static boolean isAnnotationPresent(Annotation[] annotationArr, Class<? extends Annotation> cls) {
        for (Object isInstance : annotationArr) {
            if (cls.isInstance(isInstance)) {
                return true;
            }
        }
        return false;
    }

    static Type resolve(Type type, Class<?> cls, Type type2) {
        Type type3 = type2;
        while (type3 instanceof TypeVariable) {
            type3 = (TypeVariable) type3;
            type2 = resolveTypeVariable(type, cls, type3);
            if (type2 == type3) {
                return type2;
            }
            type3 = type2;
        }
        Type componentType;
        Type resolve;
        if ((type3 instanceof Class) && ((Class) type3).isArray()) {
            Class cls2 = (Class) type3;
            componentType = cls2.getComponentType();
            resolve = resolve(type, cls, componentType);
            return componentType != resolve ? new GenericArrayTypeImpl(resolve) : cls2;
        } else if (type3 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type3;
            componentType = genericArrayType.getGenericComponentType();
            resolve = resolve(type, cls, componentType);
            return componentType != resolve ? new GenericArrayTypeImpl(resolve) : genericArrayType;
        } else if (type3 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type3;
            componentType = parameterizedType.getOwnerType();
            Type resolve2 = resolve(type, cls, componentType);
            int i = resolve2 != componentType ? 1 : 0;
            r4 = parameterizedType.getActualTypeArguments();
            int length = r4.length;
            Type[] typeArr = r4;
            Type[] typeArr2 = typeArr;
            for (int i2 = 0; i2 < length; i2++) {
                Type resolve3 = resolve(type, cls, typeArr2[i2]);
                if (resolve3 != typeArr2[i2]) {
                    if (i == 0) {
                        typeArr2 = (Type[]) typeArr2.clone();
                        i = 1;
                    }
                    typeArr2[i2] = resolve3;
                }
            }
            return i != 0 ? new ParameterizedTypeImpl(resolve2, parameterizedType.getRawType(), typeArr2) : parameterizedType;
        } else if (!(type3 instanceof WildcardType)) {
            return type3;
        } else {
            WildcardType wildcardType = (WildcardType) type3;
            Type[] lowerBounds = wildcardType.getLowerBounds();
            r4 = wildcardType.getUpperBounds();
            if (lowerBounds.length == 1) {
                if (resolve(type, cls, lowerBounds[0]) == lowerBounds[0]) {
                    return wildcardType;
                }
                return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{resolve(type, cls, lowerBounds[0])});
            } else if (r4.length != 1 || resolve(type, cls, r4[0]) == r4[0]) {
                return wildcardType;
            } else {
                return new WildcardTypeImpl(new Type[]{resolve(type, cls, r4[0])}, EMPTY_TYPE_ARRAY);
            }
        }
    }

    private static Type resolveTypeVariable(Type type, Class<?> cls, TypeVariable<?> typeVariable) {
        Class declaringClassOf = declaringClassOf(typeVariable);
        if (declaringClassOf == null) {
            return typeVariable;
        }
        Type genericSupertype = getGenericSupertype(type, cls, declaringClassOf);
        if (!(genericSupertype instanceof ParameterizedType)) {
            return typeVariable;
        }
        return ((ParameterizedType) genericSupertype).getActualTypeArguments()[indexOf(declaringClassOf.getTypeParameters(), typeVariable)];
    }

    static String typeToString(Type type) {
        return type instanceof Class ? ((Class) type).getName() : type.toString();
    }

    static <T> void validateServiceInterface(Class<T> cls) {
        if (!cls.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        } else if (cls.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }
}
