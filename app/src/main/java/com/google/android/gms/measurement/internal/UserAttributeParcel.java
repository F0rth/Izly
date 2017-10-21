package com.google.android.gms.measurement.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;

public class UserAttributeParcel implements SafeParcelable {
    public static final zzah CREATOR = new zzah();
    public final String name;
    public final int versionCode;
    public final String zzaVW;
    public final long zzaZm;
    public final Long zzaZn;
    public final Float zzaZo;
    public final String zzamJ;

    UserAttributeParcel(int i, String str, long j, Long l, Float f, String str2, String str3) {
        this.versionCode = i;
        this.name = str;
        this.zzaZm = j;
        this.zzaZn = l;
        this.zzaZo = f;
        this.zzamJ = str2;
        this.zzaVW = str3;
    }

    UserAttributeParcel(zzai com_google_android_gms_measurement_internal_zzai) {
        this(com_google_android_gms_measurement_internal_zzai.mName, com_google_android_gms_measurement_internal_zzai.zzaZp, com_google_android_gms_measurement_internal_zzai.zzNc, com_google_android_gms_measurement_internal_zzai.zzaUa);
    }

    UserAttributeParcel(String str, long j, Object obj, String str2) {
        zzx.zzcM(str);
        this.versionCode = 1;
        this.name = str;
        this.zzaZm = j;
        this.zzaVW = str2;
        if (obj == null) {
            this.zzaZn = null;
            this.zzaZo = null;
            this.zzamJ = null;
        } else if (obj instanceof Long) {
            this.zzaZn = (Long) obj;
            this.zzaZo = null;
            this.zzamJ = null;
        } else if (obj instanceof Float) {
            this.zzaZn = null;
            this.zzaZo = (Float) obj;
            this.zzamJ = null;
        } else if (obj instanceof String) {
            this.zzaZn = null;
            this.zzaZo = null;
            this.zzamJ = (String) obj;
        } else {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
    }

    public int describeContents() {
        return 0;
    }

    public Object getValue() {
        return this.zzaZn != null ? this.zzaZn : this.zzaZo != null ? this.zzaZo : this.zzamJ != null ? this.zzamJ : null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzah.zza(this, parcel, i);
    }
}
