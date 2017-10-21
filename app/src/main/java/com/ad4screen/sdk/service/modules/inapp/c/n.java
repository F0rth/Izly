package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class n implements m {
    public String a() {
        return "SessionClickCappingCheck";
    }

    public void a(Context context, k kVar) {
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return jVar.f() == null || jVar.f().intValue() > hVar.f();
    }
}
