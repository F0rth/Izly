package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class d implements m {
    public String a() {
        return "CappingCheck";
    }

    public void a(Context context, k kVar) {
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return jVar.d() == null || jVar.d().intValue() > hVar.b();
    }
}
