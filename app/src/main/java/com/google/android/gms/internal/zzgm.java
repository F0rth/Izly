package com.google.android.gms.internal;

import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.internal.zzgh.zza;

@zzhb
public final class zzgm extends zza {
    private final PlayStorePurchaseListener zzuP;

    public zzgm(PlayStorePurchaseListener playStorePurchaseListener) {
        this.zzuP = playStorePurchaseListener;
    }

    public final boolean isValidPurchase(String str) {
        return this.zzuP.isValidPurchase(str);
    }

    public final void zza(zzgg com_google_android_gms_internal_zzgg) {
        this.zzuP.onInAppPurchaseFinished(new zzgk(com_google_android_gms_internal_zzgg));
    }
}
