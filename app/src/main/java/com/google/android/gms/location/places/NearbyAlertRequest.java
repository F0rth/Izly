package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public final class NearbyAlertRequest implements SafeParcelable {
    public static final zze CREATOR = new zze();
    private int mPriority = 110;
    private final int mVersionCode;
    private final int zzaNC;
    private final int zzaPr;
    @Deprecated
    private final PlaceFilter zzaPs;
    private final NearbyAlertFilter zzaPt;
    private final boolean zzaPu;
    private final int zzaPv;

    NearbyAlertRequest(int i, int i2, int i3, PlaceFilter placeFilter, NearbyAlertFilter nearbyAlertFilter, boolean z, int i4, int i5) {
        this.mVersionCode = i;
        this.zzaNC = i2;
        this.zzaPr = i3;
        if (nearbyAlertFilter != null) {
            this.zzaPt = nearbyAlertFilter;
        } else if (placeFilter == null) {
            this.zzaPt = null;
        } else if (placeFilter.getPlaceIds() != null && !placeFilter.getPlaceIds().isEmpty()) {
            this.zzaPt = NearbyAlertFilter.zzh(placeFilter.getPlaceIds());
        } else if (placeFilter.getPlaceTypes() == null || placeFilter.getPlaceTypes().isEmpty()) {
            this.zzaPt = null;
        } else {
            this.zzaPt = NearbyAlertFilter.zzi(placeFilter.getPlaceTypes());
        }
        this.zzaPs = null;
        this.zzaPu = z;
        this.zzaPv = i4;
        this.mPriority = i5;
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof NearbyAlertRequest)) {
                return false;
            }
            NearbyAlertRequest nearbyAlertRequest = (NearbyAlertRequest) obj;
            if (this.zzaNC != nearbyAlertRequest.zzaNC || this.zzaPr != nearbyAlertRequest.zzaPr || !zzw.equal(this.zzaPt, nearbyAlertRequest.zzaPt)) {
                return false;
            }
            if (this.mPriority != nearbyAlertRequest.mPriority) {
                return false;
            }
        }
        return true;
    }

    public final int getPriority() {
        return this.mPriority;
    }

    public final int getVersionCode() {
        return this.mVersionCode;
    }

    public final int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.zzaNC), Integer.valueOf(this.zzaPr), this.zzaPt, Integer.valueOf(this.mPriority));
    }

    public final String toString() {
        return zzw.zzy(this).zzg("transitionTypes", Integer.valueOf(this.zzaNC)).zzg("loiteringTimeMillis", Integer.valueOf(this.zzaPr)).zzg("nearbyAlertFilter", this.zzaPt).zzg("priority", Integer.valueOf(this.mPriority)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }

    public final int zzyV() {
        return this.zzaNC;
    }

    public final int zzyY() {
        return this.zzaPr;
    }

    @Deprecated
    public final PlaceFilter zzyZ() {
        return null;
    }

    public final NearbyAlertFilter zzza() {
        return this.zzaPt;
    }

    public final boolean zzzb() {
        return this.zzaPu;
    }

    public final int zzzc() {
        return this.zzaPv;
    }
}
