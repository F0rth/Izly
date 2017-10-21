package com.ad4screen.sdk.service.modules.g;

import android.content.Context;
import android.location.Location;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.d.b;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.GeofencePlugin;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.provider.A4SGeofenceResolver;

import java.util.Collections;
import java.util.List;

public class a {
    private static Geofence a(Location location, Geofence geofence) {
        Geofence geofence2 = new Geofence("LIMIT");
        geofence2.setLongitude(location.getLongitude());
        geofence2.setLatitude(location.getLatitude());
        geofence2.setRadius((float) (((double) i.a(location.getLatitude(), location.getLongitude(), geofence.getLatitude(), geofence.getLongitude())) * 0.75d));
        return geofence2;
    }

    public static void a(Context context, boolean z, boolean z2) {
        a(context, z, z2, new A4SGeofenceResolver(context));
    }

    public static void a(Context context, boolean z, boolean z2, A4SGeofenceResolver a4SGeofenceResolver) {
        GeofencePlugin d = b.d();
        if (d == null) {
            Log.warn("Tracker|onGeofencingConfigurationLoaded|GeofencePlugin is not found");
            return;
        }
        List<Geofence> allGeofences = a4SGeofenceResolver.getAllGeofences();
        if (allGeofences != null) {
            int i;
            Log.internal("Tracker|onGeofencingConfigurationLoaded|the total amount of geofences: " + allGeofences.size());
            if (allGeofences.size() > 0) {
                Log.debug("Tracker|onGeofencingConfigurationLoaded| " + allGeofences.size() + " geofences will be removed from the plugin");
                String[] strArr = new String[(allGeofences.size() + 1)];
                for (i = 0; i < allGeofences.size(); i++) {
                    strArr[i] = ((Geofence) allGeofences.get(i)).getId();
                }
                strArr[allGeofences.size()] = "LIMIT";
                d.remove(context, strArr);
            }
            Location d2 = com.ad4screen.sdk.d.a.a(context).d();
            if (z || !z2) {
                if (d2 != null) {
                    for (Geofence geofence : allGeofences) {
                        geofence.setDistance(i.a(d2.getLatitude(), d2.getLongitude(), geofence.getLatitude(), geofence.getLongitude()));
                    }
                    Collections.sort(allGeofences);
                } else {
                    Log.warn("Tracker|onGeofencingConfigurationLoaded|No Location found, Geofences will be set at the next session if possible");
                    return;
                }
            }
            if (allGeofences.size() > 0) {
                int size = allGeofences.size() <= 99 ? allGeofences.size() : 99;
                Geofence[] geofenceArr = new Geofence[((d2 != null ? 1 : 0) + size)];
                Log.debug("Tracker|onGeofencingConfigurationLoaded| " + geofenceArr.length + " geofences will be added to the plugin");
                for (i = 0; i < size; i++) {
                    geofenceArr[i] = (Geofence) allGeofences.get(i);
                }
                if (d2 != null) {
                    geofenceArr[size] = a(d2, geofenceArr[size - 1]);
                }
                d.add(context, geofenceArr);
            }
        }
    }
}
