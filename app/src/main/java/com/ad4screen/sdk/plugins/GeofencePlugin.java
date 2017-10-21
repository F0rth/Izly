package com.ad4screen.sdk.plugins;

import android.content.Context;

import com.ad4screen.sdk.plugins.model.Geofence;

public interface GeofencePlugin extends BasePlugin {
    void add(Context context, Geofence[] geofenceArr);

    boolean isGeofencingServiceDeclared(Context context);

    void remove(Context context, String[] strArr);
}
