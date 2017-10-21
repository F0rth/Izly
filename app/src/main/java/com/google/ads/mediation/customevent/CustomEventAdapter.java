package com.google.ads.mediation.customevent;

import android.app.Activity;
import android.view.View;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class CustomEventAdapter implements MediationBannerAdapter<CustomEventExtras, CustomEventServerParameters>, MediationInterstitialAdapter<CustomEventExtras, CustomEventServerParameters> {
    private View zzbk;
    CustomEventBanner zzbl;
    CustomEventInterstitial zzbm;

    static final class zza implements CustomEventBannerListener {
        private final CustomEventAdapter zzbn;
        private final MediationBannerListener zzbo;

        public zza(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
            this.zzbn = customEventAdapter;
            this.zzbo = mediationBannerListener;
        }

        public final void onClick() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onFailedToReceiveAd.");
            this.zzbo.onClick(this.zzbn);
        }

        public final void onDismissScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onFailedToReceiveAd.");
            this.zzbo.onDismissScreen(this.zzbn);
        }

        public final void onFailedToReceiveAd() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onFailedToReceiveAd.");
            this.zzbo.onFailedToReceiveAd(this.zzbn, ErrorCode.NO_FILL);
        }

        public final void onLeaveApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onFailedToReceiveAd.");
            this.zzbo.onLeaveApplication(this.zzbn);
        }

        public final void onPresentScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onFailedToReceiveAd.");
            this.zzbo.onPresentScreen(this.zzbn);
        }

        public final void onReceivedAd(View view) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onReceivedAd.");
            this.zzbn.zza(view);
            this.zzbo.onReceivedAd(this.zzbn);
        }
    }

    class zzb implements CustomEventInterstitialListener {
        private final CustomEventAdapter zzbn;
        private final MediationInterstitialListener zzbp;
        final /* synthetic */ CustomEventAdapter zzbq;

        public zzb(CustomEventAdapter customEventAdapter, CustomEventAdapter customEventAdapter2, MediationInterstitialListener mediationInterstitialListener) {
            this.zzbq = customEventAdapter;
            this.zzbn = customEventAdapter2;
            this.zzbp = mediationInterstitialListener;
        }

        public void onDismissScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onDismissScreen.");
            this.zzbp.onDismissScreen(this.zzbn);
        }

        public void onFailedToReceiveAd() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onFailedToReceiveAd.");
            this.zzbp.onFailedToReceiveAd(this.zzbn, ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onLeaveApplication.");
            this.zzbp.onLeaveApplication(this.zzbn);
        }

        public void onPresentScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onPresentScreen.");
            this.zzbp.onPresentScreen(this.zzbn);
        }

        public void onReceivedAd() {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Custom event adapter called onReceivedAd.");
            this.zzbp.onReceivedAd(this.zzbq);
        }
    }

    private void zza(View view) {
        this.zzbk = view;
    }

    private static <T> T zzj(String str) {
        try {
            return Class.forName(str).newInstance();
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaK("Could not instantiate custom event adapter: " + str + ". " + th.getMessage());
            return null;
        }
    }

    public final void destroy() {
        if (this.zzbl != null) {
            this.zzbl.destroy();
        }
        if (this.zzbm != null) {
            this.zzbm.destroy();
        }
    }

    public final Class<CustomEventExtras> getAdditionalParametersType() {
        return CustomEventExtras.class;
    }

    public final View getBannerView() {
        return this.zzbk;
    }

    public final Class<CustomEventServerParameters> getServerParametersType() {
        return CustomEventServerParameters.class;
    }

    public final void requestBannerAd(MediationBannerListener mediationBannerListener, Activity activity, CustomEventServerParameters customEventServerParameters, AdSize adSize, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.zzbl = (CustomEventBanner) zzj(customEventServerParameters.className);
        if (this.zzbl == null) {
            mediationBannerListener.onFailedToReceiveAd(this, ErrorCode.INTERNAL_ERROR);
        } else {
            this.zzbl.requestBannerAd(new zza(this, mediationBannerListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, adSize, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(customEventServerParameters.label));
        }
    }

    public final void requestInterstitialAd(MediationInterstitialListener mediationInterstitialListener, Activity activity, CustomEventServerParameters customEventServerParameters, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.zzbm = (CustomEventInterstitial) zzj(customEventServerParameters.className);
        if (this.zzbm == null) {
            mediationInterstitialListener.onFailedToReceiveAd(this, ErrorCode.INTERNAL_ERROR);
        } else {
            this.zzbm.requestInterstitialAd(zza(mediationInterstitialListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(customEventServerParameters.label));
        }
    }

    public final void showInterstitial() {
        this.zzbm.showInterstitial();
    }

    final zzb zza(MediationInterstitialListener mediationInterstitialListener) {
        return new zzb(this, this, mediationInterstitialListener);
    }
}
