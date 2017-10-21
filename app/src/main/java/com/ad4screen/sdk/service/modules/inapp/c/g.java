package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.Date;

public class g implements m {
    Date a;

    public String a() {
        return "DateIntervalCheck";
    }

    public void a(Context context, k kVar) {
        this.a = new Date();
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        if (hVar.a() instanceof c) {
            this.a = ((c) hVar.a()).e();
        }
        return (jVar.b() == null || !jVar.b().after(this.a)) ? jVar.c() == null || !jVar.c().before(this.a) : false;
    }
}
