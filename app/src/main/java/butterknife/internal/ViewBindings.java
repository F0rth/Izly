package butterknife.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class ViewBindings {
    private final Set<FieldViewBinding> fieldBindings = new LinkedHashSet();
    private final int id;
    private final LinkedHashMap<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> methodBindings = new LinkedHashMap();

    ViewBindings(int i) {
        this.id = i;
    }

    public final void addFieldBinding(FieldViewBinding fieldViewBinding) {
        this.fieldBindings.add(fieldViewBinding);
    }

    public final void addMethodBinding(ListenerClass listenerClass, ListenerMethod listenerMethod, MethodViewBinding methodViewBinding) {
        Set set;
        Map map;
        Map map2 = (Map) this.methodBindings.get(listenerClass);
        if (map2 == null) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            this.methodBindings.put(listenerClass, linkedHashMap);
            LinkedHashMap linkedHashMap2 = linkedHashMap;
            set = null;
            map = linkedHashMap2;
        } else {
            Map map3 = map2;
            set = (Set) map2.get(listenerMethod);
            map = map3;
        }
        if (set == null) {
            set = new LinkedHashSet();
            map.put(listenerMethod, set);
        }
        set.add(methodViewBinding);
    }

    public final Collection<FieldViewBinding> getFieldBindings() {
        return this.fieldBindings;
    }

    public final int getId() {
        return this.id;
    }

    public final Map<ListenerClass, Map<ListenerMethod, Set<MethodViewBinding>>> getMethodBindings() {
        return this.methodBindings;
    }

    public final List<ViewBinding> getRequiredBindings() {
        List<ViewBinding> arrayList = new ArrayList();
        for (FieldViewBinding fieldViewBinding : this.fieldBindings) {
            if (fieldViewBinding.isRequired()) {
                arrayList.add(fieldViewBinding);
            }
        }
        for (Map values : this.methodBindings.values()) {
            for (Set<MethodViewBinding> it : values.values()) {
                for (MethodViewBinding methodViewBinding : it) {
                    if (methodViewBinding.isRequired()) {
                        arrayList.add(methodViewBinding);
                    }
                }
            }
        }
        return arrayList;
    }

    public final boolean hasMethodBinding(ListenerClass listenerClass, ListenerMethod listenerMethod) {
        Map map = (Map) this.methodBindings.get(listenerClass);
        return map != null && map.containsKey(listenerMethod);
    }
}
