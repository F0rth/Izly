package com.google.android.gms.location.places.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.PlacesOptions.Builder;
import com.google.android.gms.location.places.zzl;
import java.util.Locale;

public class zzk extends zzj<zzf> {
    private final PlacesParams zzaQq;
    private final Locale zzaQr = Locale.getDefault();

    public static class zza extends com.google.android.gms.common.api.Api.zza<zzk, PlacesOptions> {
        private final String zzaQs;

        public zza(String str) {
            this.zzaQs = str;
        }

        public /* synthetic */ zzb zza(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zzb(context, looper, com_google_android_gms_common_internal_zzf, (PlacesOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzk zzb(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, PlacesOptions placesOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzk(context, looper, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener, this.zzaQs != null ? this.zzaQs : context.getPackageName(), placesOptions == null ? new Builder().build() : placesOptions);
        }
    }

    public zzk(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, PlacesOptions placesOptions) {
        super(context, looper, 67, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener);
        String str2 = null;
        if (com_google_android_gms_common_internal_zzf.getAccount() != null) {
            str2 = com_google_android_gms_common_internal_zzf.getAccount().name;
        }
        this.zzaQq = new PlacesParams(str, this.zzaQr, str2, placesOptions.zzaPU, placesOptions.zzaPV);
    }

    protected /* synthetic */ IInterface zzW(IBinder iBinder) {
        return zzcq(iBinder);
    }

    public void zza(zzl com_google_android_gms_location_places_zzl, PlaceFilter placeFilter) throws RemoteException {
        if (placeFilter == null) {
            placeFilter = PlaceFilter.zzzd();
        }
        ((zzf) zzqJ()).zza(placeFilter, this.zzaQq, (zzi) com_google_android_gms_location_places_zzl);
    }

    public void zza(zzl com_google_android_gms_location_places_zzl, PlaceReport placeReport) throws RemoteException {
        zzx.zzz(placeReport);
        ((zzf) zzqJ()).zza(placeReport, this.zzaQq, (zzi) com_google_android_gms_location_places_zzl);
    }

    protected zzf zzcq(IBinder iBinder) {
        return com.google.android.gms.location.places.internal.zzf.zza.zzcm(iBinder);
    }

    protected String zzgu() {
        return "com.google.android.gms.location.places.PlaceDetectionApi";
    }

    protected String zzgv() {
        return "com.google.android.gms.location.places.internal.IGooglePlaceDetectionService";
    }
}
