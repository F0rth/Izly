package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.service.modules.inapp.a.c.a.a;
import com.ad4screen.sdk.service.modules.inapp.a.c.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.c.c;
import com.ad4screen.sdk.service.modules.inapp.a.i;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f {
    private c a(JSONObject jSONObject) {
        return new c(i.b("name", jSONObject), i.b(Lead.KEY_VALUE, jSONObject));
    }

    private void a(com.ad4screen.sdk.service.modules.inapp.a.c.a.c cVar, JSONArray jSONArray) throws JSONException {
        for (int i = 0; i < jSONArray.length(); i++) {
            cVar.a.add(c(jSONArray.getJSONObject(i)));
        }
    }

    private b b(JSONArray jSONArray) throws JSONException {
        com.ad4screen.sdk.service.modules.inapp.a.c.a.c bVar = new b();
        a(bVar, jSONArray);
        return bVar;
    }

    private com.ad4screen.sdk.service.modules.inapp.a.c.b b(JSONObject jSONObject) {
        String b = i.b("name", jSONObject);
        String b2 = i.b(Lead.KEY_VALUE, jSONObject);
        try {
            return new com.ad4screen.sdk.service.modules.inapp.a.c.b(b, b2);
        } catch (Exception e) {
            Log.error("InApp|Failed to create Pattern from regex while parsing configuration : " + b2);
            return null;
        }
    }

    private a c(JSONArray jSONArray) throws JSONException {
        com.ad4screen.sdk.service.modules.inapp.a.c.a.c aVar = new a();
        a(aVar, jSONArray);
        return aVar;
    }

    private com.ad4screen.sdk.service.modules.inapp.a.c.a c(JSONObject jSONObject) throws JSONException {
        String b = i.b("type", jSONObject);
        return "any".equals(b) ? b(jSONObject.getJSONArray(Lead.KEY_VALUE)) : "all".equals(b) ? c(jSONObject.getJSONArray(Lead.KEY_VALUE)) : "concrete".equals(b) ? a(jSONObject) : "regex".equals(b) ? b(jSONObject) : null;
    }

    public List<com.ad4screen.sdk.service.modules.inapp.a.c.a> a(JSONArray jSONArray) throws JSONException {
        List<com.ad4screen.sdk.service.modules.inapp.a.c.a> arrayList = new ArrayList();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                arrayList.add(c(jSONArray.getJSONObject(i)));
            }
        }
        return arrayList;
    }
}
