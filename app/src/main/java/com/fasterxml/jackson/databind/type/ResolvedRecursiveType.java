package com.fasterxml.jackson.databind.type;

import com.fasterxml.jackson.databind.JavaType;

public class ResolvedRecursiveType extends TypeBase {
    private static final long serialVersionUID = 1;
    protected JavaType _referencedType;

    public ResolvedRecursiveType(Class<?> cls, TypeBindings typeBindings) {
        super(cls, typeBindings, null, null, 0, null, null, false);
    }

    @Deprecated
    protected JavaType _narrow(Class<?> cls) {
        return this;
    }

    public boolean equals(Object obj) {
        return obj == this ? true : (obj == null || obj.getClass() != getClass()) ? false : ((ResolvedRecursiveType) obj).getSelfReferencedType().equals(getSelfReferencedType());
    }

    public StringBuilder getErasedSignature(StringBuilder stringBuilder) {
        return this._referencedType.getErasedSignature(stringBuilder);
    }

    public StringBuilder getGenericSignature(StringBuilder stringBuilder) {
        return this._referencedType.getGenericSignature(stringBuilder);
    }

    public JavaType getSelfReferencedType() {
        return this._referencedType;
    }

    public boolean isContainerType() {
        return false;
    }

    public JavaType refine(Class<?> cls, TypeBindings typeBindings, JavaType javaType, JavaType[] javaTypeArr) {
        return null;
    }

    public void setReference(JavaType javaType) {
        if (this._referencedType != null) {
            throw new IllegalStateException("Trying to re-set self reference; old value = " + this._referencedType + ", new = " + javaType);
        }
        this._referencedType = javaType;
    }

    public String toString() {
        return "[resolved recursive type -> " + this._referencedType + ']';
    }

    public JavaType withContentType(JavaType javaType) {
        return this;
    }

    public JavaType withContentTypeHandler(Object obj) {
        return this;
    }

    public JavaType withContentValueHandler(Object obj) {
        return this;
    }

    public JavaType withStaticTyping() {
        return this;
    }

    public JavaType withTypeHandler(Object obj) {
        return this;
    }

    public JavaType withValueHandler(Object obj) {
        return this;
    }
}
