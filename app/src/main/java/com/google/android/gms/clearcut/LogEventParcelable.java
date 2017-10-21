package com.google.android.gms.clearcut;

import android.os.Parcel;
import com.google.android.gms.clearcut.zzb.zzb;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzsz.zzd;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.Arrays;

public class LogEventParcelable implements SafeParcelable {
    public static final zzd CREATOR = new zzd();
    public final int versionCode;
    public PlayLoggerContext zzafh;
    public byte[] zzafi;
    public int[] zzafj;
    public final zzd zzafk;
    public final zzb zzafl;
    public final zzb zzafm;

    LogEventParcelable(int i, PlayLoggerContext playLoggerContext, byte[] bArr, int[] iArr) {
        this.versionCode = i;
        this.zzafh = playLoggerContext;
        this.zzafi = bArr;
        this.zzafj = iArr;
        this.zzafk = null;
        this.zzafl = null;
        this.zzafm = null;
    }

    public LogEventParcelable(PlayLoggerContext playLoggerContext, zzd com_google_android_gms_internal_zzsz_zzd, zzb com_google_android_gms_clearcut_zzb_zzb, zzb com_google_android_gms_clearcut_zzb_zzb2, int[] iArr) {
        this.versionCode = 1;
        this.zzafh = playLoggerContext;
        this.zzafk = com_google_android_gms_internal_zzsz_zzd;
        this.zzafl = com_google_android_gms_clearcut_zzb_zzb;
        this.zzafm = com_google_android_gms_clearcut_zzb_zzb2;
        this.zzafj = iArr;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof LogEventParcelable)) {
                return false;
            }
            LogEventParcelable logEventParcelable = (LogEventParcelable) obj;
            if (this.versionCode != logEventParcelable.versionCode || !zzw.equal(this.zzafh, logEventParcelable.zzafh) || !Arrays.equals(this.zzafi, logEventParcelable.zzafi) || !Arrays.equals(this.zzafj, logEventParcelable.zzafj) || !zzw.equal(this.zzafk, logEventParcelable.zzafk) || !zzw.equal(this.zzafl, logEventParcelable.zzafl)) {
                return false;
            }
            if (!zzw.equal(this.zzafm, logEventParcelable.zzafm)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.versionCode), this.zzafh, this.zzafi, this.zzafj, this.zzafk, this.zzafl, this.zzafm);
    }

    public String toString() {
        String str = null;
        StringBuilder stringBuilder = new StringBuilder("LogEventParcelable[");
        stringBuilder.append(this.versionCode);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafh);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafi == null ? null : new String(this.zzafi));
        stringBuilder.append(", ");
        if (this.zzafj != null) {
            str = zzv.zzcL(", ").zza(Arrays.asList(new int[][]{this.zzafj}));
        }
        stringBuilder.append(str);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafk);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafl);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzafm);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }
}
