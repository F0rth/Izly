package com.google.android.gms.measurement.internal;

abstract class zzz extends zzy {
    private boolean zzQk;
    private boolean zzQl;
    private boolean zzaYC;

    zzz(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
        this.zzaTV.zzb(this);
    }

    boolean isInitialized() {
        return this.zzQk && !this.zzQl;
    }

    boolean zzDi() {
        return this.zzaYC;
    }

    public final void zza() {
        if (this.zzQk) {
            throw new IllegalStateException("Can't initialize twice");
        }
        zziJ();
        this.zzaTV.zzDg();
        this.zzQk = true;
    }

    protected abstract void zziJ();

    protected void zzjv() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }
}
