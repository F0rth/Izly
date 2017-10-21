package com.ad4screen.sdk.d;

import android.content.Context;

import com.ad4screen.sdk.common.c.b;
import com.ezeeworld.b4s.android.sdk.server.Api2;

import org.json.JSONObject;

public class c extends b {
    public c(Context context) {
        super(context, "com.ad4screen.sdk.common.DeviceInfo");
    }

    public String a() {
        return a("sharedId", null);
    }

    public void a(int i) {
        a("sessionCount", (Object) Integer.valueOf(i));
    }

    public void a(boolean z) {
        a(Api2.HEADER_LOGGING, (Object) Boolean.valueOf(z));
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public void b(int i) {
        a("trackingCount", (Object) Integer.valueOf(i));
    }

    public void b(String str) {
        a("sharedId", (Object) str);
    }

    public void c(String str) {
        a("idfv", (Object) str);
    }

    public void d(String str) {
        a("referrer", (Object) str);
    }

    public String e() {
        return a("idfv", null);
    }

    public void e(String str) {
        a("fb", (Object) str);
    }

    public int f() {
        return a("sessionCount", 0);
    }

    public void f(String str) {
        a("source", (Object) str);
    }

    public int g() {
        return a("trackingCount", 1);
    }

    public void g(String str) {
        a("sourceTimestamp", (Object) str);
    }

    public String h() {
        return a("referrer", null);
    }

    public void h(String str) {
        a("nextReloadWebservices", (Object) str);
    }

    public String i() {
        return a("fb", null);
    }

    public void i(String str) {
        a("lastReloadWebservices", (Object) str);
    }

    public String j() {
        return a("source", null);
    }

    public String k() {
        return a("sourceTimestamp", null);
    }

    public boolean l() {
        return a(Api2.HEADER_LOGGING, false);
    }

    public String m() {
        return a("nextReloadWebservices", null);
    }

    public String n() {
        return a("lastReloadWebservices", null);
    }
}
