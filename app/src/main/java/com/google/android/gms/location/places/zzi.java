package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzi implements Creator<PlacePhotoResult> {
    static void zza(PlacePhotoResult placePhotoResult, Parcel parcel, int i) {
        int zzav = zzb.zzav(parcel);
        zzb.zza(parcel, 1, placePhotoResult.getStatus(), i, false);
        zzb.zzc(parcel, 1000, placePhotoResult.mVersionCode);
        zzb.zza(parcel, 2, placePhotoResult.zzaPG, i, false);
        zzb.zzI(parcel, zzav);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzfg(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzhR(i);
    }

    public PlacePhotoResult zzfg(Parcel parcel) {
        int zzau = zza.zzau(parcel);
        Status status = null;
        int i = 0;
        BitmapTeleporter bitmapTeleporter = null;
        while (parcel.dataPosition() < zzau) {
            int zzat = zza.zzat(parcel);
            switch (zza.zzca(zzat)) {
                case 1:
                    status = (Status) zza.zza(parcel, zzat, Status.CREATOR);
                    break;
                case 2:
                    bitmapTeleporter = (BitmapTeleporter) zza.zza(parcel, zzat, BitmapTeleporter.CREATOR);
                    break;
                case 1000:
                    i = zza.zzg(parcel, zzat);
                    break;
                default:
                    zza.zzb(parcel, zzat);
                    break;
            }
        }
        if (parcel.dataPosition() == zzau) {
            return new PlacePhotoResult(i, status, bitmapTeleporter);
        }
        throw new zza.zza("Overread allowed size end=" + zzau, parcel);
    }

    public PlacePhotoResult[] zzhR(int i) {
        return new PlacePhotoResult[i];
    }
}
