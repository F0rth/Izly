package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.support.v4.view.ViewCompat;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PolylineOptions implements SafeParcelable {
    public static final zzi CREATOR = new zzi();
    private int mColor;
    private final int mVersionCode;
    private final List<LatLng> zzaTJ;
    private boolean zzaTL;
    private float zzaTh;
    private boolean zzaTi;
    private float zzaTm;
    private boolean zzaTr;

    public PolylineOptions() {
        this.zzaTm = 10.0f;
        this.mColor = ViewCompat.MEASURED_STATE_MASK;
        this.zzaTh = 0.0f;
        this.zzaTi = true;
        this.zzaTL = false;
        this.zzaTr = false;
        this.mVersionCode = 1;
        this.zzaTJ = new ArrayList();
    }

    PolylineOptions(int i, List list, float f, int i2, float f2, boolean z, boolean z2, boolean z3) {
        this.zzaTm = 10.0f;
        this.mColor = ViewCompat.MEASURED_STATE_MASK;
        this.zzaTh = 0.0f;
        this.zzaTi = true;
        this.zzaTL = false;
        this.zzaTr = false;
        this.mVersionCode = i;
        this.zzaTJ = list;
        this.zzaTm = f;
        this.mColor = i2;
        this.zzaTh = f2;
        this.zzaTi = z;
        this.zzaTL = z2;
        this.zzaTr = z3;
    }

    public final PolylineOptions add(LatLng latLng) {
        this.zzaTJ.add(latLng);
        return this;
    }

    public final PolylineOptions add(LatLng... latLngArr) {
        this.zzaTJ.addAll(Arrays.asList(latLngArr));
        return this;
    }

    public final PolylineOptions addAll(Iterable<LatLng> iterable) {
        for (LatLng add : iterable) {
            this.zzaTJ.add(add);
        }
        return this;
    }

    public final PolylineOptions clickable(boolean z) {
        this.zzaTr = z;
        return this;
    }

    public final PolylineOptions color(int i) {
        this.mColor = i;
        return this;
    }

    public final int describeContents() {
        return 0;
    }

    public final PolylineOptions geodesic(boolean z) {
        this.zzaTL = z;
        return this;
    }

    public final int getColor() {
        return this.mColor;
    }

    public final List<LatLng> getPoints() {
        return this.zzaTJ;
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

    public final boolean isClickable() {
        return this.zzaTr;
    }

    public final boolean isGeodesic() {
        return this.zzaTL;
    }

    public final boolean isVisible() {
        return this.zzaTi;
    }

    public final PolylineOptions visible(boolean z) {
        this.zzaTi = z;
        return this;
    }

    public final PolylineOptions width(float f) {
        this.zzaTm = f;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }

    public final PolylineOptions zIndex(float f) {
        this.zzaTh = f;
        return this;
    }
}
