package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzi.zza;

public final class TileOverlayOptions implements SafeParcelable {
    public static final zzo CREATOR = new zzo();
    private final int mVersionCode;
    private zzi zzaTP;
    private TileProvider zzaTQ;
    private boolean zzaTR;
    private float zzaTh;
    private boolean zzaTi;

    public TileOverlayOptions() {
        this.zzaTi = true;
        this.zzaTR = true;
        this.mVersionCode = 1;
    }

    TileOverlayOptions(int i, IBinder iBinder, boolean z, float f, boolean z2) {
        this.zzaTi = true;
        this.zzaTR = true;
        this.mVersionCode = i;
        this.zzaTP = zza.zzdm(iBinder);
        this.zzaTQ = this.zzaTP == null ? null : new TileProvider(this) {
            private final zzi zzaTS = this.zzaTT.zzaTP;
            final /* synthetic */ TileOverlayOptions zzaTT;

            {
                this.zzaTT = r2;
            }

            public Tile getTile(int i, int i2, int i3) {
                try {
                    return this.zzaTS.getTile(i, i2, i3);
                } catch (RemoteException e) {
                    return null;
                }
            }
        };
        this.zzaTi = z;
        this.zzaTh = f;
        this.zzaTR = z2;
    }

    public final int describeContents() {
        return 0;
    }

    public final TileOverlayOptions fadeIn(boolean z) {
        this.zzaTR = z;
        return this;
    }

    public final boolean getFadeIn() {
        return this.zzaTR;
    }

    public final TileProvider getTileProvider() {
        return this.zzaTQ;
    }

    final int getVersionCode() {
        return this.mVersionCode;
    }

    public final float getZIndex() {
        return this.zzaTh;
    }

    public final boolean isVisible() {
        return this.zzaTi;
    }

    public final TileOverlayOptions tileProvider(final TileProvider tileProvider) {
        this.zzaTQ = tileProvider;
        this.zzaTP = this.zzaTQ == null ? null : new zza(this) {
            final /* synthetic */ TileOverlayOptions zzaTT;

            public Tile getTile(int i, int i2, int i3) {
                return tileProvider.getTile(i, i2, i3);
            }
        };
        return this;
    }

    public final TileOverlayOptions visible(boolean z) {
        this.zzaTi = z;
        return this;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzo.zza(this, parcel, i);
    }

    public final TileOverlayOptions zIndex(float f) {
        this.zzaTh = f;
        return this;
    }

    final IBinder zzAm() {
        return this.zzaTP.asBinder();
    }
}
