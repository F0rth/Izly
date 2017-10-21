package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.a.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

public class c implements m {
    private k a;

    private boolean a(h hVar) {
        return hVar.a() instanceof b;
    }

    public String a() {
        return "CancelAlarmChecker";
    }

    public void a(Context context, k kVar) {
        this.a = kVar;
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        if (a(hVar)) {
            this.a.j().add((b) hVar.a());
        }
        return true;
    }
}
