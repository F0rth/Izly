package com.ad4screen.sdk.common.c.a;

import android.location.Location;

import com.ad4screen.sdk.common.c.a.a.a;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;

import org.json.JSONException;
import org.json.JSONObject;

public class m extends a<Location> {
    private final String a = "android.location.Location";
    private final String b = GeofencesColumns.LATITUDE;
    private final String c = GeofencesColumns.LONGITUDE;
    private final String d = "provider";

    public Location a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("android.location.Location");
        Location location = new Location(jSONObject.getString("provider"));
        location.setLatitude(jSONObject.getDouble(GeofencesColumns.LATITUDE));
        location.setLongitude(jSONObject.getDouble(GeofencesColumns.LONGITUDE));
        return location;
    }

    public String a() {
        return "android.location.Location";
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
