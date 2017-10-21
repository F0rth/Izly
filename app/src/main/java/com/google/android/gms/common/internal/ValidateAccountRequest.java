package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

@Deprecated
public class ValidateAccountRequest implements SafeParcelable {
    public static final Creator<ValidateAccountRequest> CREATOR = new zzae();
    final int mVersionCode;
    private final String zzVO;
    private final Scope[] zzafT;
    final IBinder zzakA;
    private final int zzamy;
    private final Bundle zzamz;

    ValidateAccountRequest(int i, int i2, IBinder iBinder, Scope[] scopeArr, Bundle bundle, String str) {
        this.mVersionCode = i;
        this.zzamy = i2;
        this.zzakA = iBinder;
        this.zzafT = scopeArr;
        this.zzamz = bundle;
        this.zzVO = str;
    }

    public int describeContents() {
        return 0;
    }

    public String getCallingPackage() {
        return this.zzVO;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzae.zza(this, parcel, i);
    }

    public Scope[] zzrd() {
        return this.zzafT;
    }

    public int zzre() {
        return this.zzamy;
    }

    public Bundle zzrf() {
        return this.zzamz;
    }
}
