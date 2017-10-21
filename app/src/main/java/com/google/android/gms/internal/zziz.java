package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzr;

@zzhb
public class zziz {
    private long zzMJ;
    private long zzMK = Long.MIN_VALUE;
    private Object zzpV = new Object();

    public zziz(long j) {
        this.zzMJ = j;
    }

    public boolean tryAcquire() {
        synchronized (this.zzpV) {
            long elapsedRealtime = zzr.zzbG().elapsedRealtime();
            if (this.zzMK + this.zzMJ > elapsedRealtime) {
                return false;
            }
            this.zzMK = elapsedRealtime;
            return true;
        }
    }
}
