package com.ad4screen.sdk.service.modules.k.d;

import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c implements com.ad4screen.sdk.common.c.c<c>, d {
    public b[] a;
    private final String b = "com.ad4screen.sdk.service.modules.tracking.model.EventDispatch";
    private final String c = "events";
    private e d = new e();

    public int a(long j) {
        if (this.a == null) {
            return -1;
        }
        for (int i = 0; i < this.a.length; i++) {
            if (this.a[i].a.equals(String.valueOf(j))) {
                return i;
            }
        }
        return -1;
    }

    public c a(String str) throws JSONException {
        JSONArray jSONArray = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.tracking.model.EventDispatch").getJSONArray("events");
        this.a = new b[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            this.a[i] = (b) this.d.a(jSONArray.getString(i), new b());
        }
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.tracking.model.EventDispatch";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (Object a : this.a) {
            jSONArray.put(this.d.a(a));
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("events", jSONArray);
        jSONObject.put("com.ad4screen.sdk.service.modules.tracking.model.EventDispatch", jSONObject2);
        return jSONObject;
    }
}
