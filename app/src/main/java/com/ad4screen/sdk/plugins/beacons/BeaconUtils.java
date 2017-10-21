package com.ad4screen.sdk.plugins.beacons;

import android.os.Bundle;

import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.contract.A4SContract.BeaconsColumns;

import java.sql.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class BeaconUtils {
    private BeaconUtils() {
    }

    public static JSONArray parseBeacons(Bundle bundle) throws JSONException {
        if (bundle != null) {
            Bundle bundle2 = bundle.getBundle(Constants.EXTRA_BEACON_PAYLOAD);
            if (bundle2 != null) {
                JSONArray jSONArray = new JSONArray();
                for (String str : bundle2.keySet()) {
                    JSONObject jSONObject = new JSONObject();
                    Bundle bundle3 = bundle2.getBundle(str);
                    jSONObject.put("id", bundle3.getString("id"));
                    jSONObject.put("transition", bundle3.getInt("transition") == 1 ? "enter" : "exit");
                    jSONObject.put(BeaconsColumns.UUID, bundle3.getString(BeaconsColumns.UUID));
                    jSONObject.put("maj", bundle3.getInt("maj"));
                    jSONObject.put("min", bundle3.getInt("min"));
                    jSONObject.put("power", bundle3.getInt("power"));
                    jSONObject.put("dist", bundle3.getDouble("dist"));
                    jSONObject.put("acc", bundle3.getString("acc"));
                    jSONObject.put("rssi", bundle3.getInt("rssi"));
                    jSONObject.put("date", h.a(new Date(bundle3.getLong("date")), a.ISO8601));
                    jSONObject.put("ruuid", h.b());
                    jSONArray.put(jSONObject);
                }
                Log.debug("BeaconUtils|Detected beacons : " + jSONArray.toString());
                return jSONArray;
            }
        }
        return null;
    }
}
