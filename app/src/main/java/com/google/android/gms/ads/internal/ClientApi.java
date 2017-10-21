package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzl;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.client.zzs;
import com.google.android.gms.ads.internal.client.zzu;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.formats.zzk;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.purchase.zze;
import com.google.android.gms.ads.internal.reward.client.zzb;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzbt;
import com.google.android.gms.internal.zzcj;
import com.google.android.gms.internal.zzeb;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzfv;
import com.google.android.gms.internal.zzge;
import com.google.android.gms.internal.zzhs;

public class ClientApi implements zzm {
    public static void retainReference() {
        zzl.zzuq = ClientApi.class.getName();
    }

    public zzs createAdLoaderBuilder(Context context, String str, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return new zzj(context, str, com_google_android_gms_internal_zzew, versionInfoParcel, zzd.zzbg());
    }

    public zzfv createAdOverlay(Activity activity) {
        return new zzd(activity);
    }

    public zzu createBannerAdManager(Context context, AdSizeParcel adSizeParcel, String str, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return new zzf(context, adSizeParcel, str, com_google_android_gms_internal_zzew, versionInfoParcel, zzd.zzbg());
    }

    public zzge createInAppPurchaseManager(Activity activity) {
        return new zze(activity);
    }

    public zzu createInterstitialAdManager(Context context, AdSizeParcel adSizeParcel, String str, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        if (((Boolean) zzbt.zzwE.get()).booleanValue()) {
            return new zzeb(context, str, com_google_android_gms_internal_zzew, versionInfoParcel, zzd.zzbg());
        }
        return new zzk(context, adSizeParcel, str, com_google_android_gms_internal_zzew, versionInfoParcel, zzd.zzbg());
    }

    public zzcj createNativeAdViewDelegate(FrameLayout frameLayout, FrameLayout frameLayout2) {
        return new zzk(frameLayout, frameLayout2);
    }

    public zzb createRewardedVideoAd(Context context, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return new zzhs(context, zzd.zzbg(), com_google_android_gms_internal_zzew, versionInfoParcel);
    }

    public zzy getMobileAdsSettingsManager(Context context) {
        return zzn.zzr(context);
    }
}
