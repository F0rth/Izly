package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.List;

public class PlaceUserData implements SafeParcelable {
    public static final zze CREATOR = new zze();
    final int mVersionCode;
    private final String zzVa;
    private final String zzaPH;
    private final List<PlaceAlias> zzaRg;

    PlaceUserData(int i, String str, String str2, List<PlaceAlias> list) {
        this.mVersionCode = i;
        this.zzVa = str;
        this.zzaPH = str2;
        this.zzaRg = list;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof PlaceUserData)) {
                return false;
            }
            PlaceUserData placeUserData = (PlaceUserData) obj;
            if (!this.zzVa.equals(placeUserData.zzVa) || !this.zzaPH.equals(placeUserData.zzaPH)) {
                return false;
            }
            if (!this.zzaRg.equals(placeUserData.zzaRg)) {
                return false;
            }
        }
        return true;
    }

    public String getPlaceId() {
        return this.zzaPH;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzVa, this.zzaPH, this.zzaRg);
    }

    public String toString() {
        return zzw.zzy(this).zzg("accountName", this.zzVa).zzg("placeId", this.zzaPH).zzg("placeAliases", this.zzaRg).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zze.zza(this, parcel, i);
    }

    public String zzzD() {
        return this.zzVa;
    }

    public List<PlaceAlias> zzzE() {
        return this.zzaRg;
    }
}
