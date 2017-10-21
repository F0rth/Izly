package com.ad4screen.sdk.common.c.a;

import android.os.Bundle;

import com.ad4screen.sdk.common.c.a.a.b;

import org.json.JSONException;
import org.json.JSONObject;

public class d extends b<Bundle> {
    private final String a = "android.os.Bundle";

    public JSONObject a(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            if (obj instanceof Bundle) {
                jSONObject2.put(str, a(bundle.getBundle(str)));
            } else {
                jSONObject2.put(str, obj);
            }
        }
        jSONObject.put("type", "android.os.Bundle");
        jSONObject.put("android.os.Bundle", jSONObject2);
        return jSONObject;
    }
}
