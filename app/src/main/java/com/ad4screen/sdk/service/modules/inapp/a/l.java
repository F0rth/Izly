package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import org.json.JSONException;
import org.json.JSONObject;

public class l implements c<l>, d {
    private String a;

    public l(String str) {
        this.a = str;
    }

    public l a(String str) throws JSONException {
        this.a = new JSONObject(str).getJSONObject(getClassKey()).getString("name");
        return this;
    }

    public String a() {
        return this.a;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.ViewRule";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("name", this.a);
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
