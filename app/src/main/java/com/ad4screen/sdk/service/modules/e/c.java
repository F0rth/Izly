package com.ad4screen.sdk.service.modules.e;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;
import com.ad4screen.sdk.plugins.model.Geofence;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private Geofence a(JSONObject jSONObject) throws JSONException {
        Geofence geofence = new Geofence(jSONObject.getString("id"));
        geofence.setNeedToBeRemoved(jSONObject.getString("action").equalsIgnoreCase("delete"));
        if (!jSONObject.isNull("name")) {
            geofence.setName(jSONObject.getString("name"));
        }
        if (!jSONObject.isNull("lat")) {
            geofence.setLatitude(jSONObject.getDouble("lat"));
        }
        if (!jSONObject.isNull("lon")) {
            geofence.setLongitude(jSONObject.getDouble("lon"));
        }
        if (!jSONObject.isNull(GeofencesColumns.RADIUS)) {
            geofence.setRadius((float) jSONObject.getInt(GeofencesColumns.RADIUS));
        }
        if (!jSONObject.isNull("data")) {
            geofence.parseCustomParams(jSONObject);
        }
        if (!jSONObject.isNull("groups")) {
            geofence.parseGroups(jSONObject);
        }
        if (!jSONObject.isNull("externalId")) {
            geofence.setExternalId(jSONObject.getString("externalId"));
        }
        return geofence;
    }

    private void a(JSONArray jSONArray, a aVar) throws JSONException {
        Map a = aVar.a();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                Geofence a2 = a(jSONArray.getJSONObject(i));
                if (a(a2)) {
                    a.put(a2.getId(), a2);
                }
            } catch (Throwable e) {
                Log.internal("GeofencingResponseParser|Geofence " + jSONArray.getJSONObject(i) + " parse error: ", e);
            }
        }
    }

    private boolean a(Geofence geofence) {
        if (((double) geofence.getRadius()) <= 0.0d) {
            Log.warn("GeofencingResponseParser|parseGeofences|geofence has unsupported radius '" + geofence.getRadius() + "' and will be ignored");
            return false;
        } else if (geofence.getLatitude() > 90.0d || geofence.getLatitude() < -90.0d) {
            Log.warn("GeofencingResponseParser|parseGeofences|geofence has unsupported latitude '" + geofence.getLatitude() + "' and will be ignored");
            return false;
        } else if (geofence.getLongitude() <= 180.0d && geofence.getLongitude() >= -180.0d) {
            return true;
        } else {
            Log.warn("GeofencingResponseParser|parseGeofences|geofence has unsupported longitude '" + geofence.getLongitude() + "' and will be ignored");
            return false;
        }
    }

    public void a(JSONObject jSONObject, a aVar) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("geofences");
            aVar.a(h.a(jSONObject2.getString("date"), a.ISO8601));
            aVar.a(jSONObject2.getBoolean("nearestCalculated"));
            aVar.b(jSONObject2.getBoolean("differentialUpdate"));
            a(jSONObject2.getJSONArray("points"), aVar);
        } catch (Throwable e) {
            Log.internal("GeofencingResponseParser|Error during Geofences parsing", e);
        }
    }
}
