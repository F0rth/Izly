package com.ad4screen.sdk.service.modules.inapp.c.b;

import com.ad4screen.sdk.service.modules.inapp.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.a;

public class d extends a {
    public d(com.ad4screen.sdk.common.a aVar) {
        super(aVar);
    }

    public String a() {
        return "InAppDateRangeInclusionCheck";
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return a(hVar.a()) || !d(jVar) || c.a(b(jVar), this.a.c());
    }
}
