package com.ad4screen.sdk.common.c.b;

import com.ad4screen.sdk.common.c.e;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

public final class a {
    public static boolean a(JSONObject jSONObject, String str) throws JSONException {
        HashMap hashMap = (HashMap) new e().a(jSONObject.getString(str), new HashMap());
        jSONObject.remove(str);
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.putAll(hashMap);
        jSONObject.put(str, new e().a(concurrentHashMap));
        return true;
    }
}
