package com.ad4screen.sdk.service.modules.a.a;

import android.os.Bundle;

import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;

import java.util.Date;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class c extends d {
    private final String a = "com.ad4screen.sdk.model.displayformats.SetAlarm";
    private final String b = "pushPayload";
    private final String c = "date";
    private String d = "nextDisplayDate";
    private final String e = "cancelTrackingUrl";
    private final String f = "allowUpdate";
    private final String g = "updateTime";
    private Bundle p = new Bundle();
    private Date q;
    private Date r;
    private String s;
    private long t;
    private boolean u;
    private Set<String> v;
    private Set<String> w;

    public c a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("pushPayload")) {
            this.p = (Bundle) this.n.a(jSONObject.getJSONObject("pushPayload").toString(), new Bundle());
        }
        if (!jSONObject.isNull("date")) {
            this.r = h.a(jSONObject.getString("date"), a.ISO8601);
        }
        if (!jSONObject.isNull(this.d)) {
            this.q = h.a(jSONObject.getString(this.d), a.ISO8601);
        }
        if (!jSONObject.isNull("cancelTrackingUrl")) {
            this.s = jSONObject.getString("cancelTrackingUrl");
        }
        if (!jSONObject.isNull("allowUpdate")) {
            this.u = jSONObject.getBoolean("allowUpdate");
        }
        if (!jSONObject.isNull("updateTime")) {
            this.t = jSONObject.getLong("updateTime");
        }
        return this;
    }

    public Set<String> a() {
        return this.v;
    }

    public void a(long j) {
        this.t = j;
    }

    public void a(Bundle bundle) {
        this.p = bundle;
    }

    public void a(Date date) {
        this.q = date;
    }

    public void a(Set<String> set) {
        this.v = set;
    }

    public void a(boolean z) {
        this.u = z;
    }

    public /* synthetic */ d b(String str) throws JSONException {
        return a(str);
    }

    public Set<String> b() {
        return this.w;
    }

    public void b(Date date) {
        this.r = date;
    }

    public void b(Set<String> set) {
        this.w = set;
    }

    public Bundle c() {
        return this.p;
    }

    public void c(String str) {
        this.s = str;
    }

    public Date d() {
        return this.q;
    }

    public Date e() {
        return this.r;
    }

    public long f() {
        return this.t;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public boolean g() {
        return this.u;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.model.displayformats.SetAlarm";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.SetAlarm");
        if (this.p != null) {
            toJSON.put("pushPayload", this.n.a(this.p));
        }
        if (this.r != null) {
            toJSON.put("date", h.a(this.r, a.ISO8601));
        }
        if (this.q != null) {
            toJSON.put(this.d, h.a(this.q, a.ISO8601));
        }
        if (this.s != null) {
            toJSON.put("cancelTrackingUrl", this.s);
        }
        toJSON.put("allowUpdate", this.u);
        toJSON.put("updateTime", this.t);
        return toJSON;
    }
}
