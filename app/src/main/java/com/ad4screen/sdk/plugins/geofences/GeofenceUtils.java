package com.ad4screen.sdk.plugins.geofences;

import android.os.Bundle;

import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;

import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class GeofenceUtils {
    private GeofenceUtils() {
    }

    public static JSONArray parseGeofences(Bundle bundle) throws JSONException {
        Bundle bundle2 = bundle.getBundle(Constants.EXTRA_GEOFENCE_PAYLOAD);
        if (bundle2 == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        int i = bundle2.getInt("transition");
        String[] stringArray = bundle2.getStringArray("ids");
        for (int i2 = 0; i2 < stringArray.length; i2++) {
            if (!"LIMIT".equals(stringArray[i2])) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("date", h.a(Calendar.getInstance(Locale.US).getTime(), a.ISO8601));
                jSONObject.put("id", stringArray[i2]);
                if (i == 1) {
                    jSONObject.put("transition", "enter");
                } else {
                    jSONObject.put("transition", "exit");
                }
                jSONObject.put("ruuid", h.b());
                jSONArray.put(jSONObject);
            }
        }
        return jSONArray;
    }
}
