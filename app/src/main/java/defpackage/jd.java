package defpackage;

import org.json.JSONObject;

public final class jd {
    public static String a(JSONObject jSONObject, String str) {
        if (jSONObject.isNull(str)) {
            return "";
        }
        String optString = jSONObject.optString(str);
        return optString.toLowerCase().equals("null") ? "" : optString;
    }
}
