package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult implements SafeParcelable {
    public static final Creator<LocationResult> CREATOR = new zze();
    static final List<Location> zzaOd = Collections.emptyList();
    private final int mVersionCode;
    private final List<Location> zzaOe;

    LocationResult(int i, List<Location> list) {
        this.mVersionCode = i;
        this.zzaOe = list;
    }

    public static LocationResult create(List<Location> list) {
        List list2;
        if (list == null) {
            list2 = zzaOd;
        }
        return new LocationResult(2, list2);
    }

    public static LocationResult extractResult(Intent intent) {
        return !hasResult(intent) ? null : (LocationResult) intent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public static boolean hasResult(Intent intent) {
        return intent == null ? false : intent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof LocationResult)) {
            return false;
        }
        LocationResult locationResult = (LocationResult) obj;
        if (locationResult.zzaOe.size() != this.zzaOe.size()) {
            return false;
        }
        Iterator it = this.zzaOe.iterator();
        for (Location time : locationResult.zzaOe) {
            if (((Location) it.next()).getTime() != time.getTime()) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    public final Location getLastLocation() {
        int size = this.zzaOe.size();
        return size == 0 ? null : (Location) this.zzaOe.get(size - 1);
    }

    @NonNull
    public final List<Location> getLocations() {
        return this.zzaOe;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final int hashCode() {
        int i = 17;
        for (Location time : this.zzaOe) {
            long time2 = time.getTime();
            i = ((int) (time2 ^ (time2 >>> 32))) + (i * 31);
        }
        return i;
    }

    public final String toString() {
        return "LocationResult[locations: " + this.zzaOe + "]";
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }
}
