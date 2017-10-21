package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzx;

class zzi {
    final String mName;
    final String zzaUa;
    final long zzaVP;
    final long zzaVQ;
    final long zzaVR;

    zzi(String str, String str2, long j, long j2, long j3) {
        boolean z = true;
        zzx.zzcM(str);
        zzx.zzcM(str2);
        zzx.zzac(j >= 0);
        if (j2 < 0) {
            z = false;
        }
        zzx.zzac(z);
        this.zzaUa = str;
        this.mName = str2;
        this.zzaVP = j;
        this.zzaVQ = j2;
        this.zzaVR = j3;
    }

    zzi zzCB() {
        return new zzi(this.zzaUa, this.mName, this.zzaVP + 1, this.zzaVQ + 1, this.zzaVR);
    }

    zzi zzab(long j) {
        return new zzi(this.zzaUa, this.mName, this.zzaVP, this.zzaVQ, j);
    }
}
