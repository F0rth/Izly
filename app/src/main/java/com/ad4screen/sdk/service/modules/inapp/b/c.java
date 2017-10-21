package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.service.modules.inapp.a.b.a;
import com.ad4screen.sdk.service.modules.inapp.a.b.b.b;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private com.ad4screen.sdk.service.modules.inapp.a.b.b.c a(JSONObject jSONObject) throws JSONException {
        return new com.ad4screen.sdk.service.modules.inapp.a.b.b.c(Long.valueOf(jSONObject.getLong("code")), jSONObject.getString(Lead.KEY_VALUE));
    }

    private com.ad4screen.sdk.service.modules.inapp.a.b.a.c b(JSONArray jSONArray) throws JSONException {
        return (jSONArray == null || jSONArray.length() == 0) ? null : new com.ad4screen.sdk.service.modules.inapp.a.b.a.c(a(jSONArray));
    }

    private b b(JSONObject jSONObject) throws JSONException {
        long j = jSONObject.getLong("code");
        String str = null;
        if (!jSONObject.isNull(Lead.KEY_VALUE)) {
            str = jSONObject.getString(Lead.KEY_VALUE);
        }
        return new b(Long.valueOf(j), str);
    }

    private com.ad4screen.sdk.service.modules.inapp.a.b.a.b c(JSONArray jSONArray) throws JSONException {
        return (jSONArray == null || jSONArray.length() == 0) ? null : new com.ad4screen.sdk.service.modules.inapp.a.b.a.b(a(jSONArray));
    }

    private a c(JSONObject jSONObject) throws JSONException {
        return c(jSONObject.getJSONArray("events"));
    }

    private a d(JSONObject jSONObject) throws JSONException {
        return b(jSONObject.getJSONArray("events"));
    }

    public List<a> a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        List<a> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String string = jSONObject.getString("type");
            try {
                Object a;
                if ("regex".equalsIgnoreCase(string)) {
                    a = a(jSONObject);
                } else if ("concrete".equalsIgnoreCase(string)) {
                    a = b(jSONObject);
                } else if ("all".equalsIgnoreCase(string)) {
                    a = c(jSONObject);
                } else if ("any".equalsIgnoreCase(string)) {
                    a = d(jSONObject);
                } else {
                }
                if (a != null) {
                    arrayList.add(a);
                }
            } catch (Throwable e) {
                Log.internal("InAppEventParser|Impossible to parse Event", e);
            }
        }
        return arrayList;
    }
}
