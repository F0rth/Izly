package com.ad4screen.sdk.service.modules.inapp.a;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class i {
    public static Double a(String str, JSONObject jSONObject) {
        try {
            return jSONObject.isNull(str) ? null : Double.valueOf(jSONObject.getDouble(str));
        } catch (JSONException e) {
            return null;
        }
    }

    public static void a(JSONObject jSONObject, Set<String> set, String str) throws JSONException {
        if (!jSONObject.isNull(str)) {
            JSONArray jSONArray = jSONObject.getJSONArray(str);
            for (int i = 0; i < jSONArray.length(); i++) {
                set.add(jSONArray.getString(i));
            }
        }
    }

    public static String b(String str, JSONObject jSONObject) {
        try {
            return jSONObject.getString(str);
        } catch (JSONException e) {
            return null;
        }
    }

    public static Boolean c(String str, JSONObject jSONObject) {
        try {
            return jSONObject.isNull(str) ? Boolean.valueOf(false) : Boolean.valueOf(jSONObject.getBoolean(str));
        } catch (JSONException e) {
            return Boolean.valueOf(false);
        }
    }

    public static Integer d(String str, JSONObject jSONObject) {
        try {
            return jSONObject.isNull(str) ? null : Integer.valueOf(jSONObject.getInt(str));
        } catch (JSONException e) {
            return null;
        }
    }
}
