package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.util.Annotations;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;

public final class AnnotationMap implements Annotations {
    protected HashMap<Class<?>, Annotation> _annotations;

    private AnnotationMap(HashMap<Class<?>, Annotation> hashMap) {
        this._annotations = hashMap;
    }

    public static AnnotationMap merge(AnnotationMap annotationMap, AnnotationMap annotationMap2) {
        if (annotationMap == null || annotationMap._annotations == null || annotationMap._annotations.isEmpty()) {
            return annotationMap2;
        }
        if (annotationMap2 == null || annotationMap2._annotations == null || annotationMap2._annotations.isEmpty()) {
            return annotationMap;
        }
        HashMap hashMap = new HashMap();
        for (Annotation annotation : annotationMap2._annotations.values()) {
            hashMap.put(annotation.annotationType(), annotation);
        }
        for (Annotation annotation2 : annotationMap._annotations.values()) {
            hashMap.put(annotation2.annotationType(), annotation2);
        }
        return new AnnotationMap(hashMap);
    }

    protected final boolean _add(Annotation annotation) {
        if (this._annotations == null) {
            this._annotations = new HashMap();
        }
        Annotation annotation2 = (Annotation) this._annotations.put(annotation.annotationType(), annotation);
        return annotation2 == null || !annotation2.equals(annotation);
    }

    public final boolean add(Annotation annotation) {
        return _add(annotation);
    }

    public final boolean addIfNotPresent(Annotation annotation) {
        if (this._annotations != null && this._annotations.containsKey(annotation.annotationType())) {
            return false;
        }
        _add(annotation);
        return true;
    }

    public final Iterable<Annotation> annotations() {
        return (this._annotations == null || this._annotations.size() == 0) ? Collections.emptyList() : this._annotations.values();
    }

    public final <A extends Annotation> A get(Class<A> cls) {
        return this._annotations == null ? null : (Annotation) this._annotations.get(cls);
    }

    public final boolean has(Class<?> cls) {
        return this._annotations == null ? false : this._annotations.containsKey(cls);
    }

    public final boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
        if (this._annotations == null) {
            return false;
        }
        for (Object containsKey : clsArr) {
            if (this._annotations.containsKey(containsKey)) {
                return true;
            }
        }
        return false;
    }

    public final int size() {
        return this._annotations == null ? 0 : this._annotations.size();
    }

    public final String toString() {
        return this._annotations == null ? "[null]" : this._annotations.toString();
    }
}
