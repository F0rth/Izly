package defpackage;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

public class js {
    static volatile js a;
    static final kb b = new jr();
    public final ExecutorService c;
    public jq d;
    public WeakReference<Activity> e;
    final kb f;
    final boolean g;
    private final Context h;
    private final Map<Class<? extends jy>, jy> i;
    private final Handler j;
    private final jv<js> k;
    private final jv<?> l;
    private final kw m;
    private AtomicBoolean n = new AtomicBoolean(false);

    js(Context context, Map<Class<? extends jy>, jy> map, lm lmVar, Handler handler, kb kbVar, boolean z, jv jvVar, kw kwVar, Activity activity) {
        this.h = context;
        this.i = map;
        this.c = lmVar;
        this.j = handler;
        this.f = kbVar;
        this.g = z;
        this.k = jvVar;
        this.l = new 2(this, map.size());
        this.m = kwVar;
        a(activity);
    }

    public static js a(Context context, jy... jyVarArr) {
        if (a == null) {
            synchronized (js.class) {
                try {
                    if (a == null) {
                        a aVar = new a(context);
                        if (aVar.b != null) {
                            throw new IllegalStateException("Kits already set.");
                        }
                        Map hashMap;
                        aVar.b = jyVarArr;
                        if (aVar.c == null) {
                            aVar.c = lm.a();
                        }
                        if (aVar.d == null) {
                            aVar.d = new Handler(Looper.getMainLooper());
                        }
                        if (aVar.e == null) {
                            if (aVar.f) {
                                aVar.e = new jr(3);
                            } else {
                                aVar.e = new jr();
                            }
                        }
                        if (aVar.h == null) {
                            aVar.h = aVar.a.getPackageName();
                        }
                        if (aVar.i == null) {
                            aVar.i = jv.d;
                        }
                        if (aVar.b == null) {
                            hashMap = new HashMap();
                        } else {
                            Collection asList = Arrays.asList(aVar.b);
                            hashMap = new HashMap(asList.size());
                            js.a(hashMap, asList);
                        }
                        Context applicationContext = aVar.a.getApplicationContext();
                        kw kwVar = new kw(applicationContext, aVar.h, aVar.g, hashMap.values());
                        lm lmVar = aVar.c;
                        Handler handler = aVar.d;
                        kb kbVar = aVar.e;
                        boolean z = aVar.f;
                        jv jvVar = aVar.i;
                        Context context2 = aVar.a;
                        js jsVar = new js(applicationContext, hashMap, lmVar, handler, kbVar, z, jvVar, kwVar, context2 instanceof Activity ? (Activity) context2 : null);
                        a = jsVar;
                        jsVar.d = new jq(jsVar.h);
                        jsVar.d.a(new 1(jsVar));
                        jsVar.a(jsVar.h);
                    }
                } catch (Throwable th) {
                    Class cls = js.class;
                }
            }
        }
        return a;
    }

    public static <T extends jy> T a(Class<T> cls) {
        if (a != null) {
            return (jy) a.i.get(cls);
        }
        throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    }

    public static kb a() {
        return a == null ? b : a.f;
    }

    private void a(Context context) {
        Future submit = this.c.submit(new ju(context.getPackageCodePath()));
        Collection values = this.i.values();
        kc kcVar = new kc(submit, values);
        List<jy> arrayList = new ArrayList(values);
        Collections.sort(arrayList);
        kcVar.injectParameters(context, this, jv.d, this.m);
        for (jy injectParameters : arrayList) {
            injectParameters.injectParameters(context, this, this.l, this.m);
        }
        kcVar.initialize();
        StringBuilder append = js.a().a(3) ? new StringBuilder("Initializing io.fabric.sdk.android:fabric").append(" [Version: 1.4.0.18").append("], with the following kits:\n") : null;
        for (jy injectParameters2 : arrayList) {
            injectParameters2.initializationTask.a(kcVar.initializationTask);
            js.a(this.i, injectParameters2);
            injectParameters2.initialize();
            if (append != null) {
                append.append(injectParameters2.getIdentifier()).append(" [Version: ").append(injectParameters2.getVersion()).append("]\n");
            }
        }
        if (append != null) {
            js.a().a("Fabric", append.toString());
        }
    }

    private static void a(Map<Class<? extends jy>, jy> map, Collection<? extends jy> collection) {
        for (jy jyVar : collection) {
            map.put(jyVar.getClass(), jyVar);
            if (jyVar instanceof jz) {
                js.a((Map) map, ((jz) jyVar).getKits());
            }
        }
    }

    private static void a(Map<Class<? extends jy>, jy> map, jy jyVar) {
        lf lfVar = jyVar.dependsOnAnnotation;
        if (lfVar != null) {
            for (Class cls : lfVar.a()) {
                if (cls.isInterface()) {
                    for (jy jyVar2 : map.values()) {
                        if (cls.isAssignableFrom(jyVar2.getClass())) {
                            jyVar.initializationTask.a(jyVar2.initializationTask);
                        }
                    }
                } else if (((jy) map.get(cls)) == null) {
                    throw new lo("Referenced Kit was null, does the kit exist?");
                } else {
                    jyVar.initializationTask.a(((jy) map.get(cls)).initializationTask);
                }
            }
        }
    }

    public static boolean b() {
        return a == null ? false : a.g;
    }

    public final js a(Activity activity) {
        this.e = new WeakReference(activity);
        return this;
    }
}
