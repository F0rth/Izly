package com.google.gson.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class UnsafeAllocator {
    private static void assertInstantiable(Class<?> cls) {
        int modifiers = cls.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            throw new UnsupportedOperationException("Interface can't be instantiated! Interface name: " + cls.getName());
        } else if (Modifier.isAbstract(modifiers)) {
            throw new UnsupportedOperationException("Abstract class can't be instantiated! Class name: " + cls.getName());
        }
    }

    public static UnsafeAllocator create() {
        final Method method;
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            final Object obj = declaredField.get(null);
            method = cls.getMethod("allocateInstance", new Class[]{Class.class});
            return new UnsafeAllocator() {
                public final <T> T newInstance(Class<T> cls) throws Exception {
                    UnsafeAllocator.assertInstantiable(cls);
                    return method.invoke(obj, new Object[]{cls});
                }
            };
        } catch (Exception e) {
            try {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                final int intValue = ((Integer) declaredMethod.invoke(null, new Object[]{Object.class})).intValue();
                final Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return new UnsafeAllocator() {
                    public final <T> T newInstance(Class<T> cls) throws Exception {
                        UnsafeAllocator.assertInstantiable(cls);
                        return declaredMethod2.invoke(null, new Object[]{cls, Integer.valueOf(intValue)});
                    }
                };
            } catch (Exception e2) {
                try {
                    method = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    method.setAccessible(true);
                    return new UnsafeAllocator() {
                        public final <T> T newInstance(Class<T> cls) throws Exception {
                            UnsafeAllocator.assertInstantiable(cls);
                            return method.invoke(null, new Object[]{cls, Object.class});
                        }
                    };
                } catch (Exception e3) {
                    return new UnsafeAllocator() {
                        public final <T> T newInstance(Class<T> cls) {
                            throw new UnsupportedOperationException("Cannot allocate " + cls);
                        }
                    };
                }
            }
        }
    }

    public abstract <T> T newInstance(Class<T> cls) throws Exception;
}
