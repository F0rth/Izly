package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.ClassUtil;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.Collections;

public abstract class AnnotatedMember extends Annotated implements Serializable {
    private static final long serialVersionUID = 1;
    protected final transient AnnotationMap _annotations;
    protected final transient TypeResolutionContext _typeContext;

    protected AnnotatedMember(AnnotatedMember annotatedMember) {
        this._typeContext = annotatedMember._typeContext;
        this._annotations = annotatedMember._annotations;
    }

    protected AnnotatedMember(TypeResolutionContext typeResolutionContext, AnnotationMap annotationMap) {
        this._typeContext = typeResolutionContext;
        this._annotations = annotationMap;
    }

    public final boolean addIfNotPresent(Annotation annotation) {
        return this._annotations.addIfNotPresent(annotation);
    }

    public final boolean addOrOverride(Annotation annotation) {
        return this._annotations.add(annotation);
    }

    public Iterable<Annotation> annotations() {
        return this._annotations == null ? Collections.emptyList() : this._annotations.annotations();
    }

    @Deprecated
    public final void fixAccess() {
        fixAccess(true);
    }

    public final void fixAccess(boolean z) {
        ClassUtil.checkAndFixAccess(getMember(), z);
    }

    protected AnnotationMap getAllAnnotations() {
        return this._annotations;
    }

    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        return this._annotations == null ? null : this._annotations.get(cls);
    }

    public abstract Class<?> getDeclaringClass();

    public abstract Member getMember();

    public TypeResolutionContext getTypeContext() {
        return this._typeContext;
    }

    public abstract Object getValue(Object obj) throws UnsupportedOperationException, IllegalArgumentException;

    public final boolean hasAnnotation(Class<?> cls) {
        return this._annotations == null ? false : this._annotations.has(cls);
    }

    public boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
        return this._annotations == null ? false : this._annotations.hasOneOf(clsArr);
    }

    public abstract void setValue(Object obj, Object obj2) throws UnsupportedOperationException, IllegalArgumentException;
}
