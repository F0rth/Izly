package com.ad4screen.sdk.c.a;

import org.json.JSONException;
import org.json.JSONObject;

public class h extends d {
    public String a;
    private final String b = "com.ad4screen.sdk.model.displayformats.Webservice";
    private final String c = "url";

    public h a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("url")) {
            this.a = jSONObject.getString("url");
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
        return "com.ad4screen.sdk.model.displayformats.Webservice";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.Webservice");
        toJSON.put("url", this.a);
        return toJSON;
    }
}
