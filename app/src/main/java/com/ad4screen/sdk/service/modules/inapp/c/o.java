package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class o implements m {
    private a a;
    private com.ad4screen.sdk.common.a b;

    public o(com.ad4screen.sdk.common.a aVar, a aVar2) {
        this.a = aVar2;
        this.b = aVar;
    }

    public String a() {
        return "SessionTimerCheck";
    }

    public void a(Context context, k kVar) {
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        Long l = jVar.l();
        long a = this.b.a();
        if (l == null) {
            return true;
        }
        if (hVar.i() == 0) {
            hVar.b(a);
            this.a.d().g();
        } else if (a - hVar.i() >= l.longValue()) {
            return true;
        }
        return false;
    }
}
