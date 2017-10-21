package com.google.android.gms.internal;

import android.os.SystemClock;

public final class zzmt implements zzmq {
    private static zzmt zzaoa;

    public static zzmq zzsc() {
        synchronized (zzmt.class) {
            try {
                if (zzaoa == null) {
                    zzaoa = new zzmt();
                }
                zzmq com_google_android_gms_internal_zzmq = zzaoa;
                return com_google_android_gms_internal_zzmq;
            } finally {
                Object obj = zzmt.class;
            }
        }
    }

    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public final long nanoTime() {
        return System.nanoTime();
    }
}
