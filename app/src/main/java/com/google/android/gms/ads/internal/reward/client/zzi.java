package com.google.android.gms.ads.internal.reward.client;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.internal.client.zzh;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.internal.zzhb;

@zzhb
public class zzi implements RewardedVideoAd {
    private final Context mContext;
    private final zzb zzKA;
    private RewardedVideoAdListener zzaX;
    private final Object zzpV = new Object();
    private String zzrG;

    public zzi(Context context, zzb com_google_android_gms_ads_internal_reward_client_zzb) {
        this.zzKA = com_google_android_gms_ads_internal_reward_client_zzb;
        this.mContext = context;
    }

    public void destroy() {
        synchronized (this.zzpV) {
            if (this.zzKA == null) {
                return;
            }
            try {
                this.zzKA.destroy();
            } catch (Throwable e) {
                zzb.zzd("Could not forward destroy to RewardedVideoAd", e);
            }
        }
    }

    public RewardedVideoAdListener getRewardedVideoAdListener() {
        RewardedVideoAdListener rewardedVideoAdListener;
        synchronized (this.zzpV) {
            rewardedVideoAdListener = this.zzaX;
        }
        return rewardedVideoAdListener;
    }

    public String getUserId() {
        String str;
        synchronized (this.zzpV) {
            str = this.zzrG;
        }
        return str;
    }

    public boolean isLoaded() {
        boolean z = false;
        synchronized (this.zzpV) {
            if (this.zzKA == null) {
            } else {
                try {
                    z = this.zzKA.isLoaded();
                } catch (Throwable e) {
                    zzb.zzd("Could not forward isLoaded to RewardedVideoAd", e);
                }
            }
        }
        return z;
    }

    public void loadAd(String str, AdRequest adRequest) {
        synchronized (this.zzpV) {
            if (this.zzKA == null) {
                return;
            }
            try {
                this.zzKA.zza(zzh.zzcO().zza(this.mContext, adRequest.zzaE(), str));
            } catch (Throwable e) {
                zzb.zzd("Could not forward loadAd to RewardedVideoAd", e);
            }
        }
    }

    public void pause() {
        synchronized (this.zzpV) {
            if (this.zzKA == null) {
                return;
            }
            try {
                this.zzKA.pause();
            } catch (Throwable e) {
                zzb.zzd("Could not forward pause to RewardedVideoAd", e);
            }
        }
    }

    public void resume() {
        synchronized (this.zzpV) {
            if (this.zzKA == null) {
                return;
            }
            try {
                this.zzKA.resume();
            } catch (Throwable e) {
                zzb.zzd("Could not forward resume to RewardedVideoAd", e);
            }
        }
    }

    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        synchronized (this.zzpV) {
            this.zzaX = rewardedVideoAdListener;
            if (this.zzKA != null) {
                try {
                    this.zzKA.zza(new zzg(rewardedVideoAdListener));
                } catch (Throwable e) {
                    zzb.zzd("Could not forward setRewardedVideoAdListener to RewardedVideoAd", e);
                }
            }
        }
    }

    public void setUserId(String str) {
        synchronized (this.zzpV) {
            if (TextUtils.isEmpty(this.zzrG)) {
                this.zzrG = str;
                if (this.zzKA != null) {
                    try {
                        this.zzKA.setUserId(str);
                    } catch (Throwable e) {
                        zzb.zzd("Could not forward setUserId to RewardedVideoAd", e);
                    }
                }
                return;
            }
            zzb.zzaK("A user id has already been set, ignoring.");
        }
    }

    public void show() {
        synchronized (this.zzpV) {
            if (this.zzKA == null) {
                return;
            }
            try {
                this.zzKA.show();
            } catch (Throwable e) {
                zzb.zzd("Could not forward show to RewardedVideoAd", e);
            }
        }
    }
}
