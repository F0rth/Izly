package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class AppMetadata implements SafeParcelable {
    public static final zzb CREATOR = new zzb();
    public final String packageName;
    public final int versionCode;
    public final String zzaMV;
    public final String zzaVt;
    public final String zzaVu;
    public final long zzaVv;
    public final long zzaVw;
    public final String zzaVx;
    public final boolean zzaVy;
    public final boolean zzaVz;

    AppMetadata(int i, String str, String str2, String str3, String str4, long j, long j2, String str5, boolean z, boolean z2) {
        this.versionCode = i;
        this.packageName = str;
        this.zzaVt = str2;
        this.zzaMV = str3;
        this.zzaVu = str4;
        this.zzaVv = j;
        this.zzaVw = j2;
        this.zzaVx = str5;
        if (i >= 3) {
            this.zzaVy = z;
        } else {
            this.zzaVy = true;
        }
        this.zzaVz = z2;
    }

    AppMetadata(String str, String str2, String str3, String str4, long j, long j2, String str5, boolean z, boolean z2) {
        zzx.zzcM(str);
        this.versionCode = 4;
        this.packageName = str;
        if (TextUtils.isEmpty(str2)) {
            str2 = null;
        }
        this.zzaVt = str2;
        this.zzaMV = str3;
        this.zzaVu = str4;
        this.zzaVv = j;
        this.zzaVw = j2;
        this.zzaVx = str5;
        this.zzaVy = z;
        this.zzaVz = z2;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }
}
