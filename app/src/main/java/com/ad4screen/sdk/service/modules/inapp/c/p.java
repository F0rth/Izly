package com.ad4screen.sdk.service.modules.inapp.c;

import android.content.Context;

import com.ad4screen.sdk.service.modules.a.a.b;
import com.ad4screen.sdk.service.modules.a.a.c;
import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class p implements m {
    private k a;

    private boolean a(c cVar, List<b> list) {
        if (cVar == null || list == null || list.isEmpty()) {
            return false;
        }
        Set hashSet = new HashSet();
        for (b bVar : list) {
            for (Object add : bVar.a) {
                hashSet.add(add);
            }
        }
        return hashSet.contains(cVar.h);
    }

    private boolean a(h hVar) {
        return hVar.a() instanceof c;
    }

    public String a() {
        return "SetAlarmCancelCheck";
    }

    public void a(Context context, k kVar) {
        this.a = kVar;
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        if (!a(hVar)) {
            return true;
        }
        if (this.a == null) {
            return true;
        }
        return !a((c) hVar.a(), this.a.j());
    }
}
