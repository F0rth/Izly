package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.internal.client.zzac;

public final class PublisherInterstitialAd {
    private final zzac zzoL;

    public PublisherInterstitialAd(Context context) {
        this.zzoL = new zzac(context, this);
    }

    public final AdListener getAdListener() {
        return this.zzoL.getAdListener();
    }

    public final String getAdUnitId() {
        return this.zzoL.getAdUnitId();
    }

    public final AppEventListener getAppEventListener() {
        return this.zzoL.getAppEventListener();
    }

    public final String getMediationAdapterClassName() {
        return this.zzoL.getMediationAdapterClassName();
    }

    public final OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.zzoL.getOnCustomRenderedAdLoadedListener();
    }

    public final boolean isLoaded() {
        return this.zzoL.isLoaded();
    }

    public final boolean isLoading() {
        return this.zzoL.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public final void loadAd(PublisherAdRequest publisherAdRequest) {
        this.zzoL.zza(publisherAdRequest.zzaE());
    }

    public final void setAdListener(AdListener adListener) {
        this.zzoL.setAdListener(adListener);
    }

    public final void setAdUnitId(String str) {
        this.zzoL.setAdUnitId(str);
    }

    public final void setAppEventListener(AppEventListener appEventListener) {
        this.zzoL.setAppEventListener(appEventListener);
    }

    public final void setCorrelator(Correlator correlator) {
        this.zzoL.setCorrelator(correlator);
    }

    public final void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zzoL.setOnCustomRenderedAdLoadedListener(onCustomRenderedAdLoadedListener);
    }

    public final void show() {
        this.zzoL.show();
    }
}
