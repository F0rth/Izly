package com.ad4screen.sdk.common.c.a;

import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.c.a.a.a;

import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e<T, V> extends a<ConcurrentHashMap<T, V>> {
    private final String a = "java.util.concurrent.ConcurrentHashMap";
    private final String b = "key";
    private final String c = Lead.KEY_VALUE;

    public String a() {
        return "java.util.concurrent.ConcurrentHashMap";
    }

    public ConcurrentHashMap<T, V> a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        ConcurrentHashMap<T, V> concurrentHashMap = new ConcurrentHashMap();
        JSONArray jSONArray = jSONObject.getJSONArray("java.util.concurrent.ConcurrentHashMap");
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            concurrentHashMap.put(jSONObject2.get("key"), jSONObject2.get(Lead.KEY_VALUE));
        }
        return concurrentHashMap;
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
