package com.ezeeworld.b4s.android.sdk.playservices.location;

import android.content.Intent;
import android.location.Location;

public final class LocationResult {
    private final com.google.android.gms.location.LocationResult locationResult;

    public LocationResult(com.google.android.gms.location.LocationResult locationResult) {
        this.locationResult = locationResult;
    }

    public static LocationResult extractResult(Intent intent) {
        return new LocationResult(com.google.android.gms.location.LocationResult.extractResult(intent));
    }

    public final Location getLastLocation() {
        return this.locationResult == null ? null : this.locationResult.getLastLocation();
    }
}
