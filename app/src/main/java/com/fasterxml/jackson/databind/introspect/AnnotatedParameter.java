package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.JavaType;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public final class AnnotatedParameter extends AnnotatedMember {
    private static final long serialVersionUID = 1;
    protected final int _index;
    protected final AnnotatedWithParams _owner;
    protected final JavaType _type;

    public AnnotatedParameter(AnnotatedWithParams annotatedWithParams, JavaType javaType, AnnotationMap annotationMap, int i) {
        super(annotatedWithParams == null ? null : annotatedWithParams.getTypeContext(), annotationMap);
        this._owner = annotatedWithParams;
        this._type = javaType;
        this._index = i;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            AnnotatedParameter annotatedParameter = (AnnotatedParameter) obj;
            if (!annotatedParameter._owner.equals(this._owner)) {
                return false;
            }
            if (annotatedParameter._index != this._index) {
                return false;
            }
        }
        return true;
    }

    public final AnnotatedElement getAnnotated() {
        return null;
    }

    public final Class<?> getDeclaringClass() {
        return this._owner.getDeclaringClass();
    }

    public final int getIndex() {
        return this._index;
    }

    public final Member getMember() {
        return this._owner.getMember();
    }

    public final int getModifiers() {
        return this._owner.getModifiers();
    }

    public final String getName() {
        return "";
    }

    public final AnnotatedWithParams getOwner() {
        return this._owner;
    }

    public final Type getParameterType() {
        return this._type;
    }

    public final Class<?> getRawType() {
        return this._type.getRawClass();
    }

    public final JavaType getType() {
        return this._typeContext.resolveType(this._type);
    }

    public final Object getValue(Object obj) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call getValue() on constructor parameter of " + getDeclaringClass().getName());
    }

    public final int hashCode() {
        return this._owner.hashCode() + this._index;
    }

    public final void setValue(Object obj, Object obj2) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Cannot call setValue() on constructor parameter of " + getDeclaringClass().getName());
    }

    public final String toString() {
        return "[parameter #" + getIndex() + ", annotations: " + this._annotations + "]";
    }

    public final AnnotatedParameter withAnnotations(AnnotationMap annotationMap) {
        return annotationMap == this._annotations ? this : this._owner.replaceParameterAnnotations(this._index, annotationMap);
    }
}
