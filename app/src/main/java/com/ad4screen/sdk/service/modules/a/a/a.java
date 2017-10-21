package com.ad4screen.sdk.service.modules.a.a;

import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a implements c<a>, d {
    private final String a = "com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig";
    private final String b = "alarms";
    private final String c = "alarmsSessionsStarts";
    private final String d = "key";
    private final String e = Lead.KEY_VALUE;
    private e f = new e();
    private ArrayList<c> g = new ArrayList();
    private HashMap<String, Long> h = new HashMap();

    public a a(String str) throws JSONException {
        int i = 0;
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig");
        JSONArray jSONArray = jSONObject.getJSONArray("alarms");
        this.g = new ArrayList();
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.g.add((c) this.f.a(jSONArray.getString(i2), new com.ad4screen.sdk.c.a.d()));
        }
        if (jSONObject.has("alarmsSessionsStarts")) {
            JSONArray jSONArray2 = jSONObject.getJSONArray("alarmsSessionsStarts");
            this.h = new HashMap();
            while (i < jSONArray2.length()) {
                JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
                this.h.put(jSONObject2.getString("key"), Long.valueOf(jSONObject2.getLong(Lead.KEY_VALUE)));
                i++;
            }
        }
        return this;
    }

    public List<c> a() {
        return this.g;
    }

    public void a(c cVar) {
        this.g.remove(cVar);
        this.h.remove(cVar.h);
    }

    public void a(c cVar, Date date) {
        this.g.add(cVar);
        this.h.put(cVar.h, Long.valueOf(date.getTime()));
    }

    public c b(String str) {
        if (this.g == null) {
            return null;
        }
        int i = 0;
        while (i < this.g.size()) {
            if (((c) this.g.get(i)).h != null && ((c) this.g.get(i)).h.equals(str)) {
                return (c) this.g.get(i);
            }
            i++;
        }
        return null;
    }

    public Long b(c cVar) {
        return (Long) this.h.get(cVar.h);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.g.size(); i++) {
            jSONArray.put(this.f.a(this.g.get(i)));
        }
        jSONObject2.put("alarms", jSONArray);
        jSONArray = new JSONArray();
        for (Entry entry : this.h.entrySet()) {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("key", entry.getKey());
            jSONObject3.put(Lead.KEY_VALUE, entry.getValue());
            jSONArray.put(jSONObject3);
        }
        jSONObject2.put("alarmsSessionsStarts", jSONArray);
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.AlarmConfig", jSONObject2);
        return jSONObject;
    }
}
