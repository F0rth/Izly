package com.google.android.gms.ads;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.ads.internal.client.zza;
import com.google.android.gms.ads.internal.client.zzac;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public final class InterstitialAd {
    private final zzac zzoL;

    public InterstitialAd(Context context) {
        this.zzoL = new zzac(context);
    }

    public final AdListener getAdListener() {
        return this.zzoL.getAdListener();
    }

    public final String getAdUnitId() {
        return this.zzoL.getAdUnitId();
    }

    public final InAppPurchaseListener getInAppPurchaseListener() {
        return this.zzoL.getInAppPurchaseListener();
    }

    public final String getMediationAdapterClassName() {
        return this.zzoL.getMediationAdapterClassName();
    }

    public final boolean isLoaded() {
        return this.zzoL.isLoaded();
    }

    public final boolean isLoading() {
        return this.zzoL.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public final void loadAd(AdRequest adRequest) {
        this.zzoL.zza(adRequest.zzaE());
    }

    public final void setAdListener(AdListener adListener) {
        this.zzoL.setAdListener(adListener);
        if (adListener != null && (adListener instanceof zza)) {
            this.zzoL.zza((zza) adListener);
        } else if (adListener == null) {
            this.zzoL.zza(null);
        }
    }

    public final void setAdUnitId(String str) {
        this.zzoL.setAdUnitId(str);
    }

    public final void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        this.zzoL.setInAppPurchaseListener(inAppPurchaseListener);
    }

    public final void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String str) {
        this.zzoL.setPlayStorePurchaseParams(playStorePurchaseListener, str);
    }

    public final void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        this.zzoL.setRewardedVideoAdListener(rewardedVideoAdListener);
    }

    public final void show() {
        this.zzoL.show();
    }

    public final void zza(boolean z) {
        this.zzoL.zza(z);
    }

    public final void zzm(String str) {
        this.zzoL.setUserId(str);
    }
}
