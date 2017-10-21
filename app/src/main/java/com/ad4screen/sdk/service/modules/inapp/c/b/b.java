package com.ad4screen.sdk.service.modules.inapp.c.b;

import android.content.Context;

import com.ad4screen.sdk.service.modules.inapp.a.b.a;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class b implements m {
    private List<Long> a;

    private boolean a(a aVar, List<Long> list, boolean z, HashMap<Long, Integer> hashMap, boolean z2) {
        List arrayList = new ArrayList();
        for (Long bVar : list) {
            arrayList.add(new com.ad4screen.sdk.service.modules.inapp.a.b.b.b(bVar, null));
        }
        com.ad4screen.sdk.service.modules.inapp.a.b.b bVar2 = new com.ad4screen.sdk.service.modules.inapp.a.b.b();
        return z ? bVar2.a(aVar, arrayList, (HashMap) hashMap, z2) : bVar2.a(aVar, arrayList, null, z2);
    }

    private boolean a(j jVar) {
        List e = jVar.n().e();
        return (e == null || e.isEmpty()) ? false : true;
    }

    private boolean a(j jVar, h hVar, boolean z) {
        if (!a(jVar)) {
            return true;
        }
        List<a> e = jVar.n().e();
        boolean j = jVar.j();
        for (a a : e) {
            if (a(a, this.a, j, hVar.k(), z)) {
                return true;
            }
        }
        return false;
    }

    public String a() {
        return "EventInclusionCheck";
    }

    public void a(Context context, k kVar) {
        this.a = kVar.h();
    }

    public void a(j jVar, h hVar) {
        if (jVar.j()) {
            a(jVar, hVar, true);
        }
    }

    public boolean a(e eVar, j jVar, h hVar) {
        return a(jVar, hVar, false);
    }
}
