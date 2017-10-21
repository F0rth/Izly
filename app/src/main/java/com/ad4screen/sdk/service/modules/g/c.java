package com.ad4screen.sdk.service.modules.g;

import android.location.Location;
import android.os.Bundle;

import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;
import com.ad4screen.sdk.d.a.b;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.service.modules.e.d;

import java.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

public class c {
    private final b a;
    private final a b;
    private Bundle c;
    private final b d = new b(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a() {
            Log.debug("GeolocationManager|Geolocation changed, updating ad4push geolocation");
            this.a.a(false);
        }
    };
    private final com.ad4screen.sdk.service.modules.b.a.c e = new com.ad4screen.sdk.service.modules.b.a.c(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a() {
        }

        public void a(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z) {
            Log.debug("GeolocationManager|Received sharedId, starting session");
            this.a.a();
        }
    };

    public c(a aVar) {
        this.b = aVar;
        this.a = new b(aVar.a());
        f.a().a(com.ad4screen.sdk.d.a.a.class, this.d);
        f.a().a(com.ad4screen.sdk.service.modules.b.a.b.class, this.e);
    }

    private void a() {
        b();
        if (!com.ad4screen.sdk.d.b.a(this.b.a()).F()) {
            Log.debug("GeolocationManager|Starting session, updating ad4push geolocation");
            a(true);
        }
    }

    private boolean a(long j, long j2, boolean z, Location location, Location location2) {
        return location == null ? false : z ? true : j - j2 > 300000 ? true : i.a(location2.getLatitude(), location2.getLongitude(), location.getLatitude(), location.getLongitude()) >= 100;
    }

    private void b() {
        this.a.a(0);
    }

    public void a(Location location) {
        com.ad4screen.sdk.d.a.a(this.b.a()).a(location);
        a(false);
    }

    public void a(Bundle bundle) {
        this.c = bundle;
        if (this.c == null) {
            Log.error("GeolocationManager|No Geofence information found, aborting...");
            return;
        }
        Bundle bundle2 = this.c.getBundle(Constants.EXTRA_GEOFENCE_PAYLOAD);
        if (bundle2 == null || !bundle2.containsKey("triggeringLocation")) {
            Log.error("GeolocationManager|No triggeringLocation information found, aborting...");
            return;
        }
        try {
            JSONObject jSONObject = new JSONObject(bundle2.getString("triggeringLocation"));
            Location location = new Location("fused");
            if (!jSONObject.isNull("provider")) {
                location.setProvider(jSONObject.getString("provider"));
            }
            if (!jSONObject.isNull(GeofencesColumns.LATITUDE)) {
                location.setLatitude(jSONObject.getDouble(GeofencesColumns.LATITUDE));
            }
            if (!jSONObject.isNull(GeofencesColumns.LONGITUDE)) {
                location.setLongitude(jSONObject.getDouble(GeofencesColumns.LONGITUDE));
            }
            if (!jSONObject.isNull("altitude")) {
                location.setAltitude(jSONObject.getDouble("altitude"));
            }
            if (!jSONObject.isNull("accuracy")) {
                location.setAccuracy((float) jSONObject.getDouble("accuracy"));
            }
            if (!jSONObject.isNull("bearing")) {
                location.setBearing((float) jSONObject.getDouble("bearing"));
            }
            if (!jSONObject.isNull("speed")) {
                location.setSpeed((float) jSONObject.getDouble("speed"));
            }
            if (!jSONObject.isNull("time")) {
                location.setTime(jSONObject.getLong("time"));
            }
            com.ad4screen.sdk.d.a.a(this.b.a()).a(location);
            if (bundle2.getInt("transition") == 2) {
                String[] stringArray = bundle2.getStringArray("ids");
                if (stringArray != null && Arrays.asList(stringArray).contains("LIMIT")) {
                    Log.info("LIMIT geofence has been reached, recalculate nearest geofences");
                    a.a(this.b.a(), true, false);
                }
            }
            a(true);
            this.b.d().a(this.c, true);
        } catch (JSONException e) {
            Log.error("GeolocationManager|Error while parsing triggeringLocation information, aborting...");
        }
    }

    public void a(boolean z) {
        Location d = com.ad4screen.sdk.d.a.a(this.b.a()).d();
        Location a = this.a.a();
        if (d == null) {
            Log.debug("GeolocationManager|No Geolocation Found");
            return;
        }
        long e = this.a.e();
        long a2 = g.e().a();
        if (!a(a2, e, z, d, a)) {
            Log.debug("GeolocationManager|Cancelled ad4push geolocation update (cannot be sent more than once every 5 minutes and new location must be 100 meters away from last sent one)");
        } else if (com.ad4screen.sdk.d.b.a(this.b.a()).c() != null) {
            new d(this.b.a(), d).run();
            this.a.a(a2);
            try {
                this.a.a(new e().a(d));
            } catch (Throwable e2) {
                Log.internal("GeolocationManager|Can't store last location sent", e2);
            }
            if (this.c != null) {
                new d(this.b.a(), this.c).run();
                this.c = null;
            }
        }
    }
}
