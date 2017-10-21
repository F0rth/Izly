package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.List;

@Deprecated
public final class PlaceLocalization implements SafeParcelable {
    public static final zzo CREATOR = new zzo();
    public final String address;
    public final String name;
    public final int versionCode;
    public final String zzaQO;
    public final String zzaQP;
    public final List<String> zzaQQ;

    public PlaceLocalization(int i, String str, String str2, String str3, String str4, List<String> list) {
        this.versionCode = i;
        this.name = str;
        this.address = str2;
        this.zzaQO = str3;
        this.zzaQP = str4;
        this.zzaQQ = list;
    }

    public static PlaceLocalization zza(String str, String str2, String str3, String str4, List<String> list) {
        return new PlaceLocalization(0, str, str2, str3, str4, list);
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof PlaceLocalization)) {
                return false;
            }
            PlaceLocalization placeLocalization = (PlaceLocalization) obj;
            if (!zzw.equal(this.name, placeLocalization.name) || !zzw.equal(this.address, placeLocalization.address) || !zzw.equal(this.zzaQO, placeLocalization.zzaQO) || !zzw.equal(this.zzaQP, placeLocalization.zzaQP)) {
                return false;
            }
            if (!zzw.equal(this.zzaQQ, placeLocalization.zzaQQ)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return zzw.hashCode(this.name, this.address, this.zzaQO, this.zzaQP);
    }

    public final String toString() {
        return zzw.zzy(this).zzg("name", this.name).zzg("address", this.address).zzg("internationalPhoneNumber", this.zzaQO).zzg("regularOpenHours", this.zzaQP).zzg("attributions", this.zzaQQ).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }
}
