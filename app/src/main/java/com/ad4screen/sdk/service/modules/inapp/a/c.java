package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.d;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c implements com.ad4screen.sdk.common.c.c<c>, d {
    private Set<String> a = new HashSet();
    private Set<String> b = new HashSet();
    private Set<String> c = new HashSet();
    private Set<String> d = new HashSet();
    private Set<String> e = new HashSet();
    private d f;

    public c a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        if (!jSONObject.isNull("ids")) {
            a(jSONObject);
        }
        if (!jSONObject.isNull("groupIds")) {
            b(jSONObject);
        }
        if (!jSONObject.isNull("externalIds")) {
            c(jSONObject);
        }
        if (!jSONObject.isNull("persoIds")) {
            d(jSONObject);
        }
        if (!jSONObject.isNull("persoExternalIds")) {
            e(jSONObject);
        }
        if (!jSONObject.isNull("transition")) {
            try {
                this.f = d.a(jSONObject.getString("transition"));
            } catch (IllegalArgumentException e) {
                this.f = null;
            }
        }
        return this;
    }

    public Set<String> a() {
        return this.a;
    }

    public void a(d dVar) {
        this.f = dVar;
    }

    public void a(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.a, "ids");
    }

    public Set<String> b() {
        return this.b;
    }

    public void b(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.b, "groupIds");
    }

    public Set<String> c() {
        return this.c;
    }

    public void c(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.c, "externalIds");
    }

    public Set<String> d() {
        return this.d;
    }

    public void d(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.d, "persoIds");
    }

    public Set<String> e() {
        return this.e;
    }

    public void e(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.e, "persoExternalIds");
    }

    public d f() {
        return this.f;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.GeofenceRule";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (String put : this.a) {
            jSONArray.put(put);
        }
        jSONObject2.put("ids", jSONArray);
        jSONArray = new JSONArray();
        for (String put2 : this.b) {
            jSONArray.put(put2);
        }
        jSONObject2.put("groupIds", jSONArray);
        jSONArray = new JSONArray();
        for (String put22 : this.c) {
            jSONArray.put(put22);
        }
        jSONObject2.put("externalIds", jSONArray);
        jSONArray = new JSONArray();
        for (String put222 : this.d) {
            jSONArray.put(put222);
        }
        jSONObject2.put("persoIds", jSONArray);
        jSONArray = new JSONArray();
        for (String put2222 : this.e) {
            jSONArray.put(put2222);
        }
        jSONObject2.put("persoExternalIds", jSONArray);
        jSONObject2.put("transition", this.f == null ? "" : this.f.a());
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }

    public String toString() {
        return "BeaconRule ids: " + this.a + ", groups: " + this.b;
    }
}
