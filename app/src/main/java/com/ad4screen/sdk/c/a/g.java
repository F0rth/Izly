package com.ad4screen.sdk.c.a;

import com.ad4screen.sdk.common.c.a.h;
import com.ad4screen.sdk.common.c.e;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class g extends d {
    private String a;
    private String b;
    private a[] c = new a[0];
    private HashMap<String, String> d = new HashMap();

    public static class a {
        private String a;
        private String b;
        private int c;
        private d d;
        private HashMap<String, String> e = new HashMap();

        public String a() {
            return this.a != null ? new String(this.a) : null;
        }

        public void a(int i) {
            this.c = i;
        }

        public void a(d dVar) {
            this.d = dVar;
        }

        public void a(String str) {
            this.a = new String(str);
        }

        public void a(HashMap<String, String> hashMap) {
            this.e = hashMap;
        }

        public String b() {
            return this.b != null ? new String(this.b) : null;
        }

        public void b(String str) {
            this.b = new String(str);
        }

        public int c() {
            return this.c;
        }

        public d d() {
            return this.d;
        }

        public HashMap<String, String> e() {
            return this.e;
        }
    }

    public String a() {
        return this.a != null ? new String(this.a) : null;
    }

    public void a(String str) {
        this.a = new String(str);
    }

    public void a(HashMap<String, String> hashMap) {
        this.d = hashMap;
    }

    public void a(a[] aVarArr) {
        this.c = aVarArr;
    }

    public /* synthetic */ d b(String str) throws JSONException {
        return d(str);
    }

    public String b() {
        return this.b != null ? new String(this.b) : null;
    }

    public HashMap<String, String> c() {
        return this.d;
    }

    public void c(String str) {
        this.b = new String(str);
    }

    public g d(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("title")) {
            a(jSONObject.getString("title"));
        }
        if (!jSONObject.isNull("body")) {
            c(jSONObject.getString("body"));
        }
        a((HashMap) this.n.a(jSONObject.getJSONObject("displayCustomParams").toString(), new HashMap()));
        JSONArray jSONArray = jSONObject.getJSONArray("buttons");
        this.c = new a[jSONArray.length()];
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            this.c[i] = new a();
            if (!jSONObject2.isNull("id")) {
                this.c[i].a(jSONObject2.getString("id"));
            }
            if (!jSONObject2.isNull("title")) {
                this.c[i].b(jSONObject2.getString("title"));
            }
            if (!jSONObject2.isNull("icon")) {
                this.c[i].a(jSONObject2.getInt("icon"));
            }
            if (!jSONObject2.isNull("target")) {
                this.c[i].a((d) this.n.a(jSONObject2.getString("target"), new d()));
            }
            this.c[i].a((HashMap) this.n.a(jSONObject2.getJSONObject("clickCustomParams").toString(), new HashMap()));
        }
        return this;
    }

    public a[] d() {
        return this.c;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return d(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.model.displayformats.Popup";
    }

    public JSONObject toJSON() throws JSONException {
        e eVar = new e();
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.Popup");
        toJSON.put("title", a());
        toJSON.put("body", b());
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < this.c.length; i++) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", this.c[i].a());
            jSONObject.put("title", this.c[i].b());
            jSONObject.put("icon", this.c[i].c());
            if (this.c[i].d() != null) {
                jSONObject.put("target", eVar.a(this.c[i].d()));
            }
            jSONObject.put("clickCustomParams", new h().a(this.c[i].e()));
            jSONArray.put(jSONObject);
        }
        toJSON.put("buttons", jSONArray);
        toJSON.put("displayCustomParams", new h().a(this.d));
        return toJSON;
    }
}
