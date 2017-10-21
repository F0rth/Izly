package com.ad4screen.sdk.service.modules.a.a;

import com.ad4screen.sdk.c.a.d;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b extends d {
    public String[] a;
    private final String b = "com.ad4screen.sdk.model.displayformats.CancelAlarm";
    private final String c = "ids";

    public b a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("ids")) {
            JSONArray jSONArray = jSONObject.getJSONArray("ids");
            this.a = new String[jSONArray.length()];
            for (int i = 0; i < jSONArray.length(); i++) {
                this.a[i] = jSONArray.getString(i);
            }
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
        return "com.ad4screen.sdk.model.displayformats.CancelAlarm";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        toJSON.put("type", "com.ad4screen.sdk.model.displayformats.CancelAlarm");
        if (this.a != null) {
            JSONArray jSONArray = new JSONArray();
            for (Object put : this.a) {
                jSONArray.put(put);
            }
            toJSON.put("ids", jSONArray);
        }
        return toJSON;
    }
}
