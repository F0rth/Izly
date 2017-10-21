package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import org.json.JSONException;
import org.json.JSONObject;

public class a implements c<a>, d, Comparable<a> {
    public String a;
    public String b;
    public String c;
    public long d;
    public double e;

    public a(String str, String str2, String str3, double d) {
        this.d = System.currentTimeMillis();
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.e = d;
    }

    public int a(a aVar) {
        return this.e > aVar.e ? 1 : -1;
    }

    public a a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.Beacon");
        a aVar = new a(jSONObject.getString("id"), jSONObject.getString("accuracy"), jSONObject.getString("transition"), jSONObject.getDouble("dist"));
        aVar.d = jSONObject.getLong("timestamp");
        return aVar;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return a((a) obj);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.Beacon";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", this.a);
        jSONObject2.put("transition", this.c);
        jSONObject2.put("accuracy", this.b);
        jSONObject2.put("timestamp", this.d);
        jSONObject2.put("dist", this.e);
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.Beacon", jSONObject2);
        return jSONObject;
    }

    public String toString() {
        return "Beacon: " + this.a + ", distance: " + this.e;
    }
}
