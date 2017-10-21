package com.ad4screen.sdk.service.modules.inapp.a.c.a;

import com.ad4screen.sdk.service.modules.inapp.a.c.a;
import com.ad4screen.sdk.service.modules.inapp.a.c.c;

import java.util.Map;

public final class b extends c {
    private final String b = "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AnyState";

    public final boolean a(Map<String, c> map) {
        for (a a : this.a) {
            if (a.a((Map) map)) {
                return true;
            }
        }
        return false;
    }

    public final String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AnyState";
    }
}
