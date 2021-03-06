package com.fasterxml.jackson.databind.introspect;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.ClassIntrospector.MixInResolver;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext.Basic;
import com.fasterxml.jackson.databind.type.TypeBindings;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Annotations;
import com.fasterxml.jackson.databind.util.ClassUtil;
import com.fasterxml.jackson.databind.util.ClassUtil.Ctor;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class AnnotatedClass extends Annotated implements TypeResolutionContext {
    private static final AnnotationMap[] NO_ANNOTATION_MAPS = new AnnotationMap[0];
    protected final AnnotationIntrospector _annotationIntrospector;
    protected final TypeBindings _bindings;
    protected final Class<?> _class;
    protected AnnotationMap _classAnnotations;
    protected List<AnnotatedConstructor> _constructors;
    protected List<AnnotatedMethod> _creatorMethods;
    protected boolean _creatorsResolved = false;
    protected AnnotatedConstructor _defaultConstructor;
    protected List<AnnotatedField> _fields;
    protected AnnotatedMethodMap _memberMethods;
    protected final MixInResolver _mixInResolver;
    protected final Class<?> _primaryMixIn;
    protected final List<JavaType> _superTypes;
    protected final JavaType _type;
    protected final TypeFactory _typeFactory;

    private AnnotatedClass(JavaType javaType, Class<?> cls, TypeBindings typeBindings, List<JavaType> list, AnnotationIntrospector annotationIntrospector, MixInResolver mixInResolver, TypeFactory typeFactory, AnnotationMap annotationMap) {
        this._type = javaType;
        this._class = cls;
        this._bindings = typeBindings;
        this._superTypes = list;
        this._annotationIntrospector = annotationIntrospector;
        this._typeFactory = typeFactory;
        this._mixInResolver = mixInResolver;
        this._primaryMixIn = this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(this._class);
        this._classAnnotations = annotationMap;
    }

    private AnnotationMap _addAnnotationsIfNotPresent(AnnotationMap annotationMap, Annotation[] annotationArr) {
        if (annotationArr != null) {
            List list = null;
            for (Annotation annotation : annotationArr) {
                if (annotationMap.addIfNotPresent(annotation) && _isAnnotationBundle(annotation)) {
                    list = _addFromBundle(annotation, list);
                }
            }
            if (list != null) {
                _addAnnotationsIfNotPresent(annotationMap, (Annotation[]) list.toArray(new Annotation[list.size()]));
            }
        }
        return annotationMap;
    }

    private void _addAnnotationsIfNotPresent(AnnotatedMember annotatedMember, Annotation[] annotationArr) {
        while (annotationArr != null) {
            List list = null;
            for (Annotation annotation : annotationArr) {
                if (annotatedMember.addIfNotPresent(annotation) && _isAnnotationBundle(annotation)) {
                    list = _addFromBundle(annotation, list);
                }
            }
            if (list != null) {
                annotationArr = (Annotation[]) list.toArray(new Annotation[list.size()]);
            } else {
                return;
            }
        }
    }

    private List<Annotation> _addFromBundle(Annotation annotation, List<Annotation> list) {
        List<Annotation> list2 = list;
        for (Object obj : ClassUtil.findClassAnnotations(annotation.annotationType())) {
            if (!((obj instanceof Target) || (obj instanceof Retention))) {
                if (list2 == null) {
                    list2 = new ArrayList();
                }
                list2.add(obj);
            }
        }
        return list2;
    }

    private void _addOrOverrideAnnotations(AnnotatedMember annotatedMember, Annotation[] annotationArr) {
        while (annotationArr != null) {
            List list = null;
            for (Annotation annotation : annotationArr) {
                if (annotatedMember.addOrOverride(annotation) && _isAnnotationBundle(annotation)) {
                    list = _addFromBundle(annotation, list);
                }
            }
            if (list != null) {
                annotationArr = (Annotation[]) list.toArray(new Annotation[list.size()]);
            } else {
                return;
            }
        }
    }

    private AnnotationMap _classAnnotations() {
        AnnotationMap annotationMap = this._classAnnotations;
        if (annotationMap == null) {
            synchronized (this) {
                annotationMap = this._classAnnotations;
                if (annotationMap == null) {
                    annotationMap = _resolveClassAnnotations();
                    this._classAnnotations = annotationMap;
                }
            }
        }
        return annotationMap;
    }

    private AnnotationMap _emptyAnnotationMap() {
        return new AnnotationMap();
    }

    private AnnotationMap[] _emptyAnnotationMaps(int i) {
        if (i == 0) {
            return NO_ANNOTATION_MAPS;
        }
        AnnotationMap[] annotationMapArr = new AnnotationMap[i];
        for (int i2 = 0; i2 < i; i2++) {
            annotationMapArr[i2] = _emptyAnnotationMap();
        }
        return annotationMapArr;
    }

    private final boolean _isAnnotationBundle(Annotation annotation) {
        return this._annotationIntrospector != null && this._annotationIntrospector.isAnnotationBundle(annotation);
    }

    private boolean _isIncludableConstructor(Constructor<?> constructor) {
        return !constructor.isSynthetic();
    }

    private boolean _isIncludableField(Field field) {
        return (field.isSynthetic() || Modifier.isStatic(field.getModifiers())) ? false : true;
    }

    private AnnotationMap _resolveClassAnnotations() {
        AnnotationMap annotationMap = new AnnotationMap();
        if (this._annotationIntrospector != null) {
            if (this._primaryMixIn != null) {
                _addClassMixIns(annotationMap, this._class, this._primaryMixIn);
            }
            _addAnnotationsIfNotPresent(annotationMap, ClassUtil.findClassAnnotations(this._class));
            for (JavaType javaType : this._superTypes) {
                _addClassMixIns(annotationMap, javaType);
                _addAnnotationsIfNotPresent(annotationMap, ClassUtil.findClassAnnotations(javaType.getRawClass()));
            }
            _addClassMixIns(annotationMap, Object.class);
        }
        return annotationMap;
    }

    public static AnnotatedClass construct(JavaType javaType, MapperConfig<?> mapperConfig) {
        return new AnnotatedClass(javaType, javaType.getRawClass(), javaType.getBindings(), ClassUtil.findSuperTypes(javaType, null, false), mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null, mapperConfig, mapperConfig.getTypeFactory(), null);
    }

    public static AnnotatedClass construct(JavaType javaType, MapperConfig<?> mapperConfig, MixInResolver mixInResolver) {
        return new AnnotatedClass(javaType, javaType.getRawClass(), javaType.getBindings(), ClassUtil.findSuperTypes(javaType, null, false), mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null, mixInResolver, mapperConfig.getTypeFactory(), null);
    }

    public static AnnotatedClass constructWithoutSuperTypes(Class<?> cls, MapperConfig<?> mapperConfig) {
        if (mapperConfig == null) {
            return new AnnotatedClass(null, cls, TypeBindings.emptyBindings(), Collections.emptyList(), null, null, null, null);
        }
        return new AnnotatedClass(null, cls, TypeBindings.emptyBindings(), Collections.emptyList(), mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null, mapperConfig, mapperConfig.getTypeFactory(), null);
    }

    public static AnnotatedClass constructWithoutSuperTypes(Class<?> cls, MapperConfig<?> mapperConfig, MixInResolver mixInResolver) {
        if (mapperConfig == null) {
            return new AnnotatedClass(null, cls, TypeBindings.emptyBindings(), Collections.emptyList(), null, null, null, null);
        }
        return new AnnotatedClass(null, cls, TypeBindings.emptyBindings(), Collections.emptyList(), mapperConfig.isAnnotationProcessingEnabled() ? mapperConfig.getAnnotationIntrospector() : null, mixInResolver, mapperConfig.getTypeFactory(), null);
    }

    private void resolveCreators() {
        int i;
        int size;
        int i2;
        Ctor[] constructors = ClassUtil.getConstructors(this._class);
        List list = null;
        for (Ctor ctor : constructors) {
            if (_isIncludableConstructor(ctor.getConstructor())) {
                if (ctor.getParamCount() == 0) {
                    this._defaultConstructor = _constructDefaultConstructor(ctor, this);
                } else {
                    if (list == null) {
                        list = new ArrayList(Math.max(10, constructors.length));
                    }
                    list.add(_constructNonDefaultConstructor(ctor, this));
                }
            }
        }
        if (list == null) {
            this._constructors = Collections.emptyList();
        } else {
            this._constructors = list;
        }
        if (!(this._primaryMixIn == null || (this._defaultConstructor == null && this._constructors.isEmpty()))) {
            _addConstructorMixIns(this._primaryMixIn);
        }
        if (this._annotationIntrospector != null) {
            if (this._defaultConstructor != null && this._annotationIntrospector.hasIgnoreMarker(this._defaultConstructor)) {
                this._defaultConstructor = null;
            }
            if (this._constructors != null) {
                size = this._constructors.size();
                while (true) {
                    i = size - 1;
                    if (i < 0) {
                        break;
                    } else if (this._annotationIntrospector.hasIgnoreMarker((AnnotatedMember) this._constructors.get(i))) {
                        this._constructors.remove(i);
                        size = i;
                    } else {
                        size = i;
                    }
                }
            }
        }
        list = null;
        for (Method method : _findClassMethods(this._class)) {
            if (Modifier.isStatic(method.getModifiers())) {
                if (list == null) {
                    list = new ArrayList(8);
                }
                list.add(_constructCreatorMethod(method, this));
            }
        }
        if (list == null) {
            this._creatorMethods = Collections.emptyList();
        } else {
            this._creatorMethods = list;
            if (this._primaryMixIn != null) {
                _addFactoryMixIns(this._primaryMixIn);
            }
            if (this._annotationIntrospector != null) {
                size = this._creatorMethods.size();
                while (true) {
                    i2 = size - 1;
                    if (i2 < 0) {
                        break;
                    } else if (this._annotationIntrospector.hasIgnoreMarker((AnnotatedMember) this._creatorMethods.get(i2))) {
                        this._creatorMethods.remove(i2);
                        size = i2;
                    } else {
                        size = i2;
                    }
                }
            }
        }
        this._creatorsResolved = true;
    }

    private void resolveFields() {
        Map _findFields = _findFields(this._type, this, null);
        if (_findFields == null || _findFields.size() == 0) {
            this._fields = Collections.emptyList();
            return;
        }
        this._fields = new ArrayList(_findFields.size());
        this._fields.addAll(_findFields.values());
    }

    private void resolveMemberMethods() {
        this._memberMethods = new AnnotatedMethodMap();
        AnnotatedMethodMap annotatedMethodMap = new AnnotatedMethodMap();
        _addMemberMethods(this._class, this, this._memberMethods, this._primaryMixIn, annotatedMethodMap);
        for (JavaType javaType : this._superTypes) {
            _addMemberMethods(javaType.getRawClass(), new Basic(this._typeFactory, javaType.getBindings()), this._memberMethods, this._mixInResolver == null ? null : this._mixInResolver.findMixInClassFor(javaType.getRawClass()), annotatedMethodMap);
        }
        if (this._mixInResolver != null) {
            Class findMixInClassFor = this._mixInResolver.findMixInClassFor(Object.class);
            if (findMixInClassFor != null) {
                _addMethodMixIns(this._class, this._memberMethods, findMixInClassFor, annotatedMethodMap);
            }
        }
        if (this._annotationIntrospector != null && !annotatedMethodMap.isEmpty()) {
            Iterator it = annotatedMethodMap.iterator();
            while (it.hasNext()) {
                AnnotatedMethod annotatedMethod = (AnnotatedMethod) it.next();
                try {
                    Method declaredMethod = Object.class.getDeclaredMethod(annotatedMethod.getName(), annotatedMethod.getRawParameterTypes());
                    if (declaredMethod != null) {
                        AnnotatedMethod _constructMethod = _constructMethod(declaredMethod, this);
                        _addMixOvers(annotatedMethod.getAnnotated(), _constructMethod, false);
                        this._memberMethods.add(_constructMethod);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    protected final void _addClassMixIns(AnnotationMap annotationMap, JavaType javaType) {
        if (this._mixInResolver != null) {
            Class rawClass = javaType.getRawClass();
            _addClassMixIns(annotationMap, rawClass, this._mixInResolver.findMixInClassFor(rawClass));
        }
    }

    protected final void _addClassMixIns(AnnotationMap annotationMap, Class<?> cls) {
        if (this._mixInResolver != null) {
            _addClassMixIns(annotationMap, cls, this._mixInResolver.findMixInClassFor(cls));
        }
    }

    protected final void _addClassMixIns(AnnotationMap annotationMap, Class<?> cls, Class<?> cls2) {
        if (cls2 != null) {
            _addAnnotationsIfNotPresent(annotationMap, ClassUtil.findClassAnnotations(cls2));
            for (Class findClassAnnotations : ClassUtil.findSuperClasses(cls2, cls, false)) {
                _addAnnotationsIfNotPresent(annotationMap, ClassUtil.findClassAnnotations(findClassAnnotations));
            }
        }
    }

    protected final void _addConstructorMixIns(Class<?> cls) {
        int size = this._constructors == null ? 0 : this._constructors.size();
        MemberKey[] memberKeyArr = null;
        for (Ctor constructor : ClassUtil.getConstructors(cls)) {
            Constructor constructor2 = constructor.getConstructor();
            if (constructor2.getParameterTypes().length != 0) {
                MemberKey[] memberKeyArr2;
                if (memberKeyArr == null) {
                    memberKeyArr2 = new MemberKey[size];
                    for (int i = 0; i < size; i++) {
                        memberKeyArr2[i] = new MemberKey(((AnnotatedConstructor) this._constructors.get(i)).getAnnotated());
                    }
                } else {
                    memberKeyArr2 = memberKeyArr;
                }
                MemberKey memberKey = new MemberKey(constructor2);
                for (int i2 = 0; i2 < size; i2++) {
                    if (memberKey.equals(memberKeyArr2[i2])) {
                        _addMixOvers(constructor2, (AnnotatedConstructor) this._constructors.get(i2), true);
                        memberKeyArr = memberKeyArr2;
                        break;
                    }
                }
                memberKeyArr = memberKeyArr2;
            } else if (this._defaultConstructor != null) {
                _addMixOvers(constructor2, this._defaultConstructor, false);
            }
        }
    }

    protected final void _addFactoryMixIns(Class<?> cls) {
        MemberKey[] memberKeyArr = null;
        int size = this._creatorMethods.size();
        Method[] declaredMethods = ClassUtil.getDeclaredMethods(cls);
        int length = declaredMethods.length;
        int i = 0;
        while (i < length) {
            MemberKey[] memberKeyArr2;
            Method method = declaredMethods[i];
            if (!Modifier.isStatic(method.getModifiers()) || method.getParameterTypes().length == 0) {
                memberKeyArr2 = memberKeyArr;
            } else {
                if (memberKeyArr == null) {
                    memberKeyArr2 = new MemberKey[size];
                    for (int i2 = 0; i2 < size; i2++) {
                        memberKeyArr2[i2] = new MemberKey(((AnnotatedMethod) this._creatorMethods.get(i2)).getAnnotated());
                    }
                } else {
                    memberKeyArr2 = memberKeyArr;
                }
                MemberKey memberKey = new MemberKey(method);
                for (int i3 = 0; i3 < size; i3++) {
                    if (memberKey.equals(memberKeyArr2[i3])) {
                        _addMixOvers(method, (AnnotatedMethod) this._creatorMethods.get(i3), true);
                        break;
                    }
                }
            }
            i++;
            memberKeyArr = memberKeyArr2;
        }
    }

    protected final void _addFieldMixIns(Class<?> cls, Class<?> cls2, Map<String, AnnotatedField> map) {
        for (Class declaredFields : ClassUtil.findSuperClasses(cls, cls2, true)) {
            for (Field field : ClassUtil.getDeclaredFields(declaredFields)) {
                if (_isIncludableField(field)) {
                    AnnotatedField annotatedField = (AnnotatedField) map.get(field.getName());
                    if (annotatedField != null) {
                        _addOrOverrideAnnotations(annotatedField, field.getDeclaredAnnotations());
                    }
                }
            }
        }
    }

    protected final void _addMemberMethods(Class<?> cls, TypeResolutionContext typeResolutionContext, AnnotatedMethodMap annotatedMethodMap, Class<?> cls2, AnnotatedMethodMap annotatedMethodMap2) {
        if (cls2 != null) {
            _addMethodMixIns(cls, annotatedMethodMap, cls2, annotatedMethodMap2);
        }
        if (cls != null) {
            for (Method method : _findClassMethods(cls)) {
                if (_isIncludableMemberMethod(method)) {
                    AnnotatedMethod find = annotatedMethodMap.find(method);
                    if (find == null) {
                        find = _constructMethod(method, typeResolutionContext);
                        annotatedMethodMap.add(find);
                        AnnotatedMethod remove = annotatedMethodMap2.remove(method);
                        if (remove != null) {
                            _addMixOvers(remove.getAnnotated(), find, false);
                        }
                    } else {
                        _addMixUnders(method, find);
                        if (find.getDeclaringClass().isInterface() && !method.getDeclaringClass().isInterface()) {
                            annotatedMethodMap.add(find.withMethod(method));
                        }
                    }
                }
            }
        }
    }

    protected final void _addMethodMixIns(Class<?> cls, AnnotatedMethodMap annotatedMethodMap, Class<?> cls2, AnnotatedMethodMap annotatedMethodMap2) {
        for (Class declaredMethods : ClassUtil.findRawSuperTypes(cls2, cls, true)) {
            for (Method method : ClassUtil.getDeclaredMethods(declaredMethods)) {
                if (_isIncludableMemberMethod(method)) {
                    AnnotatedMethod find = annotatedMethodMap.find(method);
                    if (find != null) {
                        _addMixUnders(method, find);
                    } else {
                        find = annotatedMethodMap2.find(method);
                        if (find != null) {
                            _addMixUnders(method, find);
                        } else {
                            annotatedMethodMap2.add(_constructMethod(method, this));
                        }
                    }
                }
            }
        }
    }

    protected final void _addMixOvers(Constructor<?> constructor, AnnotatedConstructor annotatedConstructor, boolean z) {
        _addOrOverrideAnnotations(annotatedConstructor, constructor.getDeclaredAnnotations());
        if (z) {
            Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
            int length = parameterAnnotations.length;
            for (int i = 0; i < length; i++) {
                for (Annotation addOrOverrideParam : parameterAnnotations[i]) {
                    annotatedConstructor.addOrOverrideParam(i, addOrOverrideParam);
                }
            }
        }
    }

    protected final void _addMixOvers(Method method, AnnotatedMethod annotatedMethod, boolean z) {
        _addOrOverrideAnnotations(annotatedMethod, method.getDeclaredAnnotations());
        if (z) {
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            int length = parameterAnnotations.length;
            for (int i = 0; i < length; i++) {
                for (Annotation addOrOverrideParam : parameterAnnotations[i]) {
                    annotatedMethod.addOrOverrideParam(i, addOrOverrideParam);
                }
            }
        }
    }

    protected final void _addMixUnders(Method method, AnnotatedMethod annotatedMethod) {
        _addAnnotationsIfNotPresent((AnnotatedMember) annotatedMethod, method.getDeclaredAnnotations());
    }

    protected final AnnotationMap _collectRelevantAnnotations(Annotation[] annotationArr) {
        return _addAnnotationsIfNotPresent(new AnnotationMap(), annotationArr);
    }

    protected final AnnotationMap[] _collectRelevantAnnotations(Annotation[][] annotationArr) {
        int length = annotationArr.length;
        AnnotationMap[] annotationMapArr = new AnnotationMap[length];
        for (int i = 0; i < length; i++) {
            annotationMapArr[i] = _collectRelevantAnnotations(annotationArr[i]);
        }
        return annotationMapArr;
    }

    protected final AnnotatedMethod _constructCreatorMethod(Method method, TypeResolutionContext typeResolutionContext) {
        int length = method.getParameterTypes().length;
        return this._annotationIntrospector == null ? new AnnotatedMethod(typeResolutionContext, method, _emptyAnnotationMap(), _emptyAnnotationMaps(length)) : length == 0 ? new AnnotatedMethod(typeResolutionContext, method, _collectRelevantAnnotations(method.getDeclaredAnnotations()), NO_ANNOTATION_MAPS) : new AnnotatedMethod(typeResolutionContext, method, _collectRelevantAnnotations(method.getDeclaredAnnotations()), _collectRelevantAnnotations(method.getParameterAnnotations()));
    }

    protected final AnnotatedConstructor _constructDefaultConstructor(Ctor ctor, TypeResolutionContext typeResolutionContext) {
        return this._annotationIntrospector == null ? new AnnotatedConstructor(typeResolutionContext, ctor.getConstructor(), _emptyAnnotationMap(), NO_ANNOTATION_MAPS) : new AnnotatedConstructor(typeResolutionContext, ctor.getConstructor(), _collectRelevantAnnotations(ctor.getDeclaredAnnotations()), NO_ANNOTATION_MAPS);
    }

    protected final AnnotatedField _constructField(Field field, TypeResolutionContext typeResolutionContext) {
        return this._annotationIntrospector == null ? new AnnotatedField(typeResolutionContext, field, _emptyAnnotationMap()) : new AnnotatedField(typeResolutionContext, field, _collectRelevantAnnotations(field.getDeclaredAnnotations()));
    }

    protected final AnnotatedMethod _constructMethod(Method method, TypeResolutionContext typeResolutionContext) {
        return this._annotationIntrospector == null ? new AnnotatedMethod(typeResolutionContext, method, _emptyAnnotationMap(), null) : new AnnotatedMethod(typeResolutionContext, method, _collectRelevantAnnotations(method.getDeclaredAnnotations()), null);
    }

    protected final AnnotatedConstructor _constructNonDefaultConstructor(Ctor ctor, TypeResolutionContext typeResolutionContext) {
        int paramCount = ctor.getParamCount();
        if (this._annotationIntrospector == null) {
            return new AnnotatedConstructor(typeResolutionContext, ctor.getConstructor(), _emptyAnnotationMap(), _emptyAnnotationMaps(paramCount));
        }
        if (paramCount == 0) {
            return new AnnotatedConstructor(typeResolutionContext, ctor.getConstructor(), _collectRelevantAnnotations(ctor.getDeclaredAnnotations()), NO_ANNOTATION_MAPS);
        }
        AnnotationMap[] annotationMapArr;
        Annotation[][] parameterAnnotations = ctor.getParameterAnnotations();
        if (paramCount != parameterAnnotations.length) {
            Object obj;
            annotationMapArr = null;
            Class declaringClass = ctor.getDeclaringClass();
            Object obj2;
            Object obj3;
            if (declaringClass.isEnum() && paramCount == parameterAnnotations.length + 2) {
                obj2 = new Annotation[(parameterAnnotations.length + 2)][];
                System.arraycopy(parameterAnnotations, 0, obj2, 2, parameterAnnotations.length);
                obj3 = obj2;
                annotationMapArr = _collectRelevantAnnotations((Annotation[][]) obj2);
                obj = obj3;
            } else if (declaringClass.isMemberClass() && paramCount == parameterAnnotations.length + 1) {
                obj2 = new Annotation[(parameterAnnotations.length + 1)][];
                System.arraycopy(parameterAnnotations, 0, obj2, 1, parameterAnnotations.length);
                obj3 = obj2;
                annotationMapArr = _collectRelevantAnnotations((Annotation[][]) obj2);
                obj = obj3;
            }
            if (annotationMapArr == null) {
                throw new IllegalStateException("Internal error: constructor for " + ctor.getDeclaringClass().getName() + " has mismatch: " + paramCount + " parameters; " + obj.length + " sets of annotations");
            }
        }
        annotationMapArr = _collectRelevantAnnotations(parameterAnnotations);
        return new AnnotatedConstructor(typeResolutionContext, ctor.getConstructor(), _collectRelevantAnnotations(ctor.getDeclaredAnnotations()), annotationMapArr);
    }

    protected final Method[] _findClassMethods(Class<?> cls) {
        try {
            return ClassUtil.getDeclaredMethods(cls);
        } catch (Object e) {
            ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
            if (contextClassLoader == null) {
                throw e;
            }
            try {
                return contextClassLoader.loadClass(cls.getName()).getDeclaredMethods();
            } catch (ClassNotFoundException e2) {
                throw e;
            }
        }
    }

    protected final Map<String, AnnotatedField> _findFields(JavaType javaType, TypeResolutionContext typeResolutionContext, Map<String, AnnotatedField> map) {
        JavaType superClass = javaType.getSuperClass();
        if (superClass == null) {
            return map;
        }
        Class rawClass = javaType.getRawClass();
        Map<String, AnnotatedField> _findFields = _findFields(superClass, new Basic(this._typeFactory, superClass.getBindings()), map);
        Map<String, AnnotatedField> map2 = _findFields;
        for (Field field : ClassUtil.getDeclaredFields(rawClass)) {
            if (_isIncludableField(field)) {
                if (map2 == null) {
                    map2 = new LinkedHashMap();
                }
                map2.put(field.getName(), _constructField(field, typeResolutionContext));
            }
        }
        if (this._mixInResolver == null) {
            return map2;
        }
        Class findMixInClassFor = this._mixInResolver.findMixInClassFor(rawClass);
        if (findMixInClassFor == null) {
            return map2;
        }
        _addFieldMixIns(findMixInClassFor, rawClass, map2);
        return map2;
    }

    protected final boolean _isIncludableMemberMethod(Method method) {
        return (Modifier.isStatic(method.getModifiers()) || method.isSynthetic() || method.isBridge() || method.getParameterTypes().length > 2) ? false : true;
    }

    public final Iterable<Annotation> annotations() {
        return _classAnnotations().annotations();
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (obj == null || obj.getClass() != getClass()) {
                return false;
            }
            if (((AnnotatedClass) obj)._class != this._class) {
                return false;
            }
        }
        return true;
    }

    public final Iterable<AnnotatedField> fields() {
        if (this._fields == null) {
            resolveFields();
        }
        return this._fields;
    }

    public final AnnotatedMethod findMethod(String str, Class<?>[] clsArr) {
        if (this._memberMethods == null) {
            resolveMemberMethods();
        }
        return this._memberMethods.find(str, clsArr);
    }

    protected final AnnotationMap getAllAnnotations() {
        return _classAnnotations();
    }

    public final Class<?> getAnnotated() {
        return this._class;
    }

    public final <A extends Annotation> A getAnnotation(Class<A> cls) {
        return _classAnnotations().get(cls);
    }

    public final Annotations getAnnotations() {
        return _classAnnotations();
    }

    public final List<AnnotatedConstructor> getConstructors() {
        if (!this._creatorsResolved) {
            resolveCreators();
        }
        return this._constructors;
    }

    public final AnnotatedConstructor getDefaultConstructor() {
        if (!this._creatorsResolved) {
            resolveCreators();
        }
        return this._defaultConstructor;
    }

    public final int getFieldCount() {
        if (this._fields == null) {
            resolveFields();
        }
        return this._fields.size();
    }

    public final int getMemberMethodCount() {
        if (this._memberMethods == null) {
            resolveMemberMethods();
        }
        return this._memberMethods.size();
    }

    public final int getModifiers() {
        return this._class.getModifiers();
    }

    public final String getName() {
        return this._class.getName();
    }

    public final Class<?> getRawType() {
        return this._class;
    }

    public final List<AnnotatedMethod> getStaticMethods() {
        if (!this._creatorsResolved) {
            resolveCreators();
        }
        return this._creatorMethods;
    }

    public final JavaType getType() {
        return this._type;
    }

    public final boolean hasAnnotation(Class<?> cls) {
        return _classAnnotations().has(cls);
    }

    public final boolean hasAnnotations() {
        return _classAnnotations().size() > 0;
    }

    public final boolean hasOneOf(Class<? extends Annotation>[] clsArr) {
        return _classAnnotations().hasOneOf(clsArr);
    }

    public final int hashCode() {
        return this._class.getName().hashCode();
    }

    public final Iterable<AnnotatedMethod> memberMethods() {
        if (this._memberMethods == null) {
            resolveMemberMethods();
        }
        return this._memberMethods;
    }

    public final JavaType resolveType(Type type) {
        return this._typeFactory.constructType(type, this._bindings);
    }

    public final String toString() {
        return "[AnnotedClass " + this._class.getName() + "]";
    }

    public final AnnotatedClass withAnnotations(AnnotationMap annotationMap) {
        return new AnnotatedClass(this._type, this._class, this._bindings, this._superTypes, this._annotationIntrospector, this._mixInResolver, this._typeFactory, annotationMap);
    }
}
