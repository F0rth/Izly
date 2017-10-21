package com.ezeeworld.b4s.android.sdk.playservices.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;

import com.ezeeworld.b4s.android.sdk.playservices.GoogleApi;
import com.ezeeworld.b4s.android.sdk.playservices.location.Geofence.Builder;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class LocationServices {
    private Location singleLocationResult;

    static class Holder {
        static final LocationServices INSTANCE = new LocationServices();

        private Holder() {
        }
    }

    private LocationServices() {
    }

    public static LocationServices get() {
        return Holder.INSTANCE;
    }

    public final void addGeofences(List<Builder> list, PendingIntent pendingIntent) {
        if (GoogleApi.get().connect()) {
            try {
                List arrayList = new ArrayList(list.size());
                for (Builder build : list) {
                    arrayList.add(build.build());
                }
                com.google.android.gms.location.LocationServices.GeofencingApi.addGeofences(GoogleApi.get().getClient(), arrayList, pendingIntent);
            } catch (SecurityException e) {
            }
        }
    }

    public final Location getLastLocation() {
        if (GoogleApi.get().connect()) {
            try {
                return com.google.android.gms.location.LocationServices.FusedLocationApi.getLastLocation(GoogleApi.get().getClient());
            } catch (SecurityException e) {
            }
        }
        return null;
    }

    public final Location getNetworkSingleLocation(long j) {
        if (GoogleApi.get().connect()) {
            try {
                this.singleLocationResult = null;
                Status status = (Status) com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(GoogleApi.get().getClient(), LocationRequest.create().setMaxWaitTime(j).setNumUpdates(1).setPriority(102), new LocationListener() {
                    public void onLocationChanged(Location location) {
                        LocationServices.this.singleLocationResult = location;
                    }
                }, Looper.getMainLooper()).await(j, TimeUnit.MILLISECONDS);
                long currentTimeMillis = System.currentTimeMillis();
                if (!status.isSuccess()) {
                    return getLastLocation();
                }
                while (this.singleLocationResult == null && System.currentTimeMillis() < currentTimeMillis + j) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
                return this.singleLocationResult == null ? getLastLocation() : this.singleLocationResult;
            } catch (SecurityException e2) {
            }
        }
        return null;
    }

    public final void removeGeofences(PendingIntent pendingIntent) {
        if (GoogleApi.get().connect()) {
            com.google.android.gms.location.LocationServices.GeofencingApi.removeGeofences(GoogleApi.get().getClient(), pendingIntent);
        }
    }

    public final void removeLocationUpdates(PendingIntent pendingIntent) {
        if (GoogleApi.get().connect()) {
            try {
                com.google.android.gms.location.LocationServices.FusedLocationApi.removeLocationUpdates(GoogleApi.get().getClient(), pendingIntent);
            } catch (SecurityException e) {
            }
        }
    }

    public final void requestGpsLocationUpdates(long j, PendingIntent pendingIntent) {
        if (GoogleApi.get().connect()) {
            try {
                com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(GoogleApi.get().getClient(), LocationRequest.create().setFastestInterval(j).setInterval(j).setPriority(100), pendingIntent);
            } catch (SecurityException e) {
            }
        }
    }

    public final void requestNetworkLocationUpdates(long j, PendingIntent pendingIntent) {
        if (GoogleApi.get().connect()) {
            try {
                com.google.android.gms.location.LocationServices.FusedLocationApi.requestLocationUpdates(GoogleApi.get().getClient(), LocationRequest.create().setFastestInterval(j).setInterval(j).setPriority(102), pendingIntent);
            } catch (SecurityException e) {
            }
        }
    }
}
