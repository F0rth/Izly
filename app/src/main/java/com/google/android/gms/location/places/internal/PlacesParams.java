package com.google.android.gms.location.places.internal;

import android.annotation.SuppressLint;
import android.os.Parcel;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import java.util.Locale;

public class PlacesParams implements SafeParcelable {
    public static final zzs CREATOR = new zzs();
    public static final PlacesParams zzaQW = new PlacesParams("com.google.android.gms", Locale.getDefault(), null);
    public final int versionCode;
    public final String zzaPU;
    public final String zzaQX;
    public final String zzaQY;
    public final String zzaQZ;
    public final int zzaRa;
    public final int zzaRb;

    public PlacesParams(int i, String str, String str2, String str3, String str4, int i2, int i3) {
        this.versionCode = i;
        this.zzaQX = str;
        this.zzaQY = str2;
        this.zzaQZ = str3;
        this.zzaPU = str4;
        this.zzaRa = i2;
        this.zzaRb = i3;
    }

    public PlacesParams(String str, Locale locale, String str2) {
        this(3, str, locale.toString(), str2, null, GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE, 0);
    }

    public PlacesParams(String str, Locale locale, String str2, String str3, int i) {
        this(3, str, locale.toString(), str2, str3, GoogleApiAvailability.GOOGLE_PLAY_SERVICES_VERSION_CODE, i);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || !(obj instanceof PlacesParams)) {
                return false;
            }
            PlacesParams placesParams = (PlacesParams) obj;
            if (this.zzaRa != placesParams.zzaRa || this.zzaRb != placesParams.zzaRb || !this.zzaQY.equals(placesParams.zzaQY) || !this.zzaQX.equals(placesParams.zzaQX) || !zzw.equal(this.zzaQZ, placesParams.zzaQZ)) {
                return false;
            }
            if (!zzw.equal(this.zzaPU, placesParams.zzaPU)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return zzw.hashCode(this.zzaQX, this.zzaQY, this.zzaQZ, this.zzaPU, Integer.valueOf(this.zzaRa), Integer.valueOf(this.zzaRb));
    }

    @SuppressLint({"DefaultLocale"})
    public String toString() {
        return zzw.zzy(this).zzg("clientPackageName", this.zzaQX).zzg("locale", this.zzaQY).zzg("accountName", this.zzaQZ).zzg("gCoreClientName", this.zzaPU).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzs.zza(this, parcel, i);
    }
}
