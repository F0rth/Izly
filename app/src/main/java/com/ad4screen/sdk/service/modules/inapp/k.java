package com.ad4screen.sdk.service.modules.inapp;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.service.modules.a.a.b;
import com.ad4screen.sdk.service.modules.inapp.a.a;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class k implements c<k>, d {
    private static HashMap<String, a> n = new HashMap();
    private final String a = "lastSettedAlarmTime";
    private e b = new e();
    private boolean c;
    private ArrayList<String> d = new ArrayList();
    private d e = new d();
    private String f;
    private String g;
    private String h;
    private long i;
    private long j;
    private List<Long> k = new ArrayList();
    private HashMap<String, com.ad4screen.sdk.service.modules.inapp.a.c.c> l = new HashMap();
    private List<b> m = new ArrayList();
    private boolean o;
    private Date p;
    private boolean q;

    public static k a(i iVar) {
        return iVar.f().a();
    }

    private void b(HashMap<String, a> hashMap) {
        for (String str : hashMap.keySet()) {
            if (System.currentTimeMillis() - ((a) hashMap.get(str)).d > 600000) {
                n.remove(str);
            }
        }
    }

    public k a(String str) throws JSONException {
        JSONArray jSONArray;
        int i;
        int i2 = 0;
        JSONObject jSONObject = new JSONObject(str);
        this.c = jSONObject.getBoolean("userDisplayLocked");
        if (!jSONObject.isNull("displayedInApp")) {
            jSONArray = jSONObject.getJSONArray("displayedInApp");
            for (i = 0; i < jSONArray.length(); i++) {
                this.d.add(jSONArray.getString(i));
            }
        }
        if (!jSONObject.isNull("displaysList")) {
            this.e = (d) this.b.a(jSONObject.getString("displaysList"), new d());
        }
        if (!jSONObject.isNull("currentActivityClassPath")) {
            this.f = jSONObject.getString("currentActivityClassPath");
        }
        if (!jSONObject.isNull("currentActivityName")) {
            this.g = jSONObject.getString("currentActivityName");
        }
        if (!jSONObject.isNull("currentActivityInstance")) {
            this.h = jSONObject.getString("currentActivityInstance");
        }
        if (!jSONObject.isNull("lastDisplayedInAppTime")) {
            this.i = jSONObject.getLong("lastDisplayedInAppTime");
        }
        if (!jSONObject.isNull("lastSettedAlarmTime")) {
            this.j = jSONObject.getLong("lastSettedAlarmTime");
        }
        if (!jSONObject.isNull("sessionEvents")) {
            jSONArray = jSONObject.getJSONArray("sessionEvents");
            for (i = 0; i < jSONArray.length(); i++) {
                this.k.add(Long.valueOf(jSONArray.getLong(i)));
            }
        }
        if (!jSONObject.isNull("sessionStates")) {
            jSONArray = jSONObject.getJSONArray("sessionStates");
            for (i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                this.l.put(jSONObject2.getString("key"), this.b.a(jSONObject2.getString("state"), new com.ad4screen.sdk.service.modules.inapp.a.c.c()));
            }
        }
        if (!jSONObject.isNull("sessionBeacons")) {
            JSONArray jSONArray2 = jSONObject.getJSONArray("sessionBeacons");
            while (i2 < jSONArray2.length()) {
                JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                n.put(jSONObject3.getString("key"), this.b.a(jSONObject3.getString("beacon"), new a()));
                i2++;
            }
        }
        if (!jSONObject.isNull("inAppConfigUpdated")) {
            this.o = jSONObject.getBoolean("inAppConfigUpdated");
        }
        if (!jSONObject.isNull("lastInAppConfigUpdate")) {
            this.p = new Date(jSONObject.getLong("lastInAppConfigUpdate"));
        }
        if (!jSONObject.isNull("fromBeaconOrGeofence")) {
            this.q = jSONObject.getBoolean("fromBeaconOrGeofence");
        }
        return this;
    }

    public HashMap<String, a> a() {
        b(new HashMap(n));
        return n;
    }

    public void a(long j) {
        this.i = j;
    }

    public void a(a aVar) {
        if (aVar != null) {
            n.put(aVar.a, aVar);
        }
    }

    public void a(String str, com.ad4screen.sdk.service.modules.inapp.a.c.c cVar) {
        this.l.put(str, cVar);
    }

    public void a(Date date) {
        this.p = date;
    }

    public void a(HashMap<String, a> hashMap) {
        n = new HashMap(hashMap);
        b((HashMap) hashMap);
    }

    public void a(List<b> list) {
        this.m = list;
    }

    public void a(boolean z) {
        this.c = z;
    }

    public void b(long j) {
        this.j = j;
    }

    public void b(i iVar) {
        iVar.f().a(this);
    }

    public void b(String str) {
        this.f = str;
    }

    public void b(boolean z) {
        this.o = z;
    }

    public boolean b() {
        return this.c;
    }

    public k c(i iVar) {
        iVar.f().a(new k());
        return iVar.f().a();
    }

    public ArrayList<String> c() {
        return this.d;
    }

    public void c(String str) {
        this.g = str;
    }

    public void c(boolean z) {
        this.q = z;
    }

    public String d() {
        return this.f;
    }

    public void d(String str) {
        this.h = str;
    }

    public com.ad4screen.sdk.service.modules.inapp.a.c.c e(String str) {
        return (com.ad4screen.sdk.service.modules.inapp.a.c.c) this.l.get(str);
    }

    public String e() {
        return this.g;
    }

    public String f() {
        return this.h;
    }

    public void f(String str) {
        this.l.remove(str);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public long g() {
        return this.i;
    }

    public String getClassKey() {
        return "InApp.SessionData";
    }

    public List<Long> h() {
        return this.k;
    }

    public HashMap<String, com.ad4screen.sdk.service.modules.inapp.a.c.c> i() {
        return this.l;
    }

    public List<b> j() {
        return this.m;
    }

    public d k() {
        return this.e;
    }

    public long l() {
        return this.j;
    }

    public boolean m() {
        return this.o;
    }

    public Date n() {
        return this.p;
    }

    public boolean o() {
        return this.q;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("userDisplayLocked", this.c);
        jSONObject.put("currentActivityClassPath", this.f);
        jSONObject.put("currentActivityName", this.g);
        jSONObject.put("currentActivityInstance", this.h);
        jSONObject.put("lastDisplayedInAppTime", this.i);
        jSONObject.put("lastSettedAlarmTime", this.j);
        JSONArray jSONArray = new JSONArray();
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            jSONArray.put((String) it.next());
        }
        jSONObject.put("displayedInApp", jSONArray);
        jSONObject.put("displaysList", this.b.a(this.e));
        jSONArray = new JSONArray();
        for (Long put : this.k) {
            jSONArray.put(put);
        }
        jSONObject.put("sessionEvents", jSONArray);
        jSONArray = new JSONArray();
        for (String str : this.l.keySet()) {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("key", str);
            jSONObject2.put("state", this.b.a(this.l.get(str)));
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("sessionStates", jSONArray);
        jSONArray = new JSONArray();
        for (String str2 : n.keySet()) {
            jSONObject2 = new JSONObject();
            jSONObject2.put("key", str2);
            jSONObject2.put("beacon", this.b.a(n.get(str2)));
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("sessionBeacons", jSONArray);
        jSONObject.put("inAppConfigUpdated", this.o);
        if (this.p != null) {
            jSONObject.put("lastInAppConfigUpdate", this.p.getTime());
        }
        jSONObject.put("fromBeaconOrGeofence", this.q);
        return jSONObject;
    }
}
