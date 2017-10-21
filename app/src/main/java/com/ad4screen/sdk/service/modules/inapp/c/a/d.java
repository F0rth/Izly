package com.ad4screen.sdk.service.modules.inapp.c.a;

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
        return "InAppDateRangeExclusionCheck";
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return (!a(hVar.a()) && c(jVar) && c.a(a(jVar), this.a.c())) ? false : true;
    }
}
