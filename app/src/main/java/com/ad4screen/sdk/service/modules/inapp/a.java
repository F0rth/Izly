package com.ad4screen.sdk.service.modules.inapp;

import android.content.Intent;
import android.os.Bundle;

import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.d.i.d;
import com.ad4screen.sdk.d.i.e;
import com.ad4screen.sdk.d.i.g;
import com.ad4screen.sdk.d.i.h;
import com.ad4screen.sdk.d.i.j;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.service.modules.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.f;
import com.ad4screen.sdk.service.modules.inapp.c.k;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.c.n;
import com.ad4screen.sdk.service.modules.inapp.c.o;
import com.ad4screen.sdk.service.modules.inapp.c.p;
import com.ad4screen.sdk.service.modules.inapp.c.q;
import com.ad4screen.sdk.service.modules.inapp.c.r;
import com.ad4screen.sdk.service.modules.inapp.e.l;
import com.ad4screen.sdk.service.modules.k.f.c;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public final class a {
    private final com.ad4screen.sdk.A4SService.a a;
    private final b b;
    private f c;
    private k d;
    private h e;
    private b f;
    private List<m> g;
    private List<m> h;
    private m i;
    private boolean j;
    private int[] k;
    private ArrayList<String> l = new ArrayList();
    private final l m = new l(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        private void a() {
            i a = i.a(this.a.a.a());
            if (!a.g() && a.a() > 300000) {
                this.a.d.b(false);
                this.a.d.b(a);
            }
        }

        private void b() {
            if (i.a(this.a.a.a()).g()) {
                this.a.e.b(500);
            }
        }

        public void a(final f fVar, final boolean z) {
            this.a.a.a(new Runnable(this) {
                final /* synthetic */ AnonymousClass1 c;

                public void run() {
                    this.c.a.a(fVar);
                    if (z) {
                        this.c.a.c(true);
                    }
                    this.c.b();
                }
            });
        }

        public void a(final boolean z) {
            this.a.a.a(new Runnable(this) {
                final /* synthetic */ AnonymousClass1 b;

                public void run() {
                    if (z) {
                        this.b.a();
                        this.b.a.c(true);
                    }
                    this.b.b();
                }
            });
        }
    };
    private final c n = new c(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(long j, String[] strArr) {
            if (this.a.d != null && this.a.d.h() != null && this.a.a != null) {
                this.a.d.h().add(Long.valueOf(j));
                this.a.d.b(i.a(this.a.a.a()));
                this.a.i();
            }
        }
    };
    private final i o = new i(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
            try {
                this.a.a.a(new Runnable(this) {
                    final /* synthetic */ AnonymousClass5 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        Log.debug("InApp|Autocheck rules event raised");
                        this.a.a.h();
                    }
                });
            } catch (Throwable e) {
                Log.error("InApp|Autocheck rules failed", e);
            }
        }
    };
    private final h p = new h(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(final String str) {
            this.a.a.a(new Runnable(this) {
                final /* synthetic */ AnonymousClass6 b;

                public void run() {
                    Log.debug("InApp|Autoclose message was raised, closing inapp #" + str);
                    this.b.a.a(str);
                }
            });
        }
    };
    private final com.ad4screen.sdk.d.a.b q = new com.ad4screen.sdk.d.a.b(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.i();
        }
    };
    private final com.ad4screen.sdk.service.modules.b.a.c r = new com.ad4screen.sdk.service.modules.b.a.c(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
        }

        public void a(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z) {
            Log.debug("InApp|Received sharedId");
            this.a.b(z);
        }
    };
    private final e s = new e(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(String str, String str2, String str3) {
            this.a.d.b(str);
            this.a.d.c(str2);
            Log.debug("InApp|View is now set to : " + str2);
            this.a.d.d(str3);
            this.a.d.b(i.a(this.a.a.a()));
            this.a.l.clear();
        }
    };
    private final i.f t = new i.f(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
            if (this.a.d.c().size() > 0) {
                for (int i = 0; i < this.a.d.c().size(); i++) {
                    this.a.b((String) this.a.d.c().get(i));
                }
            }
        }
    };
    private final h u = new h(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.e.b(500);
        }
    };
    private final g v = new g(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.e.c();
        }
    };
    private final i.i w = new i.i(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.b(true);
        }
    };

    public a(com.ad4screen.sdk.A4SService.a aVar) {
        this.a = aVar;
        this.b = new b(this.a.a());
        this.c = this.b.a();
        this.d = k.a(i.a(this.a.a()));
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.service.modules.k.f.e.class, this.n);
        com.ad4screen.sdk.d.f.a().a(b.class, this.o);
        com.ad4screen.sdk.d.f.a().a(a.class, this.p);
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.d.a.a.class, this.q);
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.d.i.a.class, this.s);
        com.ad4screen.sdk.d.f.a().a(i.b.class, this.t);
        com.ad4screen.sdk.d.f.a().a(i.c.class, this.v);
        com.ad4screen.sdk.d.f.a().a(d.class, this.u);
        com.ad4screen.sdk.d.f.a().a(com.ad4screen.sdk.service.modules.b.a.b.class, this.r);
        com.ad4screen.sdk.d.f.a().a(j.class, this.w);
        com.ad4screen.sdk.d.f.a().a(e.e.class, this.m);
        this.g = new ArrayList(Arrays.asList(new m[]{new k(com.ad4screen.sdk.common.g.e(), this.a.a()), new p(), new com.ad4screen.sdk.service.modules.inapp.c.d(), new com.ad4screen.sdk.service.modules.inapp.c.e(), new com.ad4screen.sdk.service.modules.inapp.c.j(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.c.f(), new n(), new com.ad4screen.sdk.service.modules.inapp.c.h(), new com.ad4screen.sdk.service.modules.inapp.c.l(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.c.b.e(com.ad4screen.sdk.d.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.c.a.e(com.ad4screen.sdk.d.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.c.b.g(), new com.ad4screen.sdk.service.modules.inapp.c.a.g(), new com.ad4screen.sdk.service.modules.inapp.c.b.f(), new com.ad4screen.sdk.service.modules.inapp.c.a.f(), new com.ad4screen.sdk.service.modules.inapp.c.b.b(), new com.ad4screen.sdk.service.modules.inapp.c.a.b(), new com.ad4screen.sdk.service.modules.inapp.c.b.c(this.a.a(), com.ad4screen.sdk.d.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.c.a.c(this.a.a(), com.ad4screen.sdk.d.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.c.b.a(this.a.a()), new com.ad4screen.sdk.service.modules.inapp.c.a.a(this.a.a()), new com.ad4screen.sdk.service.modules.inapp.c.b.d(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.c.a.d(com.ad4screen.sdk.common.g.e()), new q(com.ad4screen.sdk.common.g.e()), new com.ad4screen.sdk.service.modules.inapp.c.g(), new o(com.ad4screen.sdk.common.g.e(), this.a), new r(com.ad4screen.sdk.common.g.e(), this.a), new com.ad4screen.sdk.service.modules.inapp.c.c()}));
        this.h = new ArrayList(Arrays.asList(new m[]{new com.ad4screen.sdk.service.modules.inapp.c.a.e(com.ad4screen.sdk.d.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.c.a.g(), new com.ad4screen.sdk.service.modules.inapp.c.a.f(), new com.ad4screen.sdk.service.modules.inapp.c.a.b(), new com.ad4screen.sdk.service.modules.inapp.c.a.c(this.a.a(), com.ad4screen.sdk.d.a.a(this.a.a())), new com.ad4screen.sdk.service.modules.inapp.c.a.a(this.a.a()), new q(com.ad4screen.sdk.common.g.e())}));
        this.f = b.a(this.a);
        this.e = new h();
        a();
    }

    private void a(com.ad4screen.sdk.c.a.d dVar) {
        if (!(dVar instanceof com.ad4screen.sdk.c.a.e)) {
            com.ad4screen.sdk.service.modules.inapp.a.h a = this.c.a(dVar.h);
            if (a != null) {
                a.c(com.ad4screen.sdk.common.g.e().a());
                a.a(a.b() + 1);
                a.b(a.c() + 1);
                g.a(this.c);
                g();
            }
        }
    }

    private void a(com.ad4screen.sdk.c.a.d dVar, com.ad4screen.sdk.service.modules.inapp.a.j jVar) {
        if (jVar != null && jVar.q()) {
            this.d.k().a(dVar.h, com.ad4screen.sdk.common.g.e().c(), dVar.getClass());
        }
    }

    private void a(com.ad4screen.sdk.c.a.d dVar, String str) {
        a(dVar, str, null);
    }

    private void a(com.ad4screen.sdk.c.a.d dVar, String str, String str2) {
        if (str != null && dVar != null) {
            Intent intent = new Intent(str);
            intent.addCategory(Constants.CATEGORY_INAPP_NOTIFICATIONS);
            if (dVar instanceof com.ad4screen.sdk.c.a.a) {
                com.ad4screen.sdk.c.a.a aVar = (com.ad4screen.sdk.c.a.a) dVar;
                if (Constants.ACTION_DISPLAYED.equals(str)) {
                    g.a(intent, aVar.e);
                }
                if (Constants.ACTION_CLICKED.equals(str)) {
                    g.a(intent, aVar.f);
                }
            } else if (dVar instanceof com.ad4screen.sdk.c.a.g) {
                com.ad4screen.sdk.c.a.g gVar = (com.ad4screen.sdk.c.a.g) dVar;
                if (Constants.ACTION_DISPLAYED.equals(str)) {
                    g.a(intent, gVar.c());
                }
                if (Constants.ACTION_CLICKED.equals(str) && str2 != null) {
                    for (com.ad4screen.sdk.c.a.g.a aVar2 : gVar.d()) {
                        if (str2.equals(aVar2.a())) {
                            g.a(intent, aVar2.e());
                            break;
                        }
                    }
                }
            } else if (dVar instanceof com.ad4screen.sdk.c.a.c) {
                com.ad4screen.sdk.c.a.c cVar = (com.ad4screen.sdk.c.a.c) dVar;
                if (Constants.ACTION_DISPLAYED.equals(str)) {
                    HashMap hashMap = new HashMap();
                    hashMap.put(Constants.EXTRA_FILE_CONTENT, cVar.a);
                    g.a(intent, hashMap);
                }
            }
            a(dVar.h, str, intent);
            com.ad4screen.sdk.common.i.a(this.a.a(), intent);
        }
    }

    protected static void a(com.ad4screen.sdk.service.modules.inapp.a.e eVar, d dVar) {
        if (eVar != null) {
            if (eVar.e()) {
                dVar.a(new Date(com.ad4screen.sdk.common.g.e().b() - eVar.c().longValue()), true);
            } else {
                dVar.a(new Date(0), true);
            }
            if (eVar.f()) {
                dVar.a(new Date(com.ad4screen.sdk.common.g.e().b() - eVar.a().longValue()), false);
                return;
            } else {
                dVar.a(new Date(0), false);
                return;
            }
        }
        dVar.a(new Date(0), true);
        dVar.a(new Date(0), false);
    }

    private void a(f fVar) {
        if (this.c == null || !this.c.equals(fVar)) {
            if (this.c == null) {
                this.c = fVar;
            } else {
                this.c.a(fVar);
            }
            g.a(this.c);
            g();
            Log.debug("InApp|Configuration was updated");
            this.d.b(true);
            this.d.a(com.ad4screen.sdk.common.g.e().c());
            this.d.b(i.a(this.a.a()));
            this.f.a(this.c);
        }
        a(this.c.a(), this.d.k());
        this.e.b(500);
    }

    private void a(String str, String str2, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Set<String> keySet = extras.keySet();
            if (keySet != null) {
                Log.debug("Send Customs Params for InApp " + str + " with action " + str2);
                for (String str3 : keySet) {
                    Log.debug(str3 + " -> " + extras.getString(str3));
                }
            }
        }
    }

    private boolean a(com.ad4screen.sdk.c.a.d dVar, int i, boolean z) {
        if (dVar == null) {
            return false;
        }
        if (this.d.c().contains(dVar.h)) {
            Log.internal("InApp|InApp #" + dVar.h + " is currently displayed, aborting new display calls");
            return false;
        }
        if (z) {
            if (this.l.contains(dVar.h)) {
                this.l.remove(dVar.h);
            } else {
                Log.error("InApp|InApp #" + dVar.h + " is not waiting for manual display (already displayed?) or not allowed to be displayed manually.");
                return false;
            }
        } else if ((dVar instanceof com.ad4screen.sdk.c.a.a) && this.j) {
            if (this.k == null || this.k.length == 0) {
                if (i >= 0) {
                    this.a.f().a(dVar, i);
                    this.l.add(dVar.h);
                    return false;
                }
            } else if (i >= 0) {
                for (int i2 : this.k) {
                    if (i2 == i) {
                        this.a.f().a(dVar, i);
                        this.l.add(dVar.h);
                        return false;
                    }
                }
            }
        }
        if (!(dVar instanceof com.ad4screen.sdk.service.modules.a.a.c) && !(dVar instanceof com.ad4screen.sdk.service.modules.a.a.b)) {
            return this.a.f().a(dVar, this.d.f());
        }
        d(dVar.h);
        return true;
    }

    private void b(boolean z) {
        boolean b = this.d.b();
        HashMap a = this.d.a();
        Date n = this.d.n();
        this.d = this.d.c(i.a(this.a.a()));
        this.d.a(b);
        this.d.a(a);
        this.d.a(n);
        HashMap hashMap = this.c.b;
        for (String str : hashMap.keySet()) {
            ((com.ad4screen.sdk.service.modules.inapp.a.h) hashMap.get(str)).k().clear();
            ((com.ad4screen.sdk.service.modules.inapp.a.h) hashMap.get(str)).l().clear();
            ((com.ad4screen.sdk.service.modules.inapp.a.h) hashMap.get(str)).c(0);
            ((com.ad4screen.sdk.service.modules.inapp.a.h) hashMap.get(str)).b(0);
            ((com.ad4screen.sdk.service.modules.inapp.a.h) hashMap.get(str)).b(0);
        }
        g.a(this.c);
        g();
        if (z) {
            this.d.b(false);
            this.e.c();
            e();
        }
    }

    private void c(boolean z) {
        Log.debug("InApp|Started rules analysis");
        if (com.ad4screen.sdk.d.b.a(this.a.a()).c() == null) {
            Log.debug("InApp|No Shared id, skipping rules analysis");
        } else if (this.c == null) {
            Log.debug("InApp|No configuration provided, skipping rules analysis");
        } else if (!z && !i.a(this.a.a()).g()) {
            Log.debug("InApp|Cannot display inapp in background");
        } else if (this.d.b()) {
            Log.debug("InApp|User locked InApp display. Rules checking skipped");
        } else {
            this.d.a(new ArrayList());
            this.d.c(z);
            g.a(this.c);
            for (m a : this.g) {
                a.a(this.a.a(), this.d);
            }
            for (m a2 : this.h) {
                a2.a(this.a.a(), this.d);
            }
            com.ad4screen.sdk.service.modules.inapp.a.j[] jVarArr = this.c.a;
            int length = jVarArr.length;
            long j = 10000;
            int i = 0;
            while (i < length) {
                long j2;
                com.ad4screen.sdk.service.modules.inapp.a.j jVar = jVarArr[i];
                if (jVar == null || jVar.a() == null) {
                    Log.error("InApp|Current rule is null or does not have any id because of deserialization failure. Current rule skipped");
                    j2 = j;
                } else {
                    com.ad4screen.sdk.service.modules.inapp.a.h a3 = this.c.a(jVar.a());
                    if (a3 == null) {
                        Log.warn("InApp|InApp #" + jVar.a() + " has no associated message. Rule check skipped");
                        j2 = j;
                    } else if (this.d.c().contains(a3.a().h)) {
                        Log.debug("InApp|InApp #" + a3.a().h + " was already displayed. Rules checking skipped for this in-app");
                        j2 = j;
                    } else {
                        if (!z || (a3.a() instanceof com.ad4screen.sdk.service.modules.a.a.c) || (a3.a() instanceof com.ad4screen.sdk.service.modules.a.a.b)) {
                            if (a3.a() instanceof com.ad4screen.sdk.service.modules.a.a.c) {
                                com.ad4screen.sdk.service.modules.a.a.c a4 = this.f.a(((com.ad4screen.sdk.service.modules.a.a.c) a3.a()).h);
                                if (a4 != null) {
                                    if (!a(this.c.a(), this.h, jVar, a3)) {
                                        this.f.a(a4.h, this.d);
                                        j2 = j;
                                    } else if (a4.g()) {
                                        i a5 = i.a(this.a.a());
                                        if (a5.g()) {
                                            if (Long.valueOf(a5.i().getTime()).equals(this.f.a(a4))) {
                                                this.f.b(a4, ((com.ad4screen.sdk.service.modules.a.a.c) a3.a()).e());
                                            }
                                        }
                                        j2 = j;
                                    }
                                }
                            }
                            if (!(this.j && this.l.contains(a3.a().h))) {
                                if (a(this.c.a(), this.g, jVar, a3)) {
                                    Log.debug("InApp|Found a matching message (#" + jVar.a() + ')');
                                    if ((a3.a() instanceof com.ad4screen.sdk.service.modules.a.a.c) || (a3.a() instanceof com.ad4screen.sdk.service.modules.a.a.b)) {
                                        a(a3.a().h, -1);
                                        j2 = j;
                                    } else {
                                        this.a.f().a(a3.a());
                                    }
                                } else {
                                    Long a6 = a(a3, jVar);
                                    if (a6 != null && a6.longValue() < j) {
                                        j2 = a6.longValue();
                                    }
                                }
                            }
                        }
                        j2 = j;
                    }
                }
                i++;
                j = j2;
            }
            if (j < 10000) {
                Log.debug("InApp|New delay before checking rules again : " + (j / 1000) + "s");
                this.e.a(j);
            }
            if (this.c.a.length == 0) {
                Log.debug("InApp|No message found");
            }
        }
    }

    private void h() {
        c(false);
    }

    private void i() {
        this.e.c();
        this.e.b(500);
    }

    public final Long a(com.ad4screen.sdk.service.modules.inapp.a.h hVar, com.ad4screen.sdk.service.modules.inapp.a.j jVar) {
        long longValue;
        long a = com.ad4screen.sdk.common.g.e().a();
        if (!(this.i == null || (this.i instanceof r) || (this.i instanceof o) || (this.i instanceof com.ad4screen.sdk.service.modules.inapp.c.h))) {
            hVar.a(0);
            hVar.b(0);
            g();
        }
        if (hVar.h() > 0 && jVar.k() != null) {
            longValue = jVar.k().longValue() - (a - hVar.h());
            if (longValue > 0) {
                return Long.valueOf(longValue);
            }
        }
        if (hVar.i() > 0 && jVar.l() != null) {
            longValue = jVar.l().longValue() - (a - hVar.i());
            if (longValue > 0) {
                return Long.valueOf(longValue);
            }
        }
        if (jVar.g() != null) {
            a = ((long) jVar.g().intValue()) - (a - hVar.j());
            if (a > 0) {
                return Long.valueOf(a);
            }
        }
        return null;
    }

    public final void a() {
        this.e.b();
    }

    public final void a(Bundle bundle, boolean z) {
        this.e.c();
        new i(this.a.a(), bundle, z).run();
    }

    public final void a(String str) {
        if (this.d.c().contains(str)) {
            Log.debug("InApp|Service closing inapp #" + str);
            this.a.f().a(str, this.d.f());
            this.e.a(str);
        }
    }

    public final void a(String str, int i) {
        com.ad4screen.sdk.service.modules.inapp.a.h a = this.c.a(str);
        if (a != null) {
            if (a(a.a(), i, false)) {
                com.ad4screen.sdk.service.modules.inapp.a.j c = this.c.c(str);
                if (c != null) {
                    for (m a2 : this.g) {
                        a2.a(c, a);
                    }
                }
            }
            this.b.a(this.c);
        }
    }

    public final void a(String str, String str2) {
        if (str == null) {
            Log.error("InApp|Client reported click on null inapp");
            return;
        }
        if (!this.d.c().contains(str)) {
            Log.warn("InApp|Client reported click on inapp #" + str + " but inapp seems to not be displayed");
        }
        com.ad4screen.sdk.c.a.d b = this.c.b(str);
        if (b == null) {
            Log.error("InApp|Could not find format for clicked inapp #" + str);
            return;
        }
        com.ad4screen.sdk.c.a.d dVar;
        String str3;
        Log.debug("InApp|InApp #" + str + " was clicked");
        if (b instanceof com.ad4screen.sdk.c.a.a) {
            dVar = ((com.ad4screen.sdk.c.a.a) b).d;
            str3 = Constants.ACTION_CLICKED;
        } else {
            if ((b instanceof com.ad4screen.sdk.c.a.g) && str2 != null) {
                com.ad4screen.sdk.c.a.g.a[] d = ((com.ad4screen.sdk.c.a.g) b).d();
                int length = d.length;
                int i = 0;
                while (i < length) {
                    com.ad4screen.sdk.c.a.g.a aVar = d[i];
                    if (str2.equals(aVar.a())) {
                        com.ad4screen.sdk.c.a.d d2 = aVar.d();
                        if (d2 == null) {
                            a(b, Constants.ACTION_CLICKED, str2);
                            dVar = d2;
                            str3 = Constants.ACTION_CLOSED;
                        } else {
                            dVar = d2;
                            str3 = Constants.ACTION_CLICKED;
                        }
                    } else {
                        i++;
                    }
                }
            }
            dVar = null;
            str3 = Constants.ACTION_CLICKED;
        }
        if (!Constants.ACTION_CLOSED.equals(str3)) {
            String str4 = str2 != null ? "InApp#" + b.h + "#" + str2 : "InApp#" + b.h;
            com.ad4screen.sdk.d.b.a(this.a.a()).e(str4);
            Log.info("A4S|New source : " + str4);
        }
        if (dVar != null || ((b instanceof com.ad4screen.sdk.c.a.g) && str2 != null)) {
            com.ad4screen.sdk.service.modules.inapp.a.h a = this.c.a(str);
            if (a != null) {
                a.e();
                a.g();
                g();
            }
            a(b.j, b.h, str2, com.ad4screen.sdk.service.modules.d.d.a.CLICK, new com.ad4screen.sdk.common.e[0]);
        } else {
            Log.debug("InApp|InApp #" + str + " click tracking will not be sent because target is null");
        }
        b(str);
        a(b, str3, str2);
        if (dVar != null) {
            a(dVar, -1, false);
        }
    }

    public final void a(String str, String str2, com.ad4screen.sdk.service.modules.d.d.a aVar, boolean z, com.ad4screen.sdk.common.e... eVarArr) {
        a(str, str2, null, aVar, z, eVarArr);
    }

    public final void a(String str, String str2, com.ad4screen.sdk.service.modules.d.d.a aVar, com.ad4screen.sdk.common.e... eVarArr) {
        a(str, str2, null, aVar, eVarArr);
    }

    public final void a(String str, String str2, String str3, com.ad4screen.sdk.service.modules.d.d.a aVar, boolean z, com.ad4screen.sdk.common.e... eVarArr) {
        if (str != null && str2 != null) {
            if (z) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("controlGroup", true);
                com.ad4screen.sdk.service.modules.d.h.a(this.a, str2, str3, aVar, bundle);
                return;
            }
            com.ad4screen.sdk.service.modules.d.h.a(this.a, str2, str3, aVar, null);
        }
    }

    public final void a(String str, String str2, String str3, com.ad4screen.sdk.service.modules.d.d.a aVar, com.ad4screen.sdk.common.e... eVarArr) {
        if (str != null && str2 != null) {
            com.ad4screen.sdk.service.modules.d.h.a(this.a, str2, str3, aVar, null);
        }
    }

    public final void a(boolean z) {
        this.d.a(z);
        this.d.b(i.a(this.a.a()));
        Log.debug("InApp|InApp display is now " + (z ? "" : "un") + "locked");
        if (z) {
            this.e.c();
        } else {
            this.e.b(500);
        }
    }

    public final void a(boolean z, int[] iArr) {
        this.k = new int[iArr.length];
        System.arraycopy(iArr, 0, this.k, 0, iArr.length);
        this.j = z;
    }

    public final boolean a(com.ad4screen.sdk.service.modules.inapp.a.e eVar, List<m> list, com.ad4screen.sdk.service.modules.inapp.a.j jVar, com.ad4screen.sdk.service.modules.inapp.a.h hVar) {
        com.ad4screen.sdk.c.a.d a = hVar.a();
        if (a == null) {
            Log.warn("InApp|InApp #" + jVar.a() + " has no format to display.");
            return false;
        }
        for (m mVar : list) {
            if (mVar.a(eVar, jVar, hVar)) {
                if (mVar instanceof com.ad4screen.sdk.service.modules.inapp.c.b.a) {
                    a.l = null;
                    com.ad4screen.sdk.service.modules.inapp.a.a a_ = ((com.ad4screen.sdk.service.modules.inapp.c.b.a) mVar).a_();
                    if (a_ != null) {
                        a.l = a_.a;
                    }
                }
                if (mVar instanceof com.ad4screen.sdk.service.modules.inapp.c.b.c) {
                    a.m = null;
                    Geofence b = ((com.ad4screen.sdk.service.modules.inapp.c.b.c) mVar).b();
                    if (b != null) {
                        a.m = b.getId();
                    }
                }
            } else {
                this.i = mVar;
                Log.verbose("InApp|Message #" + jVar.a() + " do not match '" + mVar.a() + "'");
                return false;
            }
        }
        this.i = null;
        return true;
    }

    public final void b() {
        this.e.a();
    }

    public final void b(String str) {
        if (str == null) {
            Log.error("InApp|Client reported null inapp was closed");
            return;
        }
        if (this.d.c().contains(str)) {
            Log.debug("InApp|InApp #" + str + " was closed");
        } else {
            Log.error("InApp|Client reported inapp #" + str + " was closed but it can not be found in current opened inapp");
        }
        this.e.a(str);
        this.d.c().remove(str);
        this.d.b(i.a(this.a.a()));
        i();
    }

    public final void b(String str, String str2) {
        if (str == null) {
            Log.debug("InApp|Cannot put state with null name");
            return;
        }
        if (str2 == null) {
            this.d.f(str);
            Log.debug("InApp|State '" + str + "' removed");
        } else {
            com.ad4screen.sdk.service.modules.inapp.a.c.c e = this.d.e(str);
            if (e == null) {
                e = new com.ad4screen.sdk.service.modules.inapp.a.c.c();
                e.a = str;
                this.d.a(str, e);
            }
            e.b = str2;
            Log.debug("InApp|State '" + str + "' is now set to '" + str2 + "'");
        }
        this.d.b(i.a(this.a.a()));
        i();
    }

    public final void c(String str) {
        if (str == null) {
            Log.error("InApp|Client reported null inapp was closed by click");
        } else if (this.d.c().contains(str)) {
            Log.debug("InApp|Client reported inapp #" + str + " was closed by click");
            com.ad4screen.sdk.c.a.d b = this.c.b(str);
            if (b == null) {
                Log.warn("InApp|Could not find inapp with id #" + str + " which was closed by user");
                b(str);
                return;
            }
            a(b.k, b.h, com.ad4screen.sdk.service.modules.d.d.a.CLOSE, new com.ad4screen.sdk.common.e[0]);
            a(b, Constants.ACTION_CLOSED);
            b(str);
        } else {
            Log.error("InApp|Client reported inapp #" + str + " was closed by click but it can not be found in current opened inapp");
        }
    }

    public final boolean c() {
        return this.d.b();
    }

    public final void d() {
        if (this.d.c().size() != 0) {
            for (int i = 0; i < this.d.c().size(); i++) {
                Log.debug("InApp|Service closing inapp #" + ((String) this.d.c().get(i)));
                this.a.f().a((String) this.d.c().get(i), this.d.f());
                this.e.a((String) this.d.c().get(i));
            }
        }
    }

    public final void d(String str) {
        if (str == null) {
            Log.error("InApp|Client reported null inapp was displayed");
            return;
        }
        if (this.d.c().size() > 0 && this.d.c().contains(str)) {
            Log.warn("InApp|Client reported inapp was displayed but inapp #" + str + " was already displayed");
        }
        com.ad4screen.sdk.c.a.d b = this.c.b(str);
        if (b == null) {
            Log.error("InApp|Could not find format for displayed inapp #" + str);
        } else if (b instanceof com.ad4screen.sdk.c.a.h) {
            com.ad4screen.sdk.service.modules.d.h.a(this.a.a(), ((com.ad4screen.sdk.c.a.h) b).a, new com.ad4screen.sdk.common.e[0]);
            a(b);
        } else {
            com.ad4screen.sdk.service.modules.inapp.a.j c = this.c.c(str);
            if (b instanceof com.ad4screen.sdk.c.a.c) {
                com.ad4screen.sdk.c.a.c cVar = (com.ad4screen.sdk.c.a.c) b;
                a(cVar.i, cVar.h, com.ad4screen.sdk.service.modules.d.d.a.DISP, cVar.o, new com.ad4screen.sdk.common.e[0]);
                if (!cVar.o) {
                    a(b, Constants.ACTION_DISPLAYED);
                    a(b);
                }
            } else if (b instanceof com.ad4screen.sdk.service.modules.a.a.c) {
                com.ad4screen.sdk.service.modules.a.a.c a = this.f.a(b.h);
                if (a == null) {
                    this.f.a((com.ad4screen.sdk.service.modules.a.a.c) b, i.a(this.a.a()).i());
                    if (!b.o) {
                        a(b);
                    }
                    a(b, c);
                    this.d.b(com.ad4screen.sdk.common.g.e().a());
                    return;
                }
                Log.verbose("InApp|Alarm #" + b.h + " is already set.");
                if (a.d() != null) {
                    Log.verbose("InApp|Alarm #" + b.h + " will be displayed at " + DateFormat.getDateTimeInstance().format(a.d()));
                }
                g();
            } else if (b instanceof com.ad4screen.sdk.service.modules.a.a.b) {
                this.f.a((com.ad4screen.sdk.service.modules.a.a.b) b, this.d);
                if (!b.o) {
                    a(b);
                }
            } else {
                if (!b.o) {
                    a(b);
                }
                a(b, c);
                a(b, Constants.ACTION_DISPLAYED);
                this.e.a(b);
                if (i.a(this.a.a()).g() || (b instanceof com.ad4screen.sdk.c.a.g)) {
                    Log.debug("InApp|InApp #" + str + " was displayed");
                    this.d.c().add(str);
                    this.d.a(com.ad4screen.sdk.common.g.e().a());
                    this.d.b(i.a(this.a.a()));
                } else {
                    Log.debug("InApp|InApp #" + str + " not displayed because application is in background");
                }
                a(b.i, b.h, com.ad4screen.sdk.service.modules.d.d.a.DISP, b.o, new com.ad4screen.sdk.common.e[0]);
            }
        }
    }

    public final void e() {
        a(null, false);
    }

    public final void e(String str) {
        com.ad4screen.sdk.service.modules.inapp.a.h a = this.c.a(str);
        if (a != null) {
            a.e();
            a.g();
            g();
        }
    }

    public final f f() {
        return this.c;
    }

    public final void f(String str) {
        if (str == null) {
            Log.debug("InApp|Cannot set view with null name");
            return;
        }
        this.d.c(str);
        this.d.b(i.a(this.a.a()));
        Log.debug("InApp|View is now set to : " + str);
        d();
        i();
    }

    public final void g() {
        this.b.a(this.c);
    }

    public final void g(String str) {
        com.ad4screen.sdk.service.modules.inapp.a.h a = this.c.a(str);
        if (a != null) {
            com.ad4screen.sdk.service.modules.inapp.a.j c = this.c.c(str);
            if (c != null) {
                for (m a2 : this.g) {
                    a2.a(this.a.a(), this.d);
                }
                a(this.c.a(), this.g, c, a);
                for (m a22 : this.g) {
                    a22.a(c, a);
                }
                a(a.a(), -1, true);
            }
        }
    }
}
