package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class LogEvent implements SafeParcelable {
    public static final zzc CREATOR = new zzc();
    public final String tag;
    public final int versionCode;
    public final long zzbdA;
    public final long zzbdB;
    public final byte[] zzbdC;
    public final Bundle zzbdD;

    LogEvent(int i, long j, long j2, String str, byte[] bArr, Bundle bundle) {
        this.versionCode = i;
        this.zzbdA = j;
        this.zzbdB = j2;
        this.tag = str;
        this.zzbdC = bArr;
        this.zzbdD = bundle;
    }

    public LogEvent(long j, long j2, String str, byte[] bArr, String... strArr) {
        this.versionCode = 1;
        this.zzbdA = j;
        this.zzbdB = j2;
        this.tag = str;
        this.zzbdC = bArr;
        this.zzbdD = zzd(strArr);
    }

    private static Bundle zzd(String... strArr) {
        Bundle bundle = null;
        if (strArr != null) {
            if (strArr.length % 2 != 0) {
                throw new IllegalArgumentException("extras must have an even number of elements");
            }
            int length = strArr.length / 2;
            if (length != 0) {
                bundle = new Bundle(length);
                for (int i = 0; i < length; i++) {
                    bundle.putString(strArr[i * 2], strArr[(i * 2) + 1]);
                }
            }
        }
        return bundle;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tag=").append(this.tag).append(",");
        stringBuilder.append("eventTime=").append(this.zzbdA).append(",");
        stringBuilder.append("eventUptime=").append(this.zzbdB).append(",");
        if (!(this.zzbdD == null || this.zzbdD.isEmpty())) {
            stringBuilder.append("keyValues=");
            for (String str : this.zzbdD.keySet()) {
                stringBuilder.append("(").append(str).append(",");
                stringBuilder.append(this.zzbdD.getString(str)).append(")");
                stringBuilder.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR);
            }
        }
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }
}
