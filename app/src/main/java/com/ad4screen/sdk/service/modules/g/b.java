package com.ad4screen.sdk.service.modules.g;

import android.content.Context;
import android.location.Location;

import org.json.JSONObject;

public class b extends com.ad4screen.sdk.common.c.b {
    public b(Context context) {
        super(context, "com.ad4screen.sdk.service.modules.location.GeolocationManager");
    }

    public Location a() {
        return (Location) b("lastLocation", new Location("fused"));
    }

    public void a(long j) {
        a("lastGeolocationUpdate", (Object) Long.valueOf(j));
    }

    public void a(JSONObject jSONObject) {
        a("lastLocation", (Object) jSONObject);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 1;
    }

    public long e() {
        return a("lastGeolocationUpdate", 0);
    }
}
