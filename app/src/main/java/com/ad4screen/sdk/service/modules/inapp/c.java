package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c.d;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class c implements com.ad4screen.sdk.common.c.c<c>, d {
    private String a;
    private Date b;
    private Class<?> c;

    public c(String str, Date date, Class<?> cls) {
        this.a = str;
        this.b = date;
        this.c = cls;
    }

    public c a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        this.a = jSONObject.getString("id");
        this.b = new Date(jSONObject.getLong("date"));
        if (!jSONObject.isNull("type")) {
            try {
                this.c = Class.forName(jSONObject.getString("type"));
            } catch (Throwable e) {
                Log.internal("InAppDisplay", e);
            }
        }
        return this;
    }

    public String a() {
        return this.a;
    }

    public Date b() {
        return this.b;
    }

    public boolean c() {
        return com.ad4screen.sdk.service.modules.a.a.c.class.equals(this.c);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            c cVar = (c) obj;
            if (this.b == null) {
                if (cVar.b != null) {
                    return false;
                }
            } else if (!this.b.equals(cVar.b)) {
                return false;
            }
            if (this.a == null) {
                if (cVar.a != null) {
                    return false;
                }
            } else if (!this.a.equals(cVar.a)) {
                return false;
            }
            if (this.c == null) {
                if (cVar.c != null) {
                    return false;
                }
            } else if (!this.c.equals(cVar.c)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.InAppDisplay";
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.b == null ? 0 : this.b.hashCode();
        int hashCode2 = this.a == null ? 0 : this.a.hashCode();
        if (this.c != null) {
            i = this.c.hashCode();
        }
        return ((((hashCode + 31) * 31) + hashCode2) * 31) + i;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", this.a);
        jSONObject2.put("date", this.b.getTime());
        if (this.c != null) {
            jSONObject2.put("type", this.c.getName());
        }
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
