package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public final class EventParcel implements SafeParcelable {
    public static final zzk CREATOR = new zzk();
    public final String name;
    public final int versionCode;
    public final EventParams zzaVV;
    public final String zzaVW;
    public final long zzaVX;

    EventParcel(int i, String str, EventParams eventParams, String str2, long j) {
        this.versionCode = i;
        this.name = str;
        this.zzaVV = eventParams;
        this.zzaVW = str2;
        this.zzaVX = j;
    }

    public EventParcel(String str, EventParams eventParams, String str2, long j) {
        this.versionCode = 1;
        this.name = str;
        this.zzaVV = eventParams;
        this.zzaVW = str2;
        this.zzaVX = j;
    }

    public final int describeContents() {
        return 0;
    }

    public final String toString() {
        return "origin=" + this.zzaVW + ",name=" + this.name + ",params=" + this.zzaVV;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzk.zza(this, parcel, i);
    }
}
