package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;

public class PlayLoggerContext implements SafeParcelable {
    public static final zze CREATOR = new zze();
    public final String packageName;
    public final int versionCode;
    public final int zzbdL;
    public final int zzbdM;
    public final String zzbdN;
    public final String zzbdO;
    public final boolean zzbdP;
    public final String zzbdQ;
    public final boolean zzbdR;
    public final int zzbdS;

    public PlayLoggerContext(int i, String str, int i2, int i3, String str2, String str3, boolean z, String str4, boolean z2, int i4) {
        this.versionCode = i;
        this.packageName = str;
        this.zzbdL = i2;
        this.zzbdM = i3;
        this.zzbdN = str2;
        this.zzbdO = str3;
        this.zzbdP = z;
        this.zzbdQ = str4;
        this.zzbdR = z2;
        this.zzbdS = i4;
    }

    public PlayLoggerContext(String str, int i, int i2, String str2, String str3, String str4, boolean z, int i3) {
        this.versionCode = 1;
        this.packageName = (String) zzx.zzz(str);
        this.zzbdL = i;
        this.zzbdM = i2;
        this.zzbdQ = str2;
        this.zzbdN = str3;
        this.zzbdO = str4;
        this.zzbdP = !z;
        this.zzbdR = z;
        this.zzbdS = i3;
    }

    @Deprecated
    public PlayLoggerContext(String str, int i, int i2, String str2, String str3, boolean z) {
        this.versionCode = 1;
        this.packageName = (String) zzx.zzz(str);
        this.zzbdL = i;
        this.zzbdM = i2;
        this.zzbdQ = null;
        this.zzbdN = str2;
        this.zzbdO = str3;
        this.zzbdP = z;
        this.zzbdR = false;
        this.zzbdS = 0;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof PlayLoggerContext)) {
                return false;
            }
            PlayLoggerContext playLoggerContext = (PlayLoggerContext) obj;
            if (this.versionCode != playLoggerContext.versionCode || !this.packageName.equals(playLoggerContext.packageName) || this.zzbdL != playLoggerContext.zzbdL || this.zzbdM != playLoggerContext.zzbdM || !zzw.equal(this.zzbdQ, playLoggerContext.zzbdQ) || !zzw.equal(this.zzbdN, playLoggerContext.zzbdN) || !zzw.equal(this.zzbdO, playLoggerContext.zzbdO) || this.zzbdP != playLoggerContext.zzbdP || this.zzbdR != playLoggerContext.zzbdR) {
                return false;
            }
            if (this.zzbdS != playLoggerContext.zzbdS) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.versionCode), this.packageName, Integer.valueOf(this.zzbdL), Integer.valueOf(this.zzbdM), this.zzbdQ, this.zzbdN, this.zzbdO, Boolean.valueOf(this.zzbdP), Boolean.valueOf(this.zzbdR), Integer.valueOf(this.zzbdS));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PlayLoggerContext[");
        stringBuilder.append("versionCode=").append(this.versionCode).append(',');
        stringBuilder.append("package=").append(this.packageName).append(',');
        stringBuilder.append("packageVersionCode=").append(this.zzbdL).append(',');
        stringBuilder.append("logSource=").append(this.zzbdM).append(',');
        stringBuilder.append("logSourceName=").append(this.zzbdQ).append(',');
        stringBuilder.append("uploadAccount=").append(this.zzbdN).append(',');
        stringBuilder.append("loggingId=").append(this.zzbdO).append(',');
        stringBuilder.append("logAndroidId=").append(this.zzbdP).append(',');
        stringBuilder.append("isAnonymous=").append(this.zzbdR).append(',');
        stringBuilder.append("qosTier=").append(this.zzbdS);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }
}
