package com.ad4screen.sdk.d;

import android.content.Context;

import com.ad4screen.sdk.Log;

import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class i {
    private static i e;
    private com.ad4screen.sdk.service.modules.b.a.a a;
    private final j b;
    private final k c;
    private final Context d;

    public static final class a implements com.ad4screen.sdk.d.f.a<e> {
        private final String a;
        private final String b;
        private final String c;

        public a(String str, String str2, String str3) {
            this.b = str2;
            this.c = str3;
            this.a = str;
        }

        public final void a(e eVar) {
            eVar.a(this.a, this.b, this.c);
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<f> {
        public final void a(f fVar) {
            fVar.a();
        }
    }

    public static final class c implements com.ad4screen.sdk.d.f.a<g> {
        public final void a(g gVar) {
            gVar.a();
        }
    }

    public static final class d implements com.ad4screen.sdk.d.f.a<h> {
        public final void a(h hVar) {
            hVar.a();
        }
    }

    public interface e {
        void a(String str, String str2, String str3);
    }

    public interface f {
        void a();
    }

    public interface g {
        void a();
    }

    public interface h {
        void a();
    }

    public interface i {
        void a();
    }

    public static final class j implements com.ad4screen.sdk.d.f.a<i> {
        public final void a(i iVar) {
            iVar.a();
        }
    }

    private i(Context context) {
        this.b = new j(context);
        this.c = new k(context);
        this.d = context;
    }

    public static i a(Context context) {
        i iVar;
        synchronized (i.class) {
            try {
                if (e == null) {
                    e = new i(context.getApplicationContext());
                }
                iVar = e;
            } catch (Throwable th) {
                Class cls = i.class;
            }
        }
        return iVar;
    }

    private int k() {
        int e = this.b.e();
        return (e <= 0 || g()) ? e : 0;
    }

    private void l() {
        this.b.c();
        this.c.c();
        if (this.a != null) {
            a(this.a);
        }
    }

    private void m() {
        this.c.c();
        this.c.a(com.ad4screen.sdk.common.g.e().c());
        f.a().a(new j());
    }

    public final long a() {
        return com.ad4screen.sdk.common.g.e().a() - this.b.a();
    }

    public final void a(com.ad4screen.sdk.service.modules.b.a.a aVar) {
        SecretKey a = com.ad4screen.sdk.common.i.a();
        Cipher a2 = com.ad4screen.sdk.common.i.a(a);
        this.b.a("t", (Object) com.ad4screen.sdk.common.i.a(a2, aVar.a));
        this.b.a("u", (Object) com.ad4screen.sdk.common.i.a(a2, aVar.b));
        this.b.a("v", (Object) com.ad4screen.sdk.common.b.m.c.a(a.getEncoded(), 0));
        this.b.a("w", (Object) com.ad4screen.sdk.common.b.m.c.a(a2.getIV(), 0));
        this.b.d();
        this.a = aVar;
    }

    public final void a(String str, String str2, String str3) {
        int i = 0;
        long a = a();
        int k = k();
        int i2 = (a <= 300000 || k != 0) ? 0 : 1;
        Log.debug("Session|Session is " + (i2 != 0 ? "restarting" : "in progress") + ". UI count : " + k + ", Last session activity : " + (a / 1000) + '/' + 300);
        Log.debug("Session|LaunchActivity started : displayed #" + (k + 1));
        if (k == 0) {
            i = 1;
        }
        if (i != 0) {
            Log.debug("Session|Entered foreground");
            f.a().a(new d());
        }
        if (i2 != 0) {
            l();
            m();
        }
        this.b.a(com.ad4screen.sdk.common.g.e().a());
        this.b.a(1);
        f.a().a(new a(str, str2, str3));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b() {
        /*
        r6 = this;
        r0 = 1;
        r1 = 0;
        r2 = r6.b;
        r2 = r2.e();
        if (r2 <= 0) goto L_0x0012;
    L_0x000a:
        r3 = r6.g();
        if (r3 != 0) goto L_0x0012;
    L_0x0010:
        r2 = r1;
        r1 = r0;
    L_0x0012:
        if (r2 <= 0) goto L_0x0060;
    L_0x0014:
        r3 = r6.b;
        r4 = r2 + -1;
        r3.a(r4);
        r3 = r2 + -1;
        if (r3 > 0) goto L_0x0060;
    L_0x001f:
        r1 = r6.b;
        r3 = com.ad4screen.sdk.common.g.e();
        r4 = r3.a();
        r1.a(r4);
        r1 = new java.lang.StringBuilder;
        r3 = "Session|LaunchActivity stopped : displayed #";
        r1.<init>(r3);
        r2 = r2 + -1;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.ad4screen.sdk.Log.debug(r1);
        if (r0 == 0) goto L_0x0053;
    L_0x0042:
        r0 = "Session|Entered background";
        com.ad4screen.sdk.Log.debug(r0);
        r0 = com.ad4screen.sdk.d.f.a();
        r1 = new com.ad4screen.sdk.d.i$c;
        r1.<init>();
        r0.a(r1);
    L_0x0053:
        r0 = com.ad4screen.sdk.d.f.a();
        r1 = new com.ad4screen.sdk.d.i$b;
        r1.<init>();
        r0.a(r1);
        return;
    L_0x0060:
        r0 = r1;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ad4screen.sdk.d.i.b():void");
    }

    public final void c() {
        this.b.c();
        this.c.c();
        this.a = null;
    }

    public final void d() {
        this.b.a(true);
    }

    public final void e() {
        this.b.a(false);
    }

    public final k f() {
        return this.c;
    }

    public final boolean g() {
        boolean z = this.b.e() > 0;
        if (z) {
            z = com.ad4screen.sdk.common.i.a(this.d);
            if (!z) {
                this.b.a(0);
                this.b.a(com.ad4screen.sdk.common.g.e().a());
                Log.debug("Session|Entered background");
                f.a().a(new c());
                f.a().a(new b());
            }
        }
        return z;
    }

    public final com.ad4screen.sdk.service.modules.b.a.a h() {
        if (this.a != null) {
            return this.a;
        }
        String a = this.b.a("t", null);
        String a2 = this.b.a("u", null);
        String a3 = this.b.a("v", null);
        String a4 = this.b.a("w", null);
        if (a3 == null || a == null || a2 == null) {
            return null;
        }
        SecretKey secretKeySpec = new SecretKeySpec(com.ad4screen.sdk.common.b.m.c.a(a3, 0), com.ad4screen.sdk.common.i.b());
        return new com.ad4screen.sdk.service.modules.b.a.a(com.ad4screen.sdk.common.i.a(com.ad4screen.sdk.common.b.m.c.a(a4, 0), a, secretKeySpec), com.ad4screen.sdk.common.i.a(com.ad4screen.sdk.common.b.m.c.a(a4, 0), a2, secretKeySpec));
    }

    public final Date i() {
        return this.c.e();
    }

    public final boolean j() {
        return this.b.f();
    }
}
