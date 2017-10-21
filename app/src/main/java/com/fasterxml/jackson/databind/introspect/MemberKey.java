package com.fasterxml.jackson.databind.introspect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public final class MemberKey {
    static final Class<?>[] NO_CLASSES = new Class[0];
    final Class<?>[] _argTypes;
    final String _name;

    public MemberKey(String str, Class<?>[] clsArr) {
        Class[] clsArr2;
        this._name = str;
        if (clsArr == null) {
            clsArr2 = NO_CLASSES;
        }
        this._argTypes = clsArr2;
    }

    public MemberKey(Constructor<?> constructor) {
        this("", constructor.getParameterTypes());
    }

    public MemberKey(Method method) {
        this(method.getName(), method.getParameterTypes());
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            MemberKey memberKey = (MemberKey) obj;
            if (!this._name.equals(memberKey._name)) {
                return false;
            }
            Class[] clsArr = memberKey._argTypes;
            int length = this._argTypes.length;
            if (clsArr.length != length) {
                return false;
            }
            for (int i = 0; i < length; i++) {
                if (clsArr[i] != this._argTypes[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    public final int hashCode() {
        return this._name.hashCode() + this._argTypes.length;
    }

    public final String toString() {
        return this._name + "(" + this._argTypes.length + "-args)";
    }
}
