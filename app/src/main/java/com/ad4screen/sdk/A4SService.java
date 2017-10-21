package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.service.modules.g.c;
import com.ad4screen.sdk.service.modules.inapp.a.f;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.c.q;
import com.ad4screen.sdk.service.modules.inapp.k;
import com.ad4screen.sdk.service.modules.k.d;
import com.ad4screen.sdk.service.modules.push.e;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@API
public class A4SService extends Service {
    private d a;
    private com.ad4screen.sdk.service.modules.push.a b;
    private com.ad4screen.sdk.service.modules.inapp.a c;
    private com.ad4screen.sdk.service.modules.f.b d;
    private c e;
    private com.ad4screen.sdk.service.modules.c.b f;
    private com.ad4screen.sdk.service.modules.d.a g;
    private com.ad4screen.sdk.service.a h;
    private HandlerThread i;
    private Handler j;
    private long k;
    private final Object l = new Object();
    private final Runnable m = new Runnable(this) {
        final /* synthetic */ A4SService a;

        {
            this.a = r1;
        }

        public void run() {
            com.ad4screen.sdk.common.a.a.a(this.a).a(new Callback<Boolean>(this) {
                final /* synthetic */ AnonymousClass1 a;

                {
                    this.a = r1;
                }

                public void a(Boolean bool) {
                    if (bool.booleanValue()) {
                        Log.debug("RequestManager|Some requests failed, will retry again at next launch");
                    }
                    if (this.a.a.j != null) {
                        this.a.a.j.postDelayed(this.a.a.n, 5000);
                    }
                }

                public void onError(int i, String str) {
                    Log.debug("RequestManager|Unrecoverable error : " + i + " - " + str);
                    if (this.a.a.j != null) {
                        this.a.a.j.postDelayed(this.a.a.n, 5000);
                    }
                }

                public /* synthetic */ void onResult(Object obj) {
                    a((Boolean) obj);
                }
            });
        }
    };
    private final Runnable n = new Runnable(this) {
        final /* synthetic */ A4SService a;

        {
            this.a = r1;
        }

        public void run() {
            if (!i.a(this.a).g() && this.a.k <= 0) {
                com.ad4screen.sdk.common.a.a.a(this.a).e();
                if (this.a.c != null) {
                    this.a.c.b();
                }
                if (this.a.f != null) {
                    this.a.f.a();
                }
                this.a.stopSelf();
            }
        }
    };
    private final transient a o = new a(this) {
        final /* synthetic */ A4SService a;

        {
            this.a = r1;
        }

        public Context a() {
            return this.a;
        }

        public void a(Runnable runnable) {
            synchronized (this.a.l) {
                this.a.k = 1 + this.a.k;
                if (this.a.j != null) {
                    this.a.j.removeCallbacks(this.a.m);
                    this.a.j.removeCallbacks(this.a.n);
                    this.a.j.post(new b(this.a, runnable));
                }
            }
        }

        public void a(Runnable runnable, long j) {
            synchronized (this.a.l) {
                this.a.k = 1 + this.a.k;
                if (this.a.j != null) {
                    this.a.j.removeCallbacks(this.a.m);
                    this.a.j.removeCallbacks(this.a.n);
                    this.a.j.postDelayed(new b(this.a, runnable), j);
                }
            }
        }

        public d b() {
            return this.a.a;
        }

        public com.ad4screen.sdk.service.modules.push.a c() {
            return this.a.b;
        }

        public com.ad4screen.sdk.service.modules.inapp.a d() {
            return this.a.c;
        }

        public com.ad4screen.sdk.service.modules.f.b e() {
            return this.a.d;
        }

        public com.ad4screen.sdk.service.modules.d.a f() {
            return this.a.g;
        }

        public c g() {
            return this.a.e;
        }

        public com.ad4screen.sdk.service.modules.c.b h() {
            return this.a.f;
        }
    };

    public interface a {
        Context a();

        void a(Runnable runnable);

        void a(Runnable runnable, long j);

        d b();

        com.ad4screen.sdk.service.modules.push.a c();

        com.ad4screen.sdk.service.modules.inapp.a d();

        com.ad4screen.sdk.service.modules.f.b e();

        com.ad4screen.sdk.service.modules.d.a f();

        c g();

        com.ad4screen.sdk.service.modules.c.b h();
    }

    final class b implements Runnable {
        final /* synthetic */ A4SService a;
        private final transient Runnable b;

        public b(A4SService a4SService, Runnable runnable) {
            this.a = a4SService;
            this.b = runnable;
        }

        public final void run() {
            this.b.run();
            synchronized (this.a.l) {
                this.a.k = this.a.k - 1;
                if (!(com.ad4screen.sdk.d.b.a(this.a).F() || com.ad4screen.sdk.d.a.a(this.a).a() || !i.a(this.a).g())) {
                    com.ad4screen.sdk.d.a.a(this.a).b();
                }
                if (this.a.k <= 0) {
                    this.a.k = 0;
                    if (!(i.a(this.a).g() || this.a.j == null)) {
                        Log.debug("A4SService|No more tasks to process and no activity is visible, trying to stop A4SService in 5s");
                        if (com.ad4screen.sdk.d.a.a(this.a).a()) {
                            com.ad4screen.sdk.d.a.a(this.a).c();
                        }
                        if (this.a.j != null) {
                            this.a.j.postDelayed(this.a.m, 5000);
                        }
                    }
                }
            }
        }
    }

    private void a(Intent intent) {
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                String scheme = data.getScheme();
                if (scheme != null && scheme.contains("a4slocalnotif")) {
                    Log.debug("Alarm|New alarm has been triggered");
                    com.ad4screen.sdk.service.modules.a.b a = com.ad4screen.sdk.service.modules.a.b.a(this.o);
                    String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    com.ad4screen.sdk.service.modules.a.a.c a2 = a.a(schemeSpecificPart);
                    if (a2 == null) {
                        Log.debug("Alarm|An error occurred when trying to launch alarm #" + schemeSpecificPart + ". Is this alarm already cancelled? Aborting display..");
                    } else if (a2.o) {
                        a.b(schemeSpecificPart);
                        Log.warn("Alarm|The alarm #" + schemeSpecificPart + " has not been displayed since this alarm is member of control group");
                    } else {
                        com.ad4screen.sdk.service.modules.push.a.a a3 = com.ad4screen.sdk.service.modules.push.a.a.a(a2.c());
                        if (a3 == null) {
                            Log.debug("Alarm|An error occurred when parsing alarm #" + schemeSpecificPart + " push payload. Aborting display..");
                            a.b(schemeSpecificPart);
                            return;
                        }
                        i a4 = i.a((Context) this);
                        if ((a4.g() || a4.j()) && !a3.y) {
                            Log.debug("Alarm|Alarm #" + schemeSpecificPart + " is ready to display, but app is in foreground. Alarm will not be displayed...");
                            a.a(schemeSpecificPart, a4.f().a());
                        } else if (a(a2, a4)) {
                            Log.debug("Alarm|Launching alarm #" + schemeSpecificPart);
                            this.o.c().setFormat(a2);
                            this.o.c().handleMessage(a2.c());
                            a.b(schemeSpecificPart);
                        } else {
                            Log.debug("Alarm|Alarm #" + schemeSpecificPart + " is not allowed to be displayed anymore.");
                            a.a(schemeSpecificPart, a4.f().a());
                        }
                    }
                }
            }
        }
    }

    private boolean a(com.ad4screen.sdk.service.modules.a.a.c cVar, i iVar) {
        com.ad4screen.sdk.service.modules.inapp.a d = this.o.d();
        f f = d.f();
        List arrayList = new ArrayList(Arrays.asList(new m[]{new q(g.e()), new com.ad4screen.sdk.service.modules.inapp.c.g(), new com.ad4screen.sdk.service.modules.inapp.c.b.a(this.o.a()), new com.ad4screen.sdk.service.modules.inapp.c.a.a(this.o.a()), new com.ad4screen.sdk.service.modules.inapp.c.b.c(this.o.a(), com.ad4screen.sdk.d.a.a(this.o.a())), new com.ad4screen.sdk.service.modules.inapp.c.a.c(this.o.a(), com.ad4screen.sdk.d.a.a(this.o.a()))}));
        k a = k.a(iVar);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((m) it.next()).a((Context) this, a);
        }
        h a2 = f.a(cVar.h);
        j c = f.c(cVar.h);
        return c == null || a2 == null || d.a(f.a(), arrayList, c, a2);
    }

    public IBinder onBind(Intent intent) {
        startService(new Intent(this, A4SService.class));
        return this.h;
    }

    public void onCreate() {
        super.onCreate();
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a((Context) this);
        if (a.D()) {
            Debug.waitForDebugger();
        }
        Log.setEnabled(a.E());
        Log.debug("---------------------------------------- A4S - START ----------------------------------------");
        this.i = new HandlerThread("com.ad4screen.sdk.A4SService.worker");
        this.i.start();
        this.j = new Handler(this.i.getLooper());
        this.g = new com.ad4screen.sdk.service.modules.d.a();
        if (a.F()) {
            Log.debug("A4SService|Geolocation is disabled in your AndroidManifest.xml. Manually update geolocation with updateGeolocation method if you want geolocated In-App or Push to be displayed.");
        }
        com.ad4screen.sdk.common.a.a.a((Context) this).b();
        this.a = new d(this.o);
        this.b = e.a(this.o);
        this.e = new c(this.o);
        this.c = new com.ad4screen.sdk.service.modules.inapp.a(this.o);
        this.d = new com.ad4screen.sdk.service.modules.f.b(this.o);
        this.f = new com.ad4screen.sdk.service.modules.c.b(this.o);
        this.h = new com.ad4screen.sdk.service.a(this.o);
        if (com.ad4screen.sdk.d.d.a((Context) this).b()) {
            Log.debug("A4SService|Refreshing webservices URLs");
            com.ad4screen.sdk.common.a.a.a((Context) this).a(new com.ad4screen.sdk.service.modules.d.c(this));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        com.ad4screen.sdk.d.f.a().b();
        com.ad4screen.sdk.common.b.m.g.a(this.i);
        this.j = null;
        com.ad4screen.sdk.common.a.a.a((Context) this).e();
        Log.debug("---------------------------------------- A4S - STOP ----------------------------------------");
    }

    public void onStart(Intent intent, int i) {
        if (this.j != null) {
            this.j.removeCallbacks(this.m);
            this.j.removeCallbacks(this.n);
        }
        a(intent);
    }

    @SuppressLint({"InlinedApi"})
    public int onStartCommand(Intent intent, int i, int i2) {
        onStart(intent, i2);
        return 2;
    }

    @TargetApi(14)
    public void onTaskRemoved(Intent intent) {
        if (this.j != null) {
            this.j.removeCallbacks(this.m);
            this.j.removeCallbacks(this.n);
        }
        onDestroy();
        super.onTaskRemoved(intent);
    }
}
