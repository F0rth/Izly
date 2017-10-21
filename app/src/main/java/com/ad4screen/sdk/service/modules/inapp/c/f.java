package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.a.j.a;
import com.ad4screen.sdk.service.modules.inapp.k;

public class f implements m {
    private boolean a;
    private boolean b;

    public String a() {
        return "ConnectivityCheck";
    }

    public void a(Context context, k kVar) {
        this.a = i.g(context);
        this.b = i.h(context);
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return jVar.h() == a.None || ((jVar.h() != a.Cellular || this.a) && (jVar.h() != a.Wifi || this.b));
    }
}
