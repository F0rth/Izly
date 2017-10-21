package com.google.android.gms.location.places;

import android.os.Parcel;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzw.zza;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class NearbyAlertFilter extends zza implements SafeParcelable {
    public static final zzd CREATOR = new zzd();
    final int mVersionCode;
    final List<String> zzaPj;
    final List<Integer> zzaPk;
    final List<UserDataType> zzaPl;
    final String zzaPm;
    final boolean zzaPn;
    private final Set<String> zzaPo;
    private final Set<Integer> zzaPp;
    private final Set<UserDataType> zzaPq;

    NearbyAlertFilter(int i, @Nullable List<String> list, @Nullable List<Integer> list2, @Nullable List<UserDataType> list3, @Nullable String str, boolean z) {
        this.mVersionCode = i;
        this.zzaPk = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.zzaPl = list3 == null ? Collections.emptyList() : Collections.unmodifiableList(list3);
        this.zzaPj = list == null ? Collections.emptyList() : Collections.unmodifiableList(list);
        this.zzaPp = zza.zzw(this.zzaPk);
        this.zzaPq = zza.zzw(this.zzaPl);
        this.zzaPo = zza.zzw(this.zzaPj);
        this.zzaPm = str;
        this.zzaPn = z;
    }

    public static NearbyAlertFilter zzh(Collection<String> collection) {
        if (collection != null && !collection.isEmpty()) {
            return new NearbyAlertFilter(0, zza.zzf(collection), null, null, null, false);
        }
        throw new IllegalArgumentException("NearbyAlertFilters must contain at least oneplace ID to match results with.");
    }

    public static NearbyAlertFilter zzi(Collection<Integer> collection) {
        if (collection != null && !collection.isEmpty()) {
            return new NearbyAlertFilter(0, null, zza.zzf(collection), null, null, false);
        }
        throw new IllegalArgumentException("NearbyAlertFilters must contain at least oneplace type to match results with.");
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof NearbyAlertFilter)) {
                return false;
            }
            NearbyAlertFilter nearbyAlertFilter = (NearbyAlertFilter) obj;
            if ((this.zzaPm == null && nearbyAlertFilter.zzaPm != null) || !this.zzaPp.equals(nearbyAlertFilter.zzaPp) || !this.zzaPq.equals(nearbyAlertFilter.zzaPq) || !this.zzaPo.equals(nearbyAlertFilter.zzaPo)) {
                return false;
            }
            if (this.zzaPm != null && !this.zzaPm.equals(nearbyAlertFilter.zzaPm)) {
                return false;
            }
            if (this.zzaPn != nearbyAlertFilter.zzyX()) {
                return false;
            }
        }
        return true;
    }

    public final Set<String> getPlaceIds() {
        return this.zzaPo;
    }

    public final int hashCode() {
        return zzw.hashCode(this.zzaPp, this.zzaPq, this.zzaPo, this.zzaPm, Boolean.valueOf(this.zzaPn));
    }

    public final String toString() {
        zza zzy = zzw.zzy(this);
        if (!this.zzaPp.isEmpty()) {
            zzy.zzg("types", this.zzaPp);
        }
        if (!this.zzaPo.isEmpty()) {
            zzy.zzg("placeIds", this.zzaPo);
        }
        if (!this.zzaPq.isEmpty()) {
            zzy.zzg("requestedUserDataTypes", this.zzaPq);
        }
        if (this.zzaPm != null) {
            zzy.zzg("chainName", this.zzaPm);
        }
        zzy.zzg("Beacon required: ", Boolean.valueOf(this.zzaPn));
        return zzy.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }

    public final boolean zzyX() {
        return this.zzaPn;
    }
}
