package com.google.android.gms.auth.firstparty.shared;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class FACLConfig implements SafeParcelable {
    public static final zza CREATOR = new zza();
    final int version;
    boolean zzYm;
    String zzYn;
    boolean zzYo;
    boolean zzYp;
    boolean zzYq;
    boolean zzYr;

    FACLConfig(int i, boolean z, String str, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.version = i;
        this.zzYm = z;
        this.zzYn = str;
        this.zzYo = z2;
        this.zzYp = z3;
        this.zzYq = z4;
        this.zzYr = z5;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FACLConfig)) {
            return false;
        }
        FACLConfig fACLConfig = (FACLConfig) obj;
        return this.zzYm == fACLConfig.zzYm && TextUtils.equals(this.zzYn, fACLConfig.zzYn) && this.zzYo == fACLConfig.zzYo && this.zzYp == fACLConfig.zzYp && this.zzYq == fACLConfig.zzYq && this.zzYr == fACLConfig.zzYr;
    }

    public int hashCode() {
        return zzw.hashCode(Boolean.valueOf(this.zzYm), this.zzYn, Boolean.valueOf(this.zzYo), Boolean.valueOf(this.zzYp), Boolean.valueOf(this.zzYq), Boolean.valueOf(this.zzYr));
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }
}
