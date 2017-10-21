package com.ad4screen.sdk.common.c.a;

import android.content.Intent;

import com.ad4screen.sdk.common.c.a.a.b;
import com.ad4screen.sdk.common.c.e;

import org.json.JSONException;
import org.json.JSONObject;

public class j extends b<Intent> {
    private final String a = "android.content.Intent";
    private final String b = "uri";
    private final String c = "extras";

    public JSONObject a(Intent intent) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("uri", intent.toUri(1));
        jSONObject2.put("extras", new e().a(intent.getExtras()));
        jSONObject.put("type", "android.content.Intent");
        jSONObject.put("android.content.Intent", jSONObject2);
        return jSONObject;
    }
}
