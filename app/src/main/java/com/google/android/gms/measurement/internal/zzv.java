package com.google.android.gms.measurement.internal;

import android.content.Context;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmq;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

public class zzv extends zzz {
    private zzc zzaXI;
    private zzc zzaXJ;
    private final BlockingQueue<FutureTask<?>> zzaXK = new LinkedBlockingQueue();
    private final BlockingQueue<FutureTask<?>> zzaXL = new LinkedBlockingQueue();
    private final UncaughtExceptionHandler zzaXM = new zzb(this, "Thread death: Uncaught exception on worker thread");
    private final UncaughtExceptionHandler zzaXN = new zzb(this, "Thread death: Uncaught exception on network thread");
    private final Object zzaXO = new Object();
    private final Semaphore zzaXP = new Semaphore(2);
    private volatile boolean zzaXQ;

    final class zza<V> extends FutureTask<V> {
        private final String zzaXR;
        final /* synthetic */ zzv zzaXS;

        zza(zzv com_google_android_gms_measurement_internal_zzv, Runnable runnable, String str) {
            this.zzaXS = com_google_android_gms_measurement_internal_zzv;
            super(runnable, null);
            zzx.zzz(str);
            this.zzaXR = str;
        }

        zza(zzv com_google_android_gms_measurement_internal_zzv, Callable<V> callable, String str) {
            this.zzaXS = com_google_android_gms_measurement_internal_zzv;
            super(callable);
            zzx.zzz(str);
            this.zzaXR = str;
        }

        protected final void setException(Throwable th) {
            this.zzaXS.zzAo().zzCE().zzj(this.zzaXR, th);
            super.setException(th);
        }
    }

    final class zzb implements UncaughtExceptionHandler {
        private final String zzaXR;
        final /* synthetic */ zzv zzaXS;

        public zzb(zzv com_google_android_gms_measurement_internal_zzv, String str) {
            this.zzaXS = com_google_android_gms_measurement_internal_zzv;
            zzx.zzz(str);
            this.zzaXR = str;
        }

        public final void uncaughtException(Thread thread, Throwable th) {
            synchronized (this) {
                this.zzaXS.zzAo().zzCE().zzj(this.zzaXR, th);
            }
        }
    }

    final class zzc extends Thread {
        final /* synthetic */ zzv zzaXS;
        private final Object zzaXT = new Object();
        private final BlockingQueue<FutureTask<?>> zzaXU;

        public zzc(zzv com_google_android_gms_measurement_internal_zzv, String str, BlockingQueue<FutureTask<?>> blockingQueue) {
            this.zzaXS = com_google_android_gms_measurement_internal_zzv;
            zzx.zzz(str);
            this.zzaXU = blockingQueue;
            setName(str);
        }

        private void zza(InterruptedException interruptedException) {
            this.zzaXS.zzAo().zzCF().zzj(getName() + " was interrupted", interruptedException);
        }

        public final void run() {
            Object obj = null;
            while (obj == null) {
                try {
                    this.zzaXS.zzaXP.acquire();
                    obj = 1;
                } catch (InterruptedException e) {
                    zza(e);
                }
            }
            while (true) {
                try {
                    FutureTask futureTask = (FutureTask) this.zzaXU.poll();
                    if (futureTask != null) {
                        futureTask.run();
                    } else {
                        synchronized (this.zzaXT) {
                            if (this.zzaXU.peek() == null && !this.zzaXS.zzaXQ) {
                                try {
                                    this.zzaXT.wait(30000);
                                } catch (InterruptedException e2) {
                                    zza(e2);
                                }
                            }
                        }
                        synchronized (this.zzaXS.zzaXO) {
                            if (this.zzaXU.peek() == null) {
                                break;
                            }
                        }
                    }
                } catch (Throwable th) {
                    synchronized (this.zzaXS.zzaXO) {
                        this.zzaXS.zzaXP.release();
                        this.zzaXS.zzaXO.notifyAll();
                        if (this == this.zzaXS.zzaXI) {
                            this.zzaXS.zzaXI = null;
                        } else if (this == this.zzaXS.zzaXJ) {
                            this.zzaXS.zzaXJ = null;
                        } else {
                            this.zzaXS.zzAo().zzCE().zzfg("Current scheduler thread is neither worker nor network");
                        }
                    }
                }
            }
            synchronized (this.zzaXS.zzaXO) {
                this.zzaXS.zzaXP.release();
                this.zzaXS.zzaXO.notifyAll();
                if (this == this.zzaXS.zzaXI) {
                    this.zzaXS.zzaXI = null;
                } else if (this == this.zzaXS.zzaXJ) {
                    this.zzaXS.zzaXJ = null;
                } else {
                    this.zzaXS.zzAo().zzCE().zzfg("Current scheduler thread is neither worker nor network");
                }
            }
        }

        public final void zzfb() {
            synchronized (this.zzaXT) {
                this.zzaXT.notifyAll();
            }
        }
    }

    zzv(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    private void zza(FutureTask<?> futureTask) {
        synchronized (this.zzaXO) {
            this.zzaXK.add(futureTask);
            if (this.zzaXI == null) {
                this.zzaXI = new zzc(this, "Measurement Worker", this.zzaXK);
                this.zzaXI.setUncaughtExceptionHandler(this.zzaXM);
                this.zzaXI.start();
            } else {
                this.zzaXI.zzfb();
            }
        }
    }

    private void zzb(FutureTask<?> futureTask) {
        synchronized (this.zzaXO) {
            this.zzaXL.add(futureTask);
            if (this.zzaXJ == null) {
                this.zzaXJ = new zzc(this, "Measurement Network", this.zzaXL);
                this.zzaXJ.setUncaughtExceptionHandler(this.zzaXN);
                this.zzaXJ.start();
            } else {
                this.zzaXJ.zzfb();
            }
        }
    }

    public /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public /* bridge */ /* synthetic */ zzp zzAo() {
        return super.zzAo();
    }

    public void zzCd() {
        if (Thread.currentThread() != this.zzaXJ) {
            throw new IllegalStateException("Call expected from network thread");
        }
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

    public <V> Future<V> zzd(Callable<V> callable) throws IllegalStateException {
        zzjv();
        zzx.zzz(callable);
        FutureTask com_google_android_gms_measurement_internal_zzv_zza = new zza(this, (Callable) callable, "Task exception on worker thread");
        if (Thread.currentThread() == this.zzaXI) {
            com_google_android_gms_measurement_internal_zzv_zza.run();
        } else {
            zza(com_google_android_gms_measurement_internal_zzv_zza);
        }
        return com_google_android_gms_measurement_internal_zzv_zza;
    }

    public void zzg(Runnable runnable) throws IllegalStateException {
        zzjv();
        zzx.zzz(runnable);
        zza(new zza(this, runnable, "Task exception on worker thread"));
    }

    public void zzh(Runnable runnable) throws IllegalStateException {
        zzjv();
        zzx.zzz(runnable);
        zzb(new zza(this, runnable, "Task exception on network thread"));
    }

    protected void zziJ() {
    }

    public /* bridge */ /* synthetic */ void zzjj() {
        super.zzjj();
    }

    public void zzjk() {
        if (Thread.currentThread() != this.zzaXI) {
            throw new IllegalStateException("Call expected from worker thread");
        }
    }

    public /* bridge */ /* synthetic */ zzmq zzjl() {
        return super.zzjl();
    }
}
