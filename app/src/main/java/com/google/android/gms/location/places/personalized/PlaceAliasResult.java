package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class PlaceAliasResult implements Result, SafeParcelable {
    public static final Creator<PlaceAliasResult> CREATOR = new zzc();
    final int mVersionCode;
    private final Status zzUX;
    final PlaceUserData zzaRf;

    PlaceAliasResult(int i, Status status, PlaceUserData placeUserData) {
        this.mVersionCode = i;
        this.zzUX = status;
        this.zzaRf = placeUserData;
    }

    public int describeContents() {
        return 0;
    }

    public Status getStatus() {
        return this.zzUX;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }

    public PlaceUserData zzzC() {
        return this.zzaRf;
    }
}
