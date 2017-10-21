package com.ad4screen.sdk.service.modules.a;

import android.content.Context;

import com.ad4screen.sdk.common.c.b;

import org.json.JSONObject;

public class a extends b {
    public a(Context context) {
        super(context, "com.ad4screen.sdk.service.modules.inapp.Alarm");
    }

    public com.ad4screen.sdk.service.modules.a.a.a a() {
        return (com.ad4screen.sdk.service.modules.a.a.a) b("com.ad4screen.Alarms", new com.ad4screen.sdk.service.modules.a.a.a());
    }

    public void a(com.ad4screen.sdk.service.modules.a.a.a aVar) {
        a("com.ad4screen.Alarms", (Object) aVar);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }
}
