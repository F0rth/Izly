package com.ad4screen.sdk.service.modules.inapp.a;

import com.ad4screen.sdk.c.a.a;
import com.ad4screen.sdk.c.a.g;
import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.h;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class f implements c<f>, d {
    public j[] a = new j[0];
    public HashMap<String, h> b = new HashMap();
    private e c = new e();
    private e d = new e();

    public com.ad4screen.sdk.c.a.d a(com.ad4screen.sdk.c.a.d dVar, String str) {
        if (dVar == null) {
            return null;
        }
        if (dVar instanceof a) {
            a aVar = (a) dVar;
            if (str.equals(aVar.d.h)) {
                return aVar.d;
            }
        } else if (dVar instanceof g) {
            g gVar = (g) dVar;
            g.a[] d = gVar.d();
            for (int i = 0; i < d.length; i++) {
                if (str.equals(gVar.h + '#' + i)) {
                    return d[i].d();
                }
            }
        }
        return null;
    }

    public e a() {
        return this.d;
    }

    public h a(String str) {
        if (str == null) {
            return null;
        }
        String[] split = str.split("#");
        return split.length > 1 ? (h) this.b.get(split[0]) : (h) this.b.get(str);
    }

    public void a(e eVar) {
        this.d = eVar;
    }

    public void a(f fVar) {
        this.a = fVar.a;
        HashMap hashMap = new HashMap();
        for (String str : fVar.b.keySet()) {
            h hVar;
            h hVar2 = (h) this.b.get(str);
            if (hVar2 == null) {
                hVar = (h) fVar.b.get(str);
            } else {
                hVar2.a(((h) fVar.b.get(str)).a());
                hVar = hVar2;
            }
            hashMap.put(hVar.a().h, hVar);
        }
        this.b = hashMap;
        this.d = fVar.a();
    }

    public com.ad4screen.sdk.c.a.d b(String str) {
        com.ad4screen.sdk.c.a.d dVar;
        if (str == null) {
            dVar = null;
        } else {
            Object split = str.split("#");
            h hVar;
            if (split.length > 1) {
                hVar = (h) this.b.get(split[0]);
                if (hVar == null) {
                    return null;
                }
                dVar = hVar.a();
                for (int i = 1; i < split.length && dVar != null; i++) {
                    String[] strArr = new String[(i + 1)];
                    System.arraycopy(split, 0, strArr, 0, i + 1);
                    dVar = a(dVar, h.a("#", strArr));
                }
            } else {
                hVar = (h) this.b.get(str);
                return hVar != null ? hVar.a() : null;
            }
        }
        return dVar;
    }

    public j c(String str) {
        if (str != null) {
            for (j jVar : this.a) {
                if (jVar != null && str.equalsIgnoreCase(jVar.a())) {
                    return jVar;
                }
            }
        }
        return null;
    }

    public f d(String str) throws JSONException {
        int i = 0;
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.model.InAppConfig");
        JSONArray jSONArray = jSONObject.getJSONArray("rules");
        this.a = new j[jSONArray.length()];
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            this.a[i2] = (j) this.c.a(jSONArray.getString(i2), new j());
        }
        JSONArray jSONArray2 = jSONObject.getJSONArray("messages");
        while (i < jSONArray2.length()) {
            JSONObject jSONObject2 = jSONArray2.getJSONObject(i);
            this.b.put(jSONObject2.getString("key"), this.c.a(jSONObject2.getString("message"), new h()));
            i++;
        }
        if (jSONObject.has("globalRule")) {
            this.d = (e) this.c.a(jSONObject.getString("globalRule"), new e());
        } else {
            this.d = new e();
        }
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return d(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.model.InAppConfig";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (Object obj : this.a) {
            if (obj != null) {
                jSONArray.put(this.c.a(obj));
            }
        }
        JSONArray jSONArray2 = new JSONArray();
        for (String str : this.b.keySet()) {
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("key", str);
            jSONObject3.put("message", this.c.a(this.b.get(str)));
            jSONArray2.put(jSONObject3);
        }
        jSONObject2.put("rules", jSONArray);
        jSONObject2.put("messages", jSONArray2);
        if (this.d != null) {
            jSONObject2.put("globalRule", this.c.a(this.d));
        }
        jSONObject.put("com.ad4screen.sdk.service.modules.inapp.model.InAppConfig", jSONObject2);
        return jSONObject;
    }
}
