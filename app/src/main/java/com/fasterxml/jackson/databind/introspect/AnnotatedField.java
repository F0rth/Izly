package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public final class AnnotatedField extends AnnotatedMember implements Serializable {
    private static final long serialVersionUID = 1;
    protected final transient Field _field;
    protected Serialization _serialization;

    static final class Serialization implements Serializable {
        private static final long serialVersionUID = 1;
        protected Class<?> clazz;
        protected String name;

        public Serialization(Field field) {
            this.clazz = field.getDeclaringClass();
            this.name = field.getName();
        }
    }

    protected AnnotatedField(Serialization serialization) {
        super(null, null);
        this._field = null;
        this._serialization = serialization;
    }

    public AnnotatedField(TypeResolutionContext typeResolutionContext, Field field, AnnotationMap annotationMap) {
        super(typeResolutionContext, annotationMap);
        this._field = field;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            if (((AnnotatedField) obj)._field != this._field) {
                return false;
            }
        }
        return true;
    }

    public final Field getAnnotated() {
        return this._field;
    }

    public final int getAnnotationCount() {
        return this._annotations.size();
    }

    public final Class<?> getDeclaringClass() {
        return this._field.getDeclaringClass();
    }

    public final String getFullName() {
        return getDeclaringClass().getName() + "#" + getName();
    }

    public final Member getMember() {
        return this._field;
    }

    public final int getModifiers() {
        return this._field.getModifiers();
    }

    public final String getName() {
        return this._field.getName();
    }

    public final Class<?> getRawType() {
        return this._field.getType();
    }

    public final JavaType getType() {
        return this._typeContext.resolveType(this._field.getGenericType());
    }

    public final Object getValue(Object obj) throws IllegalArgumentException {
        try {
            return this._field.get(obj);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Failed to getValue() for field " + getFullName() + ": " + e.getMessage(), e);
        }
    }

    public final int hashCode() {
        return this._field.getName().hashCode();
    }

    public final boolean isTransient() {
        return Modifier.isTransient(getModifiers());
    }

    final Object readResolve() {
        Class cls = this._serialization.clazz;
        try {
            Object declaredField = cls.getDeclaredField(this._serialization.name);
            if (!declaredField.isAccessible()) {
                ClassUtil.checkAndFixAccess(declaredField, false);
            }
            return new AnnotatedField(null, declaredField, null);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not find method '" + this._serialization.name + "' from Class '" + cls.getName());
        }
    }

    public final void setValue(Object obj, Object obj2) throws IllegalArgumentException {
        try {
            this._field.set(obj, obj2);
        } catch (Throwable e) {
            throw new IllegalArgumentException("Failed to setValue() for field " + getFullName() + ": " + e.getMessage(), e);
        }
    }

    public final String toString() {
        return "[field " + getFullName() + "]";
    }

    public final AnnotatedField withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedField(this._typeContext, this._field, annotationMap);
    }

    final Object writeReplace() {
        return new AnnotatedField(new Serialization(this._field));
    }
}
