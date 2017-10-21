package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.WorkerThread;
import com.google.android.gms.internal.zzmq;

public class zzad extends zzz {
    private Handler mHandler;
    private long zzaZa;
    private final Runnable zzaZb = new Runnable(this) {
        final /* synthetic */ zzad zzaZe;

        {
            this.zzaZe = r1;
        }

        @MainThread
        public void run() {
            this.zzaZe.zzCn().zzg(new Runnable(this) {
                final /* synthetic */ AnonymousClass1 zzaZf;

                {
                    this.zzaZf = r1;
                }

                public void run() {
                    this.zzaZf.zzaZe.zzDx();
                }
            });
        }
    };
    private final zzf zzaZc = new zzf(this, this.zzaTV) {
        final /* synthetic */ zzad zzaZe;

        @WorkerThread
        public void run() {
            this.zzaZe.zzDy();
        }
    };
    private final zzf zzaZd = new zzf(this, this.zzaTV) {
        final /* synthetic */ zzad zzaZe;

        @WorkerThread
        public void run() {
            this.zzaZe.zzDz();
        }
    };

    zzad(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    private void zzDv() {
        synchronized (this) {
            if (this.mHandler == null) {
                this.mHandler = new Handler(Looper.getMainLooper());
            }
        }
    }

    @WorkerThread
    private void zzDy() {
        zzjk();
        zzAo().zzCK().zzj("Session started, time", Long.valueOf(zzjl().elapsedRealtime()));
        zzCo().zzaXu.set(false);
        zzCf().zze("auto", "_s", new Bundle());
    }

    @WorkerThread
    private void zzDz() {
        zzjk();
        long elapsedRealtime = zzjl().elapsedRealtime();
        if (this.zzaZa == 0) {
            this.zzaZa = elapsedRealtime - 3600000;
        }
        long j = zzCo().zzaXw.get() + (elapsedRealtime - this.zzaZa);
        zzCo().zzaXw.set(j);
        zzAo().zzCK().zzj("Recording user engagement, ms", Long.valueOf(j));
        Bundle bundle = new Bundle();
        bundle.putLong("_et", j);
        zzCf().zze("auto", "_e", bundle);
        zzCo().zzaXw.set(0);
        this.zzaZa = elapsedRealtime;
        this.zzaZd.zzt(Math.max(0, 3600000 - zzCo().zzaXw.get()));
    }

    @WorkerThread
    private void zzae(long j) {
        zzjk();
        zzDv();
        this.zzaZc.cancel();
        this.zzaZd.cancel();
        zzAo().zzCK().zzj("Activity resumed, time", Long.valueOf(j));
        this.zzaZa = j;
        if (zzjl().currentTimeMillis() - zzCo().zzaXt.get() > zzCo().zzaXv.get()) {
            zzCo().zzaXu.set(true);
            zzCo().zzaXw.set(0);
        }
        if (zzCo().zzaXu.get()) {
            this.zzaZc.zzt(Math.max(0, zzCo().zzaXs.get() - zzCo().zzaXw.get()));
        } else {
            this.zzaZd.zzt(Math.max(0, 3600000 - zzCo().zzaXw.get()));
        }
    }

    @WorkerThread
    private void zzaf(long j) {
        zzjk();
        zzDv();
        this.zzaZc.cancel();
        this.zzaZd.cancel();
        zzAo().zzCK().zzj("Activity paused, time", Long.valueOf(j));
        if (this.zzaZa != 0) {
            zzCo().zzaXw.set(zzCo().zzaXw.get() + (j - this.zzaZa));
        }
        zzCo().zzaXv.set(zzjl().currentTimeMillis());
        synchronized (this) {
            if (!zzCo().zzaXu.get()) {
                this.mHandler.postDelayed(this.zzaZb, 1000);
            }
        }
    }

    public /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public /* bridge */ /* synthetic */ zzp zzAo() {
        return super.zzAo();
    }

    public /* bridge */ /* synthetic */ void zzCd() {
        super.zzCd();
    }

    public /* bridge */ /* synthetic */ zzc zzCe() {
        return super.zzCe();
    }

    public /* bridge */ /* synthetic */ zzab zzCf() {
        return super.zzCf();
    }

    public /* bridge */ /* synthetic */ zzn zzCg() {
        return super.zzCg();
    }

    public /* bridge */ /* synthetic */ zzg zzCh() {
        return super.zzCh();
    }

    public /* bridge */ /* synthetic */ zzac zzCi() {
        return super.zzCi();
    }

    public /* bridge */ /* synthetic */ zze zzCj() {
        return super.zzCj();
    }

    public /* bridge */ /* synthetic */ zzaj zzCk() {
        return super.zzCk();
    }

    public /* bridge */ /* synthetic */ zzu zzCl() {
        return super.zzCl();
    }

    public /* bridge */ /* synthetic */ zzad zzCm() {
        return super.zzCm();
    }

    public /* bridge */ /* synthetic */ zzv zzCn() {
        return super.zzCn();
    }

    public /* bridge */ /* synthetic */ zzt zzCo() {
        return super.zzCo();
    }

    public /* bridge */ /* synthetic */ zzd zzCp() {
        return super.zzCp();
    }

    @MainThread
    protected void zzDu() {
        synchronized (this) {
            zzDv();
            this.mHandler.removeCallbacks(this.zzaZb);
        }
        final long elapsedRealtime = zzjl().elapsedRealtime();
        zzCn().zzg(new Runnable(this) {
            final /* synthetic */ zzad zzaZe;

            public void run() {
                this.zzaZe.zzae(elapsedRealtime);
            }
        });
    }

    @MainThread
    protected void zzDw() {
        final long elapsedRealtime = zzjl().elapsedRealtime();
        zzCn().zzg(new Runnable(this) {
            final /* synthetic */ zzad zzaZe;

            public void run() {
                this.zzaZe.zzaf(elapsedRealtime);
            }
        });
    }

    @WorkerThread
    public void zzDx() {
        zzjk();
        zzAo().zzCJ().zzfg("Application backgrounded. Logging engagement");
        long j = zzCo().zzaXw.get();
        if (j > 0) {
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j);
            zzCf().zze("auto", "_e", bundle);
            zzCo().zzaXw.set(0);
            return;
        }
        zzAo().zzCF().zzj("Not logging non-positive engagement time", Long.valueOf(j));
    }

    protected void zziJ() {
    }

    public /* bridge */ /* synthetic */ void zzjj() {
        super.zzjj();
    }

    public /* bridge */ /* synthetic */ void zzjk() {
        super.zzjk();
    }

    public /* bridge */ /* synthetic */ zzmq zzjl() {
        return super.zzjl();
    }
}
