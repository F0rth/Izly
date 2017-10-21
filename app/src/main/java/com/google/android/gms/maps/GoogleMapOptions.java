package com.google.android.gms.maps;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.util.AttributeSet;
import com.google.android.gms.R;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.internal.zza;
import com.google.android.gms.maps.model.CameraPosition;

public final class GoogleMapOptions implements SafeParcelable {
    public static final zza CREATOR = new zza();
    private final int mVersionCode;
    private Boolean zzaRP;
    private Boolean zzaRQ;
    private int zzaRR;
    private CameraPosition zzaRS;
    private Boolean zzaRT;
    private Boolean zzaRU;
    private Boolean zzaRV;
    private Boolean zzaRW;
    private Boolean zzaRX;
    private Boolean zzaRY;
    private Boolean zzaRZ;
    private Boolean zzaSa;
    private Boolean zzaSb;

    public GoogleMapOptions() {
        this.zzaRR = -1;
        this.mVersionCode = 1;
    }

    GoogleMapOptions(int i, byte b, byte b2, int i2, CameraPosition cameraPosition, byte b3, byte b4, byte b5, byte b6, byte b7, byte b8, byte b9, byte b10, byte b11) {
        this.zzaRR = -1;
        this.mVersionCode = i;
        this.zzaRP = zza.zza(b);
        this.zzaRQ = zza.zza(b2);
        this.zzaRR = i2;
        this.zzaRS = cameraPosition;
        this.zzaRT = zza.zza(b3);
        this.zzaRU = zza.zza(b4);
        this.zzaRV = zza.zza(b5);
        this.zzaRW = zza.zza(b6);
        this.zzaRX = zza.zza(b7);
        this.zzaRY = zza.zza(b8);
        this.zzaRZ = zza.zza(b9);
        this.zzaSa = zza.zza(b10);
        this.zzaSb = zza.zza(b11);
    }

    public static GoogleMapOptions createFromAttributes(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return null;
        }
        TypedArray obtainAttributes = context.getResources().obtainAttributes(attributeSet, R.styleable.MapAttrs);
        GoogleMapOptions googleMapOptions = new GoogleMapOptions();
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_mapType)) {
            googleMapOptions.mapType(obtainAttributes.getInt(R.styleable.MapAttrs_mapType, -1));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_zOrderOnTop)) {
            googleMapOptions.zOrderOnTop(obtainAttributes.getBoolean(R.styleable.MapAttrs_zOrderOnTop, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_useViewLifecycle)) {
            googleMapOptions.useViewLifecycleInFragment(obtainAttributes.getBoolean(R.styleable.MapAttrs_useViewLifecycle, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiCompass)) {
            googleMapOptions.compassEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiCompass, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiRotateGestures)) {
            googleMapOptions.rotateGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiRotateGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiScrollGestures)) {
            googleMapOptions.scrollGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiScrollGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiTiltGestures)) {
            googleMapOptions.tiltGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiTiltGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiZoomGestures)) {
            googleMapOptions.zoomGesturesEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiZoomGestures, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiZoomControls)) {
            googleMapOptions.zoomControlsEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiZoomControls, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_liteMode)) {
            googleMapOptions.liteMode(obtainAttributes.getBoolean(R.styleable.MapAttrs_liteMode, false));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_uiMapToolbar)) {
            googleMapOptions.mapToolbarEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_uiMapToolbar, true));
        }
        if (obtainAttributes.hasValue(R.styleable.MapAttrs_ambientEnabled)) {
            googleMapOptions.ambientEnabled(obtainAttributes.getBoolean(R.styleable.MapAttrs_ambientEnabled, false));
        }
        googleMapOptions.camera(CameraPosition.createFromAttributes(context, attributeSet));
        obtainAttributes.recycle();
        return googleMapOptions;
    }

    public final GoogleMapOptions ambientEnabled(boolean z) {
        this.zzaSb = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions camera(CameraPosition cameraPosition) {
        this.zzaRS = cameraPosition;
        return this;
    }

    public final GoogleMapOptions compassEnabled(boolean z) {
        this.zzaRU = Boolean.valueOf(z);
        return this;
    }

    public final int describeContents() {
        return 0;
    }

    public final Boolean getAmbientEnabled() {
        return this.zzaSb;
    }

    public final CameraPosition getCamera() {
        return this.zzaRS;
    }

    public final Boolean getCompassEnabled() {
        return this.zzaRU;
    }

    public final Boolean getLiteMode() {
        return this.zzaRZ;
    }

    public final Boolean getMapToolbarEnabled() {
        return this.zzaSa;
    }

    public final int getMapType() {
        return this.zzaRR;
    }

    public final Boolean getRotateGesturesEnabled() {
        return this.zzaRY;
    }

    public final Boolean getScrollGesturesEnabled() {
        return this.zzaRV;
    }

    public final Boolean getTiltGesturesEnabled() {
        return this.zzaRX;
    }

    public final Boolean getUseViewLifecycleInFragment() {
        return this.zzaRQ;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final Boolean getZOrderOnTop() {
        return this.zzaRP;
    }

    public final Boolean getZoomControlsEnabled() {
        return this.zzaRT;
    }

    public final Boolean getZoomGesturesEnabled() {
        return this.zzaRW;
    }

    public final GoogleMapOptions liteMode(boolean z) {
        this.zzaRZ = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions mapToolbarEnabled(boolean z) {
        this.zzaSa = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions mapType(int i) {
        this.zzaRR = i;
        return this;
    }

    public final GoogleMapOptions rotateGesturesEnabled(boolean z) {
        this.zzaRY = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions scrollGesturesEnabled(boolean z) {
        this.zzaRV = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions tiltGesturesEnabled(boolean z) {
        this.zzaRX = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions useViewLifecycleInFragment(boolean z) {
        this.zzaRQ = Boolean.valueOf(z);
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }

    public final GoogleMapOptions zOrderOnTop(boolean z) {
        this.zzaRP = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions zoomControlsEnabled(boolean z) {
        this.zzaRT = Boolean.valueOf(z);
        return this;
    }

    public final GoogleMapOptions zoomGesturesEnabled(boolean z) {
        this.zzaRW = Boolean.valueOf(z);
        return this;
    }

    final byte zzzK() {
        return zza.zze(this.zzaRP);
    }

    final byte zzzL() {
        return zza.zze(this.zzaRQ);
    }

    final byte zzzM() {
        return zza.zze(this.zzaRT);
    }

    final byte zzzN() {
        return zza.zze(this.zzaRU);
    }

    final byte zzzO() {
        return zza.zze(this.zzaRV);
    }

    final byte zzzP() {
        return zza.zze(this.zzaRW);
    }

    final byte zzzQ() {
        return zza.zze(this.zzaRX);
    }

    final byte zzzR() {
        return zza.zze(this.zzaRY);
    }

    final byte zzzS() {
        return zza.zze(this.zzaRZ);
    }

    final byte zzzT() {
        return zza.zze(this.zzaSa);
    }

    final byte zzzU() {
        return zza.zze(this.zzaSb);
    }
}
