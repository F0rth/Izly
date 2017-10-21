package com.ad4screen.sdk.common.c.a;

import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.c.a.a.a;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class g<T, V> extends a<HashMap<T, V>> {
    private final String a = "java.util.HashMap";
    private final String b = "key";
    private final String c = Lead.KEY_VALUE;

    public String a() {
        return "java.util.HashMap";
    }

    public HashMap<T, V> a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        HashMap<T, V> hashMap = new HashMap();
        JSONArray jSONArray = jSONObject.getJSONArray("java.util.HashMap");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            hashMap.put(jSONObject2.get("key"), jSONObject2.get(Lead.KEY_VALUE));
        }
        return hashMap;
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
