package com.ad4screen.sdk.service.modules.e;

import android.content.Context;
import android.os.Bundle;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.plugins.geofences.GeofenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends c {
    private final String c = "com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask";
    private final String d = "content";
    private String e;
    private final Context f;
    private Bundle g;

    public d(Context context, Bundle bundle) {
        super(context);
        this.f = context;
        this.g = bundle;
    }

    protected void a(String str) {
        Log.debug("GeofencingUpdateTask|Successfully sent Geofences update");
        com.ad4screen.sdk.d.d.a(this.f).e(b.GeofencingUpdateWebservice);
    }

    protected void a(Throwable th) {
        Log.error("GeofencingUpdateTask|Failed to send Geofences update");
    }

    protected boolean a() {
        i();
        j();
        if (com.ad4screen.sdk.d.b.a(this.f).c() == null) {
            Log.warn("GeofencingUpdateTask|No SharedId, not updating geofences");
            return false;
        } else if (com.ad4screen.sdk.d.d.a(this.f).c(b.GeofencingConfigurationWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                if (this.g != null) {
                    JSONArray parseGeofences = GeofenceUtils.parseGeofences(this.g);
                    if (parseGeofences != null) {
                        jSONObject.put("geofences", parseGeofences);
                    }
                }
                this.e = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("GeofencingUpdateTask|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on GeofencingUpdateTask");
            return false;
        }
    }

    public c b(c cVar) {
        d dVar = (d) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONArray jSONArray = new JSONObject(dVar.d()).getJSONArray("geofences");
            JSONArray jSONArray2 = jSONObject.getJSONArray("geofences");
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONArray2.put(jSONArray.get(i));
            }
            this.e = jSONObject.toString();
        } catch (Throwable e) {
            Log.internal("Failed to merge " + c(), e);
        } catch (Throwable e2) {
            Log.internal("Failed to merge " + c(), e2);
        }
        return this;
    }

    protected String c() {
        return b.GeofencingUpdateWebservice.toString();
    }

    protected String d() {
        return this.e;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask");
        if (!jSONObject.isNull("content")) {
            this.e = jSONObject.getString("content");
        }
        return this;
    }

    protected String e() {
        return com.ad4screen.sdk.d.d.a(this.f).a(b.GeofencingUpdateWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.e);
        toJSON.put("com.ad4screen.sdk.service.modules.geofencing.GeofencingUpdateTask", jSONObject);
        return toJSON;
    }
}
