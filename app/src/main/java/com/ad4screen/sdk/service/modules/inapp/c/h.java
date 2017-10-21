package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class h implements m {
    private long a;

    public String a() {
        return "DelayCheck";
    }

    public void a(Context context, k kVar) {
        this.a = g.e().a();
    }

    public void a(j jVar, com.ad4screen.sdk.service.modules.inapp.a.h hVar) {
        hVar.c(g.e().a());
    }

    public boolean a(e eVar, j jVar, com.ad4screen.sdk.service.modules.inapp.a.h hVar) {
        return jVar.g() == null || (this.a - hVar.j()) / 1000 >= jVar.g().longValue();
    }
}
