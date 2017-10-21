package com.ad4screen.sdk.service.modules.inapp.a.a;

import com.ad4screen.sdk.common.c.c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d implements c<d>, com.ad4screen.sdk.common.c.d {
    private g a = g.UNKNOWN;
    private int b = 1;
    private List<Integer> c = new ArrayList();
    private HashMap<e, Integer> d = new HashMap();
    private List<Integer> e = new ArrayList();
    private List<Integer> f = new ArrayList();
    private f g = f.UNKNOWN;
    private long h;

    public d a(String str) throws JSONException {
        JSONArray jSONArray;
        int i;
        int i2 = 0;
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        if (!jSONObject.isNull("frequency")) {
            this.a = g.valueOf(jSONObject.getString("frequency"));
        }
        if (!jSONObject.isNull("interval")) {
            this.b = jSONObject.getInt("interval");
        }
        if (!jSONObject.isNull("byMonthDay")) {
            this.c = new ArrayList();
            jSONArray = jSONObject.getJSONArray("byMonthDay");
            for (i = 0; i < jSONArray.length(); i++) {
                this.c.add(Integer.valueOf(jSONArray.getInt(i)));
            }
        }
        if (!jSONObject.isNull("byDay")) {
            this.d = new HashMap();
            JSONArray jSONArray2 = jSONObject.getJSONArray("byDay");
            for (i = 0; i < jSONArray2.length(); i++) {
                JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                e valueOf = e.valueOf(jSONObject2.getString("recurrenceDay"));
                Object obj = null;
                if (!jSONObject2.isNull("recurrencePrefix")) {
                    obj = Integer.valueOf(jSONObject2.getInt("recurrencePrefix"));
                }
                this.d.put(valueOf, obj);
            }
        }
        if (!jSONObject.isNull("byHour")) {
            this.e = new ArrayList();
            jSONArray = jSONObject.getJSONArray("byHour");
            for (i = 0; i < jSONArray.length(); i++) {
                this.e.add(Integer.valueOf(jSONArray.getInt(i)));
            }
        }
        if (!jSONObject.isNull("byMinute")) {
            this.f = new ArrayList();
            JSONArray jSONArray3 = jSONObject.getJSONArray("byMinute");
            while (i2 < jSONArray3.length()) {
                this.f.add(Integer.valueOf(jSONArray3.getInt(i2)));
                i2++;
            }
        }
        if (!jSONObject.isNull("durationType")) {
            this.g = f.valueOf(jSONObject.getString("durationType"));
        }
        if (!jSONObject.isNull("durationValue")) {
            this.h = jSONObject.getLong("durationValue");
        }
        return this;
    }

    public g a() {
        return this.a;
    }

    public void a(int i) {
        this.b = i;
    }

    public void a(long j) {
        this.h = j;
    }

    public void a(f fVar) {
        this.g = fVar;
    }

    public void a(g gVar) {
        this.a = gVar;
    }

    public void a(HashMap<e, Integer> hashMap) {
        this.d = hashMap;
    }

    public void a(List<Integer> list) {
        this.c = list;
    }

    public int b() {
        return this.b;
    }

    public void b(List<Integer> list) {
        this.e = list;
    }

    public List<Integer> c() {
        return this.c;
    }

    public void c(List<Integer> list) {
        this.f = list;
    }

    public HashMap<e, Integer> d() {
        return this.d;
    }

    public List<Integer> e() {
        return this.e;
    }

    public List<Integer> f() {
        return this.f;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public long g() {
        return this.g.a() * this.h;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.daterange.Recurrence";
    }

    public JSONObject toJSON() throws JSONException {
        JSONArray jSONArray;
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (this.a != null) {
            jSONObject2.put("frequency", this.a.toString());
        }
        jSONObject2.put("interval", this.b);
        if (this.c != null) {
            jSONArray = new JSONArray();
            for (Integer put : this.c) {
                jSONArray.put(put);
            }
            jSONObject2.put("byMonthDay", jSONArray);
        }
        if (this.d != null) {
            JSONArray jSONArray2 = new JSONArray();
            for (Entry entry : this.d.entrySet()) {
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put("recurrenceDay", ((e) entry.getKey()).toString());
                jSONObject3.put("recurrencePrefix", entry.getValue());
                jSONArray2.put(jSONObject3);
            }
            jSONObject2.put("byDay", jSONArray2);
        }
        if (this.e != null) {
            jSONArray = new JSONArray();
            for (Integer put2 : this.e) {
                jSONArray.put(put2);
            }
            jSONObject2.put("byHour", jSONArray);
        }
        if (this.f != null) {
            jSONArray = new JSONArray();
            for (Integer put22 : this.f) {
                jSONArray.put(put22);
            }
            jSONObject2.put("byMinute", jSONArray);
        }
        if (this.g != null) {
            jSONObject2.put("durationType", this.g.toString());
        }
        jSONObject2.put("durationValue", this.h);
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
