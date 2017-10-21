package com.ad4screen.sdk.service.modules.g;

import android.content.Context;
import android.location.Location;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.d.d.b;

import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class d extends c {
    private final String c = "com.ad4screen.sdk.service.modules.location.LocationUpdateTask";
    private final String d = "content";
    private final String e = "location";
    private String f;
    private final Context g;
    private Location h;

    public d(Context context, Location location) {
        super(context);
        this.g = context;
        this.h = location;
    }

    protected void a(String str) {
        if (this.h != null) {
            Log.debug("LocationUpdateTask|Successfully updated location to " + this.h.getLatitude() + "," + this.h.getLongitude() + " (accuracy :" + this.h.getAccuracy() + ")");
            com.ad4screen.sdk.d.d.a(this.g).e(b.UpdateLocationWebservice);
        }
    }

    protected void a(Throwable th) {
        Log.error("LocationUpdateTask|Location update failed");
    }

    protected boolean a() {
        i();
        j();
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.g);
        if (a.c() == null) {
            Log.warn("LocationUpdateTask|No SharedId, not updating location");
            return false;
        } else if (com.ad4screen.sdk.d.d.a(this.g).c(b.UpdateLocationWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                JSONArray jSONArray = new JSONArray();
                if (this.h == null) {
                    return false;
                }
                Calendar instance = Calendar.getInstance(Locale.US);
                instance.setTimeInMillis(this.h.getTime());
                jSONObject2.put("date", h.a(instance.getTime(), a.ISO8601));
                jSONObject2.put("lat", this.h.getLatitude());
                jSONObject2.put("lon", this.h.getLongitude());
                jSONObject2.put("alt", this.h.getAltitude());
                jSONObject2.put("acc", (double) this.h.getAccuracy());
                jSONObject2.put("timezone", a.w());
                jSONObject2.put("ruuid", h.b());
                jSONArray.put(jSONObject2);
                jSONObject.put("geolocs", jSONArray);
                this.f = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("LocationUpdateTask|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on LocationUpdateTask");
            return false;
        }
    }

    public c b(c cVar) {
        d dVar = (d) cVar;
        try {
            JSONObject jSONObject = new JSONObject(d());
            JSONArray jSONArray = new JSONObject(dVar.d()).getJSONArray("geolocs");
            JSONArray jSONArray2 = jSONObject.getJSONArray("geolocs");
            for (int i = 0; i < jSONArray.length(); i++) {
                jSONArray2.put(jSONArray.get(i));
            }
            this.f = jSONObject.toString();
        } catch (Throwable e) {
            Log.internal("Failed to merge " + c(), e);
        } catch (Throwable e2) {
            Log.internal("Failed to merge " + c(), e2);
        }
        return this;
    }

    protected String c() {
        return b.UpdateLocationWebservice.toString();
    }

    protected String d() {
        return this.f;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.location.LocationUpdateTask");
        if (!jSONObject.isNull("content")) {
            this.f = jSONObject.getString("content");
        }
        if (!jSONObject.isNull("location")) {
            this.h = (Location) new e().a(jSONObject.getString("location"), new Location(""));
        }
        i();
        return this;
    }

    protected String e() {
        return com.ad4screen.sdk.d.d.a(this.g).a(b.UpdateLocationWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.location.LocationUpdateTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.f);
        toJSON.put("com.ad4screen.sdk.service.modules.location.LocationUpdateTask", jSONObject);
        if (this.h != null) {
            toJSON.put("location", new e().a(this.h));
        }
        return toJSON;
    }
}
