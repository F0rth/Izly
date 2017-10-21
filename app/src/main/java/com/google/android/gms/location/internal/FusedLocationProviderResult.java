package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class FusedLocationProviderResult implements Result, SafeParcelable {
    public static final Creator<FusedLocationProviderResult> CREATOR = new zze();
    public static final FusedLocationProviderResult zzaOC = new FusedLocationProviderResult(Status.zzagC);
    private final int mVersionCode;
    private final Status zzUX;

    FusedLocationProviderResult(int i, Status status) {
        this.mVersionCode = i;
        this.zzUX = status;
    }

    public FusedLocationProviderResult(Status status) {
        this(1, status);
    }

    public final int describeContents() {
        return 0;
    }

    public final Status getStatus() {
        return this.zzUX;
    }

    public final int getVersionCode() {
        return this.mVersionCode;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }
}
