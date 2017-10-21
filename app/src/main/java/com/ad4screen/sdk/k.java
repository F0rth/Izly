package com.ad4screen.sdk;

import com.ad4screen.sdk.service.modules.k.c.e;

import java.util.ArrayList;
import java.util.List;

class k {
    protected static StaticList a(e eVar) {
        return new StaticList(eVar.b(), eVar.c(), eVar.d(), "OK".equalsIgnoreCase(eVar.a()) ? 2 : 4);
    }

    protected static e a(StaticList staticList) {
        return new e(staticList.getListId(), staticList.getExpireAt());
    }

    protected static List<StaticList> a(List<e> list) {
        List arrayList = new ArrayList();
        for (e a : list) {
            StaticList a2 = a(a);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        return arrayList;
    }

    protected static List<e> b(List<StaticList> list) {
        List arrayList = new ArrayList();
        for (StaticList a : list) {
            e a2 = a(a);
            if (a2 != null) {
                arrayList.add(a2);
            }
        }
        return arrayList;
    }
}
