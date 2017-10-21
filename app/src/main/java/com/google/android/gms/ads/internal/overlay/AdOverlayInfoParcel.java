package com.google.android.gms.ads.internal.overlay;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.client.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzdb;
import com.google.android.gms.internal.zzdh;
import com.google.android.gms.internal.zzhb;
import com.google.android.gms.internal.zzjp;

@zzhb
public final class AdOverlayInfoParcel implements SafeParcelable {
    public static final zzf CREATOR = new zzf();
    public final int orientation;
    public final String url;
    public final int versionCode;
    public final AdLauncherIntentInfoParcel zzEA;
    public final zza zzEB;
    public final zzg zzEC;
    public final zzjp zzED;
    public final zzdb zzEE;
    public final String zzEF;
    public final boolean zzEG;
    public final String zzEH;
    public final zzp zzEI;
    public final int zzEJ;
    public final zzdh zzEK;
    public final String zzEL;
    public final InterstitialAdParameterParcel zzEM;
    public final VersionInfoParcel zzrl;

    AdOverlayInfoParcel(int i, AdLauncherIntentInfoParcel adLauncherIntentInfoParcel, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, IBinder iBinder4, String str, boolean z, String str2, IBinder iBinder5, int i2, int i3, String str3, VersionInfoParcel versionInfoParcel, IBinder iBinder6, String str4, InterstitialAdParameterParcel interstitialAdParameterParcel) {
        this.versionCode = i;
        this.zzEA = adLauncherIntentInfoParcel;
        this.zzEB = (zza) zze.zzp(zzd.zza.zzbs(iBinder));
        this.zzEC = (zzg) zze.zzp(zzd.zza.zzbs(iBinder2));
        this.zzED = (zzjp) zze.zzp(zzd.zza.zzbs(iBinder3));
        this.zzEE = (zzdb) zze.zzp(zzd.zza.zzbs(iBinder4));
        this.zzEF = str;
        this.zzEG = z;
        this.zzEH = str2;
        this.zzEI = (zzp) zze.zzp(zzd.zza.zzbs(iBinder5));
        this.orientation = i2;
        this.zzEJ = i3;
        this.url = str3;
        this.zzrl = versionInfoParcel;
        this.zzEK = (zzdh) zze.zzp(zzd.zza.zzbs(iBinder6));
        this.zzEL = str4;
        this.zzEM = interstitialAdParameterParcel;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzp com_google_android_gms_ads_internal_overlay_zzp, zzjp com_google_android_gms_internal_zzjp, int i, VersionInfoParcel versionInfoParcel, String str, InterstitialAdParameterParcel interstitialAdParameterParcel) {
        this.versionCode = 4;
        this.zzEA = null;
        this.zzEB = com_google_android_gms_ads_internal_client_zza;
        this.zzEC = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzED = com_google_android_gms_internal_zzjp;
        this.zzEE = null;
        this.zzEF = null;
        this.zzEG = false;
        this.zzEH = null;
        this.zzEI = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzEJ = 1;
        this.url = null;
        this.zzrl = versionInfoParcel;
        this.zzEK = null;
        this.zzEL = str;
        this.zzEM = interstitialAdParameterParcel;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzp com_google_android_gms_ads_internal_overlay_zzp, zzjp com_google_android_gms_internal_zzjp, boolean z, int i, VersionInfoParcel versionInfoParcel) {
        this.versionCode = 4;
        this.zzEA = null;
        this.zzEB = com_google_android_gms_ads_internal_client_zza;
        this.zzEC = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzED = com_google_android_gms_internal_zzjp;
        this.zzEE = null;
        this.zzEF = null;
        this.zzEG = z;
        this.zzEH = null;
        this.zzEI = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzEJ = 2;
        this.url = null;
        this.zzrl = versionInfoParcel;
        this.zzEK = null;
        this.zzEL = null;
        this.zzEM = null;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzdb com_google_android_gms_internal_zzdb, zzp com_google_android_gms_ads_internal_overlay_zzp, zzjp com_google_android_gms_internal_zzjp, boolean z, int i, String str, VersionInfoParcel versionInfoParcel, zzdh com_google_android_gms_internal_zzdh) {
        this.versionCode = 4;
        this.zzEA = null;
        this.zzEB = com_google_android_gms_ads_internal_client_zza;
        this.zzEC = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzED = com_google_android_gms_internal_zzjp;
        this.zzEE = com_google_android_gms_internal_zzdb;
        this.zzEF = null;
        this.zzEG = z;
        this.zzEH = null;
        this.zzEI = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzEJ = 3;
        this.url = str;
        this.zzrl = versionInfoParcel;
        this.zzEK = com_google_android_gms_internal_zzdh;
        this.zzEL = null;
        this.zzEM = null;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzdb com_google_android_gms_internal_zzdb, zzp com_google_android_gms_ads_internal_overlay_zzp, zzjp com_google_android_gms_internal_zzjp, boolean z, int i, String str, String str2, VersionInfoParcel versionInfoParcel, zzdh com_google_android_gms_internal_zzdh) {
        this.versionCode = 4;
        this.zzEA = null;
        this.zzEB = com_google_android_gms_ads_internal_client_zza;
        this.zzEC = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzED = com_google_android_gms_internal_zzjp;
        this.zzEE = com_google_android_gms_internal_zzdb;
        this.zzEF = str2;
        this.zzEG = z;
        this.zzEH = str;
        this.zzEI = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzEJ = 3;
        this.url = null;
        this.zzrl = versionInfoParcel;
        this.zzEK = com_google_android_gms_internal_zzdh;
        this.zzEL = null;
        this.zzEM = null;
    }

    public AdOverlayInfoParcel(AdLauncherIntentInfoParcel adLauncherIntentInfoParcel, zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzp com_google_android_gms_ads_internal_overlay_zzp, VersionInfoParcel versionInfoParcel) {
        this.versionCode = 4;
        this.zzEA = adLauncherIntentInfoParcel;
        this.zzEB = com_google_android_gms_ads_internal_client_zza;
        this.zzEC = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzED = null;
        this.zzEE = null;
        this.zzEF = null;
        this.zzEG = false;
        this.zzEH = null;
        this.zzEI = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = -1;
        this.zzEJ = 4;
        this.url = null;
        this.zzrl = versionInfoParcel;
        this.zzEK = null;
        this.zzEL = null;
        this.zzEM = null;
    }

    public static void zza(Intent intent, AdOverlayInfoParcel adOverlayInfoParcel) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", adOverlayInfoParcel);
        intent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bundle);
    }

    public static AdOverlayInfoParcel zzb(Intent intent) {
        try {
            Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
            bundleExtra.setClassLoader(AdOverlayInfoParcel.class.getClassLoader());
            return (AdOverlayInfoParcel) bundleExtra.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
        } catch (Exception e) {
            return null;
        }
    }

    public final int describeContents() {
        return 0;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        zzf.zza(this, parcel, i);
    }

    final IBinder zzfs() {
        return zze.zzC(this.zzEB).asBinder();
    }

    final IBinder zzft() {
        return zze.zzC(this.zzEC).asBinder();
    }

    final IBinder zzfu() {
        return zze.zzC(this.zzED).asBinder();
    }

    final IBinder zzfv() {
        return zze.zzC(this.zzEE).asBinder();
    }

    final IBinder zzfw() {
        return zze.zzC(this.zzEK).asBinder();
    }

    final IBinder zzfx() {
        return zze.zzC(this.zzEI).asBinder();
    }
}
