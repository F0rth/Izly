package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.measurement.internal.zzm.zza;

public class zzo extends zzj<zzm> {
    public zzo(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 93, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener);
    }

    public /* synthetic */ IInterface zzW(IBinder iBinder) {
        return zzdo(iBinder);
    }

    public zzm zzdo(IBinder iBinder) {
        return zza.zzdn(iBinder);
    }

    protected String zzgu() {
        return "com.google.android.gms.measurement.START";
    }

    protected String zzgv() {
        return "com.google.android.gms.measurement.internal.IMeasurementService";
    }
}
