package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.dynamic.zzd.zza;

public final class GroundOverlayOptions implements SafeParcelable {
    public static final zzc CREATOR = new zzc();
    public static final float NO_DIMENSION = -1.0f;
    private final int mVersionCode;
    private LatLngBounds zzaRk;
    private float zzaTa;
    private float zzaTh;
    private boolean zzaTi;
    private BitmapDescriptor zzaTk;
    private LatLng zzaTl;
    private float zzaTm;
    private float zzaTn;
    private float zzaTo;
    private float zzaTp;
    private float zzaTq;
    private boolean zzaTr;

    public GroundOverlayOptions() {
        this.zzaTi = true;
        this.zzaTo = 0.0f;
        this.zzaTp = 0.5f;
        this.zzaTq = 0.5f;
        this.zzaTr = false;
        this.mVersionCode = 1;
    }

    GroundOverlayOptions(int i, IBinder iBinder, LatLng latLng, float f, float f2, LatLngBounds latLngBounds, float f3, float f4, boolean z, float f5, float f6, float f7, boolean z2) {
        this.zzaTi = true;
        this.zzaTo = 0.0f;
        this.zzaTp = 0.5f;
        this.zzaTq = 0.5f;
        this.zzaTr = false;
        this.mVersionCode = i;
        this.zzaTk = new BitmapDescriptor(zza.zzbs(iBinder));
        this.zzaTl = latLng;
        this.zzaTm = f;
        this.zzaTn = f2;
        this.zzaRk = latLngBounds;
        this.zzaTa = f3;
        this.zzaTh = f4;
        this.zzaTi = z;
        this.zzaTo = f5;
        this.zzaTp = f6;
        this.zzaTq = f7;
        this.zzaTr = z2;
    }

    private GroundOverlayOptions zza(LatLng latLng, float f, float f2) {
        this.zzaTl = latLng;
        this.zzaTm = f;
        this.zzaTn = f2;
        return this;
    }

    public final GroundOverlayOptions anchor(float f, float f2) {
        this.zzaTp = f;
        this.zzaTq = f2;
        return this;
    }

    public final GroundOverlayOptions bearing(float f) {
        this.zzaTa = ((f % 360.0f) + 360.0f) % 360.0f;
        return this;
    }

    public final GroundOverlayOptions clickable(boolean z) {
        this.zzaTr = z;
        return this;
    }

    public final int describeContents() {
        return 0;
    }

    public final float getAnchorU() {
        return this.zzaTp;
    }

    public final float getAnchorV() {
        return this.zzaTq;
    }

    public final float getBearing() {
        return this.zzaTa;
    }

    public final LatLngBounds getBounds() {
        return this.zzaRk;
    }

    public final float getHeight() {
        return this.zzaTn;
    }

    public final BitmapDescriptor getImage() {
        return this.zzaTk;
    }

    public final LatLng getLocation() {
        return this.zzaTl;
    }

    public final float getTransparency() {
        return this.zzaTo;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final float getWidth() {
        return this.zzaTm;
    }

    public final float getZIndex() {
        return this.zzaTh;
    }

    public final GroundOverlayOptions image(BitmapDescriptor bitmapDescriptor) {
        this.zzaTk = bitmapDescriptor;
        return this;
    }

    public final boolean isClickable() {
        return this.zzaTr;
    }

    public final boolean isVisible() {
        return this.zzaTi;
    }

    public final GroundOverlayOptions position(LatLng latLng, float f) {
        boolean z = true;
        zzx.zza(this.zzaRk == null, (Object) "Position has already been set using positionFromBounds");
        zzx.zzb(latLng != null, (Object) "Location must be specified");
        if (f < 0.0f) {
            z = false;
        }
        zzx.zzb(z, (Object) "Width must be non-negative");
        return zza(latLng, f, NO_DIMENSION);
    }

    public final GroundOverlayOptions position(LatLng latLng, float f, float f2) {
        boolean z = true;
        zzx.zza(this.zzaRk == null, (Object) "Position has already been set using positionFromBounds");
        zzx.zzb(latLng != null, (Object) "Location must be specified");
        zzx.zzb(f >= 0.0f, (Object) "Width must be non-negative");
        if (f2 < 0.0f) {
            z = false;
        }
        zzx.zzb(z, (Object) "Height must be non-negative");
        return zza(latLng, f, f2);
    }

    public final GroundOverlayOptions positionFromBounds(LatLngBounds latLngBounds) {
        zzx.zza(this.zzaTl == null, "Position has already been set using position: " + this.zzaTl);
        this.zzaRk = latLngBounds;
        return this;
    }

    public final GroundOverlayOptions transparency(float f) {
        boolean z = f >= 0.0f && f <= 1.0f;
        zzx.zzb(z, (Object) "Transparency must be in the range [0..1]");
        this.zzaTo = f;
        return this;
    }

    public final GroundOverlayOptions visible(boolean z) {
        this.zzaTi = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzc.zza(this, parcel, i);
    }

    public final GroundOverlayOptions zIndex(float f) {
        this.zzaTh = f;
        return this;
    }

    final IBinder zzAj() {
        return this.zzaTk.zzzH().asBinder();
    }
}
