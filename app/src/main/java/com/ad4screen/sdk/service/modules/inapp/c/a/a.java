package com.ad4screen.sdk.service.modules.inapp.c.a;

import android.content.Context;

import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.b;

import java.util.List;

public class a extends b {
    public a(Context context) {
        super(context);
    }

    private boolean a(j jVar) {
        List g = jVar.o().g();
        return (g == null || g.isEmpty()) ? false : true;
    }

    public String a() {
        return "BeaconExclusionCheck";
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return (a(jVar) && a(jVar.o().g())) ? false : true;
    }
}
