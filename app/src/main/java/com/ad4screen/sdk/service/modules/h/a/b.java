package com.ad4screen.sdk.service.modules.h.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.g;

import org.json.JSONException;
import org.json.JSONObject;

public class b implements c<b>, d {
    public String a;
    public int b;
    public long c;
    private final String d;
    private final String e;
    private final String f;
    private final String g;

    public b() {
        this.d = "com.ad4screen.sdk.service.modules.member.model.Member";
        this.e = "id";
        this.f = "totalConnections";
        this.g = "lastConnectDate";
    }

    public b(String str) {
        this(str, 1, g.e().a());
    }

    public b(String str, int i, long j) {
        this.d = "com.ad4screen.sdk.service.modules.member.model.Member";
        this.e = "id";
        this.f = "totalConnections";
        this.g = "lastConnectDate";
        this.a = str;
        this.b = i;
        this.c = j;
    }

    public b a(String str) throws JSONException {
        if (str == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.member.model.Member");
        if (!jSONObject.isNull("id")) {
            this.a = jSONObject.getString("id");
        }
        this.b = jSONObject.getInt("totalConnections");
        this.c = jSONObject.getLong("lastConnectDate");
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.member.model.Member";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", this.a);
        jSONObject2.put("lastConnectDate", this.c);
        jSONObject2.put("totalConnections", this.b);
        jSONObject.put("com.ad4screen.sdk.service.modules.member.model.Member", jSONObject2);
        return jSONObject;
    }
}
