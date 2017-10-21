package com.ad4screen.sdk.c.a;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

public class a extends d {
    public Integer a;
    public boolean b;
    public b c = new b();
    public d d;
    public HashMap<String, String> e = new HashMap();
    public HashMap<String, String> f = new HashMap();
    public boolean g = true;
    private final String p = "com.ad4screen.sdk.model.displayformats.Banner";
    private final String q = "autoClose";
    private final String r = "isFullScreen";
    private final String s = "display";
    private final String t = "target";
    private final String u = "overlay";
    private final String v = "displayCustomParams";
    private final String w = "clickCustomParams";

    public a a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("autoClose")) {
            this.a = Integer.valueOf(jSONObject.getInt("autoClose"));
        }
        if (!jSONObject.isNull("isFullScreen")) {
            this.b = jSONObject.getBoolean("isFullScreen");
        }
        if (!jSONObject.isNull("overlay")) {
            this.g = jSONObject.getBoolean("overlay");
        }
        if (!jSONObject.isNull("display")) {
            this.c = (b) this.n.a(jSONObject.getString("display"), this.c);
        }
        if (!jSONObject.isNull("target")) {
            this.d = (d) this.n.a(jSONObject.getString("target"), new d());
        }
        this.e = (HashMap) this.n.a(jSONObject.getJSONObject("displayCustomParams").toString(), new HashMap());
        this.f = (HashMap) this.n.a(jSONObject.getJSONObject("clickCustomParams").toString(), new HashMap());
        return this;
    }

    public /* synthetic */ d b(String str) throws JSONException {
        return a(str);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.model.displayformats.Banner";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.Banner");
        toJSON.put("autoClose", this.a);
        toJSON.put("isFullScreen", this.b);
        toJSON.put("overlay", this.g);
        if (this.c != null) {
            toJSON.put("display", this.n.a(this.c));
        }
        if (this.d != null) {
            toJSON.put("target", this.n.a(this.d));
        }
        toJSON.put("displayCustomParams", this.n.a(this.e));
        toJSON.put("clickCustomParams", this.n.a(this.f));
        return toJSON;
    }
}
