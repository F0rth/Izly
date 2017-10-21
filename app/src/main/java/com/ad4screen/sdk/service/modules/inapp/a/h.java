package com.ad4screen.sdk.service.modules.inapp.a;

import android.annotation.SuppressLint;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

public class h implements c<h>, d {
    private e a = new e();
    private com.ad4screen.sdk.c.a.d b;
    private int c;
    private int d;
    private int e;
    private int f;
    private long g;
    private long h;
    private long i;
    private HashMap<Long, Integer> j = new HashMap();
    private HashMap<Long, Integer> k = new HashMap();

    @SuppressLint({"UseSparseArrays"})
    private HashMap<Long, Integer> b(String str) throws JSONException {
        HashMap<Long, Integer> hashMap = new HashMap();
        HashMap hashMap2 = (HashMap) this.a.a(str, new HashMap());
        if (hashMap2 != null) {
            for (Entry entry : hashMap2.entrySet()) {
                hashMap.put(Long.valueOf((long) ((Integer) entry.getKey()).intValue()), entry.getValue());
            }
        }
        return hashMap;
    }

    public com.ad4screen.sdk.c.a.d a() {
        return this.b;
    }

    public h a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.Message");
        this.c = jSONObject.getInt("displayCount");
        this.d = jSONObject.getInt("sessionDisplayCount");
        this.e = jSONObject.getInt("clickCount");
        this.f = jSONObject.getInt("sessionClickCount");
        this.g = jSONObject.getLong("lastRulesValid");
        this.h = jSONObject.getLong("sessionLastRulesValid");
        this.i = jSONObject.getLong("lastDisplayTime");
        if (!jSONObject.isNull("format")) {
            this.b = (com.ad4screen.sdk.c.a.d) this.a.a(jSONObject.getString("format"), new com.ad4screen.sdk.c.a.d());
        }
        if (!jSONObject.isNull("eventsTriggerCount")) {
            this.j = b(jSONObject.getString("eventsTriggerCount"));
        }
        if (!jSONObject.isNull("eventsTriggerExclusionsCount")) {
            this.k = b(jSONObject.getString("eventsTriggerExclusionsCount"));
        }
        return this;
    }

    public void a(int i) {
        this.c = i;
    }

    public void a(long j) {
        this.g = j;
    }

    public void a(com.ad4screen.sdk.c.a.d dVar) {
        this.b = dVar;
    }

    public int b() {
        return this.c;
    }

    public void b(int i) {
        this.d = i;
    }

    public void b(long j) {
        this.h = j;
    }

    public int c() {
        return this.d;
    }

    public void c(int i) {
        this.f = i;
    }

    public void c(long j) {
        this.i = j;
    }

    public int d() {
        return this.e;
    }

    public void e() {
        this.e++;
    }

    public int f() {
        return this.f;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public void g() {
        this.f++;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.Message";
    }

    public long h() {
        return this.g;
    }

    public long i() {
        return this.h;
    }

    public long j() {
        return this.i;
    }

    public HashMap<Long, Integer> k() {
        return this.j;
    }

    public HashMap<Long, Integer> l() {
        return this.k;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("format", this.a.a(this.b));
        jSONObject2.put("lastDisplayTime", this.i);
        jSONObject2.put("displayCount", this.c);
        jSONObject2.put("sessionDisplayCount", this.d);
        jSONObject2.put("clickCount", this.e);
        jSONObject2.put("sessionClickCount", this.f);
        jSONObject2.put("lastRulesValid", this.g);
        jSONObject2.put("sessionLastRulesValid", this.h);
        jSONObject2.put("eventsTriggerCount", this.a.a(this.j));
        jSONObject2.put("eventsTriggerExclusionsCount", this.a.a(this.k));
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.Message", jSONObject2);
        return jSONObject;
    }
}
