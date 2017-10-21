package com.ezeeworld.b4s.android.sdk.playservices.location;

import android.content.Intent;
import android.location.Location;

import java.util.List;

public class GeofencingEvent {
    private final com.google.android.gms.location.GeofencingEvent event;

    private GeofencingEvent(com.google.android.gms.location.GeofencingEvent geofencingEvent) {
        this.event = geofencingEvent;
    }

    public static GeofencingEvent fromIntent(Intent intent) {
        return new GeofencingEvent(com.google.android.gms.location.GeofencingEvent.fromIntent(intent));
    }

    public int getErrorCode() {
        return this.event.getErrorCode();
    }

    public int getGeofenceTransition() {
        return this.event.getGeofenceTransition();
    }

    public List<Geofence> getTriggeringGeofences() {
        List triggeringGeofences = this.event.getTriggeringGeofences();
        return triggeringGeofences == null ? null : Geofence.wrapGeofences(triggeringGeofences);
    }

    public Location getTriggeringLocation() {
        return this.event.getTriggeringLocation();
    }

    public boolean hasError() {
        return this.event.hasError();
    }
}
