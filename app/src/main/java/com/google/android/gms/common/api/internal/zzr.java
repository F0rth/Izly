package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResult.zza;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import java.util.concurrent.TimeUnit;

public final class zzr<R extends Result> extends OptionalPendingResult<R> {
    private final zzb<R> zzaiy;

    public zzr(PendingResult<R> pendingResult) {
        if (pendingResult instanceof zzb) {
            this.zzaiy = (zzb) pendingResult;
            return;
        }
        throw new IllegalArgumentException("OptionalPendingResult can only wrap PendingResults generated by an API call.");
    }

    public final R await() {
        return this.zzaiy.await();
    }

    public final R await(long j, TimeUnit timeUnit) {
        return this.zzaiy.await(j, timeUnit);
    }

    public final void cancel() {
        this.zzaiy.cancel();
    }

    public final R get() {
        if (isDone()) {
            return await(0, TimeUnit.MILLISECONDS);
        }
        throw new IllegalStateException("Result is not available. Check that isDone() returns true before calling get().");
    }

    public final boolean isCanceled() {
        return this.zzaiy.isCanceled();
    }

    public final boolean isDone() {
        return this.zzaiy.isReady();
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback) {
        this.zzaiy.setResultCallback(resultCallback);
    }

    public final void setResultCallback(ResultCallback<? super R> resultCallback, long j, TimeUnit timeUnit) {
        this.zzaiy.setResultCallback(resultCallback, j, timeUnit);
    }

    public final void zza(zza com_google_android_gms_common_api_PendingResult_zza) {
        this.zzaiy.zza(com_google_android_gms_common_api_PendingResult_zza);
    }

    public final Integer zzpa() {
        return this.zzaiy.zzpa();
    }
}
