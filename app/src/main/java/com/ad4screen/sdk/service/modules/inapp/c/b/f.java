package com.ad4screen.sdk.service.modules.inapp.c.b;

import android.content.Context;

import com.ad4screen.sdk.service.modules.inapp.a.c.a;
import com.ad4screen.sdk.service.modules.inapp.a.c.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class f implements m {
    HashMap<String, c> a;

    private boolean a(HashMap<String, c> hashMap, List<a> list) {
        for (a aVar : list) {
            if (aVar != null && aVar.a((Map) hashMap)) {
                return true;
            }
        }
        return false;
    }

    private boolean a(List<a> list) {
        return (list == null || list.isEmpty()) ? false : true;
    }

    public String a() {
        return "StateInclusionCheck";
    }

    public void a(Context context, k kVar) {
        this.a = kVar.i();
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        List d = jVar.n().d();
        return !a(d) || a(this.a, d);
    }
}
