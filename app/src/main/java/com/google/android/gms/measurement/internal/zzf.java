package com.google.android.gms.measurement.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.internal.zzx;

abstract class zzf {
    private static volatile Handler zzRC;
    private volatile long zzRD;
    private final zzw zzaTV;
    private boolean zzaVI = true;
    private final Runnable zzx = new Runnable(this) {
        final /* synthetic */ zzf zzaVJ;

        {
            this.zzaVJ = r1;
        }

        public void run() {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.zzaVJ.zzaTV.zzCn().zzg(this);
                return;
            }
            boolean zzbw = this.zzaVJ.zzbw();
            this.zzaVJ.zzRD = 0;
            if (zzbw && this.zzaVJ.zzaVI) {
                this.zzaVJ.run();
            }
        }
    };

    zzf(zzw com_google_android_gms_measurement_internal_zzw) {
        zzx.zzz(com_google_android_gms_measurement_internal_zzw);
        this.zzaTV = com_google_android_gms_measurement_internal_zzw;
    }

    private Handler getHandler() {
        if (zzRC != null) {
            return zzRC;
        }
        Handler handler;
        synchronized (zzf.class) {
            try {
                if (zzRC == null) {
                    zzRC = new Handler(this.zzaTV.getContext().getMainLooper());
                }
                handler = zzRC;
            } catch (Throwable th) {
                Class cls = zzf.class;
            }
        }
        return handler;
    }

    public void cancel() {
        this.zzRD = 0;
        getHandler().removeCallbacks(this.zzx);
    }

    public abstract void run();

    public boolean zzbw() {
        return this.zzRD != 0;
    }

    public void zzt(long j) {
        cancel();
        if (j >= 0) {
            this.zzRD = this.zzaTV.zzjl().currentTimeMillis();
            if (!getHandler().postDelayed(this.zzx, j)) {
                this.zzaTV.zzAo().zzCE().zzj("Failed to schedule delayed post. time", Long.valueOf(j));
            }
        }
    }
}
