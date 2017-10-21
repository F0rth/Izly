package com.google.android.gms.ads;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.ads.internal.client.zzad;
import com.google.android.gms.ads.internal.client.zzae;
import com.google.android.gms.ads.reward.RewardedVideoAd;

public class MobileAds {

    public static final class Settings {
        private final zzae zzoM = new zzae();

        @Deprecated
        public final String getTrackingId() {
            return this.zzoM.getTrackingId();
        }

        @Deprecated
        public final boolean isGoogleAnalyticsEnabled() {
            return this.zzoM.isGoogleAnalyticsEnabled();
        }

        @Deprecated
        public final Settings setGoogleAnalyticsEnabled(boolean z) {
            this.zzoM.zzm(z);
            return this;
        }

        @Deprecated
        public final Settings setTrackingId(String str) {
            this.zzoM.zzJ(str);
            return this;
        }

        final zzae zzaG() {
            return this.zzoM;
        }
    }

    private MobileAds() {
    }

    public static RewardedVideoAd getRewardedVideoAdInstance(Context context) {
        return zzad.zzdi().getRewardedVideoAdInstance(context);
    }

    public static void initialize(Context context) {
        zzad.zzdi().initialize(context);
    }

    @RequiresPermission("android.permission.INTERNET")
    @Deprecated
    public static void initialize(Context context, String str) {
        initialize(context, str, null);
    }

    @RequiresPermission("android.permission.INTERNET")
    @Deprecated
    public static void initialize(Context context, String str, Settings settings) {
        zzad.zzdi().zza(context, str, settings == null ? null : settings.zzaG());
    }

    public static void setAppVolume(float f) {
        zzad.zzdi().setAppVolume(f);
    }
}
