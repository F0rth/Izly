package com.ad4screen.sdk.service.modules.inapp.a.b.b;

import com.ad4screen.sdk.analytics.Lead;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class a extends com.ad4screen.sdk.service.modules.inapp.a.b.a {
    protected Long a;
    protected String b;

    protected a() {
    }

    protected a(Long l, String str) {
        this.a = l;
        this.b = str;
    }

    public /* synthetic */ com.ad4screen.sdk.service.modules.inapp.a.b.a a(String str) throws JSONException {
        return b(str);
    }

    public Long a() {
        return this.a;
    }

    public a b(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        this.a = Long.valueOf(jSONObject.getLong("code"));
        if (!jSONObject.isNull(Lead.KEY_VALUE)) {
            this.b = jSONObject.getString(Lead.KEY_VALUE);
        }
        return this;
    }

    public String b() {
        return this.b;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            a aVar = (a) obj;
            if (this.a == null) {
                if (aVar.a != null) {
                    return false;
                }
            } else if (!this.a.equals(aVar.a)) {
                return false;
            }
            if (this.b == null) {
                if (aVar.b != null) {
                    return false;
                }
            } else if (!this.b.equals(aVar.b)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return b(str);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.a == null ? 0 : this.a.hashCode();
        if (this.b != null) {
            i = this.b.hashCode();
        }
        return ((hashCode + 31) * 31) + i;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("code", this.a);
        jSONObject2.put(Lead.KEY_VALUE, this.b);
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
