package com.ad4screen.sdk.service.modules.push;

import android.content.Context;

import org.json.JSONObject;

public class b extends com.ad4screen.sdk.common.c.b {
    public b(Context context) {
        super(context, "com.ad4screen.sdk.service.modules.push.PushNotification");
    }

    public void a(int i) {
        a("appVersion", (Object) Integer.valueOf(i));
    }

    public void a(boolean z) {
        a("isEnabled", (Object) Boolean.valueOf(z));
    }

    public boolean a() {
        return a("isEnabled", true);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public void b(String str) {
        a("token", (Object) str);
    }

    public void c(String str) {
        a("senderID", (Object) str);
    }

    public String e() {
        return a("token", null);
    }

    public void f() {
        a("token");
    }

    public String g() {
        return a("senderID", null);
    }

    public int h() {
        return a("appVersion", -1);
    }
}
