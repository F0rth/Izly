package com.ad4screen.sdk.service.modules.k.d;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import org.json.JSONException;
import org.json.JSONObject;

public class b implements c<b>, d {
    public String a;
    public int b;
    private final String c = "com.ad4screen.sdk.service.modules.tracking.model.Event";
    private final String d = "id";
    private final String e = "dispatch";

    public b a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.model.Event");
        if (!jSONObject.isNull("id")) {
            this.a = jSONObject.getString("id");
        }
        this.b = jSONObject.getInt("dispatch");
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.model.Event";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("dispatch", this.b);
        jSONObject2.put("id", this.a);
        jSONObject.put("com.ad4screen.sdk.service.modules.tracking.model.Event", jSONObject2);
        return jSONObject;
    }
}
