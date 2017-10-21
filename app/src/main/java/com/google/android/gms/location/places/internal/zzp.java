package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.location.places.PlacePhotoMetadata;
import com.google.android.gms.location.places.PlacePhotoResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzf;
import com.google.android.gms.location.places.zzf.zza;

public class zzp implements PlacePhotoMetadata {
    private int mIndex;
    private final int zzDF;
    private final int zzDG;
    private final String zzaQR;
    private final CharSequence zzaQS;

    public zzp(String str, int i, int i2, CharSequence charSequence, int i3) {
        this.zzaQR = str;
        this.zzDF = i;
        this.zzDG = i2;
        this.zzaQS = charSequence;
        this.mIndex = i3;
    }

    public boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzp)) {
                return false;
            }
            zzp com_google_android_gms_location_places_internal_zzp = (zzp) obj;
            if (com_google_android_gms_location_places_internal_zzp.zzDF != this.zzDF || com_google_android_gms_location_places_internal_zzp.zzDG != this.zzDG || !zzw.equal(com_google_android_gms_location_places_internal_zzp.zzaQR, this.zzaQR)) {
                return false;
            }
            if (!zzw.equal(com_google_android_gms_location_places_internal_zzp.zzaQS, this.zzaQS)) {
                return false;
            }
        }
        return true;
    }

    public /* synthetic */ Object freeze() {
        return zzzz();
    }

    public CharSequence getAttributions() {
        return this.zzaQS;
    }

    public int getMaxHeight() {
        return this.zzDG;
    }

    public int getMaxWidth() {
        return this.zzDF;
    }

    public PendingResult<PlacePhotoResult> getPhoto(GoogleApiClient googleApiClient) {
        return getScaledPhoto(googleApiClient, getMaxWidth(), getMaxHeight());
    }

    public PendingResult<PlacePhotoResult> getScaledPhoto(GoogleApiClient googleApiClient, int i, int i2) {
        final int i3 = i;
        final int i4 = i2;
        return googleApiClient.zza(new zza<zze>(this, Places.zzaPN, googleApiClient) {
            final /* synthetic */ zzp zzaQV;

            protected void zza(zze com_google_android_gms_location_places_internal_zze) throws RemoteException {
                com_google_android_gms_location_places_internal_zze.zza(new zzf((zza) this), this.zzaQV.zzaQR, i3, i4, this.zzaQV.mIndex);
            }
        });
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.zzDF), Integer.valueOf(this.zzDG), this.zzaQR, this.zzaQS);
    }

    public boolean isDataValid() {
        return true;
    }

    public PlacePhotoMetadata zzzz() {
        return this;
    }
}
