package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzx;

public class BooleanResult implements Result {
    private final Status zzUX;
    private final boolean zzagf;

    public BooleanResult(Status status, boolean z) {
        this.zzUX = (Status) zzx.zzb((Object) status, (Object) "Status must not be null");
        this.zzagf = z;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof BooleanResult)) {
                return false;
            }
            BooleanResult booleanResult = (BooleanResult) obj;
            if (!this.zzUX.equals(booleanResult.zzUX)) {
                return false;
            }
            if (this.zzagf != booleanResult.zzagf) {
                return false;
            }
        }
        return true;
    }

    public Status getStatus() {
        return this.zzUX;
    }

    public boolean getValue() {
        return this.zzagf;
    }

    public final int hashCode() {
        return (this.zzagf ? 1 : 0) + ((this.zzUX.hashCode() + 527) * 31);
    }
}
