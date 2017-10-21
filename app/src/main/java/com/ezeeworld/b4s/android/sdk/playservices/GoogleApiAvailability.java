package com.ezeeworld.b4s.android.sdk.playservices;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.IntentSender.SendIntentException;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest.Builder;
import com.google.android.gms.location.LocationSettingsResult;

public class GoogleApiAvailability {
    private final com.google.android.gms.common.GoogleApiAvailability availability;
    private final Context context;
    private Boolean geoWarningShown;

    static class Holder {
        static final GoogleApiAvailability INSTANCE = new GoogleApiAvailability();

        private Holder() {
        }
    }

    private GoogleApiAvailability() {
        this.availability = com.google.android.gms.common.GoogleApiAvailability.getInstance();
        this.context = GoogleApi.get().getApplicationContext();
    }

    public static GoogleApiAvailability get() {
        return Holder.INSTANCE;
    }

    public Dialog getErrorDialog(Activity activity, int i) {
        return this.availability.getErrorDialog(activity, this.availability.isGooglePlayServicesAvailable(this.context), i);
    }

    public boolean hasGcmServices() {
        try {
            return GoogleCloudMessaging.getInstance(this.context) != null;
        } catch (Throwable th) {
            return false;
        }
    }

    public boolean hasLocationServicesEnabled() {
        if (GoogleApi.get().connect()) {
            try {
                return LocationServices.FusedLocationApi.getLocationAvailability(GoogleApi.get().getClient()).isLocationAvailable();
            } catch (SecurityException e) {
            } catch (Throwable th) {
            }
        }
        return false;
    }

    public boolean hasPlayServices() {
        return this.availability.isGooglePlayServicesAvailable(this.context) == 0;
    }

    public boolean showLocationServicesSettings(final Activity activity, final int i) {
        if (!GoogleApi.get().connect()) {
            return false;
        }
        this.geoWarningShown = null;
        LocationServices.SettingsApi.checkLocationSettings(GoogleApi.get().getClient(), new Builder().addLocationRequest(LocationRequest.create().setNumUpdates(1).setPriority(100)).setAlwaysShow(true).build()).setResultCallback(new ResultCallback<LocationSettingsResult>() {
            public void onResult(LocationSettingsResult locationSettingsResult) {
                if (locationSettingsResult.getStatus().getStatusCode() == 0) {
                    GoogleApiAvailability.this.geoWarningShown = Boolean.valueOf(false);
                } else if (locationSettingsResult.getStatus().getStatusCode() == 6) {
                    try {
                        locationSettingsResult.getStatus().startResolutionForResult(activity, i);
                        GoogleApiAvailability.this.geoWarningShown = Boolean.valueOf(true);
                    } catch (SendIntentException e) {
                        GoogleApiAvailability.this.geoWarningShown = Boolean.valueOf(false);
                    }
                } else {
                    GoogleApiAvailability.this.geoWarningShown = Boolean.valueOf(false);
                }
            }
        });
        while (this.geoWarningShown == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return this.geoWarningShown.booleanValue();
    }
}
