package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class j implements c<j>, d {
    private String a;
    private Date b;
    private Date c;
    private Integer d;
    private Integer e;
    private Integer f;
    private Integer g;
    private a h = a.None;
    private int i;
    private boolean j;
    private Long k;
    private Long l;
    private Long m;
    private boolean n;
    private boolean o;
    private boolean p;
    private k q = new k();
    private k r = new k();

    public enum a {
        None,
        Cellular,
        Wifi
    }

    public j a(String str) throws JSONException {
        e eVar = new e();
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.Rule");
        if (!jSONObject.isNull("id")) {
            this.a = jSONObject.getString("id");
        }
        if (!jSONObject.isNull("displayOnlyOnceByEvent")) {
            this.j = jSONObject.getBoolean("displayOnlyOnceByEvent");
        }
        if (!jSONObject.isNull("startDate")) {
            this.b = new Date(jSONObject.getLong("startDate"));
        }
        if (!jSONObject.isNull("endDate")) {
            this.c = new Date(jSONObject.getLong("endDate"));
        }
        if (!jSONObject.isNull("capping")) {
            this.d = Integer.valueOf(jSONObject.getInt("capping"));
        }
        if (!jSONObject.isNull("clickCapping")) {
            this.e = Integer.valueOf(jSONObject.getInt("clickCapping"));
        }
        if (!jSONObject.isNull("sessionClickCapping")) {
            this.f = Integer.valueOf(jSONObject.getInt("sessionClickCapping"));
        }
        if (!jSONObject.isNull("delay")) {
            this.g = Integer.valueOf(jSONObject.getInt("delay"));
        }
        if (!jSONObject.isNull("networkRestriction")) {
            this.h = a.valueOf(jSONObject.getString("networkRestriction"));
        }
        if (!jSONObject.isNull("priority")) {
            this.i = jSONObject.getInt("priority");
        }
        if (!jSONObject.isNull("pressureTimer")) {
            this.k = Long.valueOf(jSONObject.getLong("pressureTimer"));
        }
        if (!jSONObject.isNull("timer")) {
            this.l = Long.valueOf(jSONObject.getLong("timer"));
        }
        if (!jSONObject.isNull("sessionTimer")) {
            this.m = Long.valueOf(jSONObject.getLong("sessionTimer"));
        }
        if (!jSONObject.isNull("excludeFromCappingPressure")) {
            this.n = jSONObject.getBoolean("excludeFromCappingPressure");
        }
        if (!jSONObject.isNull("countInGlobalCapping")) {
            this.o = jSONObject.getBoolean("countInGlobalCapping");
        }
        if (!jSONObject.isNull("offlineDisplay")) {
            this.p = jSONObject.getBoolean("offlineDisplay");
        }
        if (!jSONObject.isNull("inclusions")) {
            this.q = (k) eVar.a(jSONObject.getJSONObject("inclusions").toString(), new k());
        }
        if (!jSONObject.isNull("exclusions")) {
            this.r = (k) eVar.a(jSONObject.getJSONObject("exclusions").toString(), new k());
        }
        return this;
    }

    public String a() {
        return this.a;
    }

    public void a(int i) {
        this.i = i;
    }

    public void a(a aVar) {
        this.h = aVar;
    }

    public void a(k kVar) {
        if (kVar == null) {
            this.q = new k();
        } else {
            this.q = kVar;
        }
    }

    public void a(Integer num) {
        this.d = num;
    }

    public void a(Long l) {
        this.l = l;
    }

    public void a(Date date) {
        this.b = date;
    }

    public void a(boolean z) {
        this.j = z;
    }

    public Date b() {
        return this.b;
    }

    public void b(k kVar) {
        if (kVar == null) {
            this.r = new k();
        } else {
            this.r = kVar;
        }
    }

    public void b(Integer num) {
        this.e = num;
    }

    public void b(Long l) {
        this.m = l;
    }

    public void b(String str) {
        this.a = str;
    }

    public void b(Date date) {
        this.c = date;
    }

    public void b(boolean z) {
        this.n = z;
    }

    public Date c() {
        return this.c;
    }

    public void c(Integer num) {
        this.f = num;
    }

    public void c(Long l) {
        this.k = l;
    }

    public void c(boolean z) {
        this.o = z;
    }

    public Integer d() {
        return this.d;
    }

    public void d(Integer num) {
        this.g = num;
    }

    public void d(boolean z) {
        this.p = z;
    }

    public Integer e() {
        return this.e;
    }

    public Integer f() {
        return this.f;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public Integer g() {
        return this.g;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.Rule";
    }

    public a h() {
        return this.h;
    }

    public int i() {
        return this.i;
    }

    public boolean j() {
        return this.j;
    }

    public Long k() {
        return this.l;
    }

    public Long l() {
        return this.m;
    }

    public Long m() {
        return this.k;
    }

    public k n() {
        return this.q;
    }

    public k o() {
        return this.r;
    }

    public boolean p() {
        return this.n;
    }

    public boolean q() {
        return this.o;
    }

    public boolean r() {
        return this.p;
    }

    public JSONObject toJSON() throws JSONException {
        e eVar = new e();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("id", this.a);
        if (this.b != null) {
            jSONObject2.put("startDate", this.b.getTime());
        }
        if (this.c != null) {
            jSONObject2.put("endDate", this.c.getTime());
        }
        jSONObject2.put("capping", this.d);
        jSONObject2.put("clickCapping", this.e);
        jSONObject2.put("sessionClickCapping", this.f);
        jSONObject2.put("delay", this.g);
        jSONObject2.put("networkRestriction", this.h.toString());
        jSONObject2.put("priority", this.i);
        jSONObject2.put("displayOnlyOnceByEvent", this.j);
        jSONObject2.put("timer", this.l);
        jSONObject2.put("sessionTimer", this.m);
        jSONObject2.put("pressureTimer", this.k);
        jSONObject2.put("excludeFromCappingPressure", this.n);
        jSONObject2.put("countInGlobalCapping", this.o);
        jSONObject2.put("offlineDisplay", this.p);
        jSONObject2.put("inclusions", eVar.a(this.q));
        jSONObject2.put("exclusions", eVar.a(this.r));
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.Rule", jSONObject2);
        return jSONObject;
    }
}
