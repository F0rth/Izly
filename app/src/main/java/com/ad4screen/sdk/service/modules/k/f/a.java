package com.ad4screen.sdk.service.modules.k.f;

import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.service.modules.b.a.b;
import com.ad4screen.sdk.service.modules.b.a.c;
import com.ad4screen.sdk.service.modules.k.a.d;
import com.ad4screen.sdk.service.modules.k.f.e;
import com.ad4screen.sdk.service.modules.k.f.g;

public class a implements com.ad4screen.sdk.service.modules.k.b.a {
    private com.ad4screen.sdk.A4SService.a a;
    private final c b = new c(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a() {
        }

        public void a(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z) {
            Log.debug("Ad4screen|Authentication succeeded");
            this.a.a.b().a(z);
        }
    };
    private g c = new g(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(com.ad4screen.sdk.service.modules.k.d.c cVar) {
            this.a.a.b().a(cVar);
        }

        public void a(String str) {
            new com.ad4screen.sdk.service.modules.k.f.a.a(this.a.a).c();
        }
    };

    public a(com.ad4screen.sdk.A4SService.a aVar) {
        this.a = aVar;
        f.a().a(b.class, this.b);
        f.a().a(com.ad4screen.sdk.service.modules.k.f.a.class, this.c);
        f.a().a(com.ad4screen.sdk.service.modules.k.f.b.class, this.c);
    }

    public String a() {
        return "Ad4Screen";
    }

    public void a(long j, Bundle bundle, String... strArr) {
        Log.debug("Ad4Screen|Tracking event #" + j + " : [ '" + h.a("', '", strArr) + "' ]");
        f.a().a(new e(j, strArr));
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.a.a());
        for (String dVar : strArr) {
            new d(this.a.a(), a, Long.valueOf(j), dVar).run();
        }
    }

    public void a(Cart cart, Bundle bundle) {
        try {
            Log.debug("Ad4Screen|Tracking event #30 : [ '" + new com.ad4screen.sdk.common.c.e().a(cart).toString() + "' ]");
            f.a().a(new e(30, new String[]{r0}));
            new com.ad4screen.sdk.service.modules.k.a.a(this.a.a(), com.ad4screen.sdk.d.b.a(this.a.a()), cart).run();
        } catch (Throwable e) {
            Log.internal("Ad4Screen|Error while serializing Cart to JSON", e);
        }
    }

    public void a(Lead lead, Bundle bundle) {
        try {
            Log.debug("Ad4Screen|Tracking event #10 : [ '" + new com.ad4screen.sdk.common.c.e().a(lead).toString() + "' ]");
            f.a().a(new e(10, new String[]{r0}));
            new com.ad4screen.sdk.service.modules.k.a.b(this.a.a(), com.ad4screen.sdk.d.b.a(this.a.a()), lead).run();
        } catch (Throwable e) {
            Log.internal("Ad4Screen|Error while serializing Lead to JSON", e);
        }
    }

    public void a(Purchase purchase, Bundle bundle) {
        try {
            Log.debug("Ad4Screen|Tracking event #50 : [ '" + new com.ad4screen.sdk.common.c.e().a(purchase).toString() + "' ]");
            f.a().a(new e(50, new String[]{r0}));
            new com.ad4screen.sdk.service.modules.k.a.c(this.a.a(), com.ad4screen.sdk.d.b.a(this.a.a()), purchase).run();
        } catch (Throwable e) {
            Log.internal("Ad4Screen|Error while serializing Purchase to JSON", e);
        }
    }

    public void a(String str) {
        com.ad4screen.sdk.d.b.a(this.a.a()).a(str);
        new com.ad4screen.sdk.service.modules.k.c(this.a.a()).run();
    }

    public int b() {
        return 1;
    }

    public void c() {
        new com.ad4screen.sdk.service.modules.k.g(this.a.a()).run();
    }

    public void d() {
    }

    public void e() {
    }
}
