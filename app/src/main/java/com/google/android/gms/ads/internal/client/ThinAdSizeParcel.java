package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.internal.zzhb;

@zzhb
public class ThinAdSizeParcel extends AdSizeParcel {
    public ThinAdSizeParcel(AdSizeParcel adSizeParcel) {
        super(adSizeParcel.versionCode, adSizeParcel.zzuh, adSizeParcel.height, adSizeParcel.heightPixels, adSizeParcel.zzui, adSizeParcel.width, adSizeParcel.widthPixels, adSizeParcel.zzuj, adSizeParcel.zzuk, adSizeParcel.zzul, adSizeParcel.zzum);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zzav = zzb.zzav(parcel);
        zzb.zzc(parcel, 1, this.versionCode);
        zzb.zza(parcel, 2, this.zzuh, false);
        zzb.zzc(parcel, 3, this.height);
        zzb.zzc(parcel, 6, this.width);
        zzb.zzI(parcel, zzav);
    }
}
