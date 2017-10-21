package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class StreetViewPanoramaLink implements SafeParcelable {
    public static final zzk CREATOR = new zzk();
    public final float bearing;
    private final int mVersionCode;
    public final String panoId;

    StreetViewPanoramaLink(int i, String str, float f) {
        this.mVersionCode = i;
        this.panoId = str;
        if (((double) f) <= 0.0d) {
            f = (f % 360.0f) + 360.0f;
        }
        this.bearing = f % 360.0f;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof StreetViewPanoramaLink)) {
                return false;
            }
            StreetViewPanoramaLink streetViewPanoramaLink = (StreetViewPanoramaLink) obj;
            if (!this.panoId.equals(streetViewPanoramaLink.panoId)) {
                return false;
            }
            if (Float.floatToIntBits(this.bearing) != Float.floatToIntBits(streetViewPanoramaLink.bearing)) {
                return false;
            }
        }
        return true;
    }

    int getVersionCode() {
        return this.mVersionCode;
    }

    public int hashCode() {
        return zzw.hashCode(this.panoId, Float.valueOf(this.bearing));
    }

    public String toString() {
        return zzw.zzy(this).zzg("panoId", this.panoId).zzg("bearing", Float.valueOf(this.bearing)).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzk.zza(this, parcel, i);
    }
}
