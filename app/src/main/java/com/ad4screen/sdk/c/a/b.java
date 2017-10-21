package com.ad4screen.sdk.c.a;

import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;

import org.json.JSONException;
import org.json.JSONObject;

public class b implements c<b>, d {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    private final String g = "com.ad4screen.sdk.model.displayformats.Display";
    private final String h = "title";
    private final String i = "body";
    private final String j = "url";
    private final String k = "template";
    private final String l = "inAnimation";
    private final String m = "outAnimation";

    public b a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.model.displayformats.Display");
        if (!jSONObject.isNull("title")) {
            this.a = jSONObject.getString("title");
        }
        if (!jSONObject.isNull("body")) {
            this.b = jSONObject.getString("body");
        }
        if (!jSONObject.isNull("url")) {
            this.c = jSONObject.getString("url");
        }
        if (!jSONObject.isNull("template")) {
            this.d = jSONObject.getString("template");
        }
        if (!jSONObject.isNull("inAnimation")) {
            this.e = jSONObject.getString("inAnimation");
        }
        if (!jSONObject.isNull("outAnimation")) {
            this.f = jSONObject.getString("outAnimation");
        }
        return this;
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return a(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.model.displayformats.Display";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("title", this.a);
        jSONObject2.put("body", this.b);
        jSONObject2.put("url", this.c);
        jSONObject2.put("template", this.d);
        jSONObject2.put("inAnimation", this.e);
        jSONObject2.put("outAnimation", this.f);
        jSONObject.put("com.ad4screen.sdk.model.displayformats.Display", jSONObject2);
        return jSONObject;
    }
}
