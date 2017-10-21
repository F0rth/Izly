package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.internal.client.zzab;

public final class PublisherAdView extends ViewGroup {
    private final zzab zzoJ;

    public PublisherAdView(Context context) {
        super(context);
        this.zzoJ = new zzab(this);
    }

    public PublisherAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzoJ = new zzab(this, attributeSet, true);
    }

    public PublisherAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzoJ = new zzab(this, attributeSet, true);
    }

    public final void destroy() {
        this.zzoJ.destroy();
    }

    public final AdListener getAdListener() {
        return this.zzoJ.getAdListener();
    }

    public final AdSize getAdSize() {
        return this.zzoJ.getAdSize();
    }

    public final AdSize[] getAdSizes() {
        return this.zzoJ.getAdSizes();
    }

    public final String getAdUnitId() {
        return this.zzoJ.getAdUnitId();
    }

    public final AppEventListener getAppEventListener() {
        return this.zzoJ.getAppEventListener();
    }

    public final String getMediationAdapterClassName() {
        return this.zzoJ.getMediationAdapterClassName();
    }

    public final OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.zzoJ.getOnCustomRenderedAdLoadedListener();
    }

    public final boolean isLoading() {
        return this.zzoJ.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public final void loadAd(PublisherAdRequest publisherAdRequest) {
        this.zzoJ.zza(publisherAdRequest.zzaE());
    }

    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null && childAt.getVisibility() != 8) {
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i5 = ((i3 - i) - measuredWidth) / 2;
            int i6 = ((i4 - i2) - measuredHeight) / 2;
            childAt.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
        }
    }

    protected final void onMeasure(int i, int i2) {
        int heightInPixels;
        int i3 = 0;
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            AdSize adSize = getAdSize();
            if (adSize != null) {
                Context context = getContext();
                i3 = adSize.getWidthInPixels(context);
                heightInPixels = adSize.getHeightInPixels(context);
            } else {
                heightInPixels = 0;
            }
        } else {
            measureChild(childAt, i, i2);
            i3 = childAt.getMeasuredWidth();
            heightInPixels = childAt.getMeasuredHeight();
        }
        setMeasuredDimension(View.resolveSize(Math.max(i3, getSuggestedMinimumWidth()), i), View.resolveSize(Math.max(heightInPixels, getSuggestedMinimumHeight()), i2));
    }

    public final void pause() {
        this.zzoJ.pause();
    }

    public final void recordManualImpression() {
        this.zzoJ.recordManualImpression();
    }

    public final void resume() {
        this.zzoJ.resume();
    }

    public final void setAdListener(AdListener adListener) {
        this.zzoJ.setAdListener(adListener);
    }

    public final void setAdSizes(AdSize... adSizeArr) {
        if (adSizeArr == null || adSizeArr.length <= 0) {
            throw new IllegalArgumentException("The supported ad sizes must contain at least one valid ad size.");
        }
        this.zzoJ.zza(adSizeArr);
    }

    public final void setAdUnitId(String str) {
        this.zzoJ.setAdUnitId(str);
    }

    public final void setAppEventListener(AppEventListener appEventListener) {
        this.zzoJ.setAppEventListener(appEventListener);
    }

    public final void setCorrelator(Correlator correlator) {
        this.zzoJ.setCorrelator(correlator);
    }

    public final void setManualImpressionsEnabled(boolean z) {
        this.zzoJ.setManualImpressionsEnabled(z);
    }

    public final void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zzoJ.setOnCustomRenderedAdLoadedListener(onCustomRenderedAdLoadedListener);
    }
}
