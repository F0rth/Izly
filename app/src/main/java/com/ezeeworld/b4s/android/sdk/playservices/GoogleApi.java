package com.ezeeworld.b4s.android.sdk.playservices;

import android.content.Context;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.location.LocationServices;

import de.greenrobot.event.util.AsyncExecutor;
import de.greenrobot.event.util.AsyncExecutor.RunnableEx;

public final class GoogleApi {
    private static GoogleApi instance;
    private final Context context;
    private GoogleApiClient googleApiClient;
    private final Object googleApiClientLock = new Object();

    private GoogleApi(Context context) {
        this.context = context.getApplicationContext();
    }

    private GoogleApiClient buildApiClient() {
        return new Builder(this.context).addApi(LocationServices.API).build();
    }

    public static GoogleApi get() {
        if (instance != null) {
            return instance;
        }
        throw new RuntimeException("Incorrect internal use of GoogleApi access; always call init() first");
    }

    public static void init(Context context) {
        if (instance == null) {
            synchronized (GoogleApi.class) {
                try {
                    if (instance == null) {
                        instance = new GoogleApi(context);
                        AsyncExecutor.create().execute(new RunnableEx() {
                            public final void run() throws Exception {
                                if (GoogleApi.get().connect()) {
                                    com.ezeeworld.b4s.android.sdk.playservices.location.LocationServices.get().getNetworkSingleLocation(10000);
                                }
                            }
                        });
                    }
                } catch (Throwable th) {
                    Class cls = GoogleApi.class;
                }
            }
        }
    }

    public final boolean connect() {
        boolean z = false;
        try {
            if (isInstalled()) {
                synchronized (this.googleApiClientLock) {
                    if (this.googleApiClient == null) {
                        this.googleApiClient = buildApiClient();
                    }
                    if (!this.googleApiClient.isConnected()) {
                        this.googleApiClient.blockingConnect();
                    }
                }
                z = this.googleApiClient.isConnected();
            }
        } catch (Exception e) {
        }
        return z;
    }

    public final Context getApplicationContext() {
        return this.context;
    }

    public final GoogleApiClient getClient() {
        if (this.googleApiClient != null && this.googleApiClient.isConnected()) {
            return this.googleApiClient;
        }
        throw new RuntimeException("Always use connect() before directly querying the client directly");
    }

    final boolean isInstalled() {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this.context) == 0;
    }
}
