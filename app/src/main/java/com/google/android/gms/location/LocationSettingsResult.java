package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class LocationSettingsResult implements Result, SafeParcelable {
    public static final Creator<LocationSettingsResult> CREATOR = new zzg();
    private final int mVersionCode;
    private final Status zzUX;
    private final LocationSettingsStates zzaOi;

    LocationSettingsResult(int i, Status status, LocationSettingsStates locationSettingsStates) {
        this.mVersionCode = i;
        this.zzUX = status;
        this.zzaOi = locationSettingsStates;
    }

    public LocationSettingsResult(Status status) {
        this(1, status, null);
    }

    public final int describeContents() {
        return 0;
    }

    public final LocationSettingsStates getLocationSettingsStates() {
        return this.zzaOi;
    }

    public final Status getStatus() {
        return this.zzUX;
    }

    public final int getVersionCode() {
        return this.mVersionCode;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzg.zza(this, parcel, i);
    }
}
