package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class ConnectionEvent extends zzf implements SafeParcelable {
    public static final Creator<ConnectionEvent> CREATOR = new zza();
    final int mVersionCode;
    private final long zzane;
    private int zzanf;
    private final String zzang;
    private final String zzanh;
    private final String zzani;
    private final String zzanj;
    private final String zzank;
    private final String zzanl;
    private final long zzanm;
    private final long zzann;
    private long zzano;

    ConnectionEvent(int i, long j, int i2, String str, String str2, String str3, String str4, String str5, String str6, long j2, long j3) {
        this.mVersionCode = i;
        this.zzane = j;
        this.zzanf = i2;
        this.zzang = str;
        this.zzanh = str2;
        this.zzani = str3;
        this.zzanj = str4;
        this.zzano = -1;
        this.zzank = str5;
        this.zzanl = str6;
        this.zzanm = j2;
        this.zzann = j3;
    }

    public ConnectionEvent(long j, int i, String str, String str2, String str3, String str4, String str5, String str6, long j2, long j3) {
        this(1, j, i, str, str2, str3, str4, str5, str6, j2, j3);
    }

    public final int describeContents() {
        return 0;
    }

    public final int getEventType() {
        return this.zzanf;
    }

    public final long getTimeMillis() {
        return this.zzane;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }

    public final String zzrF() {
        return this.zzang;
    }

    public final String zzrG() {
        return this.zzanh;
    }

    public final String zzrH() {
        return this.zzani;
    }

    public final String zzrI() {
        return this.zzanj;
    }

    public final String zzrJ() {
        return this.zzank;
    }

    public final String zzrK() {
        return this.zzanl;
    }

    public final long zzrL() {
        return this.zzano;
    }

    public final long zzrM() {
        return this.zzann;
    }

    public final long zzrN() {
        return this.zzanm;
    }

    public final String zzrO() {
        return "\t" + zzrF() + "/" + zzrG() + "\t" + zzrH() + "/" + zzrI() + "\t" + (this.zzank == null ? "" : this.zzank) + "\t" + zzrM();
    }
}
