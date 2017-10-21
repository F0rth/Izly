package butterknife.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

final class BindingClass {
    private final String className;
    private final String classPackage;
    private final Map<FieldCollectionViewBinding, int[]> collectionBindings = new LinkedHashMap();
    private String parentViewBinder;
    private final List<FieldResourceBinding> resourceBindings = new ArrayList();
    private final String targetClass;
    private final Map<Integer, ViewBindings> viewIdMap = new LinkedHashMap();

    BindingClass(String str, String str2, String str3) {
        this.classPackage = str;
        this.className = str2;
        this.targetClass = str3;
    }

    private void emitBindMethod(StringBuilder stringBuilder) {
        stringBuilder.append("  @Override public void bind(final Finder finder, final T target, Object source) {\n");
        if (this.parentViewBinder != null) {
            stringBuilder.append("    super.bind(finder, target, source);\n\n");
        }
        if (!(this.viewIdMap.isEmpty() && this.collectionBindings.isEmpty())) {
            stringBuilder.append("    View view;\n");
            for (ViewBindings emitViewBindings : this.viewIdMap.values()) {
                emitViewBindings(stringBuilder, emitViewBindings);
            }
            for (Entry entry : this.collectionBindings.entrySet()) {
                emitCollectionBinding(stringBuilder, (FieldCollectionViewBinding) entry.getKey(), (int[]) entry.getValue());
            }
        }
        if (!this.resourceBindings.isEmpty()) {
            stringBuilder.append("    Resources res = finder.getContext(source).getResources();\n");
            for (FieldResourceBinding fieldResourceBinding : this.resourceBindings) {
                stringBuilder.append("    target.").append(fieldResourceBinding.getName()).append(" = res.").append(fieldResourceBinding.getMethod()).append('(').append(fieldResourceBinding.getId()).append(");\n");
            }
        }
        stringBuilder.append("  }\n");
    }

    private void emitCollectionBinding(StringBuilder stringBuilder, FieldCollectionViewBinding fieldCollectionViewBinding, int[] iArr) {
        stringBuilder.append("    target.").append(fieldCollectionViewBinding.getName()).append(" = ");
        switch (fieldCollectionViewBinding.getKind()) {
            case ARRAY:
                stringBuilder.append("Finder.arrayOf(");
                break;
            case LIST:
                stringBuilder.append("Finder.listOf(");
                break;
            default:
                throw new IllegalStateException("Unknown kind: " + fieldCollectionViewBinding.getKind());
        }
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                stringBuilder.append(',');
            }
            stringBuilder.append("\n        finder.<").append(fieldCollectionViewBinding.getType()).append(">").append(fieldCollectionViewBinding.isRequired() ? "findRequiredView" : "findOptionalView").append("(source, ").append(iArr[i]).append(", \"");
            emitHumanDescription(stringBuilder, Collections.singleton(fieldCollectionViewBinding));
            stringBuilder.append("\")");
        }
        stringBuilder.append("\n    );\n");
    }

    private void emitFieldBindings(StringBuilder stringBuilder, ViewBindings viewBindings) {
        Collection<FieldViewBinding> fieldBindings = viewBindings.getFieldBindings();
        if (!fieldBindings.isEmpty()) {
            for (FieldViewBinding fieldViewBinding : fieldBindings) {
                stringBuilder.append("    target.").append(fieldViewBinding.getName()).append(" = ");
                if (fieldViewBinding.requiresCast()) {
                    stringBuilder.append("finder.castView(view, ").append(viewBindings.getId()).append(", \"");
                    emitHumanDescription(stringBuilder, fieldBindings);
                    stringBuilder.append("\");\n");
                } else {
                    stringBuilder.append("view;\n");
                }
            }
        }
    }

    static void emitHumanDescription(StringBuilder stringBuilder, Collection<? extends ViewBinding> collection) {
        Iterator it = collection.iterator();
        switch (collection.size()) {
            case 1:
                stringBuilder.append(((ViewBinding) it.next()).getDescription());
                return;
            case 2:
                stringBuilder.append(((ViewBinding) it.next()).getDescription()).append(" and ").append(((ViewBinding) it.next()).getDescription());
                return;
            default:
                int size = collection.size();
                for (int i = 0; i < size; i++) {
                    if (i != 0) {
                        stringBuilder.append(", ");
                    }
                    if (i == size - 1) {
                        stringBuilder.append("and ");
                    }
                    stringBuilder.append(((ViewBinding) it.next()).getDescription());
                }
                return;
        }
    }

    private void emitMethodBindings(StringBuilder stringBuilder, ViewBindings viewBindings) {
        Map methodBindings = viewBindings.getMethodBindings();
        if (!methodBindings.isEmpty()) {
            String str;
            boolean isEmpty = viewBindings.getRequiredBindings().isEmpty();
            if (isEmpty) {
                stringBuilder.append("    if (view != null) {\n");
                str = "  ";
            } else {
                str = "";
            }
            for (Entry entry : methodBindings.entrySet()) {
                ListenerClass listenerClass = (ListenerClass) entry.getKey();
                Map map = (Map) entry.getValue();
                Object obj = !"android.view.View".equals(listenerClass.targetType()) ? 1 : null;
                stringBuilder.append(str).append("    ");
                if (obj != null) {
                    stringBuilder.append("((").append(listenerClass.targetType());
                    if (listenerClass.genericArguments() > 0) {
                        stringBuilder.append('<');
                        for (int i = 0; i < listenerClass.genericArguments(); i++) {
                            if (i > 0) {
                                stringBuilder.append(", ");
                            }
                            stringBuilder.append('?');
                        }
                        stringBuilder.append('>');
                    }
                    stringBuilder.append(") ");
                }
                stringBuilder.append("view");
                if (obj != null) {
                    stringBuilder.append(')');
                }
                stringBuilder.append('.').append(listenerClass.setter()).append("(\n");
                stringBuilder.append(str).append("      new ").append(listenerClass.type()).append("() {\n");
                for (ListenerMethod listenerMethod : getListenerMethods(listenerClass)) {
                    stringBuilder.append(str).append("        @Override public ").append(listenerMethod.returnType()).append(' ').append(listenerMethod.name()).append("(\n");
                    String[] parameters = listenerMethod.parameters();
                    int length = parameters.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        stringBuilder.append(str).append("          ").append(parameters[i2]).append(" p").append(i2);
                        if (i2 < length - 1) {
                            stringBuilder.append(',');
                        }
                        stringBuilder.append('\n');
                    }
                    stringBuilder.append(str).append("        ) {\n");
                    stringBuilder.append(str).append("          ");
                    obj = !"void".equals(listenerMethod.returnType()) ? 1 : null;
                    if (obj != null) {
                        stringBuilder.append("return ");
                    }
                    if (map.containsKey(listenerMethod)) {
                        Iterator it = ((Set) map.get(listenerMethod)).iterator();
                        while (it.hasNext()) {
                            MethodViewBinding methodViewBinding = (MethodViewBinding) it.next();
                            stringBuilder.append("target.").append(methodViewBinding.getName()).append('(');
                            List parameters2 = methodViewBinding.getParameters();
                            String[] parameters3 = listenerMethod.parameters();
                            int size = parameters2.size();
                            for (length = 0; length < size; length++) {
                                Parameter parameter = (Parameter) parameters2.get(length);
                                int listenerPosition = parameter.getListenerPosition();
                                if (parameter.requiresCast(parameters3[listenerPosition])) {
                                    stringBuilder.append("finder.<").append(parameter.getType()).append(">castParam(p").append(listenerPosition).append(", \"").append(listenerMethod.name()).append("\", ").append(listenerPosition).append(", \"").append(methodViewBinding.getName()).append("\", ").append(length).append(")");
                                } else {
                                    stringBuilder.append('p').append(listenerPosition);
                                }
                                if (length < size - 1) {
                                    stringBuilder.append(", ");
                                }
                            }
                            stringBuilder.append(");");
                            if (it.hasNext()) {
                                stringBuilder.append("\n          ");
                            }
                        }
                    } else if (obj != null) {
                        stringBuilder.append(listenerMethod.defaultReturn()).append(';');
                    }
                    stringBuilder.append('\n');
                    stringBuilder.append(str).append("        }\n");
                }
                stringBuilder.append(str).append("      });\n");
            }
            if (isEmpty) {
                stringBuilder.append("    }\n");
            }
        }
    }

    private void emitUnbindMethod(StringBuilder stringBuilder) {
        stringBuilder.append("  @Override public void unbind(T target) {\n");
        if (this.parentViewBinder != null) {
            stringBuilder.append("    super.unbind(target);\n\n");
        }
        for (ViewBindings fieldBindings : this.viewIdMap.values()) {
            for (FieldViewBinding name : fieldBindings.getFieldBindings()) {
                stringBuilder.append("    target.").append(name.getName()).append(" = null;\n");
            }
        }
        for (FieldCollectionViewBinding name2 : this.collectionBindings.keySet()) {
            stringBuilder.append("    target.").append(name2.getName()).append(" = null;\n");
        }
        stringBuilder.append("  }\n");
    }

    private void emitViewBindings(StringBuilder stringBuilder, ViewBindings viewBindings) {
        stringBuilder.append("    view = ");
        Collection requiredBindings = viewBindings.getRequiredBindings();
        if (requiredBindings.isEmpty()) {
            stringBuilder.append("finder.findOptionalView(source, ").append(viewBindings.getId()).append(", null);\n");
        } else if (viewBindings.getId() == -1) {
            stringBuilder.append("target;\n");
        } else {
            stringBuilder.append("finder.findRequiredView(source, ").append(viewBindings.getId()).append(", \"");
            emitHumanDescription(stringBuilder, requiredBindings);
            stringBuilder.append("\");\n");
        }
        emitFieldBindings(stringBuilder, viewBindings);
        emitMethodBindings(stringBuilder, viewBindings);
    }

    static List<ListenerMethod> getListenerMethods(ListenerClass listenerClass) {
        if (listenerClass.method().length == 1) {
            return Arrays.asList(listenerClass.method());
        }
        List<ListenerMethod> arrayList = new ArrayList();
        Class callbacks = listenerClass.callbacks();
        Enum[] enumArr = (Enum[]) callbacks.getEnumConstants();
        int length = enumArr.length;
        int i = 0;
        while (i < length) {
            ListenerMethod listenerMethod = (ListenerMethod) callbacks.getField(enumArr[i].name()).getAnnotation(ListenerMethod.class);
            if (listenerMethod == null) {
                throw new IllegalStateException(String.format("@%s's %s.%s missing @%s annotation.", new Object[]{callbacks.getEnclosingClass().getSimpleName(), callbacks.getSimpleName(), r6.name(), ListenerMethod.class.getSimpleName()}));
            }
            try {
                arrayList.add(listenerMethod);
                i++;
            } catch (NoSuchFieldException e) {
                throw new AssertionError(e);
            }
        }
        return arrayList;
    }

    private ViewBindings getOrCreateViewBindings(int i) {
        ViewBindings viewBindings = (ViewBindings) this.viewIdMap.get(Integer.valueOf(i));
        if (viewBindings != null) {
            return viewBindings;
        }
        viewBindings = new ViewBindings(i);
        this.viewIdMap.put(Integer.valueOf(i), viewBindings);
        return viewBindings;
    }

    final void addField(int i, FieldViewBinding fieldViewBinding) {
        getOrCreateViewBindings(i).addFieldBinding(fieldViewBinding);
    }

    final void addFieldCollection(int[] iArr, FieldCollectionViewBinding fieldCollectionViewBinding) {
        this.collectionBindings.put(fieldCollectionViewBinding, iArr);
    }

    final boolean addMethod(int i, ListenerClass listenerClass, ListenerMethod listenerMethod, MethodViewBinding methodViewBinding) {
        ViewBindings orCreateViewBindings = getOrCreateViewBindings(i);
        if (orCreateViewBindings.hasMethodBinding(listenerClass, listenerMethod) && !"void".equals(listenerMethod.returnType())) {
            return false;
        }
        orCreateViewBindings.addMethodBinding(listenerClass, listenerMethod, methodViewBinding);
        return true;
    }

    final void addResource(FieldResourceBinding fieldResourceBinding) {
        this.resourceBindings.add(fieldResourceBinding);
    }

    final String brewJava() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("// Generated code from Butter Knife. Do not modify!\n");
        stringBuilder.append("package ").append(this.classPackage).append(";\n\n");
        if (!this.resourceBindings.isEmpty()) {
            stringBuilder.append("import android.content.res.Resources;\n");
        }
        if (!(this.viewIdMap.isEmpty() && this.collectionBindings.isEmpty())) {
            stringBuilder.append("import android.view.View;\n");
        }
        stringBuilder.append("import butterknife.ButterKnife.Finder;\n");
        if (this.parentViewBinder == null) {
            stringBuilder.append("import butterknife.ButterKnife.ViewBinder;\n");
        }
        stringBuilder.append('\n');
        stringBuilder.append("public class ").append(this.className);
        stringBuilder.append("<T extends ").append(this.targetClass).append(">");
        if (this.parentViewBinder != null) {
            stringBuilder.append(" extends ").append(this.parentViewBinder).append("<T>");
        } else {
            stringBuilder.append(" implements ViewBinder<T>");
        }
        stringBuilder.append(" {\n");
        emitBindMethod(stringBuilder);
        stringBuilder.append('\n');
        emitUnbindMethod(stringBuilder);
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    final String getFqcn() {
        return this.classPackage + "." + this.className;
    }

    final ViewBindings getViewBinding(int i) {
        return (ViewBindings) this.viewIdMap.get(Integer.valueOf(i));
    }

    final void setParentViewBinder(String str) {
        this.parentViewBinder = str;
    }
}
