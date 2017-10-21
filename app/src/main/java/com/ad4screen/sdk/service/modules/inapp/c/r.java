package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class r implements m {
    private a a;
    private A4SService.a b;

    public r(a aVar, A4SService.a aVar2) {
        this.a = aVar;
        this.b = aVar2;
    }

    public String a() {
        return "TimerCheck";
    }

    public void a(Context context, k kVar) {
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        Long k = jVar.k();
        long a = this.a.a();
        if (k == null) {
            return true;
        }
        if (hVar.h() == 0) {
            hVar.a(a);
            this.b.d().g();
        } else if (a - hVar.h() >= k.longValue()) {
            return true;
        }
        return false;
    }
}
