package com.ad4screen.sdk.c.a;

import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

public class f extends d {
    public Intent a;
    private final String b = "com.ad4screen.sdk.model.displayformats.LaunchActivity";
    private final String c = "intent";

    public f a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("intent")) {
            this.a = (Intent) this.n.a(jSONObject.getString("intent"), new Intent());
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
        return "com.ad4screen.sdk.model.displayformats.LaunchActivity";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.LaunchActivity");
        if (this.a != null) {
            toJSON.put("intent", this.n.a(this.a));
        }
        return toJSON;
    }
}
