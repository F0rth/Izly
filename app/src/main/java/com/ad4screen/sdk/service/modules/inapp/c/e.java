package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class e implements m {
    public String a() {
        return "ClickCappingCheck";
    }

    public void a(Context context, k kVar) {
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(com.ad4screen.sdk.service.modules.inapp.a.e eVar, j jVar, h hVar) {
        return jVar.e() == null || jVar.e().intValue() > hVar.d();
    }
}
