package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.service.modules.a.a.b;
import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class l implements m {
    private final a a;
    private long b;
    private long c;

    public l(a aVar) {
        this.a = aVar;
    }

    public String a() {
        return "PressureCheck";
    }

    public void a(Context context, k kVar) {
        this.b = kVar.g();
        this.c = kVar.l();
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        Long m = jVar.m();
        if (!(m == null || (hVar.a() instanceof b))) {
            long a = this.a.a();
            if (hVar.a() instanceof c) {
                if (a - this.c < m.longValue()) {
                    return false;
                }
            } else if (a - this.b < m.longValue()) {
                return false;
            }
        }
        return true;
    }
}
