package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzhb;
import java.util.List;

@zzhb
public final class AdRequestParcel implements SafeParcelable {
    public static final zzg CREATOR = new zzg();
    public final Bundle extras;
    public final int versionCode;
    public final long zztC;
    public final int zztD;
    public final List<String> zztE;
    public final boolean zztF;
    public final int zztG;
    public final boolean zztH;
    public final String zztI;
    public final SearchAdRequestParcel zztJ;
    public final Location zztK;
    public final String zztL;
    public final Bundle zztM;
    public final Bundle zztN;
    public final List<String> zztO;
    public final String zztP;
    public final String zztQ;
    public final boolean zztR;

    public AdRequestParcel(int i, long j, Bundle bundle, int i2, List<String> list, boolean z, int i3, boolean z2, String str, SearchAdRequestParcel searchAdRequestParcel, Location location, String str2, Bundle bundle2, Bundle bundle3, List<String> list2, String str3, String str4, boolean z3) {
        this.versionCode = i;
        this.zztC = j;
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.extras = bundle;
        this.zztD = i2;
        this.zztE = list;
        this.zztF = z;
        this.zztG = i3;
        this.zztH = z2;
        this.zztI = str;
        this.zztJ = searchAdRequestParcel;
        this.zztK = location;
        this.zztL = str2;
        this.zztM = bundle2;
        this.zztN = bundle3;
        this.zztO = list2;
        this.zztP = str3;
        this.zztQ = str4;
        this.zztR = z3;
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof AdRequestParcel) {
            AdRequestParcel adRequestParcel = (AdRequestParcel) obj;
            if (this.versionCode == adRequestParcel.versionCode && this.zztC == adRequestParcel.zztC && zzw.equal(this.extras, adRequestParcel.extras) && this.zztD == adRequestParcel.zztD && zzw.equal(this.zztE, adRequestParcel.zztE) && this.zztF == adRequestParcel.zztF && this.zztG == adRequestParcel.zztG && this.zztH == adRequestParcel.zztH && zzw.equal(this.zztI, adRequestParcel.zztI) && zzw.equal(this.zztJ, adRequestParcel.zztJ) && zzw.equal(this.zztK, adRequestParcel.zztK) && zzw.equal(this.zztL, adRequestParcel.zztL) && zzw.equal(this.zztM, adRequestParcel.zztM) && zzw.equal(this.zztN, adRequestParcel.zztN) && zzw.equal(this.zztO, adRequestParcel.zztO) && zzw.equal(this.zztP, adRequestParcel.zztP) && zzw.equal(this.zztQ, adRequestParcel.zztQ) && this.zztR == adRequestParcel.zztR) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.versionCode), Long.valueOf(this.zztC), this.extras, Integer.valueOf(this.zztD), this.zztE, Boolean.valueOf(this.zztF), Integer.valueOf(this.zztG), Boolean.valueOf(this.zztH), this.zztI, this.zztJ, this.zztK, this.zztL, this.zztM, this.zztN, this.zztO, this.zztP, this.zztQ, Boolean.valueOf(this.zztR));
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzg.zza(this, parcel, i);
    }
}
