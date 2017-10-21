package com.google.android.gms.location.places;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzw;

public class PlacePhotoResult implements Result, SafeParcelable {
    public static final Creator<PlacePhotoResult> CREATOR = new zzi();
    private final Bitmap mBitmap;
    final int mVersionCode;
    private final Status zzUX;
    final BitmapTeleporter zzaPG;

    PlacePhotoResult(int i, Status status, BitmapTeleporter bitmapTeleporter) {
        this.mVersionCode = i;
        this.zzUX = status;
        this.zzaPG = bitmapTeleporter;
        if (this.zzaPG != null) {
            this.mBitmap = bitmapTeleporter.zzqa();
        } else {
            this.mBitmap = null;
        }
    }

    public PlacePhotoResult(Status status, @Nullable BitmapTeleporter bitmapTeleporter) {
        this.mVersionCode = 0;
        this.zzUX = status;
        this.zzaPG = bitmapTeleporter;
        if (this.zzaPG != null) {
            this.mBitmap = bitmapTeleporter.zzqa();
        } else {
            this.mBitmap = null;
        }
    }

    public int describeContents() {
        return 0;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public Status getStatus() {
        return this.zzUX;
    }

    public String toString() {
        return zzw.zzy(this).zzg("status", this.zzUX).zzg("bitmap", this.mBitmap).toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
