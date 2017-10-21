package com.google.android.gms.location.places;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzng;

public class zzl extends com.google.android.gms.location.places.internal.zzi.zza {
    private static final String TAG = zzl.class.getSimpleName();
    private final Context mContext;
    private final zzd zzaPP;
    private final zza zzaPQ;
    private final zze zzaPR;
    private final zzf zzaPS;
    private final zzc zzaPT;

    public static abstract class zzb<R extends Result, A extends com.google.android.gms.common.api.Api.zzb> extends com.google.android.gms.common.api.internal.zza.zza<R, A> {
        public zzb(com.google.android.gms.common.api.Api.zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }
    }

    public static abstract class zzc<A extends com.google.android.gms.common.api.Api.zzb> extends zzb<PlaceBuffer, A> {
        public zzc(com.google.android.gms.common.api.Api.zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }

        protected PlaceBuffer zzaW(Status status) {
            return new PlaceBuffer(DataHolder.zzbI(status.getStatusCode()), null);
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzaW(status);
        }
    }

    public static abstract class zza<A extends com.google.android.gms.common.api.Api.zzb> extends zzb<AutocompletePredictionBuffer, A> {
        public zza(com.google.android.gms.common.api.Api.zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }

        protected AutocompletePredictionBuffer zzaV(Status status) {
            return new AutocompletePredictionBuffer(DataHolder.zzbI(status.getStatusCode()));
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzaV(status);
        }
    }

    public static abstract class zzd<A extends com.google.android.gms.common.api.Api.zzb> extends zzb<PlaceLikelihoodBuffer, A> {
        public zzd(com.google.android.gms.common.api.Api.zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }

        protected PlaceLikelihoodBuffer zzaX(Status status) {
            return new PlaceLikelihoodBuffer(DataHolder.zzbI(status.getStatusCode()), 100, null);
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzaX(status);
        }
    }

    public static abstract class zzf<A extends com.google.android.gms.common.api.Api.zzb> extends zzb<Status, A> {
        public zzf(com.google.android.gms.common.api.Api.zzc<A> com_google_android_gms_common_api_Api_zzc_A, GoogleApiClient googleApiClient) {
            super(com_google_android_gms_common_api_Api_zzc_A, googleApiClient);
        }

        protected Status zzb(Status status) {
            return status;
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzb(status);
        }
    }

    public static abstract class zze<A extends com.google.android.gms.common.api.Api.zzb> extends zzb<com.google.android.gms.location.places.personalized.zzd, A> {
        protected com.google.android.gms.location.places.personalized.zzd zzaY(Status status) {
            return com.google.android.gms.location.places.personalized.zzd.zzaZ(status);
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzaY(status);
        }
    }

    public zzl(zza com_google_android_gms_location_places_zzl_zza) {
        this.zzaPP = null;
        this.zzaPQ = com_google_android_gms_location_places_zzl_zza;
        this.zzaPR = null;
        this.zzaPS = null;
        this.zzaPT = null;
        this.mContext = null;
    }

    public zzl(zzc com_google_android_gms_location_places_zzl_zzc, Context context) {
        this.zzaPP = null;
        this.zzaPQ = null;
        this.zzaPR = null;
        this.zzaPS = null;
        this.zzaPT = com_google_android_gms_location_places_zzl_zzc;
        this.mContext = context.getApplicationContext();
    }

    public zzl(zzd com_google_android_gms_location_places_zzl_zzd, Context context) {
        this.zzaPP = com_google_android_gms_location_places_zzl_zzd;
        this.zzaPQ = null;
        this.zzaPR = null;
        this.zzaPS = null;
        this.zzaPT = null;
        this.mContext = context.getApplicationContext();
    }

    public zzl(zzf com_google_android_gms_location_places_zzl_zzf) {
        this.zzaPP = null;
        this.zzaPQ = null;
        this.zzaPR = null;
        this.zzaPS = com_google_android_gms_location_places_zzl_zzf;
        this.zzaPT = null;
        this.mContext = null;
    }

    public void zzaU(Status status) throws RemoteException {
        this.zzaPS.zza((Result) status);
    }

    public void zzac(DataHolder dataHolder) throws RemoteException {
        zzx.zza(this.zzaPP != null, (Object) "placeEstimator cannot be null");
        if (dataHolder == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "onPlaceEstimated received null DataHolder: " + zzng.zzso());
            }
            this.zzaPP.zzw(Status.zzagE);
            return;
        }
        Bundle zzpZ = dataHolder.zzpZ();
        this.zzaPP.zza(new PlaceLikelihoodBuffer(dataHolder, zzpZ == null ? 100 : PlaceLikelihoodBuffer.zzH(zzpZ), this.mContext));
    }

    public void zzad(DataHolder dataHolder) throws RemoteException {
        if (dataHolder == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "onAutocompletePrediction received null DataHolder: " + zzng.zzso());
            }
            this.zzaPQ.zzw(Status.zzagE);
            return;
        }
        this.zzaPQ.zza(new AutocompletePredictionBuffer(dataHolder));
    }

    public void zzae(DataHolder dataHolder) throws RemoteException {
        if (dataHolder == null) {
            if (Log.isLoggable(TAG, 6)) {
                Log.e(TAG, "onPlaceUserDataFetched received null DataHolder: " + zzng.zzso());
            }
            this.zzaPR.zzw(Status.zzagE);
            return;
        }
        this.zzaPR.zza(new com.google.android.gms.location.places.personalized.zzd(dataHolder));
    }

    public void zzaf(DataHolder dataHolder) throws RemoteException {
        this.zzaPT.zza(new PlaceBuffer(dataHolder, this.mContext));
    }
}
