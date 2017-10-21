package com.ad4screen.sdk.service.modules.inapp;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.e.c;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.plugins.beacons.BeaconUtils;
import com.ad4screen.sdk.plugins.geofences.GeofenceUtils;
import com.ad4screen.sdk.service.modules.inapp.e.e;
import com.ad4screen.sdk.service.modules.push.g;

import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class i extends c {
    private final String c = "com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask";
    private final String d = "content";
    private final String e = "fromGeofence";
    private final Context f;
    private String g;
    private Bundle h;
    private boolean i;

    public i(Context context, Bundle bundle, boolean z) {
        super(context);
        this.f = context;
        this.h = bundle;
        this.i = z;
    }

    protected void a(String str) {
        Log.debug("InApp|New Configuration received");
        d.a(this.f).e(b.UploadLocalDate);
        d.a(this.f).e(b.UploadConnectionType);
        try {
            Log.internal("InApp|Configuration start parsing");
            f fVar = new f(this.f);
            fVar.a(new JSONObject(str));
            if (fVar.a == null) {
                Log.error("InApp|Configuration parsing failed");
                return;
            }
            Log.internal("InApp|Configuration parsing success");
            Log.debug("InApp|Received " + fVar.a.b.size() + " inapps");
            d.a(this.f).e(b.InAppConfigurationWebservice);
            f.a().a(new e(fVar.a, this.i));
        } catch (Throwable e) {
            Log.internal("InApp|InApp config Parsing error!", e);
        }
    }

    protected void a(Throwable th) {
        Log.error("InApp|Failed to retrieve configuration from server");
        f.a().a(new e(this.i));
    }

    protected boolean a() {
        c("application/json;charset=utf-8");
        b(2);
        i();
        com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.f);
        com.ad4screen.sdk.service.modules.h.c.a(this.f);
        if (a.c() == null) {
            Log.warn("InApp|No sharedId, skipping configuration");
            return false;
        } else if (d.a(this.f).c(b.InAppConfigurationWebservice)) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("screenSize", a.A() + "," + a.B().toString());
                jSONObject.put("version", Constants.SDK_VERSION);
                jSONObject.put("deviceModel", a.n());
                jSONObject.put("sharedId", a.c());
                jSONObject.put("appLang", a.s());
                jSONObject.put("source", a.d());
                jSONObject.put("sourceDate", a.e());
                jSONObject.put("deviceSystemVersion", a.o());
                jSONObject.put("partnerId", a.l());
                jSONObject.put("openCount", a.x());
                if ((d.a(this.f).d(b.UploadLocalDate) && d.a(this.f).c(b.UploadLocalDate)) || a.M() == com.ad4screen.sdk.d.b.b.NORMAL) {
                    jSONObject.put("localDate", h.a());
                }
                jSONObject.put("deviceCountry", a.r());
                jSONObject.put("installTime", h.a(h.a(a.v(), a.ISO8601), a.ISO8601));
                boolean z = g.b(this.f) ? !com.ad4screen.sdk.common.i.i(this.f) : false;
                jSONObject.put("notificationsEnabled", z);
                jSONObject.put("bundleVersion", a.t());
                if ((d.a(this.f).d(b.UploadConnectionType) && d.a(this.f).c(b.UploadConnectionType)) || a.M() == com.ad4screen.sdk.d.b.b.NORMAL) {
                    jSONObject.put("connectionType", com.ad4screen.sdk.common.i.h(this.f) ? "wifi" : "cell");
                }
                JSONObject jSONObject2 = new JSONObject();
                if (this.h != null) {
                    if (GeofenceUtils.parseGeofences(this.h) != null) {
                        jSONObject2.put("geofences", GeofenceUtils.parseGeofences(this.h));
                    }
                    JSONArray parseBeacons = BeaconUtils.parseBeacons(this.h);
                    if (parseBeacons != null) {
                        jSONObject2.put("beacons", parseBeacons);
                    }
                }
                Location d = com.ad4screen.sdk.d.a.a(this.f).d();
                if (d != null) {
                    JSONObject jSONObject3 = new JSONObject();
                    Calendar instance = Calendar.getInstance(Locale.US);
                    instance.setTimeInMillis(d.getTime());
                    jSONObject3.put("date", h.a(instance.getTime(), a.ISO8601));
                    jSONObject3.put("lat", d.getLatitude());
                    jSONObject3.put("lon", d.getLongitude());
                    jSONObject3.put("alt", d.getAltitude());
                    jSONObject3.put("acc", (double) d.getAccuracy());
                    jSONObject2.put("geoloc", jSONObject3);
                }
                if (!(this.h == null && d == null)) {
                    jSONObject.put("geoloc", jSONObject2);
                }
                this.g = jSONObject.toString();
                return true;
            } catch (Throwable e) {
                Log.error("InApp|Could not build message to send to Ad4Screen", e);
                return false;
            }
        } else {
            Log.debug("Service interruption on LoadInAppConfigTask");
            return false;
        }
    }

    public c b(c cVar) {
        return cVar;
    }

    protected String c() {
        return b.InAppConfigurationWebservice.toString();
    }

    protected String d() {
        return this.g;
    }

    public c e(String str) throws JSONException {
        super.e(str);
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask");
        if (!jSONObject.isNull("content")) {
            this.g = jSONObject.getString("content");
        }
        if (!jSONObject.isNull("fromGeofence")) {
            this.i = jSONObject.getBoolean("fromGeofence");
        }
        return this;
    }

    protected String e() {
        return d.a(this.f).a(b.InAppConfigurationWebservice);
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject toJSON = super.toJSON();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("content", this.g);
        jSONObject.put("fromGeofence", this.i);
        toJSON.put("com.ad4screen.sdk.service.modules.inapp.LoadInAppConfigTask", jSONObject);
        return toJSON;
    }
}
