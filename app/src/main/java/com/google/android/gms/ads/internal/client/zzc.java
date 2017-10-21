package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.internal.client.zzq.zza;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzc extends zza {
    private final AdListener zztA;

    public zzc(AdListener adListener) {
        this.zztA = adListener;
    }

    public final void onAdClosed() {
        this.zztA.onAdClosed();
    }

    public final void onAdFailedToLoad(int i) {
        this.zztA.onAdFailedToLoad(i);
    }

    public final void onAdLeftApplication() {
        this.zztA.onAdLeftApplication();
    }

    public final void onAdLoaded() {
        this.zztA.onAdLoaded();
    }

    public final void onAdOpened() {
        this.zztA.onAdOpened();
    }
}
