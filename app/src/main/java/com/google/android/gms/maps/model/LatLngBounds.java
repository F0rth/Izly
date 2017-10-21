package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;

public final class LatLngBounds implements SafeParcelable {
    public static final zzd CREATOR = new zzd();
    private final int mVersionCode;
    public final LatLng northeast;
    public final LatLng southwest;

    public static final class Builder {
        private double zzaTu = Double.POSITIVE_INFINITY;
        private double zzaTv = Double.NEGATIVE_INFINITY;
        private double zzaTw = Double.NaN;
        private double zzaTx = Double.NaN;

        private boolean zzj(double d) {
            if (this.zzaTw <= this.zzaTx) {
                if (this.zzaTw > d || d > this.zzaTx) {
                    return false;
                }
            } else if (this.zzaTw > d && d > this.zzaTx) {
                return false;
            }
            return true;
        }

        public final LatLngBounds build() {
            zzx.zza(!Double.isNaN(this.zzaTw), (Object) "no included points");
            return new LatLngBounds(new LatLng(this.zzaTu, this.zzaTw), new LatLng(this.zzaTv, this.zzaTx));
        }

        public final Builder include(LatLng latLng) {
            this.zzaTu = Math.min(this.zzaTu, latLng.latitude);
            this.zzaTv = Math.max(this.zzaTv, latLng.latitude);
            double d = latLng.longitude;
            if (Double.isNaN(this.zzaTw)) {
                this.zzaTw = d;
            } else {
                if (!zzj(d)) {
                    if (LatLngBounds.zzb(this.zzaTw, d) < LatLngBounds.zzc(this.zzaTx, d)) {
                        this.zzaTw = d;
                    }
                }
                return this;
            }
            this.zzaTx = d;
            return this;
        }
    }

    LatLngBounds(int i, LatLng latLng, LatLng latLng2) {
        zzx.zzb((Object) latLng, (Object) "null southwest");
        zzx.zzb((Object) latLng2, (Object) "null northeast");
        zzx.zzb(latLng2.latitude >= latLng.latitude, "southern latitude exceeds northern latitude (%s > %s)", Double.valueOf(latLng.latitude), Double.valueOf(latLng2.latitude));
        this.mVersionCode = i;
        this.southwest = latLng;
        this.northeast = latLng2;
    }

    public LatLngBounds(LatLng latLng, LatLng latLng2) {
        this(1, latLng, latLng2);
    }

    public static Builder builder() {
        return new Builder();
    }

    private static double zzb(double d, double d2) {
        return ((d - d2) + 360.0d) % 360.0d;
    }

    private static double zzc(double d, double d2) {
        return ((d2 - d) + 360.0d) % 360.0d;
    }

    private boolean zzi(double d) {
        return this.southwest.latitude <= d && d <= this.northeast.latitude;
    }

    private boolean zzj(double d) {
        if (this.southwest.longitude <= this.northeast.longitude) {
            if (this.southwest.longitude > d || d > this.northeast.longitude) {
                return false;
            }
        } else if (this.southwest.longitude > d && d > this.northeast.longitude) {
            return false;
        }
        return true;
    }

    public final boolean contains(LatLng latLng) {
        return zzi(latLng.latitude) && zzj(latLng.longitude);
    }

    public final int describeContents() {
        return 0;
    }

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (!(obj instanceof LatLngBounds)) {
                return false;
            }
            LatLngBounds latLngBounds = (LatLngBounds) obj;
            if (!this.southwest.equals(latLngBounds.southwest)) {
                return false;
            }
            if (!this.northeast.equals(latLngBounds.northeast)) {
                return false;
            }
        }
        return true;
    }

    public final LatLng getCenter() {
        double d = (this.southwest.latitude + this.northeast.latitude) / 2.0d;
        double d2 = this.northeast.longitude;
        double d3 = this.southwest.longitude;
        return new LatLng(d, d3 <= d2 ? (d2 + d3) / 2.0d : ((d2 + 360.0d) + d3) / 2.0d);
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final int hashCode() {
        return zzw.hashCode(this.southwest, this.northeast);
    }

    public final LatLngBounds including(LatLng latLng) {
        double min = Math.min(this.southwest.latitude, latLng.latitude);
        double max = Math.max(this.northeast.latitude, latLng.latitude);
        double d = this.northeast.longitude;
        double d2 = this.southwest.longitude;
        double d3 = latLng.longitude;
        if (zzj(d3)) {
            d3 = d;
            d = d2;
        } else if (zzb(d2, d3) < zzc(d, d3)) {
            double d4 = d;
            d = d3;
            d3 = d4;
        } else {
            d = d2;
        }
        return new LatLngBounds(new LatLng(min, d), new LatLng(max, d3));
    }

    public final String toString() {
        return zzw.zzy(this).zzg("southwest", this.southwest).zzg("northeast", this.northeast).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }
}
