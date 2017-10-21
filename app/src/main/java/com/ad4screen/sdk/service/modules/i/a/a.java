package com.ad4screen.sdk.service.modules.i.a;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    public String[] a;

    public String[] a(JSONArray jSONArray) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        this.a = new String[jSONArray.length()];
        for (int i = 0; i < this.a.length; i++) {
            this.a[i] = jSONArray.getString(i);
        }
        return this.a;
    }

    public String[] a(JSONObject jSONObject) throws JSONException {
        return a(jSONObject.getJSONArray("permissions"));
    }
}
