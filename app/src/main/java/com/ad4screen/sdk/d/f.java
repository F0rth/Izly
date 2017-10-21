package com.ad4screen.sdk.d;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class f {
    private static f b;
    private final HashMap<Class, CopyOnWriteArrayList> a = new HashMap();

    public interface a<L> {
        void a(L l);
    }

    private f() {
    }

    public static f a() {
        if (b == null) {
            b = new f();
        }
        return b;
    }

    private <L> CopyOnWriteArrayList<L> a(Class<? extends a<L>> cls) {
        CopyOnWriteArrayList<L> copyOnWriteArrayList;
        synchronized (this.a) {
            copyOnWriteArrayList = (CopyOnWriteArrayList) this.a.get(cls);
            if (copyOnWriteArrayList != null) {
            } else {
                copyOnWriteArrayList = new CopyOnWriteArrayList();
                this.a.put(cls, copyOnWriteArrayList);
            }
        }
        return copyOnWriteArrayList;
    }

    public final <L> void a(a<L> aVar) {
        Iterator it = a(aVar.getClass()).iterator();
        while (it.hasNext()) {
            aVar.a(it.next());
        }
    }

    public final <L> void a(Class<? extends a<L>> cls, L l) {
        CopyOnWriteArrayList a = a((Class) cls);
        synchronized (a) {
            if (!a.contains(l)) {
                a.add(l);
            }
        }
    }

    public final void b() {
        this.a.clear();
    }

    public final <L> void b(Class<? extends a<L>> cls, L l) {
        CopyOnWriteArrayList a = a((Class) cls);
        synchronized (a) {
            a.remove(l);
        }
    }
}
