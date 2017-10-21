package com.google.android.gms.location.places;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public class zzf extends com.google.android.gms.location.places.internal.zzh.zza {
    private final zzb zzaPw;
    private final zza zzaPx;

    public static abstract class zzb<A extends com.google.android.gms.common.api.Api.zzb> extends com.google.android.gms.location.places.zzl.zzb<PlacePhotoMetadataResult, A> {
        public zzb(zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }

        protected PlacePhotoMetadataResult zzaT(Status status) {
            return new PlacePhotoMetadataResult(status, null);
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzaT(status);
        }
    }

    public static abstract class zza<A extends com.google.android.gms.common.api.Api.zzb> extends com.google.android.gms.location.places.zzl.zzb<PlacePhotoResult, A> {
        public zza(zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }

        protected PlacePhotoResult zzaS(Status status) {
            return new PlacePhotoResult(status, null);
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzaS(status);
        }
    }

    public zzf(zza com_google_android_gms_location_places_zzf_zza) {
        this.zzaPw = null;
        this.zzaPx = com_google_android_gms_location_places_zzf_zza;
    }

    public zzf(zzb com_google_android_gms_location_places_zzf_zzb) {
        this.zzaPw = com_google_android_gms_location_places_zzf_zzb;
        this.zzaPx = null;
    }

    public void zza(PlacePhotoMetadataResult placePhotoMetadataResult) throws RemoteException {
        this.zzaPw.zza((Result) placePhotoMetadataResult);
    }

    public void zza(PlacePhotoResult placePhotoResult) throws RemoteException {
        this.zzaPx.zza((Result) placePhotoResult);
    }
}
