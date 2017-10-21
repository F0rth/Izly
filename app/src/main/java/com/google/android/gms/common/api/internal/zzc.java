package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzx;

public class zzc implements ConnectionCallbacks, OnConnectionFailedListener {
    public final Api<?> zzagT;
    private final int zzagU;
    private zzl zzagV;

    public zzc(Api<?> api, int i) {
        this.zzagT = api;
        this.zzagU = i;
    }

    private void zzpi() {
        zzx.zzb(this.zzagV, (Object) "Callbacks must be attached to a GoogleApiClient instance before connecting the client.");
    }

    public void onConnected(@Nullable Bundle bundle) {
        zzpi();
        this.zzagV.onConnected(bundle);
    }

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zzpi();
        this.zzagV.zza(connectionResult, this.zzagT, this.zzagU);
    }

    public void onConnectionSuspended(int i) {
        zzpi();
        this.zzagV.onConnectionSuspended(i);
    }

    public void zza(zzl com_google_android_gms_common_api_internal_zzl) {
        this.zzagV = com_google_android_gms_common_api_internal_zzl;
    }
}
