package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.service.modules.a.a.b;
import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.d;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.Date;

public class j implements m {
    private d a;
    private a b;

    public j(a aVar) {
        this.b = aVar;
    }

    private boolean a(com.ad4screen.sdk.c.a.d dVar) {
        return dVar instanceof c;
    }

    private boolean b(com.ad4screen.sdk.c.a.d dVar) {
        return dVar instanceof b;
    }

    public String a() {
        return "GlobalCappingCheck";
    }

    public void a(Context context, k kVar) {
        this.a = kVar.k();
    }

    public void a(com.ad4screen.sdk.service.modules.inapp.a.j jVar, h hVar) {
    }

    public boolean a(e eVar, com.ad4screen.sdk.service.modules.inapp.a.j jVar, h hVar) {
        if (!(jVar.p() || b(hVar.a()))) {
            Date c;
            if (a(hVar.a())) {
                if (eVar.e()) {
                    c = this.b.c();
                    if (this.a.b(new Date(c.getTime() - eVar.c().longValue()), c, true) >= eVar.d().intValue()) {
                        return false;
                    }
                }
            } else if (eVar.f()) {
                c = this.b.c();
                if (this.a.b(new Date(c.getTime() - eVar.a().longValue()), c, false) >= eVar.b().intValue()) {
                    return false;
                }
            }
        }
        return true;
    }
}
