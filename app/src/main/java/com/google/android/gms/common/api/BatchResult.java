package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzx;
import java.util.concurrent.TimeUnit;

public final class BatchResult implements Result {
    private final Status zzUX;
    private final PendingResult<?>[] zzagc;

    BatchResult(Status status, PendingResult<?>[] pendingResultArr) {
        this.zzUX = status;
        this.zzagc = pendingResultArr;
    }

    public final Status getStatus() {
        return this.zzUX;
    }

    public final <R extends Result> R take(BatchResultToken<R> batchResultToken) {
        zzx.zzb(batchResultToken.mId < this.zzagc.length, (Object) "The result token does not belong to this batch");
        return this.zzagc[batchResultToken.mId].await(0, TimeUnit.MILLISECONDS);
    }
}
