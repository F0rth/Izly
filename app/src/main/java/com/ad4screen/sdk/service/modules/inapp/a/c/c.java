package com.ad4screen.sdk.service.modules.inapp.a.c;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public final class c extends a {
    public String a;
    public String b;

    public c(String str, String str2) {
        this.a = str;
        this.b = str2;
    }

    public final /* synthetic */ a a(String str) throws JSONException {
        return b(str);
    }

    public final boolean a(Map<String, c> map) {
        c cVar = (c) map.get(this.a);
        return cVar == null ? false : (this.a == null || this.b == null) ? false : !this.a.equals(cVar.a) ? false : this.b.equalsIgnoreCase(cVar.b);
    }

    public final c b(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.states.State");
        this.a = jSONObject.getString("name");
        this.b = jSONObject.getString("id");
        return this;
    }

    public final /* synthetic */ Object fromJSON(String str) throws JSONException {
        return b(str);
    }

    public final String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.states.State";
    }

    public final JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("name", this.a);
        jSONObject2.put("id", this.b);
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.states.State", jSONObject2);
        return jSONObject;
    }
}
