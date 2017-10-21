package com.google.android.gms.ads.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class InterstitialAdParameterParcel implements SafeParcelable {
    public static final zzl CREATOR = new zzl();
    public final int versionCode;
    public final boolean zzql;
    public final boolean zzqm;
    public final String zzqn;
    public final boolean zzqo;
    public final float zzqp;

    InterstitialAdParameterParcel(int i, boolean z, boolean z2, String str, boolean z3, float f) {
        this.versionCode = i;
        this.zzql = z;
        this.zzqm = z2;
        this.zzqn = str;
        this.zzqo = z3;
        this.zzqp = f;
    }

    public InterstitialAdParameterParcel(boolean z, boolean z2, String str, boolean z3, float f) {
        this(2, z, z2, str, z3, f);
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzl.zza(this, parcel, i);
    }
}
