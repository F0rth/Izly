package com.ad4screen.sdk.common.c.a;

import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.c.a.a.b;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class h<T, V> extends b<HashMap<T, V>> {
    private final String a = "java.util.HashMap";
    private final String b = "key";
    private final String c = Lead.KEY_VALUE;

    public JSONObject a(HashMap<T, V> hashMap) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (Object next : hashMap.keySet()) {
            JSONObject jSONObject2 = new JSONObject();
            Object next2;
            if (next2 instanceof d) {
                JSONObject a = new e().a(next2);
                if (a != null) {
                    jSONObject2.put("key", a);
                }
            } else {
                jSONObject2.put("key", next2);
            }
            if (hashMap.get(next2) instanceof d) {
                JSONObject a2 = new e().a(hashMap.get(next2));
                if (a2 != null) {
                    jSONObject2.put(Lead.KEY_VALUE, a2);
                }
            } else {
                next2 = hashMap.get(next2);
                if (next2 != null) {
                    jSONObject2.put(Lead.KEY_VALUE, next2);
                }
            }
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("type", "java.util.HashMap");
        jSONObject.put("java.util.HashMap", jSONArray);
        return jSONObject;
    }
}
