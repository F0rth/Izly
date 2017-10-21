package com.google.android.gms.ads.internal.request;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public class CapabilityParcel implements SafeParcelable {
    public static final Creator<CapabilityParcel> CREATOR = new zzi();
    public final int versionCode;
    public final boolean zzIn;
    public final boolean zzIo;
    public final boolean zzIp;

    CapabilityParcel(int i, boolean z, boolean z2, boolean z3) {
        this.versionCode = i;
        this.zzIn = z;
        this.zzIo = z2;
        this.zzIp = z3;
    }

    public CapabilityParcel(boolean z, boolean z2, boolean z3) {
        this(2, z, z2, z3);
    }

    public int describeContents() {
        return 0;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("iap_supported", this.zzIn);
        bundle.putBoolean("default_iap_supported", this.zzIo);
        bundle.putBoolean("app_streaming_supported", this.zzIp);
        return bundle;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
