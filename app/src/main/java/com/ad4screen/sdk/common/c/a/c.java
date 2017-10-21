package com.ad4screen.sdk.common.c.a;

import android.os.Bundle;

import com.ad4screen.sdk.common.c.a.a.a;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class c extends a<Bundle> {
    private final String a = "android.os.Bundle";

    public Bundle a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("android.os.Bundle");
        Bundle bundle = new Bundle();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            Object obj = jSONObject.get(str2);
            if (obj instanceof String) {
                bundle.putString(str2, (String) obj);
            } else if (obj instanceof Boolean) {
                bundle.putBoolean(str2, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Integer) {
                bundle.putInt(str2, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                bundle.putLong(str2, ((Long) obj).longValue());
            } else if (obj instanceof Float) {
                bundle.putFloat(str2, ((Float) obj).floatValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(str2, ((Double) obj).doubleValue());
            } else {
                bundle.putBundle(str2, a(obj.toString()));
            }
        }
        return bundle;
    }

    public String a() {
        return "android.os.Bundle";
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
