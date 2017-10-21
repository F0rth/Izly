package com.ad4screen.sdk.c.a;

import org.json.JSONException;
import org.json.JSONObject;

public class c extends d {
    public transient String a;
    private final String b = "com.ad4screen.sdk.model.displayformats.File";
    private final String c = "content";

    public c a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("content")) {
            this.a = jSONObject.getString("content");
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
        return "com.ad4screen.sdk.model.displayformats.File";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.File");
        toJSON.put("content", this.a);
        return toJSON;
    }
}
