package com.ad4screen.sdk.service.modules.inapp.a.c.a;

import com.ad4screen.sdk.service.modules.inapp.a.c.c;

import java.util.Map;

public final class a extends c {
    private final String b = "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AllState";

    public final boolean a(Map<String, c> map) {
        for (com.ad4screen.sdk.service.modules.inapp.a.c.a a : this.a) {
            if (!a.a((Map) map)) {
                return false;
            }
        }
        return true;
    }

    public final String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.states.composites.AllState";
    }
}
