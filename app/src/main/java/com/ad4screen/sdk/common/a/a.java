package com.ad4screen.sdk.common.a;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m;
import com.ad4screen.sdk.common.e.b;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.service.modules.c.f;
import com.ad4screen.sdk.service.modules.e.e;
import com.ad4screen.sdk.service.modules.inapp.i;
import com.ad4screen.sdk.service.modules.k.g;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.json.JSONObject;

public final class a {
    private static a u;
    private static com.ad4screen.sdk.common.e.a v;
    Context a;
    private int b;
    private boolean c = true;
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    private boolean g = false;
    private boolean h = false;
    private int i = -1;
    private int j = -1;
    private HandlerThread k;
    private Handler l;
    private Callback<Boolean> m;
    private Runnable n = a();
    private com.ad4screen.sdk.common.a.a.a o;
    private CopyOnWriteArrayList<b> p;
    private HashMap<String, c> q;
    private ConcurrentHashMap<String, c> r;
    private List<d.b> s;
    private List<String> t;
    private com.ad4screen.sdk.common.e.d.c w = new com.ad4screen.sdk.common.e.d.c(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(c cVar, String str) {
            Log.debug("RequestManager|Request to " + str + " succeeded");
            if (cVar.n() && !cVar.o()) {
                this.a.j();
            }
            if (cVar instanceof com.ad4screen.sdk.common.e.a) {
                this.a.j();
            }
        }

        public void b(c cVar, String str) {
            int i = 320000;
            this.a.d = true;
            Log.debug("RequestManager|Request to " + str + " failed");
            if (cVar.n()) {
                int e = cVar.l() == 0 ? this.a.k() : cVar.l() * 2;
                if (e <= 320000) {
                    i = e;
                }
                cVar.c(i);
                if (!this.a.h) {
                    this.a.a(cVar, str);
                }
                if (!cVar.o()) {
                    this.a.j();
                }
            }
            if (cVar instanceof com.ad4screen.sdk.common.e.a) {
                this.a.j();
            }
        }
    };

    private a(Context context) {
        this.a = context;
        this.r = new ConcurrentHashMap();
        this.p = new CopyOnWriteArrayList();
        this.o = new com.ad4screen.sdk.common.a.a.a(this.a);
        v = new com.ad4screen.sdk.common.e.a(this.a);
        a(new c[]{new com.ad4screen.sdk.service.modules.j.b(this.a, new Bundle()), new com.ad4screen.sdk.service.modules.e.d(this.a, new Bundle()), new e(this.a, 0), new f(this.a, 0), new com.ad4screen.sdk.service.modules.c.d(this.a, new Bundle()), new i(this.a, new Bundle(), false), new com.ad4screen.sdk.service.modules.i.a(this.a), new com.ad4screen.sdk.service.modules.h.a(null, this.a), new com.ad4screen.sdk.service.modules.h.f(null, this.a), new com.ad4screen.sdk.service.modules.g.d(this.a, null), new com.ad4screen.sdk.service.modules.push.f(this.a, null, null, false), new com.ad4screen.sdk.service.modules.k.a.d(this.a, com.ad4screen.sdk.d.b.a(this.a), null, null), new com.ad4screen.sdk.service.modules.k.a.b(this.a, com.ad4screen.sdk.d.b.a(this.a), null), new com.ad4screen.sdk.service.modules.k.a.a(this.a, com.ad4screen.sdk.d.b.a(this.a), null), new com.ad4screen.sdk.service.modules.k.a.c(this.a, com.ad4screen.sdk.d.b.a(this.a), null), new com.ad4screen.sdk.service.modules.k.c.a(this.a, com.ad4screen.sdk.d.b.a(this.a), null), new com.ad4screen.sdk.service.modules.k.c.b(this.a, null), new com.ad4screen.sdk.service.modules.k.e.a(this.a, null), new com.ad4screen.sdk.service.modules.k.b(this.a, null, null, null), new g(this.a), new com.ad4screen.sdk.service.modules.d.g(this.a, null, new com.ad4screen.sdk.common.e[0]), new com.ad4screen.sdk.service.modules.d.f(this.a, null), new com.ad4screen.sdk.service.modules.d.d(this.a, null, null, null, null), new com.ad4screen.sdk.service.modules.d.e(this.a, null, null)});
        h();
        this.k = new HandlerThread("com.ad4screen.sdk.A4SService.network");
        this.k.start();
        this.l = new Handler(this.k.getLooper());
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.common.e.d.b.class, this.w);
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.common.e.d.a.class, this.w);
        b();
    }

    public static a a(Context context) {
        a aVar;
        synchronized (a.class) {
            try {
                if (u == null || u.l == null || u.k == null || !u.k.isAlive()) {
                    u = new a(context);
                }
                aVar = u;
            } catch (Throwable th) {
                Class cls = a.class;
            }
        }
        return aVar;
    }

    private void a(c[] cVarArr) {
        ConcurrentHashMap a = this.o.a();
        if (a.size() > 0) {
            for (String str : a.keySet()) {
                try {
                    String string = ((JSONObject) a.get(str)).getString("type");
                    for (int i = 0; i < cVarArr.length; i++) {
                        if (string.equals(cVarArr[i].getClassKey())) {
                            c cVar = (c) new com.ad4screen.sdk.common.c.e().a(((JSONObject) a.get(str)).toString(), cVarArr[i]);
                            if (com.ad4screen.sdk.common.g.e().a() - cVar.b <= 432000000) {
                                Log.internal("RequestManager|" + str + " task restored from saved cache");
                                a(cVar, str);
                                break;
                            }
                            Log.internal("RequestManager|" + str + " is too old. Task will not be restored");
                        }
                    }
                } catch (Throwable e) {
                    Log.internal("RequestManager|Cannot deserialize " + str + " from file", e);
                }
            }
            Log.internal("RequestManager|" + this.r.size() + " task(s) restored from saved cache");
        }
    }

    private void h() {
        ArrayList arrayList = (ArrayList) this.o.b("externalSavedQueue", new ArrayList());
        if (arrayList.size() > 0) {
            int i = 0;
            while (i < arrayList.size()) {
                b[] bVarArr = new b[]{new com.ad4screen.sdk.service.modules.k.f.a.d(this.a, null, null), new com.ad4screen.sdk.service.modules.k.f.a.e(this.a)};
                int i2 = 0;
                while (i2 < 2) {
                    try {
                        if (bVarArr[i2].getClassKey() != null && bVarArr[i2].getClassKey().equals(((JSONObject) arrayList.get(i)).getString("type"))) {
                            b bVar = (b) new com.ad4screen.sdk.common.c.e().a(((JSONObject) arrayList.get(i)).toString(), bVarArr[i2]);
                            if (com.ad4screen.sdk.common.g.e().a() - bVar.a <= 432000000) {
                                Log.internal("RequestManager|" + bVar.getClassKey() + " external task restored from saved cache");
                                a(bVar);
                                break;
                            }
                            Log.internal("RequestManager|" + bVar.getClassKey() + " is too old. External Task will not be restored");
                        }
                    } catch (Throwable e) {
                        Log.internal("RequestManager|Cannot deserialize " + bVarArr[i2].getClassKey() + " from file", e);
                    }
                    i2++;
                }
                i++;
            }
        }
        Log.internal("RequestManager|" + this.p.size() + " external task(s) restored from saved cache");
    }

    private void i() {
        synchronized (this) {
            if (this.l != null) {
                if (this.n == null) {
                    this.n = a();
                }
                this.l.removeCallbacks(this.n);
                Log.internal("RequestManager|Flushing cache...");
                if ((this.q == null || this.q.size() == 0) && (this.r == null || this.r.size() == 0)) {
                    Log.internal("RequestManager|No requests currently cached");
                    Log.debug("RequestManager|Cache will be flushed again in " + (k() / 1000) + " secs");
                    this.l.postDelayed(this.n, (long) k());
                    if (this.m != null) {
                        this.m.onResult(Boolean.valueOf(this.d));
                    }
                } else if (this.c && !this.e) {
                    Log.internal("RequestManager|Manager is stopped. Flush cancelled");
                    if (this.m != null) {
                        this.m.onResult(Boolean.valueOf(true));
                    }
                } else if (!(this.e || this.c)) {
                    if (this.i == -1 || this.i < this.q.size()) {
                        this.e = true;
                        if (this.i == -1) {
                            this.f = true;
                            this.d = false;
                            this.i = 0;
                            ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap(this.r);
                            this.q = new HashMap();
                            for (String str : this.r.keySet()) {
                                c cVar = (c) this.r.get(str);
                                if (!cVar.q()) {
                                    Log.debug("RequestManager|No url for " + str + " . Task skipped and will be flushed as soon as a valid url is available");
                                } else if (cVar.o()) {
                                    if (cVar.m() || this.m != null) {
                                        v.a(cVar);
                                        concurrentHashMap.remove(str);
                                    } else if (cVar.r() >= cVar.s()) {
                                        Log.debug("RequestManager|" + cVar.r() + " of " + cVar.s() + " request to " + str + " failed. This service will be flushed at next launch");
                                    } else {
                                        Log.debug("RequestManager|Previous request to " + str + " failed, this service will be flushed later. Delay : " + (cVar.l() / 1000) + " secs");
                                    }
                                } else if (cVar.m() || this.m != null) {
                                    this.q.put(str, cVar);
                                    concurrentHashMap.remove(str);
                                } else if (cVar.r() >= cVar.s()) {
                                    Log.debug("RequestManager|" + cVar.r() + " of " + cVar.s() + " request to " + str + " failed. This service will be flushed at next launch");
                                } else {
                                    Log.debug("RequestManager|Previous request to " + str + " failed, this service will be flushed later. Delay : " + (cVar.l() / 1000) + " secs");
                                }
                            }
                            this.r = concurrentHashMap;
                            this.o.a(this.r);
                            this.s = new ArrayList();
                            this.t = new ArrayList();
                            for (String str2 : this.q.keySet()) {
                                try {
                                    this.s.add(d.b.valueOf(str2));
                                } catch (Throwable e) {
                                    Log.internal("RequestManager|Impossible to cast String in valid Service, add to external services", e);
                                    this.t.add(str2);
                                }
                            }
                            if (!v.b()) {
                                this.s.add(d.b.BulkWebservice);
                                this.q.put(d.b.BulkWebservice.toString(), v);
                            }
                            Collections.sort(this.s);
                            if (this.s.size() == 0) {
                                this.j = 0;
                            }
                        }
                        Runnable runnable = null;
                        if (this.i >= this.s.size() && this.j < this.t.size()) {
                            runnable = (c) this.q.get(this.t.get(this.j));
                            Log.internal("RequestManager|Flushing URL : " + ((String) this.t.get(this.j)));
                        } else if (this.i < this.s.size()) {
                            c cVar2 = (c) this.q.get(((d.b) this.s.get(this.i)).toString());
                            Log.internal("RequestManager|Flushing Service : " + ((d.b) this.s.get(this.i)).toString());
                        }
                        if (runnable != null) {
                            this.l.post(runnable);
                        } else {
                            j();
                        }
                    } else {
                        ArrayList arrayList = new ArrayList(this.p);
                        this.p.clear();
                        this.o.a("externalSavedQueue", (Object) new ArrayList(this.p));
                        Iterator it = arrayList.iterator();
                        while (it.hasNext()) {
                            b bVar = (b) it.next();
                            Log.internal("RequestManager|Flushing task : " + bVar.getClass().getName());
                            bVar.run();
                        }
                        this.f = false;
                        Log.internal("RequestManager|Cache flush is done");
                        this.i = -1;
                        this.j = -1;
                        this.q.clear();
                        Log.debug("RequestManager|Cache will be flushed again in " + (k() / 1000) + " secs");
                        this.l.postDelayed(this.n, (long) k());
                        if (this.m != null) {
                            this.m.onResult(Boolean.valueOf(this.d));
                        }
                        this.h = false;
                        if (this.b >= 3) {
                            this.g = false;
                            this.b = 0;
                            Log.internal("RequestManager|Too many flush done at once, aborting any new needed immediate flush..");
                        } else if (this.g) {
                            this.b++;
                            this.g = false;
                            Log.internal("RequestManager|A new request needs immediate flush. Flushing again..");
                            i();
                        }
                    }
                }
            }
        }
    }

    private void j() {
        this.e = false;
        if (this.q == null || this.s == null) {
            Log.internal("RequestManager|Can't flush next tasks, is RequestManager stopped?");
            return;
        }
        this.i++;
        if (this.i >= this.s.size()) {
            this.j++;
        }
        i();
    }

    private int k() {
        String a = com.ad4screen.sdk.common.i.a(this.a, "com.ad4screen.cache.delay", A4SService.class);
        return (a == null || Integer.valueOf(a).intValue() * 1000 < 5000 || Integer.valueOf(a).intValue() * 1000 > 320000) ? 10000 : Integer.valueOf(a).intValue() * 1000;
    }

    public final Runnable a() {
        return new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                if (!this.a.e && !this.a.c) {
                    this.a.i();
                }
            }
        };
    }

    public final void a(Callback<Boolean> callback) {
        Log.internal("RequestManager|App is stopped, flushing all");
        this.m = callback;
        i();
    }

    public final void a(b bVar) {
        Log.internal("RequestManager|Task : " + bVar.getClass().getName() + " will be flushed with cache");
        if (this.p != null) {
            this.p.add(bVar);
            this.o.a("externalSavedQueue", (Object) new ArrayList(this.p));
        }
    }

    public final void a(c cVar, String str) {
        if (this.r != null) {
            if (str == null || str.length() == 0) {
                Log.internal("RequestManager|Can't cache a request with null or empty service");
            } else if (this.r.containsKey(str)) {
                Log.internal("RequestManager|Request to " + str + " merged and added to queue");
                this.r.put(str, b.a((c) this.r.get(str), cVar));
            } else {
                this.r.put(str, cVar);
                this.o.a(this.r);
                Log.internal("RequestManager|Request to " + str + " added to queue");
            }
        }
    }

    public final void a(Runnable runnable) {
        Log.internal("RequestManager|Flushing task immediately : " + runnable.getClass().getName());
        if (this.l != null) {
            this.l.post(runnable);
            f();
        }
    }

    public final void b() {
        if (this.c) {
            if (this.n == null) {
                this.n = a();
            }
            this.c = false;
            if (this.l != null) {
                Log.debug("RequestManager|Cache will be flushed in " + (k() / 1000) + " secs");
                this.l.postDelayed(this.n, (long) k());
            }
        }
    }

    public final void c() {
        if (!this.c) {
            this.c = true;
            if (!(this.l == null || this.n == null)) {
                this.l.removeCallbacks(this.n);
            }
            Log.debug("RequestManager|Request Manager is now stopped");
        }
    }

    public final boolean d() {
        return this.c;
    }

    public final void e() {
        com.ad4screen.sdk.d.f.a().b(com.ad4screen.sdk.common.e.d.b.class, this.w);
        com.ad4screen.sdk.d.f.a().b(com.ad4screen.sdk.common.e.d.a.class, this.w);
        this.m = null;
        c();
        m.g.a(this.k);
    }

    public final void f() {
        if (this.f) {
            this.g = true;
        } else {
            i();
        }
    }

    public final void g() {
        if (this.f) {
            this.h = true;
        }
        this.r.clear();
        this.o.a(this.r);
    }
}
