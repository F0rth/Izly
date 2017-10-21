package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.service.modules.inapp.a.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.b.a;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class k implements c<k>, d {
    private List<a> a;
    private List<l> b;
    private List<b> c;
    private List<com.ad4screen.sdk.service.modules.inapp.a.c.a> d;
    private List<g> e;
    private List<c> f;
    private List<b> g;

    public k a(String str) throws JSONException {
        Iterator it;
        e eVar = new e();
        JSONObject jSONObject = new JSONObject(str).getJSONObject(getClassKey());
        this.a = new ArrayList();
        if (!jSONObject.isNull("events")) {
            it = ((ArrayList) eVar.a(jSONObject.getJSONObject("events").toString(), new ArrayList())).iterator();
            while (it.hasNext()) {
                this.a.add((a) eVar.a(((JSONObject) it.next()).toString(), new a()));
            }
        }
        this.b = new ArrayList();
        if (!jSONObject.isNull("views")) {
            it = ((ArrayList) eVar.a(jSONObject.getJSONObject("views").toString(), new ArrayList())).iterator();
            while (it.hasNext()) {
                this.b.add((l) eVar.a(((JSONObject) it.next()).toString(), new l()));
            }
        }
        this.d = new ArrayList();
        if (!jSONObject.isNull("states")) {
            it = ((ArrayList) eVar.a(jSONObject.getJSONObject("states").toString(), new ArrayList())).iterator();
            while (it.hasNext()) {
                this.d.add((com.ad4screen.sdk.service.modules.inapp.a.c.a) eVar.a(((JSONObject) it.next()).toString(), new com.ad4screen.sdk.service.modules.inapp.a.c.a()));
            }
        }
        this.c = new ArrayList();
        if (!jSONObject.isNull("dateRanges")) {
            it = ((ArrayList) eVar.a(jSONObject.getJSONObject("dateRanges").toString(), new ArrayList())).iterator();
            while (it.hasNext()) {
                this.c.add((b) eVar.a(((JSONObject) it.next()).toString(), new b()));
            }
        }
        this.e = new ArrayList();
        if (!jSONObject.isNull("locations")) {
            it = ((ArrayList) eVar.a(jSONObject.getJSONObject("locations").toString(), new ArrayList())).iterator();
            while (it.hasNext()) {
                this.e.add((g) eVar.a(((JSONObject) it.next()).toString(), new g()));
            }
        }
        this.f = new ArrayList();
        if (!jSONObject.isNull("geofences")) {
            it = ((ArrayList) eVar.a(jSONObject.getJSONObject("geofences").toString(), new ArrayList())).iterator();
            while (it.hasNext()) {
                this.f.add((c) eVar.a(((JSONObject) it.next()).toString(), new c()));
            }
        }
        this.g = new ArrayList();
        if (!jSONObject.isNull("beacons")) {
            Iterator it2 = ((ArrayList) eVar.a(jSONObject.getJSONObject("beacons").toString(), new ArrayList())).iterator();
            while (it2.hasNext()) {
                this.g.add((b) eVar.a(((JSONObject) it2.next()).toString(), new b()));
            }
        }
        return this;
    }

    public List<g> a() {
        return this.e;
    }

    public void a(List<g> list) {
        this.e = list;
    }

    public List<b> b() {
        return this.c;
    }

    public void b(List<b> list) {
        this.c = list;
    }

    public List<l> c() {
        return this.b;
    }

    public void c(List<l> list) {
        this.b = list;
    }

    public List<com.ad4screen.sdk.service.modules.inapp.a.c.a> d() {
        return this.d;
    }

    public void d(List<com.ad4screen.sdk.service.modules.inapp.a.c.a> list) {
        this.d = list;
    }

    public List<a> e() {
        return this.a;
    }

    public void e(List<a> list) {
        this.a = list;
    }

    public List<c> f() {
        return this.f;
    }

    public void f(List<c> list) {
        this.f = list;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public List<b> g() {
        return this.g;
    }

    public void g(List<b> list) {
        this.g = list;
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.TriggerRule";
    }

    public JSONObject toJSON() throws JSONException {
        e eVar = new e();
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        if (this.a != null) {
            jSONObject2.put("events", eVar.a(this.a));
        }
        if (this.b != null) {
            jSONObject2.put("views", eVar.a(this.b));
        }
        if (this.d != null) {
            jSONObject2.put("states", eVar.a(this.d));
        }
        if (this.c != null) {
            jSONObject2.put("dateRanges", eVar.a(this.c));
        }
        if (this.e != null) {
            jSONObject2.put("locations", eVar.a(this.e));
        }
        if (this.f != null) {
            jSONObject2.put("geofences", eVar.a(this.f));
        }
        if (this.g != null) {
            jSONObject2.put("beacons", eVar.a(this.g));
        }
        jSONObject.put(getClassKey(), jSONObject2);
        return jSONObject;
    }
}
