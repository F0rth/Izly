package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b implements c<b>, d {
    private Set<String> a = new HashSet();
    private Set<String> b = new HashSet();
    private Set<String> c = new HashSet();
    private Set<String> d = new HashSet();
    private Set<String> e = new HashSet();
    private String f;
    private String g;

    public String a() {
        return this.f;
    }

    public void a(String str) {
        this.f = str;
    }

    public void a(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.a, "ids");
    }

    public String b() {
        return this.g;
    }

    public void b(String str) {
        this.g = str;
    }

    public void b(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.b, "groupIds");
    }

    public b c(String str) throws JSONException {
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
        this.f = jSONObject.getString("transition");
        if (!jSONObject.isNull("accuracy")) {
            this.g = jSONObject.getString("accuracy");
        }
        return this;
    }

    public Set<String> c() {
        return this.a;
    }

    public void c(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.c, "externalIds");
    }

    public Set<String> d() {
        return this.b;
    }

    public void d(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.d, "persoIds");
    }

    public Set<String> e() {
        return this.c;
    }

    public void e(JSONObject jSONObject) throws JSONException {
        i.a(jSONObject, this.e, "persoExternalIds");
    }

    public Set<String> f() {
        return this.d;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return c(str);
    }

    public Set<String> g() {
        return this.e;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.BeaconRule";
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
        jSONObject2.put("transition", this.f);
        jSONObject2.put("accuracy", this.g);
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }

    public String toString() {
        return "BeaconRule ids: " + this.a + ", groups: " + this.b;
    }
}
