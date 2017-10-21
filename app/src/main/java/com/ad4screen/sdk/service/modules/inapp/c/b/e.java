package com.ad4screen.sdk.service.modules.inapp.c.b;

import android.content.Context;
import android.location.Location;

import com.ad4screen.sdk.d.g;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.List;

public class e implements m {
    private Location a;
    private g b;

    public e(g gVar) {
        this.b = gVar;
    }

    private boolean a(Location location, List<com.ad4screen.sdk.service.modules.inapp.a.g> list) {
        for (com.ad4screen.sdk.service.modules.inapp.a.g gVar : list) {
            if (com.ad4screen.sdk.service.modules.inapp.g.a(location, gVar) <= gVar.c() + ((double) location.getAccuracy())) {
                return true;
            }
        }
        return false;
    }

    private boolean a(List<com.ad4screen.sdk.service.modules.inapp.a.g> list) {
        return (list == null || list.isEmpty()) ? false : true;
    }

    public String a() {
        return "LocationInclusionCheck";
    }

    public void a(Context context, k kVar) {
        this.a = this.b.d();
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(com.ad4screen.sdk.service.modules.inapp.a.e eVar, j jVar, h hVar) {
        List a = jVar.n().a();
        if (a(a)) {
            if (this.a == null) {
                return false;
            }
            if (!a(this.a, a)) {
                return false;
            }
        }
        return true;
    }
}
