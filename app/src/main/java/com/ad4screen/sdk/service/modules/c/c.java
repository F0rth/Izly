package com.ad4screen.sdk.service.modules.c;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.contract.A4SContract.BeaconsColumns;
import com.ad4screen.sdk.plugins.model.Beacon;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private Beacon a(JSONObject jSONObject) throws JSONException {
        Beacon beacon = new Beacon(jSONObject.getString("id"));
        beacon.isNeedToBeRemoved = jSONObject.getString("action").equalsIgnoreCase("remove");
        if (!jSONObject.isNull("name")) {
            beacon.name = jSONObject.getString("name");
        }
        if (!jSONObject.isNull("data")) {
            beacon.parseCustomParams(jSONObject);
        }
        if (!jSONObject.isNull(BeaconsColumns.UUID)) {
            beacon.uuid = jSONObject.getString(BeaconsColumns.UUID).toUpperCase();
        }
        if (!jSONObject.isNull(BeaconsColumns.MAJOR)) {
            beacon.major = jSONObject.getInt(BeaconsColumns.MAJOR);
        }
        if (!jSONObject.isNull(BeaconsColumns.MINOR)) {
            beacon.minor = jSONObject.getInt(BeaconsColumns.MINOR);
        }
        if (!jSONObject.isNull("groups")) {
            beacon.parseGroups(jSONObject);
        }
        return beacon;
    }

    private void a(JSONArray jSONArray, e eVar) throws JSONException {
        Map a = eVar.a();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                Beacon a2 = a(jSONArray.getJSONObject(i));
                a.put(Integer.valueOf(Integer.parseInt(a2.id)), a2);
            } catch (Throwable e) {
                Log.internal("Beacon " + jSONArray.getJSONObject(i) + " parse error: ", e);
            }
        }
    }

    public void a(JSONObject jSONObject, e eVar) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("beacons");
            eVar.a(h.a(jSONObject2.getString("date"), a.ISO8601));
            eVar.a(jSONObject2.getBoolean("nearestCalculated"));
            eVar.b(jSONObject2.getBoolean("differentialUpdate"));
            a(jSONObject2.getJSONArray("points"), eVar);
        } catch (Throwable e) {
            Log.internal("Beacons Configuration|Error while parsing beacon !", e);
        }
    }
}
