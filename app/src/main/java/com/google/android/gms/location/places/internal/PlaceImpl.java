package com.google.android.gms.location.places.internal;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class PlaceImpl implements SafeParcelable, Place {
    public static final zzl CREATOR = new zzl();
    private final String mName;
    final int mVersionCode;
    private final LatLng zzaPc;
    private final List<Integer> zzaPd;
    private final String zzaPe;
    private final Uri zzaPf;
    private final String zzaQA;
    private final boolean zzaQB;
    private final float zzaQC;
    private final int zzaQD;
    private final long zzaQE;
    private final List<Integer> zzaQF;
    private final String zzaQG;
    private final List<String> zzaQH;
    private final Map<Integer, String> zzaQI;
    private final TimeZone zzaQJ;
    private Locale zzaQr;
    private final Bundle zzaQw;
    @Deprecated
    private final PlaceLocalization zzaQx;
    private final float zzaQy;
    private final LatLngBounds zzaQz;
    private final String zzawc;
    private final String zzyv;

    public static class zza {
        private String mName;
        private int mVersionCode = 0;
        private LatLng zzaPc;
        private String zzaPe;
        private Uri zzaPf;
        private String zzaQA;
        private boolean zzaQB;
        private float zzaQC;
        private int zzaQD;
        private long zzaQE;
        private String zzaQG;
        private List<String> zzaQH;
        private Bundle zzaQK;
        private List<Integer> zzaQL;
        private float zzaQy;
        private LatLngBounds zzaQz;
        private String zzawc;
        private String zzyv;

        public zza zza(LatLng latLng) {
            this.zzaPc = latLng;
            return this;
        }

        public zza zza(LatLngBounds latLngBounds) {
            this.zzaQz = latLngBounds;
            return this;
        }

        public zza zzan(boolean z) {
            this.zzaQB = z;
            return this;
        }

        public zza zzem(String str) {
            this.zzyv = str;
            return this;
        }

        public zza zzen(String str) {
            this.mName = str;
            return this;
        }

        public zza zzeo(String str) {
            this.zzawc = str;
            return this;
        }

        public zza zzep(String str) {
            this.zzaPe = str;
            return this;
        }

        public zza zzf(float f) {
            this.zzaQy = f;
            return this;
        }

        public zza zzg(float f) {
            this.zzaQC = f;
            return this;
        }

        public zza zzhX(int i) {
            this.zzaQD = i;
            return this;
        }

        public zza zzo(Uri uri) {
            this.zzaPf = uri;
            return this;
        }

        public zza zzx(List<Integer> list) {
            this.zzaQL = list;
            return this;
        }

        public zza zzy(List<String> list) {
            this.zzaQH = list;
            return this;
        }

        public PlaceImpl zzzx() {
            return new PlaceImpl(this.mVersionCode, this.zzyv, this.zzaQL, Collections.emptyList(), this.zzaQK, this.mName, this.zzawc, this.zzaPe, this.zzaQG, this.zzaQH, this.zzaPc, this.zzaQy, this.zzaQz, this.zzaQA, this.zzaPf, this.zzaQB, this.zzaQC, this.zzaQD, this.zzaQE, PlaceLocalization.zza(this.mName, this.zzawc, this.zzaPe, this.zzaQG, this.zzaQH));
        }
    }

    PlaceImpl(int i, String str, List<Integer> list, List<Integer> list2, Bundle bundle, String str2, String str3, String str4, String str5, List<String> list3, LatLng latLng, float f, LatLngBounds latLngBounds, String str6, Uri uri, boolean z, float f2, int i2, long j, PlaceLocalization placeLocalization) {
        List emptyList;
        this.mVersionCode = i;
        this.zzyv = str;
        this.zzaPd = Collections.unmodifiableList(list);
        this.zzaQF = list2;
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzaQw = bundle;
        this.mName = str2;
        this.zzawc = str3;
        this.zzaPe = str4;
        this.zzaQG = str5;
        if (list3 == null) {
            emptyList = Collections.emptyList();
        }
        this.zzaQH = emptyList;
        this.zzaPc = latLng;
        this.zzaQy = f;
        this.zzaQz = latLngBounds;
        if (str6 == null) {
            str6 = "UTC";
        }
        this.zzaQA = str6;
        this.zzaPf = uri;
        this.zzaQB = z;
        this.zzaQC = f2;
        this.zzaQD = i2;
        this.zzaQE = j;
        this.zzaQI = Collections.unmodifiableMap(new HashMap());
        this.zzaQJ = null;
        this.zzaQr = null;
        this.zzaQx = placeLocalization;
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof PlaceImpl)) {
                return false;
            }
            PlaceImpl placeImpl = (PlaceImpl) obj;
            if (!this.zzyv.equals(placeImpl.zzyv) || !zzw.equal(this.zzaQr, placeImpl.zzaQr)) {
                return false;
            }
            if (this.zzaQE != placeImpl.zzaQE) {
                return false;
            }
        }
        return true;
    }

    public final /* synthetic */ Object freeze() {
        return zzzw();
    }

    public final String getAddress() {
        return this.zzawc;
    }

    public final CharSequence getAttributions() {
        return zzc.zzj(this.zzaQH);
    }

    public final String getId() {
        return this.zzyv;
    }

    public final LatLng getLatLng() {
        return this.zzaPc;
    }

    public final Locale getLocale() {
        return this.zzaQr;
    }

    public final String getName() {
        return this.mName;
    }

    public final String getPhoneNumber() {
        return this.zzaPe;
    }

    public final List<Integer> getPlaceTypes() {
        return this.zzaPd;
    }

    public final int getPriceLevel() {
        return this.zzaQD;
    }

    public final float getRating() {
        return this.zzaQC;
    }

    public final LatLngBounds getViewport() {
        return this.zzaQz;
    }

    public final Uri getWebsiteUri() {
        return this.zzaPf;
    }

    public final int hashCode() {
        return zzw.hashCode(this.zzyv, this.zzaQr, Long.valueOf(this.zzaQE));
    }

    public final boolean isDataValid() {
        return true;
    }

    public final void setLocale(Locale locale) {
        this.zzaQr = locale;
    }

    @SuppressLint({"DefaultLocale"})
    public final String toString() {
        return zzw.zzy(this).zzg("id", this.zzyv).zzg("placeTypes", this.zzaPd).zzg("locale", this.zzaQr).zzg("name", this.mName).zzg("address", this.zzawc).zzg("phoneNumber", this.zzaPe).zzg("latlng", this.zzaPc).zzg("viewport", this.zzaQz).zzg("websiteUri", this.zzaPf).zzg("isPermanentlyClosed", Boolean.valueOf(this.zzaQB)).zzg("priceLevel", Integer.valueOf(this.zzaQD)).zzg("timestampSecs", Long.valueOf(this.zzaQE)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzl.zza(this, parcel, i);
    }

    public final List<Integer> zzzn() {
        return this.zzaQF;
    }

    public final float zzzo() {
        return this.zzaQy;
    }

    public final String zzzp() {
        return this.zzaQG;
    }

    public final List<String> zzzq() {
        return this.zzaQH;
    }

    public final boolean zzzr() {
        return this.zzaQB;
    }

    public final long zzzs() {
        return this.zzaQE;
    }

    public final Bundle zzzt() {
        return this.zzaQw;
    }

    public final String zzzu() {
        return this.zzaQA;
    }

    @Deprecated
    public final PlaceLocalization zzzv() {
        return this.zzaQx;
    }

    public final Place zzzw() {
        return this;
    }
}
