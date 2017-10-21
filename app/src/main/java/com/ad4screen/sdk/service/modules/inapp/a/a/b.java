package com.ad4screen.sdk.service.modules.inapp.a.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class b implements c<b>, d {
    private Date a;
    private Date b;
    private d c;
    private boolean d;

    public b a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        if (!jSONObject.isNull("startDate")) {
            this.a = new Date(jSONObject.getLong("startDate"));
        }
        if (!jSONObject.isNull("endDate")) {
            this.b = new Date(jSONObject.getLong("endDate"));
        }
        if (!jSONObject.isNull("isLocal")) {
            this.d = jSONObject.getBoolean("isLocal");
        }
        if (!jSONObject.isNull("recurrence")) {
            this.c = new d().a(jSONObject.getJSONObject("recurrence").toString());
        }
        return this;
    }

    public Date a() {
        return this.a;
    }

    public void a(d dVar) {
        this.c = dVar;
    }

    public void a(Date date) {
        this.a = date;
    }

    public void a(boolean z) {
        this.d = z;
    }

    public Date b() {
        return this.b;
    }

    public void b(Date date) {
        this.b = date;
    }

    public d c() {
        return this.c;
    }

    public boolean d() {
        return this.d;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.DateRange";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (this.a != null) {
            jSONObject2.put("startDate", this.a.getTime());
        }
        if (this.b != null) {
            jSONObject2.put("endDate", this.b.getTime());
        }
        jSONObject2.put("isLocal", this.d);
        if (this.c != null) {
            jSONObject2.put("recurrence", this.c.toJSON());
        }
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
