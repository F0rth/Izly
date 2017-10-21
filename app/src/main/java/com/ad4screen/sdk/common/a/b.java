package com.ad4screen.sdk.common.a;

import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.g;

public class b {
    public static c a(c cVar, c cVar2) {
        if (cVar == null) {
            return cVar2 != null ? cVar2 : null;
        } else {
            cVar.b = g.e().a();
            return ((cVar.h() & 2) != 0 || cVar2 == null) ? cVar : cVar.b(cVar2);
        }
    }
}
