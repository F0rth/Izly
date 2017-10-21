package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build.VERSION;
import android.os.Bundle;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.d.a;

@API
public class A4SLocationReceiver extends BroadcastReceiver implements LocationListener {
    private Context a;
    private LocationManager b;

    public void onLocationChanged(Location location) {
        a.a(this.a).a(location);
        Log.info("A4SLocationReceiver|Location update received, recalculate nearest geofences");
        com.ad4screen.sdk.service.modules.g.a.a(this.a, true, false);
        this.b.removeUpdates(this);
    }

    public void onProviderDisabled(String str) {
        if ("network".equals(str)) {
            this.b.removeUpdates(this);
        }
    }

    public void onProviderEnabled(String str) {
    }

    @SuppressLint({"NewApi"})
    public void onReceive(Context context, Intent intent) {
        if (VERSION.SDK_INT > 9 && "android.location.PROVIDERS_CHANGED".equalsIgnoreCase(intent.getAction())) {
            this.b = (LocationManager) context.getSystemService("location");
            if (this.b.isProviderEnabled("network")) {
                Log.info("A4SLocationReceiver|Network Location has been reactivated, waiting for location update");
                this.a = context;
                try {
                    this.b.requestSingleUpdate("network", this, null);
                } catch (Throwable e) {
                    Log.internal(e.getMessage(), e);
                }
            }
        }
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
