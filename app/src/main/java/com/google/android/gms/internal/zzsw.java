package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

final class zzsw {
    final int tag;
    final byte[] zzbuv;

    zzsw(int i, byte[] bArr) {
        this.tag = i;
        this.zzbuv = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzsw)) {
                return false;
            }
            zzsw com_google_android_gms_internal_zzsw = (zzsw) obj;
            if (this.tag != com_google_android_gms_internal_zzsw.tag) {
                return false;
            }
            if (!Arrays.equals(this.zzbuv, com_google_android_gms_internal_zzsw.zzbuv)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbuv);
    }

    final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
        com_google_android_gms_internal_zzsn.zzmB(this.tag);
        com_google_android_gms_internal_zzsn.zzH(this.zzbuv);
    }

    final int zzz() {
        return (zzsn.zzmC(this.tag) + 0) + this.zzbuv.length;
    }
}
