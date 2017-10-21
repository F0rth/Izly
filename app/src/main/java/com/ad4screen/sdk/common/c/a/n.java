package com.ad4screen.sdk.common.c.a;

import android.location.Location;

import com.ad4screen.sdk.common.c.a.a.b;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class n extends b<Location> {
    private final String a = "android.location.Location";
    private final String b = GeofencesColumns.LATITUDE;
    private final String c = GeofencesColumns.LONGITUDE;
    private final String d = "provider";

    public JSONObject a(Location location) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put(GeofencesColumns.LATITUDE, location.getLatitude());
        jSONObject2.put(GeofencesColumns.LONGITUDE, location.getLongitude());
        jSONObject2.put("provider", location.getProvider());
        jSONObject.put("type", "android.location.Location");
        jSONObject.put("android.location.Location", jSONObject2);
        return jSONObject;
    }
}
