package com.ad4screen.sdk.service.modules.k;

import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.d.k;
import com.ad4screen.sdk.service.modules.k.d.c;

public class a {
    com.ad4screen.sdk.service.modules.k.b.a[] a;
    private final k b;

    public a(com.ad4screen.sdk.A4SService.a aVar) {
        this.b = i.a(aVar.a()).f();
        this.a = new com.ad4screen.sdk.service.modules.k.b.a[]{new com.ad4screen.sdk.service.modules.k.f.a(aVar), new com.ad4screen.sdk.service.modules.k.f.a.a(aVar)};
    }

    private c d() {
        return this.b.f();
    }

    public void a() {
        for (com.ad4screen.sdk.service.modules.k.b.a aVar : this.a) {
            Log.info("Dispatcher|Tracking dispatched to " + aVar.a());
            aVar.c();
        }
    }

    public void a(long j, Bundle bundle, String... strArr) {
        int i = 0;
        Log.debug("Dispatcher|Dispatching event #" + j + " : [ '" + h.a("', '", strArr) + "' ]");
        c d = d();
        int a = d.a(j);
        if (a == -1) {
            Log.internal("Dispatcher|No Dispatch rules for this event");
            com.ad4screen.sdk.service.modules.k.b.a[] aVarArr = this.a;
            a = aVarArr.length;
            while (i < a) {
                com.ad4screen.sdk.service.modules.k.b.a aVar = aVarArr[i];
                Log.info("Dispatcher|Event dispatched to " + aVar.a());
                aVar.a(j, bundle, strArr);
                i++;
            }
            return;
        }
        int i2 = d.a[a].b;
        com.ad4screen.sdk.service.modules.k.b.a[] aVarArr2 = this.a;
        int length = aVarArr2.length;
        while (i < length) {
            com.ad4screen.sdk.service.modules.k.b.a aVar2 = aVarArr2[i];
            int b = aVar2.b();
            if ((i2 & b) == b) {
                Log.info("Dispatcher|Event dispatched to " + aVar2.a());
                aVar2.a(j, bundle, strArr);
            }
            i++;
        }
    }

    public void a(Cart cart, Bundle bundle) {
        int i = 0;
        try {
            Log.debug("Dispatcher|Dispatching add to cart #30 : [ '" + h.a("', '", new e().a(cart).toString()) + "' ]");
        } catch (Throwable e) {
            Log.internal("Dispatcher|Error while serializing Cart to JSON", e);
        }
        c d = d();
        int a = d.a(30);
        if (a == -1) {
            Log.internal("Dispatcher|No Dispatch rules for this event");
            com.ad4screen.sdk.service.modules.k.b.a[] aVarArr = this.a;
            a = aVarArr.length;
            while (i < a) {
                com.ad4screen.sdk.service.modules.k.b.a aVar = aVarArr[i];
                Log.info("Dispatcher|Event dispatched to " + aVar.a());
                aVar.a(cart, bundle);
                i++;
            }
            return;
        }
        a = d.a[a].b;
        for (com.ad4screen.sdk.service.modules.k.b.a aVar2 : this.a) {
            int b = aVar2.b();
            if ((a & b) == b) {
                Log.info("Dispatcher|Add To Cart dispatched to " + aVar2.a());
                aVar2.a(cart, bundle);
            }
        }
    }

    public void a(Lead lead, Bundle bundle) {
        int i = 0;
        try {
            Log.debug("Dispatcher|Dispatching lead #10 : [ '" + h.a("', '", new e().a(lead).toString()) + "' ]");
        } catch (Throwable e) {
            Log.internal("Dispatcher|Error while serializing Lead to JSON", e);
        }
        c d = d();
        int a = d.a(10);
        if (a == -1) {
            Log.internal("Dispatcher|No Dispatch rules for this event");
            com.ad4screen.sdk.service.modules.k.b.a[] aVarArr = this.a;
            a = aVarArr.length;
            while (i < a) {
                com.ad4screen.sdk.service.modules.k.b.a aVar = aVarArr[i];
                Log.info("Dispatcher|Event dispatched to " + aVar.a());
                aVar.a(lead, bundle);
                i++;
            }
            return;
        }
        a = d.a[a].b;
        for (com.ad4screen.sdk.service.modules.k.b.a aVar2 : this.a) {
            int b = aVar2.b();
            if ((a & b) == b) {
                Log.info("Dispatcher|Lead dispatched to " + aVar2.a());
                aVar2.a(lead, bundle);
            }
        }
    }

    public void a(Purchase purchase, Bundle bundle) {
        int i = 0;
        try {
            Log.debug("Dispatcher|Dispatching purchase #50 : [ '" + h.a("', '", new e().a(purchase).toString()) + "' ]");
        } catch (Throwable e) {
            Log.internal("Dispatcher|Error while serializing Purchase to JSON", e);
        }
        c d = d();
        int a = d.a(50);
        if (a == -1) {
            Log.internal("Dispatcher|No Dispatch rules for this event");
            com.ad4screen.sdk.service.modules.k.b.a[] aVarArr = this.a;
            a = aVarArr.length;
            while (i < a) {
                com.ad4screen.sdk.service.modules.k.b.a aVar = aVarArr[i];
                Log.info("Dispatcher|Event dispatched to " + aVar.a());
                aVar.a(purchase, bundle);
                i++;
            }
            return;
        }
        a = d.a[a].b;
        for (com.ad4screen.sdk.service.modules.k.b.a aVar2 : this.a) {
            int b = aVar2.b();
            if ((a & b) == b) {
                Log.info("Dispatcher|Purchase dispatched to " + aVar2.a());
                aVar2.a(purchase, bundle);
            }
        }
    }

    public void a(c cVar) {
        this.b.a(cVar);
    }

    public void a(String str) {
        for (com.ad4screen.sdk.service.modules.k.b.a aVar : this.a) {
            Log.info("Dispatcher|trackReferrer dispatched to " + aVar.a());
            aVar.a(str);
        }
    }

    public void b() {
        for (com.ad4screen.sdk.service.modules.k.b.a aVar : this.a) {
            Log.info("Dispatcher|EnterForeground dispatched to " + aVar.a());
            aVar.d();
        }
    }

    public void c() {
        for (com.ad4screen.sdk.service.modules.k.b.a aVar : this.a) {
            Log.info("Dispatcher|EnterBackground dispatched to " + aVar.a());
            aVar.e();
        }
    }
}
