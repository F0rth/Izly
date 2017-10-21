package com.ad4screen.sdk.service.modules.inapp.a.b;

import android.text.TextUtils;

import com.ad4screen.sdk.service.modules.inapp.a.b.a.c;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class b {
    private boolean a(List<com.ad4screen.sdk.service.modules.inapp.a.b.b.b> list, HashMap<Long, Integer> hashMap, boolean z, com.ad4screen.sdk.service.modules.inapp.a.b.b.b bVar, Long l) {
        if (hashMap == null) {
            return true;
        }
        Integer valueOf = Integer.valueOf(Collections.frequency(list, bVar));
        Integer num = (Integer) hashMap.get(l);
        if (num != null && num.intValue() >= valueOf.intValue()) {
            return false;
        }
        if (z) {
            hashMap.put(l, valueOf);
        }
        return true;
    }

    protected boolean a(com.ad4screen.sdk.service.modules.inapp.a.b.a.b bVar, List<com.ad4screen.sdk.service.modules.inapp.a.b.b.b> list, HashMap<Long, Integer> hashMap, boolean z) {
        if (bVar.c() == null || bVar.c().isEmpty() || list == null) {
            return false;
        }
        for (a a : bVar.c()) {
            if (!a(a, (List) list, (HashMap) hashMap, z)) {
                return false;
            }
        }
        return true;
    }

    protected boolean a(c cVar, List<com.ad4screen.sdk.service.modules.inapp.a.b.b.b> list, HashMap<Long, Integer> hashMap, boolean z) {
        if (cVar.c() != null) {
            for (a a : cVar.c()) {
                if (a(a, (List) list, (HashMap) hashMap, z)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean a(a aVar, List<com.ad4screen.sdk.service.modules.inapp.a.b.b.b> list, HashMap<Long, Integer> hashMap, boolean z) {
        return aVar instanceof com.ad4screen.sdk.service.modules.inapp.a.b.b.b ? a((com.ad4screen.sdk.service.modules.inapp.a.b.b.b) aVar, (List) list, (HashMap) hashMap, z) : aVar instanceof com.ad4screen.sdk.service.modules.inapp.a.b.b.c ? a((com.ad4screen.sdk.service.modules.inapp.a.b.b.c) aVar, (List) list, (HashMap) hashMap, z) : aVar instanceof com.ad4screen.sdk.service.modules.inapp.a.b.a.b ? a((com.ad4screen.sdk.service.modules.inapp.a.b.a.b) aVar, (List) list, (HashMap) hashMap, z) : aVar instanceof c ? a((c) aVar, (List) list, (HashMap) hashMap, z) : false;
    }

    protected boolean a(com.ad4screen.sdk.service.modules.inapp.a.b.b.b bVar, List<com.ad4screen.sdk.service.modules.inapp.a.b.b.b> list, HashMap<Long, Integer> hashMap, boolean z) {
        if (list == null) {
            return false;
        }
        for (com.ad4screen.sdk.service.modules.inapp.a.b.b.b bVar2 : list) {
            Long a = bVar2.a();
            if (a != null && a.equals(bVar.a())) {
                if (bVar.b() == null) {
                    return a(list, hashMap, z, bVar2, a);
                }
                return bVar.b().equals(bVar2.b()) ? a(list, hashMap, z, bVar2, a) : false;
            }
        }
        return false;
    }

    protected boolean a(com.ad4screen.sdk.service.modules.inapp.a.b.b.c cVar, List<com.ad4screen.sdk.service.modules.inapp.a.b.b.b> list, HashMap<Long, Integer> hashMap, boolean z) {
        if (list == null) {
            return false;
        }
        for (com.ad4screen.sdk.service.modules.inapp.a.b.b.b bVar : list) {
            Long a = bVar.a();
            if (a != null && a.equals(cVar.a())) {
                if (TextUtils.isEmpty(cVar.b())) {
                    return a(list, hashMap, z, bVar, a);
                }
                return cVar.c().matcher(bVar.b()).matches() ? a(list, hashMap, z, bVar, a) : false;
            }
        }
        return false;
    }
}
