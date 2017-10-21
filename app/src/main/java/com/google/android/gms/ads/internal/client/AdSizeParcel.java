package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.Parcel;
import android.util.DisplayMetrics;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.zza;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.internal.zzhb;

@zzhb
public class AdSizeParcel implements SafeParcelable {
    public static final zzi CREATOR = new zzi();
    public final int height;
    public final int heightPixels;
    public final int versionCode;
    public final int width;
    public final int widthPixels;
    public final String zzuh;
    public final boolean zzui;
    public final AdSizeParcel[] zzuj;
    public final boolean zzuk;
    public final boolean zzul;
    public boolean zzum;

    public AdSizeParcel() {
        this(5, "interstitial_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    AdSizeParcel(int i, String str, int i2, int i3, boolean z, int i4, int i5, AdSizeParcel[] adSizeParcelArr, boolean z2, boolean z3, boolean z4) {
        this.versionCode = i;
        this.zzuh = str;
        this.height = i2;
        this.heightPixels = i3;
        this.zzui = z;
        this.width = i4;
        this.widthPixels = i5;
        this.zzuj = adSizeParcelArr;
        this.zzuk = z2;
        this.zzul = z3;
        this.zzum = z4;
    }

    public AdSizeParcel(Context context, AdSize adSize) {
        this(context, new AdSize[]{adSize});
    }

    public AdSizeParcel(Context context, AdSize[] adSizeArr) {
        int i;
        int i2;
        AdSize adSize = adSizeArr[0];
        this.versionCode = 5;
        this.zzui = false;
        this.zzul = adSize.isFluid();
        if (this.zzul) {
            this.width = AdSize.BANNER.getWidth();
            this.height = AdSize.BANNER.getHeight();
        } else {
            this.width = adSize.getWidth();
            this.height = adSize.getHeight();
        }
        boolean z = this.width == -1;
        boolean z2 = this.height == -2;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (z) {
            if (zzn.zzcS().zzV(context) && zzn.zzcS().zzW(context)) {
                this.widthPixels = zza(displayMetrics) - zzn.zzcS().zzX(context);
            } else {
                this.widthPixels = zza(displayMetrics);
            }
            double d = (double) (((float) this.widthPixels) / displayMetrics.density);
            i = (int) d;
            i2 = d - ((double) ((int) d)) >= 0.01d ? i + 1 : i;
        } else {
            i = this.width;
            this.widthPixels = zzn.zzcS().zza(displayMetrics, this.width);
            i2 = i;
        }
        i = z2 ? zzc(displayMetrics) : this.height;
        this.heightPixels = zzn.zzcS().zza(displayMetrics, i);
        if (z || z2) {
            this.zzuh = i2 + "x" + i + "_as";
        } else if (this.zzul) {
            this.zzuh = "320x50_mb";
        } else {
            this.zzuh = adSize.toString();
        }
        if (adSizeArr.length > 1) {
            this.zzuj = new AdSizeParcel[adSizeArr.length];
            for (int i3 = 0; i3 < adSizeArr.length; i3++) {
                this.zzuj[i3] = new AdSizeParcel(context, adSizeArr[i3]);
            }
        } else {
            this.zzuj = null;
        }
        this.zzuk = false;
        this.zzum = false;
    }

    public AdSizeParcel(AdSizeParcel adSizeParcel, AdSizeParcel[] adSizeParcelArr) {
        this(5, adSizeParcel.zzuh, adSizeParcel.height, adSizeParcel.heightPixels, adSizeParcel.zzui, adSizeParcel.width, adSizeParcel.widthPixels, adSizeParcelArr, adSizeParcel.zzuk, adSizeParcel.zzul, adSizeParcel.zzum);
    }

    public static int zza(DisplayMetrics displayMetrics) {
        return displayMetrics.widthPixels;
    }

    public static int zzb(DisplayMetrics displayMetrics) {
        return (int) (((float) zzc(displayMetrics)) * displayMetrics.density);
    }

    private static int zzc(DisplayMetrics displayMetrics) {
        int i = (int) (((float) displayMetrics.heightPixels) / displayMetrics.density);
        return i <= 400 ? 32 : i <= 720 ? 50 : 90;
    }

    public static AdSizeParcel zzcP() {
        return new AdSizeParcel(5, "reward_mb", 0, 0, true, 0, 0, null, false, false, false);
    }

    public static AdSizeParcel zzt(Context context) {
        return new AdSizeParcel(5, "320x50_mb", 0, 0, false, 0, 0, null, true, false, false);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }

    public AdSize zzcQ() {
        return zza.zza(this.width, this.height, this.zzuh);
    }

    public void zzi(boolean z) {
        this.zzum = z;
    }
}