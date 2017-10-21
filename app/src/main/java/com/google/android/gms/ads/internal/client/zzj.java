package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.internal.client.zzw.zza;
import com.google.android.gms.internal.zzhb;

@zzhb
public final class zzj extends zza {
    private final AppEventListener zzun;

    public zzj(AppEventListener appEventListener) {
        this.zzun = appEventListener;
    }

    public final void onAppEvent(String str, String str2) {
        this.zzun.onAppEvent(str, str2);
    }
}
