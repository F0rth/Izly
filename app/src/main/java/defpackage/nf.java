package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public final class nf {
    private final AtomicReference<ni> a;
    private final CountDownLatch b;
    private nh c;
    private boolean d;

    private nf() {
        this.a = new AtomicReference();
        this.b = new CountDownLatch(1);
        this.d = false;
    }

    private void a(ni niVar) {
        this.a.set(niVar);
        this.b.countDown();
    }

    public final nf a(jy jyVar, kw kwVar, mh mhVar, String str, String str2, String str3) {
        synchronized (this) {
            if (!this.d) {
                if (this.c == null) {
                    Context context = jyVar.getContext();
                    String str4 = kwVar.b;
                    String a = new kn().a(context);
                    String d = kwVar.d();
                    la laVar = new la();
                    mz mzVar = new mz();
                    mx mxVar = new mx(jyVar);
                    String i = kp.i(context);
                    jy jyVar2 = jyVar;
                    String str5 = str3;
                    na naVar = new na(jyVar2, str5, String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", new Object[]{str4}), mhVar);
                    this.c = new my(jyVar, new nl(a, kwVar.b(), kw.a(VERSION.INCREMENTAL), kw.a(VERSION.RELEASE), kwVar.f(), kwVar.a(), kwVar.g(), kp.a(kp.k(context)), str2, str, ks.a(d).e, i), laVar, mzVar, mxVar, naVar);
                }
                this.d = true;
            }
        }
        return this;
    }

    public final ni a() {
        try {
            this.b.await();
            return (ni) this.a.get();
        } catch (InterruptedException e) {
            js.a().e("Fabric", "Interrupted while waiting for settings data.");
            return null;
        }
    }

    public final boolean b() {
        boolean z;
        synchronized (this) {
            ni a = this.c.a();
            a(a);
            z = a != null;
        }
        return z;
    }

    public final boolean c() {
        boolean z;
        synchronized (this) {
            ni a = this.c.a(ng.SKIP_CACHE_LOOKUP);
            a(a);
            if (a == null) {
                js.a().c("Fabric", "Failed to force reload of settings from Crashlytics.", null);
            }
            z = a != null;
        }
        return z;
    }
}
