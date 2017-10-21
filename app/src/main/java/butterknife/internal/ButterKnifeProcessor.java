package butterknife.internal;

import butterknife.Bind;
import butterknife.BindBool;
import butterknife.BindColor;
import butterknife.BindDimen;
import butterknife.BindDrawable;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.OnItemSelected;
import butterknife.OnLongClick;
import butterknife.OnPageChange;
import butterknife.OnTextChanged;
import butterknife.OnTouch;
import butterknife.internal.ListenerClass.NONE;
import com.ad4screen.sdk.analytics.Lead;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

public final class ButterKnifeProcessor extends AbstractProcessor {
    static final /* synthetic */ boolean $assertionsDisabled = (!ButterKnifeProcessor.class.desiredAssertionStatus());
    public static final String ANDROID_PREFIX = "android.";
    private static final String COLOR_STATE_LIST_TYPE = "android.content.res.ColorStateList";
    private static final String DRAWABLE_TYPE = "android.graphics.drawable.Drawable";
    private static final String ITERABLE_TYPE = "java.lang.Iterable<?>";
    public static final String JAVA_PREFIX = "java.";
    private static final List<Class<? extends Annotation>> LISTENERS = Arrays.asList(new Class[]{OnCheckedChanged.class, OnClick.class, OnEditorAction.class, OnFocusChange.class, OnItemClick.class, OnItemLongClick.class, OnItemSelected.class, OnLongClick.class, OnPageChange.class, OnTextChanged.class, OnTouch.class});
    private static final String LIST_TYPE = List.class.getCanonicalName();
    private static final String NULLABLE_ANNOTATION_NAME = "Nullable";
    public static final String SUFFIX = "$$ViewBinder";
    static final String VIEW_TYPE = "android.view.View";
    private Elements elementUtils;
    private Filer filer;
    private Types typeUtils;

    private String doubleErasure(TypeMirror typeMirror) {
        String typeMirror2 = this.typeUtils.erasure(typeMirror).toString();
        int indexOf = typeMirror2.indexOf(60);
        return indexOf != -1 ? typeMirror2.substring(0, indexOf) : typeMirror2;
    }

    private void error(Element element, String str, Object... objArr) {
        CharSequence format;
        if (objArr.length > 0) {
            format = String.format(str, objArr);
        }
        this.processingEnv.getMessager().printMessage(Kind.ERROR, format, element);
    }

    private void findAndParseListener(RoundEnvironment roundEnvironment, Class<? extends Annotation> cls, Map<TypeElement, BindingClass> map, Set<String> set) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(cls)) {
            try {
                parseListenerAnnotation(cls, element, map, set);
            } catch (Exception e) {
                e.printStackTrace(new PrintWriter(new StringWriter()));
                error(element, "Unable to generate view binder for @%s.\n\n%s", cls.getSimpleName(), r3.toString());
            }
        }
    }

    private Map<TypeElement, BindingClass> findAndParseTargets(RoundEnvironment roundEnvironment) {
        Map<TypeElement, BindingClass> linkedHashMap = new LinkedHashMap();
        Set linkedHashSet = new LinkedHashSet();
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Bind.class)) {
            try {
                parseBind(element, linkedHashMap, linkedHashSet);
            } catch (Exception e) {
                logParsingError(element, Bind.class, e);
            }
        }
        for (Class findAndParseListener : LISTENERS) {
            findAndParseListener(roundEnvironment, findAndParseListener, linkedHashMap, linkedHashSet);
        }
        for (Element element2 : roundEnvironment.getElementsAnnotatedWith(BindBool.class)) {
            try {
                parseResourceBool(element2, linkedHashMap, linkedHashSet);
            } catch (Exception e2) {
                logParsingError(element2, BindBool.class, e2);
            }
        }
        for (Element element22 : roundEnvironment.getElementsAnnotatedWith(BindColor.class)) {
            try {
                parseResourceColor(element22, linkedHashMap, linkedHashSet);
            } catch (Exception e22) {
                logParsingError(element22, BindColor.class, e22);
            }
        }
        for (Element element222 : roundEnvironment.getElementsAnnotatedWith(BindDimen.class)) {
            try {
                parseResourceDimen(element222, linkedHashMap, linkedHashSet);
            } catch (Exception e222) {
                logParsingError(element222, BindDimen.class, e222);
            }
        }
        for (Element element2222 : roundEnvironment.getElementsAnnotatedWith(BindDrawable.class)) {
            try {
                parseResourceDrawable(element2222, linkedHashMap, linkedHashSet);
            } catch (Exception e2222) {
                logParsingError(element2222, BindDrawable.class, e2222);
            }
        }
        for (Element element22222 : roundEnvironment.getElementsAnnotatedWith(BindInt.class)) {
            try {
                parseResourceInt(element22222, linkedHashMap, linkedHashSet);
            } catch (Exception e22222) {
                logParsingError(element22222, BindInt.class, e22222);
            }
        }
        for (Element element222222 : roundEnvironment.getElementsAnnotatedWith(BindString.class)) {
            try {
                parseResourceString(element222222, linkedHashMap, linkedHashSet);
            } catch (Exception e222222) {
                logParsingError(element222222, BindString.class, e222222);
            }
        }
        for (Entry entry : linkedHashMap.entrySet()) {
            String findParentFqcn = findParentFqcn((TypeElement) entry.getKey(), linkedHashSet);
            if (findParentFqcn != null) {
                ((BindingClass) entry.getValue()).setParentViewBinder(findParentFqcn + SUFFIX);
            }
        }
        return linkedHashMap;
    }

    private static Integer findDuplicate(int[] iArr) {
        Set linkedHashSet = new LinkedHashSet();
        for (int i : iArr) {
            if (!linkedHashSet.add(Integer.valueOf(i))) {
                return Integer.valueOf(i);
            }
        }
        return null;
    }

    private String findParentFqcn(TypeElement typeElement, Set<String> set) {
        while (true) {
            TypeMirror superclass = typeElement.getSuperclass();
            if (superclass.getKind() == TypeKind.NONE) {
                return null;
            }
            TypeElement typeElement2 = (TypeElement) ((DeclaredType) superclass).asElement();
            if (set.contains(typeElement2.toString())) {
                String packageName = getPackageName(typeElement2);
                return packageName + "." + getClassName(typeElement2, packageName);
            }
            typeElement = typeElement2;
        }
    }

    private static String getClassName(TypeElement typeElement, String str) {
        return typeElement.getQualifiedName().toString().substring(str.length() + 1).replace('.', '$');
    }

    private BindingClass getOrCreateTargetClass(Map<TypeElement, BindingClass> map, TypeElement typeElement) {
        BindingClass bindingClass = (BindingClass) map.get(typeElement);
        if (bindingClass != null) {
            return bindingClass;
        }
        String obj = typeElement.getQualifiedName().toString();
        String packageName = getPackageName(typeElement);
        bindingClass = new BindingClass(packageName, getClassName(typeElement, packageName) + SUFFIX, obj);
        map.put(typeElement, bindingClass);
        return bindingClass;
    }

    private String getPackageName(TypeElement typeElement) {
        return this.elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
    }

    private static boolean hasAnnotationWithName(Element element, String str) {
        for (AnnotationMirror annotationType : element.getAnnotationMirrors()) {
            if (str.equals(annotationType.getAnnotationType().asElement().getSimpleName().toString())) {
                return true;
            }
        }
        return false;
    }

    private boolean isBindingInWrongPackage(Class<? extends Annotation> cls, Element element) {
        String obj = ((TypeElement) element.getEnclosingElement()).getQualifiedName().toString();
        if (obj.startsWith(ANDROID_PREFIX)) {
            error(element, "@%s-annotated class incorrectly in Android framework package. (%s)", cls.getSimpleName(), obj);
            return true;
        } else if (!obj.startsWith(JAVA_PREFIX)) {
            return false;
        } else {
            error(element, "@%s-annotated class incorrectly in Java framework package. (%s)", cls.getSimpleName(), obj);
            return true;
        }
    }

    private boolean isInaccessibleViaGeneratedCode(Class<? extends Annotation> cls, String str, Element element) {
        boolean z;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        Set modifiers = element.getModifiers();
        if (modifiers.contains(Modifier.PRIVATE) || modifiers.contains(Modifier.STATIC)) {
            error(element, "@%s %s must not be private or static. (%s.%s)", cls.getSimpleName(), str, typeElement.getQualifiedName(), element.getSimpleName());
            z = true;
        } else {
            z = false;
        }
        if (typeElement.getKind() != ElementKind.CLASS) {
            error(typeElement, "@%s %s may only be contained in classes. (%s.%s)", cls.getSimpleName(), str, typeElement.getQualifiedName(), element.getSimpleName());
            z = true;
        }
        if (!typeElement.getModifiers().contains(Modifier.PRIVATE)) {
            return z;
        }
        error(typeElement, "@%s %s may not be contained in private classes. (%s.%s)", cls.getSimpleName(), str, typeElement.getQualifiedName(), element.getSimpleName());
        return true;
    }

    private boolean isInterface(TypeMirror typeMirror) {
        return (typeMirror instanceof DeclaredType) && ((DeclaredType) typeMirror).asElement().getKind() == ElementKind.INTERFACE;
    }

    private static boolean isRequiredBinding(Element element) {
        return !hasAnnotationWithName(element, NULLABLE_ANNOTATION_NAME);
    }

    private boolean isSubtypeOfType(TypeMirror typeMirror, String str) {
        boolean z = false;
        if (str.equals(typeMirror.toString())) {
            z = true;
        } else if (typeMirror.getKind() == TypeKind.DECLARED) {
            DeclaredType declaredType = (DeclaredType) typeMirror;
            List typeArguments = declaredType.getTypeArguments();
            if (typeArguments.size() > 0) {
                StringBuilder stringBuilder = new StringBuilder(declaredType.asElement().toString());
                stringBuilder.append('<');
                for (int i = 0; i < typeArguments.size(); i++) {
                    if (i > 0) {
                        stringBuilder.append(',');
                    }
                    stringBuilder.append('?');
                }
                stringBuilder.append('>');
                if (stringBuilder.toString().equals(str)) {
                    return true;
                }
            }
            Element asElement = declaredType.asElement();
            if (asElement instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) asElement;
                if (isSubtypeOfType(typeElement.getSuperclass(), str)) {
                    return true;
                }
                for (TypeMirror isSubtypeOfType : typeElement.getInterfaces()) {
                    if (isSubtypeOfType(isSubtypeOfType, str)) {
                        return true;
                    }
                }
            }
        }
        return z;
    }

    private void logParsingError(Element element, Class<? extends Annotation> cls, Exception exception) {
        exception.printStackTrace(new PrintWriter(new StringWriter()));
        error(element, "Unable to parse @%s binding.\n\n%s", cls.getSimpleName(), r0);
    }

    private void parseBind(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        if (!isInaccessibleViaGeneratedCode(Bind.class, "fields", element) && !isBindingInWrongPackage(Bind.class, element)) {
            TypeMirror asType = element.asType();
            if (asType.getKind() == TypeKind.ARRAY) {
                parseBindMany(element, map, set);
            } else if (LIST_TYPE.equals(doubleErasure(asType))) {
                parseBindMany(element, map, set);
            } else if (isSubtypeOfType(asType, ITERABLE_TYPE)) {
                error(element, "@%s must be a List or array. (%s.%s)", Bind.class.getSimpleName(), ((TypeElement) element.getEnclosingElement()).getQualifiedName(), element.getSimpleName());
            } else {
                parseBindOne(element, map, set);
            }
        }
    }

    private void parseBindMany(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        Kind kind;
        Object obj;
        Object obj2;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        TypeMirror asType = element.asType();
        String doubleErasure = doubleErasure(asType);
        if (asType.getKind() == TypeKind.ARRAY) {
            TypeMirror componentType = ((ArrayType) asType).getComponentType();
            kind = Kind.ARRAY;
            asType = componentType;
            obj = null;
        } else if (LIST_TYPE.equals(doubleErasure)) {
            List typeArguments = ((DeclaredType) asType).getTypeArguments();
            if (typeArguments.size() != 1) {
                error(element, "@%s List must have a generic component. (%s.%s)", Bind.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
                obj = 1;
                asType = null;
            } else {
                asType = (TypeMirror) typeArguments.get(0);
                obj = null;
            }
            kind = Kind.LIST;
        } else {
            throw new AssertionError();
        }
        TypeMirror upperBound = (asType == null || asType.getKind() != TypeKind.TYPEVAR) ? asType : ((TypeVariable) asType).getUpperBound();
        if (upperBound == null || isSubtypeOfType(upperBound, VIEW_TYPE) || isInterface(upperBound)) {
            obj2 = obj;
        } else {
            error(element, "@%s List or array type must extend from View or be an interface. (%s.%s)", Bind.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
            obj2 = 1;
        }
        if (obj2 == null) {
            doubleErasure = element.getSimpleName().toString();
            int[] value = ((Bind) element.getAnnotation(Bind.class)).value();
            if (value.length == 0) {
                error(element, "@%s must specify at least one ID. (%s.%s)", Bind.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
                return;
            }
            if (findDuplicate(value) != null) {
                error(element, "@%s annotation contains duplicate ID %d. (%s.%s)", Bind.class.getSimpleName(), findDuplicate(value), typeElement.getQualifiedName(), element.getSimpleName());
            }
            if ($assertionsDisabled || upperBound != null) {
                getOrCreateTargetClass(map, typeElement).addFieldCollection(value, new FieldCollectionViewBinding(doubleErasure, upperBound.toString(), kind, isRequiredBinding(element)));
                set.add(typeElement.toString());
                return;
            }
            throw new AssertionError();
        }
    }

    private void parseBindOne(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        TypeMirror asType = element.asType();
        TypeMirror upperBound = asType.getKind() == TypeKind.TYPEVAR ? ((TypeVariable) asType).getUpperBound() : asType;
        if (isSubtypeOfType(upperBound, VIEW_TYPE) || isInterface(upperBound)) {
            i = 0;
        } else {
            error(element, "@%s fields must extend from View or be an interface. (%s.%s)", Bind.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
            i = 1;
        }
        int[] value = ((Bind) element.getAnnotation(Bind.class)).value();
        if (value.length != 1) {
            error(element, "@%s for a view must only specify one ID. Found: %s. (%s.%s)", Bind.class.getSimpleName(), Arrays.toString(value), typeElement.getQualifiedName(), element.getSimpleName());
            i = 1;
        }
        if (i == 0) {
            i = value[0];
            BindingClass bindingClass = (BindingClass) map.get(typeElement);
            if (bindingClass != null) {
                ViewBindings viewBinding = bindingClass.getViewBinding(i);
                if (viewBinding != null) {
                    Iterator it = viewBinding.getFieldBindings().iterator();
                    if (it.hasNext()) {
                        FieldViewBinding fieldViewBinding = (FieldViewBinding) it.next();
                        error(element, "Attempt to use @%s for an already bound ID %d on '%s'. (%s.%s)", Bind.class.getSimpleName(), Integer.valueOf(i), fieldViewBinding.getName(), typeElement.getQualifiedName(), element.getSimpleName());
                        return;
                    }
                }
            }
            bindingClass = getOrCreateTargetClass(map, typeElement);
            bindingClass.addField(i, new FieldViewBinding(element.getSimpleName().toString(), upperBound.toString(), isRequiredBinding(element)));
            set.add(typeElement.toString());
        }
    }

    private void parseListenerAnnotation(Class<? extends Annotation> cls, Element element, Map<TypeElement, BindingClass> map, Set<String> set) throws Exception {
        if ((element instanceof ExecutableElement) && element.getKind() == ElementKind.METHOD) {
            ExecutableElement executableElement = (ExecutableElement) element;
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            Annotation annotation = element.getAnnotation(cls);
            Method declaredMethod = cls.getDeclaredMethod(Lead.KEY_VALUE, new Class[0]);
            if (declaredMethod.getReturnType() != int[].class) {
                throw new IllegalStateException(String.format("@%s annotation value() type not int[].", new Object[]{cls}));
            }
            int i;
            int[] iArr = (int[]) declaredMethod.invoke(annotation, new Object[0]);
            String obj = executableElement.getSimpleName().toString();
            boolean isRequiredBinding = isRequiredBinding(element);
            boolean isInaccessibleViaGeneratedCode = isInaccessibleViaGeneratedCode(cls, "methods", element);
            boolean isBindingInWrongPackage = isBindingInWrongPackage(cls, element);
            if (findDuplicate(iArr) != null) {
                error(element, "@%s annotation for method contains duplicate ID %d. (%s.%s)", cls.getSimpleName(), findDuplicate(iArr), typeElement.getQualifiedName(), element.getSimpleName());
                i = 1;
            } else {
                i = isInaccessibleViaGeneratedCode | isBindingInWrongPackage;
            }
            ListenerClass listenerClass = (ListenerClass) cls.getAnnotation(ListenerClass.class);
            if (listenerClass == null) {
                throw new IllegalStateException(String.format("No @%s defined on @%s.", new Object[]{ListenerClass.class.getSimpleName(), cls.getSimpleName()}));
            }
            int length = iArr.length;
            int i2 = 0;
            int i3 = i;
            while (i2 < length) {
                if (iArr[i2] != -1) {
                    i = i3;
                } else if (iArr.length == 1) {
                    if (isRequiredBinding) {
                        i = i3;
                    } else {
                        error(element, "ID-free binding must not be annotated with @Nullable. (%s.%s)", typeElement.getQualifiedName(), element.getSimpleName());
                        i = 1;
                    }
                    if (!(isSubtypeOfType(typeElement.asType(), listenerClass.targetType()) || isInterface(typeElement.asType()))) {
                        error(element, "@%s annotation without an ID may only be used with an object of type \"%s\" or an interface. (%s.%s)", cls.getSimpleName(), r9, typeElement.getQualifiedName(), element.getSimpleName());
                        i = 1;
                    }
                } else {
                    error(element, "@%s annotation contains invalid ID %d. (%s.%s)", cls.getSimpleName(), Integer.valueOf(i), typeElement.getQualifiedName(), element.getSimpleName());
                    i = 1;
                }
                i2++;
                i3 = i;
            }
            ListenerMethod[] method = listenerClass.method();
            if (method.length > 1) {
                throw new IllegalStateException(String.format("Multiple listener methods specified on @%s.", new Object[]{cls.getSimpleName()}));
            }
            ListenerMethod listenerMethod;
            if (method.length != 1) {
                Enum enumR = (Enum) cls.getDeclaredMethod("callback", new Class[0]).invoke(annotation, new Object[0]);
                listenerMethod = (ListenerMethod) enumR.getDeclaringClass().getField(enumR.name()).getAnnotation(ListenerMethod.class);
                if (listenerMethod == null) {
                    throw new IllegalStateException(String.format("No @%s defined on @%s's %s.%s.", new Object[]{ListenerMethod.class.getSimpleName(), cls.getSimpleName(), enumR.getDeclaringClass().getSimpleName(), enumR.name()}));
                }
            } else if (listenerClass.callbacks() != NONE.class) {
                throw new IllegalStateException(String.format("Both method() and callback() defined on @%s.", new Object[]{cls.getSimpleName()}));
            } else {
                listenerMethod = method[0];
            }
            List parameters = executableElement.getParameters();
            if (parameters.size() > listenerMethod.parameters().length) {
                error(element, "@%s methods can have at most %s parameter(s). (%s.%s)", cls.getSimpleName(), Integer.valueOf(listenerMethod.parameters().length), typeElement.getQualifiedName(), element.getSimpleName());
                i3 = 1;
            }
            TypeMirror returnType = executableElement.getReturnType();
            if (returnType instanceof TypeVariable) {
                returnType = ((TypeVariable) returnType).getUpperBound();
            }
            if (!returnType.toString().equals(listenerMethod.returnType())) {
                error(element, "@%s methods must have a '%s' return type. (%s.%s)", cls.getSimpleName(), listenerMethod.returnType(), typeElement.getQualifiedName(), element.getSimpleName());
                i3 = 1;
            }
            if (i3 == 0) {
                int i4;
                Object[] objArr;
                Parameter[] parameterArr = Parameter.NONE;
                if (parameters.isEmpty()) {
                    Parameter[] parameterArr2 = parameterArr;
                } else {
                    Parameter[] parameterArr3 = new Parameter[parameters.size()];
                    BitSet bitSet = new BitSet(parameters.size());
                    String[] parameters2 = listenerMethod.parameters();
                    for (i4 = 0; i4 < parameters.size(); i4++) {
                        returnType = ((VariableElement) parameters.get(i4)).asType();
                        if (returnType instanceof TypeVariable) {
                            returnType = ((TypeVariable) returnType).getUpperBound();
                        }
                        i3 = 0;
                        while (i3 < parameters2.length) {
                            if (!bitSet.get(i3) && (isSubtypeOfType(returnType, parameters2[i3]) || isInterface(returnType))) {
                                parameterArr3[i4] = new Parameter(i3, returnType.toString());
                                bitSet.set(i3);
                                break;
                            }
                            i3++;
                        }
                        if (parameterArr3[i4] == null) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unable to match @").append(cls.getSimpleName()).append(" method arguments. (").append(typeElement.getQualifiedName()).append('.').append(element.getSimpleName()).append(')');
                            for (int i5 = 0; i5 < parameterArr3.length; i5++) {
                                Parameter parameter = parameterArr3[i5];
                                stringBuilder.append("\n\n  Parameter #").append(i5 + 1).append(": ").append(((VariableElement) parameters.get(i5)).asType().toString()).append("\n    ");
                                if (parameter == null) {
                                    stringBuilder.append("did not match any listener parameters");
                                } else {
                                    stringBuilder.append("matched listener parameter #").append(parameter.getListenerPosition() + 1).append(": ").append(parameter.getType());
                                }
                            }
                            stringBuilder.append("\n\nMethods may have up to ").append(listenerMethod.parameters().length).append(" parameter(s):\n");
                            for (String append : listenerMethod.parameters()) {
                                stringBuilder.append("\n  ").append(append);
                            }
                            stringBuilder.append("\n\nThese may be listed in any order but will be searched for from top to bottom.");
                            error(executableElement, stringBuilder.toString(), new Object[0]);
                            return;
                        }
                    }
                    objArr = parameterArr3;
                }
                MethodViewBinding methodViewBinding = new MethodViewBinding(obj, Arrays.asList(objArr), isRequiredBinding);
                BindingClass orCreateTargetClass = getOrCreateTargetClass(map, typeElement);
                i4 = iArr.length;
                int i6 = 0;
                while (i6 < i4) {
                    if (orCreateTargetClass.addMethod(iArr[i6], listenerClass, listenerMethod, methodViewBinding)) {
                        i6++;
                    } else {
                        error(element, "Multiple listener methods with return value specified for ID %d. (%s.%s)", Integer.valueOf(iArr[i6]), typeElement.getQualifiedName(), element.getSimpleName());
                        return;
                    }
                }
                set.add(typeElement.toString());
                return;
            }
            return;
        }
        throw new IllegalStateException(String.format("@%s annotation must be on a method.", new Object[]{cls.getSimpleName()}));
    }

    private void parseResourceBool(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i = 1;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        if (element.asType().getKind() != TypeKind.BOOLEAN) {
            error(element, "@%s field type must be 'boolean'. (%s.%s)", BindBool.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
        } else {
            i = 0;
        }
        if (((i | isInaccessibleViaGeneratedCode(BindBool.class, "fields", element)) | isBindingInWrongPackage(BindBool.class, element)) == 0) {
            String obj = element.getSimpleName().toString();
            getOrCreateTargetClass(map, typeElement).addResource(new FieldResourceBinding(((BindBool) element.getAnnotation(BindBool.class)).value(), obj, "getBoolean"));
            set.add(typeElement.toString());
        }
    }

    private void parseResourceColor(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i = 1;
        int i2 = 0;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        TypeMirror asType = element.asType();
        if (COLOR_STATE_LIST_TYPE.equals(asType.toString())) {
            i = 0;
            i2 = 1;
        } else if (asType.getKind() != TypeKind.INT) {
            error(element, "@%s field type must be 'int' or 'ColorStateList'. (%s.%s)", BindColor.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
        } else {
            i = 0;
        }
        if (((i | isInaccessibleViaGeneratedCode(BindColor.class, "fields", element)) | isBindingInWrongPackage(BindColor.class, element)) == 0) {
            String obj = element.getSimpleName().toString();
            getOrCreateTargetClass(map, typeElement).addResource(new FieldResourceBinding(((BindColor) element.getAnnotation(BindColor.class)).value(), obj, i2 != 0 ? "getColorStateList" : "getColor"));
            set.add(typeElement.toString());
        }
    }

    private void parseResourceDimen(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i = 1;
        int i2 = 0;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        TypeMirror asType = element.asType();
        if (asType.getKind() == TypeKind.INT) {
            i = 0;
            i2 = 1;
        } else if (asType.getKind() != TypeKind.FLOAT) {
            error(element, "@%s field type must be 'int' or 'float'. (%s.%s)", BindDimen.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
        } else {
            i = 0;
        }
        if (((i | isInaccessibleViaGeneratedCode(BindDimen.class, "fields", element)) | isBindingInWrongPackage(BindDimen.class, element)) == 0) {
            String obj = element.getSimpleName().toString();
            getOrCreateTargetClass(map, typeElement).addResource(new FieldResourceBinding(((BindDimen) element.getAnnotation(BindDimen.class)).value(), obj, i2 != 0 ? "getDimensionPixelSize" : "getDimension"));
            set.add(typeElement.toString());
        }
    }

    private void parseResourceDrawable(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i = 1;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        if (DRAWABLE_TYPE.equals(element.asType().toString())) {
            i = 0;
        } else {
            error(element, "@%s field type must be 'Drawable'. (%s.%s)", BindDrawable.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
        }
        if (((i | isInaccessibleViaGeneratedCode(BindDrawable.class, "fields", element)) | isBindingInWrongPackage(BindDrawable.class, element)) == 0) {
            String obj = element.getSimpleName().toString();
            getOrCreateTargetClass(map, typeElement).addResource(new FieldResourceBinding(((BindDrawable) element.getAnnotation(BindDrawable.class)).value(), obj, "getDrawable"));
            set.add(typeElement.toString());
        }
    }

    private void parseResourceInt(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i = 1;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        if (element.asType().getKind() != TypeKind.INT) {
            error(element, "@%s field type must be 'int'. (%s.%s)", BindInt.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
        } else {
            i = 0;
        }
        if (((i | isInaccessibleViaGeneratedCode(BindInt.class, "fields", element)) | isBindingInWrongPackage(BindInt.class, element)) == 0) {
            String obj = element.getSimpleName().toString();
            getOrCreateTargetClass(map, typeElement).addResource(new FieldResourceBinding(((BindInt) element.getAnnotation(BindInt.class)).value(), obj, "getInteger"));
            set.add(typeElement.toString());
        }
    }

    private void parseResourceString(Element element, Map<TypeElement, BindingClass> map, Set<String> set) {
        int i = 1;
        TypeElement typeElement = (TypeElement) element.getEnclosingElement();
        if ("java.lang.String".equals(element.asType().toString())) {
            i = 0;
        } else {
            error(element, "@%s field type must be 'String'. (%s.%s)", BindString.class.getSimpleName(), typeElement.getQualifiedName(), element.getSimpleName());
        }
        if (((i | isInaccessibleViaGeneratedCode(BindString.class, "fields", element)) | isBindingInWrongPackage(BindString.class, element)) == 0) {
            String obj = element.getSimpleName().toString();
            getOrCreateTargetClass(map, typeElement).addResource(new FieldResourceBinding(((BindString) element.getAnnotation(BindString.class)).value(), obj, "getString"));
            set.add(typeElement.toString());
        }
    }

    public final Set<String> getSupportedAnnotationTypes() {
        Set<String> linkedHashSet = new LinkedHashSet();
        linkedHashSet.add(Bind.class.getCanonicalName());
        for (Class canonicalName : LISTENERS) {
            linkedHashSet.add(canonicalName.getCanonicalName());
        }
        linkedHashSet.add(BindBool.class.getCanonicalName());
        linkedHashSet.add(BindColor.class.getCanonicalName());
        linkedHashSet.add(BindDimen.class.getCanonicalName());
        linkedHashSet.add(BindDrawable.class.getCanonicalName());
        linkedHashSet.add(BindInt.class.getCanonicalName());
        linkedHashSet.add(BindString.class.getCanonicalName());
        return linkedHashSet;
    }

    public final SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    public final void init(ProcessingEnvironment processingEnvironment) {
        synchronized (this) {
            super.init(processingEnvironment);
            this.elementUtils = processingEnvironment.getElementUtils();
            this.typeUtils = processingEnvironment.getTypeUtils();
            this.filer = processingEnvironment.getFiler();
        }
    }

    public final boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (Entry entry : findAndParseTargets(roundEnvironment).entrySet()) {
            TypeElement typeElement = (TypeElement) entry.getKey();
            BindingClass bindingClass = (BindingClass) entry.getValue();
            try {
                Writer openWriter = this.filer.createSourceFile(bindingClass.getFqcn(), new Element[]{typeElement}).openWriter();
                openWriter.write(bindingClass.brewJava());
                openWriter.flush();
                openWriter.close();
            } catch (IOException e) {
                error(typeElement, "Unable to write view binder for type %s: %s", typeElement, e.getMessage());
            }
        }
        return true;
    }
}
