package com.google.android.gms.ads.internal.client;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.ClientApi;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzcj;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzfv;
import com.google.android.gms.internal.zzge;
import com.google.android.gms.internal.zzhb;

@zzhb
public class zzl {
    public static String zzuq = null;
    private zzm zzup;

    public zzl() {
        ClientApi.retainReference();
        if (zzuq != null) {
            try {
                this.zzup = (zzm) zzl.class.getClassLoader().loadClass(zzuq).newInstance();
                return;
            } catch (Throwable e) {
                zzb.zzd("Failed to instantiate ClientApi class.", e);
                this.zzup = new zzai();
                return;
            }
        }
        zzb.zzaK("No client jar implementation found.");
        this.zzup = new zzai();
    }

    public zzs createAdLoaderBuilder(Context context, String str, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return this.zzup.createAdLoaderBuilder(context, str, com_google_android_gms_internal_zzew, versionInfoParcel);
    }

    @Nullable
    public zzfv createAdOverlay(Activity activity) {
        return this.zzup.createAdOverlay(activity);
    }

    public zzu createBannerAdManager(Context context, AdSizeParcel adSizeParcel, String str, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return this.zzup.createBannerAdManager(context, adSizeParcel, str, com_google_android_gms_internal_zzew, versionInfoParcel);
    }

    @Nullable
    public zzge createInAppPurchaseManager(Activity activity) {
        return this.zzup.createInAppPurchaseManager(activity);
    }

    public zzu createInterstitialAdManager(Context context, AdSizeParcel adSizeParcel, String str, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return this.zzup.createInterstitialAdManager(context, adSizeParcel, str, com_google_android_gms_internal_zzew, versionInfoParcel);
    }

    public zzcj createNativeAdViewDelegate(FrameLayout frameLayout, FrameLayout frameLayout2) {
        return this.zzup.createNativeAdViewDelegate(frameLayout, frameLayout2);
    }

    public com.google.android.gms.ads.internal.reward.client.zzb createRewardedVideoAd(Context context, zzew com_google_android_gms_internal_zzew, VersionInfoParcel versionInfoParcel) {
        return this.zzup.createRewardedVideoAd(context, com_google_android_gms_internal_zzew, versionInfoParcel);
    }

    public zzy getMobileAdsSettingsManager(Context context) {
        return this.zzup.getMobileAdsSettingsManager(context);
    }
}
