package com.google.android.gms.ads.internal.purchase;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.google.android.gms.ads.internal.zzr;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.internal.zzgg.zza;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzg extends zza implements ServiceConnection {
    private Context mContext;
    zzb zzFC;
    private String zzFI;
    private zzf zzFM;
    private boolean zzFS = false;
    private int zzFT;
    private Intent zzFU;

    public zzg(Context context, String str, boolean z, int i, Intent intent, zzf com_google_android_gms_ads_internal_purchase_zzf) {
        this.zzFI = str;
        this.zzFT = i;
        this.zzFU = intent;
        this.zzFS = z;
        this.mContext = context;
        this.zzFM = com_google_android_gms_ads_internal_purchase_zzf;
    }

    public final void finishPurchase() {
        int zzd = zzr.zzbM().zzd(this.zzFU);
        if (this.zzFT == -1 && zzd == 0) {
            this.zzFC = new zzb(this.mContext);
            Intent intent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
            intent.setPackage("com.android.vending");
            zzb.zzrP().zza(this.mContext, intent, (ServiceConnection) this, 1);
        }
    }

    public final String getProductId() {
        return this.zzFI;
    }

    public final Intent getPurchaseData() {
        return this.zzFU;
    }

    public final int getResultCode() {
        return this.zzFT;
    }

    public final boolean isVerified() {
        return this.zzFS;
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaJ("In-app billing service connected.");
        this.zzFC.zzN(iBinder);
        String zzar = zzr.zzbM().zzar(zzr.zzbM().zze(this.zzFU));
        if (zzar != null) {
            if (this.zzFC.zzh(this.mContext.getPackageName(), zzar) == 0) {
                zzh.zzy(this.mContext).zza(this.zzFM);
            }
            zzb.zzrP().zza(this.mContext, this);
            this.zzFC.destroy();
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        com.google.android.gms.ads.internal.util.client.zzb.zzaJ("In-app billing service disconnected.");
        this.zzFC.destroy();
    }
}
