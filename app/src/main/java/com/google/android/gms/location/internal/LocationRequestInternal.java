package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.location.LocationRequest;
import java.util.Collections;
import java.util.List;

public class LocationRequestInternal implements SafeParcelable {
    public static final zzm CREATOR = new zzm();
    static final List<ClientIdentity> zzaOO = Collections.emptyList();
    @Nullable
    String mTag;
    private final int mVersionCode;
    LocationRequest zzaBp;
    boolean zzaOP;
    boolean zzaOQ;
    boolean zzaOR;
    List<ClientIdentity> zzaOS;
    boolean zzaOT;

    LocationRequestInternal(int i, LocationRequest locationRequest, boolean z, boolean z2, boolean z3, List<ClientIdentity> list, @Nullable String str, boolean z4) {
        this.mVersionCode = i;
        this.zzaBp = locationRequest;
        this.zzaOP = z;
        this.zzaOQ = z2;
        this.zzaOR = z3;
        this.zzaOS = list;
        this.mTag = str;
        this.zzaOT = z4;
    }

    public static LocationRequestInternal zza(@Nullable String str, LocationRequest locationRequest) {
        return new LocationRequestInternal(1, locationRequest, false, true, true, zzaOO, str, false);
    }

    @Deprecated
    public static LocationRequestInternal zzb(LocationRequest locationRequest) {
        return zza(null, locationRequest);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LocationRequestInternal) {
            LocationRequestInternal locationRequestInternal = (LocationRequestInternal) obj;
            if (zzw.equal(this.zzaBp, locationRequestInternal.zzaBp) && this.zzaOP == locationRequestInternal.zzaOP && this.zzaOQ == locationRequestInternal.zzaOQ && this.zzaOR == locationRequestInternal.zzaOR && this.zzaOT == locationRequestInternal.zzaOT && zzw.equal(this.zzaOS, locationRequestInternal.zzaOS)) {
                return true;
            }
        }
        return false;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return this.zzaBp.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.zzaBp.toString());
        if (this.mTag != null) {
            stringBuilder.append(" tag=").append(this.mTag);
        }
        stringBuilder.append(" nlpDebug=").append(this.zzaOP);
        stringBuilder.append(" trigger=").append(this.zzaOR);
        stringBuilder.append(" restorePIListeners=").append(this.zzaOQ);
        stringBuilder.append(" hideAppOps=").append(this.zzaOT);
        stringBuilder.append(" clients=").append(this.zzaOS);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzm.zza(this, parcel, i);
    }
}
