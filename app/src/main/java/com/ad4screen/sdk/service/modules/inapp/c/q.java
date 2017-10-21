package com.ad4screen.sdk.service.modules.inapp.c;

import com.ad4screen.sdk.common.a;
import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;

import java.util.Date;
import java.util.List;

public class q extends a {
    public q(a aVar) {
        super(aVar);
    }

    private Date a(long j) {
        return new Date(this.a.b() + j);
    }

    public String a() {
        return "SetAlarmDateRangeCheck";
    }

    public boolean a(e eVar, j jVar, h hVar) {
        if (!a(hVar.a())) {
            return true;
        }
        c cVar = (c) hVar.a();
        cVar.b(a(cVar.f()));
        if (!c(jVar) && !d(jVar)) {
            return true;
        }
        List a = a(jVar);
        List b = b(jVar);
        if (!com.ad4screen.sdk.service.modules.inapp.a.a.c.a(a, cVar.e()) && com.ad4screen.sdk.service.modules.inapp.a.a.c.a(b, cVar.e())) {
            return true;
        }
        Date a2 = com.ad4screen.sdk.service.modules.inapp.a.a.c.a(b, a, cVar.e());
        if (a2 == null) {
            return false;
        }
        cVar.b(a2);
        return true;
    }
}
