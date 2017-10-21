package com.ad4screen.sdk.service.modules.k;

import android.content.Context;
import android.os.Bundle;

import com.ad4screen.sdk.common.c.b;

import org.json.JSONObject;

public class e extends b {
    public e(Context context) {
        super(context, "com.ad4screen.sdk.service.modules.tracking.Tracker");
    }

    public long a() {
        return a("lastBeaconsUpdateTimestamp", 0);
    }

    public void a(long j) {
        a("lastUpdateTimestamp", (Object) Long.valueOf(j));
    }

    public void a(Bundle bundle) {
        a("userPreferences", (Object) bundle);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public void b(long j) {
        a("lastBeaconsUpdateTimestamp", (Object) Long.valueOf(j));
    }

    public long e() {
        return a("lastUpdateTimestamp", 0);
    }

    public Bundle f() {
        return (Bundle) b("userPreferences", new Bundle());
    }

    public boolean g() {
        return a("stopped", false);
    }
}
