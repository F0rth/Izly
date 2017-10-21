package com.ad4screen.sdk.d;

import android.content.Context;

import com.ad4screen.sdk.common.c.b;

import org.json.JSONObject;

public class j extends b {
    public j(Context context) {
        super(context, "com.ad4screen.sdk.common.Session");
    }

    public long a() {
        return a("Session.lastDisplayTime", 0);
    }

    public void a(int i) {
        a("Session.displayedActivities", (Object) Integer.valueOf(i));
    }

    public void a(long j) {
        a("Session.lastDisplayTime", (Object) Long.valueOf(j));
    }

    public void a(boolean z) {
        a("Session.isInterstitialDisplayed", (Object) Boolean.valueOf(z));
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public int e() {
        return a("Session.displayedActivities", 0);
    }

    public boolean f() {
        return a("Session.isInterstitialDisplayed", false);
    }
}
