package com.ad4screen.sdk.d;

import android.content.Context;

import com.ad4screen.sdk.common.c.b;
import com.ad4screen.sdk.service.modules.k.d.c;

import java.util.Date;

import org.json.JSONObject;

public class k extends b {
    public k(Context context) {
        super(context, "com.ad4screen.sdk.common.Session.Storage");
    }

    public com.ad4screen.sdk.service.modules.inapp.k a() {
        return (com.ad4screen.sdk.service.modules.inapp.k) b("InApp.SessionData", new com.ad4screen.sdk.service.modules.inapp.k());
    }

    public void a(com.ad4screen.sdk.service.modules.inapp.k kVar) {
        a("InApp.SessionData", (Object) kVar);
    }

    public void a(c cVar) {
        a("com.ad4screen.sdk.service.modules.tracking.eventDispatchs", (Object) cVar);
    }

    public void a(Date date) {
        a("sessionStartDate", (Object) Long.valueOf(date.getTime()));
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public Date e() {
        return new Date(a("sessionStartDate", 0));
    }

    public c f() {
        return (c) b("com.ad4screen.sdk.service.modules.tracking.eventDispatchs", new c());
    }
}
