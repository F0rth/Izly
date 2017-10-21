package com.ad4screen.sdk.c.a;

import org.json.JSONException;
import org.json.JSONObject;

public class e extends d {
    public b a = new b();
    public a b = a.Webview;
    private final String c = "com.ad4screen.sdk.model.displayformats.LandingPage";
    private final String d = "openType";
    private final String e = "display";
    private com.ad4screen.sdk.common.c.e f = new com.ad4screen.sdk.common.c.e();

    public enum a {
        Webview,
        System
    }

    public e a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("display")) {
            this.a = (b) this.f.a(jSONObject.getString("display"), new b());
        }
        if (!jSONObject.isNull("openType")) {
            this.b = a.valueOf(jSONObject.getString("openType"));
        }
        return this;
    }

    public /* synthetic */ d b(String str) throws JSONException {
        return a(str);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.model.displayformats.LandingPage";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.LandingPage");
        toJSON.put("openType", this.b.toString());
        if (this.a != null) {
            toJSON.put("display", this.f.a(this.a));
        }
        return toJSON;
    }
}
